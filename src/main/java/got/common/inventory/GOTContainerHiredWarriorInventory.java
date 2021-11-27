package got.common.inventory;

import got.common.entity.essos.yiti.GOTEntityYiTiBombardier;
import got.common.entity.other.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTContainerHiredWarriorInventory extends Container {
	public GOTEntityNPC theNPC;
	public GOTInventoryHiredReplacedItems npcInv;
	public IInventory proxyInv;
	public int npcFullInvSize;
	public int npcActiveSlotCount;

	public GOTContainerHiredWarriorInventory(InventoryPlayer inv, GOTEntityNPC entity) {
		int i;
		theNPC = entity;
		npcInv = theNPC.hiredReplacedInv;
		npcFullInvSize = npcInv.getSizeInventory();
		proxyInv = new InventoryBasic("npcTemp", false, npcFullInvSize);
		for (int i2 = 0; i2 < 4; ++i2) {
			GOTSlotHiredReplaceItem slot = new GOTSlotHiredReplaceItem(new GOTSlotArmorStand(proxyInv, i2, 80, 21 + i2 * 18, i2, inv.player), theNPC);
			addSlotToContainer(slot);
		}
		int[] arrn = new int[1];
		arrn[0] = 4;
		for (int i3 : arrn) {
			GOTSlotHiredReplaceItem slot = new GOTSlotHiredReplaceItem(new GOTSlotMeleeWeapon(proxyInv, i3, 50, 48), theNPC);
			addSlotToContainer(slot);
		}
		if (theNPC instanceof GOTEntityYiTiBombardier) {
			int i4 = 5;
			GOTSlotHiredReplaceItem slot = new GOTSlotHiredReplaceItem(new GOTSlotBomb(proxyInv, i4, 110, 48), theNPC);
			addSlotToContainer(slot);
		}
		for (i = 0; i < npcFullInvSize; ++i) {
			if (getSlotFromInventory(proxyInv, i) == null) {
				continue;
			}
			++npcActiveSlotCount;
		}
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 165));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theNPC != null && theNPC.isEntityAlive() && theNPC.hiredNPCInfo.isActive && theNPC.hiredNPCInfo.getHiringPlayer() == entityplayer && theNPC.hiredNPCInfo.getTask() == GOTHiredNPCInfo.Task.WARRIOR && entityplayer.getDistanceSqToEntity(theNPC) <= 144.0;
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
			if (slot.inventory == proxyInv) {
				if (!mergeItemStack(itemstack1, npcActiveSlotCount, inventorySlots.size(), true)) {
					return null;
				}
			} else {
				for (int j = 0; j < npcFullInvSize; ++j) {
					Slot npcSlot = getSlotFromInventory(proxyInv, j);
					if (npcSlot == null) {
						continue;
					}
					int npcSlotNo = npcSlot.slotNumber;
					if (!npcSlot.isItemValid(itemstack1) || mergeItemStack(itemstack1, npcSlotNo, npcSlotNo + 1, false)) {
						continue;
					}
					return null;
				}
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
