package got.client.gui;

import got.common.GOTGuiMessageTypes;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class GOTGuiMessage extends GOTGuiScreenBase {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("got:textures/gui/message.png");

	private static final int X_SIZE = 240;
	private static final int Y_SIZE = 160;

	private final GOTGuiMessageTypes type;
	private GuiButton buttonDismiss;

	private int buttonTimer = 60;
	private int guiLeft;
	private int guiTop;

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
		mc.getTextureManager().bindTexture(GUI_TEXTURE);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, X_SIZE, Y_SIZE);
		String msg = type.getMessage();
		int border = 10;
		int pageWidth = X_SIZE - border * 2;
		String[] splitNewline = msg.split(Pattern.quote("\\n"));
		Collection<String> msgLines = new ArrayList<>();
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
		drawCenteredString(s, guiLeft + X_SIZE / 2, guiTop + Y_SIZE - border / 2 - fontRendererObj.FONT_HEIGHT, 9666921);
		if (buttonTimer > 0) {
			--buttonTimer;
		}
		buttonDismiss.enabled = buttonTimer == 0;
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		guiLeft = (width - X_SIZE) / 2;
		guiTop = (height - Y_SIZE) / 2;
		buttonDismiss = new GOTGuiButton(0, guiLeft + X_SIZE / 2 - 40, guiTop + Y_SIZE + 20, 80, 20, StatCollector.translateToLocal("got.gui.message.dismiss"));
		buttonList.add(buttonDismiss);
	}

	@Override
	public void keyTyped(char c, int i) {
	}
}