package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeDoshKhalin extends GOTBiomeDothrakiSea {
	public GOTBiomeDoshKhalin(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		npcSpawnList.clear();
		decorator.clearVillages();
		decorator.clearTrees();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] c = new SpawnListContainer[1];
		c[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DOTHRAKI_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_DOSH_KHALIN;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("doshKhalin");
	}

	@Override
	public boolean hasDomesticAnimals() {
		return true;
	}
}