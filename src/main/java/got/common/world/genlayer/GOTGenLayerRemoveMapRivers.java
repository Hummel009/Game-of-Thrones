package got.common.world.genlayer;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTDimension;
import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				initChunkSeed(i + i1, k + k1);
				int biomeID = biomes[i1 + 4 + (k1 + 4) * (xSize + 4 * 2)];
				if (biomeID == GOTBiome.river.biomeID) {
					int replaceID = -1;
					for (int range = 1; range <= 4; ++range) {
						int id;
						int subBiomeID;
						int count;
						HashMap<Integer, Integer> viableBiomes = new HashMap<>();
						HashMap<Integer, Integer> viableBiomesWateryAdjacent = new HashMap<>();
						for (int k2 = k1 - range; k2 <= k1 + range; ++k2) {
							for (int i2 = i1 - range; i2 <= i1 + range; ++i2) {
								GOTBiome subBiome;
								if (Math.abs(i2 - i1) != range && Math.abs(k2 - k1) != range || (subBiome = dimension.getBiomeList()[subBiomeID = biomes[i2 + 4 + (k2 + 4) * (xSize + 4 * 2)]]) == GOTBiome.river) {
									continue;
								}
								boolean wateryAdjacent = subBiome.getHeightBaseParameter() < 0.0f && range == 1;
								HashMap<Integer, Integer> srcMap = wateryAdjacent ? viableBiomesWateryAdjacent : viableBiomes;
								int count2 = 0;
								if (srcMap.containsKey(subBiomeID)) {
									count2 = srcMap.get(subBiomeID);
								}
								count2++;
								srcMap.put(subBiomeID, count2);
							}
						}
						Map<Integer, Integer> priorityMap = viableBiomes;
						if (!viableBiomesWateryAdjacent.isEmpty()) {
							priorityMap = viableBiomesWateryAdjacent;
						}
						if (priorityMap.isEmpty()) {
							continue;
						}
						ArrayList<Integer> maxCountBiomes = new ArrayList<>();
						int maxCount = 0;
						for (Map.Entry<Integer, Integer> e : priorityMap.entrySet()) {
							count = e.getValue();
							if (count <= maxCount) {
								continue;
							}
							maxCount = count;
						}
						for (Map.Entry<Integer, Integer> e : priorityMap.entrySet()) {
							id = e.getKey();
							count = e.getValue();
							if (count != maxCount) {
								continue;
							}
							maxCountBiomes.add(id);
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