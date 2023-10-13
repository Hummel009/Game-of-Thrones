package got.common.world.biome.essos;

import java.util.ArrayList;
import java.util.Collection;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarFightingPit;

public class GOTBiomeGhiscarYunkai extends GOTBiomeGhiscar {
	public GOTBiomeGhiscarYunkai(int i, boolean major) {
		super(i, major);
		decorator.addStructure(new GOTStructureGhiscarFightingPit(false), 150);
		npcSpawnList.clear();
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_GUARDIAN, 4).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

}