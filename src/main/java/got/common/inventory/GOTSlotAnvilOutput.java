package got.common.inventory;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GOTSlotAnvilOutput extends Slot {
	private final GOTContainerAnvil theAnvil;

	public GOTSlotAnvilOutput(GOTContainerAnvil container, IInventory inv, int id, int i, int j) {
		super(inv, id, i, j);
		theAnvil = container;
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		return getHasStack() && (theAnvil.getMaterialCost() <= 0 || theAnvil.hasMaterialOrCoinAmount(theAnvil.getMaterialCost()));
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		int materials = theAnvil.getMaterialCost();
		theAnvil.getInvInput().setInventorySlotContents(0, null);
		boolean wasSmithCombine = theAnvil.isSmithScrollCombine();
		ItemStack combinerItem = theAnvil.getInvInput().getStackInSlot(1);
		if (combinerItem != null) {
			--combinerItem.stackSize;
			if (combinerItem.stackSize <= 0) {
				theAnvil.getInvInput().setInventorySlotContents(1, null);
			} else {
				theAnvil.getInvInput().setInventorySlotContents(1, combinerItem);
			}
		}
		if (materials > 0) {
			theAnvil.takeMaterialOrCoinAmount(materials);
		}
		if (!entityplayer.worldObj.isRemote && wasSmithCombine) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.combineSmithScrolls);
		}
		theAnvil.setMaterialCost(0);
		theAnvil.setSmithScrollCombine(false);
		theAnvil.playAnvilSound();
	}
}