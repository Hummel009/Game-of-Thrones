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
	private static ResourceLocation iconsTextures = new ResourceLocation("got:textures/gui/fellowships.png");
	private Page page = Page.LIST;
	private List<GOTFellowshipClient> allFellowshipsLeading = new ArrayList<>();
	private List<GOTFellowshipClient> allFellowshipsOther = new ArrayList<>();
	private List<GOTFellowshipClient> allFellowshipInvites = new ArrayList<>();
	private GOTFellowshipClient mouseOverFellowship;
	private GOTFellowshipClient viewingFellowship;
	private String mouseOverPlayer;
	private boolean mouseOverPlayerRemove;
	private boolean mouseOverPlayerOp;
	private boolean mouseOverPlayerDeop;
	private boolean mouseOverPlayerTransfer;
	private String removingPlayer;
	private String oppingPlayer;
	private String deoppingPlayer;
	private String transferringPlayer;
	private boolean mouseOverInviteAccept;
	private boolean mouseOverInviteReject;
	private GuiButton buttonCreate;
	private GuiButton buttonCreateThis;
	private GOTGuiButtonFsOption buttonInvitePlayer;
	private GuiButton buttonInviteThis;
	private GOTGuiButtonFsOption buttonDisband;
	private GuiButton buttonDisbandThis;
	private GuiButton buttonLeave;
	private GuiButton buttonLeaveThis;
	private GOTGuiButtonFsOption buttonSetIcon;
	private GuiButton buttonRemove;
	private GuiButton buttonTransfer;
	private GOTGuiButtonFsOption buttonRename;
	private GuiButton buttonRenameThis;
	private GuiButton buttonBack;
	private GuiButton buttonInvites;
	private GOTGuiButtonFsOption buttonPVP;
	private GOTGuiButtonFsOption buttonHiredFF;
	private GOTGuiButtonFsOption buttonMapShow;
	private GuiButton buttonOp;
	private GuiButton buttonDeop;
	private List<GOTGuiButtonFsOption> orderedFsOptionButtons = new ArrayList<>();
	private GuiTextField textFieldName;
	private GuiTextField textFieldPlayer;
	private GuiTextField textFieldRename;
	private int scrollBarX;
	private GOTGuiScrollPane scrollPaneLeading;
	private GOTGuiScrollPane scrollPaneOther;
	private GOTGuiScrollPane scrollPaneMembers;
	private GOTGuiScrollPane scrollPaneInvites;
	private int displayedFellowshipsLeading;
	private int displayedFellowshipsOther;
	private int displayedMembers;
	private int displayedInvites;

	public GOTGuiFellowships() {
		setxSize(256);
		int scrollWidgetWidth = 9;
		int scrollWidgetHeight = 8;
		scrollBarX = getxSize() + 2 + 1;
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
			} else if (button.enabled && button == getGoBack()) {
				mc.displayGuiScreen(new GOTGuiMenu());
			} else if (button == buttonCreateThis) {
				String name = textFieldName.getText();
				if (checkValidFellowshipName(name) == null) {
					name = StringUtils.trim(name);
					GOTPacketFellowshipCreate packet = new GOTPacketFellowshipCreate(name);
					GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				}
				page = Page.LIST;
			} else if (button == buttonInvitePlayer) {
				page = Page.INVITE;
			} else if (button == buttonInviteThis) {
				String name = textFieldPlayer.getText();
				if (checkValidPlayerName(name) == null) {
					name = StringUtils.trim(name);
					GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, name, GOTPacketFellowshipDoPlayer.PlayerFunction.INVITE);
					GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				}
				page = Page.FELLOWSHIP;
			} else if (button == buttonDisband) {
				page = Page.DISBAND;
			} else if (button == buttonDisbandThis) {
				GOTPacketFellowshipDisband packet = new GOTPacketFellowshipDisband(viewingFellowship);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				page = Page.LIST;
			} else if (button == buttonLeave) {
				page = Page.LEAVE;
			} else if (button == buttonLeaveThis) {
				GOTPacketFellowshipLeave packet = new GOTPacketFellowshipLeave(viewingFellowship);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				page = Page.LIST;
			} else if (button == buttonSetIcon) {
				GOTPacketFellowshipSetIcon packet = new GOTPacketFellowshipSetIcon(viewingFellowship);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
			} else if (button == buttonRemove) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, removingPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.REMOVE);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonOp) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, oppingPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.OP);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonDeop) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, deoppingPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.DEOP);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonTransfer) {
				GOTPacketFellowshipDoPlayer packet = new GOTPacketFellowshipDoPlayer(viewingFellowship, transferringPlayer, GOTPacketFellowshipDoPlayer.PlayerFunction.TRANSFER);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				page = Page.FELLOWSHIP;
			} else if (button == buttonRename) {
				page = Page.RENAME;
			} else if (button == buttonRenameThis) {
				String name = textFieldRename.getText();
				if (checkValidFellowshipName(name) == null) {
					name = StringUtils.trim(name);
					GOTPacketFellowshipRename packet = new GOTPacketFellowshipRename(viewingFellowship, name);
					GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				}
				page = Page.FELLOWSHIP;
			} else if (button == buttonBack) {
				keyTyped('E', 1);
			} else if (button == buttonInvites) {
				page = Page.INVITATIONS;
			} else if (button == buttonPVP) {
				GOTPacketFellowshipToggle packet = new GOTPacketFellowshipToggle(viewingFellowship, GOTPacketFellowshipToggle.ToggleFunction.PVP);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
			} else if (button == buttonHiredFF) {
				GOTPacketFellowshipToggle packet = new GOTPacketFellowshipToggle(viewingFellowship, GOTPacketFellowshipToggle.ToggleFunction.HIRED_FF);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
			} else if (button == buttonMapShow) {
				GOTPacketFellowshipToggle packet = new GOTPacketFellowshipToggle(viewingFellowship, GOTPacketFellowshipToggle.ToggleFunction.MAP_SHOW);
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
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
		int midX = getGuiLeft() + getxSize() / 2;
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

	private String checkValidFellowshipName(String name) {
		if (!StringUtils.isWhitespace(name)) {
			if (GOTLevelData.getData(mc.thePlayer).anyMatchingFellowshipNames(name, true)) {
				return StatCollector.translateToLocal("got.gui.fellowships.nameExists");
			}
			return null;
		}
		return "";
	}

	private String checkValidPlayerName(String name) {
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

	private int countOnlineMembers(GOTFellowshipClient fs) {
		int i = 0;
		ArrayList<String> allPlayers = new ArrayList<>(fs.getAllPlayerNames());
		for (String player : allPlayers) {
			if (GOTGuiFellowships.isPlayerOnline(player)) {
				++i;
			}
		}
		return i;
	}

	public void drawFellowshipEntry(GOTFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite) {
		this.drawFellowshipEntry(fs, x, y, mouseX, mouseY, isInvite, getxSize());
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
			int iconAcceptX = x + getxSize() - 18;
			int iconRejectX = x + getxSize() - 8;
			boolean accept = false;
			boolean reject = false;
			if (isMouseOver) {
				accept = mouseOverInviteAccept = mouseX >= iconAcceptX && mouseX <= iconAcceptX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
				reject = mouseOverInviteReject = mouseX >= iconRejectX && mouseX <= iconRejectX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
			}
			mc.getTextureManager().bindTexture(getIconsTextures());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(iconAcceptX, y, 16, 16 + (accept ? 0 : iconWidth), iconWidth, iconWidth);
			this.drawTexturedModalRect(iconRejectX, y, 8, 16 + (reject ? 0 : iconWidth), iconWidth, iconWidth);
		} else {
			String memberCount = String.valueOf(fs.getMemberCount());
			String onlineMemberCount = String.valueOf(countOnlineMembers(fs)) + " | ";
			fontRendererObj.drawString(memberCount, x + getxSize() - fontRendererObj.getStringWidth(memberCount), y, isMouseOver ? 12303291 : 7829367);
			fontRendererObj.drawString(onlineMemberCount, x + getxSize() - fontRendererObj.getStringWidth(memberCount) - fontRendererObj.getStringWidth(onlineMemberCount), y, 16777215);
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
			getRenderItem().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), fsIcon, Math.round(x / scale), Math.round(y / scale));
			GL11.glPopMatrix();
			GL11.glDisable(2896);
		}
	}

	public void drawPlayerEntry(String player, int x, int y, int titleOffset, int mouseX, int mouseY) {
		int selectX0 = x - 2;
		int selectX1 = x + getxSize() + 2;
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
			mc.getTextureManager().bindTexture(getIconsTextures());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(x + titleOffset + fontRendererObj.getStringWidth(player + " "), y, 0, 0, 8, 8);
		} else if (isAdmin) {
			mc.getTextureManager().bindTexture(getIconsTextures());
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			this.drawTexturedModalRect(x + titleOffset + fontRendererObj.getStringWidth(player + " "), y, 8, 0, 8, 8);
		}
		boolean owned = viewingFellowship.isOwned();
		boolean adminned = viewingFellowship.isAdminned();
		if (!isOwner && (owned || adminned)) {
			int iconWidth = 8;
			int iconRemoveX = x + getxSize() - 28;
			int iconOpDeopX = x + getxSize() - 18;
			int iconTransferX = x + getxSize() - 8;
			if (adminned) {
				iconRemoveX = x + getxSize() - 8;
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
			mc.getTextureManager().bindTexture(getIconsTextures());
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
		this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, getGuiTop() - 30, 16777215);
		switch (page) {
		case CREATE:
			s = StatCollector.translateToLocal("got.gui.fellowships.createName");
			this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, textFieldName.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldName.drawTextBox();
			if (checkValidName != null) {
				this.drawCenteredString(checkValidName, getGuiLeft() + getxSize() / 2, textFieldName.yPosition + textFieldName.height + fontRendererObj.FONT_HEIGHT, 16711680);
			}
			break;
		case DEOP:
			int x = getGuiLeft() + getxSize() / 2;
			int y = getGuiTop() + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.deopCheck", viewingFellowship.getName(), deoppingPlayer);
			List<String> lines = fontRendererObj.listFormattedStringToWidth(s, getxSize());
			for (String line : lines) {
				this.drawCenteredString(line, x, y, 16777215);
				y += fontRendererObj.FONT_HEIGHT;
			}

			break;
		case DISBAND:
			int x1 = getGuiLeft() + getxSize() / 2;
			int y1 = getGuiTop() + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.disbandCheck1", viewingFellowship.getName());
			this.drawCenteredString(s, x1, y1, 16777215);
			s = StatCollector.translateToLocal("got.gui.fellowships.disbandCheck2");
			this.drawCenteredString(s, x1, y1 += fontRendererObj.FONT_HEIGHT, 16777215);
			s = StatCollector.translateToLocal("got.gui.fellowships.disbandCheck3");
			this.drawCenteredString(s, x1, y1 += fontRendererObj.FONT_HEIGHT * 2, 16777215);
			y1 += fontRendererObj.FONT_HEIGHT;
			break;
		case FELLOWSHIP:
			int x2 = getGuiLeft();
			int y2 = getGuiTop() + 10;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.nameAndPlayers", viewingFellowship.getName(), viewingFellowship.getMemberCount());
			this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, y2, 16777215);
			y2 += fontRendererObj.FONT_HEIGHT;
			y2 += 5;
			if (viewingFellowship.getIcon() != null) {
				drawFellowshipIcon(viewingFellowship, getGuiLeft() + getxSize() / 2 - 8, y2, 1.0f);
			}
			boolean preventPVP = viewingFellowship.getPreventPVP();
			boolean preventHiredFF = viewingFellowship.getPreventHiredFriendlyFire();
			boolean mapShow = viewingFellowship.getShowMapLocations();
			int iconPVPX = getGuiLeft() + getxSize() - 36;
			int iconHFFX = getGuiLeft() + getxSize() - 16;
			int iconMapX = getGuiLeft() + getxSize() - 56;
			int iconY = y2;
			int iconSize = 16;
			mc.getTextureManager().bindTexture(getIconsTextures());
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
			y2 += iconSize;
			y2 += 10;
			int titleOffset = 0;
			drawPlayerEntry(viewingFellowship.getOwnerName(), x2, y2, titleOffset, i, j);
			y2 += fontRendererObj.FONT_HEIGHT + 10;
			List<String> membersSorted = sortMemberNamesForDisplay(viewingFellowship);
			int[] membersMinMax = scrollPaneMembers.getMinMaxIndices(membersSorted, displayedMembers);
			for (int index = membersMinMax[0]; index <= membersMinMax[1]; ++index) {
				String name = membersSorted.get(index);
				drawPlayerEntry(name, x2, y2, titleOffset, i, j);
				y2 += fontRendererObj.FONT_HEIGHT + 5;
			}
			for (Object bObj : buttonList) {
				GuiButton button = (GuiButton) bObj;
				if (button instanceof GOTGuiButtonFsOption && button.visible && button.func_146115_a()) {
					s = button.displayString;
					this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, button.yPosition + button.height + 4, 16777215);
				}
			}
			if (scrollPaneMembers.isHasScrollBar()) {
				scrollPaneMembers.drawScrollBar();
			}
			break;
		case INVITATIONS:
			int x3 = getGuiLeft();
			int y3 = getGuiTop() + 10;
			s = StatCollector.translateToLocal("got.gui.fellowships.invites");
			this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, y3, 16777215);
			y3 += fontRendererObj.FONT_HEIGHT + 10;
			if (allFellowshipInvites.isEmpty()) {
				s = StatCollector.translateToLocal("got.gui.fellowships.invitesNone");
				this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, y3 += fontRendererObj.FONT_HEIGHT, 16777215);
			} else {
				int[] invitesMinMax = scrollPaneInvites.getMinMaxIndices(allFellowshipInvites, displayedInvites);
				for (int index = invitesMinMax[0]; index <= invitesMinMax[1]; ++index) {
					GOTFellowshipClient fs = allFellowshipInvites.get(index);
					this.drawFellowshipEntry(fs, x3, y3, i, j, true);
					y3 += fontRendererObj.FONT_HEIGHT + 5;
				}
			}
			if (scrollPaneInvites.isHasScrollBar()) {
				scrollPaneInvites.drawScrollBar();
			}

			break;
		case INVITE:
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.inviteName", viewingFellowship.getName());
			this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, textFieldPlayer.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldPlayer.drawTextBox();
			if (checkValidPlayer != null) {
				this.drawCenteredString(checkValidPlayer, getGuiLeft() + getxSize() / 2, textFieldPlayer.yPosition + textFieldPlayer.height + fontRendererObj.FONT_HEIGHT, 16711680);
			}
			break;
		case LEAVE:
			int x4 = getGuiLeft() + getxSize() / 2;
			int y4 = getGuiTop() + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.leaveCheck1", viewingFellowship.getName());
			this.drawCenteredString(s, x4, y4, 16777215);
			s = StatCollector.translateToLocal("got.gui.fellowships.leaveCheck2");
			this.drawCenteredString(s, x4, y4 += fontRendererObj.FONT_HEIGHT, 16777215);
			y4 += fontRendererObj.FONT_HEIGHT * 2;
			break;
		case LIST:
			int x5 = getGuiLeft();
			int y5 = scrollPaneLeading.getPaneY0();
			s = StatCollector.translateToLocal("got.gui.fellowships.leading");
			this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, y5, 16777215);
			y5 += fontRendererObj.FONT_HEIGHT + 10;
			List<GOTFellowshipClient> sortedLeading = sortFellowshipsForDisplay(allFellowshipsLeading);
			int[] leadingMinMax = scrollPaneLeading.getMinMaxIndices(sortedLeading, displayedFellowshipsLeading);
			for (int index = leadingMinMax[0]; index <= leadingMinMax[1]; ++index) {
				GOTFellowshipClient fs = sortedLeading.get(index);
				this.drawFellowshipEntry(fs, x5, y5, i, j, false);
				y5 += fontRendererObj.FONT_HEIGHT + 5;
			}
			y5 = scrollPaneOther.getPaneY0();
			s = StatCollector.translateToLocal("got.gui.fellowships.member");
			this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, y5, 16777215);
			y5 += fontRendererObj.FONT_HEIGHT + 10;
			List<GOTFellowshipClient> sortedOther = sortFellowshipsForDisplay(allFellowshipsOther);
			int[] otherMinMax = scrollPaneOther.getMinMaxIndices(sortedOther, displayedFellowshipsOther);
			for (int index = otherMinMax[0]; index <= otherMinMax[1]; ++index) {
				GOTFellowshipClient fs = sortedOther.get(index);
				this.drawFellowshipEntry(fs, x5, y5, i, j, false);
				y5 += fontRendererObj.FONT_HEIGHT + 5;
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
				this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, buttonCreate.yPosition + buttonCreate.height + 4, 16777215);
			}
			if (scrollPaneLeading.isHasScrollBar()) {
				scrollPaneLeading.drawScrollBar();
			}
			if (scrollPaneOther.isHasScrollBar()) {
				scrollPaneOther.drawScrollBar();
			}
			break;
		case OP:
			int x6 = getGuiLeft() + getxSize() / 2;
			int y6 = getGuiTop() + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.opCheck1", viewingFellowship.getName(), oppingPlayer);
			List<String> lines6 = fontRendererObj.listFormattedStringToWidth(s, getxSize());
			for (String line : lines6) {
				this.drawCenteredString(line, x6, y6, 16777215);
				y6 += fontRendererObj.FONT_HEIGHT;
			}
			y6 += fontRendererObj.FONT_HEIGHT;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.opCheck2", viewingFellowship.getName(), oppingPlayer);
			lines6 = fontRendererObj.listFormattedStringToWidth(s, getxSize());
			for (String line : lines6) {
				this.drawCenteredString(line, x6, y6, 16777215);
				y6 += fontRendererObj.FONT_HEIGHT;
			}

			break;
		case REMOVE:
			int x7 = getGuiLeft() + getxSize() / 2;
			int y7 = getGuiTop() + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.removeCheck", viewingFellowship.getName(), removingPlayer);
			List<String> lines7 = fontRendererObj.listFormattedStringToWidth(s, getxSize());
			for (String line : lines7) {
				this.drawCenteredString(line, x7, y7, 16777215);
				y7 += fontRendererObj.FONT_HEIGHT;
			}
			break;
		case RENAME:
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.renameName", viewingFellowship.getName());
			this.drawCenteredString(s, getGuiLeft() + getxSize() / 2, textFieldRename.yPosition - 4 - fontRendererObj.FONT_HEIGHT, 16777215);
			textFieldRename.drawTextBox();
			if (checkValidRename != null) {
				this.drawCenteredString(checkValidRename, getGuiLeft() + getxSize() / 2, textFieldRename.yPosition + textFieldRename.height + fontRendererObj.FONT_HEIGHT, 16711680);
			}

			break;
		case TRANSFER:
			int x8 = getGuiLeft() + getxSize() / 2;
			int y8 = getGuiTop() + 30;
			s = StatCollector.translateToLocalFormatted("got.gui.fellowships.transferCheck1", viewingFellowship.getName(), transferringPlayer);
			List<String> lines8 = fontRendererObj.listFormattedStringToWidth(s, getxSize());
			for (String line : lines8) {
				this.drawCenteredString(line, x8, y8, 16777215);
				y8 += fontRendererObj.FONT_HEIGHT;
			}
			s = StatCollector.translateToLocal("got.gui.fellowships.transferCheck2");
			this.drawCenteredString(s, x8, y8 += fontRendererObj.FONT_HEIGHT, 16777215);
			y8 += fontRendererObj.FONT_HEIGHT;
			break;
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
			switch (page) {
			case LIST:
				if (scrollPaneLeading.isHasScrollBar() && scrollPaneLeading.isMouseOver()) {
					l = allFellowshipsLeading.size() - displayedFellowshipsLeading;
					scrollPaneLeading.mouseWheelScroll(k, l);
				}
				if (scrollPaneOther.isHasScrollBar() && scrollPaneOther.isMouseOver()) {
					l = allFellowshipsOther.size() - displayedFellowshipsOther;
					scrollPaneOther.mouseWheelScroll(k, l);
				}
				break;
			case FELLOWSHIP:
				if (scrollPaneMembers.isHasScrollBar() && scrollPaneMembers.isMouseOver()) {
					l = viewingFellowship.getMemberNames().size() - displayedMembers;
					scrollPaneMembers.mouseWheelScroll(k, l);
				}
				break;
			case INVITATIONS:
				if (scrollPaneInvites.isHasScrollBar() && scrollPaneInvites.isMouseOver()) {
					l = allFellowshipInvites.size() - displayedInvites;
					scrollPaneInvites.mouseWheelScroll(k, l);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		setGuiLeft((width - getxSize()) / 2 + 90);
		setGuiTop((height - getySize()) / 2 + 10);
		if (mc.thePlayer != null) {
			refreshFellowshipList();
		}
		int midX = getGuiLeft() + getxSize() / 2;
		buttonCreateThis = new GOTGuiButton(1, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.createThis"));
		buttonList.add(buttonCreateThis);
		buttonInvitePlayer = new GOTGuiButtonFsOption(2, midX, getGuiTop() + 232, 0, 48, StatCollector.translateToLocal("got.gui.fellowships.invite"));
		buttonList.add(buttonInvitePlayer);
		buttonInviteThis = new GOTGuiButton(3, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.inviteThis"));
		buttonList.add(buttonInviteThis);
		buttonDisband = new GOTGuiButtonFsOption(4, midX, getGuiTop() + 232, 16, 48, StatCollector.translateToLocal("got.gui.fellowships.disband"));
		buttonList.add(buttonDisband);
		buttonDisbandThis = new GOTGuiButton(5, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.disbandThis"));
		buttonList.add(buttonDisbandThis);
		buttonLeave = new GOTGuiButton(6, midX - 60, getGuiTop() + 230, 120, 20, StatCollector.translateToLocal("got.gui.fellowships.leave"));
		buttonList.add(buttonLeave);
		buttonLeaveThis = new GOTGuiButton(7, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.leaveThis"));
		buttonList.add(buttonLeaveThis);
		buttonSetIcon = new GOTGuiButtonFsOption(8, midX, getGuiTop() + 232, 48, 48, StatCollector.translateToLocal("got.gui.fellowships.setIcon"));
		buttonList.add(buttonSetIcon);
		buttonRemove = new GOTGuiButton(9, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.remove"));
		buttonList.add(buttonRemove);
		buttonTransfer = new GOTGuiButton(10, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.transfer"));
		buttonList.add(buttonTransfer);
		buttonRename = new GOTGuiButtonFsOption(11, midX, getGuiTop() + 232, 32, 48, StatCollector.translateToLocal("got.gui.fellowships.rename"));
		buttonList.add(buttonRename);
		buttonRenameThis = new GOTGuiButton(12, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.renameThis"));
		buttonList.add(buttonRenameThis);
		buttonBack = new GOTGuiButton(13, getGuiLeft(), getGuiTop(), 20, 20, "<");
		buttonList.add(buttonBack);
		buttonInvites = new GOTGuiButtonFsInvites(14, getGuiLeft() + getxSize() - 16, getGuiTop(), "");
		buttonList.add(buttonInvites);
		buttonPVP = new GOTGuiButtonFsOption(15, midX, getGuiTop() + 232, 64, 48, StatCollector.translateToLocal("got.gui.fellowships.togglePVP"));
		buttonList.add(buttonPVP);
		buttonHiredFF = new GOTGuiButtonFsOption(16, midX, getGuiTop() + 232, 80, 48, StatCollector.translateToLocal("got.gui.fellowships.toggleHiredFF"));
		buttonList.add(buttonHiredFF);
		buttonMapShow = new GOTGuiButtonFsOption(17, midX, getGuiTop() + 232, 96, 48, StatCollector.translateToLocal("got.gui.fellowships.toggleMapShow"));
		buttonList.add(buttonMapShow);
		buttonOp = new GOTGuiButton(18, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.op"));
		buttonList.add(buttonOp);
		buttonDeop = new GOTGuiButton(19, midX - 100, getGuiTop() + 170, 200, 20, StatCollector.translateToLocal("got.gui.fellowships.deop"));
		buttonList.add(buttonDeop);
		orderedFsOptionButtons.clear();
		orderedFsOptionButtons.add(buttonInvitePlayer);
		orderedFsOptionButtons.add(buttonDisband);
		orderedFsOptionButtons.add(buttonRename);
		orderedFsOptionButtons.add(buttonSetIcon);
		orderedFsOptionButtons.add(buttonMapShow);
		orderedFsOptionButtons.add(buttonPVP);
		orderedFsOptionButtons.add(buttonHiredFF);
		textFieldName = new GuiTextField(fontRendererObj, midX - 80, getGuiTop() + 40, 160, 20);
		textFieldName.setMaxStringLength(40);
		textFieldPlayer = new GuiTextField(fontRendererObj, midX - 80, getGuiTop() + 40, 160, 20);
		textFieldRename = new GuiTextField(fontRendererObj, midX - 80, getGuiTop() + 40, 160, 20);
		textFieldRename.setMaxStringLength(40);
		buttonCreate = new GOTGuiButton(88, getGuiLeft() + getxSize() / 2 - 310, getGuiTop() + 80, 160, 20, StatCollector.translateToLocal("got.gui.fellowships.create"));
		buttonList.add(buttonCreate);
		setGoBack(new GOTGuiButton(89, getGuiLeft() + getxSize() / 2 - 310, getGuiTop() + 110, 160, 20, StatCollector.translateToLocal("got.gui.menuButton")));
		buttonList.add(getGoBack());
	}

	@Override
	public void keyTyped(char c, int i) {
		if (page == Page.CREATE && textFieldName.textboxKeyTyped(c, i) || page == Page.INVITE && textFieldPlayer.textboxKeyTyped(c, i)) {
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
		GOTPacketFellowshipRespondInvite packet = null;
		super.mouseClicked(i, j, k);
		switch (page) {
		case CREATE:
			textFieldName.mouseClicked(i, j, k);
			break;
		case FELLOWSHIP:
			if (mouseOverPlayer != null && mouseOverPlayerRemove) {
				buttonSound();
				page = Page.REMOVE;
				removingPlayer = mouseOverPlayer;
			}
			if (mouseOverPlayer != null && mouseOverPlayerOp) {
				buttonSound();
				page = Page.OP;
				oppingPlayer = mouseOverPlayer;
			}
			if (mouseOverPlayer != null && mouseOverPlayerDeop) {
				buttonSound();
				page = Page.DEOP;
				deoppingPlayer = mouseOverPlayer;
			}
			if (mouseOverPlayer != null && mouseOverPlayerTransfer) {
				buttonSound();
				page = Page.TRANSFER;
				transferringPlayer = mouseOverPlayer;
			}
			break;
		case INVITATIONS:
			if (mouseOverFellowship != null) {
				buttonSound();
				if (mouseOverInviteAccept) {
					packet = new GOTPacketFellowshipRespondInvite(mouseOverFellowship, true);
				}
				if (mouseOverInviteReject) {
					packet = new GOTPacketFellowshipRespondInvite(mouseOverFellowship, false);
				}
				GOTPacketHandler.getNetworkWrapper().sendToServer(packet);
				mouseOverFellowship = null;
			}
			break;
		case INVITE:
			textFieldPlayer.mouseClicked(i, j, k);
			break;
		case LIST:
			if (mouseOverFellowship != null) {
				buttonSound();
				page = Page.FELLOWSHIP;
				viewingFellowship = mouseOverFellowship;
			}
			break;
		case RENAME:
			textFieldRename.mouseClicked(i, j, k);
			break;
		default:
			break;
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
		switch (page) {
		case FELLOWSHIP:
			displayedMembers = viewingFellowship.getMemberNames().size();
			scrollPaneMembers.setHasScrollBar(false);
			if (displayedMembers > 11) {
				displayedMembers = 11;
				scrollPaneMembers.setHasScrollBar(true);
			}
			scrollPaneMembers.setPaneX0(getGuiLeft());
			scrollPaneMembers.setScrollBarX0(getGuiLeft() + scrollBarX);
			scrollPaneMembers.setPaneY0(getGuiTop() + 10 + fontRendererObj.FONT_HEIGHT + 5 + 16 + 10 + fontRendererObj.FONT_HEIGHT + 10);
			scrollPaneMembers.setPaneY1(scrollPaneMembers.getPaneY0() + (fontRendererObj.FONT_HEIGHT + 5) * displayedMembers);
			break;
		case INVITATIONS:
			displayedInvites = allFellowshipInvites.size();
			scrollPaneInvites.setHasScrollBar(false);
			if (displayedInvites > 15) {
				displayedInvites = 15;
				scrollPaneInvites.setHasScrollBar(true);
			}
			scrollPaneInvites.setPaneX0(getGuiLeft());
			scrollPaneInvites.setScrollBarX0(getGuiLeft() + scrollBarX);
			scrollPaneInvites.setPaneY0(getGuiTop() + 10 + fontRendererObj.FONT_HEIGHT + 10);
			scrollPaneInvites.setPaneY1(scrollPaneInvites.getPaneY0() + (fontRendererObj.FONT_HEIGHT + 5) * displayedInvites);
			scrollPaneInvites.mouseDragScroll(i, j);
			break;
		case LIST:
			displayedFellowshipsLeading = allFellowshipsLeading.size();
			displayedFellowshipsOther = allFellowshipsOther.size();
			scrollPaneLeading.setHasScrollBar(false);
			scrollPaneOther.setHasScrollBar(false);
			while (displayedFellowshipsLeading + displayedFellowshipsOther > 12) {
				if (displayedFellowshipsOther >= displayedFellowshipsLeading) {
					--displayedFellowshipsOther;
					scrollPaneOther.setHasScrollBar(true);
				} else {
					--displayedFellowshipsLeading;
					scrollPaneLeading.setHasScrollBar(true);
				}
			}
			scrollPaneLeading.setPaneX0(getGuiLeft());
			scrollPaneLeading.setScrollBarX0(getGuiLeft() + scrollBarX);
			scrollPaneLeading.setPaneY0(getGuiTop() + 10);
			scrollPaneLeading.setPaneY1(scrollPaneLeading.getPaneY0() + fontRendererObj.FONT_HEIGHT + 10 + (fontRendererObj.FONT_HEIGHT + 5) * displayedFellowshipsLeading);
			scrollPaneLeading.mouseDragScroll(i, j);
			scrollPaneOther.setPaneX0(getGuiLeft());
			scrollPaneOther.setScrollBarX0(getGuiLeft() + scrollBarX);
			scrollPaneOther.setPaneY0(scrollPaneLeading.getPaneY1() + 5);
			scrollPaneOther.setPaneY1(scrollPaneOther.getPaneY0() + fontRendererObj.FONT_HEIGHT + 10 + (fontRendererObj.FONT_HEIGHT + 5) * displayedFellowshipsOther);
			scrollPaneOther.mouseDragScroll(i, j);
			break;
		default:
			scrollPaneMembers.setHasScrollBar(false);
			break;
		}
		scrollPaneMembers.mouseDragScroll(i, j);
	}

	private List<GOTFellowshipClient> sortFellowshipsForDisplay(List<GOTFellowshipClient> list) {
		ArrayList<GOTFellowshipClient> sorted = new ArrayList<>(list);
		Collections.sort(sorted, (fs1, fs2) -> {
			int count2;
			int count1 = fs1.getMemberCount();
			count2 = fs2.getMemberCount();
			if (count1 == count2) {
				return fs1.getName().toLowerCase().compareTo(fs2.getName().toLowerCase());
			}
			return -Integer.compare(count1, count2);
		});
		return sorted;
	}

	private List<String> sortMemberNamesForDisplay(GOTFellowshipClient fs) {
		ArrayList<String> members = new ArrayList<>(fs.getMemberNames());
		Collections.sort(members, (player1, player2) -> {
			boolean online2;
			boolean admin1 = fs.isAdmin(player1);
			boolean admin2 = fs.isAdmin(player2);
			boolean online1 = GOTGuiFellowships.isPlayerOnline(player1);
			online2 = GOTGuiFellowships.isPlayerOnline(player2);
			if (online1 == online2) {
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

	public static ResourceLocation getIconsTextures() {
		return iconsTextures;
	}

	private static boolean isPlayerOnline(String player) {
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

	public static void setIconsTextures(ResourceLocation iconsTextures) {
		GOTGuiFellowships.iconsTextures = iconsTextures;
	}

	public enum Page {
		LIST, CREATE, FELLOWSHIP, INVITE, DISBAND, LEAVE, REMOVE, OP, DEOP, TRANSFER, RENAME, INVITATIONS;

	}

}