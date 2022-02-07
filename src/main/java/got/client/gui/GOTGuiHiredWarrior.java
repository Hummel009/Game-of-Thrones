package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.GOTSquadrons;
import got.common.entity.other.*;
import got.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class GOTGuiHiredWarrior extends GOTGuiHiredNPC {
	private static String[] pageTitles = { "overview", "options" };
	private GuiButton buttonLeft;
	private GuiButton buttonRight;
	private GOTGuiButtonOptions buttonOpenInv;
	private GOTGuiButtonOptions buttonTeleport;
	private GOTGuiButtonOptions buttonGuardMode;
	private GOTGuiSlider sliderGuardRange;
	private GuiTextField squadronNameField;
	private boolean updatePage;
	private boolean sendSquadronUpdate = false;

	public GOTGuiHiredWarrior(GOTEntityNPC npc) {
		super(npc);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button instanceof GOTGuiSlider) {
			return;
		}
		if (button.enabled) {
			if (button instanceof GOTGuiButtonLeftRight) {
				if (button == buttonLeft) {
					setPage(getPage() - 1);
					if (getPage() < 0) {
						setPage(pageTitles.length - 1);
					}
				} else if (button == buttonRight) {
					setPage(getPage() + 1);
					if (getPage() >= pageTitles.length) {
						setPage(0);
					}
				}
				buttonList.clear();
				updatePage = true;
			} else {
				this.sendActionPacket(button.id);
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		if (getPage() == 0) {
			int midX = getGuiLeft() + getxSize() / 2;
			String s = StatCollector.translateToLocalFormatted("got.gui.warrior.health", Math.round(theNPC.getHealth()), Math.round(theNPC.getMaxHealth()));
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, getGuiTop() + 50, 4210752);
			s = theNPC.hiredNPCInfo.getStatusString();
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, getGuiTop() + 62, 4210752);
			s = StatCollector.translateToLocalFormatted("got.gui.warrior.level", theNPC.hiredNPCInfo.xpLevel);
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, getGuiTop() + 80, 4210752);
			float lvlProgress = theNPC.hiredNPCInfo.getProgressToNextLevel();
			String curLevel = EnumChatFormatting.BOLD + String.valueOf(theNPC.hiredNPCInfo.xpLevel);
			String nextLevel = EnumChatFormatting.BOLD + String.valueOf(theNPC.hiredNPCInfo.xpLevel + 1);
			String xpCurLevel = String.valueOf(theNPC.hiredNPCInfo.totalXPForLevel(theNPC.hiredNPCInfo.xpLevel));
			String xpNextLevel = String.valueOf(theNPC.hiredNPCInfo.totalXPForLevel(theNPC.hiredNPCInfo.xpLevel + 1));
			Gui.drawRect(midX - 36, getGuiTop() + 96, midX + 36, getGuiTop() + 102, -16777216);
			Gui.drawRect(midX - 35, getGuiTop() + 97, midX + 35, getGuiTop() + 101, -10658467);
			Gui.drawRect(midX - 35, getGuiTop() + 97, midX - 35 + (int) (lvlProgress * 70.0f), getGuiTop() + 101, -43776);
			GL11.glPushMatrix();
			float scale = 0.67f;
			GL11.glScalef(scale, scale, 1.0f);
			fontRendererObj.drawString(curLevel, Math.round((midX - 38 - fontRendererObj.getStringWidth(curLevel) * scale) / scale), (int) ((getGuiTop() + 94) / scale), 4210752);
			fontRendererObj.drawString(nextLevel, Math.round((midX + 38) / scale), (int) ((getGuiTop() + 94) / scale), 4210752);
			fontRendererObj.drawString(xpCurLevel, Math.round((midX - 38 - fontRendererObj.getStringWidth(xpCurLevel) * scale) / scale), (int) ((getGuiTop() + 101) / scale), 4210752);
			fontRendererObj.drawString(xpNextLevel, Math.round((midX + 38) / scale), (int) ((getGuiTop() + 101) / scale), 4210752);
			GL11.glPopMatrix();
			s = StatCollector.translateToLocalFormatted("got.gui.warrior.xp", theNPC.hiredNPCInfo.xp);
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, getGuiTop() + 110, 4210752);
			s = StatCollector.translateToLocalFormatted("got.gui.warrior.kills", theNPC.hiredNPCInfo.mobKills);
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, getGuiTop() + 122, 4210752);
		}
		if (getPage() == 1) {
			String s = StatCollector.translateToLocal("got.gui.warrior.squadron");
			fontRendererObj.drawString(s, squadronNameField.xPosition, squadronNameField.yPosition - fontRendererObj.FONT_HEIGHT - 3, 4210752);
			squadronNameField.drawTextBox();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		int midX = getGuiLeft() + getxSize() / 2;
		if (getPage() == 0) {
			buttonOpenInv = new GOTGuiButtonOptions(0, midX - 80, getGuiTop() + 142, 160, 20, StatCollector.translateToLocal("got.gui.warrior.openInv"));
			buttonList.add(buttonOpenInv);
		} else if (getPage() == 1) {
			buttonTeleport = new GOTGuiButtonOptions(0, midX - 80, getGuiTop() + 180, 160, 20, StatCollector.translateToLocal("got.gui.warrior.teleport"));
			buttonList.add(buttonTeleport);
			buttonGuardMode = new GOTGuiButtonOptions(1, midX - 80, getGuiTop() + 50, 160, 20, StatCollector.translateToLocal("got.gui.warrior.guardMode"));
			buttonList.add(buttonGuardMode);
			sliderGuardRange = new GOTGuiSlider(2, midX - 80, getGuiTop() + 74, 160, 20, StatCollector.translateToLocal("got.gui.warrior.guardRange"));
			buttonList.add(sliderGuardRange);
			sliderGuardRange.setMinMaxValues(GOTHiredNPCInfo.GUARD_RANGE_MIN, GOTHiredNPCInfo.GUARD_RANGE_MAX);
			sliderGuardRange.setSliderValue(theNPC.hiredNPCInfo.getGuardRange());
			squadronNameField = new GuiTextField(fontRendererObj, midX - 80, getGuiTop() + 130, 160, 20);
			squadronNameField.setMaxStringLength(GOTSquadrons.SQUADRON_LENGTH_MAX);
			String squadron = theNPC.hiredNPCInfo.getSquadron();
			if (!StringUtils.isNullOrEmpty(squadron)) {
				squadronNameField.setText(squadron);
			}
		}
		buttonLeft = new GOTGuiButtonLeftRight(1000, true, getGuiLeft() - 130, getGuiTop() + 50, "");
		buttonRight = new GOTGuiButtonLeftRight(1001, false, getGuiLeft() + getxSize() + 10, getGuiTop() + 50, "");
		buttonList.add(buttonLeft);
		buttonList.add(buttonRight);
		buttonLeft.displayString = getPage() == 0 ? pageTitles[pageTitles.length - 1] : pageTitles[getPage() - 1];
		buttonRight.displayString = getPage() == pageTitles.length - 1 ? pageTitles[0] : pageTitles[getPage() + 1];
		buttonLeft.displayString = StatCollector.translateToLocal("got.gui.warrior." + buttonLeft.displayString);
		buttonRight.displayString = StatCollector.translateToLocal("got.gui.warrior." + buttonRight.displayString);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (getPage() == 1 && squadronNameField != null && squadronNameField.getVisible() && squadronNameField.textboxKeyTyped(c, i)) {
			theNPC.hiredNPCInfo.setSquadron(squadronNameField.getText());
			sendSquadronUpdate = true;
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		if (getPage() == 1 && squadronNameField != null) {
			squadronNameField.mouseClicked(i, j, k);
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (sendSquadronUpdate) {
			String squadron = theNPC.hiredNPCInfo.getSquadron();
			GOTPacketNPCSquadron packet = new GOTPacketNPCSquadron(theNPC, squadron);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	@Override
	public void updateScreen() {
		if (updatePage) {
			initGui();
			updatePage = false;
		}
		super.updateScreen();
		if (getPage() == 1) {
			buttonTeleport.setState(theNPC.hiredNPCInfo.teleportAutomatically);
			buttonTeleport.enabled = !theNPC.hiredNPCInfo.isGuardMode();
			buttonGuardMode.setState(theNPC.hiredNPCInfo.isGuardMode());
			sliderGuardRange.visible = theNPC.hiredNPCInfo.isGuardMode();
			if (sliderGuardRange.isDragging()) {
				int i = sliderGuardRange.getSliderValue();
				theNPC.hiredNPCInfo.setGuardRange(i);
				this.sendActionPacket(sliderGuardRange.id, i);
			}
			squadronNameField.updateCursorCounter();
		}
	}
}
