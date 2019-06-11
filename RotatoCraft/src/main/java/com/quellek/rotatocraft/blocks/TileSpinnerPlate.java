package com.quellek.rotatocraft.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class TileSpinnerPlate extends TileEntity implements ITickable {
	
	public static final int INPUT_SLOTS = 1;
	public static final int OUTPUT_SLOTS = 1;
	public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
	
	public static final int MAX_PROGRESS = 40;
	
	private int progress = 0;
	private SpinnerState state = SpinnerState.OFF;
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (progress > 0) {
				setState(SpinnerState.WORKING);
				progress--;
				if (progress == 0) {
					attemptSmelt();
				}
				markDirty();
			} else {
				startSmelt();
			}
		}
	}
	
	private boolean insertOutput(ItemStack output, boolean simulate) {
        for (int i = 0 ; i < OUTPUT_SLOTS ; i++) {
            ItemStack remaining = outputHandler.insertItem(i, output, simulate);
            if (remaining.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    private void startSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; i++) {
            ItemStack result = RecipesSpinnerPlate.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (insertOutput(result.copy(), true)) {
                	setState(SpinnerState.WORKING);
                    progress = MAX_PROGRESS;
                    markDirty();
                    return;
                }
            }
        }
        setState(SpinnerState.OFF);
    }

    private void attemptSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; i++) {
            ItemStack result = RecipesSpinnerPlate.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {
                    inputHandler.extractItem(i, 1, false);
                    break;
                }
            }
        }
    }
	
	public int getProgress() {
		return progress;
	}
	
	public void setProgress(int data) {
		progress = data;
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTag = super.getUpdateTag();
		nbtTag.setInteger("state", state.ordinal());
		return nbtTag;
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		int stateIndex = pkt.getNbtCompound().getInteger("state");
		
		if (world.isRemote && stateIndex != state.ordinal()) {
			state = SpinnerState.VALUES[stateIndex];
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}
	
	public void setState(SpinnerState state) {
		if (this.state != state) {
			this.state = state;
			markDirty();
			IBlockState blockState = world.getBlockState(pos);
			getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
		}
	}
	
	public SpinnerState getState() {
		return state;
	}
	
	private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {
		
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			ItemStack result = RecipesSpinnerPlate.instance().getSmeltingResult(stack);
			return !result.isEmpty();
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			TileSpinnerPlate.this.markDirty();
		}
	};
	
	private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
		
		@Override
		protected void onContentsChanged(int slot) {
			TileSpinnerPlate.this.markDirty();
		}
	};
	
	private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);
	
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        progress = compound.getInteger("progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("progress", progress);
        return compound;
    }
    
    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
        	if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        	} else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
        	} else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
        	}
        }
        return super.getCapability(capability, facing);
    }
}
