package got.common.world.genlayer;

import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

public class GOTGenLayerBiomeSubtypes extends GOTGenLayer {
	public GOTGenLayer biomeLayer;
	public GOTGenLayer variantsLayer;

	public GOTGenLayerBiomeSubtypes(long l, GOTGenLayer biomes, GOTGenLayer subtypes) {
		super(l);
		biomeLayer = biomes;
		variantsLayer = subtypes;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] biomes = biomeLayer.getInts(world, i, k, xSize, zSize);
		int[] variants = variantsLayer.getInts(world, i, k, xSize, zSize);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biome = biomes[i1 + k1 * xSize];
				int variant = variants[i1 + k1 * xSize];
				int newBiome = biome;
				if (biome == GOTBiome.ocean.biomeID && variant < 2) {
					newBiome = GOTBiome.island.biomeID;
				}
				boolean areNotEqual = newBiome != biome;
				ints[i1 + k1 * xSize] = areNotEqual ? newBiome : biome;
			}
		}
		return ints;
	}

	@Override
	public void initWorldGenSeed(long l) {
		biomeLayer.initWorldGenSeed(l);
		variantsLayer.initWorldGenSeed(l);
		super.initWorldGenSeed(l);
	}
}
