package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.item.GOTWeaponStats;
import got.common.util.GOTCommonIcons;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTSlotMeleeWeapon extends Slot {
	public GOTSlotMeleeWeapon(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		return GOTCommonIcons.getIconMeleeWeapon();
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return GOTWeaponStats.isMeleeWeapon(itemstack);
	}
}
