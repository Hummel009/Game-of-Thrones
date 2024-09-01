package got.common.inventory;

import got.common.GOTLevelData;
import got.common.database.GOTInvasions;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTHireableBase;
import got.common.entity.other.iface.GOTUnitTradeable;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTContainerUnitTrade extends Container {
	private final GOTHireableBase theUnitTrader;
	private final GOTEntityNPC theLivingTrader;
	private final int reputationRewardSlots;

	public GOTContainerUnitTrade(EntityPlayer entityplayer, GOTHireableBase trader, World world) {
		int i;
		theUnitTrader = trader;
		theLivingTrader = (GOTEntityNPC) theUnitTrader;
		GOTFaction traderFaction = theLivingTrader.getFaction();
		ItemStack reward = null;
		if (theUnitTrader instanceof GOTUnitTradeable) {
			GOTInvasions invasionType = ((GOTUnitTradeable) theUnitTrader).getWarhorn();
			reward = invasionType == null ? null : invasionType.createWarhorn();
		}
		boolean hasReward = reward != null;
		reputationRewardSlots = hasReward ? 1 : 0;
		IInventory reputationRewardInv = new InventoryBasic("specialItem", false, reputationRewardSlots);
		if (hasReward) {
			addSlotToContainer(new GOTSlotReputationReward(this, reputationRewardInv, 0, 174, 78, theUnitTrader, reward.copy()));
			if (!world.isRemote && GOTLevelData.getData(entityplayer).getReputation(traderFaction) >= GOTSlotReputationReward.WARHORN_REPUTATION) {
				reputationRewardInv.setInventorySlotContents(0, reward.copy());
			}
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 30 + j * 18, 174 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(entityplayer.inventory, i, 30 + i * 18, 232));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theLivingTrader != null && entityplayer.getDistanceToEntity(theLivingTrader) <= 12.0 && theLivingTrader.isEntityAlive() && theLivingTrader.getAttackTarget() == null && theUnitTrader.getTradeCondition((GOTEntityNPC) theUnitTrader, entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < reputationRewardSlots ? !mergeItemStack(itemstack1, reputationRewardSlots, 36 + reputationRewardSlots, true) : i < 27 + reputationRewardSlots ? !mergeItemStack(itemstack1, 27 + reputationRewardSlots, 36 + reputationRewardSlots, false) : !mergeItemStack(itemstack1, reputationRewardSlots, 27 + reputationRewardSlots, false)) {
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

	public GOTHireableBase getTheUnitTrader() {
		return theUnitTrader;
	}

	public GOTEntityNPC getTheLivingTrader() {
		return theLivingTrader;
	}

	public int getReputationRewardSlots() {
		return reputationRewardSlots;
	}
}