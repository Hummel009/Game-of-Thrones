package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeYiTiTropicalForest extends GOTBiomeEssosBase {
	public GOTBiomeYiTiTropicalForest(int i, boolean major) {
		super(i, major);
		preseter.setupJungleView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupJungleTrees();

		setupRuinedStructures(false);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.JUNGLE_SCORPION, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);

		biomeWaypoints = GOTWaypoint.Region.YI_TI;
		biomeAchievement = GOTAchievement.enterYiTi;
		banditChance = GOTEventSpawner.EventChance.COMMON;
		roadBlock = GOTBezierType.PATH_PAVING;
	}
}