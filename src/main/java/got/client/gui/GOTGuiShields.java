package got.client.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import got.client.render.other.GOTRenderShield;
import got.common.GOTLevelData;
import got.common.database.GOTShields;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GOTGuiShields extends GOTGuiMenuWBBase {
	private static ModelBiped playerModel = new ModelBiped();
	private static int currentShieldTypeID;
	private static int currentShieldID;
	private int modelX;
	private int modelY;
	private float modelRotation = -140.0f;
	private float modelRotationPrev = -140.0f;
	private int isMouseDown;
	private int mouseX;
	private int mouseY;
	private int prevMouseX;
	private GOTShields.ShieldType currentShieldType;
	private GOTShields currentShield;
	private GuiButton shieldLeft;
	private GuiButton shieldRight;
	private GuiButton shieldSelect;
	private GuiButton shieldRemove;
	private GuiButton goToCape;

	private GuiButton changeCategory;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == shieldLeft) {
				updateCurrentShield(-1, 0);
			} else if (button.enabled && button == getGoBack()) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else if (button.enabled && button == goToCape) {
				mc.displayGuiScreen(new GOTGuiCapes());
			} else if (button == shieldSelect) {
				updateCurrentShield(0, 0);
				GOTPacketSelectShield packet = new GOTPacketSelectShield(currentShield);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == shieldRight) {
				updateCurrentShield(1, 0);
			} else if (button == shieldRemove) {
				updateCurrentShield(0, 0);
				GOTPacketSelectShield packet = new GOTPacketSelectShield(null);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == changeCategory) {
				updateCurrentShield(0, 1);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	private boolean canGoLeft() {
		for (int i = 0; i <= currentShieldID - 1; ++i) {
			GOTShields shield = currentShieldType.list.get(i);
			if (!shield.canDisplay(mc.thePlayer)) {
				continue;
			}
			return true;
		}
		return false;
	}

	private boolean canGoRight() {
		for (int i = currentShieldID + 1; i <= currentShieldType.list.size() - 1; ++i) {
			GOTShields shield = currentShieldType.list.get(i);
			if (!shield.canDisplay(mc.thePlayer)) {
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
		GL11.glRotatef(modelRotationPrev + (modelRotation - modelRotationPrev) * f, 0.0f, 1.0f, 0.0f);
		mc.getTextureManager().bindTexture(mc.thePlayer.getLocationSkin());
		playerModel.isChild = false;
		playerModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GOTRenderShield.renderShield(currentShield, null, playerModel);
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
		s = currentShield.getShieldName();
		this.drawCenteredString(s, x, y, 16777215);
		y += fontRendererObj.FONT_HEIGHT * 2;
		List desc = fontRendererObj.listFormattedStringToWidth(currentShield.getShieldDesc(), 220);
		for (Object element : desc) {
			s = (String) element;
			this.drawCenteredString(s, x, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
		}
		shieldLeft.enabled = canGoLeft();
		shieldSelect.enabled = currentShield.canPlayerWear(mc.thePlayer);
		shieldSelect.displayString = getPlayerEquippedShield() == currentShield ? StatCollector.translateToLocal("got.gui.shields.selected") : StatCollector.translateToLocal("got.gui.shields.select");
		shieldRight.enabled = canGoRight();
		shieldRemove.enabled = getPlayerEquippedShield() != null && getPlayerEquippedShield() == currentShield;
		changeCategory.displayString = currentShieldType.getDisplayName();
		super.drawScreen(i, j, f);
	}

	private GOTShields getPlayerEquippedShield() {
		return GOTLevelData.getData(mc.thePlayer).getShield();
	}

	@Override
	public void initGui() {
		super.initGui();
		setGuiLeft((width - getxSize()) / 2 + 100);
		setGuiTop((height - getySize()) / 2);
		modelX = getGuiLeft() + getxSize() / 2;
		modelY = getGuiTop() + 40;
		shieldLeft = new GOTGuiButtonArrows(0, true, getGuiLeft() + getxSize() / 2 - 64, getGuiTop() + 207);
		buttonList.add(shieldLeft);
		shieldSelect = new GOTGuiButton(1, getGuiLeft() + getxSize() / 2 - 40, getGuiTop() + 195, 80, 20, StatCollector.translateToLocal("got.gui.shields.select"));
		buttonList.add(shieldSelect);
		shieldRight = new GOTGuiButtonArrows(2, false, getGuiLeft() + getxSize() / 2 + 44, getGuiTop() + 207);
		buttonList.add(shieldRight);
		shieldRemove = new GOTGuiButton(3, getGuiLeft() + getxSize() / 2 - 40, getGuiTop() + 219, 80, 20, StatCollector.translateToLocal("got.gui.shields.remove"));
		buttonList.add(shieldRemove);
		changeCategory = new GOTGuiButton(4, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 90, 160, 20, "");
		buttonList.add(changeCategory);
		setGoBack(new GOTGuiButton(5, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 150, 160, 20, StatCollector.translateToLocal("got.gui.menuButton")));
		buttonList.add(getGoBack());
		goToCape = new GOTGuiButton(6, getGuiLeft() + getxSize() / 2 - 290, getGuiTop() + 120, 160, 20, StatCollector.translateToLocal("got.gui.capes"));
		buttonList.add(goToCape);
		GOTShields equippedShield = getPlayerEquippedShield();
		if (equippedShield != null) {
			currentShieldTypeID = equippedShield.shieldType.ordinal();
			currentShieldID = equippedShield.shieldID;
		}
		updateCurrentShield(0, 0);
	}

	public void updateCurrentShield(int shield, int type) {
		if (shield != 0) {
			currentShieldID += shield;
			currentShieldID = Math.max(currentShieldID, 0);
			currentShieldID = Math.min(currentShieldID, currentShieldType.list.size() - 1);
		}
		if (type != 0) {
			if ((currentShieldTypeID += type) > GOTShields.ShieldType.values().length - 1) {
				currentShieldTypeID = 0;
			}
			if (currentShieldTypeID < 0) {
				currentShieldTypeID = GOTShields.ShieldType.values().length - 1;
			}
			currentShieldID = 0;
		}
		currentShieldType = GOTShields.ShieldType.values()[currentShieldTypeID];
		currentShield = currentShieldType.list.get(currentShieldID);
		while (!currentShield.canDisplay(mc.thePlayer)) {
			if ((shield < 0 || type != 0) && canGoLeft()) {
				updateCurrentShield(-1, 0);
				continue;
			}
			if ((shield > 0 || type != 0) && canGoRight()) {
				updateCurrentShield(1, 0);
				continue;
			}
			updateCurrentShield(0, 1);
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		modelRotationPrev = modelRotation;
		modelRotationPrev = MathHelper.wrapAngleTo180_float(modelRotationPrev);
		modelRotation = MathHelper.wrapAngleTo180_float(modelRotation);
		boolean mouseWithinModel = Math.abs(mouseX - modelX) <= 60 && Math.abs(mouseY - modelY) <= 80;
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
