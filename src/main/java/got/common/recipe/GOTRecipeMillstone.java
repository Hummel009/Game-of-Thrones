package got.common.recipe;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GOTRecipeMillstone {
	public static Map<ItemStack, MillstoneResult> recipeList = new HashMap<>();

	public static void addCrackedBricks(ItemStack itemstack, ItemStack result) {
		addRecipe(itemstack, result, 1.0f);
		GameRegistry.addSmelting(itemstack, result, 0.1f);
	}

	public static void addRecipe(Block block, ItemStack result) {
		addRecipe(block, result, 1.0f);
	}

	public static void addRecipe(Block block, ItemStack result, float chance) {
		addRecipe(Item.getItemFromBlock(block), result, chance);
	}

	public static void addRecipe(Item item, ItemStack result, float chance) {
		addRecipe(new ItemStack(item, 1, 32767), result, chance);
	}

	public static void addRecipe(ItemStack itemstack, ItemStack result) {
		addRecipe(itemstack, result, 1.0f);
	}

	public static void addRecipe(ItemStack itemstack, ItemStack result, float chance) {
		recipeList.put(itemstack, new MillstoneResult(result, chance));
	}

	public static MillstoneResult getMillingResult(ItemStack itemstack) {
		for (Map.Entry<ItemStack, MillstoneResult> e : recipeList.entrySet()) {
			ItemStack target = e.getKey();
			MillstoneResult result = e.getValue();
			if (!matches(itemstack, target)) {
				continue;
			}
			return result;
		}
		return null;
	}

	public static boolean matches(ItemStack itemstack, ItemStack target) {
		return target.getItem() == itemstack.getItem() && (target.getItemDamage() == 32767 || target.getItemDamage() == itemstack.getItemDamage());
	}

	public static void onInit() {
		addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone));
		addRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel), 0.75f);
		addRecipe(new ItemStack(GOTBlocks.rock, 1, 0), new ItemStack(GOTBlocks.basaltGravel), 0.75f);
		addRecipe(Blocks.gravel, new ItemStack(Items.flint), 0.25f);
		addRecipe(GOTBlocks.basaltGravel, new ItemStack(Items.flint), 0.25f);
		addRecipe(GOTBlocks.obsidianGravel, new ItemStack(GOTItems.obsidianShard), 1.0f);
		addRecipe(GOTBlocks.oreSalt, new ItemStack(GOTItems.salt));
		addRecipe(new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sand, 2, 0));
		addRecipe(new ItemStack(GOTBlocks.redSandstone, 1, 0), new ItemStack(Blocks.sand, 2, 1));
		addRecipe(new ItemStack(GOTBlocks.whiteSandstone, 1, 0), new ItemStack(GOTBlocks.whiteSand, 2, 0));
		addCrackedBricks(new ItemStack(Blocks.brick_block, 1, 0), new ItemStack(GOTBlocks.redBrick, 1, 1));
		addCrackedBricks(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stonebrick, 1, 2));
		addCrackedBricks(new ItemStack(GOTBlocks.brick1, 1, 0), new ItemStack(GOTBlocks.brick1, 1, 7));
		addCrackedBricks(new ItemStack(GOTBlocks.brick1, 1, 1), new ItemStack(GOTBlocks.brick1, 1, 3));
		addCrackedBricks(new ItemStack(GOTBlocks.brick1, 1, 15), new ItemStack(GOTBlocks.brick3, 1, 11));
		addCrackedBricks(new ItemStack(GOTBlocks.brick3, 1, 13), new ItemStack(GOTBlocks.brick3, 1, 14));
		addCrackedBricks(new ItemStack(GOTBlocks.brick4, 1, 0), new ItemStack(GOTBlocks.brick4, 1, 2));
		addCrackedBricks(new ItemStack(GOTBlocks.brick5, 1, 8), new ItemStack(GOTBlocks.brick5, 1, 10));
		addCrackedBricks(new ItemStack(GOTBlocks.brick5, 1, 11), new ItemStack(GOTBlocks.brick5, 1, 14));
		addCrackedBricks(new ItemStack(GOTBlocks.brick6, 1, 6), new ItemStack(GOTBlocks.brick6, 1, 4));
	}

	public static class MillstoneResult {
		public ItemStack resultItem;
		public float chance;

		public MillstoneResult(ItemStack itemstack, float f) {
			resultItem = itemstack;
			chance = f;
		}
	}

}
