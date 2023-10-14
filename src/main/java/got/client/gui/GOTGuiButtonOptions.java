package got.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GOTGuiButtonOptions extends GuiButton {
	public String baseDisplayString;

	public GOTGuiButtonOptions(int i, int j, int k, int l, int i1, String s) {
		super(i, j, k, l, i1, s);
		baseDisplayString = s;
	}

	@Override
	public void drawButton(Minecraft mc, int i, int j) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager().bindTexture(GOTGuiQuestBook.guiTexture);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			field_146123_n = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
			int k = getHoverState(field_146123_n);
			Gui.func_146110_a(xPosition, yPosition, 170.0f, 256 + k * 20, width, height, 512.0f, 512.0f);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			Gui.func_146110_a(xPosition, yPosition, 170.0f, 316.0f, width / 2, height, 512.0f, 512.0f);
			Gui.func_146110_a(xPosition + width / 2, yPosition, 370 - (float) width / 2, 316.0f, width / 2, height, 512.0f, 512.0f);
			mouseDragged(mc, i, j);
			fontrenderer.drawString(displayString, xPosition + width / 2 - fontrenderer.getStringWidth(displayString) / 2, yPosition + (height - 8) / 2, 0x5E1C15);
		}
	}

	public void drawTooltip(Minecraft mc, int i, int j) {
		if (enabled && func_146115_a()) {
			String s = getDescription();
			int border = 3;
			int stringWidth = 200;
			int stringHeight = mc.fontRenderer.listFormattedStringToWidth(s, stringWidth).size() * mc.fontRenderer.FONT_HEIGHT;
			int offset = 10;
			Gui.drawRect(i += offset, j += offset, i + stringWidth + border * 2, j + stringHeight + border * 2, -1073741824);
			mc.fontRenderer.drawSplitString(s, i + border, j + border, stringWidth, 16777215);
		}
	}

	public String getDescription() {
		return StatCollector.translateToLocal(baseDisplayString + ".desc.on") + "\n\n" + StatCollector.translateToLocal(baseDisplayString + ".desc.off");
	}

	public void setState(boolean flag) {
		setState(flag ? StatCollector.translateToLocal("got.gui.button.on") : StatCollector.translateToLocal("got.gui.button.off"));
	}

	public void setState(String s) {
		displayString = StatCollector.translateToLocal(baseDisplayString) + ": " + s;
	}
}