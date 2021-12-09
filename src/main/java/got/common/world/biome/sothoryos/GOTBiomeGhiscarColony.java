package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarCity;
import got.common.world.structure.other.*;

public class GOTBiomeGhiscarColony extends GOTBiomeSothoryosJungle {
	public GOTBiomeGhiscarColony(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 1;
		npcSpawnList.clear();
		decorator.clearVillages();
		decorator.clearRandomStructures();
		SpawnListContainer[] c0 = new SpawnListContainer[1];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);
		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(c1);
		GOTStructureGhiscarCity colony = new GOTStructureGhiscarCity(this, 0.0f).setIsColony();
		colony.affix(GOTWaypoint.BarterBeach);
		colony.affix(GOTWaypoint.Gogossos);
		colony.affix(GOTWaypoint.Zamettar, 0, -1, 2);
		colony.affix(GOTWaypoint.Gorosh);
		decorator.affix(colony);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.SOTHORYOS(1, 4), 400);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_GHISCAR_COLONY;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("ghiscarColony");
	}
}