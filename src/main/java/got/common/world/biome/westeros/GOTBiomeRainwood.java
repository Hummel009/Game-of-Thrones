package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeRainwood extends GOTBiomeStormlands {
	public GOTBiomeRainwood(int i, boolean major) {
		super(i, major);
		setupStandartForestFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.treesPerChunk = 10;
		decorator.flowersPerChunk = 6;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.whiteSand = true;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK, 500);
		decorator.addTree(GOTTreeType.OAK_TALL, 500);
		decorator.addTree(GOTTreeType.OAK_LARGE, 200);
		decorator.addTree(GOTTreeType.BEECH, 500);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 200);
		decorator.addTree(GOTTreeType.SPRUCE, 100);
		decorator.addTree(GOTTreeType.FIR, 100);
		decorator.addTree(GOTTreeType.PINE, 100);
		decorator.addTree(GOTTreeType.MAPLE, 50);
		decorator.addTree(GOTTreeType.MAPLE_LARGE, 20);
		decorator.addTree(GOTTreeType.ASPEN, 100);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 20);
		decorator.clearVillages();
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("rainwood");
	}
}