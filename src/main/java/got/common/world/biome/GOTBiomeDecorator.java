package got.common.world.biome;

import got.GOT;
import got.common.database.GOTBlocks;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.sothoryos.GOTBiomeYeen;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.westeros.GOTBiomeAlwaysWinter;
import got.common.world.feature.*;
import got.common.world.map.GOTBeziers;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

import java.util.*;

public class GOTBiomeDecorator {
	private final Collection<GOTTreeType.WeightedTreeType> treeTypes = new HashSet<>();
	private final Collection<GOTStructureBaseSettlement> settlements = new HashSet<>();
	private final Collection<GOTStructureBaseSettlement> fixedSettlements = new HashSet<>();
	private final Collection<OreGenerant> biomeOres = new HashSet<>();
	private final Collection<OreGenerant> biomeGems = new HashSet<>();
	private final Collection<Structure> structures = new HashSet<>();

	private final List<OreGenerant> biomeSoils = new ArrayList<>();

	private final WorldGenerator sandGen = new GOTWorldGenSand(Blocks.sand, 7, 2);
	private final WorldGenerator whiteSandGen = new GOTWorldGenSand(GOTBlocks.whiteSand, 7, 2);
	private final WorldGenerator quagmireGen = new GOTWorldGenSand(GOTBlocks.quagmire, 7, 2);
	private final WorldGenerator surfaceGravelGen = new GOTWorldGenSurfaceGravel();
	private final WorldGenerator flowerGen = new GOTWorldGenBiomeFlowers();
	private final WorldGenerator logGen = new GOTWorldGenLogs();
	private final WorldGenerator caneGen = new WorldGenReed();
	private final WorldGenerator reedGen = new GOTWorldGenReeds(GOTBlocks.reeds);
	private final WorldGenerator dryReedGen = new GOTWorldGenReeds(GOTBlocks.driedReeds);
	private final WorldGenerator cornGen = new GOTWorldGenCorn();
	private final WorldGenerator waterlilyGen = new WorldGenWaterlily();
	private final WorldGenerator cactusGen = new WorldGenCactus();

	private final GOTBiome biome;

	private final Random structureRand = new Random();

	private WorldGenerator clayGen = new GOTWorldGenSand(Blocks.clay, 5, 1);
	private WorldGenerator stalactiteGen = new GOTWorldGenStalactites(GOTBlocks.stalactite);

	private Random rand;

	private World worldObj;

	private boolean generateAgriculture;
	private boolean whiteSand;

	private float biomeOreFactor = 1.0f;
	private float biomeGemFactor = 0.5f;
	private float dryReedChance = 0.1f;

	private int sandPerChunk = 4;
	private int clayPerChunk = 3;
	private int quagmirePerChunk;
	private int treesPerChunk;
	private int logsPerChunk;
	private int flowersPerChunk = 2;
	private int doubleFlowersPerChunk;
	private int grassPerChunk = 1;
	private int doubleGrassPerChunk;
	private int deadBushPerChunk;
	private int waterlilyPerChunk;
	private int canePerChunk;
	private int reedPerChunk = 1;
	private int cornPerChunk;
	private int cactiPerChunk;
	private int chunkX;
	private int chunkZ;

	public GOTBiomeDecorator(GOTBiome gotbiome) {
		biome = gotbiome;
		addDefaultOres();
	}

