package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.item.other.GOTItemMug;
import got.common.recipe.GOTRecipeBrewing;
import got.common.tileentity.GOTTileEntityBarrel;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTSlotBarrel extends Slot {
	private GOTTileEntityBarrel theBarrel;
	private boolean isWater;

	public GOTSlotBarrel(GOTTileEntityBarrel inv, int i, int j, int k) {
		super(inv, i, j, k);
		theBarrel = inv;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		IIcon iIcon;
		if (getSlotIndex() > 5) {
			iIcon = GOTItemMug.barrelGui_emptyBucketSlotIcon;
		} else {
			iIcon = null;
		}
		return iIcon;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if (theBarrel.getBarrelMode() == 0) {
			if (isWater) {
				return GOTRecipeBrewing.isWaterSource(itemstack);
			}
			return true;
		}
		return false;
	}

	public GOTSlotBarrel setWaterSource() {
		isWater = true;
		return this;
	}
}
