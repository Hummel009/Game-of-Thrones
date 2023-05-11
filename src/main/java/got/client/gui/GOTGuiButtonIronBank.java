package got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GOTGuiButtonIronBank extends GuiButton {
	public GOTGuiButtonIronBank(int id, int x, int y) {
		super(id, x, y, 28, 28, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mousex, int mousey) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(new ResourceLocation("got:textures/gui/transparent.png"));
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = mousex >= xPosition && mousey >= yPosition && mousex < xPosition + width && mousey < yPosition + height;
			int k = getHoverState(field_146123_n);
			GL11.glEnable(3042);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(770, 771);
			drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
			drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
			mouseDragged(mc, mousex, mousey);
			int l = 14737632;
			if (packedFGColour != 0) {
				l = packedFGColour;
			} else if (!enabled) {
				l = 10526880;
			} else if (field_146123_n) {
				l = 16777120;
			}
			drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, l);
		}
	}
}
