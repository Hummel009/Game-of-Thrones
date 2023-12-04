package got.common.entity.other;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTLevelData;
import got.common.database.GOTTradeEntries;
import got.common.inventory.GOTContainerTrade;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketTraderInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;

import java.util.List;
import java.util.Random;

public class GOTTraderNPCInfo {
	public GOTEntityNPC theEntity;
	public GOTTradeEntry[] buyTrades;
	public GOTTradeEntry[] sellTrades;
	public int timeUntilAdvertisement;
	public int timeSinceTrade;
	public boolean shouldLockTrades = true;
	public int lockTradeAtValue = 200;
	public int lockValueDecayTicks = 60;
	public boolean shouldRefresh = true;
	public int valueSinceRefresh;
	public int refreshAtValue = 5000;
	public int lockTicksAfterRefresh = 6000;

	public GOTTraderNPCInfo(GOTEntityNPC npc) {
		theEntity = npc;
		if (theEntity instanceof GOTTradeable && !theEntity.worldObj.isRemote) {
			refreshTrades();
		}
	}

	public GOTTradeEntry[] getBuyTrades() {
		return buyTrades;
	}

	public void setBuyTrades(GOTTradeEntry[] trades) {
		for (GOTTradeEntry trade : trades) {
			trade.setOwningTrader(this);
		}
		buyTrades = trades;
	}

	public int getLockTradeAtValue() {
		return lockTradeAtValue;
	}

	public GOTTradeEntry[] getSellTrades() {
		return sellTrades;
	}

	public void setSellTrades(GOTTradeEntry[] trades) {
		for (GOTTradeEntry trade : trades) {
			trade.setOwningTrader(this);
		}
		sellTrades = trades;
	}

	public int getValueDecayTicks() {
		return lockValueDecayTicks;
	}

	public void onTrade(EntityPlayer entityplayer, GOTTradeEntry trade, GOTTradeEntries.TradeType type, int value) {
		((GOTTradeable) theEntity).onPlayerTrade(entityplayer, type, trade.createTradeItem());
		GOTLevelData.getData(entityplayer).getFactionData(theEntity.getFaction()).addTrade();
		trade.doTransaction(value);
		timeSinceTrade = 0;
		valueSinceRefresh += value;
		sendClientPacket(entityplayer);
	}

