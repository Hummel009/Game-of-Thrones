package got.client.gui;

import got.common.inventory.GOTContainerOven;
import got.common.tileentity.GOTTileEntityOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GOTGuiOven extends GuiContainer {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/oven.png");

	private final GOTTileEntityOven theOven;

	public GOTGuiOven(InventoryPlayer inv, GOTTileEntityOven oven) {
		super(new GOTContainerOven(inv, oven));
		theOven = oven;
		ySize = 215;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(GUI_TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theOven.isCooking()) {
			int k = theOven.getCookTimeRemainingScaled(12);
			drawTexturedModalRect(guiLeft + 80, guiTop + 94 + 12 - k, 176, 12 - k, 14, k + 2);
		}
		int l = theOven.getCookProgressScaled(24);
		drawTexturedModalRect(guiLeft + 80, guiTop + 40, 176, 14, 16, l + 1);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		String s = theOven.getInventoryName();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 121, 4210752);
	}
}