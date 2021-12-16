package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarFightingPit;

public class GOTBiomeAstapor extends GOTBiomeGhiscar {
	public GOTBiomeAstapor(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.clearVillages();
		decorator.clearRandomStructures();

		SpawnListContainer[] c0 = new SpawnListContainer[3];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_UNSULLIED, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[2] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		decorator.addRandomStructure(new GOTStructureGhiscarFightingPit(false), 250);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("astapor");
	}
}