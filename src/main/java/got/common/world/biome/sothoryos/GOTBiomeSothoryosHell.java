package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeSothoryosHell extends GOTBiomeSothoryosJungle {

	public GOTBiomeSothoryosHell(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		npcSpawnList.clear();
		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WYVERN, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(c1);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SOTHORYOS_HELL;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosHell");
	}
}