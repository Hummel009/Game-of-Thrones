package got.client.gui;

import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.other.GOTEntityRegistry;
import got.common.network.GOTPacketEditNPCRespawner;
import got.common.network.GOTPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

public class GOTGuiNPCRespawner extends GOTGuiScreenBase {
	private static final int X_SIZE = 256;

	private final GOTEntityNPCRespawner theSpawner;

	private GOTGuiButtonOptions buttonMounts;

	private GuiTextField textSpawnClass1;
	private GuiTextField textSpawnClass2;

	private GOTGuiSlider sliderCheckHorizontal;
	private GOTGuiSlider sliderCheckVerticalMin;
	private GOTGuiSlider sliderCheckVerticalMax;
	private GOTGuiSlider sliderSpawnCap;
	private GOTGuiSlider sliderBlockEnemy;
	private GOTGuiSlider sliderSpawnHorizontal;
	private GOTGuiSlider sliderSpawnVerticalMin;
	private GOTGuiSlider sliderSpawnVerticalMax;
	private GOTGuiSlider sliderHomeRange;
	private GOTGuiSlider sliderSpawnIntervalM;
	private GOTGuiSlider sliderSpawnIntervalS;
	private GOTGuiSlider sliderNoPlayerRange;

	private GuiButton buttonDestroy;

	private boolean destroySpawner;
	private int guiLeft;
	private int guiTop;

