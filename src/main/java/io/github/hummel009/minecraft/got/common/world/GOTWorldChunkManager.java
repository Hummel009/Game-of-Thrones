package io.github.hummel009.minecraft.got.common.world;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.hummel009.minecraft.got.GOT;
import io.github.hummel009.minecraft.got.common.GOTDimension;
import io.github.hummel009.minecraft.got.common.util.GOTCrashHandler;
import io.github.hummel009.minecraft.got.common.world.biome.GOTBiome;
import io.github.hummel009.minecraft.got.common.world.biome.sothoryos.GOTBiomeSothoryosJungle;
import io.github.hummel009.minecraft.got.common.world.biome.sothoryos.GOTBiomeSothoryosMangrove;
import io.github.hummel009.minecraft.got.common.world.biome.variant.GOTBiomeVariant;
import io.github.hummel009.minecraft.got.common.world.biome.variant.GOTBiomeVariantList;
import io.github.hummel009.minecraft.got.common.world.biome.variant.GOTBiomeVariantStorage;
import io.github.hummel009.minecraft.got.common.world.genlayer.*;
import io.github.hummel009.minecraft.got.common.world.map.GOTFixedStructures;
import io.github.hummel009.minecraft.got.common.world.structure.other.GOTSettlementPositionCache;
import io.github.hummel009.minecraft.got.common.world.structure.other.GOTStructureBaseSettlement;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.MapGenStructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GOTWorldChunkManager extends WorldChunkManager {
	private static final GOTBiomeVariantList EMPTY_LIST = new GOTBiomeVariantList();
	private static final int LAYER_BIOME = 0;

	private final Map<GOTStructureBaseSettlement, GOTSettlementPositionCache> settlementCacheMap = new HashMap<>();
	private final Map<MapGenStructure, GOTSettlementPositionCache> structureCacheMap = new HashMap<>();

	private final BiomeCache biomeCache;
	private final World worldObj;

	private final GOTDimension gotDimension;

	private GOTGenLayer[] chunkGenLayers;
	private GOTGenLayer[] worldLayers;

	public GOTWorldChunkManager(World world, GOTDimension dim) {
		worldObj = world;
		biomeCache = new BiomeCache(this);
		gotDimension = dim;
		setupGenLayers();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean areBiomesViable(int i, int k, int range, List list) {
		GOTIntCache.get(worldObj).resetIntCache();
		int i1 = i - range >> 2;
		int k1 = k - range >> 2;
		int i2 = i + range >> 2;
		int k2 = k + range >> 2;
		int i3 = i2 - i1 + 1;
		int k3 = k2 - k1 + 1;
		int[] ints = chunkGenLayers[LAYER_BIOME].getInts(worldObj, i1, k1, i3, k3);
		for (int l = 0; l < i3 * k3; ++l) {
			GOTBiome biome = gotDimension.getBiomeList()[ints[l]];
			if (list.contains(biome)) {
				continue;
			}
			return false;
		}
		return true;
	}

	public boolean areVariantsSuitableSettlement(int i, int k, int range) {
		int i1 = i - range >> 2;
		int k1 = k - range >> 2;
		int i2 = i + range >> 2;
		int k2 = k + range >> 2;
		int i3 = i2 - i1 + 1;
		int k3 = k2 - k1 + 1;
		BiomeGenBase[] biomes = getBiomesForGeneration(null, i1, k1, i3, k3);
		for (GOTBiomeVariant v : getVariantsChunkGen(null, i1, k1, i3, k3, biomes)) {
			if (v.getHillFactor() > 1.0f || v.getTreeFactor() > 1.0f || v.isDisableSettlements() || v.isAbsoluteHeight() && v.getAbsoluteHeightLevel() < 0.0f) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ChunkPosition findBiomePosition(int i, int k, int range, List list, Random random) {
		GOTIntCache.get(worldObj).resetIntCache();
		int i1 = i - range >> 2;
		int k1 = k - range >> 2;
		int i2 = i + range >> 2;
		int k2 = k + range >> 2;
		int i3 = i2 - i1 + 1;
		int k3 = k2 - k1 + 1;
		int[] ints = chunkGenLayers[LAYER_BIOME].getInts(worldObj, i1, k1, i3, k3);
		ChunkPosition chunkpos = null;
		int j = 0;
		for (int l = 0; l < i3 * k3; ++l) {
			int xPos = i1 + l % i3 << 2;
			int zPos = k1 + l / i3 << 2;
			GOTBiome biome = gotDimension.getBiomeList()[ints[l]];
			if (!list.contains(biome) || chunkpos != null && random.nextInt(j + 1) != 0) {
				continue;
			}
			chunkpos = new ChunkPosition(xPos, 0, zPos);
			++j;
		}
		return chunkpos;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize, boolean useCache) {
		BiomeGenBase[] bms = biomes;
		GOTIntCache.get(worldObj).resetIntCache();
		if (bms == null || bms.length < xSize * zSize) {
			bms = new BiomeGenBase[xSize * zSize];
		}
		if (useCache && xSize == 16 && zSize == 16 && (i & 0xF) == 0 && (k & 0xF) == 0) {
			BiomeGenBase[] cachedBiomes = biomeCache.getCachedBiomes(i, k);
			System.arraycopy(cachedBiomes, 0, bms, 0, 16 * 16);
			return bms;
		}
		int[] ints = worldLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			bms[l] = gotDimension.getBiomeList()[biomeID];
		}
		return bms;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int i, int k) {
		return biomeCache.getBiomeGenAt(i, k);
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize) {
		BiomeGenBase[] biomes1 = biomes;
		GOTIntCache.get(worldObj).resetIntCache();
		if (biomes1 == null || biomes1.length < xSize * zSize) {
			biomes1 = new BiomeGenBase[xSize * zSize];
		}
		int[] ints = chunkGenLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			biomes1[l] = gotDimension.getBiomeList()[biomeID];
		}
		return biomes1;
	}

	public GOTBiomeVariant getBiomeVariantAt(int i, int k) {
		byte[] variants;
		Chunk chunk = worldObj.getChunkFromBlockCoords(i, k);
		if (chunk != null && (variants = GOTBiomeVariantStorage.getChunkBiomeVariants(worldObj, chunk)) != null) {
			if (variants.length == 256) {
				int chunkX = i & 0xF;
				int chunkZ = k & 0xF;
				byte variantID = variants[chunkX + chunkZ * 16];
				return GOTBiomeVariant.getVariantForID(variantID);
			}
			FMLLog.severe("Found chunk biome variant array of unexpected length " + variants.length);
		}
		if (!worldObj.isRemote) {
			return getBiomeVariants(null, i, k, 1, 1)[0];
		}
		return GOTBiomeVariant.STANDARD;
	}

	public GOTBiomeVariant[] getBiomeVariants(GOTBiomeVariant[] variants, int i, int k, int xSize, int zSize) {
		return getBiomeVariantsFromLayers(variants, i, k, xSize, zSize, null, false);
	}

	private GOTBiomeVariant[] getBiomeVariantsFromLayers(GOTBiomeVariant[] variants, int i, int k, int xSize, int zSize, BiomeGenBase[] biomeSource, boolean isChunkGeneration) {
		GOTBiomeVariant[] variants1 = variants;
		GOTIntCache.get(worldObj).resetIntCache();
		BiomeGenBase[] biomes = new BiomeGenBase[xSize * zSize];
		if (biomeSource != null) {
			biomes = biomeSource;
		} else {
			for (int k1 = 0; k1 < zSize; ++k1) {
				for (int i1 = 0; i1 < xSize; ++i1) {
					int index = i1 + k1 * xSize;
					biomes[index] = GOTCrashHandler.getBiomeGenForCoords(worldObj, i + i1, k + k1);
				}
			}
		}
		if (variants1 == null || variants1.length < xSize * zSize) {
			variants1 = new GOTBiomeVariant[xSize * zSize];
		}
		GOTGenLayer[] sourceGenLayers = isChunkGeneration ? chunkGenLayers : worldLayers;
		GOTGenLayer variantsLarge = sourceGenLayers[1];
		GOTGenLayer variantsSmall = sourceGenLayers[2];
		GOTGenLayer variantsLakes = sourceGenLayers[3];
		GOTGenLayer variantsRivers = sourceGenLayers[4];
		int[] variantsLargeInts = variantsLarge.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsSmallInts = variantsSmall.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsLakesInts = variantsLakes.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsRiversInts = variantsRivers.getInts(worldObj, i, k, xSize, zSize);

		byte[] precomputedFlags = null;
		if (isChunkGeneration) {
			precomputedFlags = new byte[xSize * zSize];
			for (int k1 = 0; k1 < zSize; ++k1) {
				for (int i1 = 0; i1 < xSize; ++i1) {
					int index = i1 + k1 * xSize;
					int xPos = i + i1 << 2;
					int zPos = k + k1 << 2;
					byte flags = 0;
					if (GOTFixedStructures.hasMapFeatures(worldObj)) {
						boolean[] mtnStr = GOTFixedStructures.isMountainOrStructureNear(worldObj, xPos, zPos);
						if (mtnStr[0]) {
							flags |= 1;
						}
						if (mtnStr[1]) {
							flags |= 2;
						}
					}
					GOTBiome biome = (GOTBiome) biomes[index];
					if (biome.getDecorator().anyFixedSettlementsAt(worldObj, xPos, zPos)) {
						flags |= 4;
					}
					precomputedFlags[index] = flags;
				}
			}
		}

		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int riverCode;
				int index = i1 + k1 * xSize;
				GOTBiome biome = (GOTBiome) biomes[index];
				GOTBiomeVariant variant = GOTBiomeVariant.STANDARD;
				int xPos = i + i1;
				int zPos = k + k1;
				boolean mountainNear;
				boolean structureNear;
				boolean fixedSettlementNear;
				if (isChunkGeneration) {
					byte flags = precomputedFlags[index];
					mountainNear = (flags & 1) != 0;
					structureNear = (flags & 2) != 0;
					fixedSettlementNear = (flags & 4) != 0;
				} else {
					boolean[] mtnStr = GOTFixedStructures.isMountainOrStructureNear(worldObj, xPos, zPos);
					mountainNear = mtnStr[0];
					structureNear = mtnStr[1];
					fixedSettlementNear = biome.getDecorator().anyFixedSettlementsAt(worldObj, xPos, zPos);
				}
				if (fixedSettlementNear) {
					variant = GOTBiomeVariant.STEPPE;
				} else {
					if (!mountainNear) {
						float variantChance = biome.getVariantChance();
						if (variantChance > 0.0f) {
							for (int pass = 0; pass <= 1; ++pass) {
								GOTBiomeVariantList variantList = pass == 0 ? EMPTY_LIST : biome.getBiomeVariants();
								if (variantList.isEmpty()) {
									continue;
								}
								int[] sourceInts = pass == 0 ? variantsLargeInts : variantsSmallInts;
								int variantCode = sourceInts[index];
								float variantF = (float) variantCode / GOTGenLayerBiomeVariants.RANDOM_MAX;
								if (variantF < variantChance) {
									float variantFNormalised = variantF / variantChance;
									variant = variantList.get(variantFNormalised);
									break;
								}
								variant = GOTBiomeVariant.STANDARD;
							}
						}
						if (!structureNear && biome.getEnableRiver()) {
							int lakeCode = variantsLakesInts[index];
							if (GOTGenLayerBiomeVariantsLake.getFlag(lakeCode, 1)) {
								variant = GOTBiomeVariant.LAKE;
							}
							if (GOTGenLayerBiomeVariantsLake.getFlag(lakeCode, 2) && biome instanceof GOTBiomeSothoryosJungle) {
								variant = GOTBiomeVariant.LAKE;
							}
							if (GOTGenLayerBiomeVariantsLake.getFlag(lakeCode, 4) && biome instanceof GOTBiomeSothoryosMangrove) {
								variant = GOTBiomeVariant.LAKE;
							}
						}
					}
					riverCode = variantsRiversInts[index];
					if (riverCode == 2 || riverCode == 1 && biome.getEnableRiver() && !structureNear) {
						variant = GOTBiomeVariant.RIVER;
					}
				}
				variants1[index] = variant;
			}
		}
		return variants1;
	}

	@Override
	public float[] getRainfall(float[] rainfall, int i, int k, int xSize, int zSize) {
		float[] rainfall1 = rainfall;
		GOTIntCache.get(worldObj).resetIntCache();
		if (rainfall1 == null || rainfall1.length < xSize * zSize) {
			rainfall1 = new float[xSize * zSize];
		}
		int[] ints = worldLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			float f = gotDimension.getBiomeList()[biomeID].getIntRainfall() / 65536.0f;
			if (f > 1.0f) {
				f = 1.0f;
			}
			rainfall1[l] = f;
		}
		return rainfall1;
	}

	public GOTSettlementPositionCache getSettlementCache(GOTStructureBaseSettlement settlement) {
		return settlementCacheMap.computeIfAbsent(settlement, k -> new GOTSettlementPositionCache());
	}

	public GOTSettlementPositionCache getStructureCache(MapGenStructure structure) {
		return structureCacheMap.computeIfAbsent(structure, k -> new GOTSettlementPositionCache());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float f, int height) {
		if (worldObj.isRemote && GOT.isNewYear()) {
			return 0.0f;
		}
		return f;
	}

	public GOTBiomeVariant[] getVariantsChunkGen(GOTBiomeVariant[] variants, int i, int k, int xSize, int zSize, BiomeGenBase[] biomeSource) {
		return getBiomeVariantsFromLayers(variants, i, k, xSize, zSize, biomeSource, true);
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize) {
		return getBiomeGenAt(biomes, i, k, xSize, zSize, true);
	}

	private void setupGenLayers() {
		int i;
		long seed = worldObj.getSeed() + 1954L;
		chunkGenLayers = GOTGenLayerWorld.createWorld(gotDimension, worldObj.getWorldInfo().getTerrainType());
		worldLayers = new GOTGenLayer[chunkGenLayers.length];
		for (i = 0; i < worldLayers.length; ++i) {
			GOTGenLayer layer = chunkGenLayers[i];
			worldLayers[i] = new GOTGenLayerZoomVoronoi(10L, layer);
		}
		for (i = 0; i < worldLayers.length; ++i) {
			chunkGenLayers[i].initWorldGenSeed(seed);
			worldLayers[i].initWorldGenSeed(seed);
		}
	}
}