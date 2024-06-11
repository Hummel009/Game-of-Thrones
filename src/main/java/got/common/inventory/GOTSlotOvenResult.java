package got.common.inventory;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class GOTSlotOvenResult extends SlotFurnace {
	private final InventoryPlayer inv;

	public GOTSlotOvenResult(InventoryPlayer inv, IInventory oven, int i) {
		super(inv.player, oven, i + 9, 8 + i * 18, 67);
		this.inv = inv;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		super.onPickupFromSlot(entityplayer, itemstack);
		if (!inv.player.worldObj.isRemote) {
			GOTLevelData.getData(inv.player).addAchievement(GOTAchievement.useOven);
		}
	}
}
