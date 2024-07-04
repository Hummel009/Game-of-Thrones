package got.common.world.biome.essos;

import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;

public class GOTBiomeBleedingBeach extends GOTBiomeBleedingSea {
	public GOTBiomeBleedingBeach(int i, boolean major) {
		super(i, major);
	}

	public GOTBiome setBeachBlock(Block block, int meta) {
		topBlock = block;
		topBlockMeta = meta;
		fillerBlock = block;
		fillerBlockMeta = meta;
		return this;
	}
}