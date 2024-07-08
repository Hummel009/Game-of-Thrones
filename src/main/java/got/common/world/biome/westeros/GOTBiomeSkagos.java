package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.north.GOTStructureNorthSettlement;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeSkagos extends GOTBiomeWesterosBase {
	public GOTBiomeSkagos(int i, boolean major) {
		super(i, major);
		preseter.setupColdPlainsView();
		preseter.setupColdPlainsFlora();
		preseter.setupColdPlainsFauna();
		preseter.setupStandardNorthernTrees();

		setupRuinedStructures(false);

		decorator.addSettlement(new GOTStructureNorthSettlement(this, 1.0f).type(GOTStructureNorthSettlement.Type.HILLMAN, 6));

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_HILLMEN, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN));
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSkagos;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.NORTH;
	}
}