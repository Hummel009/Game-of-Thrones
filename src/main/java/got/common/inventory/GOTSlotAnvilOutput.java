package got.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTSlotAnvilOutput extends Slot {
	public GOTContainerAnvil theAnvil;

	public GOTSlotAnvilOutput(GOTContainerAnvil container, IInventory inv, int id, int i, int j) {
		super(inv, id, i, j);
		theAnvil = container;
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		if (getHasStack()) {
			if (theAnvil.materialCost > 0) {
				return theAnvil.hasMaterialOrCoinAmount(theAnvil.materialCost);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		int materials = theAnvil.materialCost;
		theAnvil.invInput.setInventorySlotContents(0, null);
		ItemStack combinerItem = theAnvil.invInput.getStackInSlot(1);
		if (combinerItem != null) {
			--combinerItem.stackSize;
			if (combinerItem.stackSize <= 0) {
				theAnvil.invInput.setInventorySlotContents(1, null);
			} else {
				theAnvil.invInput.setInventorySlotContents(1, combinerItem);
			}
		}
		if (materials > 0) {
			theAnvil.takeMaterialOrCoinAmount(materials);
		}
		theAnvil.materialCost = 0;
		theAnvil.isSmithScrollCombine = false;
		theAnvil.playAnvilSound();
	}
}
