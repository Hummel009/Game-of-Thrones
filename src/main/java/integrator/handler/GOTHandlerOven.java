package integrator.handler;

import java.util.*;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.*;
import codechicken.nei.recipe.*;
import got.client.gui.GOTGuiOven;
import got.common.tileentity.GOTTileEntityOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.StatCollector;

public class GOTHandlerOven extends TemplateRecipeHandler {
	public GOTHandlerOven() {
		new GOTTileEntityOven();
	}

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 120);
	}

	@Override
	public void drawExtras(int recipe) {
		this.drawProgressBar(75, 83, 176, 0, 14, 14, 48, 7);
		this.drawProgressBar(75, 29, 176, 14, 24, 25, 48, 1);
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GOTGuiOven.class;
	}

	@Override
	public String getGuiTexture() {
		return "got:textures/gui/oven.png";
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("got.container.oven");
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		result.stackSize = 1;
		if (GOTTileEntityOven.isCookResultAcceptable(result)) {
			Map map = FurnaceRecipes.smelting().getSmeltingList();
			Iterator<ItemStack> it = map.keySet().iterator();
			while (it.hasNext()) {
				ItemStack itemStack = it.next();
				if (NEIServerUtils.areStacksSameTypeCrafting(FurnaceRecipes.smelting().getSmeltingResult(itemStack), result)) {
					arecipes.add(new CachedOvenRecipe(itemStack, result));
				}
			}
		}
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ("item".equals(outputId)) {
			this.loadCraftingRecipes((ItemStack) results[0]);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ingredient.stackSize = 1;
		ItemStack tmp = FurnaceRecipes.smelting().getSmeltingResult(ingredient);
		if (tmp != null && GOTTileEntityOven.isCookResultAcceptable(tmp)) {
			arecipes.add(new CachedOvenRecipe(ingredient, tmp));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ("item".equals(inputId)) {
			this.loadUsageRecipes((ItemStack) ingredients[0]);
		}
	}

	@Override
	public GOTHandlerOven newInstance() {
		return new GOTHandlerOven();
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	public class CachedOvenRecipe extends TemplateRecipeHandler.CachedRecipe {
		public ArrayList<PositionedStack> ingredients;
		public ArrayList<PositionedStack> results;
		public int fuelX;
		public int fuelY;

		public CachedOvenRecipe(ItemStack ingredient, ItemStack result) {
			ingredients = new ArrayList();
			results = new ArrayList();
			fuelX = 75;
			fuelY = 100;
			for (int i = 0; i < 9; ++i) {
				ingredients.add(new PositionedStack(ingredient, 18 * i + 3, 10));
				results.add(new PositionedStack(result, 18 * i + 3, 56));
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(GOTHandlerOven.this.cycleticks / 48, ingredients);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			ArrayList<PositionedStack> tmp = new ArrayList<>();
			PositionedStack tmpStack = FurnaceRecipeHandler.afuels.get(GOTHandlerOven.this.cycleticks / 48 % FurnaceRecipeHandler.afuels.size()).stack;
			tmpStack.relx = fuelX;
			tmpStack.rely = fuelY;
			tmp.add(tmpStack);
			tmp.addAll(1, results);
			return tmp;
		}

		@Override
		public PositionedStack getResult() {
			return results.get(0);
		}
	}

}
