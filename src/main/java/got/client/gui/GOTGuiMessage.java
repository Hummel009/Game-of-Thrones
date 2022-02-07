package got.client.gui;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;

import got.common.GOTGuiMessageTypes;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.*;

public class GOTGuiMessage extends GOTGuiScreenBase {
	private static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/message.png");
	private GOTGuiMessageTypes type;
	private int xSize = 240;
	private int ySize = 160;
	private int border = 10;
	private int guiLeft;
	private int guiTop;
	private GuiButton buttonDismiss;
	private int buttonTimer = 60;

	public GOTGuiMessage(GOTGuiMessageTypes t) {
		type = t;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonDismiss) {
			mc.thePlayer.closeScreen();
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(guiTexture);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String msg = type.getMessage();
		int pageWidth = xSize - border * 2;
		String[] splitNewline = msg.split(Pattern.quote("\\n"));
		ArrayList<String> msgLines = new ArrayList();
		for (String line : splitNewline) {
			msgLines.addAll(fontRendererObj.listFormattedStringToWidth(line, pageWidth));
		}
		int x = guiLeft + border;
		int y = guiTop + border;
		for (String line : msgLines) {
			fontRendererObj.drawString(line, x, y, 8019267);
			y += fontRendererObj.FONT_HEIGHT;
		}
		String s = StatCollector.translateToLocal("got.gui.message.notDisplayedAgain");
		this.drawCenteredString(s, guiLeft + xSize / 2, guiTop + ySize - border / 2 - fontRendererObj.FONT_HEIGHT, 9666921);

		if (buttonTimer > 0) {
			--buttonTimer;
		}
		buttonDismiss.enabled = buttonTimer == 0;
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonDismiss = new GOTGuiButton(0, guiLeft + xSize / 2 - 40, guiTop + ySize + 20, 80, 20, StatCollector.translateToLocal("got.gui.message.dismiss"));
		buttonList.add(buttonDismiss);
	}

	@Override
	public void keyTyped(char c, int i) {
	}
}
