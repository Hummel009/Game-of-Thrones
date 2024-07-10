package got.common.world.biome.other;

import got.common.entity.animal.GOTEntitySeagull;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeBeach extends GOTBiomeOcean {
	public GOTBiomeBeach(int i, boolean major, Block block, int meta) {
		super(i, major);
		topBlock = block;
		topBlockMeta = meta;
		fillerBlock = block;
		fillerBlockMeta = meta;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySeagull.class, 20, 4, 4));
	}

}