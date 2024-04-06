package integrator.handler;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import got.client.gui.GOTGuiBarrel;
import got.common.recipe.GOTRecipeBrewing;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GOTHandlerBarrel extends ShapelessRecipeHandler {
	private final Collection<CachedBarrelRecipe> barrelRecipes = new ArrayList<>();
	private final Random rand = new Random();

	public GOTHandlerBarrel() {
		for (ShapelessOreRecipe rec : GOTRecipeBrewing.recipes) {
			barrelRecipes.add(getBarrelRecipe(rec));
		}
	}

	@Override
	public void drawExtras(int recipe) {
	}

	private CachedBarrelRecipe getBarrelRecipe(ShapelessOreRecipe recipe) {
		return new CachedBarrelRecipe(forgeShapelessRecipe(recipe));
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GOTGuiBarrel.class;
	}

	@Override
	public String getGuiTexture() {
		return "textures/gui/container/crafting_table.png";
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("got.container.barrel");
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		int damage = result.getItemDamage();
		for (CachedBarrelRecipe rec : barrelRecipes) {
			try {
				rec.getResult().setPermutationToRender(damage);
			} catch (ArrayIndexOutOfBoundsException e) {
				rec.getResult().setPermutationToRender(0);
			}
			if (NEIServerUtils.areStacksSameType(rec.getResult().item, result)) {
				rec.setFixedResult(true);
				arecipes.add(rec);
			}
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ("item".equals(outputId)) {
			loadCraftingRecipes((ItemStack) results[0]);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (CachedBarrelRecipe barrelRecipe : barrelRecipes) {

			List<PositionedStack> ingreds;
			if (barrelRecipe.contains(ingreds = barrelRecipe.getIngredients(), ingredient)) {

				barrelRecipe.setIngredientPermutation(ingreds, ingredient);
				barrelRecipe.setFixedResult(false);
				barrelRecipe.getResult().setPermutationToRender(0);
				arecipes.add(barrelRecipe);
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ("item".equals(inputId)) {
			loadUsageRecipes((ItemStack) ingredients[0]);
		}
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return new GOTHandlerBarrel();
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	private class CachedBarrelRecipe extends TemplateRecipeHandler.CachedRecipe {
		private final List<PositionedStack> recipeIngreds = new ArrayList<>();
		private final PositionedStack recipeResult;
		private int lastCycleR = cycleticks;
		private boolean fixedResult;

		private CachedBarrelRecipe(ShapelessRecipeHandler.CachedShapelessRecipe recipe) {
			for (int i = 0; i < recipe.getIngredients().size(); ++i) {
				PositionedStack tmp = new PositionedStack(recipe.ingredients.get(i).items, getX(i), getY(i));
				tmp.item = recipe.ingredients.get(i).item;
				tmp.generatePermutations();
				recipeIngreds.add(tmp);
			}
			recipeIngreds.add(new PositionedStack(new ItemStack(Items.water_bucket), getX(7), getY(7)));
			recipeIngreds.add(new PositionedStack(new ItemStack(Items.water_bucket), getX(8), getY(8)));
			recipeIngreds.add(new PositionedStack(new ItemStack(Items.water_bucket), getX(9), getY(9)));
			recipeResult = new PositionedStack(new ItemStack[]{new ItemStack(recipe.getResult().item.getItem(), 1, 0), new ItemStack(recipe.getResult().item.getItem(), 1, 1), new ItemStack(recipe.getResult().item.getItem(), 1, 2), new ItemStack(recipe.getResult().item.getItem(), 1, 3), new ItemStack(recipe.getResult().item.getItem(), 1, 4)}, 119, 24, true);
		}

		private PositionedStack getCycledResult(int cycle, PositionedStack result) {
			if (cycle != lastCycleR) {
				lastCycleR = cycle;
				if (!fixedResult) {
					result.setPermutationToRender(rand.nextInt(result.items.length));
				}
			}
			return result;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, recipeIngreds);
		}

		@Override
		public PositionedStack getResult() {
			return getCycledResult(cycleticks / 20, recipeResult);
		}

		private int getX(int i) {
			switch (i) {
				case 0:
				case 3:
				case 6:

				case 9:
					return 25;

				case 1:
				case 4:
				case 7:
					return 43;

				case 2:
				case 5:
				case 8:
					return 61;
			}

			return 0;
		}

		private int getY(int i) {
			switch (i) {
				case 0:
				case 1:
				case 2:
					return 6;

				case 3:
				case 4:
				case 5:
					return 24;

				case 6:
				case 7:
				case 8:

				case 9:
					return 42;
			}

			return 0;
		}

		private boolean isFixedResult() {
			return fixedResult;
		}

		private void setFixedResult(boolean fixedResult) {
			this.fixedResult = fixedResult;
		}
	}
}