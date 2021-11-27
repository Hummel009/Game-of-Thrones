package got.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTSlotBonemeal extends Slot {
	public GOTSlotBonemeal(IInventory inv, int i, int j, int k, World world) {
		super(inv, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15;
	}
}
