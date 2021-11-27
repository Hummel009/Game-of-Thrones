package got.common.recipe;

import got.common.item.other.GOTItemBanner;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTRecipeBanners implements IRecipe {
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack baseBanner = null;
		int emptyBanners = 0;
		for (int pass = 0; pass < 2; ++pass) {
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				ItemStack itemstack = inv.getStackInSlot(i);
				if (itemstack == null) {
					continue;
				}
				if (itemstack.getItem() instanceof GOTItemBanner) {
					NBTTagCompound data = GOTItemBanner.getProtectionData(itemstack);
					if (pass == 0 && data != null) {
						if (baseBanner != null) {
							return null;
						}
						baseBanner = itemstack;
					}
					if (pass != 1) {
						continue;
					}
					if (baseBanner != null && itemstack.getItemDamage() == baseBanner.getItemDamage()) {
						if (data != null) {
							continue;
						}
						++emptyBanners;
						continue;
					}
				}
				return null;
			}
		}
		if (baseBanner == null) {
			return null;
		}
		if (emptyBanners > 0) {
			ItemStack result = baseBanner.copy();
			result.stackSize = emptyBanners + 1;
			return result;
		}
		ItemStack result = baseBanner.copy();
		result.stackSize = 1;
		result.setTagCompound(null);
		return result;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return getCraftingResult(inv) != null;
	}
}
