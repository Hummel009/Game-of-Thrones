package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeYiTiMarshes extends GOTBiomeYiTi implements GOTBiome.Marshes {
	public GOTBiomeYiTiMarshes(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
		variantChance = 1.0f;
		decorator.setSandPerChunk(0);
		decorator.setQuagmirePerChunk(1);
		decorator.setTreesPerChunk(0);
		decorator.setLogsPerChunk(3);
		decorator.setFlowersPerChunk(0);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(8);
		decorator.setCanePerChunk(10);
		decorator.setReedPerChunk(5);
		decorator.setWaterlilyPerChunk(4);
		decorator.clearSettlements();
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(10).add(c0);
	}
}