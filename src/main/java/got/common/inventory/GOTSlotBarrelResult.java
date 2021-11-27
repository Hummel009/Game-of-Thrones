package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.item.other.GOTItemMug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTSlotBarrelResult extends Slot {
	public GOTSlotBarrelResult(IInventory inv, int i, int j, int k) {
		super(inv, i, j, k);
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		return false;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		IIcon iIcon;
		if (getSlotIndex() > 5) {
			iIcon = GOTItemMug.barrelGui_emptyMugSlotIcon;
		} else {
			iIcon = null;
		}
		return iIcon;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}
}
