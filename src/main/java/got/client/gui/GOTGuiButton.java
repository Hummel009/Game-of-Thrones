package got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTGuiButton extends GuiButton {
	public GOTGuiButton(int i, int x, int y, int w, int h, String s) {
		super(i, x, y, w, h, s);
	}

	public GOTGuiButton(int i, int x, int y, String s) {
		this(i, x, y, 200, 20, s);
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(new ResourceLocation("got:textures/gui/buttons.png"));
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
			drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
			mouseDragged(mc, i, j);
			int color = 8019267;
			if (!enabled) {
				color = 5521198;
			}
			fontrenderer.drawString(displayString, xPosition + width / 2 - fontrenderer.getStringWidth(displayString) / 2, yPosition + (height - 8) / 2, color);
		}
	}
}