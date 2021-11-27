package integrator.handler;

import java.util.*;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.*;
import codechicken.nei.recipe.*;
import cpw.mods.fml.common.registry.*;
import got.client.gui.GOTGuiAlloyForge;
import got.common.database.GOTRegistry;
import got.common.tileentity.GOTTileEntityAlloyForge;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class GOTHandlerAlloyForge extends TemplateRecipeHandler {
	public GOTTileEntityAlloyForge alloyForgeDummy;

	public GOTHandlerAlloyForge() {
		alloyForgeDummy = new GOTTileEntityAlloyForge();
	}

	@Override
	public void drawBackground(final int recipe) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 12, 166, 138);
	}

	@Override
	public void drawExtras(final int recipe) {
		this.drawProgressBar(75, 100, 176, 0, 14, 14, 48, 7);
		this.drawProgressBar(75, 45, 176, 14, 24, 25, 48, 1);
	}

	public ArrayList<GOTHandlerAlloyForge.CachedForgeRecipe> getAlloySmeltingRecipes(final ItemStack result) {
		final ArrayList<GOTHandlerAlloyForge.CachedForgeRecipe> ret = new ArrayList<>();
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTRegistry.bronzeIngot))) {
			result.stackSize = 2;
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.oreCopper), new ItemStack(GOTRegistry.copperIngot) }, new ItemStack[] { new ItemStack(GOTRegistry.oreTin), new ItemStack(GOTRegistry.tinIngot) }, result);
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.oreTin), new ItemStack(GOTRegistry.tinIngot) }, new ItemStack[] { new ItemStack(GOTRegistry.oreCopper), new ItemStack(GOTRegistry.copperIngot) }, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTRegistry.ice))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.widowWail) }, new ItemStack[] { new ItemStack(GOTRegistry.oathkeeper) }, result);
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.oathkeeper) }, new ItemStack[] { new ItemStack(GOTRegistry.widowWail) }, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTRegistry.valyrianPowder))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.valyrianNugget) }, new ItemStack[] { new ItemStack(GOTRegistry.silverIngot), new ItemStack(GOTRegistry.oreSilver) }, result);
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.silverIngot), new ItemStack(GOTRegistry.oreSilver) }, new ItemStack[] { new ItemStack(GOTRegistry.valyrianNugget) }, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTRegistry.yitiSteelIngot))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(Items.gold_nugget) }, new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Blocks.iron_ore) }, result);
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Blocks.iron_ore) }, new ItemStack[] { new ItemStack(Items.gold_nugget) }, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(result, new ItemStack(GOTRegistry.alloySteelIgnot))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.cobaltIngot) }, new ItemStack[] { new ItemStack(Items.iron_ingot) }, result);
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(Items.iron_ingot) }, new ItemStack[] { new ItemStack(GOTRegistry.cobaltIngot) }, result);
			ret.add(rec1);
			ret.add(rec2);
		}
		return ret;
	}

	public ArrayList<GOTHandlerAlloyForge.CachedForgeRecipe> getAlloySmeltingRecipesUsage(final ItemStack ingredient) {
		final ArrayList<GOTHandlerAlloyForge.CachedForgeRecipe> ret = new ArrayList<>();
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.copperIngot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.oreCopper))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(GOTRegistry.oreTin), new ItemStack(GOTRegistry.tinIngot) }, new ItemStack(GOTRegistry.bronzeIngot, 2));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.oreTin), new ItemStack(GOTRegistry.tinIngot) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.bronzeIngot, 2));
			ret.add(rec1);
			ret.add(rec2);
		} else if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.tinIngot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.oreTin))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(GOTRegistry.oreCopper), new ItemStack(GOTRegistry.copperIngot) }, new ItemStack(GOTRegistry.bronzeIngot, 2));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.oreCopper), new ItemStack(GOTRegistry.copperIngot) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.bronzeIngot, 2));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.widowWail))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(GOTRegistry.oathkeeper) }, new ItemStack(GOTRegistry.ice));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.oathkeeper) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.ice));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.oathkeeper))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(GOTRegistry.widowWail) }, new ItemStack(GOTRegistry.ice));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.widowWail) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.ice));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.silverIngot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.oreSilver))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(GOTRegistry.valyrianNugget) }, new ItemStack(GOTRegistry.valyrianPowder));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.valyrianNugget) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.valyrianPowder));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(GOTRegistry.valyrianNugget))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(GOTRegistry.oreSilver), new ItemStack(GOTRegistry.silverIngot) }, new ItemStack(GOTRegistry.valyrianPowder));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(GOTRegistry.oreSilver), new ItemStack(GOTRegistry.silverIngot) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.valyrianPowder));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(Items.iron_ingot)) || NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(Blocks.iron_ore))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(Items.gold_nugget) }, new ItemStack(GOTRegistry.yitiSteelIngot));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(Items.gold_nugget) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.yitiSteelIngot));
			ret.add(rec1);
			ret.add(rec2);
		}
		if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, new ItemStack(Items.gold_nugget))) {
			final GOTHandlerAlloyForge.CachedForgeRecipe rec1 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { ingredient }, new ItemStack[] { new ItemStack(Blocks.iron_ore), new ItemStack(Items.iron_ingot) }, new ItemStack(GOTRegistry.yitiSteelIngot));
			final GOTHandlerAlloyForge.CachedForgeRecipe rec2 = new GOTHandlerAlloyForge.CachedForgeRecipe(new ItemStack[] { new ItemStack(Blocks.iron_ore), new ItemStack(Items.iron_ingot) }, new ItemStack[] { ingredient }, new ItemStack(GOTRegistry.yitiSteelIngot));
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
		return "got:gui/forge.png";
	}

	@Override
	public String getRecipeName() {
		return alloyForgeDummy.getForgeName();
	}

	public void handlerCRStack(final Item forgeItem, final ItemStack result) {
		final ItemStack stack = new ItemStack(forgeItem, 1);
		if (NEIServerUtils.areStacksSameType(alloyForgeDummy.getSmeltingResult(stack), result)) {
			final ArrayList list = new ArrayList();
			stack.getItem().getSubItems(forgeItem, (CreativeTabs) null, list);
			for (final Object tmp : list) {
				if (tmp instanceof ItemStack) {
					continue;
				}
				if (tmp instanceof Item) {
					list.remove(tmp);
					list.add(new ItemStack((Item) tmp, 1, 0));
				} else if (tmp instanceof Block) {
					list.remove(tmp);
					list.add(new ItemStack((Block) tmp, 1, 0));
				} else {
					list.remove(tmp);
				}
			}
			list.add(stack);
			arecipes.add(new GOTHandlerAlloyForge.CachedForgeRecipe((ItemStack[]) null, (ItemStack[]) list.toArray(new ItemStack[list.size()]), result));
		}
	}

	@Override
	public void loadCraftingRecipes(final ItemStack result) {
		result.stackSize = 1;
		final FMLControlledNamespacedRegistry<Item> items = GameData.getItemRegistry();
		final Iterator<Item> it = items.iterator();
		final FMLControlledNamespacedRegistry<Block> blocks = GameData.getBlockRegistry();
		blocks.iterator();
		while (it.hasNext()) {
			handlerCRStack(it.next(), result);
		}
		arecipes.addAll(getAlloySmeltingRecipes(result));
	}

	@Override
	public void loadCraftingRecipes(final String outputId, final Object... results) {
		if ("item".equals(outputId)) {
			this.loadCraftingRecipes((ItemStack) results[0]);
		}
	}

	@Override
	public void loadUsageRecipes(final ItemStack ingredient) {
		final ItemStack tmp = alloyForgeDummy.getSmeltingResult(ingredient);
		if (tmp != null) {
			arecipes.add(new GOTHandlerAlloyForge.CachedForgeRecipe((ItemStack[]) null, new ItemStack[] { ingredient }, tmp));
		}
		arecipes.addAll(getAlloySmeltingRecipesUsage(ingredient));
	}

	@Override
	public void loadUsageRecipes(final String inputId, final Object... ingredients) {
		if ("item".equals(inputId)) {
			this.loadUsageRecipes((ItemStack) ingredients[0]);
		}
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	public class CachedForgeRecipe extends TemplateRecipeHandler.CachedRecipe {
		public ArrayList<PositionedStack> ingredients;
		public PositionedStack[] resultItem;
		public int fuelX;
		public int fuelY;

		public CachedForgeRecipe(final ItemStack[] alloyItems, final ItemStack[] forgeItems, final ItemStack resultItem) {
			this.resultItem = null;
			fuelX = 75;
			fuelY = 117;
			if (alloyItems != null) {
				for (final ItemStack tmpStackA : alloyItems) {
					tmpStackA.stackSize = 1;
				}
			}
			for (final ItemStack tmpStackF : forgeItems) {
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
			return getCycledIngredients(GOTHandlerAlloyForge.this.cycleticks / 48, ingredients);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			final ArrayList<PositionedStack> tmp = new ArrayList<>();
			final PositionedStack tmpStack = FurnaceRecipeHandler.afuels.get(GOTHandlerAlloyForge.this.cycleticks / 48 % FurnaceRecipeHandler.afuels.size()).stack;
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