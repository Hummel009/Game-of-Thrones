package got.common.entity.other.utils;

import got.common.entity.other.info.GOTTraderInfo;
import got.common.item.other.GOTItemMug;
import got.common.quest.GOTPickpoketableHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class GOTTradeEntry {
	private final ItemStack tradeItem;
	private final boolean frozenPrice;

	private GOTTraderInfo theTrader;

	private int tradeCost;
	private int recentTradeValue;
	private int lockedTicks;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTTradeEntry(ItemStack itemstack, int cost) {
		tradeItem = itemstack;
		tradeCost = cost;
		frozenPrice = false;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTTradeEntry(ItemStack itemstack, int cost, boolean frozenPrice) {
		tradeItem = itemstack;
		tradeCost = cost;
		this.frozenPrice = frozenPrice;
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

	private float getLockedProgress() {
		if (theTrader != null && theTrader.shouldLockTrades()) {
			return (float) recentTradeValue / theTrader.getLockTradeAtValue();
		}
		return 0.0f;
	}

	private int getLockedProgressForSlot() {
		return getLockedProgressInt(16);
	}

	private int getLockedProgressInt(int i) {
		float f = getLockedProgress();
		return Math.round(f * i);
	}

	public boolean isAvailable() {
		return theTrader == null || !theTrader.shouldLockTrades() || recentTradeValue < theTrader.getLockTradeAtValue() && lockedTicks <= 0;
	}

	public boolean matches(ItemStack itemstack) {
		if (GOTPickpoketableHelper.isPickpocketed(itemstack)) {
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

	public void setOwningTrader(GOTTraderInfo trader) {
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

	public ItemStack getTradeItem() {
		return tradeItem;
	}

	public boolean isFrozenPrice() {
		return frozenPrice;
	}
}