package got.common.inventory;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GOTSlotUnsmeltResult extends GOTSlotProtected {
	public GOTSlotUnsmeltResult(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		super.onPickupFromSlot(entityplayer, itemstack);
		if (!entityplayer.worldObj.isRemote) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.unsmelt);
		}
	}
}