package got.common.world.genlayer;

import net.minecraft.world.World;

public class GOTGenLayerIncludeMapRivers extends GOTGenLayer {
	public GOTGenLayer riverLayer;
	public GOTGenLayer mapRiverLayer;

	public GOTGenLayerIncludeMapRivers(long l, GOTGenLayer rivers, GOTGenLayer mapRivers) {
		super(l);
		riverLayer = rivers;
		mapRiverLayer = mapRivers;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] rivers = riverLayer.getInts(world, i, k, xSize, zSize);
		int[] mapRivers = mapRiverLayer.getInts(world, i, k, xSize, zSize);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int index = i1 + k1 * xSize;
				int isRiver = rivers[index];
				int isMapRiver = mapRivers[index];
				ints[index] = isMapRiver == 2 ? isMapRiver : isRiver;
			}
		}
		return ints;
	}

	@Override
	public void initWorldGenSeed(long l) {
		super.initWorldGenSeed(l);
		riverLayer.initWorldGenSeed(l);
		mapRiverLayer.initWorldGenSeed(l);
	}
}
