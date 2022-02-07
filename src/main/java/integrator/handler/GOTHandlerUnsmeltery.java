package integrator.handler;

import java.util.Iterator;

import codechicken.core.ReflectionManager;
import codechicken.nei.*;
import codechicken.nei.recipe.*;
import cpw.mods.fml.common.registry.*;
import got.client.gui.GOTGuiUnsmeltery;
import got.common.tileentity.GOTTileEntityUnsmeltery;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class GOTHandlerUnsmeltery extends FurnaceRecipeHandler {
	private static GOTTileEntityUnsmeltery unsmelteryTileEntity = new GOTTileEntityUnsmeltery();

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GOTGuiUnsmeltery.class;
	}

	@Override
	public String getGuiTexture() {
		return "got:textures/gui/unsmelter.png";
	}

	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("got.container.unsmeltery");
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		FMLControlledNamespacedRegistry items = GameData.getItemRegistry();
		Iterator it = items.iterator();
		while (it.hasNext()) {
			ItemStack stack = new ItemStack((Item) it.next(), 1);
			ItemStack equipmentMaterial = GOTTileEntityUnsmeltery.getEquipmentMaterial(stack);
			if (equipmentMaterial == null || !NEIServerUtils.areStacksSameTypeCrafting(equipmentMaterial, result)) {
				continue;
			}
			ItemStack randomResult = GOTHandlerUnsmeltery.getRandomUnsmelteryResult(stack);
			UnsmeltingPair pair = new UnsmeltingPair(stack, randomResult != null ? randomResult : equipmentMaterial);
			arecipes.add(pair);
		}
		FMLControlledNamespacedRegistry blocks = GameData.getBlockRegistry();
		Iterator it2 = blocks.iterator();
		while (it2.hasNext()) {
			ItemStack stack = new ItemStack((Block) it2.next(), 1);
			ItemStack equipmentMaterial = GOTTileEntityUnsmeltery.getEquipmentMaterial(stack);
			if (equipmentMaterial == null || !NEIServerUtils.areStacksSameTypeCrafting(equipmentMaterial, result)) {
				continue;
			}
			ItemStack randomResult = GOTHandlerUnsmeltery.getRandomUnsmelteryResult(stack);
			UnsmeltingPair pair = new UnsmeltingPair(stack, randomResult != null ? randomResult : equipmentMaterial);
			arecipes.add(pair);
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
		ItemStack resultStack = GOTHandlerUnsmeltery.getRandomUnsmelteryResult(ingredient);
		if (resultStack != null) {
			UnsmeltingPair pair = new UnsmeltingPair(ingredient, resultStack);
			arecipes.add(pair);
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ("item".equals(inputId)) {
			this.loadUsageRecipes((ItemStack) ingredients[0]);
		}
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return new GOTHandlerUnsmeltery();
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	private static ItemStack getRandomUnsmelteryResult(ItemStack stack) {
		ItemStack ret = null;
		try {
			ret = ReflectionManager.callMethod(GOTTileEntityUnsmeltery.class, ItemStack.class, unsmelteryTileEntity, "getRandomUnsmeltingResult");
		} catch (Exception exception) {
			// empty catch block
		}
		return ret;
	}

	public class UnsmeltingPair extends FurnaceRecipeHandler.SmeltingPair {
		private ItemStack ingredient;
		private PositionedStack lastIngredient;
		private int lastCycle;

		private UnsmeltingPair(ItemStack ingred, ItemStack result) {
			super(ingred, result);
			lastCycle = GOTHandlerUnsmeltery.this.cycleticks / 48;
			ingredient = ingred;
		}

		private PositionedStack getCycledResult(int cycle, PositionedStack result) {
			if (cycle != lastCycle) {
				lastCycle = cycle;
				ItemStack stack = GOTHandlerUnsmeltery.getRandomUnsmelteryResult(ingredient);
				if (stack != null) {
					lastIngredient = new PositionedStack(stack, result.relx, result.rely);
					return lastIngredient;
				}
			}
			return lastIngredient == null ? result : lastIngredient;
		}

		@Override
		public PositionedStack getResult() {
			return getCycledResult(GOTHandlerUnsmeltery.this.cycleticks / 48, super.getResult());
		}
	}

}
