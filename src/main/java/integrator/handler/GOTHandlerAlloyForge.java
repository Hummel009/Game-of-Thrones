package integrator.handler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import got.client.gui.GOTGuiAlloyForge;
import got.common.tileentity.GOTTileEntityAlloyForge;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GOTHandlerAlloyForge extends TemplateRecipeHandler {
	private static final GOTTileEntityAlloyForge INSTANCE = new GOTTileEntityAlloyForge();

	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 12, 166, 138);
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(75, 100, 176, 0, 14, 14, 48, 7);
		drawProgressBar(75, 45, 176, 14, 24, 25, 48, 1);
	}

	private List<CachedForgeRecipe> getAlloySmeltingRecipes(ItemStack result) {
		List<CachedForgeRecipe> ret = new ArrayList<>();

		for (GOTTileEntityAlloyForge.CraftingSnapshot craftingSnapshot : GOTTileEntityAlloyForge.CRAFTING_SNAPSHOTS) {
			if (NEIServerUtils.areStacksSameTypeCrafting(result, craftingSnapshot.getResult())) {
				ret.add(new CachedForgeRecipe(craftingSnapshot.getRowUpper(), craftingSnapshot.getRowLower(), craftingSnapshot.getResult()));
			}
		}

		return ret;
	}

	private List<CachedForgeRecipe> getAlloySmeltingRecipesUsage(ItemStack ingredient) {
		List<CachedForgeRecipe> ret = new ArrayList<>();

		for (GOTTileEntityAlloyForge.CraftingSnapshot craftingSnapshot : GOTTileEntityAlloyForge.CRAFTING_SNAPSHOTS) {
			for (ItemStack itemStack : craftingSnapshot.getRowUpper()) {
				if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, itemStack)) {
					ret.add(new CachedForgeRecipe(craftingSnapshot.getRowUpper(), craftingSnapshot.getRowLower(), craftingSnapshot.getResult()));
				}
			}
			for (ItemStack itemStack : craftingSnapshot.getRowLower()) {
				if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, itemStack)) {
					ret.add(new CachedForgeRecipe(craftingSnapshot.getRowUpper(), craftingSnapshot.getRowLower(), craftingSnapshot.getResult()));
				}
			}
		}

		return ret;
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GOTGuiAlloyForge.class;
	}

	@Override
	public String getGuiTexture() {
		return "got:textures/gui/forge.png";
	}

	@Override
	public String getRecipeName() {
		return INSTANCE.getForgeName();
	}

	private void handlerCRStack(Item forgeItem, ItemStack result) {
		ItemStack stack = new ItemStack(forgeItem, 1);
		if (NEIServerUtils.areStacksSameType(GOTTileEntityAlloyForge.getSmeltingResult(stack), result)) {
			List<Object> list = new ArrayList<>();
			ArrayList<ItemStack> newList = new ArrayList<>();
			stack.getItem().getSubItems(forgeItem, null, list);
			for (Object tmp : list) {
				if (tmp instanceof ItemStack) {
					newList.add((ItemStack) tmp);
				} else if (tmp instanceof Item) {
					newList.add(new ItemStack((Item) tmp, 1, 0));
				} else if (tmp instanceof Block) {
					newList.add(new ItemStack((Block) tmp, 1, 0));
				}
			}
			list.add(stack);
			arecipes.add(new CachedForgeRecipe(null, newList.toArray(new ItemStack[0]), result));
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		result.stackSize = 1;
		FMLControlledNamespacedRegistry<Item> items = GameData.getItemRegistry();
		Iterator<Item> it = items.iterator();
		FMLControlledNamespacedRegistry<Block> blocks = GameData.getBlockRegistry();
		blocks.iterator();
		while (it.hasNext()) {
			handlerCRStack(it.next(), result);
		}
		arecipes.addAll(getAlloySmeltingRecipes(result));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ("item".equals(outputId)) {
			loadCraftingRecipes((ItemStack) results[0]);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		ItemStack tmp = GOTTileEntityAlloyForge.getSmeltingResult(ingredient);
		if (tmp != null) {
			arecipes.add(new CachedForgeRecipe(null, new ItemStack[]{ingredient}, tmp));
		}
		arecipes.addAll(getAlloySmeltingRecipesUsage(ingredient));
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ("item".equals(inputId)) {
			loadUsageRecipes((ItemStack) ingredients[0]);
		}
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	private class CachedForgeRecipe extends TemplateRecipeHandler.CachedRecipe {
		private final List<PositionedStack> ingredients;
		private final PositionedStack[] resultItem;

		private CachedForgeRecipe(ItemStack[] alloyItems, ItemStack[] forgeItems, ItemStack resultItem) {
			if (alloyItems != null) {
				for (ItemStack tmpStackA : alloyItems) {
					tmpStackA.stackSize = 1;
				}
			}
			for (ItemStack tmpStackF : forgeItems) {
				tmpStackF.stackSize = 1;
			}
			this.resultItem = new PositionedStack[4];
			ingredients = new ArrayList<>();
			if (alloyItems != null) {
				ingredients.add(new PositionedStack(alloyItems, 48, 9, true));
				ingredients.add(new PositionedStack(alloyItems, 66, 9, true));
				ingredients.add(new PositionedStack(alloyItems, 84, 9, true));
				ingredients.add(new PositionedStack(alloyItems, 102, 9, true));
			}
			ingredients.add(new PositionedStack(forgeItems, 48, 27, true));
			ingredients.add(new PositionedStack(forgeItems, 66, 27, true));
			ingredients.add(new PositionedStack(forgeItems, 84, 27, true));
			ingredients.add(new PositionedStack(forgeItems, 102, 27, true));
			this.resultItem[0] = new PositionedStack(resultItem, 48, 73);
			this.resultItem[1] = new PositionedStack(resultItem, 66, 73);
			this.resultItem[2] = new PositionedStack(resultItem, 84, 73);
			this.resultItem[3] = new PositionedStack(resultItem, 102, 73);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, ingredients);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> tmp = new ArrayList<>();
			PositionedStack tmpStack = FurnaceRecipeHandler.afuels.get(cycleticks / 48 % FurnaceRecipeHandler.afuels.size()).stack;
			tmpStack.relx = 75;
			tmpStack.rely = 117;
			tmp.add(tmpStack);
			tmp.add(resultItem[1]);
			tmp.add(resultItem[2]);
			tmp.add(resultItem[3]);
			return tmp;
		}

		@Override
		public PositionedStack getResult() {
			return resultItem[0];
		}
	}
}