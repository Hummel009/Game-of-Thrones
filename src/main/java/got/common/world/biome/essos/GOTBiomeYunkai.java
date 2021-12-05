package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTStructureGhiscarPyramid1;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ghiscar.*;

public class GOTBiomeYunkai extends GOTBiomeGhiscar {
	public GOTBiomeYunkai(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.clearVillages();
		
		SpawnListContainer[] c0 = new SpawnListContainer[3];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_GUARDIAN, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[2] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);
		
		GOTStructureGhiscarCity town = new GOTStructureGhiscarCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Yunkai, -1, 0, 1);
		decorator.affix(town);
		
		GOTStructureGhiscarPyramid1 pyramid = new GOTStructureGhiscarPyramid1(this, 0.0f);
		pyramid.affix(GOTWaypoint.Yunkai, -2, 0);
		decorator.affix(pyramid);
		
		decorator.addRandomStructure(new GOTStructureGhiscarFightingPit(false), 250);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("yunkai");
	}
}