package com.quellek.rotatocraft.items;

import java.util.List;

import javax.annotation.Nullable;

import com.quellek.rotatocraft.RotatoCraft;
import com.quellek.rotatocraft.util.Reference;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Rotato extends Item{

	public Rotato() {
		setRegistryName("rotato");
		setUnlocalizedName(Reference.MOD_ID + ".rotato");
		setCreativeTab(RotatoCraft.creativeTab);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("You've discovered the sacred\n"
				+ "spinning crop. Use wisely.\n\n"
				+ "Probably not great to eat though.");
	}
}
