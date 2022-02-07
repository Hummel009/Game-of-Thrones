package got.client.gui;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.mojang.authlib.GameProfile;

import got.common.GOTBannerProtection;
import got.common.entity.other.*;
import got.common.faction.GOTAlignmentValues;
import got.common.fellowship.GOTFellowshipProfile;
import got.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class GOTGuiBanner extends GOTGuiScreenBase {
	private static ResourceLocation bannerTexture = new ResourceLocation("got:textures/gui/banner_edit.png");
	private GOTEntityBanner theBanner;
	private int xSize = 200;
	private int ySize = 250;
	private int guiLeft;
	private int guiTop;
	private GuiButton buttonMode;
	private GOTGuiButtonBanner buttonSelfProtection;
	private GuiButton buttonAddSlot;
	private GuiButton buttonRemoveSlot;
	private GOTGuiButtonBanner buttonDefaultPermissions;
	private GuiTextField alignmentField;
	private GuiTextField[] allowedPlayers = {};
	private boolean[] invalidUsernames = {};
	private boolean[] validatedUsernames = {};
	private boolean[] checkUsernames = {};
	private float currentScroll = 0.0f;
	private boolean isScrolling = false;
	private boolean wasMouseDown;
	private int scrollBarWidth = 12;
	private int scrollBarHeight = 132;
	private int scrollBarX = 181;
	private int scrollBarY = 68;
	private int scrollBarBorder = 1;
	private int scrollWidgetWidth = 10;
	private int scrollWidgetHeight = 17;
	private int permIconX = 3;
	private int permIconY = 0;
	private int permIconWidth = 10;
	private int permissionsMouseoverIndex = -1;
	private int permissionsMouseoverY = -1;
	private int permWindowBorder = 4;
	private int permWindowWidth = 150;
	private int permWindowHeight = 70;
	private int permissionsOpenIndex = -1;
	private int permissionsOpenY = -1;
	private GOTBannerProtection.Permission mouseOverPermission = null;
	private boolean defaultPermissionsOpen = false;

	public GOTGuiBanner(GOTEntityBanner banner) {
		setTheBanner(banner);
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonMode) {
				getTheBanner().setPlayerSpecificProtection(!getTheBanner().isPlayerSpecificProtection());
			}
			if (button == buttonSelfProtection) {
				getTheBanner().setSelfProtection(!getTheBanner().isSelfProtection());
			}
			if (button == buttonAddSlot) {
				getTheBanner().resizeWhitelist(getTheBanner().getWhitelistLength() + 1);
				refreshWhitelist();
			}
			if (button == buttonRemoveSlot) {
				getTheBanner().resizeWhitelist(getTheBanner().getWhitelistLength() - 1);
				refreshWhitelist();
			}
			if (button == buttonDefaultPermissions) {
				defaultPermissionsOpen = true;
			}
		}
	}

	public void buttonSound() {
		buttonMode.func_146113_a(mc.getSoundHandler());
	}

	private boolean canScroll() {
		return true;
	}

	public void checkUsernameValid(int index) {
		GuiTextField textBox = allowedPlayers[index];
		String username = textBox.getText();
		if (!StringUtils.isBlank(username) && !invalidUsernames[index]) {
			GOTPacketBannerRequestInvalidName packet = new GOTPacketBannerRequestInvalidName(getTheBanner(), index, username);
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	public void drawPermissionsWindow(int i, int j, int windowX, int windowY, String boxTitle, String boxSubtitle, Function<GOTBannerProtection.Permission, Boolean> getEnabled, boolean includeFull) {
		Gui.drawRect(windowX, windowY, windowX + permWindowWidth, windowY + permWindowHeight, -1442840576);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		fontRendererObj.drawString(boxTitle, windowX + 4, windowY + 4, 16777215);
		fontRendererObj.drawString(boxSubtitle, windowX + 4, windowY + 14, 11184810);
		mc.getTextureManager().bindTexture(getBannerTexture());
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		int x = windowX + 4;
		int y = windowY + 32;
		mouseOverPermission = null;
		for (GOTBannerProtection.Permission p : GOTBannerProtection.Permission.values()) {
			if (!includeFull && p == GOTBannerProtection.Permission.FULL) {
				continue;
			}
			if (i >= x && i < x + 10 && j >= y && j < y + 10) {
				mouseOverPermission = p;
			}
			this.drawTexturedModalRect(x, y, 200 + (getEnabled.apply(p) ? 0 : 20) + (mouseOverPermission == p ? 10 : 0), 160 + p.ordinal() * 10, 10, 10);
			x += 14;
			if (p != GOTBannerProtection.Permission.FULL) {
				continue;
			}
			x += 4;
		}
		if (mouseOverPermission != null) {
			String permName = StatCollector.translateToLocal("got.gui.bannerEdit.perm." + mouseOverPermission.codeName);
			fontRendererObj.drawSplitString(permName, windowX + 4, windowY + 47, permWindowWidth - permWindowBorder * 2, 16777215);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		int windowY;
		String s;
		permissionsMouseoverIndex = -1;
		permissionsMouseoverY = -1;
		mouseOverPermission = null;
		setupScrollBar(i, j);
		alignmentField.setVisible(false);
		alignmentField.setEnabled(false);
		for (GuiTextField textBox : allowedPlayers) {
			textBox.setVisible(false);
			textBox.setEnabled(false);
		}
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(getBannerTexture());
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		String title = StatCollector.translateToLocal("got.gui.bannerEdit.title");
		fontRendererObj.drawString(title, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(title) / 2, guiTop + 6, 4210752);
		if (getTheBanner().isPlayerSpecificProtection()) {
			buttonMode.displayString = StatCollector.translateToLocal("got.gui.bannerEdit.protectionMode.playerSpecific");
			s = StatCollector.translateToLocal("got.gui.bannerEdit.protectionMode.playerSpecific.desc.1");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46, 4210752);
			s = StatCollector.translateToLocal("got.gui.bannerEdit.protectionMode.playerSpecific.desc.2");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT, 4210752);
			s = GOTFellowshipProfile.getFellowshipCodeHint();
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 206, 4210752);
			int start = 0 + Math.round(currentScroll * (allowedPlayers.length - 6));
			int end = start + 6 - 1;
			start = Math.max(start, 0);
			end = Math.min(end, allowedPlayers.length - 1);
			for (int index = start; index <= end; ++index) {
				int displayIndex = index - start;
				GuiTextField textBox = allowedPlayers[index];
				textBox.setVisible(true);
				textBox.setEnabled(index != 0);
				textBox.xPosition = guiLeft + xSize / 2 - 70;
				textBox.yPosition = guiTop + 70 + displayIndex * (textBox.height + 4);
				textBox.drawTextBox();
				String number = index + 1 + ".";
				fontRendererObj.drawString(number, guiLeft + 24 - fontRendererObj.getStringWidth(number), textBox.yPosition + 6, 4210752);
				if (index <= 0 || !validatedUsernames[index]) {
					continue;
				}
				mc.getTextureManager().bindTexture(getBannerTexture());
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				int permX = textBox.xPosition + textBox.width + permIconX;
				int permY = textBox.yPosition + permIconY;
				boolean mouseOver = i >= permX && i < permX + permIconWidth && j >= permY && j < permY + permIconWidth;
				this.drawTexturedModalRect(permX, permY, 200 + (mouseOver ? permIconWidth : 0), 150, permIconWidth, permIconWidth);
				if (!mouseOver) {
					continue;
				}
				permissionsMouseoverIndex = index;
				permissionsMouseoverY = textBox.yPosition;
			}
			if (hasScrollBar()) {
				mc.getTextureManager().bindTexture(getBannerTexture());
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				this.drawTexturedModalRect(guiLeft + scrollBarX, guiTop + scrollBarY, 200, 0, scrollBarWidth, scrollBarHeight);
				if (canScroll()) {
					int scroll = (int) (currentScroll * (scrollBarHeight - scrollBarBorder * 2 - scrollWidgetHeight));
					this.drawTexturedModalRect(guiLeft + scrollBarX + scrollBarBorder, guiTop + scrollBarY + scrollBarBorder + scroll, 212, 0, scrollWidgetWidth, scrollWidgetHeight);
				}
			}
		} else {
			permissionsOpenY = -1;
			permissionsOpenIndex = -1;
			buttonMode.displayString = StatCollector.translateToLocal("got.gui.bannerEdit.protectionMode.faction");
			s = StatCollector.translateToLocalFormatted("got.gui.bannerEdit.protectionMode.faction.desc.1");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46, 4210752);
			s = StatCollector.translateToLocalFormatted("got.gui.bannerEdit.protectionMode.faction.desc.2", Float.valueOf(getTheBanner().getAlignmentProtection()), getTheBanner().getBannerType().faction.factionName());
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT, 4210752);
			s = StatCollector.translateToLocal("got.gui.bannerEdit.protectionMode.faction.desc.3");
			fontRendererObj.drawString(s, guiLeft + xSize / 2 - fontRendererObj.getStringWidth(s) / 2, guiTop + 46 + fontRendererObj.FONT_HEIGHT * 2, 4210752);
			s = StatCollector.translateToLocal("got.gui.bannerEdit.protectionMode.faction.alignment");
			fontRendererObj.drawString(s, alignmentField.xPosition, alignmentField.yPosition - fontRendererObj.FONT_HEIGHT - 3, 4210752);
			alignmentField.setVisible(true);
			alignmentField.setEnabled(true);
			alignmentField.drawTextBox();
		}
		if (permissionsOpenIndex >= 0) {
			int windowX = guiLeft + xSize + permWindowBorder;
			windowY = permissionsOpenY;
			String username = allowedPlayers[permissionsOpenIndex].getText();
			boolean isFellowship = GOTFellowshipProfile.hasFellowshipCode(username);
			if (isFellowship) {
				username = GOTFellowshipProfile.stripFellowshipCode(username);
			}
			String boxTitle = StatCollector.translateToLocal(isFellowship ? "got.gui.bannerEdit.perms.fellowship" : "got.gui.bannerEdit.perms.player");
			String boxSubtitle = StatCollector.translateToLocalFormatted("got.gui.bannerEdit.perms.name", username);
			Function<GOTBannerProtection.Permission, Boolean> getEnabled = new Function<GOTBannerProtection.Permission, Boolean>() {

				@Override
				public Boolean apply(GOTBannerProtection.Permission p) {
					return getTheBanner().getWhitelistEntry(permissionsOpenIndex).isPermissionEnabled(p);
				}
			};
			drawPermissionsWindow(i, j, windowX, windowY, boxTitle, boxSubtitle, getEnabled, true);
		}
		if (defaultPermissionsOpen) {
			int windowX = guiLeft + xSize + permWindowBorder;
			windowY = guiTop + ySize - permWindowHeight;
			String boxTitle = StatCollector.translateToLocal("got.gui.bannerEdit.perms.default");
			String boxSubtitle = StatCollector.translateToLocalFormatted("got.gui.bannerEdit.perms.allPlayers");
			Function<GOTBannerProtection.Permission, Boolean> getEnabled = new Function<GOTBannerProtection.Permission, Boolean>() {

				@Override
				public Boolean apply(GOTBannerProtection.Permission p) {
					return getTheBanner().hasDefaultPermission(p);
				}
			};
			drawPermissionsWindow(i, j, windowX, windowY, boxTitle, boxSubtitle, getEnabled, false);
		}
		super.drawScreen(i, j, f);
		if (buttonSelfProtection.func_146115_a()) {
			float z = zLevel;
			String tooltip = StatCollector.translateToLocal("got.gui.bannerEdit.selfProtection." + (buttonSelfProtection.isActivated() ? "on" : "off"));
			drawCreativeTabHoveringText(tooltip, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (buttonDefaultPermissions.func_146115_a()) {
			float z = zLevel;
			String tooltip = StatCollector.translateToLocal("got.gui.bannerEdit.perms.default");
			drawCreativeTabHoveringText(tooltip, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (permissionsMouseoverIndex >= 0) {
			float z = zLevel;
			String username = allowedPlayers[permissionsMouseoverIndex].getText();
			boolean isFellowship = GOTFellowshipProfile.hasFellowshipCode(username);
			String tooltip = StatCollector.translateToLocal(isFellowship ? "got.gui.bannerEdit.perms.fellowship" : "got.gui.bannerEdit.perms.player");
			drawCreativeTabHoveringText(tooltip, i, j);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
	}

	public GOTEntityBanner getTheBanner() {
		return theBanner;
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0 && canScroll()) {
			int j = allowedPlayers.length - 6;
			if (i > 0) {
				i = 1;
			}
			if (i < 0) {
				i = -1;
			}
			currentScroll = (float) (currentScroll - (double) i / (double) j);
			if (currentScroll < 0.0f) {
				currentScroll = 0.0f;
			}
			if (currentScroll > 1.0f) {
				currentScroll = 1.0f;
			}
		}
	}

	private boolean hasScrollBar() {
		return getTheBanner().isPlayerSpecificProtection();
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonMode = new GuiButton(0, guiLeft + xSize / 2 - 80, guiTop + 20, 160, 20, "");
		buttonList.add(buttonMode);
		buttonSelfProtection = new GOTGuiButtonBanner(1, guiLeft + xSize / 2 - 24, guiTop + 224, 212, 100);
		buttonList.add(buttonSelfProtection);
		buttonAddSlot = new GOTGuiButtonBannerWhitelistSlots(0, guiLeft + 179, guiTop + 202);
		buttonList.add(buttonAddSlot);
		buttonRemoveSlot = new GOTGuiButtonBannerWhitelistSlots(1, guiLeft + 187, guiTop + 202);
		buttonList.add(buttonRemoveSlot);
		buttonDefaultPermissions = new GOTGuiButtonBanner(2, guiLeft + xSize / 2 + 8, guiTop + 224, 200, 134);
		buttonList.add(buttonDefaultPermissions);
		buttonDefaultPermissions.setActivated(true);
		alignmentField = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 70, guiTop + 100, 130, 18);
		alignmentField.setText(String.valueOf(getTheBanner().getAlignmentProtection()));
		alignmentField.setEnabled(false);
		refreshWhitelist();
		for (int i = 0; i < allowedPlayers.length; ++i) {
			String name;
			GuiTextField textBox = allowedPlayers[i];
			textBox.setTextColor(16777215);
			GameProfile profile = getTheBanner().getWhitelistedPlayer(i);
			if (profile != null && !StringUtils.isBlank(name = profile.getName())) {
				textBox.setText(name);
				textBox.setTextColor(65280);
				validatedUsernames[i] = true;
			}
			allowedPlayers[i] = textBox;
		}
		allowedPlayers[0].setEnabled(false);
		Arrays.fill(checkUsernames, false);
	}

	@Override
	public void keyTyped(char c, int i) {
		if (alignmentField.getVisible() && alignmentField.textboxKeyTyped(c, i)) {
			return;
		}
		for (int l = 1; l < allowedPlayers.length; ++l) {
			GuiTextField textBox = allowedPlayers[l];
			if (!textBox.getVisible() || !textBox.textboxKeyTyped(c, i)) {
				continue;
			}
			validatedUsernames[l] = false;
			checkUsernames[l] = true;
			textBox.setTextColor(16777215);
			updateWhitelistedPlayer(l, null);
			return;
		}
		if (permissionsOpenIndex >= 0 && (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())) {
			permissionsOpenY = -1;
			permissionsOpenIndex = -1;
			return;
		}
		if (defaultPermissionsOpen && (i == 1 || i == mc.gameSettings.keyBindInventory.getKeyCode())) {
			defaultPermissionsOpen = false;
			return;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		int dx;
		super.mouseClicked(i, j, k);
		if (alignmentField.getVisible()) {
			alignmentField.mouseClicked(i, j, k);
		}
		for (int l = 1; l < allowedPlayers.length; ++l) {
			GuiTextField textBox = allowedPlayers[l];
			if (!textBox.getVisible()) {
				continue;
			}
			textBox.mouseClicked(i, j, k);
			if (!textBox.isFocused() && checkUsernames[l]) {
				checkUsernameValid(l);
				checkUsernames[l] = false;
			}
			if (!textBox.isFocused() || !invalidUsernames[l]) {
				continue;
			}
			invalidUsernames[l] = false;
			textBox.setTextColor(16777215);
			textBox.setText("");
		}
		if (permissionsMouseoverIndex >= 0) {
			permissionsOpenIndex = permissionsMouseoverIndex;
			permissionsOpenY = permissionsMouseoverY;
			permissionsMouseoverIndex = -1;
			permissionsMouseoverY = -1;
			defaultPermissionsOpen = false;
			buttonSound();
			return;
		}
		if (permissionsOpenIndex >= 0) {
			dx = i - (guiLeft + xSize + permWindowBorder);
			int dy = j - permissionsOpenY;
			if (dx < 0 || dx >= permWindowWidth || dy < 0 || dy >= permWindowHeight) {
				permissionsOpenY = -1;
				permissionsOpenIndex = -1;
				return;
			}
			if (mouseOverPermission != null) {
				GOTBannerWhitelistEntry entry = getTheBanner().getWhitelistEntry(permissionsOpenIndex);
				if (mouseOverPermission == GOTBannerProtection.Permission.FULL) {
					if (entry.isPermissionEnabled(mouseOverPermission)) {
						entry.clearPermissions();
					} else {
						entry.clearPermissions();
						entry.addPermission(mouseOverPermission);
					}
				} else if (entry.isPermissionEnabled(mouseOverPermission)) {
					entry.removePermission(mouseOverPermission);
				} else {
					if (entry.isPermissionEnabled(GOTBannerProtection.Permission.FULL)) {
						entry.removePermission(GOTBannerProtection.Permission.FULL);
					}
					entry.addPermission(mouseOverPermission);
				}
				buttonSound();
				return;
			}
		}
		if (defaultPermissionsOpen) {
			dx = i - (guiLeft + xSize + permWindowBorder);
			int dy = j - (guiTop + ySize - permWindowHeight);
			if ((dx < 0 || dx >= permWindowWidth || dy < 0 || dy >= permWindowHeight) && !buttonDefaultPermissions.mousePressed(mc, i, j)) {
				defaultPermissionsOpen = false;
				return;
			}
			if (mouseOverPermission != null) {
				if (getTheBanner().hasDefaultPermission(mouseOverPermission)) {
					getTheBanner().removeDefaultPermission(mouseOverPermission);
				} else {
					getTheBanner().addDefaultPermission(mouseOverPermission);
				}
				sendBannerData(false);
				buttonSound();
			}
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		sendBannerData(true);
	}

	public void refreshWhitelist() {
		int length = getTheBanner().getWhitelistLength();
		GuiTextField[] allowedPlayers_new = new GuiTextField[length];
		boolean[] invalidUsernames_new = new boolean[length];
		boolean[] validatedUsernames_new = new boolean[length];
		boolean[] checkUsernames_new = new boolean[length];
		for (int i = 0; i < length; ++i) {
			allowedPlayers_new[i] = i < allowedPlayers.length ? allowedPlayers[i] : new GuiTextField(fontRendererObj, 0, 0, 130, 18);
			if (i < invalidUsernames.length) {
				invalidUsernames_new[i] = invalidUsernames[i];
			}
			if (i < validatedUsernames.length) {
				validatedUsernames_new[i] = validatedUsernames[i];
			}
			if (i >= checkUsernames.length) {
				continue;
			}
			checkUsernames_new[i] = checkUsernames[i];
		}
		allowedPlayers = allowedPlayers_new;
		invalidUsernames = invalidUsernames_new;
		validatedUsernames = validatedUsernames_new;
		checkUsernames = checkUsernames_new;
	}

	public void sendBannerData(boolean sendWhitelist) {
		GOTPacketEditBanner packet = new GOTPacketEditBanner(getTheBanner());
		packet.playerSpecificProtection = getTheBanner().isPlayerSpecificProtection();
		packet.selfProtection = getTheBanner().isSelfProtection();
		packet.alignmentProtection = getTheBanner().getAlignmentProtection();
		packet.whitelistLength = getTheBanner().getWhitelistLength();
		if (sendWhitelist) {
			String[] whitelistSlots = new String[allowedPlayers.length];
			int[] whitelistPerms = new int[allowedPlayers.length];
			for (int index = 1; index < allowedPlayers.length; ++index) {
				String text = allowedPlayers[index].getText();
				updateWhitelistedPlayer(index, text);
				GOTBannerWhitelistEntry entry = getTheBanner().getWhitelistEntry(index);
				if (entry == null) {
					whitelistSlots[index] = null;
					continue;
				}
				GameProfile profile = entry.profile;
				if (profile == null) {
					whitelistSlots[index] = null;
					continue;
				}
				String username = profile.getName();
				if (StringUtils.isBlank(username)) {
					whitelistSlots[index] = null;
					continue;
				}
				whitelistSlots[index] = username;
				whitelistPerms[index] = entry.encodePermBitFlags();
			}
			packet.whitelistSlots = whitelistSlots;
			packet.whitelistPerms = whitelistPerms;
		}
		packet.defaultPerms = getTheBanner().getDefaultPermBitFlags();
		GOTPacketHandler.networkWrapper.sendToServer(packet);
	}

	public void setTheBanner(GOTEntityBanner theBanner) {
		this.theBanner = theBanner;
	}

	public void setupScrollBar(int i, int j) {
		boolean isMouseDown = Mouse.isButtonDown(0);
		int i1 = guiLeft + scrollBarX;
		int j1 = guiTop + scrollBarY;
		int i2 = i1 + scrollBarWidth;
		int j2 = j1 + scrollBarHeight;
		if (!wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
			isScrolling = canScroll();
		}
		if (!isMouseDown) {
			isScrolling = false;
		}
		wasMouseDown = isMouseDown;
		if (isScrolling) {
			currentScroll = (j - j1 - scrollWidgetHeight / 2.0f) / ((float) (j2 - j1) - (float) scrollWidgetHeight);
			if (currentScroll < 0.0f) {
				currentScroll = 0.0f;
			}
			if (currentScroll > 1.0f) {
				currentScroll = 1.0f;
			}
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		buttonSelfProtection.setActivated(getTheBanner().isSelfProtection());
		buttonAddSlot.visible = buttonRemoveSlot.visible = getTheBanner().isPlayerSpecificProtection();
		buttonAddSlot.enabled = getTheBanner().getWhitelistLength() < GOTEntityBanner.WHITELIST_MAX;
		buttonRemoveSlot.enabled = getTheBanner().getWhitelistLength() > GOTEntityBanner.WHITELIST_MIN;
		alignmentField.updateCursorCounter();
		alignmentField.setVisible(!getTheBanner().isPlayerSpecificProtection());
		alignmentField.setEnabled(alignmentField.getVisible());
		if (alignmentField.getVisible() && !alignmentField.isFocused()) {
			float prevAlignment = getTheBanner().getAlignmentProtection();
			float alignment = GOTAlignmentValues.parseDisplayedAlign(alignmentField.getText());
			alignment = MathHelper.clamp_float(alignment, GOTEntityBanner.ALIGNMENT_PROTECTION_MIN, GOTEntityBanner.ALIGNMENT_PROTECTION_MAX);
			getTheBanner().setAlignmentProtection(alignment);
			alignmentField.setText(GOTAlignmentValues.formatAlignForDisplay(alignment));
			if (alignment != prevAlignment) {
				sendBannerData(false);
			}
		}
		for (GuiTextField textBox : allowedPlayers) {
			textBox.updateCursorCounter();
		}
	}

	public void updateWhitelistedPlayer(int index, String username) {
		GOTBannerWhitelistEntry prevEntry = getTheBanner().getWhitelistEntry(index);
		int prevPerms = -1;
		if (prevEntry != null) {
			prevPerms = prevEntry.encodePermBitFlags();
		}
		if (StringUtils.isBlank(username)) {
			getTheBanner().whitelistPlayer(index, null);
		} else {
			if (GOTFellowshipProfile.hasFellowshipCode(username)) {
				String fsName = GOTFellowshipProfile.stripFellowshipCode(username);
				if (StringUtils.isBlank(fsName)) {
					getTheBanner().whitelistPlayer(index, null);
				} else {
					getTheBanner().whitelistPlayer(index, new GOTFellowshipProfile(getTheBanner(), null, fsName));
				}
			} else {
				getTheBanner().whitelistPlayer(index, new GameProfile(null, username));
			}
			if (prevPerms >= 0) {
				getTheBanner().getWhitelistEntry(index).decodePermBitFlags(prevPerms);
			}
		}
	}

	public void validateUsername(int index, String prevText, boolean valid) {
		GuiTextField textBox = allowedPlayers[index];
		String username = textBox.getText();
		if (username.equals(prevText)) {
			if (valid) {
				validatedUsernames[index] = true;
				invalidUsernames[index] = false;
				textBox.setTextColor(65280);
				updateWhitelistedPlayer(index, username);
			} else {
				invalidUsernames[index] = true;
				validatedUsernames[index] = false;
				textBox.setTextColor(16711680);
				textBox.setText(StatCollector.translateToLocal("got.gui.bannerEdit.invalidUsername"));
				updateWhitelistedPlayer(index, null);
			}
		}
	}

	public static ResourceLocation getBannerTexture() {
		return bannerTexture;
	}

	public static void setBannerTexture(ResourceLocation bannerTexture) {
		GOTGuiBanner.bannerTexture = bannerTexture;
	}

}
