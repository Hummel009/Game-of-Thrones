package got.common.recipe;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Collection;

public class GOTRecipeBrewing {
	public static Collection<ShapelessOreRecipe> recipes = new ArrayList<>();
	public static int BARREL_CAPACITY = 16;

	public static void addBrewingRecipe(ItemStack result, Object... ingredients) {
		if (ingredients.length != 6) {
			throw new IllegalArgumentException("Brewing recipes must contain exactly 6 items");
		}
		recipes.add(new ShapelessOreRecipe(result, ingredients));
	}

	public static ItemStack findMatchingRecipe(IInventory barrel) {
		for (int i = 6; i < 9; ++i) {
			ItemStack itemstack = barrel.getStackInSlot(i);
			if (isWaterSource(itemstack)) {
				continue;
			}
			return null;
		}
		block1:
		for (ShapelessOreRecipe recipe : recipes) {
			Collection<Object> ingredients = new ArrayList<>(recipe.getInput());
			for (int i = 0; i < 6; ++i) {
				ItemStack itemstack = barrel.getStackInSlot(i);
				if (itemstack == null) {
					continue;
				}
				boolean inRecipe = false;
				for (Object next : ingredients) {
					boolean match = false;
					if (next instanceof ItemStack) {
						match = GOTRecipe.checkItemEquals((ItemStack) next, itemstack);
					} else if (next instanceof ArrayList) {
						for (ItemStack item : (Iterable<ItemStack>) next) {
							match = match || GOTRecipe.checkItemEquals(item, itemstack);
						}
					}
					if (!match) {
						continue;
					}
					inRecipe = true;
					ingredients.remove(next);
					break;
				}
				if (!inRecipe) {
					continue block1;
				}
			}
			if (!ingredients.isEmpty()) {
				continue;
			}
			return recipe.getRecipeOutput().copy();
		}
		return null;
	}

	public static boolean isWaterSource(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.water_bucket;
	}

