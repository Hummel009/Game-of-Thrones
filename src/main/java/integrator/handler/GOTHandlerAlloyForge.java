package integrator.handler;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import got.client.gui.GOTGuiAlloyForge;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.tileentity.GOTTileEntityAlloyForge;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GOTHandlerAlloyForge extends TemplateRecipeHandler {
	public GOTTileEntityAlloyForge alloyForgeDummy = new GOTTileEntityAlloyForge();

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

	public List<CachedForgeRecipe> getAlloySmeltingRecipes(ItemStack result) {
		List<CachedForgeRecipe> ret = new ArrayList<>();
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTItems.bronzeIngot))) {
			result.stackSize = 2;
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTBlocks.oreCopper), new ItemStack(GOTItems.copperIngot)}, new ItemStack[]{new ItemStack(GOTBlocks.oreTin), new ItemStack(GOTItems.tinIngot)}, result);
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTBlocks.oreTin), new ItemStack(GOTItems.tinIngot)}, new ItemStack[]{new ItemStack(GOTBlocks.oreCopper), new ItemStack(GOTItems.copperIngot)}, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTItems.ice))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.widowWail)}, new ItemStack[]{new ItemStack(GOTItems.oathkeeper)}, result);
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.oathkeeper)}, new ItemStack[]{new ItemStack(GOTItems.widowWail)}, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTItems.valyrianPowder))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.valyrianNugget)}, new ItemStack[]{new ItemStack(GOTItems.silverIngot), new ItemStack(GOTBlocks.oreSilver)}, result);
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.silverIngot), new ItemStack(GOTBlocks.oreSilver)}, new ItemStack[]{new ItemStack(GOTItems.valyrianNugget)}, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTItems.yitiSteelIngot))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(Items.gold_nugget)}, new ItemStack[]{new ItemStack(Items.iron_ingot), new ItemStack(Blocks.iron_ore)}, result);
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(Items.iron_ingot), new ItemStack(Blocks.iron_ore)}, new ItemStack[]{new ItemStack(Items.gold_nugget)}, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTItems.alloySteelIngot))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.cobaltIngot)}, new ItemStack[]{new ItemStack(Items.iron_ingot)}, result);
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(Items.iron_ingot)}, new ItemStack[]{new ItemStack(GOTItems.cobaltIngot)}, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		return ret;
	}

	public List<CachedForgeRecipe> getAlloySmeltingRecipesUsage(ItemStack ingredient) {
		List<CachedForgeRecipe> ret = new ArrayList<>();
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTItems.copperIngot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTBlocks.oreCopper))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(GOTBlocks.oreTin), new ItemStack(GOTItems.tinIngot)}, new ItemStack(GOTItems.bronzeIngot, 2));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTBlocks.oreTin), new ItemStack(GOTItems.tinIngot)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.bronzeIngot, 2));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTItems.tinIngot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTBlocks.oreTin))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(GOTBlocks.oreCopper), new ItemStack(GOTItems.copperIngot)}, new ItemStack(GOTItems.bronzeIngot, 2));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTBlocks.oreCopper), new ItemStack(GOTItems.copperIngot)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.bronzeIngot, 2));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTItems.widowWail))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(GOTItems.oathkeeper)}, new ItemStack(GOTItems.ice));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.oathkeeper)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.ice));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTItems.oathkeeper))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(GOTItems.widowWail)}, new ItemStack(GOTItems.ice));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.widowWail)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.ice));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTItems.silverIngot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTBlocks.oreSilver))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(GOTItems.valyrianNugget)}, new ItemStack(GOTItems.valyrianPowder));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTItems.valyrianNugget)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.valyrianPowder));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTItems.valyrianNugget))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(GOTBlocks.oreSilver), new ItemStack(GOTItems.silverIngot)}, new ItemStack(GOTItems.valyrianPowder));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(GOTBlocks.oreSilver), new ItemStack(GOTItems.silverIngot)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.valyrianPowder));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(Items.iron_ingot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(Blocks.iron_ore))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(Items.gold_nugget)}, new ItemStack(GOTItems.yitiSteelIngot));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(Items.gold_nugget)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.yitiSteelIngot));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(Items.gold_nugget))) {
			CachedForgeRecipe rec1 = new CachedForgeRecipe(new ItemStack[]{ingredient}, new ItemStack[]{new ItemStack(Blocks.iron_ore), new ItemStack(Items.iron_ingot)}, new ItemStack(GOTItems.yitiSteelIngot));
			CachedForgeRecipe rec2 = new CachedForgeRecipe(new ItemStack[]{new ItemStack(Blocks.iron_ore), new ItemStack(Items.iron_ingot)}, new ItemStack[]{ingredient}, new ItemStack(GOTItems.yitiSteelIngot));
			ret.add(rec1);
			ret.add(rec2);
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
		return alloyForgeDummy.getForgeName();
	}

	public void handlerCRStack(Item forgeItem, ItemStack result) {
		ItemStack stack = new ItemStack(forgeItem, 1);
		if (NEIServerUtils.areStacksSameType(alloyForgeDummy.getSmeltingResult(stack), result)) {
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
		ItemStack tmp = alloyForgeDummy.getSmeltingResult(ingredient);
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

	public class CachedForgeRecipe extends TemplateRecipeHandler.CachedRecipe {
		public List<PositionedStack> ingredients;
		public PositionedStack[] resultItem;
		public int fuelX;
		public int fuelY;

		public CachedForgeRecipe(ItemStack[] alloyItems, ItemStack[] forgeItems, ItemStack resultItem) {
			fuelX = 75;
			fuelY = 117;
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
			tmpStack.relx = fuelX;
			tmpStack.rely = fuelY;
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
