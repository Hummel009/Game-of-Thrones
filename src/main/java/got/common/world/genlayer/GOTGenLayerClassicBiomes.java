package got.common.world.genlayer;

import got.common.GOTDimension;
import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

import java.util.List;

public class GOTGenLayerClassicBiomes extends GOTGenLayer {
	private final GOTDimension dimension;

	public GOTGenLayerClassicBiomes(long l, GOTGenLayer layer, GOTDimension dim) {
		super(l);
		gotParent = layer;
		dimension = dim;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] oceans = gotParent.getInts(world, i, k, xSize, zSize);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int biomeID;
				initChunkSeed(i + i1, k + k1);
				int isOcean = oceans[i1 + k1 * xSize];
				if (isOcean == 1) {
					biomeID = GOTBiome.ocean.biomeID;
				} else {
					List<GOTBiome> biomeList = dimension.getMajorBiomes();
					int randIndex = nextInt(biomeList.size());
					GOTBiome biome = biomeList.get(randIndex);
					biomeID = biome.biomeID;
				}
				ints[i1 + k1 * xSize] = biomeID;
			}
		}
		return ints;
	}
}