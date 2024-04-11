package got.common.recipe;

import got.common.database.GOTItems;
import got.common.item.other.GOTItemDye;
import got.common.item.other.GOTItemPipe;
import net.minecraft.block.BlockColored;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class GOTRecipePipe implements IRecipe {
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack pipe = null;
		ItemStack dye = null;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.getItem() == GOTItems.pipe) {
				if (pipe != null) {
					return null;
				}
				pipe = itemstack;
				continue;
			}
			if (itemstack.getItem() == GOTItems.valyrianNugget) {
				dye = itemstack;
				continue;
			}
			if (GOTItemDye.isItemDye(itemstack) == -1) {
				return null;
			}
			dye = itemstack;
		}
		if (pipe != null && dye != null) {
			int itemDamage = pipe.getItemDamage();
			int smokeType = dye.getItem() == GOTItems.valyrianNugget ? 16 : BlockColored.func_150031_c(GOTItemDye.isItemDye(dye));
			ItemStack result = new ItemStack(GOTItems.pipe);
			result.setItemDamage(itemDamage);
			GOTItemPipe.setSmokeColor(result, smokeType);
			return result;
		}
		return null;
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
		ItemStack pipe = null;
		ItemStack dye = null;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (itemstack == null) {
				continue;
			}
			if (itemstack.getItem() == GOTItems.pipe) {
				if (pipe != null) {
					return false;
				}
				pipe = itemstack;
				continue;
			}
			if (itemstack.getItem() == GOTItems.valyrianNugget) {
				dye = itemstack;
				continue;
			}
			if (GOTItemDye.isItemDye(itemstack) == -1) {
				return false;
			}
			dye = itemstack;
		}
		return pipe != null && dye != null;
	}
}