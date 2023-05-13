package got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTGuiButtonLeftRight extends GuiButton {
	public static ResourceLocation texture = new ResourceLocation("got:textures/gui/widgets.png");
	public boolean leftOrRight;

	public GOTGuiButtonLeftRight(int i, boolean flag, int j, int k, String s) {
		super(i, j, k, 120, 20, s);
		leftOrRight = flag;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, leftOrRight ? 0 : 136, k * 20, width, height);
			mouseDragged(mc, i, j);
			int color = 8019267;
			if (!enabled) {
				color = 5521198;
			}
			if (leftOrRight) {
				fontrenderer.drawString(displayString, xPosition + 67 - fontrenderer.getStringWidth(displayString) / 2, yPosition + (height - 8) / 2, color);
			} else {
				fontrenderer.drawString(displayString, xPosition + width - 67 - fontrenderer.getStringWidth(displayString) / 2, yPosition + (height - 8) / 2, color);
			}
		}
	}
}