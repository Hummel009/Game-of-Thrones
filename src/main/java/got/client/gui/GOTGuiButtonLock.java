package got.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GOTGuiButtonLock extends GuiButton {
	private static ResourceLocation texture = new ResourceLocation("got:textures/gui/widgets.png");

	public GOTGuiButtonLock(int i, int j, int k) {
		super(i, j, k, 20, 20, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, 0, 196 + k * 20, width, height);
			mouseDragged(mc, i, j);
		}
	}
}