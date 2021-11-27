package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.dorne.GOTStructureDorneCity;

public class GOTBiomeDorneFlat extends GOTBiomeDorne {
	public GOTBiomeDorneFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureDorneCity castle = new GOTStructureDorneCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Starfall, 0, -1);
		castle.affix(GOTWaypoint.HighHermitage);
		castle.affix(GOTWaypoint.Blackmont);
		castle.affix(GOTWaypoint.Kingsgrave, -1, 0);
		castle.affix(GOTWaypoint.SkyReach, 0, 1);
		castle.affix(GOTWaypoint.Yronwood, 1, 0);
		castle.affix(GOTWaypoint.Wyl, 0, -1);
		castle.affix(GOTWaypoint.Vaith);
		castle.affix(GOTWaypoint.Saltshore);
		castle.affix(GOTWaypoint.Godsgrace);
		castle.affix(GOTWaypoint.Tor);
		castle.affix(GOTWaypoint.Hellholt);
		castle.affix(GOTWaypoint.GhostHill);
		castle.affix(GOTWaypoint.Spottswood);
		castle.affix(GOTWaypoint.WaterGardens);
		castle.affix(GOTWaypoint.Lemonwood);
		decorator.addVillage(castle);
		GOTStructureDorneCity town = new GOTStructureDorneCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.GhastonGrey);
		town.affix(GOTWaypoint.Sunspear);
		town.affix(GOTWaypoint.PlankyTown);
		decorator.addVillage(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}