package got;

import got.common.*;
import got.common.database.*;
import got.common.enchant.GOTEnchantmentCombining;
import got.common.entity.GOTEntity;
import got.common.faction.GOTFaction;
import got.common.item.GOTPoisonedDrinks;
import got.common.quest.GOTMiniQuestFactory;
import got.common.recipe.*;
import got.common.tileentity.GOTTileEntityRegistry;
import got.common.util.GOTModChecker;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTFixer;
import got.common.world.map.*;
import got.common.world.structure.GOTStructure;
import got.common.world.structure.other.GOTStructureScan;
import integrator.NEIGOTIntegrator;

public class GOTLoader {

	public static void onInit() {
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
		GOTRoads.onInit();
		GOTWalls.onInit();
		GOTMiniQuestFactory.onInit();
		GOTLore.onInit();
		GOTTitle.onInit();
		GOTFixer.onInit();
		if (GOTModChecker.hasLOTR()) {
			GOTRegistry.fallenLeaves1.setCreativeTab(null);
			GOTRegistry.fallenLeaves2.setCreativeTab(null);
			GOTRegistry.fallenLeaves3.setCreativeTab(null);
			GOTRegistry.fallenLeaves4.setCreativeTab(null);
		}
	}

	public static void preInit() {
		GOTConfig.preInit();
		GOTRegistry.assignContent();
		GOTRegistry.assignMetadata();
		GOTRegistry.registerBlocks();
		GOTRegistry.registerItems();
		GOTEntity.preInit();
		GOTInvasions.preInit();
		GOTBiome.preInit();
		GOTShields.preInit();
		GOTCapes.preInit();
		GOTPoisonedDrinks.preInit();
		GOTPotionChanges.preInit();
		if (GOTModChecker.hasNEI()) {
			NEIGOTIntegrator.registerRecipes();
		}
	}
}