package got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GOTGuiTradeButton extends GuiButton {
	public GOTGuiTradeButton(int i, int j, int k) {
		super(i, j, k, 18, 18, "Trade");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		GL11.glDisable(2896);
		mc.getTextureManager().bindTexture(GOTGuiTrade.guiTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
		int hoverState = getHoverState(field_146123_n);
		Gui.func_146110_a(xPosition, yPosition, 176.0f, hoverState * 18, width, height, 512.0f, 512.0f);
		mouseDragged(mc, i, j);
		GL11.glEnable(2896);
	}
}
