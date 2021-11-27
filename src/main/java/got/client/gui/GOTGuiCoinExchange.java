package got.client.gui;

import org.lwjgl.opengl.GL11;

import got.common.entity.other.GOTEntityNPC;
import got.common.inventory.GOTContainerCoinExchange;
import got.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.*;

public class GOTGuiCoinExchange extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("got:gui/coin_exchange.png");
	public GOTContainerCoinExchange theContainer;
	public GuiButton buttonLeft;
	public GuiButton buttonRight;

	public GOTGuiCoinExchange(EntityPlayer entityplayer, GOTEntityNPC npc) {
		super(new GOTContainerCoinExchange(entityplayer, npc));
		theContainer = (GOTContainerCoinExchange) inventorySlots;
		ySize = 188;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button == buttonLeft || button == buttonRight) {
				GOTPacketCoinExchange packet = new GOTPacketCoinExchange(button.id);
				GOTPacketHandler.networkWrapper.sendToServer(packet);
			} else {
				super.actionPerformed(button);
			}
		}
	}

	public void drawCenteredString(String s, int i, int j, int k) {
		fontRendererObj.drawString(s, i - fontRendererObj.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (theContainer.exchanged) {
			for (int l = 0; l < theContainer.exchangeInv.getSizeInventory(); ++l) {
				Slot slot = theContainer.getSlotFromInventory(theContainer.exchangeInv, l);
				if (!slot.getHasStack()) {
					continue;
				}
				drawTexturedModalRect(guiLeft + slot.xDisplayPosition - 5, guiTop + slot.yDisplayPosition - 5, 176, 51, 26, 26);
			}
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		this.drawCenteredString(StatCollector.translateToLocal("got.container.coinExchange"), 89, 11, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 94, 4210752);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		buttonLeft.enabled = !theContainer.exchanged && theContainer.exchangeInv.getStackInSlot(0) != null;
		buttonRight.enabled = !theContainer.exchanged && theContainer.exchangeInv.getStackInSlot(1) != null;
		super.drawScreen(i, j, f);
	}

	@Override
	public void initGui() {
		super.initGui();
		int i = guiLeft + xSize / 2;
		int j = 28;
		int k = 16;
		buttonLeft = new GOTGuiButtonCoinExchange(0, i - j - k, guiTop + 45);
		buttonList.add(buttonLeft);
		buttonRight = new GOTGuiButtonCoinExchange(1, i + j - k, guiTop + 45);
		buttonList.add(buttonRight);
	}
}