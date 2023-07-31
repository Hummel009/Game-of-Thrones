package got.common.world.biome;

import got.GOT;
import got.common.database.GOTBlocks;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.*;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTBeziers;
import got.common.world.map.GOTFixedStructures;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class GOTBiomeDecorator {
	public World worldObj;
	public Random rand;
	public int chunkX;
	public int chunkZ;
	public GOTBiome biome;
	public List<OreGenerant> biomeSoils = new ArrayList<>();
	public Collection<OreGenerant> biomeOres = new ArrayList<>();
	public Collection<OreGenerant> biomeGems = new ArrayList<>();
	public float biomeOreFactor = 1.0f;
	public float biomeGemFactor = 0.5f;
	public WorldGenerator clayGen = new GOTWorldGenSand(Blocks.clay, 5, 1);
	public WorldGenerator sandGen = new GOTWorldGenSand(Blocks.sand, 7, 2);
	public WorldGenerator whiteSandGen = new GOTWorldGenSand(GOTBlocks.whiteSand, 7, 2);
	public WorldGenerator quagmireGen = new GOTWorldGenSand(GOTBlocks.quagmire, 7, 2);
	public WorldGenerator surfaceGravelGen = new GOTWorldGenSurfaceGravel();
	public WorldGenerator flowerGen = new GOTWorldGenBiomeFlowers();
	public WorldGenerator logGen = new GOTWorldGenLogs();
	public WorldGenerator caneGen = new WorldGenReed();
	public WorldGenerator reedGen = new GOTWorldGenReeds(GOTBlocks.reeds);
	public WorldGenerator dryReedGen = new GOTWorldGenReeds(GOTBlocks.driedReeds);
	public WorldGenerator cornGen = new GOTWorldGenCorn();
	public WorldGenerator waterlilyGen = new WorldGenWaterlily();
	public WorldGenerator stalactiteGen = new GOTWorldGenStalactites();
	public WorldGenerator cactusGen = new WorldGenCactus();
	public int sandPerChunk = 4;
	public int clayPerChunk = 3;
	public int quagmirePerChunk;
	public int treesPerChunk;
	public int logsPerChunk;
	public int flowersPerChunk = 2;
	public int doubleFlowersPerChunk;
	public int grassPerChunk = 1;
	public int doubleGrassPerChunk;
	public int deadBushPerChunk;
	public int waterlilyPerChunk;
	public int canePerChunk;
	public int reedPerChunk = 1;
	public float dryReedChance = 0.1f;
	public int cornPerChunk;
	public int cactiPerChunk;
	public boolean generateAgriculture;
	public boolean whiteSand;
	public Collection<GOTTreeType.WeightedTreeType> treeTypes = new ArrayList<>();
	public Random structureRand = new Random();
	public Collection<Structure> structures = new ArrayList<>();
	public Collection<GOTStructureBaseSettlement> settlements = new ArrayList<>();
	public Collection<GOTStructureBaseSettlement> fixedSettlements = new ArrayList<>();

	public GOTBiomeDecorator(GOTBiome gotbiome) {
		biome = gotbiome;
		addDefaultOres();
	}

	public void addDefaultOres() {
		addSoil(new WorldGenMinable(Blocks.dirt, 32), 40.0f, 0, 256);
		addSoil(new WorldGenMinable(Blocks.gravel, 32), 20.0f, 0, 256);
		addSoil(new WorldGenMinable(GOTBlocks.rock, 0, 12, Blocks.stone), 30.0f, 0, 10);
		addSoil(new WorldGenMinable(GOTBlocks.rock, 1, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTBlocks.rock, 2, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTBlocks.rock, 3, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTBlocks.rock, 4, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTBlocks.rock, 5, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTBlocks.rock, 6, 12, Blocks.stone), 30.0f, 0, 64);
		addOre(new WorldGenMinable(GOTBlocks.oreCopper, 8), 15.0f, 0, 128);
		addOre(new WorldGenMinable(GOTBlocks.oreTin, 8), 15.0f, 0, 128);
		addOre(new WorldGenMinable(GOTBlocks.oreSilver, 5), 8.0f, 0, 32);
		addOre(new WorldGenMinable(Blocks.gold_ore, 4), 2.0f, 0, 32);
		addOre(new WorldGenMinable(Blocks.coal_ore, 16), 40.0f, 0, 128);
		addOre(new WorldGenMinable(Blocks.iron_ore, 8), 20.0f, 0, 64);
		addOre(new WorldGenMinable(GOTBlocks.oreSulfur, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(GOTBlocks.oreSaltpeter, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(GOTBlocks.oreSalt, 12), 2.0f, 0, 64);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 0, 6, Blocks.stone), 2.0f, 0, 64);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 1, 6, Blocks.stone), 2.0f, 0, 64);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 2, 4, Blocks.stone), 1.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 3, 4, Blocks.stone), 1.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 4, 5, Blocks.stone), 1.5f, 0, 48);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 5, 4, Blocks.stone), 0.5f, 0, 16);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 6, 5, Blocks.stone), 1.5f, 0, 48);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 7, 4, Blocks.stone), 0.75f, 0, 24);
	}

	public void addFixedSettlement(GOTStructureBaseSettlement settlement) {
		settlements.add(settlement);
		fixedSettlements.add(settlement);
	}

	public void addGem(WorldGenMinable gen, float f, int min, int max) {
		biomeGems.add(new OreGenerant(gen, f, min, max));
	}

	public void addOre(WorldGenMinable gen, float f, int min, int max) {
		biomeOres.add(new OreGenerant(gen, f, min, max));
	}

	public void addSettlement(GOTStructureBaseSettlement settlement) {
		settlements.add(settlement);
	}

	public void addSoil(WorldGenMinable gen, float f, int min, int max) {
		biomeSoils.add(new OreGenerant(gen, f, min, max));
	}

	public void addStructure(WorldGenerator structure, int chunkChance) {
		structures.add(new Structure(structure, chunkChance));
	}

	public void addTree(GOTTreeType type, int weight) {
		treeTypes.add(new GOTTreeType.WeightedTreeType(type, weight));
	}

	public boolean anyFixedSettlementsAt(World world, int i, int k) {
		for (GOTStructureBaseSettlement settlement : fixedSettlements) {
			if (settlement.anyFixedSettlementsAt(world, i, k)) {
				return true;
			}
		}
		return false;
	}

	public void clearSettlements() {
		settlements.clear();
		settlements.addAll(fixedSettlements);
	}

	public void clearStructures() {
		structures.clear();
	}

	public void clearTrees() {
		treeTypes.clear();
	}

	public void decorate() {
		int k;
		int k2;
		int i;
		int l;
		int k3;
		int k4;
		int l2;
		int j2;
		int i2;
		int l3;
		int k5;
		int l4;
		int i3;
		int l5;
		int j3;
		int j4;
		int k6;
		int i4;
		int i5;
		int l7;
		int j5;
		int k7;
		GOTBiomeVariant biomeVariant = ((GOTWorldChunkManager) worldObj.getWorldChunkManager()).getBiomeVariantAt(chunkX + 8, chunkZ + 8);
		generateOres();
		biomeVariant.decorateVariant(worldObj, rand, chunkX, chunkZ, biome);
		for (l2 = 0; l2 < 3; ++l2) {
			i = chunkX + rand.nextInt(16) + 8;
			j4 = rand.nextInt(60);
			int k8 = chunkZ + rand.nextInt(16) + 8;
			stalactiteGen.generate(worldObj, rand, i, j4, k8);
		}
		for (l2 = 0; l2 < quagmirePerChunk; ++l2) {
			i = chunkX + rand.nextInt(16) + 8;
			k = chunkZ + rand.nextInt(16) + 8;
			quagmireGen.generate(worldObj, rand, i, worldObj.getTopSolidOrLiquidBlock(i, k), k);
		}
		for (l2 = 0; l2 < sandPerChunk; ++l2) {
			i = chunkX + rand.nextInt(16) + 8;
			k = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator biomeSandGenerator = sandGen;
			if (whiteSand) {
				biomeSandGenerator = whiteSandGen;
			}
			biomeSandGenerator.generate(worldObj, rand, i, worldObj.getTopSolidOrLiquidBlock(i, k), k);
		}
		for (l2 = 0; l2 < clayPerChunk; ++l2) {
			i = chunkX + rand.nextInt(16) + 8;
			k = chunkZ + rand.nextInt(16) + 8;
			clayGen.generate(worldObj, rand, i, worldObj.getTopSolidOrLiquidBlock(i, k), k);
		}
		if (rand.nextInt(60) == 0) {
			i4 = chunkX + rand.nextInt(16) + 8;
			k6 = chunkZ + rand.nextInt(16) + 8;
			surfaceGravelGen.generate(worldObj, rand, i4, 0, k6);
		}
		if (!biomeVariant.disableStructures && Math.abs(chunkX) > 32 && Math.abs(chunkZ) > 32) {
			long seed = chunkX * 1879267L ^ chunkZ * 67209689L;
			seed = seed * seed * 5829687L + seed * 2876L;
			structureRand.setSeed(seed);
			boolean roadNear = GOTBeziers.isBezierNear(chunkX + 8, chunkZ + 8, 16, GOTBeziers.Type.ROAD) >= 0.0f;
			boolean wallNear = GOTBeziers.isBezierNear(chunkX + 8, chunkZ + 8, 16, GOTBeziers.Type.WALL) >= 0.0f;
			boolean linkerNear = GOTBeziers.isBezierNear(chunkX + 8, chunkZ + 8, 16, GOTBeziers.Type.LINKER) >= 0.0f;
			if ((!roadNear && !wallNear && !linkerNear) && !anyFixedSettlementsAt(worldObj, chunkX, chunkZ)) {
				for (Structure randomstructure : structures) {
					if (structureRand.nextInt(randomstructure.chunkChance) == 0) {
						int i6 = chunkX + rand.nextInt(16) + 8;
						k2 = chunkZ + rand.nextInt(16) + 8;
						j5 = worldObj.getTopSolidOrLiquidBlock(i6, k2);
						randomstructure.structureGen.generate(worldObj, rand, i6, j5, k2);
					}
				}
			}
			for (GOTStructureBaseSettlement settlement : settlements) {
				settlement.generateInChunk(worldObj, chunkX, chunkZ);
			}
		}
		int trees = getVariantTreesPerChunk(biomeVariant);
		if (rand.nextFloat() < biome.getTreeIncreaseChance() * biomeVariant.treeFactor) {
			++trees;
		}
		for (l4 = 0; l4 < trees; ++l4) {
			int i7 = chunkX + rand.nextInt(16) + 8;
			k7 = chunkZ + rand.nextInt(16) + 8;
			WorldGenAbstractTree treeGen = getRandomTreeForVariant(rand, biomeVariant).create(false, rand);
			treeGen.generate(worldObj, rand, i7, worldObj.getHeightValue(i7, k7), k7);
		}
		if (trees > 0) {
			float fallenLeaves = trees / 2.0f;
			l3 = 0;
			while (l3 < fallenLeaves) {
				i5 = chunkX + rand.nextInt(16) + 8;
				k4 = chunkZ + rand.nextInt(16) + 8;
				new GOTWorldGenFallenLeaves().generate(worldObj, rand, i5, worldObj.getTopSolidOrLiquidBlock(i5, k4), k4);
				++l3;
			}
			float bushes = trees / 3.0f;
			l3 = 0;
			while (l3 < bushes) {
				i5 = chunkX + rand.nextInt(16) + 8;
				k4 = chunkZ + rand.nextInt(16) + 8;
				new GOTWorldGenBushes().generate(worldObj, rand, i5, worldObj.getTopSolidOrLiquidBlock(i5, k4), k4);
				++l3;
			}
		}
		for (l5 = 0; l5 < logsPerChunk; ++l5) {
			int i9 = chunkX + rand.nextInt(16) + 8;
			int k9 = chunkZ + rand.nextInt(16) + 8;
			logGen.generate(worldObj, rand, i9, worldObj.getHeightValue(i9, k9), k9);
		}
		int flowers = flowersPerChunk;
		flowers = Math.round(flowers * biomeVariant.flowerFactor);
		for (int l8 = 0; l8 < flowers; ++l8) {
			int i11 = chunkX + rand.nextInt(16) + 8;
			int j8 = rand.nextInt(128);
			int k10 = chunkZ + rand.nextInt(16) + 8;
			flowerGen.generate(worldObj, rand, i11, j8, k10);
		}
		int doubleFlowers = doubleFlowersPerChunk;
		doubleFlowers = Math.round(doubleFlowers * biomeVariant.flowerFactor);
		for (int l9 = 0; l9 < doubleFlowers; ++l9) {
			int i12 = chunkX + rand.nextInt(16) + 8;
			j5 = rand.nextInt(128);
			k4 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator doubleFlowerGen = biome.getRandomWorldGenForDoubleFlower(rand);
			doubleFlowerGen.generate(worldObj, rand, i12, j5, k4);
		}
		int grasses = grassPerChunk;
		grasses = Math.round(grasses * biomeVariant.grassFactor);
		for (l3 = 0; l3 < grasses; ++l3) {
			i5 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			int k11 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator grassGen = biome.getRandomWorldGenForGrass(rand);
			grassGen.generate(worldObj, rand, i5, j3, k11);
		}
		int doubleGrasses = doubleGrassPerChunk;
		doubleGrasses = Math.round(doubleGrasses * biomeVariant.grassFactor);
		for (l7 = 0; l7 < doubleGrasses; ++l7) {
			i2 = chunkX + rand.nextInt(16) + 8;
			int j9 = rand.nextInt(128);
			int k12 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator grassGen = biome.getRandomWorldGenForDoubleGrass();
			grassGen.generate(worldObj, rand, i2, j9, k12);
		}
		for (l7 = 0; l7 < deadBushPerChunk; ++l7) {
			i2 = chunkX + rand.nextInt(16) + 8;
			int j10 = rand.nextInt(128);
			int k13 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenDeadBush(Blocks.deadbush).generate(worldObj, rand, i2, j10, k13);
		}
		for (l7 = 0; l7 < waterlilyPerChunk; ++l7) {
			int j11;
			i2 = chunkX + rand.nextInt(16) + 8;
			int k14 = chunkZ + rand.nextInt(16) + 8;
			j11 = rand.nextInt(128);
			waterlilyGen.generate(worldObj, rand, i2, j11, k14);
		}
		for (l7 = 0; l7 < canePerChunk; ++l7) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k17 = chunkZ + rand.nextInt(16) + 8;
			caneGen.generate(worldObj, rand, i2, j2, k17);
		}
		for (l7 = 0; l7 < 10; ++l7) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k18 = chunkZ + rand.nextInt(16) + 8;
			caneGen.generate(worldObj, rand, i2, j2, k18);
		}
		for (l7 = 0; l7 < reedPerChunk; ++l7) {
			int j13;
			i2 = chunkX + rand.nextInt(16) + 8;
			k5 = chunkZ + rand.nextInt(16) + 8;
			j13 = rand.nextInt(128);
			if (rand.nextFloat() < dryReedChance) {
				dryReedGen.generate(worldObj, rand, i2, j13, k5);
				continue;
			}
			reedGen.generate(worldObj, rand, i2, j13, k5);
		}
		for (l7 = 0; l7 < cornPerChunk; ++l7) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k19 = chunkZ + rand.nextInt(16) + 8;
			cornGen.generate(worldObj, rand, i2, j2, k19);
		}
		for (l7 = 0; l7 < cactiPerChunk; ++l7) {
			i2 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k20 = chunkZ + rand.nextInt(16) + 8;
			cactusGen.generate(worldObj, rand, i2, j2, k20);
		}
		if (flowersPerChunk > 0 && rand.nextInt(4) == 0) {
			i5 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			k5 = chunkZ + rand.nextInt(16) + 8;
			new GOTWorldGenBerryBush().generate(worldObj, rand, i5, j3, k5);
		}
		if (generateAgriculture && rand.nextInt(6) == 0) {
			i5 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			k5 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenFlowers(GOTBlocks.pipeweedPlant).generate(worldObj, rand, i5, j3, k5);
		}
		if (generateAgriculture && rand.nextInt(6) == 0) {
			i5 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			k5 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenFlowers(GOTBlocks.flaxPlant).generate(worldObj, rand, i5, j3, k5);
		}
		if (generateAgriculture && rand.nextInt(6) == 0) {
			i5 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			k5 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenFlowers(GOTBlocks.cucumberPlant).generate(worldObj, rand, i5, j3, k5);
		}
		if (biomeVariant.boulderGen != null && rand.nextInt(biomeVariant.boulderChance) == 0) {
			int boulders = MathHelper.getRandomIntegerInRange(rand, 1, biomeVariant.boulderMax);
			for (l = 0; l < boulders; ++l) {
				i3 = chunkX + rand.nextInt(16) + 8;
				k3 = chunkZ + rand.nextInt(16) + 8;
				biomeVariant.boulderGen.generate(worldObj, rand, i3, worldObj.getHeightValue(i3, k3), k3);
			}
		}
	}

	public void decorate(World world, Random random, int i, int k) {
		worldObj = world;
		rand = random;
		chunkX = i;
		chunkZ = k;
		boolean disableLocations = world.getWorldInfo().getTerrainType() == GOT.worldTypeGOTEmpty;
		decorate();
		if (!disableLocations) {
			GOTFixer.addSpecialLocations(world, random, i, k);
			for (Entry<GOTAbstractWaypoint, GOTStructureBase> wp : GOTFixer.structures.entrySet()) {
				if (GOTFixedStructures.fixedAt(i, k, wp.getKey())) {
					wp.getValue().generate(world, random, i, world.getTopSolidOrLiquidBlock(i, k), k, 0);
				}
			}
		}
	}

	public void generateOres() {
		float f;
		for (OreGenerant soil : biomeSoils) {
			genStandardOre(soil.oreChance, soil.oreGen, soil.minHeight, soil.maxHeight);
		}
		for (OreGenerant ore : biomeOres) {
			f = ore.oreChance * biomeOreFactor;
			genStandardOre(f, ore.oreGen, ore.minHeight, ore.maxHeight);
		}
		for (OreGenerant gem : biomeGems) {
			f = gem.oreChance * biomeGemFactor;
			genStandardOre(f, gem.oreGen, gem.minHeight, gem.maxHeight);
		}
	}

	public void genStandardOre(float ores, WorldGenerator oreGen, int minHeight, int maxHeight) {
		while (ores > 0.0f) {
			boolean generate = ores >= 1.0f || rand.nextFloat() < ores;
			ores -= 1.0f;
			if (!generate) {
				continue;
			}
			int i = chunkX + rand.nextInt(16);
			int j = MathHelper.getRandomIntegerInRange(rand, minHeight, maxHeight);
			int k = chunkZ + rand.nextInt(16);
			oreGen.generate(worldObj, rand, i, j, k);
		}
	}

	public void genTree(World world, Random random, int i, int j, int k) {
		WorldGenAbstractTree treeGen = biome.getTreeGen(world, random, i, j);
		treeGen.generate(world, random, i, j, k);
	}

	public GOTTreeType getRandomTree(Random random) {
		if (treeTypes.isEmpty()) {
			return GOTTreeType.OAK;
		}
		WeightedRandom.Item item = WeightedRandom.getRandomItem(random, treeTypes);
		return ((GOTTreeType.WeightedTreeType) item).treeType;
	}

	public GOTTreeType getRandomTreeForVariant(Random random, GOTBiomeVariant variant) {
		if (variant.treeTypes.isEmpty()) {
			return getRandomTree(random);
		}
		float f = variant.variantTreeChance;
		if (random.nextFloat() < f) {
			return variant.getRandomTree(random);
		}
		return getRandomTree(random);
	}

	public int getVariantTreesPerChunk(GOTBiomeVariant variant) {
		int trees = treesPerChunk;
		if (variant.treeFactor > 1.0f) {
			trees = Math.max(trees, 1);
		}
		return Math.round(trees * variant.treeFactor);
	}

	public static class OreGenerant {
		public WorldGenMinable oreGen;
		public float oreChance;
		public int minHeight;
		public int maxHeight;

		public OreGenerant(WorldGenMinable gen, float f, int min, int max) {
			oreGen = gen;
			oreChance = f;
			minHeight = min;
			maxHeight = max;
		}
	}

	public static class Structure {
		public WorldGenerator structureGen;
		public int chunkChance;

		public Structure(WorldGenerator w, int i) {
			structureGen = w;
			chunkChance = i;
		}
	}

}