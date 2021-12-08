package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureRuins;
import got.common.world.structure.westeros.crownlands.GOTStructureCrownlandsCity;
import got.common.world.structure.westeros.crownlands.red.GOTWaypointRedCastle;

public class GOTBiomeCrownlands extends GOTBiomeWesteros {
	public GOTBiomeCrownlands(int i, boolean major) {
		super(i, major);
		SpawnListContainer[] c0 = new SpawnListContainer[1];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.CROWNLANDS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(5).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(5).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		SpawnListContainer[] c4 = new SpawnListContainer[1];
		c4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c4);

		SpawnListContainer[] c5 = new SpawnListContainer[1];
		c5[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c5);

		SpawnListContainer[] c6 = new SpawnListContainer[1];
		c6[0] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c6);

		SpawnListContainer[] c7 = new SpawnListContainer[1];
		c7[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c7);

		decorator.affix(new GOTStructureCrownlandsCity(this, 1.0f));

		GOTStructureCrownlandsCity village = new GOTStructureCrownlandsCity(this, 0.0f);
		village.affix(GOTWaypoint.Briarwhite);
		decorator.affix(village);

		GOTStructureCrownlandsCity castle = new GOTStructureCrownlandsCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Antlers);
		castle.affix(GOTWaypoint.Stokeworth);
		castle.affix(GOTWaypoint.DyreDen);
		castle.affix(GOTWaypoint.Brownhollow);
		castle.affix(GOTWaypoint.RooksRest, 0, -1);
		castle.affix(GOTWaypoint.Rosby, 0, -1);
		castle.affix(GOTWaypoint.Hayford, -1, 0);
		decorator.affix(castle);

		GOTStructureCrownlandsCity town = new GOTStructureCrownlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Duskendale, -2, 0, 3);
		decorator.affix(town);

		GOTStructureCrownlandsCity capital = new GOTStructureCrownlandsCity(this, 0.0f).setIsCapital();
		capital.affix(GOTWaypoint.KingsLanding, 1, 0, 1);
		decorator.affix(capital);

		GOTWaypointRedCastle rc = new GOTWaypointRedCastle(this, 0.0f);
		rc.affix(GOTWaypoint.KingsLanding, 2, 0, 1);
		decorator.affix(rc);

		GOTStructureRuins ruins = new GOTStructureRuins(this, 0.0f);
		ruins.affix(GOTWaypoint.HollardCastle);
		ruins.affix(GOTWaypoint.Whispers);
		decorator.affix(ruins);

		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.STORMLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.RIVERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_CROWNLANDS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("crownlands");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.CROWNLANDS;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}