package got.common.inventory;

import got.common.item.other.GOTItemPouch;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTSlotPouch extends Slot {
	public GOTSlotPouch(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (itemstack.getItem() instanceof GOTItemPouch) {
			return false;
		}
		return super.isItemValid(itemstack);
	}
}
