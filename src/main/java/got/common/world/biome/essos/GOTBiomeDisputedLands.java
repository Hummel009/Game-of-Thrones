package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.gold.GOTStructureGoldenCamp;
import got.common.world.structure.other.GOTStructureStoneRuin;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeDisputedLands extends GOTBiomeEssos {
	public GOTBiomeDisputedLands(int i, boolean major) {
		super(i, major);
		decorator.addSettlement(new GOTStructureGoldenCamp(this, 1.0f));
		invasionSpawns.addInvasion(GOTInvasions.MYR, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.VOLANTIS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.TYROSH, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.LYS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.BRAAVOS, GOTEventSpawner.EventChance.UNCOMMON);
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GOLDEN_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.BRAAVOS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.MYR_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<SpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.LYS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);
		Collection<SpawnListContainer> c4 = new ArrayList<>();
		c4.add(GOTBiomeSpawnList.entry(GOTSpawnList.TYROSH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c4);
		Collection<SpawnListContainer> c5 = new ArrayList<>();
		c5.add(GOTBiomeSpawnList.entry(GOTSpawnList.VOLANTIS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c5);
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public boolean disableNoise() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDisputedLands;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}
}