	private void addDefaultOres() {
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
		addOre(new WorldGenMinable(Blocks.coal_ore, 16), 40.0f, 0, 128);
		addOre(new WorldGenMinable(Blocks.iron_ore, 8), 20.0f, 0, 64);
		addOre(new WorldGenMinable(GOTBlocks.oreSulfur, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(GOTBlocks.oreSaltpeter, 8), 2.0f, 0, 64);
		addOre(new WorldGenMinable(GOTBlocks.oreSalt, 12), 2.0f, 0, 64);

		/* Зарегулированные пропорциональные цены */
		addOre(new WorldGenMinable(GOTBlocks.oreSilver, 4), 8.0f, 0, 32);
		addOre(new WorldGenMinable(Blocks.gold_ore, 4), 2.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 5, 4, Blocks.stone), 2.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 7, 4, Blocks.stone), 3.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 0, 4, Blocks.stone), 4.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 1, 4, Blocks.stone), 4.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 2, 4, Blocks.stone), 4.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 3, 4, Blocks.stone), 4.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 4, 4, Blocks.stone), 4.0f, 0, 32);
		addGem(new WorldGenMinable(GOTBlocks.oreGem, 6, 4, Blocks.stone), 4.0f, 0, 32);
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

	private void decorate() {
		int k;
		int k2;
		int i;
		int k4;
		int l2;
		int j2;
		int i2;
		int l3;
		int k5;
		int l4;
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
		biomeVariant.decorateVariant(worldObj, rand, chunkX, chunkZ);
		if (biome instanceof GOTBiomeAlwaysWinter) {
			stalactiteGen = new GOTWorldGenStalactites(GOTBlocks.stalactiteIce);
		} else if (biome instanceof GOTBiomeYeen) {
			stalactiteGen = new GOTWorldGenStalactites(GOTBlocks.stalactiteObsidian);
		}
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
		if (!biomeVariant.isDisableStructures() && Math.abs(chunkX) > 32 && Math.abs(chunkZ) > 32) {
			long seed = chunkX * 1879267L ^ chunkZ * 67209689L;
			seed = seed * seed * 5829687L + seed * 2876L;
			structureRand.setSeed(seed);
			boolean roadNear = GOTBeziers.isBezierNear(chunkX + 8, chunkZ + 8, 16, GOTBeziers.Type.ROAD) >= 0.0f;
			boolean wallNear = GOTBeziers.isBezierNear(chunkX + 8, chunkZ + 8, 16, GOTBeziers.Type.WALL) >= 0.0f;
			boolean linkerNear = GOTBeziers.isBezierNear(chunkX + 8, chunkZ + 8, 16, GOTBeziers.Type.LINKER) >= 0.0f;
			if (!roadNear && !wallNear && !linkerNear && !anyFixedSettlementsAt(worldObj, chunkX, chunkZ)) {
				for (Structure randomstructure : structures) {
					if (structureRand.nextInt(randomstructure.getChunkChance()) == 0) {
						int i6 = chunkX + rand.nextInt(16) + 8;
						k2 = chunkZ + rand.nextInt(16) + 8;
						j5 = worldObj.getTopSolidOrLiquidBlock(i6, k2);
						randomstructure.getStructureGen().generate(worldObj, rand, i6, j5, k2);
					}
				}
			}
			for (GOTStructureBaseSettlement settlement : settlements) {
				settlement.generateInChunk(worldObj, chunkX, chunkZ);
			}
		}
		int trees = getVariantTreesPerChunk(biomeVariant);
		if (rand.nextFloat() < 0.1f * biomeVariant.getTreeFactor()) {
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
		flowers = Math.round(flowers * biomeVariant.getFlowerFactor());
		for (int l8 = 0; l8 < flowers; ++l8) {
			int i11 = chunkX + rand.nextInt(16) + 8;
			int j8 = rand.nextInt(128);
			int k10 = chunkZ + rand.nextInt(16) + 8;
			flowerGen.generate(worldObj, rand, i11, j8, k10);
		}
		int doubleFlowers = doubleFlowersPerChunk;
		doubleFlowers = Math.round(doubleFlowers * biomeVariant.getFlowerFactor());
		for (int l9 = 0; l9 < doubleFlowers; ++l9) {
			int i12 = chunkX + rand.nextInt(16) + 8;
			j5 = rand.nextInt(128);
			k4 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator doubleFlowerGen = biome.getRandomWorldGenForDoubleFlower(rand);
			doubleFlowerGen.generate(worldObj, rand, i12, j5, k4);
		}
		int grasses = grassPerChunk;
		grasses = Math.round(grasses * biomeVariant.getGrassFactor());
		for (l3 = 0; l3 < grasses; ++l3) {
			i5 = chunkX + rand.nextInt(16) + 8;
			j3 = rand.nextInt(128);
			int k11 = chunkZ + rand.nextInt(16) + 8;
			WorldGenerator grassGen = biome.getRandomWorldGenForGrass(rand);
			grassGen.generate(worldObj, rand, i5, j3, k11);
		}
		int doubleGrasses = doubleGrassPerChunk;
		doubleGrasses = Math.round(doubleGrasses * biomeVariant.getGrassFactor());
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
			i2 = chunkX + rand.nextInt(16) + 8;
			int k14 = chunkZ + rand.nextInt(16) + 8;
			int j11 = rand.nextInt(128);
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
			i2 = chunkX + rand.nextInt(16) + 8;
			k5 = chunkZ + rand.nextInt(16) + 8;
			int j13 = rand.nextInt(128);
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
		}
	}

	private void generateOres() {
		float f;
		for (OreGenerant soil : biomeSoils) {
			genStandardOre(soil.getOreChance(), soil.getOreGen(), soil.getMinHeight(), soil.getMaxHeight());
		}
		for (OreGenerant ore : biomeOres) {
			f = ore.getOreChance() * biomeOreFactor;
			genStandardOre(f, ore.getOreGen(), ore.getMinHeight(), ore.getMaxHeight());
		}
		for (OreGenerant gem : biomeGems) {
			f = gem.getOreChance() * biomeGemFactor;
			genStandardOre(f, gem.getOreGen(), gem.getMinHeight(), gem.getMaxHeight());
		}
	}

	private void genStandardOre(float ores, WorldGenerator oreGen, int minHeight, int maxHeight) {
		float ores1 = ores;
		while (ores1 > 0.0f) {
			boolean generate = ores1 >= 1.0f || rand.nextFloat() < ores1;
			ores1 -= 1.0f;
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
		return ((GOTTreeType.WeightedTreeType) item).getTreeType();
	}

	public GOTTreeType getRandomTreeForVariant(Random random, GOTBiomeVariant variant) {
		if (variant.getTreeTypes().isEmpty()) {
			return getRandomTree(random);
		}
		float f = variant.getVariantTreeChance();
		if (random.nextFloat() < f) {
			return variant.getRandomTree(random);
		}
		return getRandomTree(random);
	}

	public int getVariantTreesPerChunk(GOTBiomeVariant variant) {
		int trees = treesPerChunk;
		if (variant.getTreeFactor() > 1.0f) {
			trees = Math.max(trees, 1);
		}
		return Math.round(trees * variant.getTreeFactor());
	}

	public Collection<OreGenerant> getBiomeOres() {
		return biomeOres;
	}

	public Collection<OreGenerant> getBiomeGems() {
		return biomeGems;
	}

	public List<OreGenerant> getBiomeSoils() {
		return biomeSoils;
	}

	public float getBiomeOreFactor() {
		return biomeOreFactor;
	}

	public void setBiomeOreFactor(float biomeOreFactor) {
		this.biomeOreFactor = biomeOreFactor;
	}

	public float getBiomeGemFactor() {
		return biomeGemFactor;
	}

	public void setBiomeGemFactor(float biomeGemFactor) {
		this.biomeGemFactor = biomeGemFactor;
	}

	public void setClayGen(WorldGenerator clayGen) {
		this.clayGen = clayGen;
	}

	public void setSandPerChunk(int sandPerChunk) {
		this.sandPerChunk = sandPerChunk;
	}

	public void setClayPerChunk(int clayPerChunk) {
		this.clayPerChunk = clayPerChunk;
	}

	public void setQuagmirePerChunk(int quagmirePerChunk) {
		this.quagmirePerChunk = quagmirePerChunk;
	}

	public int getTreesPerChunk() {
		return treesPerChunk;
	}

	public void setTreesPerChunk(int treesPerChunk) {
		this.treesPerChunk = treesPerChunk;
	}

	public void setLogsPerChunk(int logsPerChunk) {
		this.logsPerChunk = logsPerChunk;
	}

	public void setFlowersPerChunk(int flowersPerChunk) {
		this.flowersPerChunk = flowersPerChunk;
	}

	public void setDoubleFlowersPerChunk(int doubleFlowersPerChunk) {
		this.doubleFlowersPerChunk = doubleFlowersPerChunk;
	}

	public int getGrassPerChunk() {
		return grassPerChunk;
	}

	public void setGrassPerChunk(int grassPerChunk) {
		this.grassPerChunk = grassPerChunk;
	}

	public void setDoubleGrassPerChunk(int doubleGrassPerChunk) {
		this.doubleGrassPerChunk = doubleGrassPerChunk;
	}

	public boolean isWhiteSand() {
		return whiteSand;
	}

	public void setWhiteSand(boolean whiteSand) {
		this.whiteSand = whiteSand;
	}

	public Collection<Structure> getStructures() {
		return structures;
	}

	public Collection<GOTStructureBaseSettlement> getSettlements() {
		return settlements;
	}

	public Collection<GOTTreeType.WeightedTreeType> getTreeTypes() {
		return treeTypes;
	}

	public void setGenerateAgriculture(boolean generateAgriculture) {
		this.generateAgriculture = generateAgriculture;
	}

	public void setCactiPerChunk(int cactiPerChunk) {
		this.cactiPerChunk = cactiPerChunk;
	}

	public void setCornPerChunk(int cornPerChunk) {
		this.cornPerChunk = cornPerChunk;
	}

	public void setDryReedChance(float dryReedChance) {
		this.dryReedChance = dryReedChance;
	}

	public void setReedPerChunk(int reedPerChunk) {
		this.reedPerChunk = reedPerChunk;
	}

	public void setCanePerChunk(int canePerChunk) {
		this.canePerChunk = canePerChunk;
	}

	public void setWaterlilyPerChunk(int waterlilyPerChunk) {
		this.waterlilyPerChunk = waterlilyPerChunk;
	}

	public void setDeadBushPerChunk(int deadBushPerChunk) {
		this.deadBushPerChunk = deadBushPerChunk;
	}

	public static class OreGenerant {
		private final WorldGenMinable oreGen;
		private final float oreChance;
		private final int minHeight;
		private final int maxHeight;

		private OreGenerant(WorldGenMinable gen, float f, int min, int max) {
			oreGen = gen;
			oreChance = f;
			minHeight = min;
			maxHeight = max;
		}

		public int getMaxHeight() {
			return maxHeight;
		}

		public int getMinHeight() {
			return minHeight;
		}

		public float getOreChance() {
			return oreChance;
		}

		public WorldGenMinable getOreGen() {
			return oreGen;
		}
	}

	public static class Structure {
		private final WorldGenerator structureGen;
		private final int chunkChance;

		private Structure(WorldGenerator w, int i) {
			structureGen = w;
			chunkChance = i;
		}

		public WorldGenerator getStructureGen() {
			return structureGen;
		}

		private int getChunkChance() {
			return chunkChance;
		}
	}
}