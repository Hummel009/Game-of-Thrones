package got.client.gui;

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
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GOTGuiShields extends GOTGuiMenuBase {
	private static final ModelBiped PLAYER_MODEL = new ModelBiped();

	private static int currentShieldTypeID;
	private static int currentShieldID;

	static {
		PLAYER_MODEL.isChild = false;
	}

	private GOTShields.ShieldType currentShieldType;
	private GOTShields currentShield;

	private GuiButton shieldLeft;
	private GuiButton shieldRight;
	private GuiButton shieldSelect;
	private GuiButton shieldRemove;
	private GuiButton goToCape;
	private GuiButton changeCategory;

	private float modelRotation = -140.0f;
	private float modelRotationPrev = -140.0f;

	private int prevMouseX;
	private int isMouseDown;
	private int modelX;
	private int modelY;
	private int mouseX;
	private int mouseY;

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
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
			} else if (button == shieldRight) {
				updateCurrentShield(1, 0);
			} else if (button == shieldRemove) {
				updateCurrentShield(0, 0);
				IMessage packet = new GOTPacketSelectShield(null);
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
			} else if (button == changeCategory) {
				updateCurrentShield(0, 1);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	private boolean canGoLeft() {
		for (int i = 0; i <= currentShieldID - 1; ++i) {
			GOTShields shield = currentShieldType.getShields().get(i);
			if (shield.canDisplay(mc.thePlayer)) {
				return true;
			}
		}
		return false;
	}

	private boolean canGoRight() {
		for (int i = currentShieldID + 1; i <= currentShieldType.getShields().size() - 1; ++i) {
			GOTShields shield = currentShieldType.getShields().get(i);
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
		PLAYER_MODEL.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		GOTRenderShield.renderShield(currentShield, null, PLAYER_MODEL);
		GL11.glDisable(32826);
		GL11.glEnable(2884);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(3553);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int x = guiLeft + sizeX / 2;
		int y = guiTop + 145;
		String s = currentShield.getShieldName();
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

	private GOTShields getPlayerEquippedShield() {
		return GOTLevelData.getData(mc.thePlayer).getShield();
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - sizeX) / 2 + 100;
		guiTop = (height - sizeY) / 2;
		modelX = guiLeft + sizeX / 2;
		modelY = guiTop + 40;
		shieldLeft = new GOTGuiButtonArrows(0, true, guiLeft + sizeX / 2 - 64, guiTop + 207);
		buttonList.add(shieldLeft);
		shieldSelect = new GOTGuiButton(1, guiLeft + sizeX / 2 - 40, guiTop + 195, 80, 20, StatCollector.translateToLocal("got.gui.shields.select"));
		buttonList.add(shieldSelect);
		shieldRight = new GOTGuiButtonArrows(2, false, guiLeft + sizeX / 2 + 44, guiTop + 207);
		buttonList.add(shieldRight);
		shieldRemove = new GOTGuiButton(3, guiLeft + sizeX / 2 - 40, guiTop + 219, 80, 20, StatCollector.translateToLocal("got.gui.shields.remove"));
		buttonList.add(shieldRemove);
		changeCategory = new GOTGuiButton(4, guiLeft + sizeX / 2 - 290, guiTop + 90, 160, 20, "");
		buttonList.add(changeCategory);
		goBack = new GOTGuiButton(5, guiLeft + sizeX / 2 - 290, guiTop + 150, 160, 20, StatCollector.translateToLocal("got.gui.menuButton"));
		buttonList.add(goBack);
		goToCape = new GOTGuiButton(6, guiLeft + sizeX / 2 - 290, guiTop + 120, 160, 20, StatCollector.translateToLocal("got.gui.capes"));
		buttonList.add(goToCape);
		GOTShields equippedShield = getPlayerEquippedShield();
		if (equippedShield != null) {
			currentShieldTypeID = equippedShield.getShieldType().ordinal();
			currentShieldID = equippedShield.getShieldID();
		}
		updateCurrentShield(0, 0);
	}

	private void updateCurrentShield(int shield, int type) {
		if (shield != 0) {
			currentShieldID += shield;
			currentShieldID = Math.max(currentShieldID, 0);
			currentShieldID = Math.min(currentShieldID, currentShieldType.getShields().size() - 1);
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
		currentShield = currentShieldType.getShields().get(currentShieldID);
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