package got.common.world.structure.other;

import java.util.*;

import got.GOT;
import got.common.GOTConfig;
import got.common.util.CentredSquareArray;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.*;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class GOTVillageGen {
	public static Random villageRand = new Random();
	public static double SQRT2 = Math.sqrt(2.0);
	public GOTBiome villageBiome;
	public List<BiomeGenBase> spawnBiomes;
	public int gridScale;
	public int gridRandomDisplace;
	public float spawnChance;
	public int villageChunkRadius;
	public int fixedVillageChunkRadius;
	public List<LocationInfo> fixedLocations = new ArrayList<>();

	public GOTVillageGen(GOTBiome biome) {
		villageBiome = biome;
		spawnBiomes = new ArrayList<>();
		spawnBiomes.add(villageBiome);
	}

	public LocationInfo affix(GOTWaypoint... wps) {
		LocationInfo loc = null;
		for (GOTWaypoint wp : wps) {
			loc = new LocationInfo(wp.getXCoord() + wp.getAddX(), wp.getZCoord() + wp.getAddZ(), wp.getRotation(), wp.getCodeName()).setFixedLocation(wp);
			fixedLocations.add(loc);
		}
		return loc;
	}

	public boolean anyFixedVillagesAt(World world, int i, int k) {
		if (!GOTVillageGen.hasFixedSettlements(world)) {
			return false;
		}
		int checkRange = fixedVillageChunkRadius + 1;
		checkRange <<= 4;
		for (LocationInfo loc : fixedLocations) {
			int dx = Math.abs(loc.posX - i);
			int dz = Math.abs(loc.posZ - k);
			if (dx <= checkRange && dz <= checkRange) {
				return true;
			}
		}
		return false;
	}

	public AbstractInstance<?> createAndSetupVillageInstance(World world, int i, int k, Random random, LocationInfo location) {
		AbstractInstance<?> instance = createVillageInstance(world, i, k, random, location);
		instance.setupBaseAndVillageProperties();
		return instance;
	}

	public abstract AbstractInstance<?> createVillageInstance(World var1, int var2, int var3, Random var4, LocationInfo var5);

	public void generateCompleteVillageInstance(AbstractInstance<?> instance, World world, int i, int k) {
		instance.setupVillageStructures();
		int checkRange = Math.max(villageChunkRadius, fixedVillageChunkRadius);
		for (int i1 = -checkRange; i1 <= checkRange; ++i1) {
			for (int k1 = -checkRange; k1 <= checkRange; ++k1) {
				int i2 = i - 8 + i1 * 16;
				int k2 = k - 8 + k1 * 16;
				generateInstanceInChunk(instance, world, i2, k2);
			}
		}
	}

	public void generateInChunk(World world, int i, int k) {
		List<AbstractInstance<?>> villages = getNearbyVillagesAtPosition(world, i, k);
		for (AbstractInstance<?> instance : villages) {
			instance.setupVillageStructures();
			generateInstanceInChunk(instance, world, i, k);
		}
	}

	public void generateInstanceInChunk(AbstractInstance<?> instance, World world, int i, int k) {
		for (int i1 = i; i1 <= i + 15; ++i1) {
			for (int k1 = k; k1 <= k + 15; ++k1) {
				BiomeGenBase biome = world.getBiomeGenForCoords(i1, k1);
				Object[] pathData = getHeight_getPath_isSlab(instance, world, i1, k1, biome);
				GOTBezierType pathType = (GOTBezierType) pathData[1];
				if (pathType != null) {
					int j1 = (Integer) pathData[0];
					boolean isSlab = (Boolean) pathData[2];
					instance.setupWorldPositionSeed(i1, k1);
					GOTBezierType.BezierBlock roadblock = pathType.getBlock(instance.instanceRand, biome, true, isSlab);
					GOTBezierType.BezierBlock roadblockSolid = pathType.getBlock(instance.instanceRand, biome, false, false);
					world.setBlock(i1, j1, k1, roadblock.block, roadblock.meta, 2);
					world.setBlock(i1, j1 - 1, k1, roadblockSolid.block, roadblockSolid.meta, 2);
					Block above = world.getBlock(i1, j1 + 1, k1);
					if (!above.canBlockStay(world, i1, j1 + 1, k1)) {
						world.setBlock(i1, j1 + 1, k1, Blocks.air, 0, 3);
					}
				}
				instance.setupWorldPositionSeed(i1, k1);
				for (StructureInfo struct : instance.structures) {
					int[] coords = instance.getWorldCoords(struct.posX, struct.posZ);
					if (i1 != coords[0] || k1 != coords[1]) {
						continue;
					}
					int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
					int minHeight = 62;
					int terrainTypeMinHeight = world.provider.terrainType.getMinimumSpawnHeight(world);
					if (terrainTypeMinHeight < 62) {
						minHeight = terrainTypeMinHeight - 1;
					}
					if (j1 <= minHeight) {
						continue;
					}
					struct.structure.generate(world, instance.instanceRand, i1, j1, k1, instance.getStructureRotation(struct.rotation));
				}
			}
		}
	}

	public Object[] getHeight_getPath_isSlab(AbstractInstance<?> instance, World world, int i, int k, BiomeGenBase biome) {
		instance.setupWorldPositionSeed(i, k);
		int[] coords = instance.getRelativeCoords(i, k);
		int i1 = coords[0];
		int k1 = coords[1];
		GOTBezierType road = instance.getPath(instance.instanceRand, i1, k1);
		boolean isPath = false;
		boolean isSlab = false;
		int j1 = getTopTerrainBlock(world, i, k, biome, true);
		if (road != null && j1 > 0 && GOTStructureBase.isSurfaceStatic(world, i, j1, k)) {
			isPath = true;
			int slabRange = 1;
			CentredSquareArray<Integer> slabArray = new CentredSquareArray<>(slabRange);
			slabArray.fill(j1);
			for (int i2 = -slabRange; i2 <= slabRange; ++i2) {
				for (int k2 = -slabRange; k2 <= slabRange; ++k2) {
					int j2;
					int i3 = i + i2;
					int k3 = k + k2;
					if (i2 == 0 && k2 == 0 || (j2 = getTopTerrainBlock(world, i3, k3, biome, true)) <= 0 || j2 >= j1) {
						continue;
					}
					slabArray.set(i2, k2, j2);
				}
			}
			if (slabArray.get(-1, 0) < j1 || slabArray.get(1, 0) < j1 || slabArray.get(0, -1) < j1 || slabArray.get(0, 1) < j1) {
				isSlab = true;
			} else if (slabArray.get(-1, -1) < j1 || slabArray.get(1, -1) < j1 || slabArray.get(-1, 1) < j1 || slabArray.get(1, 1) < j1) {
				isSlab = true;
			}
			if (isSlab && world.getBlock(i, j1 + 1, k).isOpaqueCube()) {
				isSlab = false;
			}
		}
		Object[] ret = new Object[3];
		if (isPath) {
			ret[0] = j1;
			ret[1] = road;
			ret[2] = isSlab;
		} else {
			ret[0] = -1;
			ret[1] = null;
			ret[2] = false;
		}
		return ret;
	}

	public List<AbstractInstance<?>> getNearbyVillages(World world, int chunkX, int chunkZ) {
		ArrayList<AbstractInstance<?>> villages = new ArrayList<>();
		int checkRange = Math.max(villageChunkRadius, fixedVillageChunkRadius);
		for (int i = chunkX - checkRange; i <= chunkX + checkRange; ++i) {
			for (int k = chunkZ - checkRange; k <= chunkZ + checkRange; ++k) {
				int centreZ;
				int centreX;
				LocationInfo loc = isVillageCentre(world, i, k);
				if (!loc.isPresent()) {
					continue;
				}
				if (loc.isFixedLocation()) {
					centreX = loc.posX;
					centreZ = loc.posZ;
				} else {
					centreX = (i << 4) + 8;
					centreZ = (k << 4) + 8;
				}
				GOTVillageGen.seedVillageRand(world, centreX, centreZ);
				AbstractInstance<?> instance = createAndSetupVillageInstance(world, centreX, centreZ, villageRand, loc);
				villages.add(instance);
			}
		}
		return villages;
	}

	public List<AbstractInstance<?>> getNearbyVillagesAtPosition(World world, int i, int k) {
		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		return getNearbyVillages(world, chunkX, chunkZ);
	}

	public int getTopTerrainBlock(World world, int i, int k, BiomeGenBase biome, boolean acceptSlab) {
		int j = world.getTopSolidOrLiquidBlock(i, k) - 1;
		while (!world.getBlock(i, j + 1, k).getMaterial().isLiquid()) {
			Block block = world.getBlock(i, j, k);
			Block below = world.getBlock(i, j - 1, k);
			if (block.isOpaqueCube() || acceptSlab && block instanceof BlockSlab && below.isOpaqueCube()) {
				return j;
			}
			j--;
			if (j > 62) {
				continue;
			}
			break;
		}
		return -1;
	}

	public LocationInfo isVillageCentre(World world, int chunkX, int chunkZ) {
		GOTWorldChunkManager worldChunkMgr = (GOTWorldChunkManager) world.getWorldChunkManager();
		GOTVillagePositionCache cache = worldChunkMgr.getVillageCache(this);
		LocationInfo cacheLocation = cache.getLocationAt(chunkX, chunkZ);
		if (cacheLocation != null) {
			return cacheLocation;
		}
		if (GOTVillageGen.hasFixedSettlements(world)) {
			for (LocationInfo loc : fixedLocations) {
				int locChunkX = loc.posX >> 4;
				int locChunkZ = loc.posZ >> 4;
				if (chunkX == locChunkX && chunkZ == locChunkZ) {
					return cache.markResult(chunkX, chunkZ, loc);
				}
				int locCheckSize = Math.max(villageChunkRadius, fixedVillageChunkRadius);
				if (Math.abs(chunkX - locChunkX) > locCheckSize || Math.abs(chunkZ - locChunkZ) > locCheckSize) {
					continue;
				}
				return cache.markResult(chunkX, chunkZ, LocationInfo.NONE_HERE);
			}
		}
		int i2 = MathHelper.floor_double((double) chunkX / (double) gridScale);
		int k2 = MathHelper.floor_double((double) chunkZ / (double) gridScale);
		GOTVillageGen.seedVillageRand(world, i2, k2);
		i2 *= gridScale;
		k2 *= gridScale;
		i2 += MathHelper.getRandomIntegerInRange(villageRand, -gridRandomDisplace, gridRandomDisplace);
		if (chunkX == i2 && chunkZ == (k2 += MathHelper.getRandomIntegerInRange(villageRand, -gridRandomDisplace, gridRandomDisplace))) {
			int i1 = chunkX * 16 + 8;
			int k1 = chunkZ * 16 + 8;
			int villageRange = villageChunkRadius * 16;
			GOTBiomeVariant variant = worldChunkMgr.getBiomeVariantAt(i1, k1);
			if (variant == GOTBiomeVariant.STEPPE) {
				spawnChance = 0.0f;
			}
			if (villageRand.nextFloat() < spawnChance) {
				int diagRange = (int) Math.round((villageRange + 8) * SQRT2);
				boolean anythingNear;
				anythingNear = GOTRoads.isRoadNear(i1, k1, diagRange) >= 0.0f;
				if (!anythingNear && !(anythingNear = GOTMountains.mountainNear(i1, k1, diagRange))) {
					anythingNear = GOTFixedStructures.structureNear(world, i1, k1, diagRange);
				}
				if (!anythingNear) {
					GOTVillageGen.seedVillageRand(world, i1, k1);
					LocationInfo loc = LocationInfo.RANDOM_GEN_HERE;
					createAndSetupVillageInstance(world, i1, k1, villageRand, loc);
					if (worldChunkMgr.areBiomesViable(i1, k1, villageRange, spawnBiomes) && worldChunkMgr.areVariantsSuitableVillage(i1, k1, villageRange)) {
						return cache.markResult(chunkX, chunkZ, loc);
					}
				}
			}
		}
		return cache.markResult(chunkX, chunkZ, LocationInfo.NONE_HERE);
	}

	public static boolean hasFixedSettlements(World world) {
		if (GOTConfig.clearMap) {
			return false;
		}
		return world.getWorldInfo().getTerrainType() != GOT.worldTypeGOTClassic;
	}

	public static void seedVillageRand(World world, int i, int k) {
		long seed = i * 6890360793007L + k * 456879569029062L + world.getWorldInfo().getSeed() + 274893855L;
		villageRand.setSeed(seed);
	}

	public static abstract class AbstractInstance<V extends GOTVillageGen> {
		public GOTBiome instanceVillageBiome;
		public World theWorld;
		public Random instanceRand;
		public long instanceRandSeed;
		public int centreX;
		public int centreZ;
		public int rotationMode;
		public List<StructureInfo> structures = new ArrayList<>();
		public final LocationInfo locationInfo;

		public AbstractInstance(V village, World world, int i, int k, Random random, LocationInfo loc) {
			this.instanceVillageBiome = ((GOTVillageGen) village).villageBiome;
			this.theWorld = world;
			this.instanceRand = new Random();
			this.instanceRandSeed = random.nextLong();
			this.centreX = i;
			this.centreZ = k;
			this.locationInfo = loc;
		}

		public void addStructure(GOTStructureBase structure, int x, int z, int r) {
			this.addStructure(structure, x, z, r, false);
		}

		public void addStructure(GOTStructureBase structure, int x, int z, int r, boolean force) {
			structure.villageInstance = this;
			structure.restrictions = !force;
			if (force) {
				structure.shouldFindSurface = true;
			}
			this.structures.add(new StructureInfo(structure, x, z, r));
		}

		public abstract void addVillageStructures(Random var1);

		public abstract GOTBezierType getPath(Random var1, int var2, int var3);

		public int[] getRelativeCoords(int xWorld, int zWorld) {
			int xRel = 0;
			int zRel = 0;
			switch (this.rotationMode) {
			case 0: {
				xRel = this.centreX - xWorld;
				zRel = this.centreZ - zWorld;
				break;
			}
			case 1: {
				xRel = this.centreZ - zWorld;
				zRel = xWorld - this.centreX;
				break;
			}
			case 2: {
				xRel = xWorld - this.centreX;
				zRel = zWorld - this.centreZ;
				break;
			}
			case 3: {
				xRel = zWorld - this.centreZ;
				zRel = this.centreX - xWorld;
			}
			}
			return new int[] { xRel, zRel };
		}

		public int getStructureRotation(int r) {
			return (r + this.rotationMode + 2) % 4;
		}

		public int[] getWorldCoords(int xRel, int zRel) {
			int xWorld = this.centreX;
			int zWorld = this.centreZ;
			switch (this.rotationMode) {
			case 0: {
				xWorld = this.centreX - xRel;
				zWorld = this.centreZ - zRel;
				break;
			}
			case 1: {
				xWorld = this.centreX + zRel;
				zWorld = this.centreZ - xRel;
				break;
			}
			case 2: {
				xWorld = this.centreX + xRel;
				zWorld = this.centreZ + zRel;
				break;
			}
			case 3: {
				xWorld = this.centreX - zRel;
				zWorld = this.centreZ + xRel;
			}
			}
			return new int[] { xWorld, zWorld };
		}

		public abstract boolean isVillageSpecificSurface(World var1, int var2, int var3, int var4);

		public void setRotation(int i) {
			this.rotationMode = i;
		}

		public void setupBaseAndVillageProperties() {
			this.setupVillageSeed();
			this.rotationMode = this.locationInfo.isFixedLocation() ? (this.locationInfo.rotation + 2) % 4 : this.instanceRand.nextInt(4);
			this.setupVillageProperties(this.instanceRand);
		}

		public abstract void setupVillageProperties(Random var1);

		public void setupVillageSeed() {
			long seed = this.centreX * 580682095692076767L + this.centreZ * 12789948968296726L + this.theWorld.getWorldInfo().getSeed() + 49920968939865L;
			this.instanceRand.setSeed(seed += this.instanceRandSeed);
		}

		public void setupVillageStructures() {
			this.setupVillageSeed();
			this.structures.clear();
			this.addVillageStructures(this.instanceRand);
		}

		public void setupWorldPositionSeed(int i, int k) {
			this.setupVillageSeed();
			int[] coords = this.getRelativeCoords(i, k);
			int i1 = coords[0];
			int k1 = coords[1];
			long seed1 = this.instanceRand.nextLong();
			long seed2 = this.instanceRand.nextLong();
			long seed = i1 * seed1 + k1 * seed2 ^ this.theWorld.getWorldInfo().getSeed();
			this.instanceRand.setSeed(seed);
		}
	}

	public static class StructureInfo {
		public GOTStructureBase structure;
		public int posX;
		public int posZ;
		public int rotation;

		public StructureInfo(GOTStructureBase s, int x, int z, int r) {
			structure = s;
			posX = x;
			posZ = z;
			rotation = r;
		}
	}

}