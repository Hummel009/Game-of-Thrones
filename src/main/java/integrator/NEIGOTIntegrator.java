package integrator;

import java.util.List;

import codechicken.nei.recipe.*;
import got.client.gui.GOTGuiCraftingTable;
import got.common.recipe.GOTRecipe;
import integrator.handler.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.crafting.IRecipe;

public class NEIGOTIntegrator {
	public static void registerHandler(String unlocalizedName, Class<? extends GuiContainer> guiClass, List<IRecipe> recipes) {
		registerHandler(new GOTHandlerTableShaped(recipes, guiClass, unlocalizedName));
		registerHandler(new GOTHandlerTableShapeless(recipes, guiClass, unlocalizedName));
	}

	public static void registerHandler(TemplateRecipeHandler handler) {
		GuiCraftingRecipe.craftinghandlers.add(handler);
		GuiUsageRecipe.usagehandlers.add(handler);
	}

	public static void registerRecipes() {
		registerHandler(new GOTHandlerAlloyForge());
		registerHandler(new GOTHandlerBarrel());
		registerHandler(new GOTHandlerOven());
		registerHandler(new GOTHandlerKebab());
		registerHandler("asshai", GOTGuiCraftingTable.Asshai.class, GOTRecipe.asshai);
		registerHandler("arryn", GOTGuiCraftingTable.Arryn.class, GOTRecipe.arryn);
		registerHandler("braavos", GOTGuiCraftingTable.Braavos.class, GOTRecipe.braavos);
		registerHandler("crownlands", GOTGuiCraftingTable.Crownlands.class, GOTRecipe.crownlands);
		registerHandler("dorne", GOTGuiCraftingTable.Dorne.class, GOTRecipe.dorne);
		registerHandler("dragonstone", GOTGuiCraftingTable.Dragonstone.class, GOTRecipe.dragonstone);
		registerHandler("ghiscar", GOTGuiCraftingTable.Ghiscar.class, GOTRecipe.ghiscar);
		registerHandler("gift", GOTGuiCraftingTable.Gift.class, GOTRecipe.gift);
		registerHandler("hillmen", GOTGuiCraftingTable.Hillmen.class, GOTRecipe.hillmen);
		registerHandler("ibben", GOTGuiCraftingTable.Ibben.class, GOTRecipe.ibben);
		registerHandler("ironborn", GOTGuiCraftingTable.Ironborn.class, GOTRecipe.ironborn);
		registerHandler("lhazar", GOTGuiCraftingTable.Lhazar.class, GOTRecipe.lhazar);
		registerHandler("lorath", GOTGuiCraftingTable.Lorath.class, GOTRecipe.lorath);
		registerHandler("lys", GOTGuiCraftingTable.Lys.class, GOTRecipe.lys);
		registerHandler("myr", GOTGuiCraftingTable.Myr.class, GOTRecipe.myr);
		registerHandler("north", GOTGuiCraftingTable.North.class, GOTRecipe.north);
		registerHandler("norvos", GOTGuiCraftingTable.Norvos.class, GOTRecipe.norvos);
		registerHandler("pentos", GOTGuiCraftingTable.Pentos.class, GOTRecipe.pentos);
		registerHandler("qarth", GOTGuiCraftingTable.Qarth.class, GOTRecipe.qarth);
		registerHandler("qohor", GOTGuiCraftingTable.Qohor.class, GOTRecipe.qohor);
		registerHandler("reach", GOTGuiCraftingTable.Reach.class, GOTRecipe.reach);
		registerHandler("riverlands", GOTGuiCraftingTable.Riverlands.class, GOTRecipe.riverlands);
		registerHandler("sothoryos", GOTGuiCraftingTable.Sothoryos.class, GOTRecipe.sothoryos);
		registerHandler("stormlands", GOTGuiCraftingTable.Stormlands.class, GOTRecipe.stormlands);
		registerHandler("summer", GOTGuiCraftingTable.Summer.class, GOTRecipe.summer);
		registerHandler("tyrosh", GOTGuiCraftingTable.Tyrosh.class, GOTRecipe.tyrosh);
		registerHandler("volantis", GOTGuiCraftingTable.Volantis.class, GOTRecipe.volantis);
		registerHandler("westerlands", GOTGuiCraftingTable.Westerlands.class, GOTRecipe.westerlands);
		registerHandler("wildling", GOTGuiCraftingTable.Wildling.class, GOTRecipe.wildling);
		registerHandler("yiti", GOTGuiCraftingTable.YiTi.class, GOTRecipe.yiti);
		registerHandler("dothraki", GOTGuiCraftingTable.Dothraki.class, GOTRecipe.nomad);
		registerHandler("jogos", GOTGuiCraftingTable.Jogos.class, GOTRecipe.nomad);
	}
}