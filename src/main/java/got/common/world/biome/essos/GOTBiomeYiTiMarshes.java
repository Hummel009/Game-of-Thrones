package got.common.world.biome.essos;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeYiTiMarshes extends GOTBiomeYiTi {
	public GOTBiomeYiTiMarshes(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.SWAMP_LOWLAND);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		variantChance = 1.0f;
		decorator.sandPerChunk = 0;
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 0;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 8;
		decorator.canePerChunk = 10;
		decorator.reedPerChunk = 5;
		decorator.waterlilyPerChunk = 4;
		decorator.clearVillages();
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("volantisMarshes");
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}