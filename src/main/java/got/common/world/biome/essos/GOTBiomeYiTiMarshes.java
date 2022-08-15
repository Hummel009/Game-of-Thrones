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
		variantChance = 1.0f;
		addBiomeVariant(GOTBiomeVariant.SWAMP_LOWLAND);
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
		npcSpawnList.clear();
		decorator.clearVillages();
		invasionSpawns.clearInvasions();

		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 1).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(1).add(c0);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
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