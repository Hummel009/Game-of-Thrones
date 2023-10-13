package got.client.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.client.render.other.GOTRenderShield;
import got.common.GOTLevelData;
import got.common.database.GOTShields;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketSelectShield;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class GOTGuiShields extends GOTGuiMenuWBBase {
	public static ModelBiped playerModel = new ModelBiped();
	public static int currentShieldTypeID;
	public static int currentShieldID;

	static {
		playerModel.isChild = false;
	}

	public int modelX;
	public int modelY;
	public float modelRotation = -140.0f;
	public float modelRotationPrev = -140.0f;
	public int isMouseDown;
	public int mouseX;
	public int mouseY;
	public int prevMouseX;
	public GOTShields.ShieldType currentShieldType;
	public GOTShields currentShield;
	public GuiButton shieldLeft;
	public GuiButton shieldRight;
	public GuiButton shieldSelect;
	public GuiButton shieldRemove;
	public GuiButton goToCape;

	public GuiButton changeCategory;

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == shieldLeft) {
				updateCurrentShield(-1, 0);
			} else if (button == goBack) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else if (button == goToCape) {
				mc.displayGuiScreen(new GOTGuiCapes());
			} else if (button == shieldSelect) {
				updateCurrentShield(0, 0);
				IMessage packet = new GOTPacketSelectShield(currentShield);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == shieldRight) {
				updateCurrentShield(1, 0);
			} else if (button == shieldRemove) {
				updateCurrentShield(0, 0);
				IMessage packet = new GOTPacketSelectShield(null);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == changeCategory) {
				updateCurrentShield(0, 1);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	public boolean canGoLeft() {
		for (int i = 0; i <= currentShieldID - 1; ++i) {
			GOTShields shield = currentShieldType.list.get(i);
			if (shield.canDisplay(mc.thePlayer)) {
				return true;
			}
		}
		return false;
	}

	public boolean canGoRight() {
		for (int i = currentShieldID + 1; i <= currentShieldType.list.size() - 1; ++i) {
			GOTShields shield = currentShieldType.list.get(i);
			if (shield.canDisplay(mc.thePlayer)) {
				return true;
			}
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
		int x = guiLeft + xSize / 2;
		int y = guiTop + 145;
		s = currentShield.getShieldName();
		drawCenteredString(s, x, y, 16777215);
		y += fontRendererObj.FONT_HEIGHT * 2;
		List<String> desc = fontRendererObj.listFormattedStringToWidth(currentShield.getShieldDesc(), 220);
		for (String element : desc) {
			s = element;
			drawCenteredString(s, x, y, 16777215);
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

	public GOTShields getPlayerEquippedShield() {
		return GOTLevelData.getData(mc.thePlayer).getShield();
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - xSize) / 2 + 100;
		guiTop = (height - ySize) / 2;
		modelX = guiLeft + xSize / 2;
		modelY = guiTop + 40;
		shieldLeft = new GOTGuiButtonArrows(0, true, guiLeft + xSize / 2 - 64, guiTop + 207);
		buttonList.add(shieldLeft);
		shieldSelect = new GOTGuiButton(1, guiLeft + xSize / 2 - 40, guiTop + 195, 80, 20, StatCollector.translateToLocal("got.gui.shields.select"));
		buttonList.add(shieldSelect);
		shieldRight = new GOTGuiButtonArrows(2, false, guiLeft + xSize / 2 + 44, guiTop + 207);
		buttonList.add(shieldRight);
		shieldRemove = new GOTGuiButton(3, guiLeft + xSize / 2 - 40, guiTop + 219, 80, 20, StatCollector.translateToLocal("got.gui.shields.remove"));
		buttonList.add(shieldRemove);
		changeCategory = new GOTGuiButton(4, guiLeft + xSize / 2 - 290, guiTop + 90, 160, 20, "");
		buttonList.add(changeCategory);
		goBack = new GOTGuiButton(5, guiLeft + xSize / 2 - 290, guiTop + 150, 160, 20, StatCollector.translateToLocal("got.gui.menuButton"));
		buttonList.add(goBack);
		goToCape = new GOTGuiButton(6, guiLeft + xSize / 2 - 290, guiTop + 120, 160, 20, StatCollector.translateToLocal("got.gui.capes"));
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
			currentShieldTypeID += type;
			if (currentShieldTypeID > GOTShields.ShieldType.values().length - 1) {
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
			} else if ((shield > 0 || type != 0) && canGoRight()) {
				updateCurrentShield(1, 0);
			} else {
				updateCurrentShield(0, 1);
			}
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
