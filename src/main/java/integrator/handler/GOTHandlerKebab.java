package integrator.handler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import got.common.database.GOTItems;
import got.common.tileentity.GOTTileEntityKebabStand;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTHandlerKebab extends TemplateRecipeHandler {
	private static final GOTTileEntityKebabStand INSTANCE = new GOTTileEntityKebabStand();

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
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTItems.kebab, 1))) {
			FMLControlledNamespacedRegistry<Item> items = GameData.getItemRegistry();
			for (Item item : (Iterable<Item>) items) {
				ItemStack stack = new ItemStack(item, 1);
				if (INSTANCE.isMeat(stack)) {
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
		if (INSTANCE.isMeat(ingredient)) {
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

	private class CachedKebabRecipe extends TemplateRecipeHandler.CachedRecipe {
		private final PositionedStack result = new PositionedStack(new ItemStack(GOTItems.kebab, 1), 119, 24);
		private final PositionedStack ingredient;

		private CachedKebabRecipe(ItemStack ingredient) {
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