	public static void onInit() {
		addBrewingRecipe(new ItemStack(GOTItems.mugAle, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat);
		addBrewingRecipe(new ItemStack(GOTItems.mugUnsulliedTonic, BARREL_CAPACITY), "bone", "bone", "bone", "bone", "bone", "bone");
		addBrewingRecipe(new ItemStack(GOTItems.mugMead, BARREL_CAPACITY), Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar);
		addBrewingRecipe(new ItemStack(GOTItems.mugCider, BARREL_CAPACITY), "apple", "apple", "apple", "apple", "apple", "apple");
		addBrewingRecipe(new ItemStack(GOTItems.mugPerry, BARREL_CAPACITY), GOTItems.pear, GOTItems.pear, GOTItems.pear, GOTItems.pear, GOTItems.pear, GOTItems.pear);
		addBrewingRecipe(new ItemStack(GOTItems.mugCherryLiqueur, BARREL_CAPACITY), GOTItems.cherry, GOTItems.cherry, GOTItems.cherry, GOTItems.cherry, GOTItems.cherry, GOTItems.cherry);
		addBrewingRecipe(new ItemStack(GOTItems.mugRum, BARREL_CAPACITY), Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds);
		addBrewingRecipe(new ItemStack(GOTItems.mugPlantainBrew, BARREL_CAPACITY), GOTBlocks.plantain, GOTBlocks.plantain, GOTBlocks.plantain, GOTBlocks.plantain, GOTBlocks.plantain, GOTBlocks.plantain);
		addBrewingRecipe(new ItemStack(GOTItems.mugVodka, BARREL_CAPACITY), Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato);
		addBrewingRecipe(new ItemStack(GOTItems.mugMapleBeer, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, GOTItems.mapleSyrup, GOTItems.mapleSyrup);
		addBrewingRecipe(new ItemStack(GOTItems.mugAraq, BARREL_CAPACITY), GOTItems.date, GOTItems.date, GOTItems.date, GOTItems.date, GOTItems.date, GOTItems.date);
		addBrewingRecipe(new ItemStack(GOTItems.mugCarrotWine, BARREL_CAPACITY), Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot);
		addBrewingRecipe(new ItemStack(GOTItems.mugBananaBeer, BARREL_CAPACITY), GOTItems.banana, GOTItems.banana, GOTItems.banana, GOTItems.banana, GOTItems.banana, GOTItems.banana);
		addBrewingRecipe(new ItemStack(GOTItems.mugMelonLiqueur, BARREL_CAPACITY), Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon);
		addBrewingRecipe(new ItemStack(GOTItems.mugCactusLiqueur, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus);
		addBrewingRecipe(new ItemStack(GOTItems.mugLemonLiqueur, BARREL_CAPACITY), GOTItems.lemon, GOTItems.lemon, GOTItems.lemon, GOTItems.lemon, GOTItems.lemon, GOTItems.lemon);
		addBrewingRecipe(new ItemStack(GOTItems.mugLimeLiqueur, BARREL_CAPACITY), GOTItems.lime, GOTItems.lime, GOTItems.lime, GOTItems.lime, GOTItems.lime, GOTItems.lime);
		addBrewingRecipe(new ItemStack(GOTItems.mugCornLiquor, BARREL_CAPACITY), GOTItems.corn, GOTItems.corn, GOTItems.corn, GOTItems.corn, GOTItems.corn, GOTItems.corn);
		addBrewingRecipe(new ItemStack(GOTItems.mugRedWine, BARREL_CAPACITY), GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.grapeRed);
		addBrewingRecipe(new ItemStack(GOTItems.mugWhiteWine, BARREL_CAPACITY), GOTItems.grapeWhite, GOTItems.grapeWhite, GOTItems.grapeWhite, GOTItems.grapeWhite, GOTItems.grapeWhite, GOTItems.grapeWhite);
		addBrewingRecipe(new ItemStack(GOTItems.mugShadeEvening, BARREL_CAPACITY), Items.dye, Items.dye, Items.dye, Items.dye, Items.dye, Items.dye);
		addBrewingRecipe(new ItemStack(GOTItems.mugPlumKvass, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, GOTItems.plum, GOTItems.plum, GOTItems.plum);
		addBrewingRecipe(new ItemStack(GOTItems.mugTermiteTequila, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, GOTItems.termite);
		addBrewingRecipe(new ItemStack(GOTItems.mugSourMilk, BARREL_CAPACITY), Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket);
		addBrewingRecipe(new ItemStack(GOTItems.mugPomegranateWine, BARREL_CAPACITY), GOTItems.pomegranate, GOTItems.pomegranate, GOTItems.pomegranate, GOTItems.pomegranate, GOTItems.pomegranate, GOTItems.pomegranate);
		addBrewingRecipe(new ItemStack(GOTItems.mugEthanol, BARREL_CAPACITY), Items.potato, Items.potato, Items.potato, Items.wheat, Items.wheat, Items.wheat);
		addBrewingRecipe(new ItemStack(GOTItems.mugWhisky, BARREL_CAPACITY), GOTItems.corn, GOTItems.corn, GOTItems.corn, Items.wheat, Items.wheat, Items.wheat);
		addBrewingRecipe(new ItemStack(GOTItems.mugSambuca, BARREL_CAPACITY), GOTItems.elderberry, GOTItems.elderberry, GOTItems.elderberry, GOTItems.elderberry, GOTItems.elderberry, GOTItems.elderberry);
		addBrewingRecipe(new ItemStack(GOTItems.mugGin, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, GOTItems.almond, GOTItems.almond);
		addBrewingRecipe(new ItemStack(GOTItems.mugBrandy, BARREL_CAPACITY), GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.plum, GOTItems.plum);
		addBrewingRecipe(new ItemStack(GOTItems.mugPoppyMilk, BARREL_CAPACITY), Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower);
		addBrewingRecipe(new ItemStack(GOTItems.mugWildFire, BARREL_CAPACITY), GOTBlocks.wildFireJar, GOTBlocks.wildFireJar, GOTBlocks.wildFireJar, GOTBlocks.wildFireJar, GOTBlocks.wildFireJar, GOTBlocks.wildFireJar);
	}
}