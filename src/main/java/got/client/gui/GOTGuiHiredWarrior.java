package got.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTSquadrons;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.info.GOTHireableInfo;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketNPCSquadron;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class GOTGuiHiredWarrior extends GOTGuiHiredNPC {
	private static final String[] PAGE_TITLES = {"overview", "options"};

	private GOTGuiButtonOptions buttonTeleport;
	private GOTGuiButtonOptions buttonGuardMode;
	private GOTGuiSlider sliderGuardRange;
	private GuiTextField squadronNameField;
	private GuiButton buttonLeft;
	private GuiButton buttonRight;

	private boolean updatePage;
	private boolean sendSquadronUpdate;

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
					--page;
					if (page < 0) {
						page = PAGE_TITLES.length - 1;
					}
				} else if (button == buttonRight) {
					++page;
					if (page >= PAGE_TITLES.length) {
						page = 0;
					}
				}
				buttonList.clear();
				updatePage = true;
			} else {
				sendActionPacket(button.id);
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		if (page == 0) {
			int midX = guiLeft + X_SIZE / 2;
			String s = StatCollector.translateToLocalFormatted("got.gui.warrior.health", Math.round(theNPC.getHealth()), Math.round(theNPC.getMaxHealth()));
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, guiTop + 50, 4210752);
			s = theNPC.getHireableInfo().getStatusString();
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, guiTop + 62, 4210752);
			s = StatCollector.translateToLocalFormatted("got.gui.warrior.level", theNPC.getHireableInfo().getXpLevel());
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, guiTop + 80, 4210752);
			float lvlProgress = theNPC.getHireableInfo().getProgressToNextLevel();
			String curLevel = EnumChatFormatting.BOLD + String.valueOf(theNPC.getHireableInfo().getXpLevel());
			String nextLevel = EnumChatFormatting.BOLD + String.valueOf(theNPC.getHireableInfo().getXpLevel() + 1);
			String xpCurLevel = String.valueOf(theNPC.getHireableInfo().totalXPForLevel(theNPC.getHireableInfo().getXpLevel()));
			String xpNextLevel = String.valueOf(theNPC.getHireableInfo().totalXPForLevel(theNPC.getHireableInfo().getXpLevel() + 1));
			drawRect(midX - 36, guiTop + 96, midX + 36, guiTop + 102, -16777216);
			drawRect(midX - 35, guiTop + 97, midX + 35, guiTop + 101, -10658467);
			drawRect(midX - 35, guiTop + 97, midX - 35 + (int) (lvlProgress * 70.0f), guiTop + 101, -43776);
			GL11.glPushMatrix();
			float scale = 0.67f;
			GL11.glScalef(scale, scale, 1.0f);
			fontRendererObj.drawString(curLevel, Math.round((midX - 38 - fontRendererObj.getStringWidth(curLevel) * scale) / scale), (int) ((guiTop + 94) / scale), 4210752);
			fontRendererObj.drawString(nextLevel, Math.round((midX + 38) / scale), (int) ((guiTop + 94) / scale), 4210752);
			fontRendererObj.drawString(xpCurLevel, Math.round((midX - 38 - fontRendererObj.getStringWidth(xpCurLevel) * scale) / scale), (int) ((guiTop + 101) / scale), 4210752);
			fontRendererObj.drawString(xpNextLevel, Math.round((midX + 38) / scale), (int) ((guiTop + 101) / scale), 4210752);
			GL11.glPopMatrix();
			s = StatCollector.translateToLocalFormatted("got.gui.warrior.xp", theNPC.getHireableInfo().getXp());
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, guiTop + 110, 4210752);
			s = StatCollector.translateToLocalFormatted("got.gui.warrior.kills", theNPC.getHireableInfo().getMobKills());
			fontRendererObj.drawString(s, midX - fontRendererObj.getStringWidth(s) / 2, guiTop + 122, 4210752);
		}
		if (page == 1) {
			String s = StatCollector.translateToLocal("got.gui.warrior.squadron");
			fontRendererObj.drawString(s, squadronNameField.xPosition, squadronNameField.yPosition - fontRendererObj.FONT_HEIGHT - 3, 4210752);
			squadronNameField.drawTextBox();
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		int midX = guiLeft + X_SIZE / 2;
		if (page == 0) {
			GOTGuiButtonOptions buttonOpenInv = new GOTGuiButtonOptions(0, midX - 80, guiTop + 142, 160, 20, StatCollector.translateToLocal("got.gui.warrior.openInv"));
			buttonList.add(buttonOpenInv);
		} else if (page == 1) {
			buttonTeleport = new GOTGuiButtonOptions(0, midX - 80, guiTop + 180, 160, 20, StatCollector.translateToLocal("got.gui.warrior.teleport"));
			buttonList.add(buttonTeleport);
			buttonGuardMode = new GOTGuiButtonOptions(1, midX - 80, guiTop + 50, 160, 20, StatCollector.translateToLocal("got.gui.warrior.guardMode"));
			buttonList.add(buttonGuardMode);
			sliderGuardRange = new GOTGuiSlider(2, midX - 80, guiTop + 74, 160, 20, StatCollector.translateToLocal("got.gui.warrior.guardRange"));
			buttonList.add(sliderGuardRange);
			sliderGuardRange.setMinMaxValues(GOTHireableInfo.GUARD_RANGE_MIN, GOTHireableInfo.GUARD_RANGE_MAX);
			sliderGuardRange.setSliderValue(theNPC.getHireableInfo().getGuardRange());
			squadronNameField = new GuiTextField(fontRendererObj, midX - 80, guiTop + 130, 160, 20);
			squadronNameField.setMaxStringLength(GOTSquadrons.SQUADRON_LENGTH_MAX);
			String squadron = theNPC.getHireableInfo().getHiredSquadron();
			if (!StringUtils.isNullOrEmpty(squadron)) {
				squadronNameField.setText(squadron);
			}
		}
		buttonLeft = new GOTGuiButtonLeftRight(1000, true, guiLeft - 130, guiTop + 50, "");
		buttonRight = new GOTGuiButtonLeftRight(1001, false, guiLeft + X_SIZE + 10, guiTop + 50, "");
		buttonList.add(buttonLeft);
		buttonList.add(buttonRight);
		buttonLeft.displayString = PAGE_TITLES[(page == 0 ? PAGE_TITLES.length : page) - 1];
		buttonRight.displayString = PAGE_TITLES[page == PAGE_TITLES.length - 1 ? 0 : page + 1];
		buttonLeft.displayString = StatCollector.translateToLocal("got.gui.warrior." + buttonLeft.displayString);
		buttonRight.displayString = StatCollector.translateToLocal("got.gui.warrior." + buttonRight.displayString);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (page == 1 && squadronNameField != null && squadronNameField.getVisible() && squadronNameField.textboxKeyTyped(c, i)) {
			theNPC.getHireableInfo().setHiredSquadron(squadronNameField.getText());
			sendSquadronUpdate = true;
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		if (page == 1 && squadronNameField != null) {
			squadronNameField.mouseClicked(i, j, k);
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (sendSquadronUpdate) {
			String squadron = theNPC.getHireableInfo().getHiredSquadron();
			IMessage packet = new GOTPacketNPCSquadron(theNPC, squadron);
			GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
		}
	}

	@Override
	public void updateScreen() {
		if (updatePage) {
			initGui();
			updatePage = false;
		}
		super.updateScreen();
		if (page == 1) {
			buttonTeleport.setState(theNPC.getHireableInfo().isTeleportAutomatically());
			buttonTeleport.enabled = !theNPC.getHireableInfo().isGuardMode();
			buttonGuardMode.setState(theNPC.getHireableInfo().isGuardMode());
			sliderGuardRange.visible = theNPC.getHireableInfo().isGuardMode();
			if (sliderGuardRange.isDragging()) {
				int i = sliderGuardRange.getSliderValue();
				theNPC.getHireableInfo().setGuardRange(i);
				sendActionPacket(sliderGuardRange.id, i);
			}
			squadronNameField.updateCursorCounter();
		}
	}
}