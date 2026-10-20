package io.github.hummel009.minecraft.got.common.world.genlayer;

import io.github.hummel009.minecraft.got.common.GOTDimension;
import io.github.hummel009.minecraft.got.common.world.biome.GOTBiome;
import io.github.hummel009.minecraft.got.common.world.biome.other.GOTBiomeBeach;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTGenLayerBeach extends GOTGenLayer {
	private final GOTDimension dimension;
	private final BiomeGenBase targetBiome;

	public GOTGenLayerBeach(long l, GOTGenLayer layer, GOTDimension dim, BiomeGenBase target) {
		super(l);
		gotParent = layer;
		dimension = dim;
		targetBiome = target;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] biomes = gotParent.getInts(world, i - 1, k - 1, xSize + 2, zSize + 2);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		int targetID = targetBiome.biomeID;
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biomeID = biomes[i1 + 1 + (k1 + 1) * (xSize + 2)];
				GOTBiome biome = dimension.getBiomeList()[biomeID];
				int newBiomeID = biomeID;
				if (biomeID != targetID && biome.getHeightBaseParameter() >= 0.0f) {
					int biome1 = biomes[i1 + 1 + (k1 + 1 - 1) * (xSize + 2)];
					int biome2 = biomes[i1 + 1 + 1 + (k1 + 1) * (xSize + 2)];
					int biome3 = biomes[i1 + 1 - 1 + (k1 + 1) * (xSize + 2)];
					int biome4 = biomes[i1 + 1 + (k1 + 1 + 1) * (xSize + 2)];
					if (biome1 == targetID || biome2 == targetID || biome3 == targetID || biome4 == targetID) {
						if (!(biome instanceof GOTBiomeBeach)) {
							newBiomeID = (nextInt(20) == 0 ? GOTBiome.beachGravel : GOTBiome.beach).biomeID;
						}
					}
				}
				ints[i1 + k1 * xSize] = newBiomeID;
			}
		}
		return ints;
	}
}