package got.common.world.biome;

import java.util.*;

import got.common.GOTConfig;
import got.common.database.GOTRegistry;
import got.common.world.*;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.*;
import got.common.world.fixed.*;
import got.common.world.map.*;
import got.common.world.structure.other.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeDecorator {
	public World worldObj;
	public Random rand;
	public Random structureRand = new Random();
	public int chunkX;
	public int chunkZ;
	public GOTBiome biome;
	public GOTSpawner spawner;
	public List<OreGenerant> biomeSoils = new ArrayList<>();
	public List<OreGenerant> biomeOres = new ArrayList<>();
	public List<OreGenerant> biomeGems = new ArrayList<>();
	public float biomeOreFactor = 1.0f;
	public float biomeGemFactor = 0.5f;
	public WorldGenerator clayGen = new GOTWorldGenSand(Blocks.clay, 5, 1);
	public WorldGenerator sandGen = new GOTWorldGenSand(Blocks.sand, 7, 2);
	public WorldGenerator whiteSandGen = new GOTWorldGenSand(GOTRegistry.whiteSand, 7, 2);
	public WorldGenerator quagmireGen = new GOTWorldGenSand(GOTRegistry.quagmire, 7, 2);
	public WorldGenerator surfaceGravelGen = new GOTWorldGenSurfaceGravel();
	public WorldGenerator flowerGen = new GOTWorldGenBiomeFlowers();
	public WorldGenerator logGen = new GOTWorldGenLogs();
	public WorldGenerator caneGen = new WorldGenReed();
	public WorldGenerator reedGen = new GOTWorldGenReeds(GOTRegistry.reeds);
	public WorldGenerator dryReedGen = new GOTWorldGenReeds(GOTRegistry.driedReeds);
	public WorldGenerator stalactiteGen = new GOTWorldGenStalactites();
	public WorldGenerator cactusGen = new WorldGenCactus();
	public int clayPerChunk = 3;
	public int sandPerChunk = 4;
	public int quagmirePerChunk = 0;
	public int treesPerChunk = 0;
	public int logsPerChunk = 0;
	public int flowersPerChunk = 2;
	public int doubleFlowersPerChunk = 0;
	public int grassPerChunk = 1;
	public int doubleGrassPerChunk = 0;
	public int canePerChunk = 0;
	public int reedPerChunk = 1;
	public float dryReedChance = 0.1f;
	public int cactiPerChunk = 0;
	public boolean whiteSand = false;
	public List<GOTTreeType.WeightedTreeType> treeTypes = new ArrayList<>();
	public List<RandomStructure> randomStructures = new ArrayList<>();
	public List<GOTVillageGen> villages = new ArrayList<>();

	public GOTBiomeDecorator(GOTBiome gotbiome) {
		biome = gotbiome;
		addDefaultOres();
	}

	public void addDefaultOres() {
		addSoil(new WorldGenMinable(Blocks.dirt, 32), 40.0f, 0, 256);
		addSoil(new WorldGenMinable(Blocks.gravel, 32), 20.0f, 0, 256);
		addSoil(new WorldGenMinable(GOTRegistry.rock, 0, 12, Blocks.stone), 30.0f, 0, 10);
		addSoil(new WorldGenMinable(GOTRegistry.rock, 1, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTRegistry.rock, 2, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTRegistry.rock, 3, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTRegistry.rock, 4, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTRegistry.rock, 5, 12, Blocks.stone), 30.0f, 0, 64);
		addSoil(new WorldGenMinable(GOTRegistry.rock, 6, 12, Blocks.stone), 30.0f, 0, 64);
		addOre(new WorldGenMinable(GOTRegistry.oreCopper, 8), 15.0f, 0, 128);
		addOre(new WorldGenMinable(GOTRegistry.oreTin, 8), 15.0f, 0, 128);
		addOre(new WorldGenMinable(GOTRegistry.oreSilver, 3), 4.0f, 0, 32);
		addOre(new WorldGenMinable(Blocks.gold_ore, 3), 1.0f, 0, 32);
		addOre(new WorldGenMinable(Blocks.coal_ore, 16), 40.0f, 0, 128);
		addOre(new WorldGenMinable(Blocks.iron_ore, 8), 20.0f, 0, 64);
		addOre(new WorldGenMinable(GOTRegistry.oreSulfur, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(GOTRegistry.oreSaltpeter, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(GOTRegistry.oreSalt, 12), 2.0f, 0, 64);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 1, 6, Blocks.stone), 2.0f, 0, 64);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 0, 6, Blocks.stone), 2.0f, 0, 64);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 4, 5, Blocks.stone), 1.5f, 0, 48);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 6, 5, Blocks.stone), 1.5f, 0, 48);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 2, 4, Blocks.stone), 1.0f, 0, 32);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 3, 4, Blocks.stone), 1.0f, 0, 32);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 7, 4, Blocks.stone), 0.75f, 0, 24);
		addGem(new WorldGenMinable(GOTRegistry.oreGem, 5, 4, Blocks.stone), 0.5f, 0, 16);
	}

	public void addGem(WorldGenerator gen, float f, int min, int max) {
		biomeGems.add(new OreGenerant(gen, f, min, max));
	}

	public void addOre(WorldGenerator gen, float f, int min, int max) {
		biomeOres.add(new OreGenerant(gen, f, min, max));
	}

	public void addRandomStructure(WorldGenerator structure, int chunkChance) {
		randomStructures.add(new RandomStructure(structure, chunkChance));
	}

	public void addSoil(WorldGenerator gen, float f, int min, int max) {
		biomeSoils.add(new OreGenerant(gen, f, min, max));
	}

	public void addSpawner(GOTStructureBase structure) {
	}

	public void addSpecialStructures(World world, Random random, int i, int k) {
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts1).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts2).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts3).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts4).generate(world, random, i, 0, k, 0);
		new GOTStructureFiveFortsWall(false, GOTWaypoint.FiveForts5).generate(world, random, i, 0, k, 0);
		if (GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.WhiteWood) || (GOTFixedStructures.fixedAtMapImageCoords(i, k, GOTWaypoint.Winterfell))) {
			((GOTWorldGenPartyTrees) GOTTreeType.WEIRWOOD.create(false, random)).disableRestrictions().generate(world, random, i + 50, world.getTopSolidOrLiquidBlock(i + 50, k), k);
		}
	}

	public void addTree(GOTTreeType type, int weight) {
		treeTypes.add(new GOTTreeType.WeightedTreeType(type, weight));
	}

	public void affix(GOTVillageGen village) {
		villages.add(village);
	}

	public boolean anyFixedVillagesAt(World world, int i, int k) {
		for (GOTVillageGen village : villages) {
			if (!village.anyFixedVillagesAt(world, i, k)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public void checkForVillages(World world, int i, int k, GOTChunkProvider.ChunkFlags chunkFlags) {
		chunkFlags.isVillage = false;
		for (GOTVillageGen village : villages) {
			List<GOTVillageGen.AbstractInstance<?>> instances = village.getNearbyVillagesAtPosition(world, i, k);
			if (instances.isEmpty()) {
				continue;
			}
			chunkFlags.isVillage = true;
		}
	}

	public void clearOres() {
		biomeSoils.clear();
		biomeOres.clear();
		biomeGems.clear();
	}

	public void clearRandomStructures() {
		randomStructures.clear();
	}

	public void clearTrees() {
		treeTypes.clear();
	}

	public void clearVillages() {
		villages.clear();
	}

	public void decorate() {
		int l;
		int j3;
		int k2;
		int l2;
		int j2;
		int j4;
		int l7;
		int k4;
		int k6;
		int i4;
		int i3;
		int k;
		int i;
		GOTBiomeVariant biomeVariant = ((GOTWorldChunkManager) worldObj.getWorldChunkManager()).getBiomeVariantAt(chunkX + 8, chunkZ + 8);
		generateOres();
		biomeVariant.decorateVariant(worldObj, rand, chunkX, chunkZ, biome);
		for (l2 = 0; l2 < 3; ++l2) {
			i4 = chunkX + rand.nextInt(16) + 8;
			j4 = rand.nextInt(60);
			int k8 = chunkZ + rand.nextInt(16) + 8;
			stalactiteGen.generate(worldObj, rand, i4, j4, k8);
		}
		for (l2 = 0; l2 < quagmirePerChunk; ++l2) {
			i4 = chunkX + rand.nextInt(16) + 8;
			k2 = chunkZ + rand.nextInt(16) + 8;
			quagmireGen.generate(worldObj, rand, i4, worldObj.getTopSolidOrLiquidBlock(i4, k2), k2);
		}
		for (l2 = 0; l2 < sandPerChunk; ++l2) {
			i4 = chunkX + rand.nextInt(16) + 8;
			k2 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator biomeSandGenerator = sandGen;
			if (whiteSand) {
				biomeSandGenerator = whiteSandGen;
			}
			biomeSandGenerator.generate(worldObj, rand, i4, worldObj.getTopSolidOrLiquidBlock(i4, k2), k2);
		}
		for (l2 = 0; l2 < clayPerChunk; ++l2) {
			i4 = chunkX + rand.nextInt(16) + 8;
			k2 = chunkZ + rand.nextInt(16) + 8;
			clayGen.generate(worldObj, rand, i4, worldObj.getTopSolidOrLiquidBlock(i4, k2), k2);
		}
		if (rand.nextInt(60) == 0) {
			int i2 = chunkX + rand.nextInt(16) + 8;
			k = chunkZ + rand.nextInt(16) + 8;
			surfaceGravelGen.generate(worldObj, rand, i2, 0, k);
		}
		if (!biomeVariant.disableStructures && Math.abs(chunkX) > 32 && Math.abs(chunkZ) > 32) {
			int i6;
			long seed = chunkX * 1879267 ^ chunkZ * 67209689L;
			seed = seed * seed * 5829687L + seed * 2876L;
			structureRand.setSeed(seed);
			boolean roadNear = GOTRoads.isRoadNear(chunkX + 8, chunkZ + 8, 16) >= 0.0f;
			boolean wallNear = GOTWalls.isWallNear(chunkX + 8, chunkZ + 8, 16) >= 0.0f;
			if (!roadNear || !wallNear) {
				for (RandomStructure randomstructure : randomStructures) {
					if (structureRand.nextInt(randomstructure.chunkChance) != 0) {
						continue;
					}
					i6 = chunkX + rand.nextInt(16) + 8;
					k = chunkZ + rand.nextInt(16) + 8;
					j4 = worldObj.getTopSolidOrLiquidBlock(i6, k);
					randomstructure.structureGen.generate(worldObj, rand, i6, j4, k);
				}
			}
			for (GOTVillageGen village : villages) {
				village.generateInChunk(worldObj, chunkX, chunkZ);
			}
		}
		int trees = getVariantTreesPerChunk(biomeVariant);
		if (rand.nextFloat() < biome.getTreeIncreaseChance() * biomeVariant.treeFactor) {
			++trees;
		}
		for (int l4 = 0; l4 < trees; ++l4) {
			int i7 = chunkX + rand.nextInt(16) + 8;
			int k5 = chunkZ + rand.nextInt(16) + 8;
			WorldGenAbstractTree treeGen = getRandomTreeForVariant(rand, biomeVariant).create(false, rand);
			treeGen.generate(worldObj, rand, i7, worldObj.getHeightValue(i7, k5), k5);
		}
		if (trees > 0) {
			float fallenLeaves = trees / 2.0f;
			int fallenLeavesI = (int) fallenLeaves;
			float fallenLeavesR = fallenLeaves - fallenLeavesI;
			if (rand.nextFloat() < fallenLeavesR) {
				++fallenLeavesI;
			}
			l7 = 0;
			while (l7 < fallenLeaves) {
				i = chunkX + rand.nextInt(16) + 8;
				k4 = chunkZ + rand.nextInt(16) + 8;
				new GOTWorldGenFallenLeaves().generate(worldObj, rand, i, worldObj.getTopSolidOrLiquidBlock(i, k4), k4);
				++l7;
			}
		}
		if (trees > 0) {
			float bushes = trees / 3.0f;
			int bushesI = (int) bushes;
			float bushesR = bushes - bushesI;
			if (rand.nextFloat() < bushesR) {
				++bushesI;
			}
			l7 = 0;
			while (l7 < bushes) {
				i = chunkX + rand.nextInt(16) + 8;
				k4 = chunkZ + rand.nextInt(16) + 8;
				new GOTWorldGenBushes().generate(worldObj, rand, i, worldObj.getTopSolidOrLiquidBlock(i, k4), k4);
				++l7;
			}
		}
		for (int l3 = 0; l3 < logsPerChunk; ++l3) {
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
			int j5 = rand.nextInt(128);
			k4 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator doubleFlowerGen = biome.getRandomWorldGenForDoubleFlower(rand);
			doubleFlowerGen.generate(worldObj, rand, i12, j5, k4);
		}
		int grasses = grassPerChunk;
		grasses = Math.round(grasses * biomeVariant.grassFactor);
		for (l7 = 0; l7 < grasses; ++l7) {
			i = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			int k11 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator grassGen = biome.getRandomWorldGenForGrass(rand);
			grassGen.generate(worldObj, rand, i, j3, k11);
		}
		int doubleGrasses = doubleGrassPerChunk;
		doubleGrasses = Math.round(doubleGrasses * biomeVariant.grassFactor);
		for (l = 0; l < doubleGrasses; ++l) {
			i3 = chunkX + rand.nextInt(16) + 8;
			int j9 = rand.nextInt(128);
			int k12 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator grassGen = biome.getRandomWorldGenForDoubleGrass(rand);
			grassGen.generate(worldObj, rand, i3, j9, k12);
		}
		for (l = 0; l < canePerChunk; ++l) {
			i3 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k17 = chunkZ + rand.nextInt(16) + 8;
			caneGen.generate(worldObj, rand, i3, j2, k17);
		}
		for (l = 0; l < 10; ++l) {
			i3 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k18 = chunkZ + rand.nextInt(16) + 8;
			caneGen.generate(worldObj, rand, i3, j2, k18);
		}
		for (l = 0; l < reedPerChunk; ++l) {
			int j13;
			i3 = chunkX + rand.nextInt(16) + 8;
			k6 = chunkZ + rand.nextInt(16) + 8;
			for (j13 = rand.nextInt(128); j13 > 0 && worldObj.getBlock(i3, j13 - 1, k6) == Blocks.air; --j13) {
			}
			if (rand.nextFloat() < dryReedChance) {
				dryReedGen.generate(worldObj, rand, i3, j13, k6);
				continue;
			}
			reedGen.generate(worldObj, rand, i3, j13, k6);
		}
		for (l = 0; l < cactiPerChunk; ++l) {
			i3 = chunkX + rand.nextInt(16) + 8;
			j2 = rand.nextInt(128);
			int k20 = chunkZ + rand.nextInt(16) + 8;
			cactusGen.generate(worldObj, rand, i3, j2, k20);
		}
		if (flowersPerChunk > 0 && rand.nextInt(4) == 0) {
			i = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			k6 = chunkZ + rand.nextInt(16) + 8;
			new GOTWorldGenBerryBush().generate(worldObj, rand, i, j3, k6);
		}
		if (biomeVariant.boulderGen != null && rand.nextInt(biomeVariant.boulderChance) == 0) {
			int boulders = MathHelper.getRandomIntegerInRange(rand, 1, biomeVariant.boulderMax);
			for (int l6 = 0; l6 < boulders; ++l6) {
				int i5 = chunkX + rand.nextInt(16) + 8;
				int k3 = chunkZ + rand.nextInt(16) + 8;
				biomeVariant.boulderGen.generate(worldObj, rand, i5, worldObj.getHeightValue(i5, k3), k3);
			}
		}
	}

	public void decorate(World world, Random random, int i, int k) {
		worldObj = world;
		rand = random;
		chunkX = i;
		chunkZ = k;
		this.decorate();
		if (!GOTConfig.clearMap) {
			addSpecialStructures(world, random, i, k);
		}
		GOTStructureBase structure = getFixedStructure(world, random, i, k);
		if (structure != null && !GOTConfig.clearMap) {
			structure.generate(world, random, i, world.getTopSolidOrLiquidBlock(i, k), k, 0);
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
			boolean generate = ores >= 1.0f ? true : rand.nextFloat() < ores;
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
		WorldGenAbstractTree treeGen = biome.getTreeGen(world, random, i, j, k);
		treeGen.generate(world, random, i, j, k);
	}

	public GOTStructureBase getFixedStructure(World world, Random random, int i, int k) {

		if (GOTSpawner.AddamMarbrand.fixedAt(world, i, k)) {
			return new GOTSpawner.AddamMarbrand(false);
		}
		if (GOTSpawner.AeronGreyjoy.fixedAt(world, i, k)) {
			return new GOTSpawner.AeronGreyjoy(false);
		}
		if (GOTSpawner.AndersYronwood.fixedAt(world, i, k)) {
			return new GOTSpawner.AndersYronwood(false);
		}
		if (GOTSpawner.AndrikTheUnsmilling.fixedAt(world, i, k)) {
			return new GOTSpawner.AndrikTheUnsmilling(false);
		}
		if (GOTSpawner.AnyaWaynwood.fixedAt(world, i, k)) {
			return new GOTSpawner.AnyaWaynwood(false);
		}
		if (GOTSpawner.ArdrianCeltigar.fixedAt(world, i, k)) {
			return new GOTSpawner.ArdrianCeltigar(false);
		}
		if (GOTSpawner.Asshai.fixedAt(world, i, k)) {
			return new GOTSpawner.Asshai(false);
		}
		if (GOTSpawner.Astapor.fixedAt(world, i, k)) {
			return new GOTSpawner.Astapor(false);
		}
		if (GOTSpawner.BaelorBlacktyde.fixedAt(world, i, k)) {
			return new GOTSpawner.BaelorBlacktyde(false);
		}
		if (GOTSpawner.BarbreyDustin.fixedAt(world, i, k)) {
			return new GOTSpawner.BarbreyDustin(false);
		}
		if (GOTSpawner.BenedarBelmore.fixedAt(world, i, k)) {
			return new GOTSpawner.BenedarBelmore(false);
		}
		if (GOTSpawner.BenjenStark.fixedAt(world, i, k)) {
			return new GOTSpawner.BenjenStark(false);
		}
		if (GOTSpawner.BericDayne.fixedAt(world, i, k)) {
			return new GOTSpawner.BericDayne(false);
		}
		if (GOTSpawner.BericDondarrion.fixedAt(world, i, k)) {
			return new GOTSpawner.BericDondarrion(false);
		}
		if (GOTSpawner.BuGai.fixedAt(world, i, k)) {
			return new GOTSpawner.BuGai(false);
		}
		if (GOTSpawner.CasterlyRock.fixedAt(world, i, k)) {
			return new GOTSpawner.CasterlyRock(false);
		}
		if (GOTSpawner.ClementPiper.fixedAt(world, i, k)) {
			return new GOTSpawner.ClementPiper(false);
		}
		if (GOTSpawner.CleyCerwyn.fixedAt(world, i, k)) {
			return new GOTSpawner.CleyCerwyn(false);
		}
		if (GOTSpawner.DaenerysTargaryen.fixedAt(world, i, k)) {
			return new GOTSpawner.DaenerysTargaryen(false);
		}
		if (GOTSpawner.Dagmer.fixedAt(world, i, k)) {
			return new GOTSpawner.Dagmer(false);
		}
		if (GOTSpawner.Dragonstone.fixedAt(world, i, k)) {
			return new GOTSpawner.Dragonstone(false);
		}
		if (GOTSpawner.Dreadfort.fixedAt(world, i, k)) {
			return new GOTSpawner.Dreadfort(false);
		}
		if (GOTSpawner.Driftmark.fixedAt(world, i, k)) {
			return new GOTSpawner.Driftmark(false);
		}
		if (GOTSpawner.DunstanDrumm.fixedAt(world, i, k)) {
			return new GOTSpawner.DunstanDrumm(false);
		}
		if (GOTSpawner.Ebrose.fixedAt(world, i, k)) {
			return new GOTSpawner.Ebrose(false);
		}
		if (GOTSpawner.EldonEstermont.fixedAt(world, i, k)) {
			return new GOTSpawner.EldonEstermont(false);
		}
		if (GOTSpawner.ErikIronmaker.fixedAt(world, i, k)) {
			return new GOTSpawner.ErikIronmaker(false);
		}
		if (GOTSpawner.ForleyPrester.fixedAt(world, i, k)) {
			return new GOTSpawner.ForleyPrester(false);
		}
		if (GOTSpawner.FranklynFowler.fixedAt(world, i, k)) {
			return new GOTSpawner.FranklynFowler(false);
		}
		if (GOTSpawner.GarlanTyrell.fixedAt(world, i, k)) {
			return new GOTSpawner.GarlanTyrell(false);
		}
		if (GOTSpawner.GateOfTheMoon.fixedAt(world, i, k)) {
			return new GOTSpawner.GateOfTheMoon(false);
		}
		if (GOTSpawner.GeroldDayne.fixedAt(world, i, k)) {
			return new GOTSpawner.GeroldDayne(false);
		}
		if (GOTSpawner.GeroldGrafton.fixedAt(world, i, k)) {
			return new GOTSpawner.GeroldGrafton(false);
		}
		if (GOTSpawner.GilwoodHunter.fixedAt(world, i, k)) {
			return new GOTSpawner.GilwoodHunter(false);
		}
		if (GOTSpawner.GoroldGoodbrother.fixedAt(world, i, k)) {
			return new GOTSpawner.GoroldGoodbrother(false);
		}
		if (GOTSpawner.GregorClegane.fixedAt(world, i, k)) {
			return new GOTSpawner.GregorClegane(false);
		}
		if (GOTSpawner.GulianSwann.fixedAt(world, i, k)) {
			return new GOTSpawner.GulianSwann(false);
		}
		if (GOTSpawner.GylbertFarwynd.fixedAt(world, i, k)) {
			return new GOTSpawner.GylbertFarwynd(false);
		}
		if (GOTSpawner.HarmenUller.fixedAt(world, i, k)) {
			return new GOTSpawner.HarmenUller(false);
		}
		if (GOTSpawner.HarrasHarlaw.fixedAt(world, i, k)) {
			return new GOTSpawner.HarrasHarlaw(false);
		}
		if (GOTSpawner.HarroldHardyng.fixedAt(world, i, k)) {
			return new GOTSpawner.HarroldHardyng(false);
		}
		if (GOTSpawner.HarryStrickland.fixedAt(world, i, k)) {
			return new GOTSpawner.HarryStrickland(false);
		}
		if (GOTSpawner.HarysSwyft.fixedAt(world, i, k)) {
			return new GOTSpawner.HarysSwyft(false);
		}
		if (GOTSpawner.HelmanTallhart.fixedAt(world, i, k)) {
			return new GOTSpawner.HelmanTallhart(false);
		}
		if (GOTSpawner.Highgarden.fixedAt(world, i, k)) {
			return new GOTSpawner.Highgarden(false);
		}
		if (GOTSpawner.HizdahrZoLoraq.fixedAt(world, i, k)) {
			return new GOTSpawner.HizdahrZoLoraq(false);
		}
		if (GOTSpawner.HortonRedfort.fixedAt(world, i, k)) {
			return new GOTSpawner.HortonRedfort(false);
		}
		if (GOTSpawner.HowlandReed.fixedAt(world, i, k)) {
			return new GOTSpawner.HowlandReed(false);
		}
		if (GOTSpawner.Hummel009.fixedAt(world, i, k)) {
			return new GOTSpawner.Hummel009(false);
		}
		if (GOTSpawner.IllyrioMopatis.fixedAt(world, i, k)) {
			return new GOTSpawner.IllyrioMopatis(false);
		}
		if (GOTSpawner.JasonMallister.fixedAt(world, i, k)) {
			return new GOTSpawner.JasonMallister(false);
		}
		if (GOTSpawner.JohnUmber.fixedAt(world, i, k)) {
			return new GOTSpawner.JohnUmber(false);
		}
		if (GOTSpawner.JonConnington.fixedAt(world, i, k)) {
			return new GOTSpawner.JonConnington(false);
		}
		if (GOTSpawner.JonosBracken.fixedAt(world, i, k)) {
			return new GOTSpawner.JonosBracken(false);
		}
		if (GOTSpawner.Lannisport.fixedAt(world, i, k)) {
			return new GOTSpawner.Lannisport(false);
		}
		if (GOTSpawner.LeoLefford.fixedAt(world, i, k)) {
			return new GOTSpawner.LeoLefford(false);
		}
		if (GOTSpawner.LeytonHightower.fixedAt(world, i, k)) {
			return new GOTSpawner.LeytonHightower(false);
		}
		if (GOTSpawner.LyleCrakehall.fixedAt(world, i, k)) {
			return new GOTSpawner.LyleCrakehall(false);
		}
		if (GOTSpawner.LynCorbray.fixedAt(world, i, k)) {
			return new GOTSpawner.LynCorbray(false);
		}
		if (GOTSpawner.MaegeMormont.fixedAt(world, i, k)) {
			return new GOTSpawner.MaegeMormont(false);
		}
		if (GOTSpawner.MaronVolmark.fixedAt(world, i, k)) {
			return new GOTSpawner.MaronVolmark(false);
		}
		if (GOTSpawner.MathisRowan.fixedAt(world, i, k)) {
			return new GOTSpawner.MathisRowan(false);
		}
		if (GOTSpawner.Mellario.fixedAt(world, i, k)) {
			return new GOTSpawner.Mellario(false);
		}
		if (GOTSpawner.Moqorro.fixedAt(world, i, k)) {
			return new GOTSpawner.Moqorro(false);
		}
		if (GOTSpawner.OrtonMerryweather.fixedAt(world, i, k)) {
			return new GOTSpawner.OrtonMerryweather(false);
		}
		if (GOTSpawner.PaxterRedwyne.fixedAt(world, i, k)) {
			return new GOTSpawner.PaxterRedwyne(false);
		}
		if (GOTSpawner.Pyke.fixedAt(world, i, k)) {
			return new GOTSpawner.Pyke(false);
		}
		if (GOTSpawner.QuennRoxton.fixedAt(world, i, k)) {
			return new GOTSpawner.QuennRoxton(false);
		}
		if (GOTSpawner.QuentenBanefort.fixedAt(world, i, k)) {
			return new GOTSpawner.QuentenBanefort(false);
		}
		if (GOTSpawner.QuentynMartell.fixedAt(world, i, k)) {
			return new GOTSpawner.QuentynMartell(false);
		}
		if (GOTSpawner.QuentynQorgyle.fixedAt(world, i, k)) {
			return new GOTSpawner.QuentynQorgyle(false);
		}
		if (GOTSpawner.RandyllTarly.fixedAt(world, i, k)) {
			return new GOTSpawner.RandyllTarly(false);
		}
		if (GOTSpawner.RickardKarstark.fixedAt(world, i, k)) {
			return new GOTSpawner.RickardKarstark(false);
		}
		if (GOTSpawner.Riverrun.fixedAt(world, i, k)) {
			return new GOTSpawner.Riverrun(false);
		}
		if (GOTSpawner.RodrikHarlaw.fixedAt(world, i, k)) {
			return new GOTSpawner.RodrikHarlaw(false);
		}
		if (GOTSpawner.RodrikRyswell.fixedAt(world, i, k)) {
			return new GOTSpawner.RodrikRyswell(false);
		}
		if (GOTSpawner.SalladhorSaan.fixedAt(world, i, k)) {
			return new GOTSpawner.SalladhorSaan(false);
		}
		if (GOTSpawner.SebastonFarman.fixedAt(world, i, k)) {
			return new GOTSpawner.SebastonFarman(false);
		}
		if (GOTSpawner.SelwynTarth.fixedAt(world, i, k)) {
			return new GOTSpawner.SelwynTarth(false);
		}
		if (GOTSpawner.StormsEnd.fixedAt(world, i, k)) {
			return new GOTSpawner.StormsEnd(false);
		}
		if (GOTSpawner.Sunspear.fixedAt(world, i, k)) {
			return new GOTSpawner.Sunspear(false);
		}
		if (GOTSpawner.SymondTempleton.fixedAt(world, i, k)) {
			return new GOTSpawner.SymondTempleton(false);
		}
		if (GOTSpawner.ThreeEyedRaven.fixedAt(world, i, k)) {
			return new GOTSpawner.ThreeEyedRaven(false);
		}
		if (GOTSpawner.TugarKhan.fixedAt(world, i, k)) {
			return new GOTSpawner.TugarKhan(false);
		}
		if (GOTSpawner.TwinsLeft.fixedAt(world, i, k)) {
			return new GOTSpawner.TwinsLeft(false);
		}
		if (GOTSpawner.TychoNestoris.fixedAt(world, i, k)) {
			return new GOTSpawner.TychoNestoris(false);
		}
		if (GOTSpawner.TytosBlackwood.fixedAt(world, i, k)) {
			return new GOTSpawner.TytosBlackwood(false);
		}
		if (GOTSpawner.TytosBrax.fixedAt(world, i, k)) {
			return new GOTSpawner.TytosBrax(false);
		}
		if (GOTSpawner.WalderFrey.fixedAt(world, i, k)) {
			return new GOTSpawner.WalderFrey(false);
		}
		if (GOTSpawner.WilliamMooton.fixedAt(world, i, k)) {
			return new GOTSpawner.WilliamMooton(false);
		}
		if (GOTSpawner.Winterfell.fixedAt(world, i, k)) {
			return new GOTSpawner.Winterfell(false);
		}
		if (GOTSpawner.WymanManderly.fixedAt(world, i, k)) {
			return new GOTSpawner.WymanManderly(false);
		}
		if (GOTSpawner.XaroXhoanDaxos.fixedAt(world, i, k)) {
			return new GOTSpawner.XaroXhoanDaxos(false);
		}
		if (GOTSpawner.YohnRoyce.fixedAt(world, i, k)) {
			return new GOTSpawner.YohnRoyce(false);
		}
		if (GOTSpawner.YoungGriff.fixedAt(world, i, k)) {
			return new GOTSpawner.YoungGriff(false);
		}
		if (GOTSpawner.Yunkai.fixedAt(world, i, k)) {
			return new GOTSpawner.Yunkai(false);
		}
		if (GOTStructureEuronShip.fixedAt(world, i, k)) {
			return new GOTStructureEuronShip(false);
		}
		return null;
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
		public WorldGenerator oreGen;
		public float oreChance;
		public int minHeight;
		public int maxHeight;

		public OreGenerant(WorldGenerator gen, float f, int min, int max) {
			oreGen = gen;
			oreChance = f;
			minHeight = min;
			maxHeight = max;
		}
	}

	public static class RandomStructure {
		public WorldGenerator structureGen;
		public int chunkChance;

		public RandomStructure(WorldGenerator w, int i) {
			structureGen = w;
			chunkChance = i;
		}
	}

}