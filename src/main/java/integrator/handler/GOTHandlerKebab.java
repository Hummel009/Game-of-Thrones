package integrator.handler;

import java.util.Iterator;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.*;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.registry.*;
import got.common.database.GOTRegistry;
import got.common.tileentity.GOTTileEntityKebabStand;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class GOTHandlerKebab extends TemplateRecipeHandler {
	private GOTTileEntityKebabStand kebabStand = new GOTTileEntityKebabStand();

	@Override
	public void drawForeground(int recipe) {
		super.drawForeground(recipe);
		GuiDraw.drawRect(24, 5, 54, 18, 14995111);
		GuiDraw.drawRect(24, 41, 54, 18, 14995111);
		GuiDraw.drawRect(24, 23, 18, 18, 14995111);
		GuiDraw.drawRect(60, 23, 18, 18, 14995111);
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GuiCrafting.class;
	}

	@Override
	public String getGuiTexture() {
		return "textures/gui/container/crafting_table.png";
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.got:kebabStand.name");
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		result.stackSize = 1;
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTRegistry.kebab, 1))) {
			FMLControlledNamespacedRegistry items = GameData.getItemRegistry();
			Iterator<Item> it = items.iterator();
			while (it.hasNext()) {
				ItemStack stack = new ItemStack(it.next(), 1);
				if (kebabStand.isMeat(stack)) {
					arecipes.add(new CachedKebabRecipe(stack));
				}
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
		if (kebabStand.isMeat(ingredient)) {
			ingredient.stackSize = 1;
			arecipes.add(new CachedKebabRecipe(ingredient));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ("item".equals(inputId)) {
			loadUsageRecipes((ItemStack) ingredients[0]);
		}
	}

	@Override
	public GOTHandlerKebab newInstance() {
		return new GOTHandlerKebab();
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	public class CachedKebabRecipe extends TemplateRecipeHandler.CachedRecipe {
		private PositionedStack result;
		private PositionedStack ingredient;

		private CachedKebabRecipe(ItemStack ingredient) {
			result = new PositionedStack(new ItemStack(GOTRegistry.kebab, 1), 119, 24);
			this.ingredient = new PositionedStack(ingredient, 43, 24);
		}

		@Override
		public PositionedStack getIngredient() {
			return ingredient;
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}
	}
}
