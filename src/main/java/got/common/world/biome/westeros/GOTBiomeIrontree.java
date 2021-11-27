package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint.Region;

public class GOTBiomeIrontree extends GOTBiomeWesteros {
	public GOTBiomeIrontree(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.REDWOOD, 10000);
		decorator.addTree(GOTTreeType.REDWOOD_2, 10000);
		decorator.addTree(GOTTreeType.REDWOOD_3, 5000);
		decorator.addTree(GOTTreeType.REDWOOD_4, 5000);
		decorator.addTree(GOTTreeType.REDWOOD_5, 2000);
		decorator.treesPerChunk = 6;
		decorator.logsPerChunk = 1;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		registerForestFlowers();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_IRONTREE;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("irontree");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.NORTH;
	}
}