package got.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GOTSlotBonemeal extends Slot {
	public GOTSlotBonemeal(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15;
	}
}
