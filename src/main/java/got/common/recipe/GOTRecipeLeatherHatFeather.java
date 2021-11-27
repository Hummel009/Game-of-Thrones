package got.common.recipe;

import got.GOT;
import got.common.database.GOTRegistry;
import got.common.item.other.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class GOTRecipeLeatherHatFeather implements IRecipe {
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack hat = null;
		ItemStack feather = null;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.getItem() == GOTRegistry.leatherHat && !GOTItemLeatherHat.hasFeather(itemstack)) {
				if (hat != null) {
					return null;
				}
				hat = itemstack.copy();
				continue;
			}
			if (GOT.isOreNameEqual(itemstack, "feather") || itemstack.getItem() == GOTRegistry.featherDyed) {
				if (feather != null) {
					return null;
				}
				feather = itemstack.copy();
				continue;
			}
			return null;
		}
		if (hat == null || feather == null) {
			return null;
		}
		int featherColor = 16777215;
		if (feather.getItem() == GOTRegistry.featherDyed) {
			featherColor = GOTItemFeatherDyed.getFeatherColor(feather);
		}
		GOTItemLeatherHat.setFeatherColor(hat, featherColor);
		return hat;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		ItemStack hat = null;
		ItemStack feather = null;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.getItem() == GOTRegistry.leatherHat && !GOTItemLeatherHat.hasFeather(itemstack)) {
				if (hat != null) {
					return false;
				}
				hat = itemstack;
				continue;
			}
			if (GOT.isOreNameEqual(itemstack, "feather") || itemstack.getItem() == GOTRegistry.featherDyed) {
				if (feather != null) {
					return false;
				}
				feather = itemstack;
				continue;
			}
			return false;
		}
		return hat != null && feather != null;
	}
}
