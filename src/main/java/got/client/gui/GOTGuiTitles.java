package got.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.client.GOTReflectionClient;
import got.common.GOTLevelData;
import got.common.database.GOTTitle;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketSelectTitle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.*;

public class GOTGuiTitles extends GOTGuiMenuBase {
	private static final int COLOR_BOX_WIDTH = 8;
	private static final int SCROLL_BAR_WIDTH = 11;
	private static final int SCROLL_BAR_HEIGHT = 144;
	private static final int SCROLL_BAR_X = 197 - (SCROLL_BAR_WIDTH - 1) / 2;
	private static final int SCROLL_BAR_Y = 30;
	private static final int SCROLL_WIDGET_HEIGHT = 8;

	private final List<GOTTitle> displayedTitles = new ArrayList<>();
	private final Map<GOTTitle, Pair<Boolean, Pair<Integer, Integer>>> displayedTitleInfo = new HashMap<>();
	private final Map<EnumChatFormatting, Pair<Integer, Integer>> displayedColorBoxes = new EnumMap<>(EnumChatFormatting.class);

	private GOTTitle.PlayerTitle currentTitle;
	private GOTTitle selectedTitle;
	private EnumChatFormatting selectedColor = EnumChatFormatting.WHITE;
	private GuiButton selectButton;
	private GuiButton removeButton;
	private float currentScroll;
	private boolean isScrolling;
	private boolean wasMouseDown;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == selectButton && (currentTitle == null || selectedTitle != currentTitle.getTitle() || selectedColor != currentTitle.getColor())) {
				IMessage packet = new GOTPacketSelectTitle(new GOTTitle.PlayerTitle(selectedTitle, selectedColor));
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
			} else if (button == removeButton) {
				IMessage packet = new GOTPacketSelectTitle(null);
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
			} else if (button.enabled && button == goBack) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else {
				super.actionPerformed(button);
			}
		}
	}

	@Override
	@SuppressWarnings("NonConstantStringShouldBeStringBuffer")
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		setupScrollBar(i, j);
		String titleName = currentTitle == null ? StatCollector.translateToLocal("got.gui.titles.currentTitle.none") : currentTitle.getTitle().getDisplayName(mc.thePlayer);
		EnumChatFormatting currentColor = currentTitle == null ? EnumChatFormatting.WHITE : currentTitle.getColor();
		titleName = currentColor + titleName + EnumChatFormatting.RESET;
		drawCenteredString(StatCollector.translateToLocalFormatted("got.gui.titles.currentTitle", titleName), guiLeft + sizeX / 2, guiTop, 16777215);
		displayedTitleInfo.clear();
		int titleX = guiLeft + sizeX / 2;
		int titleY = guiTop + 30;
		int yIncrement = 12;
		drawVerticalLine(titleX - 70, titleY - 1, titleY + yIncrement * 12, -1711276033);
		drawVerticalLine(titleX + 70 - 1, titleY - 1, titleY + yIncrement * 12, -1711276033);
		int size = displayedTitles.size();
		int min = Math.round(currentScroll * (size - 12));
		int max = 11 + Math.round(currentScroll * (size - 12));
		min = Math.max(min, 0);
		max = Math.min(max, size - 1);
		for (int index = min; index <= max; ++index) {
			String name;
			GOTTitle title = displayedTitles.get(index);
			boolean isCurrentTitle = currentTitle != null && currentTitle.getTitle() == title;
			if (title != null) {
				name = title.getDisplayName(mc.thePlayer);
				if (isCurrentTitle) {
					name = currentTitle.getColor() + ('[' + name + ']');
				}
			} else {
				name = "---";
			}
			int nameWidth = fontRendererObj.getStringWidth(name);
			int nameHeight = mc.fontRenderer.FONT_HEIGHT;
			int nameXMin = titleX - nameWidth / 2;
			int nameXMax = titleX + nameWidth / 2;
			int nameYMin = titleY;
			int nameYMax = titleY + nameHeight;
			int mouseOver = i >= nameXMin && i < nameXMax && j >= nameYMin && j < nameYMax ? 1 : 0;
			if (title != null) {
				displayedTitleInfo.put(title, Pair.of(mouseOver != 0, Pair.of(titleX, titleY)));
			}
			int textColor = title != null ? title.canPlayerUse(mc.thePlayer) ? mouseOver != 0 ? 16777120 : 16777215 : mouseOver != 0 ? 12303291 : 7829367 : 7829367;
			drawCenteredString(name, titleX, titleY, textColor);
			titleY += yIncrement;
		}
		displayedColorBoxes.clear();
		if (selectedTitle != null) {
			String title = selectedColor + selectedTitle.getDisplayName(mc.thePlayer);
			drawCenteredString(title, guiLeft + sizeX / 2, guiTop + 185, 16777215);
			Collection<EnumChatFormatting> colorCodes = new ArrayList<>();
			for (EnumChatFormatting ecf : EnumChatFormatting.values()) {
				if (ecf.isColor()) {
					colorCodes.add(ecf);
				}
			}
			int colorBoxGap = 4;
			int colorX = guiLeft + sizeX / 2 - (COLOR_BOX_WIDTH * colorCodes.size() + colorBoxGap * (colorCodes.size() - 1)) / 2;
			int colorY = guiTop + 200;
			for (EnumChatFormatting code : colorCodes) {
				int color = GOTReflectionClient.getFormattingColor(code);
				float[] rgb = new Color(color).getColorComponents(null);
				GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
				boolean mouseOver = i >= colorX && i < colorX + COLOR_BOX_WIDTH && j >= colorY && j < colorY + COLOR_BOX_WIDTH;
				GL11.glDisable(3553);
				drawTexturedModalRect(colorX, colorY + (mouseOver ? -1 : 0), 0, 0, COLOR_BOX_WIDTH, COLOR_BOX_WIDTH);
				GL11.glEnable(3553);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				displayedColorBoxes.put(code, Pair.of(colorX, colorY));
				colorX += COLOR_BOX_WIDTH + colorBoxGap;
			}
		}
		if (displayedTitles.size() > 12) {
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int scroll = (int) (currentScroll * (SCROLL_BAR_HEIGHT - SCROLL_WIDGET_HEIGHT));
			int x1 = guiLeft + SCROLL_BAR_X;
			int y1 = guiTop + SCROLL_BAR_Y + scroll;
			int scrollWidgetWidth = 11;
			int x2 = x1 + scrollWidgetWidth;
			int y2 = y1 + SCROLL_WIDGET_HEIGHT;
			drawRect(x1, y1, x2, y2, -1426063361);
		}
		selectButton.enabled = selectedTitle != null;
		removeButton.enabled = currentTitle != null;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		super.drawScreen(i, j, f);
		for (Map.Entry<GOTTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : displayedTitleInfo.entrySet()) {
			GOTTitle title = entry.getKey();
			String desc = title.getDescription(mc.thePlayer);
			boolean mouseOver = entry.getValue().getLeft();
			if (mouseOver) {
				int stringWidth = 200;
				List<String> titleLines = fontRendererObj.listFormattedStringToWidth(desc, stringWidth);
				int offset = 10;
				int x = i + offset;
				int y = j + offset;
				func_146283_a(titleLines, x, y);
				GL11.glDisable(2896);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			}
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			i = Integer.signum(i);
			int j = displayedTitles.size() - 12;
			currentScroll -= (float) i / j;
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
		}
	}

	@Override
	public void initGui() {
		sizeX = 256;
		super.initGui();
		guiLeft = (width - sizeX) / 2 + 100;
		guiTop = (height - sizeY) / 2 + 20;
		selectButton = new GOTGuiButton(0, guiLeft + sizeX / 2 - 290, guiTop + 90, 80, 20, StatCollector.translateToLocal("got.gui.titles.select"));
		buttonList.add(selectButton);
		removeButton = new GOTGuiButton(1, guiLeft + sizeX / 2 - 200, guiTop + 90, 80, 20, StatCollector.translateToLocal("got.gui.titles.remove"));
		buttonList.add(removeButton);
		goBack = new GOTGuiButton(2, guiLeft + sizeX / 2 - 290, guiTop + 120, 170, 20, StatCollector.translateToLocal("got.gui.menuButton"));
		buttonList.add(goBack);
		updateScreen();
	}

	@Override
	public void mouseClicked(int i, int j, int mouse) {
		if (mouse == 0) {
			for (Map.Entry<GOTTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : displayedTitleInfo.entrySet()) {
				GOTTitle title = entry.getKey();
				boolean mouseOver = entry.getValue().getLeft();
				if (mouseOver && title.canPlayerUse(mc.thePlayer)) {
					selectedTitle = title;
					selectedColor = EnumChatFormatting.WHITE;
					return;
				}
			}
			if (!displayedColorBoxes.isEmpty()) {
				for (Map.Entry<EnumChatFormatting, Pair<Integer, Integer>> entry : displayedColorBoxes.entrySet()) {
					EnumChatFormatting color = entry.getKey();
					int colorX = entry.getValue().getLeft();
					int colorY = entry.getValue().getRight();
					if (i >= colorX && i < colorX + COLOR_BOX_WIDTH && j >= colorY && j < colorY + COLOR_BOX_WIDTH) {
						selectedColor = color;
						break;
					}
				}
			}
		}
		super.mouseClicked(i, j, mouse);
	}

	private void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = guiLeft + SCROLL_BAR_X;
		int j1 = guiTop + SCROLL_BAR_Y;
		int i2 = i1 + SCROLL_BAR_WIDTH;
		int j2 = j1 + SCROLL_BAR_HEIGHT;
		if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
			isScrolling = true;
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (j - j1 - SCROLL_WIDGET_HEIGHT / 2.0f) / ((float) (j2 - j1) - SCROLL_WIDGET_HEIGHT);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		currentTitle = GOTLevelData.getData(mc.thePlayer).getPlayerTitle();
		displayedTitles.clear();
		List<GOTTitle> availableTitles = new ArrayList<>();
		List<GOTTitle> unavailableTitles = new ArrayList<>();
		for (GOTTitle title : GOTTitle.CONTENT) {
			if (title.canPlayerUse(mc.thePlayer)) {
				availableTitles.add(title);
			} else if (title.canDisplay(mc.thePlayer)) {
				unavailableTitles.add(title);
			}
		}
		Comparator<GOTTitle> sorter = GOTTitle.createTitleSorter(mc.thePlayer);
		availableTitles.sort(sorter);
		unavailableTitles.sort(sorter);
		displayedTitles.addAll(availableTitles);
		displayedTitles.add(null);
		displayedTitles.addAll(unavailableTitles);
	}
}
