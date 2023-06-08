package got;

import got.common.*;
import got.common.block.other.GOTBlockIronBank;
import got.common.database.*;
import got.common.enchant.GOTEnchantmentCombining;
import got.common.entity.GOTEntity;
import got.common.faction.GOTFaction;
import got.common.item.GOTPoisonedDrinks;
import got.common.quest.GOTMiniQuestFactory;
import got.common.recipe.GOTRecipe;
import got.common.recipe.GOTRecipeBrewing;
import got.common.recipe.GOTRecipeMillstone;
import got.common.tileentity.GOTTileEntityRegistry;
import got.common.util.GOTModChecker;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBeziers;
import got.common.world.map.GOTFixer;
import got.common.world.structure.GOTStructure;
import got.common.world.structure.other.GOTStructureScan;
import integrator.NEIGOTIntegrator;
import net.minecraft.block.Block;

public class GOTLoader {

	public static void onInit() {
		GOTBlocks.onInit();
		GOTItems.onInit();
		GOTTileEntityRegistry.onInit();
		GOTCreativeTabs.onInit();
		GOTRecipe.onInit();
		GOTMaterial.onInit();
		GOTDimension.onInit();
		GOTSpeech.onInit();
		GOTNames.onInit();
		GOTStructureScan.onInit();
		GOTRecipeBrewing.onInit();
		GOTRecipeMillstone.onInit();
		GOTEnchantmentCombining.onInit();
		GOTAchievement.onInit();
		GOTChatEvents.onInit();
		GOTFaction.onInit();
		GOTStructure.onInit();
		GOTBeziers.onInit();
		GOTMiniQuestFactory.onInit();
		GOTLore.onInit();
		GOTTitle.onInit();
		GOTFixer.onInit();
		if (GOTModChecker.hasLOTR()) {
			GOTBlocks.fallenLeaves1.setCreativeTab(null);
			GOTBlocks.fallenLeaves2.setCreativeTab(null);
			GOTBlocks.fallenLeaves3.setCreativeTab(null);
			GOTBlocks.fallenLeaves4.setCreativeTab(null);
		}
	}

	public static void preInit() {
		GOTConfig.preInit();
		GOTBlocks.preInit();
		GOTItems.preInit();
		GOTEntity.preInit();
		GOTInvasions.preInit();
		GOTBiome.preInit();
		GOTShields.preInit();
		GOTCapes.preInit();
		GOTPoisonedDrinks.preInit();
		GOTPotionChanges.preInit();
		if (GOTModChecker.hasNEI() && GOTModChecker.hasGuiContainer()) {
			NEIGOTIntegrator.registerRecipes();
		}
		GOTBlockIronBank.preInit();
	}
}