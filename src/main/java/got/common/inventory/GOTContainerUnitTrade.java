package got.common.inventory;

import got.common.GOTLevelData;
import got.common.database.GOTInvasions;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTContainerUnitTrade extends Container {
	private GOTHireableBase theUnitTrader;
	private GOTEntityNPC theLivingTrader;
	private GOTFaction traderFaction;
	private IInventory alignmentRewardInv;
	private int alignmentRewardSlots;

	public GOTContainerUnitTrade(EntityPlayer entityplayer, GOTHireableBase trader, World world) {
		int i;
		setTheUnitTrader(trader);
		setTheLivingTrader((GOTEntityNPC) getTheUnitTrader());
		traderFaction = getTheLivingTrader().getFaction();
		ItemStack reward = null;
		if (getTheUnitTrader() instanceof GOTUnitTradeable) {
			GOTInvasions conquestType = ((GOTUnitTradeable) getTheUnitTrader()).getWarhorn();
			reward = conquestType == null ? null : conquestType.createConquestHorn();
		}
		boolean hasReward = reward != null;
		setAlignmentRewardSlots(hasReward ? 1 : 0);
		alignmentRewardInv = new InventoryBasic("specialItem", false, getAlignmentRewardSlots());
		if (hasReward) {
			addSlotToContainer(new GOTSlotAlignmentReward(this, alignmentRewardInv, 0, 174, 78, getTheUnitTrader(), reward.copy()));
			if (!world.isRemote && GOTLevelData.getData(entityplayer).getAlignment(traderFaction) >= 1500.0f) {
				alignmentRewardInv.setInventorySlotContents(0, reward.copy());
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
		return getTheLivingTrader() != null && entityplayer.getDistanceToEntity(getTheLivingTrader()) <= 12.0 && getTheLivingTrader().isEntityAlive() && getTheLivingTrader().getAttackTarget() == null && getTheUnitTrader().canTradeWith(entityplayer);
	}

	public int getAlignmentRewardSlots() {
		return alignmentRewardSlots;
	}

	public GOTEntityNPC getTheLivingTrader() {
		return theLivingTrader;
	}

	public GOTHireableBase getTheUnitTrader() {
		return theUnitTrader;
	}

	public void setAlignmentRewardSlots(int alignmentRewardSlots) {
		this.alignmentRewardSlots = alignmentRewardSlots;
	}

	public void setTheLivingTrader(GOTEntityNPC theLivingTrader) {
		this.theLivingTrader = theLivingTrader;
	}

	public void setTheUnitTrader(GOTHireableBase theUnitTrader) {
		this.theUnitTrader = theUnitTrader;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i >= 0 && i < getAlignmentRewardSlots() ? !mergeItemStack(itemstack1, getAlignmentRewardSlots(), 36 + getAlignmentRewardSlots(), true) : i >= getAlignmentRewardSlots() && i < 27 + getAlignmentRewardSlots() ? !mergeItemStack(itemstack1, 27 + getAlignmentRewardSlots(), 36 + getAlignmentRewardSlots(), false) : i >= 27 + getAlignmentRewardSlots() && i < 36 + getAlignmentRewardSlots() ? !mergeItemStack(itemstack1, getAlignmentRewardSlots(), 27 + getAlignmentRewardSlots(), false) : !mergeItemStack(itemstack1, getAlignmentRewardSlots(), 27 + getAlignmentRewardSlots(), false)) {
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
}
