package integrator.handler;

import java.awt.Rectangle;
import java.util.List;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.recipe.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GOTHandlerTableShapeless extends ShapelessRecipeHandler {
	private List recipeList;
	public Class guiClass;
	private String recipeName;

	public GOTHandlerTableShapeless(List<IRecipe> recipes, Class<? extends GuiContainer> gui, String name) {
		recipeList = recipes;
		guiClass = gui;
		recipeName = name;
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return guiClass;
	}

	@Override
	public String getOverlayIdentifier() {
		return getRecipeName();
	}

	private List<IRecipe> getRecipeList() {
		return recipeList;
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("got.container.crafting." + recipeName);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		List<IRecipe> allrecipes = getRecipeList();
		for (IRecipe irecipe : allrecipes) {
			if (!NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
				continue;
			}
			ShapelessRecipeHandler.CachedShapelessRecipe recipe = null;
			if (irecipe instanceof ShapelessRecipes) {
				recipe = new ShapelessRecipeHandler.CachedShapelessRecipe();
			} else if (irecipe instanceof ShapelessOreRecipe) {
				recipe = forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
			}
			if (recipe == null) {
				continue;
			}
			arecipes.add(recipe);
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getOverlayIdentifier())) {
			List<IRecipe> allrecipes = getRecipeList();
			for (IRecipe irecipe : allrecipes) {
				ShapelessRecipeHandler.CachedShapelessRecipe recipe = null;
				if (irecipe instanceof ShapelessRecipes) {
					recipe = new ShapelessRecipeHandler.CachedShapelessRecipe();
				} else if (irecipe instanceof ShapelessOreRecipe) {
					recipe = forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
				}
				if (recipe == null) {
					continue;
				}
				arecipes.add(recipe);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84, 23, 24, 18), getOverlayIdentifier()));
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		List<IRecipe> allrecipes = getRecipeList();
		for (IRecipe irecipe : allrecipes) {
			ShapelessRecipeHandler.CachedShapelessRecipe recipe = null;
			if (irecipe instanceof ShapelessRecipes) {
				recipe = new ShapelessRecipeHandler.CachedShapelessRecipe();
			} else if (irecipe instanceof ShapelessOreRecipe) {
				recipe = forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
			}
			if (recipe == null || !recipe.contains(recipe.ingredients, ingredient)) {
				continue;
			}
			recipe.setIngredientPermutation(recipe.ingredients, ingredient);
			arecipes.add(recipe);
		}
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return new GOTHandlerTableShapeless(recipeList, guiClass, recipeName);
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}
}
