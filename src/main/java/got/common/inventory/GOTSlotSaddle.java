package got.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTSlotSaddle extends Slot {
	public GOTSlotSaddle(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return super.isItemValid(itemstack) && itemstack.getItem() == Items.saddle && !getHasStack();
	}
}
