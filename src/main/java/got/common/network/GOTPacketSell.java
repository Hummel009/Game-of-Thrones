package got.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.*;
import got.common.inventory.GOTContainerTrade;
import got.common.item.other.GOTItemCoin;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTPacketSell implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketSell, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketSell packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerTrade) {
				GOTContainerTrade tradeContainer = (GOTContainerTrade) container;
				GOTEntityNPC trader = tradeContainer.theTraderNPC;
				IInventory invSellOffer = tradeContainer.getTradeInvSellOffer();
				HashMap<GOTTradeEntry, Integer> tradesUsed = new HashMap<>();
				int totalCoins = 0;
				for (int i = 0; i < invSellOffer.getSizeInventory(); ++i) {
					GOTTradeSellResult sellResult;
					ItemStack itemstack = invSellOffer.getStackInSlot(i);
					if (itemstack == null || (sellResult = GOTTradeEntries.getItemSellResult(itemstack, trader)) == null) {
						continue;
					}
					int tradeIndex = sellResult.tradeIndex;
					int value = sellResult.totalSellValue;
					int itemsSold = sellResult.itemsSold;
					GOTTradeEntry[] sellTrades = trader.traderNPCInfo.getSellTrades();
					GOTTradeEntry trade = null;
					if (sellTrades != null) {
						trade = sellTrades[tradeIndex];
					}
					totalCoins += value;
					if (trade != null) {
						Integer prevValue = tradesUsed.get(trade);
						if (prevValue == null) {
							tradesUsed.put(trade, value);
						} else {
							tradesUsed.put(trade, prevValue + value);
						}
					}
					itemstack.stackSize -= itemsSold;
					if (itemstack.stackSize > 0) {
						continue;
					}
					invSellOffer.setInventorySlotContents(i, null);
				}
				if (totalCoins > 0) {
					for (Map.Entry e : tradesUsed.entrySet()) {
						GOTTradeEntry trade = (GOTTradeEntry) e.getKey();
						int value = (Integer) e.getValue();
						trader.traderNPCInfo.onTrade(entityplayer, trade, GOTTradeEntries.TradeType.SELL, value);
					}
					GOTItemCoin.giveCoins(totalCoins, entityplayer);
					if (totalCoins >= 1000) {
						GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.EARN_MANY_COINS);
					}
					trader.playTradeSound();
				}
			}
			return null;
		}
	}

}
