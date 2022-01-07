package got.client.gui;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import got.common.*;
import got.common.fellowship.GOTFellowshipClient;
import got.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTGuiFellowships extends GOTGuiMenuWBBase {
	public static ResourceLocation iconsTextures = new ResourceLocation("got:gui/fellowships.png");
	public static int MAX_NAME_LENGTH = 40;
	public static int entrySplit = 5;
	public static int entryBorder = 10;
	public static int selectBorder = 2;
	public static int maxDisplayedFellowships = 12;
	public static int maxDisplayedMembers = 11;
	public static int maxDisplayedInvites = 15;
	public Page page = Page.LIST;
	public List<GOTFellowshipClient> allFellowshipsLeading = new ArrayList<>();
	public List<GOTFellowshipClient> allFellowshipsOther = new ArrayList<>();
	public List<GOTFellowshipClient> allFellowshipInvites = new ArrayList<>();
	public GOTFellowshipClient mouseOverFellowship;
	public GOTFellowshipClient viewingFellowship;
	public String mouseOverPlayer;
	public boolean mouseOverPlayerRemove;
	public boolean mouseOverPlayerOp;
	public boolean mouseOverPlayerDeop;
	public boolean mouseOverPlayerTransfer;
	public String removingPlayer;
	public String oppingPlayer;
	public String deoppingPlayer;
	public String transferringPlayer;
	public boolean mouseOverInviteAccept;
	public boolean mouseOverInviteReject;
	public GuiButton buttonCreate;
	public GuiButton buttonCreateThis;
	public GOTGuiButtonFsOption buttonInvitePlayer;
	public GuiButton buttonInviteThis;
	public GOTGuiButtonFsOption buttonDisband;
	public GuiButton buttonDisbandThis;
	public GuiButton buttonLeave;
	public GuiButton buttonLeaveThis;
	public GOTGuiButtonFsOption buttonSetIcon;
	public GuiButton buttonRemove;
	public GuiButton buttonTransfer;
	public GOTGuiButtonFsOption buttonRename;
	public GuiButton buttonRenameThis;
	public GuiButton buttonBack;
	public GuiButton buttonInvites;
	public GOTGuiButtonFsOption buttonPVP;
	public GOTGuiButtonFsOption buttonHiredFF;
	public GOTGuiButtonFsOption buttonMapShow;
	public GuiButton buttonOp;
	public GuiButton buttonDeop;
	public List<GOTGuiButtonFsOption> orderedFsOptionButtons = new ArrayList<>();
	public GuiTextField textFieldName;
	public GuiTextField textFieldPlayer;
	public GuiTextField textFieldRename;
	public int scrollWidgetWidth;
	public int scrollWidgetHeight;
	public int scrollBarX;
	public GOTGuiScrollPane scrollPaneLeading;
	public GOTGuiScrollPane scrollPaneOther;
	public GOTGuiScrollPane scrollPaneMembers;
	public GOTGuiScrollPane scrollPaneInvites;
	public int displayedFellowshipsLeading;
	public int displayedFellowshipsOther;
	public int displayedMembers;
	public int displayedInvites;

	public GOTGuiFellowships() {
		xSize = 256;
		scrollWidgetWidth = 9;
		scrollWidgetHeight = 8;
		scrollBarX = xSize + 2 + 1;
		scrollPaneLeading = new GOTGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
		scrollPaneOther = new GOTGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
		scrollPaneMembers = new GOTGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
		scrollPaneInvites = new GOTGuiScrollPane(scrollWidgetWidth, scrollWidgetHeight);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonCreate) {
				page = Page.CREATE;
			} else if (button.enabled && button == goBack) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else if (button == buttonCreateThis) {
				String name = textFieldName.getText();
				if (checkValidFellowshipName(name) == null) {
					name = StringUtils.trim(name);
					GOTPacketFellowshipCreate packet = new GOTPacketFellowshipCreate(name);
					GOTPacketHandler.networkWrapper.sendToServer(packet);
				}
				page = Page.LIST;
			} else if (button == buttonInvitePlayer) {
				page = Page.INVITE;
			} else if (button == buttonInviteThis) {
				String name = textFieldPlayer.getText();
				if (checkValidPlayerName(name) == null) {
					name = StringUtils.trim(name);
					GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, name, GOTPacketFellowshipDoPlayer.PlayerFunction.INVITE);
					GOTPacketHandler.networkWrapper.sendToServer(packet);
				}
				page = Page.FELLOWSHIP;
			} else if (button == buttonDisband) {
				page = Page.DISBAND;
			} else if (button == buttonDisbandThis) {
				GOTPacketFellowshipDisband packet = new GOTPacketFellowshipDisband(viewingFellowship);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.LIST;
			} else if (button == buttonLeave) {
				page = Page.LEAVE;
			} else if (button == buttonLeaveThis) {
				GOTPacketFellowshipLeave packet = new GOTPacketFellowshipLeave(viewingFellowship);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.LIST;
			} else if (button == buttonSetIcon) {
				GOTPacketFellowshipSetIcon packet = new GOTPacketFellowshipSetIcon(viewingFellowship);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == buttonRemove) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, removingPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.REMOVE);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonOp) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, oppingPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.OP);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonDeop) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, deoppingPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.DEOP);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonTransfer) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, transferringPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.TRANSFER);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonRename) {
				page = Page.RENAME;
			} else if (button == buttonRenameThis) {
				String name = textFieldRename.getText();
				if (checkValidFellowshipName(name) == null) {
					name = StringUtils.trim(name);
					GOTPacketFellowshipRename packet = new GOTPacketFellowshipRename(viewingFellowship, name);
					GOTPacketHandler.networkWrapper.sendToServer(packet);
				}
				page = Page.FELLOWSHIP;
			} else if (button == buttonBack) {
				keyTyped('E', 1);
			} else if (button == buttonInvites) {
				page = Page.INVITATIONS;
			} else if (button == buttonPVP) {
				GOTPacketFellowshipToggle packet = new GOTPacketFellowshipToggle(viewingFellowship, GOTPacketFellowshipToggle.ToggleFunction.PVP);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == buttonHiredFF) {
				GOTPacketFellowshipToggle packet = new GOTPacketFellowshipToggle(viewingFellowship, GOTPacketFellowshipToggle.ToggleFunction.HIRED_FF);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else if (button == buttonMapShow) {
				GOTPacketFellowshipToggle packet = new GOTPacketFellowshipToggle(viewingFellowship, GOTPacketFellowshipToggle.ToggleFunction.MAP_SHOW);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	public void alignOptionButtons() {
		ArrayList<GuiButton> activeOptionButtons = new ArrayList<>();
		for (GuiButton button : orderedFsOptionButtons) {
			if (button.visible) {
				activeOptionButtons.add(button);
			}
		}
		if (buttonLeave.visible) {
			activeOptionButtons.add(buttonLeave);
		}
		int midX = guiLeft + xSize / 2;
		int numActive = activeOptionButtons.size();
		if (numActive > 0) {
			int gap = 8;
			int allWidth = 0;
			for (GuiButton button : activeOptionButtons) {
				if (allWidth > 0) {
					allWidth += gap;
				}
				allWidth += button.width;
			}
			int x = midX - allWidth / 2;
			for (GuiButton activeOptionButton : activeOptionButtons) {
				GuiButton button = activeOptionButton;
				button.xPosition = x;
				x += button.width;
				x += gap;
			}
		}
	}

	public void buttonSound() {
		buttonBack.func_146113_a(mc.getSoundHandler());
	}

	public String checkValidFellowshipName(String name) {
		if (!StringUtils.isWhitespace(name)) {
			if (GOTLevelData.getData(mc.thePlayer).anyMatchingFellowshipNames(name, true)) {
				return StatCollector.translateToLocal("got.gui.fellowships.nameExists");
			}
			return null;
		}
		return "";
	}

	public String checkValidPlayerName(String name) {
		if (!StringUtils.isWhitespace(name)) {
			if (viewingFellowship.isPlayerIn(name)) {
				return StatCollector.translateToLocalFormatted("got.gui.fellowships.playerExists", name);
			}
			return null;
		}
		return "";
	}

	public void clearMouseOverFellowship() {
		mouseOverFellowship = null;
	}

	public int countOnlineMembers(GOTFellowshipClient fs) {
		int i = 0;
		ArrayList<String> allPlayers = new ArrayList<>(fs.getAllPlayerNames());
		for (String player : allPlayers) {
			if (!(!GOTGuiFellowships.isPlayerOnline(player))) {
				++i;
			}
		}
		return i;
	}

	public void drawFellowshipEntry(GOTFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite) {
		this.drawFellowshipEntry(fs, x, y, mouseX, mouseY, isInvite, xSize);
	}

	public void drawFellowshipEntry(GOTFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite, int selectWidth) {
		int selectX0 = x - 2;
		int selectX1 = x + selectWidth + 2;
		int selectY0 = y - 2;
		int selectY1 = y + fontRendererObj.FONT_HEIGHT + 2;
		if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
			Gui.drawRect(selectX0, selectY0, selectX1, selectY1, 1442840575);
			mouseOverFellowship = fs;
		}
		boolean isMouseOver = mouseOverFellowship == fs;
		drawFellowshipIcon(fs, x, y, 0.5f);
		String fsName = fs.getName();
		int maxLength = 110;
		if (fontRendererObj.getStringWidth(fsName) > maxLength) {
			String ellipsis = "...";
			while (fontRendererObj.getStringWidth(fsName + ellipsis) > maxLength) {
				fsName = fsName.substring(0, fsName.length() - 1);
			}
			fsName = fsName + ellipsis;
		}
		String ownerName = fs.getOwnerName();
		boolean ownerOnline = GOTGuiFellowships.isPlayerOnline(ownerName);
		fontRendererObj.drawString(fsName, x + 15, y, 16777215);
		fontRendererObj.drawString(ownerName, x + 130, y, ownerOnline ? 16777215 : isMouseOver ? 12303291 : 7829367);
		if (isInvite) {
			int iconWidth = 8;
			int iconAcceptX = x + xSize - 18;
			int iconRejectX = x + xSize - 8;
			boolean accept = false;
			boolean reject = false;
			if (isMouseOver) {
				accept = mouseOverInviteAccept = mouseX >= iconAcceptX && mouseX <= iconAcceptX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
				reject = mouseOverInviteReject = mouseX >= iconRejectX && mouseX <= iconRejectX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
			}
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(iconAcceptX, y, 16, 16 + (accept ? 0 : iconWidth), iconWidth, iconWidth);
			this.drawTexturedModalRect(iconRejectX, y, 8, 16 + (reject ? 0 : iconWidth), iconWidth, iconWidth);
		} else {
			String memberCount = String.valueOf(fs.getMemberCount());
			String onlineMemberCount = String.valueOf(countOnlineMembers(fs)) + " | ";
			fontRendererObj.drawString(memberCount, x + xSize - fontRendererObj.getStringWidth(memberCount), y, isMouseOver ? 12303291 : 7829367);
			fontRendererObj.drawString(onlineMemberCount, x + xSize - fontRendererObj.getStringWidth(memberCount) - fontRendererObj.getStringWidth(onlineMemberCount), y, 16777215);
		}
	}

	public void drawFellowshipIcon(GOTFellowshipClient fsClient, int x, int y, float scale) {
		ItemStack fsIcon = fsClient.getIcon();
		if (fsIcon != null) {
			GL11.glDisable(3042);
			GL11.glDisable(3008);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(2896);
			GL11.glEnable(32826);
			GL11.glEnable(2896);
			GL11.glEnable(2884);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glPushMatrix();
			GL11.glScalef(scale, scale, 1.0f);
			renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), fsIcon, Math.round(x / scale), Math.round(y / scale));
			GL11.glPopMatrix();
			GL11.glDisable(2896);
		}
	}

	public void drawPlayerEntry(String player, int x, int y, int titleOffset, int mouseX, int mouseY) {
		int selectX0 = x - 2;
		int selectX1 = x + xSize + 2;
		int selectY0 = y - 2;
		int selectY1 = y + fontRendererObj.FONT_HEIGHT + 2;
		if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
			Gui.drawRect(selectX0, selectY0, selectX1, selectY1, 1442840575);
			mouseOverPlayer = player;
		}
		boolean isMouseOver = mouseOverPlayer == player;

		fontRendererObj.drawString(player, x + titleOffset, y, GOTGuiFellowships.isPlayerOnline(player) ? 16777215 : isMouseOver ? 12303291 : 7829367);
		boolean isOwner = viewingFellowship.getOwnerName().equals(player);
		boolean isAdmin = viewingFellowship.isAdmin(player);
		if (isOwner) {
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(x + titleOffset + fontRendererObj.getStringWidth(player + " "), y, 0, 0, 8, 8);
		} else if (isAdmin) {
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(x + titleOffset + fontRendererObj.getStringWidth(player + " "), y, 8, 0, 8, 8);
		}
		boolean owned = viewingFellowship.isOwned();
		boolean adminned = viewingFellowship.isAdminned();
		if (!isOwner && (owned || adminned)) {
			int iconWidth = 8;
			int iconRemoveX = x + xSize - 28;
			int iconOpDeopX = x + xSize - 18;
			int iconTransferX = x + xSize - 8;
			if (adminned) {
				iconRemoveX = x + xSize - 8;
			}
			boolean remove = false;
			boolean opDeop = false;
			boolean transfer = false;
			if (isMouseOver) {
				remove = mouseOverPlayerRemove = mouseX >= iconRemoveX && mouseX <= iconRemoveX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
				if (owned) {
					opDeop = isAdmin ? (mouseOverPlayerDeop = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth) : (mouseOverPlayerOp = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
					transfer = mouseOverPlayerTransfer = mouseX >= iconTransferX && mouseX <= iconTransferX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
				}
			}
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(iconRemoveX, y, 8, 16 + (remove ? 0 : iconWidth), iconWidth, iconWidth);
			if (owned) {
				if (isAdmin) {
					this.drawTexturedModalRect(iconOpDeopX, y, 32, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
				} else {
					this.drawTexturedModalRect(iconOpDeopX, y, 24, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
				}
				this.drawTexturedModalRect(iconTransferX, y, 0, 16 + (transfer ? 0 : iconWidth), iconWidth, iconWidth);
			}
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		GOTPlayerData playerData = GOTLevelData.getData(mc.thePlayer);
		boolean viewingOwned = viewingFellowship != null && viewingFellowship.isOwned();
		boolean viewingAdminned = viewingFellowship != null && viewingFellowship.isAdminned();
		mouseOverFellowship = null;
		mouseOverPlayer = null;
		mouseOverPlayerRemove = false;
		mouseOverPlayerOp = false;
		mouseOverPlayerDeop = false;
		mouseOverPlayerTransfer = false;
		if (page != Page.REMOVE) {
			removingPlayer = null;
		}
		if (page != Page.OP) {
			oppingPlayer = null;
		}
		if (page != Page.DEOP) {
			deoppingPlayer = null;
		}
		if (page != Page.TRANSFER) {
			transferringPlayer = null;
		}
		mouseOverInviteAccept = false;
		mouseOverInviteReject = false;
		boolean canCreateNew = playerData.canCreateFellowships(true);
		buttonCreate.visible = page == Page.LIST;
		buttonCreate.enabled = buttonCreate.visible && canCreateNew;
		buttonCreateThis.visible = page == Page.CREATE;
		String checkValidName = checkValidFellowshipName(textFieldName.getText());
		buttonCreateThis.enabled = buttonCreateThis.visible && checkValidName == null;
		buttonInvitePlayer.enabled = page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
		buttonInvitePlayer.visible = buttonInvitePlayer.enabled;
		buttonInviteThis.visible = page == Page.INVITE;
		String checkValidPlayer = checkValidPlayerName(textFieldPlayer.getText());
		buttonInviteThis.enabled = buttonInviteThis.visible && checkValidPlayer == null;
		buttonDisband.enabled = page == Page.FELLOWSHIP && viewingOwned;
		buttonDisband.visible = buttonDisband.enabled;
		buttonDisbandThis.enabled = page == Page.DISBAND;
		buttonDisbandThis.visible = buttonDisbandThis.enabled;
		buttonLeave.enabled = page == Page.FELLOWSHIP && !viewingOwned;
		buttonLeave.visible = buttonLeave.enabled;
		buttonLeaveThis.enabled = page == Page.LEAVE;
		buttonLeaveThis.visible = buttonLeaveThis.enabled;
		buttonSetIcon.enabled = page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
		buttonSetIcon.visible = buttonSetIcon.enabled;
		buttonRemove.enabled = page == Page.REMOVE;
		buttonRemove.visible = buttonRemove.enabled;
		buttonTransfer.enabled = page == Page.TRANSFER;
		buttonTransfer.visible = buttonTransfer.enabled;
		buttonRename.enabled = page == Page.FELLOWSHIP && viewingOwned;
		buttonRename.visible = buttonRename.enabled;
		buttonRenameThis.visible = page == Page.RENAME;
		String checkValidRename = checkValidFellowshipName(textFieldRename.getText());
		buttonRenameThis.enabled = buttonRenameThis.visible && checkValidRename == null;
		buttonBack.enabled = page != Page.LIST;
		buttonBack.visible = buttonBack.enabled;
		buttonInvites.enabled = page == Page.LIST;
		buttonInvites.visible = buttonInvites.enabled;
		buttonPVP.enabled = page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
		buttonPVP.visible = buttonPVP.enabled;
		if (buttonPVP.enabled) {
			buttonPVP.setIconUV(64, viewingFellowship.getPreventPVP() ? 80 : 48);
		}
		buttonHiredFF.enabled = page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
		buttonHiredFF.visible = buttonHiredFF.enabled;
		if (buttonHiredFF.enabled) {
			buttonHiredFF.setIconUV(80, viewingFellowship.getPreventHiredFriendlyFire() ? 80 : 48);
		}
		buttonMapShow.enabled = page == Page.FELLOWSHIP && viewingOwned;
		buttonMapShow.visible = buttonMapShow.enabled;
		if (buttonMapShow.enabled) {
			buttonMapShow.setIconUV(96, viewingFellowship.getShowMapLocations() ? 48 : 80);
		}
		buttonOp.enabled = page == Page.OP;
		buttonOp.visible = buttonOp.enabled;
		buttonDeop.enabled = page == Page.DEOP;
		buttonDeop.visible = buttonDeop.enabled;
		alignOptionButtons();
		setupScrollBars(i, j);
		drawDefaultBackground();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		super.drawScreen(i, j, f);
		String s = StatCollector.translateToLocal("got.gui.fellowships.title");
		this.drawCenteredString(s, guiLeft + xSize / 2, guiTop - 30, 16777215);
		if (page == Page.LIST) {
			int x = guiLeft;
			int y = scrollPaneLeading.paneY0;
			s = StatCollector.translateToLocal("got.gui.fellowships.leading");
			this.drawCenteredString(s, guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT + 10;
			List<GOTFellowshipClient> sortedLeading = sortFellowshipsForDisplay(allFellowshipsLeading);
			int[] leadingMinMax = scrollPaneLeading.getMinMaxIndices(sortedLeading, displayedFellowshipsLeading);
			for (int index = leadingMinMax[0]; index <= leadingMinMax[1]; ++index) {
				GOTFellowshipClient fs = sortedLeading.get(index);
				this.drawFellowshipEntry(fs, x, y, i, j, false);
				y += fontRendererObj.FONT_HEIGHT + 5;
			}
			y = scrollPaneOther.paneY0;
			s = StatCollector.translateToLocal("got.gui.fellowships.member");
			this.drawCenteredString(s, guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT + 10;
			List<GOTFellowshipClient> sortedOther = sortFellowshipsForDisplay(allFellowshipsOther);
			int[] otherMinMax = scrollPaneOther.getMinMaxIndices(sortedOther, displayedFellowshipsOther);
			for (int index = otherMinMax[0]; index <= otherMinMax[1]; ++index) {
				GOTFellowshipClient fs = sortedOther.get(index);
				this.drawFellowshipEntry(fs, x, y, i, j, false);
				y += fontRendererObj.FONT_HEIGHT + 5;
			}
			String invites = String.valueOf(playerData.getClientFellowshipInvites().size());
			int invitesX = buttonInvites.xPosition - 2 - fontRendererObj.getStringWidth(invites);
			int invitesY = buttonInvites.yPosition + buttonInvites.height / 2 - fontRendererObj.FONT_HEIGHT / 2;
			fontRendererObj.drawString(invites, invitesX, invitesY, 16777215);
			if (buttonInvites.func_146115_a()) {
				renderIconTooltip(i, j, StatCollector.translateToLocal("got.gui.fellowships.invitesTooltip"));
			}
			if (!canCreateNew && buttonCreate.func_146115_a()) {
				s = StatCollector.translateToLocal("got.gui.fellowships.createLimit");
				this.drawCenteredString(s, guiLeft + xSize / 2, buttonCreate.yPosition + buttonCreate.height + 4, 16777215);
			}
			if (scrollPaneLeading.hasScrollBar) {
				scrollPaneLeading.drawScrollBar();
			}
			if (scrollPaneOther.hasScrollBar) {
				scrollPaneOther.drawScrollBar();
			}
		} else if (page == Page.CREATE) {
			s = StatCollector.translateToLocal("got.gui.fellowships.createName");
			this.drawCenteredString(s, guiLeft + xSize / 2, textFieldName.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldName.drawTextBox();
			if (checkValidName != null) {
				this.drawCenteredString(checkValidName, guiLeft + xSize / 2, textFieldName.yPosition + textFieldName.height + fontRendererObj.FONT_HEIGHT, 16711680);
			}
		} else if (page == Page.FELLOWSHIP) {
			int x = guiLeft;
			int y = guiTop + 10;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.nameAndPlayers", viewingFellowship.getName(), viewingFellowship.getMemberCount());
			this.drawCenteredString(s, guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
			y += 5;
			if (viewingFellowship.getIcon() != null) {
				drawFellowshipIcon(viewingFellowship, guiLeft + xSize / 2 - 8, y, 1.0f);
			}
			boolean preventPVP = viewingFellowship.getPreventPVP();
			boolean preventHiredFF = viewingFellowship.getPreventHiredFriendlyFire();
			boolean mapShow = viewingFellowship.getShowMapLocations();
			int iconPVPX = guiLeft + xSize - 36;
			int iconHFFX = guiLeft + xSize - 16;
			int iconMapX = guiLeft + xSize - 56;
			int iconY = y;
			int iconSize = 16;
			mc.getTextureManager().bindTexture(iconsTextures);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(iconPVPX, iconY, 64, preventPVP ? 80 : 48, iconSize, iconSize);
			this.drawTexturedModalRect(iconHFFX, iconY, 80, preventHiredFF ? 80 : 48, iconSize, iconSize);
			this.drawTexturedModalRect(iconMapX, iconY, 96, mapShow ? 48 : 80, iconSize, iconSize);
			if (i >= iconPVPX && i < iconPVPX + iconSize && j >= iconY && j < iconY + iconSize) {
				renderIconTooltip(i, j, StatCollector.translateToLocal(preventPVP ? "got.gui.fellowships.pvp.prevent" : "got.gui.fellowships.pvp.allow"));
			}
			if (i >= iconHFFX && i < iconHFFX + iconSize && j >= iconY && j < iconY + iconSize) {
				renderIconTooltip(i, j, StatCollector.translateToLocal(preventHiredFF ? "got.gui.fellowships.hiredFF.prevent" : "got.gui.fellowships.hiredFF.allow"));
			}
			if (i >= iconMapX && i < iconMapX + iconSize && j >= iconY && j < iconY + iconSize) {
				renderIconTooltip(i, j, StatCollector.translateToLocal(mapShow ? "got.gui.fellowships.mapShow.on" : "got.gui.fellowships.mapShow.off"));
			}
			y += iconSize;
			y += 10;
			int titleOffset = 0;
			drawPlayerEntry(viewingFellowship.getOwnerName(), x, y, titleOffset, i, j);
			y += fontRendererObj.FONT_HEIGHT + 10;
			List<String> membersSorted = sortMemberNamesForDisplay(viewingFellowship);
			int[] membersMinMax = scrollPaneMembers.getMinMaxIndices(membersSorted, displayedMembers);
			for (int index = membersMinMax[0]; index <= membersMinMax[1]; ++index) {
				String name = membersSorted.get(index);
				drawPlayerEntry(name, x, y, titleOffset, i, j);
				y += fontRendererObj.FONT_HEIGHT + 5;
			}
			for (Object bObj : buttonList) {
				GuiButton button = (GuiButton) bObj;
				if (!(!(button instanceof GOTGuiButtonFsOption) || !button.visible || !button.func_146115_a())) {
					s = button.displayString;
					this.drawCenteredString(s, guiLeft + xSize / 2, button.yPosition + button.height + 4, 16777215);
				}
			}
			if (scrollPaneMembers.hasScrollBar) {
				scrollPaneMembers.drawScrollBar();
			}
		} else if (page == Page.INVITE) {
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.inviteName", viewingFellowship.getName());
			this.drawCenteredString(s, guiLeft + xSize / 2, textFieldPlayer.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldPlayer.drawTextBox();
			if (checkValidPlayer != null) {
				this.drawCenteredString(checkValidPlayer, guiLeft + xSize / 2, textFieldPlayer.yPosition + textFieldPlayer.height + fontRendererObj.FONT_HEIGHT, 16711680);
			}
		} else if (page == Page.DISBAND) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.disbandCheck1", viewingFellowship.getName());
			this.drawCenteredString(s, x, y, 16777215);
			s = StatCollector.translateToLocal("got.gui.fellowships.disbandCheck2");
			this.drawCenteredString(s, x, y += fontRendererObj.FONT_HEIGHT, 16777215);
			s = StatCollector.translateToLocal("got.gui.fellowships.disbandCheck3");
			this.drawCenteredString(s, x, y += fontRendererObj.FONT_HEIGHT * 2, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
		} else if (page == Page.LEAVE) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.leaveCheck1", viewingFellowship.getName());
			this.drawCenteredString(s, x, y, 16777215);
			s = StatCollector.translateToLocal("got.gui.fellowships.leaveCheck2");
			this.drawCenteredString(s, x, y += fontRendererObj.FONT_HEIGHT, 16777215);
			y += fontRendererObj.FONT_HEIGHT * 2;
		} else if (page == Page.REMOVE) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.removeCheck", viewingFellowship.getName(), removingPlayer);
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s, xSize);
			for (String line : lines) {
				this.drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
		} else if (page == Page.OP) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.opCheck1", viewingFellowship.getName(), oppingPlayer);
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s, xSize);
			for (String line : lines) {
				this.drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
			y += fontRendererObj.FONT_HEIGHT;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.opCheck2", viewingFellowship.getName(), oppingPlayer);
			lines = fontRendererObj.listFormattedStringToWidth(s, xSize);
			for (String line : lines) {
				this.drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
		} else if (page == Page.DEOP) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.deopCheck", viewingFellowship.getName(), deoppingPlayer);
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s, xSize);
			for (String line : lines) {
				this.drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
		} else if (page == Page.TRANSFER) {
			int x = guiLeft + xSize / 2;
			int y = guiTop + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.transferCheck1", viewingFellowship.getName(), transferringPlayer);
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s, xSize);
			for (String line : lines) {
				this.drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}
			s = StatCollector.translateToLocal("got.gui.fellowships.transferCheck2");
			this.drawCenteredString(s, x, y += fontRendererObj.FONT_HEIGHT, 16777215);
			y += fontRendererObj.FONT_HEIGHT;
		} else if (page == Page.RENAME) {
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.renameName", viewingFellowship.getName());
			this.drawCenteredString(s, guiLeft + xSize / 2, textFieldRename.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldRename.drawTextBox();
			if (checkValidRename != null) {
				this.drawCenteredString(checkValidRename, guiLeft + xSize / 2, textFieldRename.yPosition + textFieldRename.height + fontRendererObj.FONT_HEIGHT, 16711680);
			}
		} else if (page == Page.INVITATIONS) {
			int x = guiLeft;
			int y = guiTop + 10;
			s = StatCollector.translateToLocal("got.gui.fellowships.invites");
			this.drawCenteredString(s, guiLeft + xSize / 2, y, 16777215);
			y += fontRendererObj.FONT_HEIGHT + 10;
			if (allFellowshipInvites.isEmpty()) {
				s = StatCollector.translateToLocal("got.gui.fellowships.invitesNone");
				this.drawCenteredString(s, guiLeft + xSize / 2, y += fontRendererObj.FONT_HEIGHT, 16777215);
			} else {
				int[] invitesMinMax = scrollPaneInvites.getMinMaxIndices(allFellowshipInvites, displayedInvites);
				for (int index = invitesMinMax[0]; index <= invitesMinMax[1]; ++index) {
					GOTFellowshipClient fs = allFellowshipInvites.get(index);
					this.drawFellowshipEntry(fs, x, y, i, j, true);
					y += fontRendererObj.FONT_HEIGHT + 5;
				}
			}
			if (scrollPaneInvites.hasScrollBar) {
				scrollPaneInvites.drawScrollBar();
			}
		}
	}

	public GOTFellowshipClient getMouseOverFellowship() {
		return mouseOverFellowship;
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int k = Mouse.getEventDWheel();
		if (k != 0) {
			int l;
			k = Integer.signum(k);
			if (page == Page.LIST) {
				if (scrollPaneLeading.hasScrollBar && scrollPaneLeading.mouseOver) {
					l = allFellowshipsLeading.size() - displayedFellowshipsLeading;
					scrollPaneLeading.mouseWheelScroll(k, l);
				}
				if (scrollPaneOther.hasScrollBar && scrollPaneOther.mouseOver) {
					l = allFellowshipsOther.size() - displayedFellowshipsOther;
					scrollPaneOther.mouseWheelScroll(k, l);
				}
			}
			if (page == Page.FELLOWSHIP && scrollPaneMembers.hasScrollBar && scrollPaneMembers.mouseOver) {
				l = viewingFellowship.getMemberNames().size() - displayedMembers;
				scrollPaneMembers.mouseWheelScroll(k, l);
			}
			if (page == Page.INVITATIONS && scrollPaneInvites.hasScrollBar && scrollPaneInvites.mouseOver) {
				l = allFellowshipInvites.size() - displayedInvites;
				scrollPaneInvites.mouseWheelScroll(k, l);
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		guiLeft = (width - xSize) / 2 + 90;
		guiTop = (height - ySize) / 2 + 10;
		if (mc.thePlayer != null) {
			refreshFellowshipList();
		}
		int midX = guiLeft + xSize / 2;
		buttonCreateThis = new GOTGuiButton(1, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.createThis"));
		buttonList.add(buttonCreateThis);
		buttonInvitePlayer = new GOTGuiButtonFsOption(2, midX, guiTop + 232, 0, 48, StatCollector.translateToLocal("got.gui.fellowships.invite"));
		buttonList.add(buttonInvitePlayer);
		buttonInviteThis = new GOTGuiButton(3, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.inviteThis"));
		buttonList.add(buttonInviteThis);
		buttonDisband = new GOTGuiButtonFsOption(4, midX, guiTop + 232, 16, 48, StatCollector.translateToLocal("got.gui.fellowships.disband"));
		buttonList.add(buttonDisband);
		buttonDisbandThis = new GOTGuiButton(5, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.disbandThis"));
		buttonList.add(buttonDisbandThis);
		buttonLeave = new GOTGuiButton(6, midX - 60, guiTop + 230, 120, 20, StatCollector.translateToLocal("got.gui.fellowships.leave"));
		buttonList.add(buttonLeave);
		buttonLeaveThis = new GOTGuiButton(7, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.leaveThis"));
		buttonList.add(buttonLeaveThis);
		buttonSetIcon = new GOTGuiButtonFsOption(8, midX, guiTop + 232, 48, 48, StatCollector.translateToLocal("got.gui.fellowships.setIcon"));
		buttonList.add(buttonSetIcon);
		buttonRemove = new GOTGuiButton(9, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.remove"));
		buttonList.add(buttonRemove);
		buttonTransfer = new GOTGuiButton(10, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.transfer"));
		buttonList.add(buttonTransfer);
		buttonRename = new GOTGuiButtonFsOption(11, midX, guiTop + 232, 32, 48, StatCollector.translateToLocal("got.gui.fellowships.rename"));
		buttonList.add(buttonRename);
		buttonRenameThis = new GOTGuiButton(12, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.renameThis"));
		buttonList.add(buttonRenameThis);
		buttonBack = new GOTGuiButton(13, guiLeft, guiTop, 20, 20, "<");
		buttonList.add(buttonBack);
		buttonInvites = new GOTGuiButtonFsInvites(14, guiLeft + xSize - 16, guiTop, "");
		buttonList.add(buttonInvites);
		buttonPVP = new GOTGuiButtonFsOption(15, midX, guiTop + 232, 64, 48, StatCollector.translateToLocal("got.gui.fellowships.togglePVP"));
		buttonList.add(buttonPVP);
		buttonHiredFF = new GOTGuiButtonFsOption(16, midX, guiTop + 232, 80, 48, StatCollector.translateToLocal("got.gui.fellowships.toggleHiredFF"));
		buttonList.add(buttonHiredFF);
		buttonMapShow = new GOTGuiButtonFsOption(17, midX, guiTop + 232, 96, 48, StatCollector.translateToLocal("got.gui.fellowships.toggleMapShow"));
		buttonList.add(buttonMapShow);
		buttonOp = new GOTGuiButton(18, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.op"));
		buttonList.add(buttonOp);
		buttonDeop = new GOTGuiButton(19, midX - 100, guiTop + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.deop"));
		buttonList.add(buttonDeop);
		orderedFsOptionButtons.clear();
		orderedFsOptionButtons.add(buttonInvitePlayer);
		orderedFsOptionButtons.add(buttonDisband);
		orderedFsOptionButtons.add(buttonRename);
		orderedFsOptionButtons.add(buttonSetIcon);
		orderedFsOptionButtons.add(buttonMapShow);
		orderedFsOptionButtons.add(buttonPVP);
		orderedFsOptionButtons.add(buttonHiredFF);
		textFieldName = new GuiTextField(fontRendererObj, midX - 80, guiTop + 40, 160, 20);
		textFieldName.setMaxStringLength(40);
		textFieldPlayer = new GuiTextField(fontRendererObj, midX - 80, guiTop + 40, 160, 20);
		textFieldRename = new GuiTextField(fontRendererObj, midX - 80, guiTop + 40, 160, 20);
		textFieldRename.setMaxStringLength(40);
		buttonCreate = new GOTGuiButton(88, guiLeft + xSize / 2 - 310, guiTop + 80, 160, 20, StatCollector.translateToLocal("got.gui.fellowships.create"));
		buttonList.add(buttonCreate);
		goBack = new GOTGuiButton(89, guiLeft + xSize / 2 - 310, guiTop + 110, 160, 20, StatCollector.translateToLocal("got.gui.menuButton"));
		buttonList.add(goBack);
	}

	@Override
	public void keyTyped(char c, int i) {
		if ((page == Page.CREATE && textFieldName.textboxKeyTyped(c, i)) || (page == Page.INVITE && textFieldPlayer.textboxKeyTyped(c, i))) {
			return;
		}
		if (page == Page.RENAME && textFieldRename.textboxKeyTyped(c, i)) {
			return;
		}
		if (page != Page.LIST) {
			if (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode()) {
				page = page == Page.INVITE || page == Page.DISBAND || page == Page.LEAVE || page == Page.REMOVE || page == Page.OP || page == Page.DEOP || page == Page.TRANSFER || page == Page.RENAME ? Page.FELLOWSHIP : Page.LIST;
			}
		} else {
			super.keyTyped(c, i);
		}
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		GOTPacketFellowshipRespondInvite packet;
		super.mouseClicked(i, j, k);
		if (page == Page.LIST && mouseOverFellowship != null) {
			buttonSound();
			page = Page.FELLOWSHIP;
			viewingFellowship = mouseOverFellowship;
		}
		if (page == Page.CREATE) {
			textFieldName.mouseClicked(i, j, k);
		}
		if (page == Page.INVITE) {
			textFieldPlayer.mouseClicked(i, j, k);
		}
		if (page == Page.RENAME) {
			textFieldRename.mouseClicked(i, j, k);
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerRemove) {
			buttonSound();
			page = Page.REMOVE;
			removingPlayer = mouseOverPlayer;
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerOp) {
			buttonSound();
			page = Page.OP;
			oppingPlayer = mouseOverPlayer;
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerDeop) {
			buttonSound();
			page = Page.DEOP;
			deoppingPlayer = mouseOverPlayer;
		}
		if (page == Page.FELLOWSHIP && mouseOverPlayer != null && mouseOverPlayerTransfer) {
			buttonSound();
			page = Page.TRANSFER;
			transferringPlayer = mouseOverPlayer;
		}
		if (page == Page.INVITATIONS && mouseOverFellowship != null && mouseOverInviteAccept) {
			buttonSound();
			packet = new GOTPacketFellowshipRespondInvite(mouseOverFellowship, true);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
			mouseOverFellowship = null;
		}
		if (page == Page.INVITATIONS && mouseOverFellowship != null && mouseOverInviteReject) {
			buttonSound();
			packet = new GOTPacketFellowshipRespondInvite(mouseOverFellowship, false);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
			mouseOverFellowship = null;
		}
	}

	public void refreshFellowshipList() {
		allFellowshipsLeading.clear();
		allFellowshipsOther.clear();
		ArrayList<GOTFellowshipClient> fellowships = new ArrayList<>(GOTLevelData.getData(mc.thePlayer).getClientFellowships());
		for (GOTFellowshipClient fs : fellowships) {
			if (fs.isOwned()) {
				allFellowshipsLeading.add(fs);
			} else {
				allFellowshipsOther.add(fs);
			}
		}
		allFellowshipInvites.clear();
		allFellowshipInvites.addAll(GOTLevelData.getData(mc.thePlayer).getClientFellowshipInvites());
	}

	public void renderIconTooltip(int x, int y, String s) {
		float z = zLevel;
		int stringWidth = 200;
		List desc = fontRendererObj.listFormattedStringToWidth(s, stringWidth);
		func_146283_a(desc, x, y);
		GL11.glDisable(2896);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		zLevel = z;
	}

	public void setupScrollBars(int i, int j) {
		if (page == Page.LIST) {
			displayedFellowshipsLeading = allFellowshipsLeading.size();
			displayedFellowshipsOther = allFellowshipsOther.size();
			scrollPaneLeading.hasScrollBar = false;
			scrollPaneOther.hasScrollBar = false;
			while (displayedFellowshipsLeading + displayedFellowshipsOther > 12) {
				if (displayedFellowshipsOther >= displayedFellowshipsLeading) {
					--displayedFellowshipsOther;
					scrollPaneOther.hasScrollBar = true;
				} else {
					--displayedFellowshipsLeading;
					scrollPaneLeading.hasScrollBar = true;
				}
			}
			scrollPaneLeading.paneX0 = guiLeft;
			scrollPaneLeading.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneLeading.paneY0 = guiTop + 10;
			scrollPaneLeading.paneY1 = scrollPaneLeading.paneY0 + fontRendererObj.FONT_HEIGHT + 10 + (fontRendererObj.FONT_HEIGHT + 5) * displayedFellowshipsLeading;
			scrollPaneLeading.mouseDragScroll(i, j);
			scrollPaneOther.paneX0 = guiLeft;
			scrollPaneOther.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneOther.paneY0 = scrollPaneLeading.paneY1 + 5;
			scrollPaneOther.paneY1 = scrollPaneOther.paneY0 + fontRendererObj.FONT_HEIGHT + 10 + (fontRendererObj.FONT_HEIGHT + 5) * displayedFellowshipsOther;
			scrollPaneOther.mouseDragScroll(i, j);
		}
		if (page == Page.FELLOWSHIP) {
			displayedMembers = viewingFellowship.getMemberNames().size();
			scrollPaneMembers.hasScrollBar = false;
			if (displayedMembers > 11) {
				displayedMembers = 11;
				scrollPaneMembers.hasScrollBar = true;
			}
			scrollPaneMembers.paneX0 = guiLeft;
			scrollPaneMembers.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneMembers.paneY0 = guiTop + 10 + fontRendererObj.FONT_HEIGHT + 5 + 16 + 10 + fontRendererObj.FONT_HEIGHT + 10;
			scrollPaneMembers.paneY1 = scrollPaneMembers.paneY0 + (fontRendererObj.FONT_HEIGHT + 5) * displayedMembers;
		} else {
			scrollPaneMembers.hasScrollBar = false;
		}
		scrollPaneMembers.mouseDragScroll(i, j);
		if (page == Page.INVITATIONS) {
			displayedInvites = allFellowshipInvites.size();
			scrollPaneInvites.hasScrollBar = false;
			if (displayedInvites > 15) {
				displayedInvites = 15;
				scrollPaneInvites.hasScrollBar = true;
			}
			scrollPaneInvites.paneX0 = guiLeft;
			scrollPaneInvites.scrollBarX0 = guiLeft + scrollBarX;
			scrollPaneInvites.paneY0 = guiTop + 10 + fontRendererObj.FONT_HEIGHT + 10;
			scrollPaneInvites.paneY1 = scrollPaneInvites.paneY0 + (fontRendererObj.FONT_HEIGHT + 5) * displayedInvites;
			scrollPaneInvites.mouseDragScroll(i, j);
		}
	}

	public List<GOTFellowshipClient> sortFellowshipsForDisplay(List<GOTFellowshipClient> list) {
		ArrayList<GOTFellowshipClient> sorted = new ArrayList<>(list);
		Collections.sort(sorted, new Comparator<GOTFellowshipClient>() {

			@Override
			public int compare(GOTFellowshipClient fs1, GOTFellowshipClient fs2) {
				int count2;
				int count1 = fs1.getMemberCount();
				if (count1 == (count2 = fs2.getMemberCount())) {
					return fs1.getName().toLowerCase().compareTo(fs2.getName().toLowerCase());
				}
				return -Integer.compare(count1, count2);
			}
		});
		return sorted;
	}

	public List<String> sortMemberNamesForDisplay(GOTFellowshipClient fs) {
		ArrayList<String> members = new ArrayList<>(fs.getMemberNames());
		Collections.sort(members, new Comparator<String>() {

			@Override
			public int compare(String player1, String player2) {
				boolean online2;
				boolean admin1 = fs.isAdmin(player1);
				boolean admin2 = fs.isAdmin(player2);
				boolean online1 = GOTGuiFellowships.isPlayerOnline(player1);
				if (online1 == (online2 = GOTGuiFellowships.isPlayerOnline(player2))) {
					if (admin1 == admin2) {
						return player1.toLowerCase().compareTo(player2.toLowerCase());
					}
					if (admin1 && !admin2) {
						return -1;
					}
					if (!admin1 && admin2) {
						return 1;
					}
				} else {
					if (online1 && !online2) {
						return -1;
					}
					if (!online1 && online2) {
						return 1;
					}
				}
				return 0;
			}
		});
		return members;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		refreshFellowshipList();
		textFieldName.updateCursorCounter();
		if (page != Page.CREATE) {
			textFieldName.setText("");
		}
		textFieldPlayer.updateCursorCounter();
		if (page != Page.INVITE) {
			textFieldPlayer.setText("");
		}
		textFieldRename.updateCursorCounter();
		if (page != Page.RENAME) {
			textFieldRename.setText("");
		}
	}

	public static boolean isPlayerOnline(String player) {
		EntityClientPlayerMP mcPlayer = Minecraft.getMinecraft().thePlayer;
		List list = mcPlayer.sendQueue.playerInfoList;
		for (Object obj : list) {
			GuiPlayerInfo info = (GuiPlayerInfo) obj;
			if (info.name.equalsIgnoreCase(player)) {
				return true;
			}
		}
		return false;
	}

	public enum Page {
		LIST, CREATE, FELLOWSHIP, INVITE, DISBAND, LEAVE, REMOVE, OP, DEOP, TRANSFER, RENAME, INVITATIONS;

	}

}