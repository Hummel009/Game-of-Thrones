package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.tileentity.GOTTileEntityUnsmeltery;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class GOTContainerUnsmeltery extends Container {
	private GOTTileEntityUnsmeltery theUnsmeltery;
	private int currentSmeltTime = 0;
	private int forgeSmeltTime = 0;
	private int currentItemFuelValue = 0;

	public GOTContainerUnsmeltery(InventoryPlayer inv, GOTTileEntityUnsmeltery unsmeltery) {
		int i;
		theUnsmeltery = unsmeltery;
		addSlotToContainer(new Slot(unsmeltery, 0, 56, 17));
		addSlotToContainer(new Slot(unsmeltery, 1, 56, 53));
		addSlotToContainer(new GOTSlotUnsmeltResult(unsmeltery, 2, 116, 35));
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, theUnsmeltery.getCurrentSmeltTime());
		crafting.sendProgressBarUpdate(this, 1, theUnsmeltery.getForgeSmeltTime());
		crafting.sendProgressBarUpdate(this, 2, theUnsmeltery.getCurrentItemFuelValue());
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return theUnsmeltery.isUseableByPlayer(entityplayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object element : crafters) {
			ICrafting crafting = (ICrafting) element;
			if (currentSmeltTime != theUnsmeltery.getCurrentSmeltTime()) {
				crafting.sendProgressBarUpdate(this, 0, theUnsmeltery.getCurrentSmeltTime());
			}
			if (forgeSmeltTime != theUnsmeltery.getForgeSmeltTime()) {
				crafting.sendProgressBarUpdate(this, 1, theUnsmeltery.getForgeSmeltTime());
			}
			if (currentItemFuelValue == theUnsmeltery.getCurrentItemFuelValue()) {
				continue;
			}
			crafting.sendProgressBarUpdate(this, 2, theUnsmeltery.getCurrentItemFuelValue());
		}
		currentSmeltTime = theUnsmeltery.getCurrentSmeltTime();
		forgeSmeltTime = theUnsmeltery.getForgeSmeltTime();
		currentItemFuelValue = theUnsmeltery.getCurrentItemFuelValue();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i == 2) {
				if (!mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (i != 1 && i != 0 ? theUnsmeltery.canBeUnsmelted(itemstack1) ? !mergeItemStack(itemstack1, 0, 1, false) : TileEntityFurnace.isItemFuel(itemstack1) ? !mergeItemStack(itemstack1, 1, 2, false) : i >= 3 && i < 30 ? !mergeItemStack(itemstack1, 30, 39, false) : i >= 30 && i < 39 && !mergeItemStack(itemstack1, 3, 30, false) : !mergeItemStack(itemstack1, 3, 39, false)) {
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
			theUnsmeltery.setCurrentSmeltTime(j);
		}
		if (i == 1) {
			theUnsmeltery.setForgeSmeltTime(j);
		}
		if (i == 2) {
			theUnsmeltery.setCurrentItemFuelValue(j);
		}
	}
}
