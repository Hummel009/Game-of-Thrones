package got.common.world.genlayer;

import net.minecraft.world.World;

public class GOTGenLayerClassicOcean extends GOTGenLayer {
	public GOTGenLayerClassicOcean(long l) {
		super(l);
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				boolean ocean = nextInt(5) == 0;
				ints[i1 + k1 * xSize] = ocean ? 1 : 0;
			}
		}
		return ints;
	}
}