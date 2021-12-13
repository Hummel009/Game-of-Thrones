package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureRuins;
import got.common.world.structure.westeros.stormlands.*;

public class GOTBiomeStormlands extends GOTBiomeWesteros {
	public GOTBiomeStormlands(int i, boolean major) {
		super(i, major);
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FIELD_CORN, 0.2f);
		decorator.generatePipeweed = true;

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		decorator.affix(new GOTStructureStormlandsCity(this, 1.0f));

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

		GOTStructureRuins ruins = new GOTStructureRuins(this, 0.0f);
		ruins.affix(GOTWaypoint.Summerhall);
		ruins.affix(GOTWaypoint.TowerOfJoy);
		decorator.affix(ruins);

		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);

		decorator.addRandomStructure(new GOTStructureStormlandsWatchfort(false), 800);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_STORMLANDS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("stormlands");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.STORMLANDS;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
