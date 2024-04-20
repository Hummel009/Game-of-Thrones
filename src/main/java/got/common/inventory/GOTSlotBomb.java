package got.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.other.GOTBlockBomb;
import got.common.util.GOTCommonIcons;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTSlotBomb extends Slot {
	public GOTSlotBomb(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		return GOTCommonIcons.iconBomb;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return itemstack != null && Block.getBlockFromItem(itemstack.getItem()) instanceof GOTBlockBomb;
	}
}