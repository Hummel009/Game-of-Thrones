package got.common.world.biome.essos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeShadowMountains extends GOTBiome {
	public static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(389502092662L), 1);
	public static NoiseGeneratorPerlin noiseGravel = new NoiseGeneratorPerlin(new Random(1379468206L), 1);

	public GOTBiomeShadowMountains(int i, boolean major) {
		super(i, major);
		topBlock = GOTRegistry.rock;
		topBlockMeta = 0;
		fillerBlock = GOTRegistry.rock;
		fillerBlockMeta = 0;
		spawnableGOTAmbientList.clear();
		spawnableCreatureList.clear();
		addBiomeVariant(GOTBiomeVariant.MOUNTAIN);
		decorator.biomeOreFactor = 2.0f;
		decorator.biomeGemFactor = 1.5f;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreCobalt, 5), 5.0f, 0, 32);
		decorator.clearTrees();
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 1;
		decorator.sandPerChunk = 0;
		decorator.clayPerChunk = 0;
		decorator.dryReedChance = 1.0f;
		enableRocky = false;
		decorator.addTree(GOTTreeType.CHARRED, 1000);
		biomeColors.setSky(0);
		biomeColors.setClouds(0);
		biomeColors.setFog(0);
		biomeColors.setWater(0);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int j1;
		int i1;
		int k1;
		super.decorate(world, random, i, k);
		int l;
		int k12;
		int i12;
		int j12;
		if (random.nextInt(60) == 0) {
			for (l = 0; l < 8; ++l) {
				i12 = i + random.nextInt(16) + 8;
				k12 = k + random.nextInt(16) + 8;
				j12 = world.getHeightValue(i12, k12);
				decorator.genTree(world, random, i12, j12, k12);
			}
		}
		if (decorator.grassPerChunk > 0) {
			if (random.nextInt(20) == 0) {
				for (l = 0; l < 6; ++l) {
					i12 = i + random.nextInt(6) + 8;
					if (!world.isAirBlock(i12, j12 = world.getHeightValue(i12, k12 = k + random.nextInt(6) + 8), k12) || !GOTRegistry.asshaiThorn.canBlockStay(world, i12, j12, k12)) {
						continue;
					}
					world.setBlock(i12, j12, k12, GOTRegistry.asshaiThorn, 0, 2);
				}
			}
			if (random.nextInt(20) == 0 && world.isAirBlock(i1 = i + random.nextInt(16) + 8, j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8), k1) && GOTRegistry.asshaiMoss.canBlockStay(world, i1, j1, k1)) {
				new GOTWorldGenAsshaiMoss().generate(world, random, i1, j1, k1);
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseDirt.func_151601_a(i * 0.08, k * 0.08);
		double d2 = noiseDirt.func_151601_a(i * 0.4, k * 0.4);
		double d3 = noiseGravel.func_151601_a(i * 0.08, k * 0.08);
		if (d3 + (noiseGravel.func_151601_a(i * 0.4, k * 0.4)) > 0.8) {
			topBlock = GOTRegistry.basaltGravel;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 > 0.5) {
			topBlock = GOTRegistry.asshaiDirt;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
		for (int j = ySize - 1; j >= 0; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (block != Blocks.stone) {
				continue;
			}
			blocks[index] = GOTRegistry.rock;
			meta[index] = 0;
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SHADOW_MOUNTAINS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("shadowMountains");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ASSHAI;
	}

	@Override
	public GrassBlockAndMeta getRandomGrass(Random random) {
		return new GrassBlockAndMeta(GOTRegistry.asshaiGrass, 0);
	}

	public static boolean isBasalt(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		return block == GOTRegistry.rock && meta == 0 || block == GOTRegistry.asshaiDirt || block == GOTRegistry.basaltGravel;
	}
}
