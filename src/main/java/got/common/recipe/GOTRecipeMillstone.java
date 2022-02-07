package got.common.recipe;

import java.util.*;

import cpw.mods.fml.common.registry.GameRegistry;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class GOTRecipeMillstone {
	private static Map<ItemStack, MillstoneResult> recipeList = new HashMap<>();

	private static void addCrackedBricks(ItemStack itemstack, ItemStack result) {
		GOTRecipeMillstone.addRecipe(itemstack, result, 1.0f);
		GameRegistry.addSmelting(itemstack, result, 0.1f);
	}

	private static void addRecipe(Block block, ItemStack result) {
		GOTRecipeMillstone.addRecipe(block, result, 1.0f);
	}

	private static void addRecipe(Block block, ItemStack result, float chance) {
		GOTRecipeMillstone.addRecipe(Item.getItemFromBlock(block), result, chance);
	}

	private static void addRecipe(Item item, ItemStack result, float chance) {
		GOTRecipeMillstone.addRecipe(new ItemStack(item, 1, 32767), result, chance);
	}

	private static void addRecipe(ItemStack itemstack, ItemStack result) {
		GOTRecipeMillstone.addRecipe(itemstack, result, 1.0f);
	}

	private static void addRecipe(ItemStack itemstack, ItemStack result, float chance) {
		recipeList.put(itemstack, new MillstoneResult(result, chance));
	}

	public static MillstoneResult getMillingResult(ItemStack itemstack) {
		for (Map.Entry<ItemStack, MillstoneResult> e : recipeList.entrySet()) {
			ItemStack target = e.getKey();
			MillstoneResult result = e.getValue();
			if (!GOTRecipeMillstone.matches(itemstack, target)) {
				continue;
			}
			return result;
		}
		return null;
	}

	private static boolean matches(ItemStack itemstack, ItemStack target) {
		return target.getItem() == itemstack.getItem() && (target.getItemDamage() == 32767 || target.getItemDamage() == itemstack.getItemDamage());
	}

	public static void onInit() {
		GOTRecipeMillstone.addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone));
		GOTRecipeMillstone.addRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel), 0.75f);
		GOTRecipeMillstone.addRecipe(new ItemStack(GOTRegistry.rock, 1, 0), new ItemStack(GOTRegistry.basaltGravel), 0.75f);
		GOTRecipeMillstone.addRecipe(Blocks.gravel, new ItemStack(Items.flint), 0.25f);
		GOTRecipeMillstone.addRecipe(GOTRegistry.basaltGravel, new ItemStack(Items.flint), 0.25f);
		GOTRecipeMillstone.addRecipe(GOTRegistry.obsidianGravel, new ItemStack(GOTRegistry.obsidianShard), 1.0f);
		GOTRecipeMillstone.addRecipe(GOTRegistry.oreSalt, new ItemStack(GOTRegistry.salt));
		GOTRecipeMillstone.addRecipe(new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sand, 2, 0));
		GOTRecipeMillstone.addRecipe(new ItemStack(GOTRegistry.redSandstone, 1, 0), new ItemStack(Blocks.sand, 2, 1));
		GOTRecipeMillstone.addRecipe(new ItemStack(GOTRegistry.whiteSandstone, 1, 0), new ItemStack(GOTRegistry.whiteSand, 2, 0));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(Blocks.brick_block, 1, 0), new ItemStack(GOTRegistry.redBrick, 1, 1));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stonebrick, 1, 2));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick1, 1, 0), new ItemStack(GOTRegistry.brick1, 1, 7));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick1, 1, 1), new ItemStack(GOTRegistry.brick1, 1, 3));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick1, 1, 15), new ItemStack(GOTRegistry.brick3, 1, 11));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick3, 1, 13), new ItemStack(GOTRegistry.brick3, 1, 14));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick4, 1, 0), new ItemStack(GOTRegistry.brick4, 1, 2));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick5, 1, 8), new ItemStack(GOTRegistry.brick5, 1, 10));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick5, 1, 11), new ItemStack(GOTRegistry.brick5, 1, 14));
		GOTRecipeMillstone.addCrackedBricks(new ItemStack(GOTRegistry.brick6, 1, 6), new ItemStack(GOTRegistry.brick6, 1, 4));
	}

	public static class MillstoneResult {
		private ItemStack resultItem;
		private float chance;

		public MillstoneResult(ItemStack itemstack, float f) {
			setResultItem(itemstack);
			setChance(f);
		}

		public float getChance() {
			return chance;
		}

		public ItemStack getResultItem() {
			return resultItem;
		}

		public void setChance(float chance) {
			this.chance = chance;
		}

		public void setResultItem(ItemStack resultItem) {
			this.resultItem = resultItem;
		}
	}

}
