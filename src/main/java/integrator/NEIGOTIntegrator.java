package integrator;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import got.client.gui.GOTGuiCraftingTable;
import got.common.recipe.GOTRecipe;
import integrator.handler.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

public class NEIGOTIntegrator {
	private NEIGOTIntegrator() {
	}

	private static void registerHandler(String unlocalizedName, Class<? extends GuiContainer> guiClass, List<IRecipe> recipes) {
		registerHandler(new GOTHandlerTableShaped(recipes, guiClass, unlocalizedName));
		registerHandler(new GOTHandlerTableShapeless(recipes, guiClass, unlocalizedName));
	}

	private static void registerHandler(TemplateRecipeHandler handler) {
		GuiCraftingRecipe.craftinghandlers.add(handler);
		GuiUsageRecipe.usagehandlers.add(handler);
	}

	public static void registerRecipes() {
		registerHandler(new GOTHandlerAlloyForge());
		registerHandler(new GOTHandlerBarrel());
		registerHandler(new GOTHandlerOven());
		registerHandler(new GOTHandlerKebab());
		registerHandler(new GOTHandlerUnsmeltery());
		registerHandler("asshai", GOTGuiCraftingTable.Asshai.class, GOTRecipe.ASSHAI);
		registerHandler("arryn", GOTGuiCraftingTable.Arryn.class, GOTRecipe.ARRYN);
		registerHandler("braavos", GOTGuiCraftingTable.Braavos.class, GOTRecipe.BRAAVOS);
		registerHandler("crownlands", GOTGuiCraftingTable.Crownlands.class, GOTRecipe.CROWNLANDS);
		registerHandler("dorne", GOTGuiCraftingTable.Dorne.class, GOTRecipe.DORNE);
		registerHandler("dragonstone", GOTGuiCraftingTable.Dragonstone.class, GOTRecipe.DRAGONSTONE);
		registerHandler("ghiscar", GOTGuiCraftingTable.Ghiscar.class, GOTRecipe.GHISCAR);
		registerHandler("gift", GOTGuiCraftingTable.Gift.class, GOTRecipe.GIFT);
		registerHandler("hillmen", GOTGuiCraftingTable.Hillmen.class, GOTRecipe.HILLMEN);
		registerHandler("ibben", GOTGuiCraftingTable.Ibben.class, GOTRecipe.IBBEN);
		registerHandler("ironborn", GOTGuiCraftingTable.Ironborn.class, GOTRecipe.IRONBORN);
		registerHandler("lhazar", GOTGuiCraftingTable.Lhazar.class, GOTRecipe.LHAZAR);
		registerHandler("lorath", GOTGuiCraftingTable.Lorath.class, GOTRecipe.LORATH);
		registerHandler("lys", GOTGuiCraftingTable.Lys.class, GOTRecipe.LYS);
		registerHandler("myr", GOTGuiCraftingTable.Myr.class, GOTRecipe.MYR);
		registerHandler("north", GOTGuiCraftingTable.North.class, GOTRecipe.NORTH);
		registerHandler("norvos", GOTGuiCraftingTable.Norvos.class, GOTRecipe.NORVOS);
		registerHandler("pentos", GOTGuiCraftingTable.Pentos.class, GOTRecipe.PENTOS);
		registerHandler("qarth", GOTGuiCraftingTable.Qarth.class, GOTRecipe.QARTH);
		registerHandler("qohor", GOTGuiCraftingTable.Qohor.class, GOTRecipe.QOHOR);
		registerHandler("reach", GOTGuiCraftingTable.Reach.class, GOTRecipe.REACH);
		registerHandler("riverlands", GOTGuiCraftingTable.Riverlands.class, GOTRecipe.RIVERLANDS);
		registerHandler("sothoryos", GOTGuiCraftingTable.Sothoryos.class, GOTRecipe.SOTHORYOS);
		registerHandler("stormlands", GOTGuiCraftingTable.Stormlands.class, GOTRecipe.STORMLANDS);
		registerHandler("summer", GOTGuiCraftingTable.Summer.class, GOTRecipe.SUMMER);
		registerHandler("tyrosh", GOTGuiCraftingTable.Tyrosh.class, GOTRecipe.TYROSH);
		registerHandler("volantis", GOTGuiCraftingTable.Volantis.class, GOTRecipe.VOLANTIS);
		registerHandler("westerlands", GOTGuiCraftingTable.Westerlands.class, GOTRecipe.WESTERLANDS);
		registerHandler("wildling", GOTGuiCraftingTable.Wildling.class, GOTRecipe.WILDLING);
		registerHandler("yiti", GOTGuiCraftingTable.YiTi.class, GOTRecipe.YITI);
		registerHandler("dothraki", GOTGuiCraftingTable.Dothraki.class, GOTRecipe.DOTHRAKI);
		registerHandler("jogos", GOTGuiCraftingTable.Jogos.class, GOTRecipe.JOGOS);
		registerHandler("mossovy", GOTGuiCraftingTable.Mossovy.class, GOTRecipe.MOSSOVY);
	}
}