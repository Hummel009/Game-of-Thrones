package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.volantis.GOTStructureVolantisFortress;
import got.common.world.structure.essos.volantis.GOTStructureVolantisSettlement;
import got.common.world.structure.other.GOTStructureStoneRuin;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeVolantis extends GOTBiomeEssos {
	public GOTBiomeVolantis(int i, boolean major) {
		super(i, major);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		addBiomeVariant(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		decorator.addSettlement(new GOTStructureVolantisSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureVolantisFortress(false), 800);
		invasionSpawns.addInvasion(GOTInvasions.BRAAVOS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.TYROSH, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.LYS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.MYR, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.NORVOS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.DOTHRAKI, GOTEventSpawner.EventChance.UNCOMMON);
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.VOLANTIS_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.VOLANTIS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN));
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
		c5.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORVOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c5);
		decorator.addStructure(new GOTStructureStoneRuin.RuinSandstone(1, 4), 400);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterVolantis;
	}

}