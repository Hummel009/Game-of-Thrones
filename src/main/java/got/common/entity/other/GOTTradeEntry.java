package got.common.entity.other;

import got.common.item.other.GOTItemMug;
import got.common.quest.IPickpocketable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class GOTTradeEntry {
	public ItemStack tradeItem;
	public int tradeCost;
	public int recentTradeValue;
	public int lockedTicks;
	public GOTTraderNPCInfo theTrader;

	public GOTTradeEntry(ItemStack itemstack, int cost) {
		tradeItem = itemstack;
		tradeCost = cost;
	}

	public static GOTTradeEntry readFromNBT(NBTTagCompound nbt) {
		ItemStack savedItem = ItemStack.loadItemStackFromNBT(nbt);
		if (savedItem != null) {
			int cost = nbt.getInteger("Cost");
			GOTTradeEntry trade = new GOTTradeEntry(savedItem, cost);
			if (nbt.hasKey("RecentTradeValue")) {
				trade.recentTradeValue = nbt.getInteger("RecentTradeValue");
			}
			trade.lockedTicks = nbt.getInteger("LockedTicks");
			return trade;
		}
		return null;
	}

	public ItemStack createTradeItem() {
		return tradeItem.copy();
	}

	public void doTransaction(int value) {
		recentTradeValue += value;
	}

	public int getCost() {
		return tradeCost;
	}

	public void setCost(int i) {
		tradeCost = i;
	}

	public float getLockedProgress() {
		if (theTrader != null && theTrader.shouldLockTrades()) {
			return (float) recentTradeValue / theTrader.getLockTradeAtValue();
		}
		return 0.0f;
	}

	public int getLockedProgressForSlot() {
		return getLockedProgressInt(16);
	}

	public int getLockedProgressInt(int i) {
		float f = getLockedProgress();
		return Math.round(f * i);
	}

	public boolean isAvailable() {
		return theTrader == null || !theTrader.shouldLockTrades() || recentTradeValue < theTrader.getLockTradeAtValue() && lockedTicks <= 0;
	}

	public boolean matches(ItemStack itemstack) {
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			return false;
		}
		ItemStack tradeCreated = createTradeItem();
		if (GOTItemMug.isItemFullDrink(tradeCreated)) {
			ItemStack tradeDrink = GOTItemMug.getEquivalentDrink(tradeCreated);
			ItemStack offerDrink = GOTItemMug.getEquivalentDrink(itemstack);
			return tradeDrink.getItem() == offerDrink.getItem();
		}
		return OreDictionary.itemMatches(tradeCreated, itemstack, false);
	}

	public void setLockedForTicks(int ticks) {
		lockedTicks = ticks;
	}

	public void setOwningTrader(GOTTraderNPCInfo trader) {
		if (theTrader != null) {
			throw new IllegalArgumentException("Cannot assign already-owned trade entry to a different trader!");
		}
		theTrader = trader;
	}

	public boolean updateAvailability(int tick) {
		boolean prevAvailable = isAvailable();
		int prevLockProgress = getLockedProgressForSlot();
		if (tick % theTrader.getValueDecayTicks() == 0 && recentTradeValue > 0) {
			--recentTradeValue;
		}
		if (lockedTicks > 0) {
			--lockedTicks;
		}
		return isAvailable() != prevAvailable || getLockedProgressForSlot() != prevLockProgress;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		tradeItem.writeToNBT(nbt);
		nbt.setInteger("Cost", tradeCost);
		nbt.setInteger("RecentTradeValue", recentTradeValue);
		nbt.setInteger("LockedTicks", lockedTicks);
	}
}