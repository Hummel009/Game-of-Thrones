package got.common.world.biome.ulthos;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeUlthosForest extends GOTBiomeUlthos {
	public GOTBiomeUlthosForest(int i, boolean major) {
		super(i, major);
		setupStandartForestFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.FLOWERS);
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.treesPerChunk = 8;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 2;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 12;
		decorator.doubleGrassPerChunk = 6;
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.ULTHOS, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosForest;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosForest");
	}

	@Override
	public boolean isBushland() {
		return false;
	}
}
