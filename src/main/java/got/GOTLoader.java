package got;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import got.common.*;
import got.common.database.*;
import got.common.enchant.GOTEnchantmentCombining;
import got.common.entity.GOTEntity;
import got.common.faction.GOTFaction;
import got.common.item.GOTPoisonedDrinks;
import got.common.quest.GOTMiniQuestFactory;
import got.common.recipe.*;
import got.common.tileentity.GOTTileEntityRegistry;
import got.common.util.*;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTFixer;
import got.common.world.map.*;
import got.common.world.structure.GOTStructure;
import got.common.world.structure.other.GOTStructureScan;
import integrator.NEIGOTIntegrator;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.StatCollector;

public class GOTLoader {

	public static void generateWikiaDatabases() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String display = "food";

		for (Item item : GOTCommander.getObjectFieldsOfType(GOTRegistry.class, Item.class)) {
			String genInfo = StatCollector.translateToLocal(item.getUnlocalizedName() + ".name") + " || [[File:" + item.getUnlocalizedName().substring(9) + ".png|32px|link=]] ||";
			if (item instanceof ItemFood && "food".equals(display)) {
				Field pf0 = ItemFood.class.getDeclaredField("saturationModifier");
				Field pf1 = ItemFood.class.getDeclaredField("healAmount");
				pf0.setAccessible(true);
				pf1.setAccessible(true);
				GOTLog.logger.info("| " + genInfo + "{{Bar|bread|" + new DecimalFormat("#.##").format((float) pf0.get(item) * (int) pf1.get(item) * 2) + "}} || {{Bar|food|" + (int) pf1.get(item) + "}} || " + item.getItemStackLimit());
				GOTLog.logger.info("|-");
			}
			if (item instanceof ItemArmor && "armor".equals(display)) {
				Field pf0 = ItemArmor.class.getDeclaredField("maxDamage");
				pf0.setAccessible(true);
				GOTLog.logger.info("| " + genInfo + (int) pf0.get(item) + " || " + ((ItemArmor) item).damageReduceAmount + " || " + StatCollector.translateToLocal(((ItemArmor) item).getArmorMaterial().customCraftingMaterial.getUnlocalizedName() + ".name"));
				GOTLog.logger.info("|-");
			}
			if (item instanceof ItemSword && "weapon".equals(display)) {
				Field pf0 = ItemSword.class.getDeclaredField("maxDamage");
				Field pf1 = ItemSword.class.getDeclaredField("field_150934_a");
				Field pf2 = ItemSword.class.getDeclaredField("field_150933_b");
				pf0.setAccessible(true);
				pf1.setAccessible(true);
				pf2.setAccessible(true);
				GOTLog.logger.info("| " + genInfo + (int) pf0.get(item) + " || " + (float) pf1.get(item) + " || " + StatCollector.translateToLocal(((ToolMaterial) pf2.get(item)).getRepairItemStack().getUnlocalizedName() + ".name"));
				GOTLog.logger.info("|-");
			}
		}
	}

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
		GOTConfig.setupAndLoad();
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