	public void onUpdate() {
		if (!theEntity.worldObj.isRemote) {
			if (timeUntilAdvertisement > 0) {
				--timeUntilAdvertisement;
			}
			++timeSinceTrade;
			int ticksExisted = theEntity.ticksExisted;
			boolean sendUpdate = false;
			for (GOTTradeEntry trade : buyTrades) {
				if (trade == null || !trade.updateAvailability(ticksExisted)) {
					continue;
				}
				sendUpdate = true;
			}
			for (GOTTradeEntry trade : sellTrades) {
				if (trade == null || !trade.updateAvailability(ticksExisted)) {
					continue;
				}
				sendUpdate = true;
			}
			if (shouldRefresh && valueSinceRefresh >= refreshAtValue) {
				refreshTrades();
				setAllTradesDelayed();
				sendUpdate = true;
			}
			if (sendUpdate) {
				List<EntityPlayer> players = theEntity.worldObj.playerEntities;
				for (EntityPlayer entityplayer : players) {
					Container container = entityplayer.openContainer;
					if (!(container instanceof GOTContainerTrade) || ((GOTContainerTrade) container).theTraderNPC != theEntity) {
						continue;
					}
					sendClientPacket(entityplayer);
				}
			}
			if (theEntity.isEntityAlive() && theEntity.getAttackTarget() == null && timeUntilAdvertisement == 0 && timeSinceTrade > 600) {
				double range = 10.0;
				List<EntityPlayer> players = theEntity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theEntity.boundingBox.expand(range, range, range));
				for (EntityPlayer obj : players) {
					String speechBank;
					if (!obj.isEntityAlive() || obj.capabilities.isCreativeMode || obj.openContainer != null && obj.openContainer != obj.inventoryContainer || (speechBank = theEntity.getSpeechBank(obj)) == null || theEntity.getRNG().nextInt(3) != 0) {
						continue;
					}
					theEntity.sendSpeechBank(obj, speechBank);
				}
				timeUntilAdvertisement = 20 * MathHelper.getRandomIntegerInRange(theEntity.getRNG(), 5, 20);
			}
		}
	}

	public void readFromNBT(NBTTagCompound data) {
		int i;
		NBTTagCompound nbt;
		NBTTagCompound buyTradesData;
		GOTTradeEntry trade;
		if (data.hasKey("GOTBuyTrades") && (buyTradesData = data.getCompoundTag("GOTBuyTrades")).hasKey("Trades")) {
			NBTTagList buyTradesTags = buyTradesData.getTagList("Trades", 10);
			buyTrades = new GOTTradeEntry[buyTradesTags.tagCount()];
			for (i = 0; i < buyTradesTags.tagCount(); ++i) {
				nbt = buyTradesTags.getCompoundTagAt(i);
				trade = GOTTradeEntry.readFromNBT(nbt);
				trade.setOwningTrader(this);
				buyTrades[i] = trade;
			}
		}
		NBTTagCompound sellTradesData;
		if (data.hasKey("GOTSellTrades") && (sellTradesData = data.getCompoundTag("GOTSellTrades")).hasKey("Trades")) {
			NBTTagList sellTradesTags = sellTradesData.getTagList("Trades", 10);
			sellTrades = new GOTTradeEntry[sellTradesTags.tagCount()];
			for (i = 0; i < sellTradesTags.tagCount(); ++i) {
				nbt = sellTradesTags.getCompoundTagAt(i);
				trade = GOTTradeEntry.readFromNBT(nbt);
				trade.setOwningTrader(this);
				sellTrades[i] = trade;
			}
		}
		timeSinceTrade = data.getInteger("TimeSinceTrade");
		if (data.hasKey("ShouldLockTrades")) {
			shouldLockTrades = data.getBoolean("ShouldLockTrades");
		}
		if (data.hasKey("LockTradeAtValue")) {
			lockTradeAtValue = data.getInteger("LockTradeAtValue");
		}
		if (data.hasKey("LockValueDecayTicks")) {
			lockValueDecayTicks = data.getInteger("LockValueDecayTicks");
		}
		if (data.hasKey("ShouldRefresh")) {
			shouldRefresh = data.getBoolean("ShouldRefresh");
		}
		if (data.hasKey("RefreshAtValue")) {
			refreshAtValue = data.getInteger("RefreshAtValue");
		}
		if (data.hasKey("LockTicksAfterRefresh")) {
			lockTicksAfterRefresh = data.getInteger("LockTicksAfterRefresh");
		}
		valueSinceRefresh = data.getInteger("ValueSinceRefresh");
	}

	public void receiveClientPacket(GOTPacketTraderInfo packet) {
		NBTTagCompound nbt = packet.traderData;
		readFromNBT(nbt);
	}

	public void refreshTrades() {
		GOTTradeable theTrader = (GOTTradeable) theEntity;
		Random rand = theEntity.getRNG();
		setBuyTrades(theTrader.getBuyPool().getRandomTrades(rand));
		setSellTrades(theTrader.getSellPool().getRandomTrades(rand));
		valueSinceRefresh = 0;
		List<EntityPlayer> players = theEntity.worldObj.playerEntities;
		for (EntityPlayer entityplayer : players) {
			Container container = entityplayer.openContainer;
			if (!(container instanceof GOTContainerTrade) || ((GOTContainerTrade) container).theTraderNPC != theEntity) {
				continue;
			}
			((GOTContainerTrade) container).updateAllTradeSlots();
		}
	}

	public void sendClientPacket(EntityPlayer entityplayer) {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		IMessage packet = new GOTPacketTraderInfo(nbt);
		GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
	}

	public void setAllTradesDelayed() {
		int delay = lockTicksAfterRefresh;
		for (GOTTradeEntry trade : buyTrades) {
			if (trade == null) {
				continue;
			}
			trade.setLockedForTicks(delay);
		}
		for (GOTTradeEntry trade : sellTrades) {
			if (trade == null) {
				continue;
			}
			trade.setLockedForTicks(delay);
		}
	}

	public boolean shouldLockTrades() {
		return shouldLockTrades;
	}

	public void writeToNBT(NBTTagCompound data) {
		NBTTagCompound nbt;
		if (buyTrades != null) {
			NBTTagList buyTradesTags = new NBTTagList();
			for (GOTTradeEntry trade : buyTrades) {
				if (trade == null) {
					continue;
				}
				nbt = new NBTTagCompound();
				trade.writeToNBT(nbt);
				buyTradesTags.appendTag(nbt);
			}
			NBTTagCompound buyTradesData = new NBTTagCompound();
			buyTradesData.setTag("Trades", buyTradesTags);
			data.setTag("GOTBuyTrades", buyTradesData);
		}
		if (sellTrades != null) {
			NBTTagList sellTradesTags = new NBTTagList();
			for (GOTTradeEntry trade : sellTrades) {
				if (trade == null) {
					continue;
				}
				nbt = new NBTTagCompound();
				trade.writeToNBT(nbt);
				sellTradesTags.appendTag(nbt);
			}
			NBTTagCompound sellTradesData = new NBTTagCompound();
			sellTradesData.setTag("Trades", sellTradesTags);
			data.setTag("GOTSellTrades", sellTradesData);
		}
		data.setInteger("TimeSinceTrade", timeSinceTrade);
		data.setBoolean("ShouldLockTrades", shouldLockTrades);
		data.setInteger("LockTradeAtValue", lockTradeAtValue);
		data.setInteger("LockValueDecayTicks", lockValueDecayTicks);
		data.setBoolean("ShouldRefresh", shouldRefresh);
		data.setInteger("RefreshAtValue", refreshAtValue);
		data.setInteger("LockTicksAfterRefresh", lockTicksAfterRefresh);
		data.setInteger("ValueSinceRefresh", valueSinceRefresh);
	}
}
