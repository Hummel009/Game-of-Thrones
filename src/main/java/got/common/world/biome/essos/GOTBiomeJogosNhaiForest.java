package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeJogosNhaiForest extends GOTBiomeEssosBase {
	public GOTBiomeJogosNhaiForest(int i, boolean major) {
		super(i, major);
		preseter.setupSavannahView();
		preseter.setupSavannahFlora();
		preseter.setupSavannahFauna();
		preseter.setupSavannahTrees();

		decorator.setTreesPerChunk(8);

		biomeWaypoints = GOTWaypoint.Region.JOGOS_NHAI;
		biomeAchievement = GOTAchievement.enterJogosNhai;
		banditChance = GOTEventSpawner.EventChance.NEVER;
		wallBlock = GOTBezierType.WALL_YI_TI;
	}
}