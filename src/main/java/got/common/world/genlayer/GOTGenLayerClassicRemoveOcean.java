package got.common.world.genlayer;

import net.minecraft.world.World;

public class GOTGenLayerClassicRemoveOcean extends GOTGenLayer {
	public GOTGenLayerClassicRemoveOcean(long l, GOTGenLayer layer) {
		super(l);
		gotParent = layer;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] oceans = gotParent.getInts(world, i, k, xSize, zSize);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int isOcean = oceans[i1 + k1 * xSize];
				if (Math.abs(i + i1) <= 1 && Math.abs(k + k1) <= 1) {
					isOcean = 0;
				}
				ints[i1 + k1 * xSize] = isOcean;
			}
		}
		return ints;
	}
}
