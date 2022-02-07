package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.recipe.GOTRecipeMillstone;
import got.common.tileentity.GOTTileEntityMillstone;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTContainerMillstone extends Container {
	private GOTTileEntityMillstone theMillstone;
	private int currentMillTime = 0;
	private boolean isMilling;

	public GOTContainerMillstone(InventoryPlayer inv, GOTTileEntityMillstone millstone) {
		int i;
		theMillstone = millstone;
		addSlotToContainer(new Slot(millstone, 0, 84, 25));
		addSlotToContainer(new GOTSlotMillstone(inv.player, millstone, 1, 84, 71));
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 100 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 158));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, theMillstone.getCurrentMillTime());
		crafting.sendProgressBarUpdate(this, 1, theMillstone.isMilling() ? 1 : 0);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theMillstone.isUseableByPlayer(entityplayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object element : crafters) {
			ICrafting crafting = (ICrafting) element;
			if (currentMillTime != theMillstone.getCurrentMillTime()) {
				crafting.sendProgressBarUpdate(this, 0, theMillstone.getCurrentMillTime());
			}
			if (isMilling == theMillstone.isMilling()) {
				continue;
			}
			crafting.sendProgressBarUpdate(this, 1, theMillstone.isMilling() ? 1 : 0);
		}
		currentMillTime = theMillstone.getCurrentMillTime();
		isMilling = theMillstone.isMilling();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i == 1) {
				if (!mergeItemStack(itemstack1, 2, 38, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (i != 0 ? GOTRecipeMillstone.getMillingResult(itemstack1) != null ? !mergeItemStack(itemstack1, 0, 1, false) : i >= 2 && i < 29 ? !mergeItemStack(itemstack1, 29, 38, false) : i >= 29 && i < 38 && !mergeItemStack(itemstack1, 2, 29, false) : !mergeItemStack(itemstack1, 2, 38, false)) {
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

	@SideOnly(value = Side.CLIENT)
	@Override
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			theMillstone.setCurrentMillTime(j);
		}
		if (i == 1) {
			theMillstone.setMilling(j == 1);
		}
	}
}
