package got.common.world.structure.other;

import got.GOT;
import got.common.GOTConfig;
import got.common.util.GOTCentredSquareArray;
import got.common.util.GOTCrashHandler;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class GOTStructureBaseSettlement {
	public static Random settlementRand = new Random();
	public static double SQRT2 = 1.4142135623730951;
	public GOTBiome settlementBiome;
	public List<BiomeGenBase> spawnBiomes;
	public float spawnChance;
	public int settlementChunkRadius;
	public int fixedSettlementChunkRadius;
	public Collection<LocationInfo> fixedLocations = new ArrayList<>();
	public Collection<GOTFixer.SpawnInfo> spawnInfos = new ArrayList<>();

	protected GOTStructureBaseSettlement(GOTBiome biome) {
		settlementBiome = biome;
		spawnBiomes = new ArrayList<>();
		spawnBiomes.add(settlementBiome);
	}

	public static boolean hasFixedSettlements(World world) {
		boolean disableMap = world.getWorldInfo().getTerrainType() == GOT.worldTypeGOTClassic;
		boolean disableLocations = world.getWorldInfo().getTerrainType() == GOT.worldTypeGOTEmpty;
		return !disableMap && !disableLocations;
	}

	public static void seedSettlementRand(World world, int i, int k) {
		long seed = i * 6890360793007L + k * 456879569029062L + world.getWorldInfo().getSeed() + 274893855L;
		settlementRand.setSeed(seed);
	}

	public void affix(GOTAbstractWaypoint... wps) {
		for (GOTAbstractWaypoint wp : wps) {
			LocationInfo loc = new LocationInfo(wp.getCoordX(), wp.getCoordZ(), wp.getRotation(), wp.getCodeName()).setFixedLocation(wp.getInstance());
			fixedLocations.add(loc);
		}
	}

	public boolean anyFixedSettlementsAt(World world, int i, int k) {
		if (!hasFixedSettlements(world)) {
			return false;
		}
		int checkRange = fixedSettlementChunkRadius + 1;
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

	public AbstractInstance<?> createAndSetupSettlementInstance(World world, int i, int k, Random random, LocationInfo location) {
		AbstractInstance<?> instance = createSettlementInstance(world, i, k, random, location, () -> addLegendaryNPCs(world), spawnInfos);
		instance.setupBaseAndSettlementProperties();
		return instance;
	}

	public abstract AbstractInstance<?> createSettlementInstance(World var1, int var2, int var3, Random var4, LocationInfo var5, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos);

	public void generateCompleteSettlementInstance(AbstractInstance<?> instance, World world, int i, int k) {
		instance.setupSettlementStructures();
		int checkRange = Math.max(settlementChunkRadius, fixedSettlementChunkRadius);
		for (int i1 = -checkRange; i1 <= checkRange; ++i1) {
			for (int k1 = -checkRange; k1 <= checkRange; ++k1) {
				int i2 = i - 8 + i1 * 16;
				int k2 = k - 8 + k1 * 16;
				generateInstanceInChunk(instance, world, i2, k2);
			}
		}
	}

	public void generateInChunk(World world, int i, int k) {
		List<AbstractInstance<?>> settlements = getNearbySettlementsAtPosition(world, i, k);
		for (AbstractInstance<?> instance : settlements) {
			instance.setupSettlementStructures();
			generateInstanceInChunk(instance, world, i, k);
		}
	}

	public void generateInstanceInChunk(AbstractInstance<?> instance, World world, int i, int k) {
		for (int i1 = i; i1 <= i + 15; ++i1) {
			for (int k1 = k; k1 <= k + 15; ++k1) {
				BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(world, i1, k1);
				Object[] pathData = getHeight_getPath_isSlab(instance, world, i1, k1, biome);
				GOTBezierType pathType = (GOTBezierType) pathData[1];
				if (pathType != null) {
					int j1 = (Integer) pathData[0];
					boolean isSlab = (Boolean) pathData[2];
					instance.setupWorldPositionSeed(i1, k1);
					GOTBezierType.BezierBlock bezierblock = pathType.getBlock(instance.instanceRand, biome, true, isSlab);
					GOTBezierType.BezierBlock bezierblockSolid = pathType.getBlock(instance.instanceRand, biome, false, false);
					world.setBlock(i1, j1, k1, bezierblock.getBlock(), bezierblock.getMeta(), 2);
					world.setBlock(i1, j1 - 1, k1, bezierblockSolid.getBlock(), bezierblockSolid.getMeta(), 2);
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
		GOTBezierType bezier = instance.getPath(instance.instanceRand, i1, k1);
		boolean isPath = false;
		boolean isSlab = false;
		int j1 = getTopTerrainBlock(world, i, k, biome, true);
		if (bezier != null && j1 > 0 && GOTStructureBase.isSurfaceStatic(world, i, j1, k)) {
			isPath = true;
			int slabRange = 1;
			GOTCentredSquareArray<Integer> slabArray = new GOTCentredSquareArray<>(slabRange);
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
			if (slabArray.get(-1, 0) < j1 || slabArray.get(1, 0) < j1 || slabArray.get(0, -1) < j1 || slabArray.get(0, 1) < j1 || slabArray.get(-1, -1) < j1 || slabArray.get(1, -1) < j1 || slabArray.get(-1, 1) < j1 || slabArray.get(1, 1) < j1) {
				isSlab = true;
			}
			if (isSlab && world.getBlock(i, j1 + 1, k).isOpaqueCube()) {
				isSlab = false;
			}
		}
		Object[] ret = new Object[3];
		if (isPath) {
			ret[0] = j1;
			ret[1] = bezier;
			ret[2] = isSlab;
		} else {
			ret[0] = -1;
			ret[1] = null;
			ret[2] = false;
		}
		return ret;
	}

	public List<AbstractInstance<?>> getNearbySettlements(World world, int chunkX, int chunkZ) {
		List<AbstractInstance<?>> settlements = new ArrayList<>();
		int checkRange = Math.max(settlementChunkRadius, fixedSettlementChunkRadius);
		for (int i = chunkX - checkRange; i <= chunkX + checkRange; ++i) {
			for (int k = chunkZ - checkRange; k <= chunkZ + checkRange; ++k) {
				int centreZ;
				int centreX;
				LocationInfo loc = isSettlementCentre(world, i, k);
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
				seedSettlementRand(world, centreX, centreZ);
				AbstractInstance<?> instance = createAndSetupSettlementInstance(world, centreX, centreZ, settlementRand, loc);
				settlements.add(instance);
			}
		}
		return settlements;
	}

	public List<AbstractInstance<?>> getNearbySettlementsAtPosition(World world, int i, int k) {
		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		return getNearbySettlements(world, chunkX, chunkZ);
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

	public LocationInfo isSettlementCentre(World world, int chunkX, int chunkZ) {
		GOTWorldChunkManager worldChunkMgr = (GOTWorldChunkManager) world.getWorldChunkManager();
		GOTSettlementPositionCache cache = worldChunkMgr.getSettlementCache(this);
		LocationInfo cacheLocation = cache.getLocationAt(chunkX, chunkZ);
		if (cacheLocation != null) {
			return cacheLocation;
		}
		if (hasFixedSettlements(world)) {
			for (LocationInfo loc : fixedLocations) {
				int locChunkX = loc.posX >> 4;
				int locChunkZ = loc.posZ >> 4;
				if (chunkX == locChunkX && chunkZ == locChunkZ) {
					return cache.markResult(chunkX, chunkZ, loc);
				}
				int locCheckSize = Math.max(settlementChunkRadius, fixedSettlementChunkRadius);
				if (Math.abs(chunkX - locChunkX) > locCheckSize || Math.abs(chunkZ - locChunkZ) > locCheckSize) {
					continue;
				}
				return cache.markResult(chunkX, chunkZ, LocationInfo.NONE_HERE);
			}
		}
		int gridScale = GOTConfig.gridScale;
		int i2 = MathHelper.floor_double((double) chunkX / gridScale);
		int k2 = MathHelper.floor_double((double) chunkZ / gridScale);
		seedSettlementRand(world, i2, k2);
		i2 *= gridScale;
		k2 *= gridScale;
		i2 += MathHelper.getRandomIntegerInRange(settlementRand, -1, 1);
		if (chunkX == i2 && chunkZ == k2 + MathHelper.getRandomIntegerInRange(settlementRand, -1, 1)) {
			int i1 = chunkX * 16 + 8;
			int k1 = chunkZ * 16 + 8;
			int settlementRange = settlementChunkRadius * 16;
			GOTBiomeVariant variant = worldChunkMgr.getBiomeVariantAt(i1, k1);
			if (variant == GOTBiomeVariant.STEPPE) {
				spawnChance = 0.0f;
			}
			if (settlementRand.nextFloat() < spawnChance) {
				int diagRange = (int) Math.round((settlementRange + 8) * SQRT2);
				boolean isRoadNear = GOTBeziers.isBezierNear(i1, k1, diagRange, GOTBeziers.Type.ROAD) >= 0.0f;
				boolean isWallNear = GOTBeziers.isBezierNear(i1, k1, diagRange, GOTBeziers.Type.LINKER) >= 0.0f;
				boolean isLinkerNear = GOTBeziers.isBezierNear(i1, k1, diagRange, GOTBeziers.Type.LINKER) >= 0.0f;
				boolean anythingNear = isRoadNear || isWallNear || isLinkerNear;
				if (!anythingNear) {
					anythingNear = GOTMountains.mountainNear(i1, k1, diagRange);
					if (!anythingNear) {
						anythingNear = GOTFixedStructures.structureNear(i1, k1, diagRange);
					}
				}
				if (!anythingNear) {
					seedSettlementRand(world, i1, k1);
					LocationInfo loc = LocationInfo.RANDOM_GEN_HERE;
					createAndSetupSettlementInstance(world, i1, k1, settlementRand, loc);
					if (worldChunkMgr.areBiomesViable(i1, k1, settlementRange, spawnBiomes) && worldChunkMgr.areVariantsSuitableSettlement(i1, k1, settlementRange)) {
						return cache.markResult(chunkX, chunkZ, loc);
					}
				}
			}
		}
		return cache.markResult(chunkX, chunkZ, LocationInfo.NONE_HERE);
	}

	public void addLegendaryNPCs(World world) {
	}

	public GOTStructureBase getSpecialStructure() {
		return null;
	}

	public abstract static class AbstractInstance<V extends GOTStructureBaseSettlement> {
		public final LocationInfo locationInfo;
		public GOTBiome instanceSettlementBiome;
		public World theWorld;
		public Random instanceRand;
		public long instanceRandSeed;
		public int centreX;
		public int centreZ;
		public int rotationMode;
		public Collection<StructureInfo> structures = new ArrayList<>();
		public Runnable filler;
		public Collection<GOTFixer.SpawnInfo> spawnInfos;

		protected AbstractInstance(V settlement, World world, int i, int k, Random random, LocationInfo loc, Runnable f, Collection<GOTFixer.SpawnInfo> spawn) {
			instanceSettlementBiome = settlement.settlementBiome;
			theWorld = world;
			instanceRand = new Random();
			instanceRandSeed = random.nextLong();
			centreX = i;
			centreZ = k;
			locationInfo = loc;
			filler = f;
			spawnInfos = spawn;
		}

		public void addSettlementStructures(Random var1) {
			filler.run();
			spawnInfos.forEach((info) -> addStructure(new GOTStructureBase(false) {
				@Override
				public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
					setOriginAndRotation(world, i, j, k, rotation, 0);
					spawnLegendaryNPC(info.getNPC(), world, 0, 2, 0);
					return true;
				}
			}, info.getI(), info.getK(), 0, true));
			spawnInfos.clear();
		}

		public void addStructure(GOTStructureBase structure, int x, int z, int r) {
			addStructure(structure, x, z, r, false);
		}

		public void addStructure(GOTStructureBase structure, int x, int z, int r, boolean force) {
			structure.settlementInstance = this;
			structure.restrictions = !force;
			if (force) {
				structure.shouldFindSurface = true;
			}
			structures.add(new StructureInfo(structure, x, z, r));
		}

		public abstract GOTBezierType getPath(Random var1, int var2, int var3);

		public int[] getRelativeCoords(int xWorld, int zWorld) {
			int xRel = 0;
			int zRel = 0;
			switch (rotationMode) {
				case 0: {
					xRel = centreX - xWorld;
					zRel = centreZ - zWorld;
					break;
				}
				case 1: {
					xRel = centreZ - zWorld;
					zRel = xWorld - centreX;
					break;
				}
				case 2: {
					xRel = xWorld - centreX;
					zRel = zWorld - centreZ;
					break;
				}
				case 3: {
					xRel = zWorld - centreZ;
					zRel = centreX - xWorld;
				}
			}
			return new int[]{xRel, zRel};
		}

		public int getStructureRotation(int r) {
			return (r + rotationMode + 2) % 4;
		}

		public int[] getWorldCoords(int xRel, int zRel) {
			int xWorld = centreX;
			int zWorld = centreZ;
			switch (rotationMode) {
				case 0: {
					xWorld = centreX - xRel;
					zWorld = centreZ - zRel;
					break;
				}
				case 1: {
					xWorld = centreX + zRel;
					zWorld = centreZ - xRel;
					break;
				}
				case 2: {
					xWorld = centreX + xRel;
					zWorld = centreZ + zRel;
					break;
				}
				case 3: {
					xWorld = centreX - zRel;
					zWorld = centreZ + xRel;
				}
			}
			return new int[]{xWorld, zWorld};
		}

		public abstract boolean isSettlementSpecificSurface(World var1, int var2, int var3, int var4);

		public void setRotation(int i) {
			rotationMode = i;
		}

		public void setupBaseAndSettlementProperties() {
			setupSettlementSeed();
			rotationMode = locationInfo.isFixedLocation() ? (locationInfo.rotation + 2) % 4 : instanceRand.nextInt(4);
			setupSettlementProperties(instanceRand);
		}

		public abstract void setupSettlementProperties(Random var1);

		public void setupSettlementSeed() {
			long seed = centreX * 580682095692076767L + centreZ * 12789948968296726L + theWorld.getWorldInfo().getSeed() + 49920968939865L;
			instanceRand.setSeed(seed + instanceRandSeed);
		}

		public void setupSettlementStructures() {
			setupSettlementSeed();
			structures.clear();
			addSettlementStructures(instanceRand);
		}

		public void setupWorldPositionSeed(int i, int k) {
			setupSettlementSeed();
			int[] coords = getRelativeCoords(i, k);
			int i1 = coords[0];
			int k1 = coords[1];
			long seed1 = instanceRand.nextLong();
			long seed2 = instanceRand.nextLong();
			long seed = i1 * seed1 + k1 * seed2 ^ theWorld.getWorldInfo().getSeed();
			instanceRand.setSeed(seed);
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