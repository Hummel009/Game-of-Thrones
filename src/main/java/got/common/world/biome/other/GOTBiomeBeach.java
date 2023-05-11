package got.common.world.biome.other;

import got.common.entity.animal.GOTEntitySeagull;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeBeach extends GOTBiomeOcean {
	public GOTBiomeBeach(int i, boolean major) {
		super(i, major);
		setMinMaxHeight(0.1f, 0.0f);
		setTemperatureRainfall(0.8f, 0.4f);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySeagull.class, 20, 4, 4));
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	public GOTBiomeBeach setBeachBlock(Block block, int meta) {
		topBlock = block;
		topBlockMeta = meta;
		fillerBlock = block;
		fillerBlockMeta = meta;
		return this;
	}
}
