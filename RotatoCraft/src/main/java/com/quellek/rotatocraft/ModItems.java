package com.quellek.rotatocraft;

import java.util.ArrayList;
import java.util.List;

import com.quellek.rotatocraft.blocks.BlockSpinnerPlate;
import com.quellek.rotatocraft.items.Rotato;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems 
{
	@GameRegistry.ObjectHolder("rotato:rotato")
	public static Rotato rotato;
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		rotato.initModel();
	}
}
