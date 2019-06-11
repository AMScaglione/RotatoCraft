package com.quellek.rotatocraft;

import com.quellek.rotatocraft.blocks.BlockSpinnerPlate;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ModBlocks {

		@GameRegistry.ObjectHolder("rotato:spinner_plate")
		public static BlockSpinnerPlate blockSpinnerPlate;
		
		@SideOnly(Side.CLIENT)
		public static void initModels() {
			blockSpinnerPlate.initModel();
		}
}
