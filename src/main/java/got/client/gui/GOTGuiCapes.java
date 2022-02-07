package got.client.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import got.common.GOTLevelData;
import got.common.database.GOTCapes;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GOTGuiCapes extends GOTGuiMenuWBBase {
	private static ModelBiped playerModel = new ModelBiped();
	private static int currentCapeTypeID;
	private static int currentCapeID;
	private int modelX;
	private int modelY;
	private float modelRotation;
	private float modelRotationPrev;
	private int isMouseDown;
	private int mouseX;
	private int mouseY;
	private int prevMouseX;
	private GOTCapes.CapeType currentCapeType;
	private GOTCapes currentCape;
	private GuiButton capeLeft;
	private GuiButton capeRight;
	private GuiButton capeSelect;
	private GuiButton capeRemove;
	private GuiButton goToShield;

	private GuiButton changeCategory;

	public GOTGuiCapes() {
		modelRotationPrev = modelRotation = -140.0f;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == capeLeft) {
				updateCurrentCape(-1, 0);
			} else if (button.enabled && button == getGoBack()) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else if (button.enabled && button == goToShield) {
				mc.displayGuiScreen(new GOTGuiShields());
			} else if (button == capeSelect) {
				updateCurrentCape(0, 0);
				GOTPacketSelectCape packet = new GOTPacketSelectCape(currentCape);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == capeRight) {
				updateCurrentCape(1, 0);
			} else if (button == capeRemove) {
				updateCurrentCape(0, 0);
				GOTPacketSelectCape packet = new GOTPacketSelectCape(null);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == changeCategory) {
				updateCurrentCape(0, 1);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	private boolean canGoLeft() {
		for (int i = 0; i <= currentCapeID - 1; ++i) {
			GOTCapes cape = currentCapeType.getList().get(i);
			if (!cape.canDisplay(mc.thePlayer)) {
				continue;
			}
			return true;
		}
		return false;
	}

	private boolean canGoRight() {
		for (int i = currentCapeID + 1; i <= currentCapeType.getList().size() - 1; ++i) {
			GOTCapes cape = currentCapeType.getList().get(i);
			if (!cape.canDisplay(mc.thePlayer)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		mouseX = i;
		mouseY = j;
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		String s;
		GL11.glEnable(2903);
		RenderHelper.enableStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glEnable(32826);
		GL11.glEnable(3008);
		GL11.glTranslatef(modelX, modelY, 50.0f);
		float scale = 55.0f;
		GL11.glScalef(-scale, scale, scale);
		GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(90 + modelRotationPrev + (modelRotation - modelRotationPrev) * f, 0.0f, 1.0f, 0.0f);
		mc.getTextureManager().bindTexture(mc.thePlayer.getLocationSkin());
		playerModel.isChild = false;
		playerModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		mc.getTextureManager().bindTexture(currentCape.getCapeTexture());
		GL11.glTranslatef(0.0f, 0.0f, 0.125f);
		GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
		playerModel.renderCloak(0.0625f);
		GL11.glDisable(32826);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(3553);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int x = getGuiLeft() + getxSize() / 2;
		int y = getGuiTop() + 145;
		s = currentCape.getCapeName();
		this.drawCenteredString(s, x, y, 16777215);
		y += fontRendererObj.FONT_HEIGHT * 2;
		List desc = fontRendererObj.listFormattedStringToWidth(currentCape.getCapeDesc(), 220);
		for (Object element : desc) {
			s = (String) element;
			this.drawCenteredString(s, x, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
		}
		capeLeft.enabled = canGoLeft();
		capeSelect.enabled = currentCape.canPlayerWear(mc.thePlayer);
		capeSelect.displayString = getPlayerEquippedCape() == currentCape ? StatCollector.translateToLocal("got.gui.capes.selected") : StatCollector.translateToLocal("got.gui.capes.select");
		capeRight.enabled = canGoRight();
		capeRemove.enabled = getPlayerEquippedCape() != null && getPlayerEquippedCape() == currentCape;
		changeCategory.displayString = currentCapeType.getDisplayName();
		super.drawScreen(i, j, f);
	}

	private GOTCapes getPlayerEquippedCape() {
		return GOTLevelData.getData(mc.thePlayer).getCape();
	}

	@Override
	public void initGui() {
		super.initGui();
		setGuiLeft((width - getxSize()) / 2 + 100);
		setGuiTop((height - getySize()) / 2);
		modelX = getGuiLeft() + getxSize() / 2;
		modelY = getGuiTop() + 40;
		capeLeft = new GOTGuiButtonArrows(0, true, getGuiLeft() + getxSize() / 2 - 64, getGuiTop() + 207);
		buttonList.add(capeLeft);
		capeSelect = new GOTGuiButton(1, getGuiLeft() + getxSize() / 2 - 40, getGuiTop() + 195, 80, 20, StatCollector.translateToLocal("got.gui.capes.select"));
		buttonList.add(capeSelect);
		capeRight = new GOTGuiButtonArrows(2, false, getGuiLeft() + getxSize() / 2 + 44, getGuiTop() + 207);
		buttonList.add(capeRight);
		capeRemove = new GOTGuiButton(3, getGuiLeft() + getxSize() / 2 - 40, getGuiTop() + 219, 80, 20, StatCollector.translateToLocal("got.gui.capes.remove"));
		buttonList.add(capeRemove);
		changeCategory = new GOTGuiButton(4, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 90, 160, 20, "");
		buttonList.add(changeCategory);
		setGoBack(new GOTGuiButton(5, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 150, 160, 20, StatCollector.translateToLocal("got.gui.menuButton")));
		buttonList.add(getGoBack());
		goToShield = new GOTGuiButton(6, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 120, 160, 20, StatCollector.translateToLocal("got.gui.shields"));
		buttonList.add(goToShield);

		GOTCapes equippedCape = getPlayerEquippedCape();
		if (equippedCape != null) {
			currentCapeTypeID = equippedCape.getCapeType().ordinal();
			currentCapeID = equippedCape.getCapeID();
		}
		updateCurrentCape(0, 0);
	}

	public void updateCurrentCape(int cape, int type) {
		if (cape != 0) {
			currentCapeID += cape;
			currentCapeID = Math.max(currentCapeID, 0);
			currentCapeID = Math.min(currentCapeID, currentCapeType.getList().size() - 1);
		}
		if (type != 0) {
			currentCapeTypeID += type;
			if (currentCapeTypeID > GOTCapes.CapeType.values().length - 1) {
				currentCapeTypeID = 0;
			}
			if (currentCapeTypeID < 0) {
				currentCapeTypeID = GOTCapes.CapeType.values().length - 1;
			}
			currentCapeID = 0;
		}
		currentCapeType = GOTCapes.CapeType.values()[currentCapeTypeID];
		currentCape = currentCapeType.getList().get(currentCapeID);
		while (!currentCape.canDisplay(mc.thePlayer)) {
			if ((cape < 0 || type != 0) && canGoLeft()) {
				updateCurrentCape(-1, 0);
				continue;
			}
			if ((cape > 0 || type != 0) && canGoRight()) {
				updateCurrentCape(1, 0);
				continue;
			}
			updateCurrentCape(0, 1);
		}
	}

	@Override
	public void updateScreen() {
		boolean mouseWithinModel;
		super.updateScreen();
		modelRotationPrev = modelRotation;
		modelRotationPrev = MathHelper.wrapAngleTo180_float(modelRotationPrev);
		modelRotation = MathHelper.wrapAngleTo180_float(modelRotation);
		mouseWithinModel = Math.abs(mouseX - modelX) <= 60 && Math.abs(mouseY - modelY) <= 80;
		if (Mouse.isButtonDown(0)) {
			if (isMouseDown == 0 || isMouseDown == 1) {
				if (isMouseDown == 0) {
					if (mouseWithinModel) {
						isMouseDown = 1;
					}
				} else if (mouseX != prevMouseX) {
					float move = -(mouseX - prevMouseX) * 1.0f;
					modelRotation += move;
				}
				prevMouseX = mouseX;
			}
		} else {
			isMouseDown = 0;
		}
	}
}