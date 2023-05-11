package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTTradeEntry;
import got.common.entity.other.GOTTradeSellResult;
import got.common.inventory.GOTContainerTrade;
import got.common.item.other.GOTItemCoin;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

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
				IInventory invSellOffer = tradeContainer.tradeInvSellOffer;
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
						tradesUsed.merge(trade, value, Integer::sum);
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
						trader.traderNPCInfo.onTrade(entityplayer, trade, GOTTradeEntries.TradeType.WE_CAN_SELL, value);
					}
					GOTItemCoin.giveCoins(totalCoins, entityplayer);
					if (totalCoins >= 1000) {
						GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.earnManyCoins);
					}
					trader.playTradeSound();
				}
			}
			return null;
		}
	}

}
