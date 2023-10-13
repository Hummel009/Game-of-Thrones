package got.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GOTGuiButtonFactionsMap extends GuiButton {
	public GOTGuiButtonFactionsMap(int i, int x, int y) {
		super(i, x, y, 8, 8, "");
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(GOTGuiFactions.factionsTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			drawTexturedModalRect(xPosition, yPosition, 17 + (field_146123_n ? width : 0), 142, width, height);
			mouseDragged(mc, i, j);
		}
	}
}