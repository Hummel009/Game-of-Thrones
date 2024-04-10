package got.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.item.other.GOTItemMug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
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

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		if (getSlotIndex() > 5) {
			return GOTItemMug.getBarrelGuiEmptyMugSlotIcon();
		}
		return null;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}
}
