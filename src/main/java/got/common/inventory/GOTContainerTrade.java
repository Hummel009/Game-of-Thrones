package got.common.inventory;

import got.common.database.GOTTradeEntries;
import got.common.entity.other.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTContainerTrade extends Container {
	public IInventory tradeInvBuy = new InventoryBasic("trade", false, 9);
	public IInventory tradeInvSell = new InventoryBasic("trade", false, 9);
	public IInventory tradeInvSellOffer = new InventoryBasic("trade", false, 9);
	public GOTTradeable theTrader;
	public GOTEntityNPC theTraderNPC;
	public World theWorld;

	public GOTContainerTrade(InventoryPlayer inv, GOTTradeable trader, World world) {
		int i;
		theTrader = trader;
		theTraderNPC = (GOTEntityNPC) (trader);
		theWorld = world;
		if (!world.isRemote) {
			updateAllTradeSlots();
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new GOTSlotTrade(this, tradeInvBuy, i, 8 + i * 18, 40, theTraderNPC, GOTTradeEntries.TradeType.BUY));
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new GOTSlotTrade(this, tradeInvSell, i, 8 + i * 18, 92, theTraderNPC, GOTTradeEntries.TradeType.SELL));
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(tradeInvSellOffer, i, 8 + i * 18, 141));
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 188 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 246));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		theTraderNPC.traderNPCInfo.sendClientPacket((EntityPlayer) (crafting));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theTraderNPC != null && entityplayer.getDistanceToEntity(theTraderNPC) <= 12.0 && theTraderNPC.isEntityAlive() && theTraderNPC.getAttackTarget() == null && theTrader.canTradeWith(entityplayer);
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!theWorld.isRemote) {
			for (int i = 0; i < tradeInvSellOffer.getSizeInventory(); ++i) {
				ItemStack itemstack = tradeInvSellOffer.getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			boolean sellable;
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			GOTTradeSellResult sellResult = GOTTradeEntries.getItemSellResult(itemstack1, theTraderNPC);
			sellable = sellResult != null && sellResult.tradesMade > 0;
			if (i < 9) {
				if (!mergeItemStack(itemstack1, 27, 63, true)) {
					return null;
				}
			} else if ((i >= 9 && i < 18) || (i >= 18 && i < 27 ? !mergeItemStack(itemstack1, 27, 63, true) : (sellable ? !mergeItemStack(itemstack1, 18, 27, false) : (i >= 27 && i < 54 ? !mergeItemStack(itemstack1, 54, 63, false) : i >= 54 && i < 63 && !mergeItemStack(itemstack1, 27, 54, false))))) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(entityplayer, itemstack1);
		}
		return itemstack;
	}

	public void updateAllTradeSlots() {
		GOTTradeEntry trade;
		int i;
		GOTTradeEntry[] buyTrades = theTraderNPC.traderNPCInfo.getBuyTrades();
		GOTTradeEntry[] sellTrades = theTraderNPC.traderNPCInfo.getSellTrades();
		if (buyTrades != null) {
			for (i = 0; i < tradeInvBuy.getSizeInventory(); ++i) {
				trade = null;
				if (i < buyTrades.length) {
					trade = buyTrades[i];
				}
				if (trade != null) {
					tradeInvBuy.setInventorySlotContents(i, trade.createTradeItem());
					continue;
				}
				tradeInvBuy.setInventorySlotContents(i, null);
			}
		}
		if (sellTrades != null) {
			for (i = 0; i < tradeInvSell.getSizeInventory(); ++i) {
				trade = null;
				if (i < sellTrades.length) {
					trade = sellTrades[i];
				}
				if (trade != null) {
					tradeInvSell.setInventorySlotContents(i, trade.createTradeItem());
					continue;
				}
				tradeInvSell.setInventorySlotContents(i, null);
			}
		}
	}
}
