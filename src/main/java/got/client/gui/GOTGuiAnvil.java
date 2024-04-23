package got.client.gui;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.other.GOTEntityNPC;
import got.common.inventory.GOTContainerAnvil;
import got.common.network.GOTPacketAnvilEngraveOwner;
import got.common.network.GOTPacketAnvilReforge;
import got.common.network.GOTPacketAnvilRename;
import got.common.network.GOTPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GOTGuiAnvil extends GuiContainer {
	public static final ResourceLocation ANVIL_TEXTURE = new ResourceLocation("got:textures/gui/anvil.png");

	private static final int[] COLOR_CODES = new int[16];

	static {
		for (int i = 0; i < 16; ++i) {
			int baseBrightness = (i >> 3 & 1) * 85;
			int r = (i >> 2 & 1) * 170 + baseBrightness;
			int g = (i >> 1 & 1) * 170 + baseBrightness;
			int b = (i & 1) * 170 + baseBrightness;
			if (i == 6) {
				r += 85;
			}
			COLOR_CODES[i] = (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
		}
	}

	private final GOTContainerAnvil theAnvil;

	private GuiTextField textFieldRename;
	private GuiButton buttonReforge;
	private GuiButton buttonEngraveOwner;
	private ItemStack prevItemStack;

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
				ItemStack inputItem2 = theAnvil.getInvInput().getStackInSlot(0);
				if (inputItem2 != null && theAnvil.getReforgeCost() > 0 && theAnvil.hasMaterialOrCoinAmount(theAnvil.getReforgeCost())) {
					IMessage packet = new GOTPacketAnvilReforge();
					GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
				}
			} else if (button == buttonEngraveOwner && theAnvil.getInvInput().getStackInSlot(0) != null && theAnvil.getEngraveOwnerCost() > 0 && theAnvil.hasMaterialOrCoinAmount(theAnvil.getEngraveOwnerCost())) {
				IMessage packet = new GOTPacketAnvilEngraveOwner();
				GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
			}
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(ANVIL_TEXTURE);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theAnvil.isTrader()) {
			drawTexturedModalRect(guiLeft + 75, guiTop + 69, 176, 21, 18, 18);
		}
		drawTexturedModalRect(guiLeft + 59, guiTop + 20, 0, ySize + (theAnvil.getInvInput().getStackInSlot(0) != null ? 0 : 16), 110, 16);
		if (theAnvil.getInvOutput().getStackInSlot(0) == null) {
			boolean flag = false;
			for (int l = 0; l < theAnvil.getInvInput().getSizeInventory(); ++l) {
				if (theAnvil.getInvInput().getStackInSlot(l) != null) {
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
		String s = theAnvil.isTrader() ? StatCollector.translateToLocal("got.container.smith") : StatCollector.translateToLocal("got.container.anvil");
		fontRendererObj.drawString(s, 60, 6, 4210752);
		boolean reforge = buttonReforge.enabled && buttonReforge.func_146115_a();
		boolean engraveOwner = buttonEngraveOwner.enabled && buttonEngraveOwner.func_146115_a();
		String costText = null;
		int color = 8453920;
		ItemStack inputItem = theAnvil.getInvInput().getStackInSlot(0);
		ItemStack outputItem = theAnvil.getInvOutput().getStackInSlot(0);
		if (inputItem != null) {
			if (reforge && theAnvil.getReforgeCost() > 0) {
				costText = StatCollector.translateToLocalFormatted("got.container.anvil.reforgeCost", theAnvil.getReforgeCost());
				if (!theAnvil.hasMaterialOrCoinAmount(theAnvil.getReforgeCost())) {
					color = 16736352;
				}
			} else if (engraveOwner && theAnvil.getEngraveOwnerCost() > 0) {
				costText = StatCollector.translateToLocalFormatted("got.container.anvil.engraveOwnerCost", theAnvil.getEngraveOwnerCost());
				if (!theAnvil.hasMaterialOrCoinAmount(theAnvil.getEngraveOwnerCost())) {
					color = 16736352;
				}
			} else if (theAnvil.getMaterialCost() > 0 && outputItem != null) {
				costText = StatCollector.translateToLocalFormatted(theAnvil.isTrader() ? "got.container.smith.cost" : "got.container.anvil.cost", theAnvil.getMaterialCost());
				if (!theAnvil.getSlotFromInventory(theAnvil.getInvOutput(), 0).canTakeStack(mc.thePlayer)) {
					color = 16736352;
				}
			}
		}
		if (costText != null) {
			int colorF = 0xFF000000 | (color & 0xFCFCFC) >> 2;
			int x = xSize - 8 - fontRendererObj.getStringWidth(costText);
			int y = 94;
			if (fontRendererObj.getUnicodeFlag()) {
				drawRect(x - 3, y - 2, xSize - 7, y + 10, -16777216);
				drawRect(x - 2, y - 1, xSize - 8, y + 9, -12895429);
			} else {
				fontRendererObj.drawString(costText, x, y + 1, colorF);
				fontRendererObj.drawString(costText, x + 1, y, colorF);
				fontRendererObj.drawString(costText, x + 1, y + 1, colorF);
			}
			fontRendererObj.drawString(costText, x, y, color);
		}
		GL11.glEnable(2896);
		if (theAnvil.getClientReforgeTime() > 0) {
			float f = theAnvil.getClientReforgeTime() / 40.0f;
			int alpha = (int) (f * 255.0f);
			alpha = MathHelper.clamp_int(alpha, 0, 255);
			int overlayColor = 0xFFFFFF | alpha << 24;
			Slot slot = theAnvil.getSlotFromInventory(theAnvil.getInvInput(), 0);
			drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, overlayColor);
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		float z;
		String tooltip;
		ItemStack inputItem = theAnvil.getInvInput().getStackInSlot(0);
		boolean canReforge = inputItem != null && GOTEnchantmentHelper.isReforgeable(inputItem) && theAnvil.getReforgeCost() > 0;
		boolean canEngrave = inputItem != null && GOTEnchantmentHelper.isReforgeable(inputItem) && theAnvil.getEngraveOwnerCost() > 0;
		buttonReforge.visible = buttonReforge.enabled = canReforge;
		buttonEngraveOwner.enabled = canEngrave && GOTContainerAnvil.canEngraveNewOwner(inputItem, mc.thePlayer);
		buttonEngraveOwner.visible = buttonEngraveOwner.enabled;
		super.drawScreen(i, j, f);
		if (buttonReforge.visible && buttonReforge.func_146115_a()) {
			z = zLevel;
			tooltip = StatCollector.translateToLocal("got.container.anvil.reforge");
			drawCreativeTabHoveringText(tooltip, i - 12, j + 24);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		if (buttonEngraveOwner.visible && buttonEngraveOwner.func_146115_a()) {
			z = zLevel;
			tooltip = StatCollector.translateToLocal("got.container.anvil.engraveOwner");
			drawCreativeTabHoveringText(tooltip, i - fontRendererObj.getStringWidth(tooltip), j + 24);
			GL11.glDisable(2896);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			zLevel = z;
		}
		GL11.glDisable(2896);
		GL11.glDisable(3042);
		List<EnumChatFormatting> itemNameFormatting = theAnvil.getActiveItemNameFormatting();
		for (EnumChatFormatting formatting : itemNameFormatting) {
			int formattingID = formatting.ordinal();
			if (formatting.isColor() && formattingID < COLOR_CODES.length) {
				int color = COLOR_CODES[formattingID];
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

	private void renameItem(String rename) {
		String rename1 = rename;
		ItemStack itemstack = theAnvil.getInvInput().getStackInSlot(0);
		if (itemstack != null && !itemstack.hasDisplayName()) {
			String displayNameStripped = GOTContainerAnvil.stripFormattingCodes(itemstack.getDisplayName());
			String renameStripped = GOTContainerAnvil.stripFormattingCodes(rename1);
			if (renameStripped.equals(displayNameStripped)) {
				rename1 = "";
			}
		}
		theAnvil.updateItemName(rename1);
		IMessage packet = new GOTPacketAnvilRename(rename1);
		GOTPacketHandler.NETWORK_WRAPPER.sendToServer(packet);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (theAnvil.getClientReforgeTime() > 0) {
			theAnvil.setClientReforgeTime(theAnvil.getClientReforgeTime() - 1);
		}
		ItemStack itemstack = theAnvil.getInvInput().getStackInSlot(0);
		if (itemstack != prevItemStack) {
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