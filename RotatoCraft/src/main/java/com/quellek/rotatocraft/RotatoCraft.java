package com.quellek.rotatocraft;

import org.apache.logging.log4j.Logger;

import com.quellek.rotatocraft.proxy.CommonProxy;
import com.quellek.rotatocraft.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class RotatoCraft {
	
	@Instance
	public static RotatoCraft instance;
	
	public static Logger logger;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs creativeTab = new CreativeTabs("RotatoCraft") {
		@Override
		public ItemStack getTabIconItem() { return new ItemStack(ModBlocks.blockSpinnerPlate); }
	};
	
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}