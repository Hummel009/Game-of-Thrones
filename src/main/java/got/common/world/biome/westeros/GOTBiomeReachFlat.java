package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureTower;
import got.common.world.structure.westeros.reach.GOTStructureReachCity;

public class GOTBiomeReachFlat extends GOTBiomeReach {
	public GOTBiomeReachFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.REACH_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.REACH_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureReachCity village = new GOTStructureReachCity(this, 0.0f);
		village.affix(GOTWaypoint.Oxcross);
		decorator.addVillage(village);
		GOTStructureReachCity castle = new GOTStructureReachCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.SunHouse, 0, -1);
		castle.affix(GOTWaypoint.GarnetGrove, -1, 0);
		castle.affix(GOTWaypoint.Blackcrown);
		castle.affix(GOTWaypoint.Honeyholt);
		castle.affix(GOTWaypoint.Bandallon);
		castle.affix(GOTWaypoint.BrightwaterKeep);
		castle.affix(GOTWaypoint.Uplands);
		castle.affix(GOTWaypoint.HornHill);
		castle.affix(GOTWaypoint.Whitegrove, -1, 0);
		castle.affix(GOTWaypoint.Dunstonbury);
		castle.affix(GOTWaypoint.Southshield);
		castle.affix(GOTWaypoint.Greenshield);
		castle.affix(GOTWaypoint.Grimston);
		castle.affix(GOTWaypoint.HewettTown, 0, -1);
		castle.affix(GOTWaypoint.Appleton, 0, -1);
		castle.affix(GOTWaypoint.OldOak, -1, 0);
		castle.affix(GOTWaypoint.RedLake);
		castle.affix(GOTWaypoint.Coldmoat);
		castle.affix(GOTWaypoint.Goldengrove);
		castle.affix(GOTWaypoint.Holyhall);
		castle.affix(GOTWaypoint.DarkDell);
		castle.affix(GOTWaypoint.Hammerhal);
		castle.affix(GOTWaypoint.IvyHall);
		castle.affix(GOTWaypoint.Ring);
		castle.affix(GOTWaypoint.CiderHall);
		castle.affix(GOTWaypoint.Longtable);
		castle.affix(GOTWaypoint.NewBarrel);
		castle.affix(GOTWaypoint.GrassyVale);
		castle.affix(GOTWaypoint.Ashford, 0, 1);
		castle.affix(GOTWaypoint.Bitterbridge, 0, 1);
		decorator.addVillage(castle);
		GOTStructureReachCity town = new GOTStructureReachCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.StarfishHarbor);
		town.affix(GOTWaypoint.Vinetown);
		town.affix(GOTWaypoint.Ryamsport);
		town.affix(GOTWaypoint.HewettTown, 0, 1);
		town.affix(GOTWaypoint.Ashford, 0, -1);
		town.affix(GOTWaypoint.Appleton, 0, 1, 2);
		town.affix(GOTWaypoint.Tumbleton, 0, -1);
		town.affix(GOTWaypoint.Smithyton, 0, 1, 2);
		town.affix(GOTWaypoint.Oldtown, -1, 0, 3);
		decorator.addVillage(town);
		GOTStructureTower tower = new GOTStructureTower(this, 0.0f);
		tower.affix(GOTWaypoint.Standfast);
		tower.affix(GOTWaypoint.ThreeTowers, -1, 0, 1);
		tower.affix(GOTWaypoint.ThreeTowers, -1, -1, 1);
		tower.affix(GOTWaypoint.ThreeTowers, -1, 1, 1);
		tower.affix(GOTWaypoint.HightowerLitehouse);
		decorator.addVillage(tower);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}