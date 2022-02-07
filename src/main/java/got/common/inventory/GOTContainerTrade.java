package got.common.inventory;

import got.common.database.GOTTradeEntries;
import got.common.entity.other.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTContainerTrade extends Container {
	private IInventory tradeInvBuy = new InventoryBasic("trade", false, 9);
	private IInventory tradeInvSell = new InventoryBasic("trade", false, 9);
	private IInventory tradeInvSellOffer = new InventoryBasic("trade", false, 9);
	private GOTTradeable theTrader;
	private GOTEntityNPC theTraderNPC;
	private World theWorld;

	public GOTContainerTrade(InventoryPlayer inv, GOTTradeable trader, World world) {
		int i;
		theTrader = trader;
		setTheTraderNPC((GOTEntityNPC) trader);
		theWorld = world;
		if (!world.isRemote) {
			updateAllTradeSlots();
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new GOTSlotTrade(this, getTradeInvBuy(), i, 8 + i * 18, 40, getTheTraderNPC(), GOTTradeEntries.TradeType.BUY));
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new GOTSlotTrade(this, getTradeInvSell(), i, 8 + i * 18, 92, getTheTraderNPC(), GOTTradeEntries.TradeType.SELL));
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(getTradeInvSellOffer(), i, 8 + i * 18, 141));
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
		getTheTraderNPC().traderNPCInfo.sendClientPacket((EntityPlayer) crafting);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return getTheTraderNPC() != null && entityplayer.getDistanceToEntity(getTheTraderNPC()) <= 12.0 && getTheTraderNPC().isEntityAlive() && getTheTraderNPC().getAttackTarget() == null && theTrader.canTradeWith(entityplayer);
	}

	public GOTEntityNPC getTheTraderNPC() {
		return theTraderNPC;
	}

	public IInventory getTradeInvBuy() {
		return tradeInvBuy;
	}

	public IInventory getTradeInvSell() {
		return tradeInvSell;
	}

	public IInventory getTradeInvSellOffer() {
		return tradeInvSellOffer;
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!theWorld.isRemote) {
			for (int i = 0; i < getTradeInvSellOffer().getSizeInventory(); ++i) {
				ItemStack itemstack = getTradeInvSellOffer().getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
		}
	}

	public void setTheTraderNPC(GOTEntityNPC theTraderNPC) {
		this.theTraderNPC = theTraderNPC;
	}

	public void setTradeInvBuy(IInventory tradeInvBuy) {
		this.tradeInvBuy = tradeInvBuy;
	}

	public void setTradeInvSell(IInventory tradeInvSell) {
		this.tradeInvSell = tradeInvSell;
	}

	public void setTradeInvSellOffer(IInventory tradeInvSellOffer) {
		this.tradeInvSellOffer = tradeInvSellOffer;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			boolean sellable;
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			GOTTradeSellResult sellResult = GOTTradeEntries.getItemSellResult(itemstack1, getTheTraderNPC());
			sellable = sellResult != null && sellResult.tradesMade > 0;
			if (i < 9) {
				if (!mergeItemStack(itemstack1, 27, 63, true)) {
					return null;
				}
			} else if (i >= 9 && i < 18 || (i >= 18 && i < 27 ? !mergeItemStack(itemstack1, 27, 63, true) : sellable ? !mergeItemStack(itemstack1, 18, 27, false) : i >= 27 && i < 54 ? !mergeItemStack(itemstack1, 54, 63, false) : i >= 54 && i < 63 && !mergeItemStack(itemstack1, 27, 54, false))) {
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
		GOTTradeEntry[] buyTrades = getTheTraderNPC().traderNPCInfo.getBuyTrades();
		GOTTradeEntry[] sellTrades = getTheTraderNPC().traderNPCInfo.getSellTrades();
		if (buyTrades != null) {
			for (i = 0; i < getTradeInvBuy().getSizeInventory(); ++i) {
				trade = null;
				if (i < buyTrades.length) {
					trade = buyTrades[i];
				}
				if (trade != null) {
					getTradeInvBuy().setInventorySlotContents(i, trade.createTradeItem());
					continue;
				}
				getTradeInvBuy().setInventorySlotContents(i, null);
			}
		}
		if (sellTrades != null) {
			for (i = 0; i < getTradeInvSell().getSizeInventory(); ++i) {
				trade = null;
				if (i < sellTrades.length) {
					trade = sellTrades[i];
				}
				if (trade != null) {
					getTradeInvSell().setInventorySlotContents(i, trade.createTradeItem());
					continue;
				}
				getTradeInvSell().setInventorySlotContents(i, null);
			}
		}
	}
}
