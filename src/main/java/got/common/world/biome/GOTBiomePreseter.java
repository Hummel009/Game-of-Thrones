package got.common.world.biome;

import got.common.database.GOTBlocks;
import got.common.entity.animal.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.westeros.GOTBiomeWesterosBase;
import got.common.world.feature.GOTTreeType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomePreseter {
	private final GOTBiome biome;

	public GOTBiomePreseter(GOTBiome biome) {
		this.biome = biome;
	}

	public GOTBiome getBiome() {
		return biome;
	}

	public void setupDesertView() {
		biome.setVariantChance(0.1f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getDecorator().setGrassPerChunk(5);
		biome.getDecorator().setDoubleGrassPerChunk(0);
		biome.getDecorator().setCactiPerChunk(2);
		biome.getDecorator().setDeadBushPerChunk(2);
	}

	public void setupDesertFlora() {
		setupStandardAridFlora();
	}

	public void setupDesertFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.addSpawnableCreature(GOTEntityCamel.class, 100, 1, 2);
		biome.getSpawnableGOTAmbientList().clear();
	}

	public void setupDesertTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.OAK_DEAD, 800);
		biome.getDecorator().addTree(GOTTreeType.OAK_DESERT, 200);
	}

	public void setupDesertColdViewOverride() {
		biome.getDecorator().setGrassPerChunk(0);
		biome.getDecorator().setCactiPerChunk(0);
		biome.getDecorator().setDeadBushPerChunk(0);
	}

	public void setupDesertColdFloraOverride() {
		setupStandardColdFlora();
	}

	public void setupDesertColdFaunaOverride() {
		biome.getSpawnableCreatureList().clear();
	}

	public void setupDesertColdTreesOverride() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.OAK_DEAD, 800);
	}

	public void setupFrostView() {
		biome.setVariantChance(0.1f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getDecorator().setFlowersPerChunk(0);
		biome.getDecorator().setDoubleFlowersPerChunk(0);
		biome.getDecorator().setGrassPerChunk(0);
		biome.getDecorator().setDoubleGrassPerChunk(0);
	}

	public void setupFrostFlora() {
		setupStandardColdFlora();
	}

	public void setupFrostFauna() {
		removeAllEntities();
		biome.addSpawnableCreature(GOTEntitySnowBear.class, 60, 1, 1);
	}

	public void setupFrostTrees(boolean fotinia) {
		setupStandardColdTrees(fotinia);
	}

	public void setupTaigaView() {
		biome.setVariantChance(0.1f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.CLEARING, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 0.9f);
		biome.getDecorator().setTreesPerChunk(10);
		biome.getDecorator().setFlowersPerChunk(1);
		biome.getDecorator().setDoubleFlowersPerChunk(0);
		biome.getDecorator().setGrassPerChunk(2);
		biome.getDecorator().setDoubleGrassPerChunk(0);
	}

	public void setupTaigaFlora() {
		setupStandardColdFlora();
	}

	public void setupTaigaFauna() {
		removeAllEntities();
		biome.addSpawnableCreature(GOTEntityDeer.class, 30, 1, 2);
		biome.addSpawnableCreature(GOTEntityBoar.class, 20, 2, 3);
		biome.addSpawnableCreature(GOTEntityBison.class, 15, 1, 2);
		biome.addSpawnableCreature(GOTEntityBear.class, 15, 1, 1);
		biome.addSpawnableCreature(GOTEntityWoolyRhino.class, 10, 1, 1);
		biome.addSpawnableCreature(GOTEntityMammoth.class, 10, 1, 1);
	}

	public void setupTaigaTrees(boolean fotinia) {
		setupStandardColdTrees(fotinia);
	}

	public void setupSavannahView() {
		biome.setVariantChance(0.1f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 0.9f);
		biome.getBiomeVariants().add(GOTBiomeVariant.SAVANNAH_BAOBAB, 0.1f);
		biome.getDecorator().setFlowersPerChunk(0);
		biome.getDecorator().setDoubleFlowersPerChunk(0);
		biome.getDecorator().setGrassPerChunk(256);
		biome.getDecorator().setDoubleGrassPerChunk(10);
	}

	public void setupSavannahFlora() {
		setupStandardAridFlora();
	}

	public void setupSavannahFauna() {
		setupStandardExoticFauna();
	}

	public void setupSavannahTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.ACACIA, 500);
		biome.getDecorator().addTree(GOTTreeType.DRAGONBLOOD, 200);
		biome.getDecorator().addTree(GOTTreeType.DRAGONBLOOD_LARGE, 100);
		biome.getDecorator().addTree(GOTTreeType.KANUKA, 100);
		biome.getDecorator().addTree(GOTTreeType.ACACIA_DEAD, 2);
		biome.getDecorator().addTree(GOTTreeType.BAOBAB, 1);
	}

	public void setupJungleView() {
		biome.setVariantChance(0.2f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.MOUNTAIN, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.CLEARING, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 0.8f);
		biome.getDecorator().setTreesPerChunk(40);
		biome.getDecorator().setFlowersPerChunk(4);
		biome.getDecorator().setDoubleFlowersPerChunk(4);
		biome.getDecorator().setGrassPerChunk(15);
		biome.getDecorator().setDoubleGrassPerChunk(10);
		biome.getDecorator().setCornPerChunk(10);
		biome.getDecorator().setLogsPerChunk(0);
	}

	public void setupJungleFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.chrysanthemum, 0, 10);
		biome.addFlower(GOTBlocks.chrysanthemum, 1, 10);
		biome.addFlower(GOTBlocks.chrysanthemum, 2, 10);
		biome.addFlower(GOTBlocks.chrysanthemum, 3, 10);
		biome.addFlower(GOTBlocks.chrysanthemum, 4, 10);
	}

	public void setupJungleFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.addSpawnableCreature(GOTEntityFlamingo.class, 100, 2, 3);
		biome.getSpawnableGOTAmbientList().clear();
		biome.addSpawnableAmbient(GOTEntityButterfly.class, 60, 4, 4);
		biome.addSpawnableAmbient(GOTEntityBird.class, 40, 2, 3);
	}

	public void setupJungleTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.JUNGLE, 1000);
		biome.getDecorator().addTree(GOTTreeType.JUNGLE_LARGE, 500);
		biome.getDecorator().addTree(GOTTreeType.MAHOGANY, 500);
		biome.getDecorator().addTree(GOTTreeType.MANGO, 20);
		biome.getDecorator().addTree(GOTTreeType.BANANA, 50);
	}

	public void setupMountainsFlora() {
		setupStandardColdFlora();
	}

	public void setupMountainsFauna() {
		setupStandardWildFauna();
		biome.addSpawnableCreature(GOTEntityBear.class, 10, 1, 1);
		biome.addSpawnableCreature(GOTEntityShadowcat.class, 10, 1, 1);
	}

	public void setupMarshesView() {
		biome.setVariantChance(1.0f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);
		biome.getDecorator().setTreesPerChunk(3);
		biome.getDecorator().setSandPerChunk(0);
		biome.getDecorator().setQuagmirePerChunk(1);
		biome.getDecorator().setTreesPerChunk(0);
		biome.getDecorator().setLogsPerChunk(2);
		biome.getDecorator().setGrassPerChunk(6);
		biome.getDecorator().setDoubleGrassPerChunk(6);
		biome.getDecorator().setFlowersPerChunk(0);
		biome.getDecorator().setReedPerChunk(2);
		biome.getDecorator().setDryReedChance(1.0f);
	}

	public void setupMarshesFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.deadMarshPlant, 0, 10);
	}

	public void setupMarshesFauna() {
		removeAllEntities();
		biome.addSpawnableCreature(GOTEntityBeaver.class, 50, 1, 1);
		biome.addSpawnableAmbient(GOTEntityMidges.class, 95, 4, 4);
		biome.addSpawnableAmbient(GOTEntitySwan.class, 5, 1, 2);
	}

	public void setupForestView() {
		biome.setVariantChance(0.1f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.CLEARING, 0.1f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 0.9f);
		biome.getDecorator().setTreesPerChunk(10);
		biome.getDecorator().setGrassPerChunk(8);
		biome.getDecorator().setDoubleGrassPerChunk(4);
		biome.getDecorator().setFlowersPerChunk(4);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
	}

	public void setupForestFlora() {
		biome.getFlowers().clear();
		biome.addFlower(Blocks.yellow_flower, 0, 20);
		biome.addFlower(Blocks.red_flower, 0, 10);
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
		biome.addFlower(GOTBlocks.marigold, 0, 10);
	}

	public void setupForestFauna() {
		setupStandardWildFauna();
		if (biome instanceof GOTBiomeWesterosBase) {
			biome.addSpawnableCreature(GOTEntityBison.class, 20, 1, 2);
		} else {
			biome.addSpawnableCreature(GOTEntityWhiteBison.class, 20, 1, 2);
		}
		biome.addSpawnableCreature(GOTEntityDeer.class, 30, 1, 2);
		biome.addSpawnableCreature(GOTEntityBear.class, 10, 1, 1);
	}

	public void setupMountainsView() {
		biome.setVariantChance(0.3f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biome.getDecorator().setTreesPerChunk(3);
		biome.getDecorator().setGrassPerChunk(6);
		biome.getDecorator().setDoubleGrassPerChunk(1);
		biome.getDecorator().setFlowersPerChunk(3);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
		biome.getDecorator().setBiomeGemFactor(biome.getDecorator().getBiomeGemFactor() * 2.0f);
		biome.getDecorator().setBiomeOreFactor(biome.getDecorator().getBiomeOreFactor() * 2.0f);
		biome.getDecorator().addOre(new WorldGenMinable(GOTBlocks.oreGlowstone, 4), 8.0f, 0, 48);
		biome.getDecorator().addOre(new WorldGenMinable(GOTBlocks.oreCobalt, 5), 5.0f, 0, 32);
	}

	public void setupBushlandView() {
		biome.setVariantChance(0.1f);
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		biome.getDecorator().setGrassPerChunk(12);
		biome.getDecorator().setDoubleGrassPerChunk(6);
		biome.getDecorator().setFlowersPerChunk(1);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
		biome.getDecorator().setLogsPerChunk(1);
		biome.getDecorator().setCornPerChunk(4);
	}

	public void setupBushlandFlora() {
		setupStandardAridFlora();
	}

	public void setupBushlandFauna() {
		setupStandardExoticFauna();
	}

	public void setupPlainsView() {
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		biome.getDecorator().setGrassPerChunk(10);
		biome.getDecorator().setDoubleGrassPerChunk(6);
		biome.getDecorator().setFlowersPerChunk(3);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
	}

	public void setupPlainsFlora() {
		biome.getFlowers().clear();
		biome.addFlower(Blocks.red_flower, 4, 3);
		biome.addFlower(Blocks.red_flower, 5, 3);
		biome.addFlower(Blocks.red_flower, 6, 3);
		biome.addFlower(Blocks.red_flower, 7, 3);
		biome.addFlower(Blocks.red_flower, 0, 20);
		biome.addFlower(Blocks.red_flower, 3, 20);
		biome.addFlower(Blocks.red_flower, 8, 20);
		biome.addFlower(Blocks.yellow_flower, 0, 30);
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
		biome.addFlower(GOTBlocks.marigold, 0, 10);
	}

	public void setupPlainsFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.addSpawnableCreature(GOTEntityBoar.class, 15, 2, 3);
		biome.addSpawnableCreature(GOTEntityBison.class, 10, 1, 2);

		biome.addSpawnableCreature(GOTEntityRabbit.class, 15, 1, 2);

		biome.addSpawnableCreature(GOTEntityHorse.class, 30, 1, 2);
		biome.addSpawnableCreature(EntitySheep.class, 20, 1, 2);
		biome.addSpawnableCreature(EntityChicken.class, 10, 1, 2);
		biome.getSpawnableGOTAmbientList().clear();
		biome.addSpawnableAmbient(GOTEntityButterfly.class, 50, 4, 4);
		biome.addSpawnableAmbient(GOTEntityBird.class, 30, 2, 3);
		biome.addSpawnableAmbient(GOTEntityGorcrow.class, 5, 2, 3);
	}

	public void setupPlainsFaunaDomesticOverride() {
		biome.getSpawnableCreatureList().clear();
		biome.addSpawnableCreature(EntityPig.class, 15, 2, 4);
		biome.addSpawnableCreature(EntityCow.class, 10, 1, 2);

		biome.addSpawnableCreature(GOTEntityRabbit.class, 15, 1, 2);

		biome.addSpawnableCreature(GOTEntityHorse.class, 30, 1, 2);
		biome.addSpawnableCreature(EntitySheep.class, 20, 1, 2);
		biome.addSpawnableCreature(EntityChicken.class, 10, 1, 2);
		biome.getSpawnableGOTAmbientList().clear();
		biome.addSpawnableAmbient(GOTEntityButterfly.class, 50, 4, 4);
		biome.addSpawnableAmbient(GOTEntityBird.class, 30, 2, 3);
		biome.addSpawnableAmbient(GOTEntityGorcrow.class, 5, 2, 3);
	}

	public void setupColdPlainsView() {
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_PINE, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_ASPEN, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LARCH, 0.2f);
		biome.getDecorator().setGrassPerChunk(10);
		biome.getDecorator().setDoubleGrassPerChunk(6);
		biome.getDecorator().setFlowersPerChunk(3);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
	}

	public void setupColdPlainsFlora() {
		setupStandardColdFlora();
	}

	public void setupColdPlainsFauna() {
		setupStandardWildFauna();
		if (biome instanceof GOTBiomeWesterosBase) {
			biome.addSpawnableCreature(GOTEntityBison.class, 20, 1, 2);
		} else {
			biome.addSpawnableCreature(GOTEntityWhiteBison.class, 20, 1, 2);
		}
	}

	public void setupAridPlainsView() {
		biome.getBiomeVariants().clear();
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FLOWERS, 1.0f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_CEDAR, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.FOREST_CYPRESS, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		biome.getBiomeVariants().add(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.2f);
		biome.getDecorator().setGrassPerChunk(10);
		biome.getDecorator().setDoubleGrassPerChunk(6);
		biome.getDecorator().setFlowersPerChunk(3);
		biome.getDecorator().setDoubleFlowersPerChunk(1);
		biome.getDecorator().setCornPerChunk(4);
		biome.getDecorator().setGenerateAgriculture(true);
	}

	public void setupAridPlainsFlora() {
		setupPlainsFlora();
	}

	public void setupAridPlainsFauna() {
		setupPlainsFauna();
	}

	public void setupStandardNorthernTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.PINE, 500);
		biome.getDecorator().addTree(GOTTreeType.SPRUCE, 400);
		biome.getDecorator().addTree(GOTTreeType.SPRUCE_THIN, 400);
		biome.getDecorator().addTree(GOTTreeType.FIR, 350);
		biome.getDecorator().addTree(GOTTreeType.LARCH, 300);
		biome.getDecorator().addTree(GOTTreeType.ASPEN, 100);
		biome.getDecorator().addTree(GOTTreeType.ASPEN_LARGE, 50);
		biome.getDecorator().addTree(GOTTreeType.SPRUCE_MEGA, 20);
		biome.getDecorator().addTree(GOTTreeType.SPRUCE_MEGA_THIN, 10);
	}

	public void setupStandardMiderateTrees() {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.OAK, 500);
		biome.getDecorator().addTree(GOTTreeType.OAK_LARGE, 300);
		biome.getDecorator().addTree(GOTTreeType.OAK_TALL, 50);
		biome.getDecorator().addTree(GOTTreeType.OAK_TALLER, 30);
		biome.getDecorator().addTree(GOTTreeType.CHESTNUT, 250);
		biome.getDecorator().addTree(GOTTreeType.CHESTNUT_LARGE, 125);
		biome.getDecorator().addTree(GOTTreeType.BIRCH, 200);
		biome.getDecorator().addTree(GOTTreeType.BIRCH_LARGE, 100);
		biome.getDecorator().addTree(GOTTreeType.BIRCH_TALL, 20);
		biome.getDecorator().addTree(GOTTreeType.HOLLY, 200);
		biome.getDecorator().addTree(GOTTreeType.HOLLY_LARGE, 100);
		biome.getDecorator().addTree(GOTTreeType.MAPLE, 150);
		biome.getDecorator().addTree(GOTTreeType.MAPLE_LARGE, 75);
		biome.getDecorator().addTree(GOTTreeType.ARAMANT, 100);
		biome.getDecorator().addTree(GOTTreeType.APPLE, 5);
		biome.getDecorator().addTree(GOTTreeType.PEAR, 5);
		biome.getDecorator().addTree(GOTTreeType.PLUM, 5);
		biome.getDecorator().addTree(GOTTreeType.CHESTNUT_PARTY, 2);
		biome.getDecorator().addTree(GOTTreeType.BEECH_PARTY, 1);
		biome.getDecorator().addTree(GOTTreeType.BIRCH_PARTY, 1);
		biome.getDecorator().addTree(GOTTreeType.MAPLE_PARTY, 1);
		biome.getDecorator().addTree(GOTTreeType.OAK_PARTY, 1);
	}

	public void setupStandardSouthernTrees(boolean exotic) {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(GOTTreeType.CEDAR, 500);
		biome.getDecorator().addTree(GOTTreeType.CEDAR_LARGE, 300);
		biome.getDecorator().addTree(GOTTreeType.CYPRESS, 250);
		biome.getDecorator().addTree(GOTTreeType.CYPRESS_LARGE, 125);
		if (exotic) {
			biome.getDecorator().addTree(GOTTreeType.BIRCH, 200);
			biome.getDecorator().addTree(GOTTreeType.BIRCH_LARGE, 100);
			biome.getDecorator().addTree(GOTTreeType.BIRCH_TALL, 20);
			biome.getDecorator().addTree(GOTTreeType.BEECH, 200);
			biome.getDecorator().addTree(GOTTreeType.BEECH_LARGE, 100);
		} else {
			biome.getDecorator().addTree(GOTTreeType.DATE_PALM, 100);
			biome.getDecorator().addTree(GOTTreeType.PALM, 50);
		}
		biome.getDecorator().addTree(GOTTreeType.OLIVE, 100);
		biome.getDecorator().addTree(GOTTreeType.OLIVE_LARGE, 50);
		biome.getDecorator().addTree(GOTTreeType.POMEGRANATE, 5);
		biome.getDecorator().addTree(GOTTreeType.ORANGE, 5);
		biome.getDecorator().addTree(GOTTreeType.LIME, 5);
		biome.getDecorator().addTree(GOTTreeType.LEMON, 5);
		biome.getDecorator().addTree(GOTTreeType.CHERRY, 5);
		biome.getDecorator().addTree(GOTTreeType.ALMOND, 5);
	}

	private void setupStandardColdTrees(boolean fotinia) {
		biome.getDecorator().clearTrees();
		biome.getDecorator().addTree(fotinia ? GOTTreeType.FOTINIA : GOTTreeType.PINE, 20);
	}

	private void setupStandardAridFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.southernFlower, 0, 10);
		biome.addFlower(GOTBlocks.southernFlower, 1, 10);
		biome.addFlower(GOTBlocks.southernFlower, 2, 10);
		biome.addFlower(GOTBlocks.southernFlower, 3, 10);
	}

	private void setupStandardColdFlora() {
		biome.getFlowers().clear();
		biome.addFlower(GOTBlocks.bluebell, 0, 5);
	}

	private void setupStandardWildFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.addSpawnableCreature(GOTEntityBoar.class, 20, 2, 3);
		biome.addSpawnableCreature(GOTEntityRabbit.class, 20, 1, 2);
		biome.getSpawnableGOTAmbientList().clear();
		biome.addSpawnableAmbient(GOTEntityButterfly.class, 50, 4, 4);
		biome.addSpawnableAmbient(GOTEntityBird.class, 30, 2, 3);
		biome.addSpawnableAmbient(GOTEntityGorcrow.class, 5, 2, 3);
	}

	private void setupStandardExoticFauna() {
		biome.getSpawnableCreatureList().clear();
		biome.addSpawnableCreature(GOTEntityZebra.class, 15, 1, 2);
		biome.addSpawnableCreature(GOTEntityGemsbok.class, 15, 1, 2);
		biome.addSpawnableCreature(GOTEntityWhiteOryx.class, 15, 1, 2);
		biome.addSpawnableCreature(GOTEntityDikDik.class, 15, 1, 2);
		biome.addSpawnableCreature(GOTEntityGiraffe.class, 10, 1, 2);
		biome.addSpawnableCreature(GOTEntityRabbit.class, 10, 1, 2);
		biome.addSpawnableCreature(GOTEntityLion.class, 5, 1, 2);
		biome.addSpawnableCreature(GOTEntityLioness.class, 5, 1, 2);
		biome.addSpawnableCreature(GOTEntityRhino.class, 5, 1, 1);
		biome.addSpawnableCreature(GOTEntityElephant.class, 5, 1, 1);
		biome.getSpawnableGOTAmbientList().clear();
		biome.addSpawnableAmbient(GOTEntityButterfly.class, 50, 4, 4);
		biome.addSpawnableAmbient(GOTEntityBird.class, 30, 2, 3);
		biome.addSpawnableAmbient(GOTEntityGorcrow.class, 20, 2, 3);
	}

	private void removeAllEntities() {
		biome.getSpawnableWaterCreatureList().clear();
		biome.getSpawnableCaveCreatureList().clear();
		biome.getSpawnableGOTAmbientList().clear();
		biome.getSpawnableCreatureList().clear();
	}
}