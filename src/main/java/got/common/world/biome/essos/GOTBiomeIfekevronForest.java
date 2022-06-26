package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeIfekevronForest extends GOTBiomeTropicalForest {
	public GOTBiomeIfekevronForest(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		SpawnListContainer[] c = new SpawnListContainer[1];
		c[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IFEKEVRON, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterIfekevron;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("ifekevronForest");
	}
}