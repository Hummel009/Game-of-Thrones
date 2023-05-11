package got.common.recipe;

import got.common.database.GOTRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class GOTRecipeBrewing {
	public static List<ShapelessOreRecipe> recipes = new ArrayList<>();
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
			if (GOTRecipeBrewing.isWaterSource(itemstack)) {
				continue;
			}
			return null;
		}
		block1:
		for (ShapelessOreRecipe recipe : recipes) {
			ArrayList<Object> ingredients = new ArrayList<>(recipe.getInput());
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
						for (ItemStack item : (ArrayList<ItemStack>) next) {
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
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugAle, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugUnsulliedTonic, BARREL_CAPACITY), "bone", "bone", "bone", "bone", "bone", "bone");
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugMead, BARREL_CAPACITY), Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCider, BARREL_CAPACITY), "apple", "apple", "apple", "apple", "apple", "apple");
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPerry, BARREL_CAPACITY), GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCherryLiqueur, BARREL_CAPACITY), GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugRum, BARREL_CAPACITY), Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPlantainBrew, BARREL_CAPACITY), GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugVodka, BARREL_CAPACITY), Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugMapleBeer, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, GOTRegistry.mapleSyrup, GOTRegistry.mapleSyrup);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugAraq, BARREL_CAPACITY), GOTRegistry.date, GOTRegistry.date, GOTRegistry.date, GOTRegistry.date, GOTRegistry.date, GOTRegistry.date);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCarrotWine, BARREL_CAPACITY), Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugBananaBeer, BARREL_CAPACITY), GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugMelonLiqueur, BARREL_CAPACITY), Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCactusLiqueur, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugLemonLiqueur, BARREL_CAPACITY), GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugLimeLiqueur, BARREL_CAPACITY), GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCornLiquor, BARREL_CAPACITY), GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugRedWine, BARREL_CAPACITY), GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugWhiteWine, BARREL_CAPACITY), GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugShadeEvening, BARREL_CAPACITY), Items.dye, Items.dye, Items.dye, Items.dye, Items.dye, Items.dye);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPlumKvass, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, GOTRegistry.plum, GOTRegistry.plum, GOTRegistry.plum);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugTermiteTequila, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, GOTRegistry.termite);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugSourMilk, BARREL_CAPACITY), Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPomegranateWine, BARREL_CAPACITY), GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugEthanol, BARREL_CAPACITY), Items.potato, Items.potato, Items.potato, Items.wheat, Items.wheat, Items.wheat);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugWhisky, BARREL_CAPACITY), GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, Items.wheat, Items.wheat, Items.wheat);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugSambuca, BARREL_CAPACITY), GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugGin, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, GOTRegistry.almond, GOTRegistry.almond);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugBrandy, BARREL_CAPACITY), GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.plum, GOTRegistry.plum);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPoppyMilk, BARREL_CAPACITY), Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugWildFire, BARREL_CAPACITY), GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar);
	}
}