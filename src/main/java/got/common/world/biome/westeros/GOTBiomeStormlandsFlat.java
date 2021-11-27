package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.stormlands.GOTStructureStormlandsCity;

public class GOTBiomeStormlandsFlat extends GOTBiomeStormlands {
	public GOTBiomeStormlandsFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
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
		GOTStructureStormlandsCity castle = new GOTStructureStormlandsCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Nightsong, 0, 1);
		castle.affix(GOTWaypoint.Poddingfield);
		castle.affix(GOTWaypoint.HarvestHall);
		castle.affix(GOTWaypoint.Fawntown);
		castle.affix(GOTWaypoint.Blackhaven, -1, 0);
		castle.affix(GOTWaypoint.DeatsHear);
		castle.affix(GOTWaypoint.Stonehelm);
		castle.affix(GOTWaypoint.BlackHeart);
		castle.affix(GOTWaypoint.SeaworthCastle);
		castle.affix(GOTWaypoint.Amberly);
		castle.affix(GOTWaypoint.RainHouse);
		castle.affix(GOTWaypoint.Mistwood);
		castle.affix(GOTWaypoint.Greenstone);
		castle.affix(GOTWaypoint.TudburyHoll);
		castle.affix(GOTWaypoint.Bronzegate, 1, 0);
		castle.affix(GOTWaypoint.Felwood, 0, 1);
		castle.affix(GOTWaypoint.Grandview);
		castle.affix(GOTWaypoint.HaystackHall);
		castle.affix(GOTWaypoint.Gallowsgrey);
		castle.affix(GOTWaypoint.Parchments);
		castle.affix(GOTWaypoint.BroadArch);
		castle.affix(GOTWaypoint.EvenfallHall);
		castle.affix(GOTWaypoint.StormsEnd);
		decorator.affix(castle);
		GOTStructureStormlandsCity town = new GOTStructureStormlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.WeepingTown);
		decorator.affix(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}