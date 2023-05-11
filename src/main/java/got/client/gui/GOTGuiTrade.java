package got.client.gui;

import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTTradeEntry;
import got.common.entity.other.GOTTradeSellResult;
import got.common.entity.other.GOTTradeable;
import got.common.inventory.GOTContainerTrade;
import got.common.inventory.GOTSlotTrade;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketSell;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GOTGuiTrade extends GuiContainer {
	public static ResourceLocation guiTexture = new ResourceLocation("got:textures/gui/npc/trade.png");
	public static int lockedTradeColor = -1610612736;
	public GOTEntityNPC theEntity;
	public GOTContainerTrade containerTrade;

	public GuiButton buttonSell;

	public GOTGuiTrade(InventoryPlayer inv, GOTTradeable trader, World world) {
		super(new GOTContainerTrade(inv, trader, world));
		containerTrade = (GOTContainerTrade) inventorySlots;
		theEntity = (GOTEntityNPC) trader;
		ySize = 270;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.enabled && button == buttonSell) {
			GOTPacketSell packet = new GOTPacketSell();
			GOTPacketHandler.networkWrapper.sendToServer(packet);
		}
	}

	public void drawCenteredString(String s, int i, int j, int k) {
		fontRendererObj.drawString(s, i - fontRendererObj.getStringWidth(s) / 2, j, k);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(guiTexture);
		Gui.func_146110_a(guiLeft, guiTop, 0.0f, 0.0f, xSize, ySize, 512.0f, 512.0f);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i, int j) {
		GOTTradeEntry trade;
		int y;
		int x;
		int cost;
		int l;
		drawCenteredString(theEntity.getNPCName(), 89, 11, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("got.container.trade.buy"), 8, 28, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("got.container.trade.sell"), 8, 79, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("got.container.trade.sellOffer"), 8, 129, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 176, 4210752);
		for (l = 0; l < containerTrade.tradeInvBuy.getSizeInventory(); ++l) {
			GOTSlotTrade slotBuy = (GOTSlotTrade) containerTrade.getSlotFromInventory(containerTrade.tradeInvBuy, l);
			trade = slotBuy.getTrade();
			if (trade != null) {
				if (trade.isAvailable()) {
					cost = slotBuy.cost();
					if (cost <= 0) {
						continue;
					}
					renderCost(Integer.toString(cost), slotBuy.xDisplayPosition + 8, slotBuy.yDisplayPosition + 22);
				} else {
					GL11.glTranslatef(0.0f, 0.0f, 200.0f);
					x = slotBuy.xDisplayPosition;
					y = slotBuy.yDisplayPosition;
					Gui.drawRect(x, y, x + 16, y + 16, lockedTradeColor);
					GL11.glTranslatef(0.0f, 0.0f, -200.0f);
					drawCenteredString(StatCollector.translateToLocal("got.container.trade.locked"), slotBuy.xDisplayPosition + 8, slotBuy.yDisplayPosition + 22, 4210752);
				}
			}

		}
		for (l = 0; l < containerTrade.tradeInvSell.getSizeInventory(); ++l) {
			GOTSlotTrade slotSell = (GOTSlotTrade) containerTrade.getSlotFromInventory(containerTrade.tradeInvSell, l);
			trade = slotSell.getTrade();
			if (trade != null) {
				if (trade.isAvailable()) {
					cost = slotSell.cost();
					if (cost <= 0) {
						continue;
					}
					renderCost(Integer.toString(cost), slotSell.xDisplayPosition + 8, slotSell.yDisplayPosition + 22);
				} else {
					GL11.glTranslatef(0.0f, 0.0f, 200.0f);
					x = slotSell.xDisplayPosition;
					y = slotSell.yDisplayPosition;
					Gui.drawRect(x, y, x + 16, y + 16, lockedTradeColor);
					GL11.glTranslatef(0.0f, 0.0f, -200.0f);
					drawCenteredString(StatCollector.translateToLocal("got.container.trade.locked"), slotSell.xDisplayPosition + 8, slotSell.yDisplayPosition + 22, 4210752);
				}
			}
		}
		int totalSellPrice = 0;
		for (int l2 = 0; l2 < containerTrade.tradeInvSellOffer.getSizeInventory(); ++l2) {
			GOTTradeSellResult sellResult;
			Slot slotSell = containerTrade.getSlotFromInventory(containerTrade.tradeInvSellOffer, l2);
			ItemStack item = slotSell.getStack();
			if (item != null && (sellResult = GOTTradeEntries.getItemSellResult(item, theEntity)) != null) {
				totalSellPrice += sellResult.totalSellValue;
			}
		}
		if (totalSellPrice > 0) {
			fontRendererObj.drawString(StatCollector.translateToLocalFormatted("got.container.trade.sellPrice", totalSellPrice), 100, 169, 4210752);
		}
		buttonSell.enabled = totalSellPrice > 0;
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonSell = new GOTGuiTradeButton(0, guiLeft + 79, guiTop + 164);
		buttonSell.enabled = false;
		buttonList.add(buttonSell);
	}

	public void renderCost(String s, int x, int y) {
		int l = fontRendererObj.getStringWidth(s);
		boolean halfSize = l > 20;
		if (halfSize) {
			GL11.glPushMatrix();
			GL11.glScalef(0.5f, 0.5f, 1.0f);
			x *= 2;
			y *= 2;
			y += fontRendererObj.FONT_HEIGHT / 2;
		}
		drawCenteredString(s, x, y, 4210752);
		if (halfSize) {
			GL11.glPopMatrix();
		}
	}
}
