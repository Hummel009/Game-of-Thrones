package got.client.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.other.GOTEntityNPC;
import got.common.inventory.GOTContainerAnvil;
import got.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTGuiAnvil extends GuiContainer {
	public static ResourceLocation anvilTexture = new ResourceLocation("got:gui/anvil.png");
	public static int[] colorCodes = new int[16];
	static {
		for (int i = 0; i < 16; ++i) {
			int baseBrightness = (i >> 3 & 1) * 85;
			int r = (i >> 2 & 1) * 170 + baseBrightness;
			int g = (i >> 1 & 1) * 170 + baseBrightness;
			int b = (i >> 0 & 1) * 170 + baseBrightness;
			if (i == 6) {
				r += 85;
			}
			GOTGuiAnvil.colorCodes[i] = (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
		}
	}
	public GOTContainerAnvil theAnvil;
	public ItemStack prevItemStack;
	public GuiButton buttonReforge;
	public GuiButton buttonEngraveOwner;

	public GuiTextField textFieldRename;

	public GOTGuiAnvil(EntityPlayer entityplayer, GOTEntityNPC npc) {
		super(new GOTContainerAnvil(entityplayer, npc));
		theAnvil = (GOTContainerAnvil) inventorySlots;
		xSize = 176;
		ySize = 198;
	}

	public GOTGuiAnvil(EntityPlayer entityplayer, int i, int j, int k) {
		super(new GOTContainerAnvil(entityplayer, i, j, k));
		theAnvil = (GOTContainerAnvil) inventorySlots;
		xSize = 176;
		ySize = 198;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonReforge) {
				ItemStack inputItem2 = theAnvil.invInput.getStackInSlot(0);
				if (inputItem2 != null && theAnvil.reforgeCost > 0 && theAnvil.hasMaterialOrCoinAmount(theAnvil.reforgeCost)) {
					GOTPacketAnvilReforge packet = new GOTPacketAnvilReforge();
					GOTPacketHandler.networkWrapper.sendToServer(packet);
				}
			} else if (button == buttonEngraveOwner && (theAnvil.invInput.getStackInSlot(0)) != null && theAnvil.engraveOwnerCost > 0 && theAnvil.hasMaterialOrCoinAmount(theAnvil.engraveOwnerCost)) {
				GOTPacketAnvilEngraveOwner packet = new GOTPacketAnvilEngraveOwner();
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			}
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(anvilTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theAnvil.isTrader) {
			drawTexturedModalRect(guiLeft + 75, guiTop + 69, 176, 21, 18, 18);
		}
		drawTexturedModalRect(guiLeft + 59, guiTop + 20, 0, ySize + (theAnvil.invInput.getStackInSlot(0) != null ? 0 : 16), 110, 16);
		if (theAnvil.invOutput.getStackInSlot(0) == null) {
			boolean flag = false;
			for (int l = 0; l < theAnvil.invInput.getSizeInventory(); ++l) {
				if (theAnvil.invInput.getStackInSlot(l) != null) {
					flag = true;
					break;
				}
			}
			if (flag) {
				drawTexturedModalRect(guiLeft + 99, guiTop + 56, xSize, 0, 28, 21);
			}
		}
		if (buttonReforge.visible && buttonEngraveOwner.visible) {
			drawTexturedModalRect(guiLeft + 5, guiTop + 78, 176, 99, 40, 20);
		} else if (buttonReforge.visible) {
			drawTexturedModalRect(guiLeft + 25, guiTop + 78, 176, 79, 20, 20);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		GL11.glDisable(2896);
		GL11.glDisable(3042);
		String s = theAnvil.isTrader ? StatCollector.translateToLocal("got.container.smith") : StatCollector.translateToLocal("got.container.anvil");
		fontRendererObj.drawString(s, 60, 6, 4210752);
		boolean reforge = buttonReforge.enabled && buttonReforge.func_146115_a();
		boolean engraveOwner = buttonEngraveOwner.enabled && buttonEngraveOwner.func_146115_a();
		String costText = null;
		int color = 8453920;
		ItemStack inputItem = theAnvil.invInput.getStackInSlot(0);
		ItemStack outputItem = theAnvil.invOutput.getStackInSlot(0);
		if (inputItem != null) {
			if (reforge && theAnvil.reforgeCost > 0) {
				costText = StatCollector.translateToLocalFormatted("got.container.anvil.reforgeCost", theAnvil.reforgeCost);
				if (!theAnvil.hasMaterialOrCoinAmount(theAnvil.reforgeCost)) {
					color = 16736352;
				}
			} else if (engraveOwner && theAnvil.engraveOwnerCost > 0) {
				costText = StatCollector.translateToLocalFormatted("got.container.anvil.engraveOwnerCost", theAnvil.engraveOwnerCost);
				if (!theAnvil.hasMaterialOrCoinAmount(theAnvil.engraveOwnerCost)) {
					color = 16736352;
				}
			} else if (theAnvil.materialCost > 0 && outputItem != null) {
				costText = theAnvil.isTrader ? StatCollector.translateToLocalFormatted("got.container.smith.cost", theAnvil.materialCost) : StatCollector.translateToLocalFormatted("got.container.anvil.cost", theAnvil.materialCost);
				if (!theAnvil.getSlotFromInventory(theAnvil.invOutput, 0).canTakeStack(mc.thePlayer)) {
					color = 16736352;
				}
			}
		}
		if (costText != null) {
			int colorF = 0xFF000000 | (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
			int x = xSize - 8 - fontRendererObj.getStringWidth(costText);
			int y = 94;
			if (fontRendererObj.getUnicodeFlag()) {
				Gui.drawRect(x - 3, y - 2, xSize - 7, y + 10, -16777216);
				Gui.drawRect(x - 2, y - 1, xSize - 8, y + 9, -12895429);
			} else {
				fontRendererObj.drawString(costText, x, y + 1, colorF);
				fontRendererObj.drawString(costText, x + 1, y, colorF);
				fontRendererObj.drawString(costText, x + 1, y + 1, colorF);
			}
			fontRendererObj.drawString(costText, x, y, color);
		}
		GL11.glEnable(2896);
		if (theAnvil.clientReforgeTime > 0) {
			float f = theAnvil.clientReforgeTime / 40.0f;
			int alpha = (int) (f * 255.0f);
			alpha = MathHelper.clamp_int(alpha, 0, 255);
			int overlayColor = 0xFFFFFF | alpha << 24;
			Slot slot = theAnvil.getSlotFromInventory(theAnvil.invInput, 0);
			Gui.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, overlayColor);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		float z;
		Object tooltip;
		ItemStack inputItem = theAnvil.invInput.getStackInSlot(0);
		boolean canReforge = inputItem != null && GOTEnchantmentHelper.isReforgeable(inputItem) && theAnvil.reforgeCost > 0;
		boolean canEngrave = inputItem != null && GOTEnchantmentHelper.isReforgeable(inputItem) && theAnvil.engraveOwnerCost > 0;
		buttonReforge.visible = buttonReforge.enabled = canReforge;
		buttonEngraveOwner.enabled = canEngrave && theAnvil.canEngraveNewOwner(inputItem, mc.thePlayer);
		buttonEngraveOwner.visible = buttonEngraveOwner.enabled;
		super.drawScreen(i, j, f);
		if (buttonReforge.visible && buttonReforge.func_146115_a()) {
			z = zLevel;
			tooltip = StatCollector.translateToLocal("got.container.anvil.reforge");
			drawCreativeTabHoveringText((String) tooltip, i - 12, j + 24);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (buttonEngraveOwner.visible && buttonEngraveOwner.func_146115_a()) {
			z = zLevel;
			tooltip = StatCollector.translateToLocal("got.container.anvil.engraveOwner");
			drawCreativeTabHoveringText((String) tooltip, i - fontRendererObj.getStringWidth((String) tooltip), j + 24);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		GL11.glDisable(2896);
		GL11.glDisable(3042);
		List<EnumChatFormatting> itemNameFormatting = theAnvil.getActiveItemNameFormatting();
		for (EnumChatFormatting formatting : itemNameFormatting) {
			int formattingID = formatting.ordinal();
			if ((formatting.isColor() && (formattingID < colorCodes.length))) {
				int color = colorCodes[formattingID];
				textFieldRename.setTextColor(color);
			}
		}
		textFieldRename.drawTextBox();
		textFieldRename.setTextColor(-1);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonReforge = new GOTGuiButtonReforge(0, guiLeft + 25, guiTop + 78, 176, 39);
		buttonEngraveOwner = new GOTGuiButtonReforge(1, guiLeft + 5, guiTop + 78, 176, 59);
		buttonList.add(buttonReforge);
		buttonList.add(buttonEngraveOwner);
		Keyboard.enableRepeatEvents(true);
		textFieldRename = new GuiTextField(fontRendererObj, guiLeft + 62, guiTop + 24, 103, 12);
		textFieldRename.setTextColor(-1);
		textFieldRename.setDisabledTextColour(-1);
		textFieldRename.setEnableBackgroundDrawing(false);
		textFieldRename.setMaxStringLength(40);
		prevItemStack = null;
	}

	@Override
	public void keyTyped(char c, int i) {
		if (textFieldRename.textboxKeyTyped(c, i)) {
			renameItem(textFieldRename.getText());
		} else {
			super.keyTyped(c, i);
		}
	}

	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		textFieldRename.mouseClicked(i, j, k);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	public void renameItem(String rename) {
		ItemStack itemstack = theAnvil.invInput.getStackInSlot(0);
		if (itemstack != null && !itemstack.hasDisplayName()) {
			String displayNameStripped = GOTContainerAnvil.stripFormattingCodes(itemstack.getDisplayName());
			String renameStripped = GOTContainerAnvil.stripFormattingCodes(rename);
			if (renameStripped.equals(displayNameStripped)) {
				rename = "";
			}
		}
		theAnvil.updateItemName(rename);
		GOTPacketAnvilRename packet = new GOTPacketAnvilRename(rename);
		GOTPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		ItemStack itemstack;
		super.updateScreen();
		if (theAnvil.clientReforgeTime > 0) {
			--theAnvil.clientReforgeTime;
		}
		if ((itemstack = theAnvil.invInput.getStackInSlot(0)) != prevItemStack) {
			prevItemStack = itemstack;
			String textFieldText = itemstack == null ? "" : GOTContainerAnvil.stripFormattingCodes(itemstack.getDisplayName());
			boolean textFieldEnabled = itemstack != null;
			textFieldRename.setText(textFieldText);
			textFieldRename.setEnabled(textFieldEnabled);
			if (itemstack != null) {
				renameItem(textFieldText);
			}
		}
	}
}