package got.common.inventory;

import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTTradeEntry;
import got.common.item.other.GOTItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GOTSlotTrade extends GOTSlotProtected {
	public GOTContainerTrade theContainer;
	public GOTEntityNPC theEntity;
	public GOTTradeEntries.TradeType tradeType;

	public GOTSlotTrade(GOTContainerTrade container, IInventory inv, int i, int j, int k, GOTEntityNPC entity, GOTTradeEntries.TradeType type) {
		super(inv, i, j, k);
		theContainer = container;
		theEntity = entity;
		tradeType = type;
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		if (tradeType == GOTTradeEntries.TradeType.WE_CAN_BUY) {
			if (getTrade() != null && !getTrade().isAvailable()) {
				return false;
			}
			int coins = GOTItemCoin.getInventoryValue(entityplayer, false);
			if (coins < cost()) {
				return false;
			}
		}
		if (tradeType == GOTTradeEntries.TradeType.WE_CAN_SELL) {
			return false;
		}
		return super.canTakeStack(entityplayer);
	}

	public int cost() {
		GOTTradeEntry trade = getTrade();
		return trade == null ? 0 : trade.getCost();
	}

	public GOTTradeEntry getTrade() {
		GOTTradeEntry[] trades = null;
		if (tradeType == GOTTradeEntries.TradeType.WE_CAN_BUY) {
			trades = theEntity.traderNPCInfo.getBuyTrades();
		} else if (tradeType == GOTTradeEntries.TradeType.WE_CAN_SELL) {
			trades = theEntity.traderNPCInfo.getSellTrades();
		}
		if (trades == null) {
			return null;
		}
		int i = getSlotIndex();
		if (i >= 0 && i < trades.length) {
			return trades[i];
		}
		return null;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		if (tradeType == GOTTradeEntries.TradeType.WE_CAN_BUY && !entityplayer.worldObj.isRemote) {
			GOTItemCoin.takeCoins(cost(), entityplayer);
		}
		super.onPickupFromSlot(entityplayer, itemstack);
		if (tradeType == GOTTradeEntries.TradeType.WE_CAN_BUY) {
			GOTTradeEntry trade = getTrade();
			if (!entityplayer.worldObj.isRemote && trade != null) {
				putStack(trade.createTradeItem());
				((EntityPlayerMP) entityplayer).sendContainerToPlayer(theContainer);
				theEntity.traderNPCInfo.onTrade(entityplayer, trade, GOTTradeEntries.TradeType.WE_CAN_BUY, cost());
				theEntity.playTradeSound();
			}
		}
	}
}
