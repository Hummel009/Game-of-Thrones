package io.github.hummel009.minecraft.got.common.world.genlayer;

import cpw.mods.fml.common.FMLLog;
import io.github.hummel009.minecraft.got.common.GOTDimension;
import io.github.hummel009.minecraft.got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class GOTGenLayerRemoveMapRivers extends GOTGenLayer {
	private final GOTDimension dimension;

	public GOTGenLayerRemoveMapRivers(long l, GOTGenLayer biomes, GOTDimension dim) {
		super(l);
		gotParent = biomes;
		dimension = dim;
	}

	@Override
	public int[] getInts(World world, int i, int k, int xSize, int zSize) {
		int[] biomes = gotParent.getInts(world, i - 4, k - 4, xSize + 4 * 2, zSize + 4 * 2);
		int[] ints = GOTIntCache.get(world).getIntArray(xSize * zSize);

		int biomeListLen = dimension.getBiomeList().length;
		int[] counts = new int[biomeListLen];
		int[] countsWatery = new int[biomeListLen];

		int riverID = GOTBiome.river.biomeID;

		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biomeID = biomes[i1 + 4 + (k1 + 4) * (xSize + 4 * 2)];
				if (biomeID == riverID) {
					int replaceID = -1;
					for (int range = 1; range <= 4; ++range) {
						Arrays.fill(counts, 0);
						Arrays.fill(countsWatery, 0);
						for (int k2 = k1 - range; k2 <= k1 + range; ++k2) {
							for (int i2 = i1 - range; i2 <= i1 + range; ++i2) {
								if (Math.abs(i2 - i1) != range && Math.abs(k2 - k1) != range) {
									continue;
								}
								int subBiomeID = biomes[i2 + 4 + (k2 + 4) * (xSize + 4 * 2)];
								if (subBiomeID == riverID) {
									continue;
								}
								GOTBiome subBiome = dimension.getBiomeList()[subBiomeID];
								boolean wateryAdjacent = subBiome.getHeightBaseParameter() < 0.0f && range == 1;
								if (wateryAdjacent) {
									countsWatery[subBiomeID]++;
								} else {
									counts[subBiomeID]++;
								}
							}
						}
						int[] priority = counts;
						boolean hasWatery = false;
						for (int c : countsWatery) {
							if (c > 0) {
								hasWatery = true;
								break;
							}
						}
						if (hasWatery) {
							priority = countsWatery;
						}
						int maxCount = 0;
						ArrayList<Integer> maxCountBiomes = null;
						for (int id = 0; id < biomeListLen; ++id) {
							int c = priority[id];
							if (c == 0) {
								continue;
							}
							if (c > maxCount) {
								maxCount = c;
								if (maxCountBiomes == null) {
									maxCountBiomes = new ArrayList<>();
								} else {
									maxCountBiomes.clear();
								}
								maxCountBiomes.add(id);
							} else if (c == maxCount) {
								maxCountBiomes.add(id);
							}
						}
						if (maxCountBiomes == null) {
							continue;
						}
						replaceID = maxCountBiomes.get(nextInt(maxCountBiomes.size()));
						break;
					}
					if (replaceID == -1) {
						FMLLog.warning("WARNING! GOT map generation failed to replace map river at %d, %d", i, k);
						ints[i1 + k1 * xSize] = 0;
						continue;
					}
					ints[i1 + k1 * xSize] = replaceID;
					continue;
				}
				ints[i1 + k1 * xSize] = biomeID;
			}
		}
		return ints;
	}
}