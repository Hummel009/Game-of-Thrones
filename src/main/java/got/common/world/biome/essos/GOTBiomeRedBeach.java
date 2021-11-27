package got.common.world.biome.essos;

import got.common.world.biome.other.GOTBiomeOcean;
import net.minecraft.block.Block;

public class GOTBiomeRedBeach extends GOTBiomeOcean {
	public GOTBiomeRedBeach(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		biomeColors.setWater(0x640a0a);
	}

	public GOTBiomeRedBeach setBeachBlock(Block block, int meta) {
		topBlock = block;
		topBlockMeta = meta;
		fillerBlock = block;
		fillerBlockMeta = meta;
		return this;
	}
}
