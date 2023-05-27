package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.ibben.GOTStructureIbbenSettlement;

import java.util.ArrayList;

public class GOTBiomeIbben extends GOTBiomeEssosCold {
	public GOTBiomeIbben(int i, boolean major) {
		super(i, major);
		decorator.addTree(GOTTreeType.IBBEN_PINE, 500);
		decorator.addSettlement(new GOTStructureIbbenSettlement(this, 1.0f));
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IBBEN_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IBBEN_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterIbben;
	}


	@Override
	public Region getBiomeWaypoints() {
		return Region.IBBEN;
	}
}