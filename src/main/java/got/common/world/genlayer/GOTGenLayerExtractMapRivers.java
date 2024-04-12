package got.common.world.genlayer;

import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

public class GOTGenLayerExtractMapRivers extends GOTGenLayer {
	public GOTGenLayerExtractMapRivers(long l, GOTGenLayer layer) {
		super(l);
		gotParent = layer;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] biomes = gotParent.getInts(world, i, k, xSize, zSize);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biomeID = biomes[i1 + k1 * xSize];
				ints[i1 + k1 * xSize] = biomeID == GOTBiome.river.biomeID ? 2 : 0;
			}
		}
		return ints;
	}
}