	public GOTGuiNPCRespawner(GOTEntityNPCRespawner entity) {
		theSpawner = entity;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button instanceof GOTGuiSlider) {
			return;
		}
		if (button.enabled) {
			if (button == buttonMounts) {
				theSpawner.toggleMountSetting();
			}
			if (button == buttonDestroy) {
				destroySpawner = true;
				mc.thePlayer.closeScreen();
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		int i1 = i;
		int j1 = j;
		drawDefaultBackground();
		String s = StatCollector.translateToLocal("got.gui.npcRespawner.title");
		fontRendererObj.drawString(s, guiLeft + X_SIZE / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop, 16777215);
		textSpawnClass1.drawTextBox();
		textSpawnClass2.drawTextBox();
		s = StatCollector.translateToLocal("got.gui.npcRespawner.spawnClass1");
		fontRendererObj.drawString(s, textSpawnClass1.xPosition + 3, textSpawnClass1.yPosition - fontRendererObj.FONT_HEIGHT - 3, 13421772);
		s = StatCollector.translateToLocal("got.gui.npcRespawner.spawnClass2");
		fontRendererObj.drawString(s, textSpawnClass2.xPosition + 3, textSpawnClass2.yPosition - fontRendererObj.FONT_HEIGHT - 3, 13421772);
		if (theSpawner.mountSetting == 0) {
			buttonMounts.setState(StatCollector.translateToLocal("got.gui.npcRespawner.mounts.0"));
		} else if (theSpawner.mountSetting == 1) {
			buttonMounts.setState(StatCollector.translateToLocal("got.gui.npcRespawner.mounts.1"));
		} else {
			buttonMounts.setState(StatCollector.translateToLocal("got.gui.npcRespawner.mounts.2"));
		}
		if (theSpawner.blockEnemySpawns()) {
			sliderBlockEnemy.setOverrideStateString(null);
		} else {
			sliderBlockEnemy.setOverrideStateString(StatCollector.translateToLocal("got.gui.npcRespawner.blockEnemy.off"));
		}
		if (theSpawner.hasHomeRange()) {
			sliderHomeRange.setOverrideStateString(null);
		} else {
			sliderHomeRange.setOverrideStateString(StatCollector.translateToLocal("got.gui.npcRespawner.homeRange.off"));
		}
		String timepre = StatCollector.translateToLocal("got.gui.npcRespawner.spawnInterval");
		int timepreX = sliderSpawnIntervalM.xPosition - 5 - fontRendererObj.getStringWidth(timepre);
		int timepreY = sliderSpawnIntervalM.yPosition + sliderSpawnIntervalM.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
		fontRendererObj.drawString(timepre, timepreX, timepreY, 16777215);
		String timesplit = ":";
		int timesplitX = (sliderSpawnIntervalM.xPosition + sliderSpawnIntervalM.width + sliderSpawnIntervalS.xPosition) / 2 - fontRendererObj.getStringWidth(timesplit) / 2;
		int timesplitY = sliderSpawnIntervalM.yPosition + sliderSpawnIntervalM.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
		fontRendererObj.drawString(timesplit, timesplitX, timesplitY, 16777215);
		super.drawScreen(i1, j1, f);
		updateSliders();
		if (sliderBlockEnemy.enabled && sliderBlockEnemy.func_146115_a() && !sliderBlockEnemy.isDragging()) {
			String tooltip = StatCollector.translateToLocal("got.gui.npcRespawner.blockEnemy.tooltip");
			int border = 3;
			int stringWidth = mc.fontRenderer.getStringWidth(tooltip);
			int stringHeight = mc.fontRenderer.FONT_HEIGHT;
			int offset = 10;
			drawRect(i1 += offset, j1 += offset, i1 + stringWidth + border * 2, j1 + stringHeight + border * 2, -1073741824);
			mc.fontRenderer.drawString(tooltip, i1 + border, j1 + border, 16777215);
		}
	}

	@Override
	public void initGui() {
		guiLeft = (width - X_SIZE) / 2;
		int ySize = 280;
		guiTop = (height - ySize) / 2;
		textSpawnClass1 = new GuiTextField(fontRendererObj, guiLeft + X_SIZE / 2 - 190, guiTop + 35, 180, 20);
		if (theSpawner.spawnClass1 != null) {
			textSpawnClass1.setText(GOTEntityRegistry.getStringFromClass(theSpawner.spawnClass1));
		}
		textSpawnClass2 = new GuiTextField(fontRendererObj, guiLeft + X_SIZE / 2 + 10, guiTop + 35, 180, 20);
		if (theSpawner.spawnClass2 != null) {
			textSpawnClass2.setText(GOTEntityRegistry.getStringFromClass(theSpawner.spawnClass2));
		}
		sliderCheckHorizontal = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 - 180, guiTop + 70, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.checkHorizontal"));
		buttonList.add(sliderCheckHorizontal);
		sliderCheckHorizontal.setMinMaxValues(0, 64);
		sliderCheckHorizontal.setSliderValue(theSpawner.checkHorizontalRange);
		sliderCheckVerticalMin = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 - 180, guiTop + 95, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.checkVerticalMin"));
		buttonList.add(sliderCheckVerticalMin);
		sliderCheckVerticalMin.setMinMaxValues(-64, 64);
		sliderCheckVerticalMin.setSliderValue(theSpawner.checkVerticalMin);
		sliderCheckVerticalMax = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 - 180, guiTop + 120, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.checkVerticalMax"));
		buttonList.add(sliderCheckVerticalMax);
		sliderCheckVerticalMax.setMinMaxValues(-64, 64);
		sliderCheckVerticalMax.setSliderValue(theSpawner.checkVerticalMax);
		sliderSpawnCap = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 - 180, guiTop + 145, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.spawnCap"));
		buttonList.add(sliderSpawnCap);
		sliderSpawnCap.setMinMaxValues(0, 64);
		sliderSpawnCap.setSliderValue(theSpawner.spawnCap);
		sliderBlockEnemy = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 - 180, guiTop + 170, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.blockEnemy"));
		buttonList.add(sliderBlockEnemy);
		sliderBlockEnemy.setMinMaxValues(0, 64);
		sliderBlockEnemy.setSliderValue(theSpawner.blockEnemySpawns);
		sliderSpawnHorizontal = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 + 20, guiTop + 70, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.spawnHorizontal"));
		buttonList.add(sliderSpawnHorizontal);
		sliderSpawnHorizontal.setMinMaxValues(0, 64);
		sliderSpawnHorizontal.setSliderValue(theSpawner.spawnHorizontalRange);
		sliderSpawnVerticalMin = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 + 20, guiTop + 95, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.spawnVerticalMin"));
		buttonList.add(sliderSpawnVerticalMin);
		sliderSpawnVerticalMin.setMinMaxValues(-64, 64);
		sliderSpawnVerticalMin.setSliderValue(theSpawner.spawnVerticalMin);
		sliderSpawnVerticalMax = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 + 20, guiTop + 120, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.spawnVerticalMax"));
		buttonList.add(sliderSpawnVerticalMax);
		sliderSpawnVerticalMax.setMinMaxValues(-64, 64);
		sliderSpawnVerticalMax.setSliderValue(theSpawner.spawnVerticalMax);
		sliderHomeRange = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 + 20, guiTop + 145, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.homeRange"));
		buttonList.add(sliderHomeRange);
		sliderHomeRange.setMinMaxValues(-1, 64);
		sliderHomeRange.setSliderValue(theSpawner.homeRange);
		buttonMounts = new GOTGuiButtonOptions(0, guiLeft + X_SIZE / 2 + 20, guiTop + 170, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.mounts"));
		buttonList.add(buttonMounts);
		sliderSpawnIntervalM = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 - 100 - 5, guiTop + 195, 100, 20, StatCollector.translateToLocal("got.gui.npcRespawner.spawnIntervalM"));
		buttonList.add(sliderSpawnIntervalM);
		sliderSpawnIntervalM.setMinMaxValues(0, 60);
		sliderSpawnIntervalM.setValueOnly();
		sliderSpawnIntervalM.setSliderValue(theSpawner.spawnInterval / 20 / 60);
		sliderSpawnIntervalS = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 + 5, guiTop + 195, 100, 20, StatCollector.translateToLocal("got.gui.npcRespawner.spawnIntervalS"));
		buttonList.add(sliderSpawnIntervalS);
		sliderSpawnIntervalS.setMinMaxValues(0, 59);
		sliderSpawnIntervalS.setValueOnly();
		sliderSpawnIntervalS.setNumberDigits(2);
		sliderSpawnIntervalS.setSliderValue(theSpawner.spawnInterval / 20 % 60);
		sliderNoPlayerRange = new GOTGuiSlider(0, guiLeft + X_SIZE / 2 - 80, guiTop + 220, 160, 20, StatCollector.translateToLocal("got.gui.npcRespawner.noPlayerRange"));
		buttonList.add(sliderNoPlayerRange);
		sliderNoPlayerRange.setMinMaxValues(0, 64);
		sliderNoPlayerRange.setSliderValue(theSpawner.noPlayerRange);
		buttonDestroy = new GOTGuiButton(0, guiLeft + X_SIZE / 2 - 50, guiTop + 255, 100, 20, StatCollector.translateToLocal("got.gui.npcRespawner.destroy"));
		buttonList.add(buttonDestroy);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (textSpawnClass1.getVisible() && textSpawnClass1.textboxKeyTyped(c, i) || textSpawnClass2.getVisible() && textSpawnClass2.textboxKeyTyped(c, i)) {
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		textSpawnClass1.mouseClicked(i, j, k);
		textSpawnClass2.mouseClicked(i, j, k);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		sendSpawnerData();
	}

	private void sendSpawnerData() {
		String s1 = textSpawnClass1.getText();
		String s2 = textSpawnClass2.getText();
		if (!StringUtils.isNullOrEmpty(s1)) {
			theSpawner.spawnClass1 = GOTEntityRegistry.getClassFromString(s1);
		}
		if (!StringUtils.isNullOrEmpty(s2)) {
			theSpawner.spawnClass2 = GOTEntityRegistry.getClassFromString(s2);
		}
		GOTPacketEditNPCRespawner packet = new GOTPacketEditNPCRespawner(theSpawner);
		packet.setDestroy(destroySpawner);
		GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		textSpawnClass1.updateCursorCounter();
		textSpawnClass2.updateCursorCounter();
	}

	private void updateSliders() {
		if (sliderCheckHorizontal.isDragging()) {
			theSpawner.checkHorizontalRange = sliderCheckHorizontal.getSliderValue();
		}
		if (sliderCheckVerticalMin.isDragging()) {
			theSpawner.checkVerticalMin = sliderCheckVerticalMin.getSliderValue();
			if (theSpawner.checkVerticalMax < theSpawner.checkVerticalMin) {
				theSpawner.checkVerticalMax = theSpawner.checkVerticalMin;
				sliderCheckVerticalMax.setSliderValue(theSpawner.checkVerticalMax);
			}
		}
		if (sliderCheckVerticalMax.isDragging()) {
			theSpawner.checkVerticalMax = sliderCheckVerticalMax.getSliderValue();
			if (theSpawner.checkVerticalMin > theSpawner.checkVerticalMax) {
				theSpawner.checkVerticalMin = theSpawner.checkVerticalMax;
				sliderCheckVerticalMin.setSliderValue(theSpawner.checkVerticalMin);
			}
		}
		if (sliderSpawnCap.isDragging()) {
			theSpawner.spawnCap = sliderSpawnCap.getSliderValue();
		}
		if (sliderBlockEnemy.isDragging()) {
			theSpawner.blockEnemySpawns = sliderBlockEnemy.getSliderValue();
		}
		if (sliderSpawnHorizontal.isDragging()) {
			theSpawner.spawnHorizontalRange = sliderSpawnHorizontal.getSliderValue();
		}
		if (sliderSpawnVerticalMin.isDragging()) {
			theSpawner.spawnVerticalMin = sliderSpawnVerticalMin.getSliderValue();
			if (theSpawner.spawnVerticalMax < theSpawner.spawnVerticalMin) {
				theSpawner.spawnVerticalMax = theSpawner.spawnVerticalMin;
				sliderSpawnVerticalMax.setSliderValue(theSpawner.spawnVerticalMax);
			}
		}
		if (sliderSpawnVerticalMax.isDragging()) {
			theSpawner.spawnVerticalMax = sliderSpawnVerticalMax.getSliderValue();
			if (theSpawner.spawnVerticalMin > theSpawner.spawnVerticalMax) {
				theSpawner.spawnVerticalMin = theSpawner.spawnVerticalMax;
				sliderSpawnVerticalMin.setSliderValue(theSpawner.spawnVerticalMin);
			}
		}
		if (sliderHomeRange.isDragging()) {
			theSpawner.homeRange = sliderHomeRange.getSliderValue();
		}
		if (sliderSpawnIntervalM.isDragging() || sliderSpawnIntervalS.isDragging()) {
			if (sliderSpawnIntervalM.getSliderValue() == 0) {
				int s = sliderSpawnIntervalS.getSliderValue();
				s = Math.max(s, 1);
				sliderSpawnIntervalS.setSliderValue(s);
			}
			theSpawner.spawnInterval = (sliderSpawnIntervalM.getSliderValue() * 60 + sliderSpawnIntervalS.getSliderValue()) * 20;
		}
		if (sliderNoPlayerRange.isDragging()) {
			theSpawner.noPlayerRange = sliderNoPlayerRange.getSliderValue();
		}
	}
}