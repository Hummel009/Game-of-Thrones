package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTStructurePyramidMeereen;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ghiscar.*;

public class GOTBiomeMeereen extends GOTBiomeGhiscar {
	public GOTBiomeMeereen(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] c0 = new SpawnListContainer[3];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_HARPY, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[2] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);
		GOTStructureGhiscarCity town = new GOTStructureGhiscarCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Meereen, 0, -1, 2);
		decorator.affix(town);
		GOTStructurePyramidMeereen pyramid = new GOTStructurePyramidMeereen(this, 0.0f);
		pyramid.affix(GOTWaypoint.Meereen, 0, -2, 0);
		decorator.affix(pyramid);
		decorator.addRandomStructure(new GOTStructureGhiscarFightingPit(false), 250);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("meereen");
	}
}