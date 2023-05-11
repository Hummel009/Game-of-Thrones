package got.common.inventory;

import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTHiredNPCInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GOTContainerHiredFarmerInventory extends Container {
	public GOTEntityNPC theNPC;

	public GOTContainerHiredFarmerInventory(InventoryPlayer inv, GOTEntityNPC entity) {
		int i;
		theNPC = entity;
		addSlotToContainer(new GOTSlotSeeds(theNPC.hiredNPCInfo.getHiredInventory(), 0, 80, 21, theNPC.worldObj));
		for (i = 0; i < 2; ++i) {
			addSlotToContainer(new GOTSlotProtected(theNPC.hiredNPCInfo.getHiredInventory(), i + 1, 71 + i * 18, 47));
		}
		addSlotToContainer(new GOTSlotBonemeal(theNPC.hiredNPCInfo.getHiredInventory(), 3, 123, 34, theNPC.worldObj));
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 79 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 137));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theNPC != null && theNPC.isEntityAlive() && theNPC.hiredNPCInfo.isActive && theNPC.hiredNPCInfo.getHiringPlayer() == entityplayer && theNPC.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.FARMER && entityplayer.getDistanceSqToEntity(theNPC) <= 144.0;
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!theNPC.worldObj.isRemote) {
			theNPC.hiredNPCInfo.sendClientPacket(true);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 4) {
				if (!mergeItemStack(itemstack1, 4, 40, true)) {
					return null;
				}
			} else if (((Slot) inventorySlots.get(0)).isItemValid(itemstack1) && !mergeItemStack(itemstack1, 0, 1, false) || ((Slot) inventorySlots.get(3)).isItemValid(itemstack1) && !mergeItemStack(itemstack1, 3, 4, false)) {
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
