package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.inventory.GOTContainerMillstone;
import got.common.tileentity.GOTTileEntityMillstone;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GOTGuiMillstone extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/millstone.png");
	public GOTTileEntityMillstone theMillstone;

	public GOTGuiMillstone(InventoryPlayer inv, GOTTileEntityMillstone millstone) {
		super(new GOTContainerMillstone(inv, millstone));
		theMillstone = millstone;
		ySize = 182;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theMillstone.isMilling()) {
			int k = theMillstone.getMillProgressScaled(14);
			drawTexturedModalRect(guiLeft + 85, guiTop + 47, 176, 0, 14, k);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theMillstone.getInventoryName();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 88, 4210752);
	}
}
