package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.structure.other.*;

public abstract class GOTBiomeWesteros extends GOTBiome {
	protected GOTBiomeWesteros(int i, boolean major) {
		super(i, major);
		setupStandardPlainsFauna();
		addBiomeVariant(GOTBiomeVariant.FOREST);
		addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		addBiomeVariant(GOTBiomeVariant.FOREST_ASPEN, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_LARCH, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.1f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.1f);
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.addTree(GOTTreeType.BEECH_PARTY, 2);
		decorator.addTree(GOTTreeType.OAK, 1000);
		decorator.addTree(GOTTreeType.OAK_TALLER, 12);
		decorator.addTree(GOTTreeType.OAK_LARGE, 300);
		decorator.addTree(GOTTreeType.BIRCH, 50);
		decorator.addTree(GOTTreeType.BIRCH_LARGE, 20);
		decorator.addTree(GOTTreeType.BIRCH_PARTY, 3);
		decorator.addTree(GOTTreeType.BIRCH_TALL, 4);
		decorator.addTree(GOTTreeType.BIRCH_DEAD, 1);
		decorator.addTree(GOTTreeType.BEECH, 50);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.APPLE, 5);
		decorator.addTree(GOTTreeType.PEAR, 5);
		decorator.addTree(GOTTreeType.PLUM, 5);
		decorator.addTree(GOTTreeType.OLIVE, 1);
		decorator.addTree(GOTTreeType.ALMOND, 1);
		decorator.addTree(GOTTreeType.CHESTNUT, 20);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("westeros");
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}