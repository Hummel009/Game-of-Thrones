package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeDothrakiSeaForest extends GOTBiomeEssosBase {
	public GOTBiomeDothrakiSeaForest(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.setTreesPerChunk(8);

		biomeWaypoints = GOTWaypoint.Region.DOTHRAKI;
		biomeAchievement = GOTAchievement.enterDothrakiSea;
		banditChance = GOTEventSpawner.EventChance.NEVER;
		wallBlock = GOTBezierType.WALL_IBBEN;
	}
}