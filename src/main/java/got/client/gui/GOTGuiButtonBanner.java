package got.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GOTGuiButtonBanner extends GuiButton {
	private boolean activated;
	private int iconU;
	private int iconV;

	public GOTGuiButtonBanner(int i, int x, int y, int u, int v) {
		super(i, x, y, 16, 16, "");
		iconU = u;
		iconV = v;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			mc.getTextureManager().bindTexture(GOTGuiBanner.getBannerTexture());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int state = getHoverState(field_146123_n);
			drawTexturedModalRect(xPosition, yPosition, iconU + state % 2 * width, iconV + state / 2 * height, width, height);
			mouseDragged(mc, i, j);
		}
	}

	@Override
	public int getHoverState(boolean mouseover) {
		return (isActivated() ? 0 : 2) + (mouseover ? 1 : 0);
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
