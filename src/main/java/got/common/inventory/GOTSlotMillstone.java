package got.common.inventory;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GOTSlotMillstone extends Slot {
	private final EntityPlayer thePlayer;

	private int itemsTaken;

	public GOTSlotMillstone(EntityPlayer entityplayer, IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
		thePlayer = entityplayer;
	}

	@Override
	public ItemStack decrStackSize(int i) {
		if (getHasStack()) {
			itemsTaken += Math.min(i, getStack().stackSize);
		}
		return super.decrStackSize(i);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}

	@Override
	public void onCrafting(ItemStack itemstack) {
		itemstack.onCrafting(thePlayer.worldObj, thePlayer, itemsTaken);
		itemsTaken = 0;
		if (!thePlayer.worldObj.isRemote && itemstack.getItem() == GOTItems.obsidianShard) {
			GOTLevelData.getData(thePlayer).addAchievement(GOTAchievement.smeltObsidianShard);
		}
	}

	@Override
	public void onCrafting(ItemStack itemstack, int i) {
		itemsTaken += i;
		onCrafting(itemstack);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		onCrafting(itemstack);
		super.onPickupFromSlot(entityplayer, itemstack);
	}
}
