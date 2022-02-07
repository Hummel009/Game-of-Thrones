package got.common.recipe;

import java.util.*;

import cpw.mods.fml.common.registry.GameRegistry;
import got.common.item.other.GOTItemMug;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GOTRecipeVessels {
	public static void addRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
		List<IRecipe> recipes = GOTRecipeVessels.generateRecipes(result, drinkBase, ingredients);
		for (IRecipe r : recipes) {
			GameRegistry.addRecipe(r);
		}
	}

	public static void addRecipes(ItemStack result, Object[] ingredients) {
		GOTRecipeVessels.addRecipes(result, null, ingredients);
	}

	private static List<IRecipe> generateRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
		ArrayList<IRecipe> recipes = new ArrayList<>();
		for (GOTItemMug.Vessel v : GOTItemMug.Vessel.values()) {
			ArrayList<Object> vIngredients = new ArrayList<>();
			ItemStack vBase = v.getEmptyVessel();
			if (drinkBase != null) {
				vBase = new ItemStack(drinkBase);
				GOTItemMug.setVessel(vBase, v, true);
			}
			vIngredients.add(vBase);
			vIngredients.addAll(Arrays.asList(ingredients));
			ItemStack vResult = result.copy();
			GOTItemMug.setVessel(vResult, v, true);
			ShapelessOreRecipe recipe = new ShapelessOreRecipe(vResult, vIngredients.toArray());
			recipes.add(recipe);
		}
		return recipes;
	}
}
