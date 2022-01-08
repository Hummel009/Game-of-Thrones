package got.common.world.biome.sothoryos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.entity.animal.*;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureStoneRuin;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosVillage;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeSothoryosKanuka extends GOTBiome {
	public static NoiseGeneratorPerlin noisePaths1 = new NoiseGeneratorPerlin(new Random(22L), 1);
	public static NoiseGeneratorPerlin noisePaths2 = new NoiseGeneratorPerlin(new Random(11L), 1);
	public static int FOREST_HEIGHT = 75;

	public GOTBiomeSothoryosKanuka(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityZebra.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRhino.class, 2, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGemsbok.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityElephant.class, 2, 1, 1));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 5, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 8, 1, 2));
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.KANUKA, 100);
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.treesPerChunk = 10;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 4;
		decorator.doubleGrassPerChunk = 1;
        decorator.cornPerChunk = 10;
		SpawnListContainer[] c = new SpawnListContainer[1];
		c[0] = GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c);
		decorator.addVillage(new GOTStructureSothoryosVillage(this, 1.0f));
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		decorator.addRandomStructure(new GOTStructureStoneRuin.SOTHORYOS(1, 4), 400);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		GOTBiomeVariant variant = ((GOTWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
		int grasses = 12;
		grasses = Math.round(grasses * variant.grassFactor);
		for (int l = 0; l < grasses; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			if (world.getHeightValue(i1, k1) <= 75) {
				continue;
			}
			WorldGenerator grassGen = getRandomWorldGenForGrass(random);
			grassGen.generate(world, random, i1, j1, k1);
		}
		int doubleGrasses = 4;
		doubleGrasses = Math.round(doubleGrasses * variant.grassFactor);
		for (int l = 0; l < doubleGrasses; ++l) {
			int i1 = i + random.nextInt(16) + 8;
			int j1 = random.nextInt(128);
			int k1 = k + random.nextInt(16) + 8;
			if (world.getHeightValue(i1, k1) <= 75) {
				continue;
			}
			WorldGenerator grassGen = getRandomWorldGenForDoubleGrass();
			grassGen.generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		double d1 = noisePaths1.func_151601_a(i * 0.008, k * 0.008);
		double d2 = noisePaths2.func_151601_a(i * 0.008, k * 0.008);
		if (d1 > 0.0 && d1 < 0.1 || d2 > 0.0 && d2 < 0.1) {
			topBlock = GOTRegistry.dirtPath;
			topBlockMeta = 1;
		}
		enablePodzol = height > 75;
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		enablePodzol = false;
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SOTHORYOS_KANUKA;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("sothoryosKanuka");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.SOTHORYOS;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public GOTBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		if (random.nextInt(5) != 0) {
			return new GOTBiome.GrassBlockAndMeta(Blocks.tallgrass, 2);
		}
		return super.getRandomGrass(random);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleGrass() {
		WorldGenDoublePlant generator = new WorldGenDoublePlant();
		generator.func_150548_a(3);
		return generator;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.SOTHORYOS;
	}
}
