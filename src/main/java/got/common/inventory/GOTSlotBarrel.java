package got.common.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.item.other.GOTItemMug;
import got.common.recipe.GOTRecipeBrewing;
import got.common.tileentity.GOTTileEntityBarrel;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTSlotBarrel extends Slot {
	private final GOTTileEntityBarrel theBarrel;
	private boolean isWater;

	public GOTSlotBarrel(GOTTileEntityBarrel inv, int i, int j, int k) {
		super(inv, i, j, k);
		theBarrel = inv;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		if (getSlotIndex() > 5) {
			return GOTItemMug.barrelGui_emptyBucketSlotIcon;
		}
		return null;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return theBarrel.barrelMode == 0 && (!isWater || GOTRecipeBrewing.isWaterSource(itemstack));
	}

	public void setWaterSource() {
		isWater = true;
	}
}
