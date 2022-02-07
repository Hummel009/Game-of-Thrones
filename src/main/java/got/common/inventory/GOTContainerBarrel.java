package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.recipe.GOTRecipeBrewing;
import got.common.tileentity.GOTTileEntityBarrel;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTContainerBarrel extends Container {
	private GOTTileEntityBarrel theBarrel;
	private int barrelMode = 0;
	private int brewingTime = 0;

	public GOTContainerBarrel(InventoryPlayer inv, GOTTileEntityBarrel barrel) {
		int i;
		int j;
		setTheBarrel(barrel);
		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 3; ++j) {
				GOTSlotBarrel slot = new GOTSlotBarrel(barrel, j + i * 3, 14 + j * 18, 34 + i * 18);
				if (i == 2) {
					slot.setWaterSource();
				}
				addSlotToContainer(slot);
			}
		}
		addSlotToContainer(new GOTSlotBarrelResult(barrel, 9, 108, 52));
		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inv, j + i * 9 + 9, 25 + j * 18, 139 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inv, i, 25 + i * 18, 197));
		}
		if (!barrel.getWorldObj().isRemote && inv.player instanceof EntityPlayerMP) {
			barrel.getPlayers().add(inv.player);
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, getTheBarrel().getBarrelMode());
		crafting.sendProgressBarUpdate(this, 1, getTheBarrel().getBrewingTime());
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return getTheBarrel().isUseableByPlayer(entityplayer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object crafter : crafters) {
			ICrafting crafting = (ICrafting) crafter;
			if (barrelMode != getTheBarrel().getBarrelMode()) {
				crafting.sendProgressBarUpdate(this, 0, getTheBarrel().getBarrelMode());
			}
			if (brewingTime == getTheBarrel().getBrewingTime()) {
				continue;
			}
			crafting.sendProgressBarUpdate(this, 1, getTheBarrel().getBrewingTime());
		}
		barrelMode = getTheBarrel().getBarrelMode();
		brewingTime = getTheBarrel().getBrewingTime();
	}

	public GOTTileEntityBarrel getTheBarrel() {
		return theBarrel;
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!getTheBarrel().getWorldObj().isRemote && entityplayer instanceof EntityPlayerMP) {
			getTheBarrel().getPlayers().remove(entityplayer);
		}
	}

	public void setTheBarrel(GOTTileEntityBarrel theBarrel) {
		this.theBarrel = theBarrel;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 9) {
				if (!mergeItemStack(itemstack1, 10, 46, true)) {
					return null;
				}
			} else if (i != 9) {
				boolean flag = false;
				Slot aBarrelSlot = (Slot) inventorySlots.get(0);
				if (aBarrelSlot.isItemValid(itemstack1)) {
					flag = GOTRecipeBrewing.isWaterSource(itemstack1) ? mergeItemStack(itemstack1, 6, 9, false) : mergeItemStack(itemstack1, 0, 6, false);
				}
				if (!flag && (i >= 10 && i < 37 ? !mergeItemStack(itemstack1, 37, 46, false) : !mergeItemStack(itemstack1, 10, 37, false))) {
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

	@Override
	@SideOnly(value = Side.CLIENT)
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			getTheBarrel().setBarrelMode(j);
		}
		if (i == 1) {
			getTheBarrel().setBrewingTime(j);
		}
	}
}
