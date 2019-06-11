package com.quellek.rotatocraft.proxy;

import javax.annotation.Nullable;

import com.quellek.rotatocraft.blocks.ContainerSpinnerPlate;
import com.quellek.rotatocraft.blocks.GuiSpinnerPlate;
import com.quellek.rotatocraft.blocks.TileSpinnerPlate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
	
	@Nullable
	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSpinnerPlate) {
            return new ContainerSpinnerPlate(player.inventory, (TileSpinnerPlate) te);
        }
        return null;
    }

	@Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSpinnerPlate) {
        	TileSpinnerPlate containerTileEntity = (TileSpinnerPlate) te;
            return new GuiSpinnerPlate(containerTileEntity, new ContainerSpinnerPlate(player.inventory, containerTileEntity));
        }
        return null;
    }
}
