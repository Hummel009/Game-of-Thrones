package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.GOTBiomeEssos;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.other.GOTStructureStoneRuin;
import got.common.world.structure.westeros.dorne.GOTStructureDorneSettlement;
import got.common.world.structure.westeros.dorne.GOTStructureDorneWatchfort;

import java.util.ArrayList;

public class GOTBiomeDorne extends GOTBiomeEssos {
	public GOTBiomeDorne(int i, boolean major) {
		super(i, major);
		setDarkUnreliable();
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.2f);
		decorator.addSettlement(new GOTStructureDorneSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureDorneWatchfort(false), 800);
		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(8).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		ArrayList<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		ArrayList<SpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.RED_SCORPION, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(2).add(c3);
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
	}

	@Override
	public boolean disableNoise() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDorne;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("dorne");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.DORNE;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}
}
