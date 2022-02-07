package got.client.gui;

import java.awt.Color;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import got.client.GOTReflectionClient;
import got.common.GOTLevelData;
import got.common.database.GOTTitle;
import got.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class GOTGuiTitles extends GOTGuiMenuWBBase {
	private GOTTitle.PlayerTitle currentTitle;
	private List<GOTTitle> displayedTitles = new ArrayList<>();
	private Map<GOTTitle, Pair<Boolean, Pair<Integer, Integer>>> displayedTitleInfo = new HashMap<>();
	private GOTTitle selectedTitle;
	private EnumChatFormatting selectedColor = EnumChatFormatting.WHITE;
	private int colorBoxWidth = 8;
	private int colorBoxGap = 4;
	private Map<EnumChatFormatting, Pair<Integer, Integer>> displayedColorBoxes = new HashMap<>();
	private GuiButton selectButton;
	private GuiButton removeButton;
	private float currentScroll = 0.0f;
	private boolean isScrolling = false;
	private boolean wasMouseDown;
	private int scrollBarWidth = 11;
	private int scrollBarHeight = 144;
	private int scrollBarX = 197 - (scrollBarWidth - 1) / 2;
	private int scrollBarY = 30;
	private int scrollWidgetWidth = 11;
	private int scrollWidgetHeight = 8;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == selectButton && (currentTitle == null || selectedTitle != currentTitle.getTitle() || selectedColor != currentTitle.getColor())) {
				GOTPacketSelectTitle packet = new GOTPacketSelectTitle(new GOTTitle.PlayerTitle(selectedTitle, selectedColor));
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == removeButton) {
				GOTPacketSelectTitle packet = new GOTPacketSelectTitle(null);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button.enabled && button == getGoBack()) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else {
				super.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		setupScrollBar(i, j);
		String titleName = currentTitle == null ? StatCollector.translateToLocal("got.gui.titles.currentTitle.none") : currentTitle.getTitle().getDisplayName(mc.thePlayer);
		EnumChatFormatting currentColor = currentTitle == null ? EnumChatFormatting.WHITE : currentTitle.getColor();
		titleName = currentColor + titleName + EnumChatFormatting.RESET;
		this.drawCenteredString(StatCollector.translateToLocalFormatted("got.gui.titles.currentTitle", titleName), getGuiLeft() + getxSize() / 2, getGuiTop(), 16777215);
		displayedTitleInfo.clear();
		int titleX = getGuiLeft() + getxSize() / 2;
		int titleY = getGuiTop() + 30;
		int yIncrement = 12;
		drawVerticalLine(titleX - 70, titleY - 1, titleY + yIncrement * 12, -1711276033);
		drawVerticalLine(titleX + 70 - 1, titleY - 1, titleY + yIncrement * 12, -1711276033);
		int size = displayedTitles.size();
		int min = 0 + Math.round(currentScroll * (size - 12));
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
					name = "[" + name + "]";
					name = currentTitle.getColor() + name;
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
			this.drawCenteredString(name, titleX, titleY, textColor);
			titleY += yIncrement;
		}
		displayedColorBoxes.clear();
		if (selectedTitle != null) {
			String title = selectedColor + selectedTitle.getDisplayName(mc.thePlayer);
			this.drawCenteredString(title, getGuiLeft() + getxSize() / 2, getGuiTop() + 185, 16777215);
			ArrayList<EnumChatFormatting> colorCodes = new ArrayList<>();
			for (EnumChatFormatting ecf : EnumChatFormatting.values()) {
				if (!ecf.isColor()) {
					continue;
				}
				colorCodes.add(ecf);
			}
			int colorX = getGuiLeft() + getxSize() / 2 - (colorBoxWidth * colorCodes.size() + colorBoxGap * (colorCodes.size() - 1)) / 2;
			int colorY = getGuiTop() + 200;
			for (EnumChatFormatting code : colorCodes) {
				int color = GOTReflectionClient.getFormattingColor(code);
				float[] rgb = new Color(color).getColorComponents(null);
				GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
				boolean mouseOver = i >= colorX && i < colorX + colorBoxWidth && j >= colorY && j < colorY + colorBoxWidth;
				GL11.glDisable(3553);
				this.drawTexturedModalRect(colorX, colorY + (mouseOver ? -1 : 0), 0, 0, colorBoxWidth, colorBoxWidth);
				GL11.glEnable(3553);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				displayedColorBoxes.put(code, Pair.of(colorX, colorY));
				colorX += colorBoxWidth + colorBoxGap;
			}
		}
		if (displayedTitles.size() > 12) {
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			int scroll = (int) (currentScroll * (scrollBarHeight - scrollWidgetHeight));
			int x1 = getGuiLeft() + scrollBarX;
			int y1 = getGuiTop() + scrollBarY + scroll;
			int x2 = x1 + scrollWidgetWidth;
			int y2 = y1 + scrollWidgetHeight;
			Gui.drawRect(x1, y1, x2, y2, -1426063361);
		}
		selectButton.enabled = selectedTitle != null;
		removeButton.enabled = currentTitle != null;
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		super.drawScreen(i, j, f);
		for (Map.Entry<GOTTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : displayedTitleInfo.entrySet()) {
			GOTTitle title = entry.getKey();
			String desc = title.getDescription(mc.thePlayer);
			titleX = (Integer) ((Pair) entry.getValue().getRight()).getLeft();
			titleY = (Integer) ((Pair) entry.getValue().getRight()).getRight();
			boolean mouseOver = entry.getValue().getLeft();
			if (!mouseOver) {
				continue;
			}
			int stringWidth = 200;
			List titleLines = fontRendererObj.listFormattedStringToWidth(desc, stringWidth);
			int offset = 10;
			int x = i + offset;
			int y = j + offset;
			func_146283_a(titleLines, x, y);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			i = Integer.signum(i);
			int j = displayedTitles.size() - 12;
			currentScroll -= (float) i / (float) j;
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
		}
	}

	@Override
	public void initGui() {
		setxSize(256);
		super.initGui();
		setGuiLeft((width - getxSize()) / 2 + 100);
		setGuiTop((height - getySize()) / 2 + 20);
		selectButton = new GOTGuiButton(0, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 90, 80, 20, StatCollector.translateToLocal("got.gui.titles.select"));
		buttonList.add(selectButton);
		removeButton = new GOTGuiButton(1, getGuiLeft() + getxSize() / 2 - 200, getGuiTop() + 90, 80, 20, StatCollector.translateToLocal("got.gui.titles.remove"));
		buttonList.add(removeButton);
		setGoBack(new GOTGuiButton(2, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 120, 170, 20, StatCollector.translateToLocal("got.gui.menuButton")));
		buttonList.add(getGoBack());
		updateScreen();
	}

	@Override
	public void mouseClicked(int i, int j, int mouse) {
		if (mouse == 0) {
			for (Map.Entry<GOTTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : displayedTitleInfo.entrySet()) {
				GOTTitle title = entry.getKey();
				boolean mouseOver = (Boolean) ((Pair) entry.getValue()).getLeft();
				if (mouseOver && title.canPlayerUse(mc.thePlayer)) {
					selectedTitle = title;
					selectedColor = EnumChatFormatting.WHITE;
					return;
				}
			}
			if (!displayedColorBoxes.isEmpty()) {
				for (Map.Entry<EnumChatFormatting, Pair<Integer, Integer>> entry : displayedColorBoxes.entrySet()) {
					EnumChatFormatting color = entry.getKey();
					int colorX = (Integer) ((Pair) entry.getValue()).getLeft();
					int colorY = (Integer) ((Pair) entry.getValue()).getRight();
					if (i >= colorX && i < colorX + colorBoxWidth && j >= colorY && j < colorY + colorBoxWidth) {
						selectedColor = color;
						break;
					}
				}
			}
		}
		super.mouseClicked(i, j, mouse);
	}

	public void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = getGuiLeft() + scrollBarX;
		int j1 = getGuiTop() + scrollBarY;
		int i2 = i1 + scrollBarWidth;
		int j2 = j1 + scrollBarHeight;
		if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
			isScrolling = true;
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (j - j1 - scrollWidgetHeight / 2.0f) / ((float) (j2 - j1) - (float) scrollWidgetHeight);
			currentScroll = MathHelper.clamp_float(currentScroll, 0.0f, 1.0f);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		currentTitle = GOTLevelData.getData(mc.thePlayer).getPlayerTitle();
		displayedTitles.clear();
		ArrayList<GOTTitle> availableTitles = new ArrayList<>();
		ArrayList<GOTTitle> unavailableTitles = new ArrayList<>();
		for (GOTTitle title : GOTTitle.getAllTitles()) {
			if (title.canPlayerUse(mc.thePlayer)) {
				availableTitles.add(title);
				continue;
			}
			if (!title.canDisplay(mc.thePlayer)) {
				continue;
			}
			unavailableTitles.add(title);
		}
		Comparator<GOTTitle> sorter = GOTTitle.createTitleSorter(mc.thePlayer);
		Collections.sort(availableTitles, sorter);
		Collections.sort(unavailableTitles, sorter);
		displayedTitles.addAll(availableTitles);
		displayedTitles.add(null);
		displayedTitles.addAll(unavailableTitles);
	}
}
