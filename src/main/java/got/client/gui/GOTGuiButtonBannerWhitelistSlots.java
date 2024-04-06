package got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTGuiButtonBannerWhitelistSlots extends GuiButton {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/banner_edit.png");

	public GOTGuiButtonBannerWhitelistSlots(int i, int j, int k) {
		super(i, j, k, 7, 7, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(GUI_TEXTURE);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			boolean over = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = 226 + id * width;
			int l = getHoverState(over) * width;
			drawTexturedModalRect(xPosition, yPosition, k, l, width, height);
		}
	}
}
