package got.common.recipe;

import java.util.*;

import got.common.database.GOTRegistry;
import got.common.tileentity.GOTTileEntityBarrel;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GOTRecipeBrewing {
	private static ArrayList<ShapelessOreRecipe> recipes = new ArrayList();
	private static int BARREL_CAPACITY = 16;

	private static void addBrewingRecipe(ItemStack result, Object... ingredients) {
		if (ingredients.length != 6) {
			throw new IllegalArgumentException("Brewing recipes must contain exactly 6 items");
		}
		getRecipes().add(new ShapelessOreRecipe(result, ingredients));
	}

	public static ItemStack findMatchingRecipe(GOTTileEntityBarrel barrel) {
		for (int i = 6; i < 9; ++i) {
			ItemStack itemstack = barrel.getStackInSlot(i);
			if (GOTRecipeBrewing.isWaterSource(itemstack)) {
				continue;
			}
			return null;
		}
		block1: for (ShapelessOreRecipe recipe : getRecipes()) {
			ArrayList<Object> ingredients = new ArrayList<>(recipe.getInput());
			for (int i = 0; i < 6; ++i) {
				ItemStack itemstack = barrel.getStackInSlot(i);
				if (itemstack == null) {
					continue;
				}
				boolean inRecipe = false;
				Iterator<Object> it = ingredients.iterator();
				while (it.hasNext()) {
					boolean match = false;
					Object next = it.next();
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

	public static int getBarrelCapacity() {
		return BARREL_CAPACITY;
	}

	public static ArrayList<ShapelessOreRecipe> getRecipes() {
		return recipes;
	}

	public static boolean isWaterSource(ItemStack itemstack) {
		return itemstack != null && itemstack.getItem() == Items.water_bucket;
	}

	public static void onInit() {
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugAle, getBarrelCapacity()), Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugUnsulliedTonic, getBarrelCapacity()), "bone", "bone", "bone", "bone", "bone", "bone");
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugMead, getBarrelCapacity()), Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCider, getBarrelCapacity()), "apple", "apple", "apple", "apple", "apple", "apple");
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPerry, getBarrelCapacity()), GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear, GOTRegistry.pear);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCherryLiqueur, getBarrelCapacity()), GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry, GOTRegistry.cherry);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugRum, getBarrelCapacity()), Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPlantainBrew, getBarrelCapacity()), GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain, GOTRegistry.plantain);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugVodka, getBarrelCapacity()), Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugMapleBeer, getBarrelCapacity()), Items.wheat, Items.wheat, Items.wheat, Items.wheat, GOTRegistry.mapleSyrup, GOTRegistry.mapleSyrup);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugAraq, getBarrelCapacity()), GOTRegistry.date, GOTRegistry.date, GOTRegistry.date, GOTRegistry.date, GOTRegistry.date, GOTRegistry.date);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCarrotWine, getBarrelCapacity()), Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugBananaBeer, getBarrelCapacity()), GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana, GOTRegistry.banana);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugMelonLiqueur, getBarrelCapacity()), Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCactusLiqueur, getBarrelCapacity()), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugLemonLiqueur, getBarrelCapacity()), GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon, GOTRegistry.lemon);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugLimeLiqueur, getBarrelCapacity()), GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime, GOTRegistry.lime);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugCornLiquor, getBarrelCapacity()), GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugRedWine, getBarrelCapacity()), GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugWhiteWine, getBarrelCapacity()), GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugShadeEvening, getBarrelCapacity()), Items.dye, Items.dye, Items.dye, Items.dye, Items.dye, Items.dye);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPlumKvass, getBarrelCapacity()), Items.wheat, Items.wheat, Items.wheat, GOTRegistry.plum, GOTRegistry.plum, GOTRegistry.plum);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugTermiteTequila, getBarrelCapacity()), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, GOTRegistry.termite);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugSourMilk, getBarrelCapacity()), Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPomegranateWine, getBarrelCapacity()), GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate, GOTRegistry.pomegranate);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugEthanol, getBarrelCapacity()), Items.potato, Items.potato, Items.potato, Items.wheat, Items.wheat, Items.wheat);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugWhisky, getBarrelCapacity()), GOTRegistry.corn, GOTRegistry.corn, GOTRegistry.corn, Items.wheat, Items.wheat, Items.wheat);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugSambuca, getBarrelCapacity()), GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugGin, getBarrelCapacity()), Items.wheat, Items.wheat, Items.wheat, Items.wheat, GOTRegistry.almond, GOTRegistry.almond);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugBrandy, getBarrelCapacity()), GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.plum, GOTRegistry.plum);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugPoppyMilk, getBarrelCapacity()), Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower, Blocks.red_flower);
		GOTRecipeBrewing.addBrewingRecipe(new ItemStack(GOTRegistry.mugWildFire, getBarrelCapacity()), GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar, GOTRegistry.wildFireJar);
	}

	public static void setBarrelCapacity(int bARREL_CAPACITY) {
		BARREL_CAPACITY = bARREL_CAPACITY;
	}

	public static void setRecipes(ArrayList<ShapelessOreRecipe> recipes) {
		GOTRecipeBrewing.recipes = recipes;
	}
}