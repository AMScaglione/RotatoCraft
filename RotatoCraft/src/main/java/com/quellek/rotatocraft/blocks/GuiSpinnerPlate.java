package com.quellek.rotatocraft.blocks;

import com.quellek.rotatocraft.util.Reference;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class GuiSpinnerPlate extends GuiContainer{
	
	public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/spinner_plate.png");
    private TileSpinnerPlate spinner;
    
    public GuiSpinnerPlate(TileSpinnerPlate tileEntity, ContainerSpinnerPlate container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
        
        spinner = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        if (spinner.getProgress() > 0) {
        	int percentage = 100 - spinner.getProgress() * 100 / TileSpinnerPlate.MAX_PROGRESS;
        	drawString(mc.fontRenderer, "Progress: " + percentage + "%", guiLeft + 10, guiTop + 50, 0xffffff);
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
    	renderHoveredToolTip(mouseX, mouseY);
    }
}
