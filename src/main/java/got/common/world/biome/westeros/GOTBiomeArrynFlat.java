package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.arryn.GOTStructureArrynCity;

public class GOTBiomeArrynFlat extends GOTBiomeArryn {
	public GOTBiomeArrynFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container2 = new SpawnListContainer[1];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureArrynCity castle = new GOTStructureArrynCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Pebble);
		castle.affix(GOTWaypoint.ThePaps);
		castle.affix(GOTWaypoint.ColdwaterBurn);
		castle.affix(GOTWaypoint.Snakewood);
		castle.affix(GOTWaypoint.HeartsHome);
		castle.affix(GOTWaypoint.Strongsong);
		castle.affix(GOTWaypoint.LongbowHall);
		castle.affix(GOTWaypoint.OldAnchor);
		castle.affix(GOTWaypoint.Ninestars);
		castle.affix(GOTWaypoint.IronOak);
		castle.affix(GOTWaypoint.Runestone);
		castle.affix(GOTWaypoint.GreyGlen);
		castle.affix(GOTWaypoint.Redfort);
		castle.affix(GOTWaypoint.GateOfTheMoon, 0, 1);
		castle.affix(GOTWaypoint.WitchIsle);
		castle.affix(GOTWaypoint.Wickenden);
		decorator.affix(castle);
		GOTStructureArrynCity town = new GOTStructureArrynCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Sisterton);
		town.affix(GOTWaypoint.Gulltown);
		decorator.affix(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public boolean hasDomesticAnimals() {
		return true;
	}
}