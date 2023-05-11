package got.common.world;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.GOTDimension;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.sothoryos.GOTBiomeSothoryosJungle;
import got.common.world.biome.sothoryos.GOTBiomeSothoryosMangrove;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.variant.GOTBiomeVariantList;
import got.common.world.biome.variant.GOTBiomeVariantStorage;
import got.common.world.genlayer.*;
import got.common.world.map.GOTFixedStructures;
import got.common.world.structure.other.GOTVillageGen;
import got.common.world.structure.other.GOTVillagePositionCache;
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
	public static int LAYER_BIOME;
	public static int LAYER_VARIANTS_LARGE = 1;
	public static int LAYER_VARIANTS_SMALL = 2;
	public static int LAYER_VARIANTS_LAKES = 3;
	public static int LAYER_VARIANTS_RIVERS = 4;
	public World worldObj;
	public GOTDimension gotDimension;
	public GOTGenLayer[] chunkGenLayers;
	public GOTGenLayer[] worldLayers;
	public BiomeCache biomeCache;
	public Map<GOTVillageGen, GOTVillagePositionCache> villageCacheMap = new HashMap<>();
	public Map<MapGenStructure, GOTVillagePositionCache> structureCacheMap = new HashMap<>();

	public GOTWorldChunkManager(World world, GOTDimension dim) {
		worldObj = world;
		biomeCache = new BiomeCache(this);
		gotDimension = dim;
		setupGenLayers();
	}

	@Override
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
			GOTBiome biome = gotDimension.biomeList[ints[l]];
			if (list.contains(biome)) {
				continue;
			}
			return false;
		}
		return true;
	}

	public boolean areVariantsSuitableVillage(int i, int k, int range) {
		int i1 = i - range >> 2;
		int k1 = k - range >> 2;
		int i2 = i + range >> 2;
		int k2 = k + range >> 2;
		int i3 = i2 - i1 + 1;
		int k3 = k2 - k1 + 1;
		BiomeGenBase[] biomes = getBiomesForGeneration(null, i1, k1, i3, k3);
		for (GOTBiomeVariant v : getVariantsChunkGen(null, i1, k1, i3, k3, biomes)) {
			if (v.hillFactor > 1.0f || v.treeFactor > 1.0f || v.disableVillages || v.absoluteHeight && v.absoluteHeightLevel < 0.0f) {
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
			GOTBiome biome = gotDimension.biomeList[ints[l]];
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
		GOTIntCache.get(worldObj).resetIntCache();
		if (biomes == null || biomes.length < xSize * zSize) {
			biomes = new BiomeGenBase[xSize * zSize];
		}
		if (useCache && xSize == 16 && zSize == 16 && (i & 0xF) == 0 && (k & 0xF) == 0) {
			BiomeGenBase[] cachedBiomes = biomeCache.getCachedBiomes(i, k);
			System.arraycopy(cachedBiomes, 0, biomes, 0, xSize * zSize);
			return biomes;
		}
		int[] ints = worldLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			biomes[l] = gotDimension.biomeList[biomeID];
		}
		return biomes;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int i, int k) {
		return biomeCache.getBiomeGenAt(i, k);
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize) {
		GOTIntCache.get(worldObj).resetIntCache();
		if (biomes == null || biomes.length < xSize * zSize) {
			biomes = new BiomeGenBase[xSize * zSize];
		}
		int[] ints = chunkGenLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			biomes[l] = gotDimension.biomeList[biomeID];
		}
		return biomes;
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

	public GOTBiomeVariant[] getBiomeVariantsFromLayers(GOTBiomeVariant[] variants, int i, int k, int xSize, int zSize, BiomeGenBase[] biomeSource, boolean isChunkGeneration) {
		GOTIntCache.get(worldObj).resetIntCache();
		BiomeGenBase[] biomes = new BiomeGenBase[xSize * zSize];
		if (biomeSource != null) {
			biomes = biomeSource;
		} else {
			for (int k1 = 0; k1 < zSize; ++k1) {
				for (int i1 = 0; i1 < xSize; ++i1) {
					int index = i1 + k1 * xSize;
					biomes[index] = worldObj.getBiomeGenForCoords(i + i1, k + k1);
				}
			}
		}
		if (variants == null || variants.length < xSize * zSize) {
			variants = new GOTBiomeVariant[xSize * zSize];
		}
		GOTGenLayer[] sourceGenLayers = isChunkGeneration ? chunkGenLayers : worldLayers;
		GOTGenLayer variantsLarge = sourceGenLayers[LAYER_VARIANTS_LARGE];
		GOTGenLayer variantsSmall = sourceGenLayers[LAYER_VARIANTS_SMALL];
		GOTGenLayer variantsLakes = sourceGenLayers[LAYER_VARIANTS_LAKES];
		GOTGenLayer variantsRivers = sourceGenLayers[LAYER_VARIANTS_RIVERS];
		int[] variantsLargeInts = variantsLarge.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsSmallInts = variantsSmall.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsLakesInts = variantsLakes.getInts(worldObj, i, k, xSize, zSize);
		int[] variantsRiversInts = variantsRivers.getInts(worldObj, i, k, xSize, zSize);
		for (int k1 = 0; k1 < zSize; ++k1) {
			for (int i1 = 0; i1 < xSize; ++i1) {
				int riverCode;
				int index = i1 + k1 * xSize;
				GOTBiome biome = (GOTBiome) biomes[index];
				GOTBiomeVariant variant = GOTBiomeVariant.STANDARD;
				int xPos = i + i1;
				int zPos = k + k1;
				if (isChunkGeneration) {
					xPos <<= 2;
					zPos <<= 2;
				}
				boolean[] flags = GOTFixedStructures._mountainNear_structureNear(worldObj, xPos, zPos);
				boolean mountainNear = flags[0];
				boolean structureNear = flags[1];
				boolean fixedVillageNear = biome.decorator.anyFixedVillagesAt(worldObj, xPos, zPos);
				if (fixedVillageNear) {
					variant = GOTBiomeVariant.STEPPE;
				} else {
					if (!mountainNear) {
						float variantChance = biome.getVariantChance();
						if (variantChance > 0.0f) {
							for (int pass = 0; pass <= 1; ++pass) {
								GOTBiomeVariantList variantList;
								variantList = pass == 0 ? biome.getBiomeVariantsLarge() : biome.getBiomeVariantsSmall();
								if (variantList.isEmpty()) {
									continue;
								}
								int[] sourceInts = pass == 0 ? variantsLargeInts : variantsSmallInts;
								int variantCode = sourceInts[index];
								float variantF = (float) variantCode / (float) GOTGenLayerBiomeVariants.RANDOM_MAX;
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
				variants[index] = variant;
			}
		}
		return variants;
	}

	@Override
	public float[] getRainfall(float[] rainfall, int i, int k, int xSize, int zSize) {
		GOTIntCache.get(worldObj).resetIntCache();
		if (rainfall == null || rainfall.length < xSize * zSize) {
			rainfall = new float[xSize * zSize];
		}
		int[] ints = worldLayers[LAYER_BIOME].getInts(worldObj, i, k, xSize, zSize);
		for (int l = 0; l < xSize * zSize; ++l) {
			int biomeID = ints[l];
			float f = gotDimension.biomeList[biomeID].getIntRainfall() / 65536.0f;
			if (f > 1.0f) {
				f = 1.0f;
			}
			rainfall[l] = f;
		}
		return rainfall;
	}

	public GOTVillagePositionCache getStructureCache(MapGenStructure structure) {
		return structureCacheMap.computeIfAbsent(structure, k -> new GOTVillagePositionCache());
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public float getTemperatureAtHeight(float f, int height) {
		if (worldObj.isRemote && GOT.isNewYear()) {
			return 0.0f;
		}
		return f;
	}

	public GOTBiomeVariant[] getVariantsChunkGen(GOTBiomeVariant[] variants, int i, int k, int xSize, int zSize, BiomeGenBase[] biomeSource) {
		return getBiomeVariantsFromLayers(variants, i, k, xSize, zSize, biomeSource, true);
	}

	public GOTVillagePositionCache getVillageCache(GOTVillageGen village) {
		return villageCacheMap.computeIfAbsent(village, k -> new GOTVillagePositionCache());
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomes, int i, int k, int xSize, int zSize) {
		return this.getBiomeGenAt(biomes, i, k, xSize, zSize, true);
	}

	public void setupGenLayers() {
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
