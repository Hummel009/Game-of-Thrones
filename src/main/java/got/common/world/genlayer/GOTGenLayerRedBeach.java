package got.common.world.genlayer;

import got.common.GOTDimension;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.GOTBiomeBleedingBeach;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTGenLayerRedBeach extends GOTGenLayer {
	public GOTDimension dimension;
	public BiomeGenBase targetBiome;

	public GOTGenLayerRedBeach(long l, GOTGenLayer layer, GOTDimension dim, BiomeGenBase target) {
		super(l);
		gotParent = layer;
		dimension = dim;
		targetBiome = target;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] biomes = gotParent.getInts(world, i - 1, k - 1, xSize + 2, zSize + 2);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biomeID = biomes[i1 + 1 + (k1 + 1) * (xSize + 2)];
				GOTBiome biome = dimension.getBiomeList()[biomeID];
				int newBiomeID = biomeID;
				if (biomeID != targetBiome.biomeID && !biome.isWateryBiome()) {
					int biome1 = biomes[i1 + 1 + (k1 + 1 - 1) * (xSize + 2)];
					int biome2 = biomes[i1 + 1 + 1 + (k1 + 1) * (xSize + 2)];
					int biome3 = biomes[i1 + 1 - 1 + (k1 + 1) * (xSize + 2)];
					int biome4 = biomes[i1 + 1 + (k1 + 1 + 1) * (xSize + 2)];
					if (biome1 == targetBiome.biomeID || biome2 == targetBiome.biomeID || biome3 == targetBiome.biomeID || biome4 == targetBiome.biomeID) {
						if (!(biome instanceof GOTBiomeBleedingBeach)) {
							newBiomeID = GOTBiome.bleedingBeach.biomeID;
						}
					}
				}
				ints[i1 + k1 * xSize] = newBiomeID;
			}
		}
		return ints;
	}
}
