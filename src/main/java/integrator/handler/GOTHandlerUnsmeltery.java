package integrator.handler;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import got.client.gui.GOTGuiUnsmeltery;
import got.common.tileentity.GOTTileEntityUnsmeltery;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTHandlerUnsmeltery extends FurnaceRecipeHandler {
	private static final GOTTileEntityUnsmeltery INSTANCE = new GOTTileEntityUnsmeltery();

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
		FMLControlledNamespacedRegistry<Item> items = GameData.getItemRegistry();
		for (Item item : (Iterable<Item>) items) {
			ItemStack stack = new ItemStack(item, 1);
			ItemStack equipmentMaterial = GOTTileEntityUnsmeltery.getEquipmentMaterial(stack);
			if (equipmentMaterial != null && NEIServerUtils.areStacksSameTypeCrafting(equipmentMaterial, result)) {
				ItemStack randomResult = INSTANCE.getRandomUnsmeltingResult(null);
				UnsmeltingPair pair;
				if (randomResult != null) {
					pair = new UnsmeltingPair(stack, randomResult);
				} else {
					pair = new UnsmeltingPair(stack, equipmentMaterial);
				}
				arecipes.add(pair);
			}
		}
		FMLControlledNamespacedRegistry<Block> blocks = GameData.getBlockRegistry();
		for (Block block : (Iterable<Block>) blocks) {
			ItemStack stack = new ItemStack(block, 1);
			ItemStack equipmentMaterial = GOTTileEntityUnsmeltery.getEquipmentMaterial(stack);
			if (equipmentMaterial != null && NEIServerUtils.areStacksSameTypeCrafting(equipmentMaterial, result)) {
				ItemStack randomResult = INSTANCE.getRandomUnsmeltingResult(null);
				UnsmeltingPair pair;
				if (randomResult != null) {
					pair = new UnsmeltingPair(stack, randomResult);
				} else {
					pair = new UnsmeltingPair(stack, equipmentMaterial);
				}
				arecipes.add(pair);
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
		ItemStack resultStack = INSTANCE.getRandomUnsmeltingResult(null);
		if (resultStack != null) {
			UnsmeltingPair pair = new UnsmeltingPair(ingredient, resultStack);
			arecipes.add(pair);
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
		return new GOTHandlerUnsmeltery();
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	private class UnsmeltingPair extends FurnaceRecipeHandler.SmeltingPair {
		private PositionedStack lastIngredient;
		private int lastCycle = cycleticks / 48;

		protected UnsmeltingPair(ItemStack ingred, ItemStack result) {
			super(ingred, result);
		}

		protected PositionedStack getCycledResult(int cycle, PositionedStack result) {
			if (cycle != lastCycle) {
				lastCycle = cycle;
				ItemStack stack = INSTANCE.getRandomUnsmeltingResult(null);
				if (stack != null) {
					lastIngredient = new PositionedStack(stack, result.relx, result.rely);
					return lastIngredient;
				}
			}
			if (lastIngredient == null) {
				return result;
			}
			return lastIngredient;
		}

		@Override
		public PositionedStack getResult() {
			return getCycledResult(cycleticks / 48, super.getResult());
		}
	}
}
