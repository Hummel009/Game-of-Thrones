package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.database.GOTAchievement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GOTGuiButtonAchievements extends GuiButton {
	private boolean leftOrRight;
	private GOTAchievement.Category buttonCategory;

	public GOTGuiButtonAchievements(int i, boolean flag, int j, int k) {
		super(i, j, k, 15, 21, "");
		leftOrRight = flag;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			boolean highlighted;
			mc.getTextureManager().bindTexture(GOTGuiAchievements.getIconsTexture());
			int texU = leftOrRight ? 0 : width * 3;
			int texV = 124;
			highlighted = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			if (!enabled) {
				texU += width * 2;
			} else if (highlighted) {
				texU += width;
			}
			float[] catColors = getButtonCategory().getCategoryRGB();
			GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0f);
			drawTexturedModalRect(xPosition, yPosition, texU, texV, width, height);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			drawTexturedModalRect(xPosition, yPosition, texU, texV + height, width, height);
		}
	}

	public GOTAchievement.Category getButtonCategory() {
		return buttonCategory;
	}

	public void setButtonCategory(GOTAchievement.Category buttonCategory) {
		this.buttonCategory = buttonCategory;
	}
}