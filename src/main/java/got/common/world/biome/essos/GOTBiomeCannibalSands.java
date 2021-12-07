package got.common.world.biome.essos;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeCannibalSands extends GOTBiome {
	public static NoiseGeneratorPerlin noiseAridGrass = new NoiseGeneratorPerlin(new Random(62926025827260L), 1);
	public NoiseGeneratorPerlin noiseQuicksand = new NoiseGeneratorPerlin(new Random(42956029606L), 1);
	public NoiseGeneratorPerlin noiseQuicksand2 = new NoiseGeneratorPerlin(new Random(7185609602367L), 1);
	public WorldGenerator boulderGen = new GOTWorldGenBoulder(Blocks.stone, 0, 1, 3);
	public WorldGenerator boulderGenSandstone = new GOTWorldGenBoulder(Blocks.sandstone, 0, 1, 3);

	public GOTBiomeCannibalSands(int i, boolean major) {
		super(i, major);
		setDisableRain();
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sandstone;
		spawnableCreatureList.clear();
		spawnableGOTAmbientList.clear();
		this.addBiomeVariant(GOTBiomeVariant.STEPPE);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.addOre(new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
		decorator.grassPerChunk = 0;
		decorator.doubleGrassPerChunk = 0;
		decorator.cactiPerChunk = 0;
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int k12;
		int preGrasses;
		int i1;
		int j1;
		int j12;
		int l;
		int i12;
		int grasses = preGrasses = decorator.grassPerChunk;
		double d1 = noiseAridGrass.func_151601_a(i * 0.002, k * 0.002);
		if (d1 > 0.5) {
			++grasses;
		}
		decorator.grassPerChunk = grasses;
		super.decorate(world, random, i, k);
		decorator.grassPerChunk = preGrasses;
		if (random.nextInt(50) == 0) {
			i12 = i + random.nextInt(16) + 8;
			k12 = k + random.nextInt(16) + 8;
			j12 = world.getHeightValue(i12, k12);
			new WorldGenCactus().generate(world, random, i12, j12, k12);
		}
		if (random.nextInt(16) == 0) {
			i12 = i + random.nextInt(16) + 8;
			k12 = k + random.nextInt(16) + 8;
			j12 = world.getHeightValue(i12, k12);
			new WorldGenDeadBush(Blocks.deadbush).generate(world, random, i12, j12, k12);
		}
		if (random.nextInt(120) == 0) {
			int boulders = 1 + random.nextInt(4);
			for (l = 0; l < boulders; ++l) {
				i1 = i + random.nextInt(16) + 8;
				k1 = k + random.nextInt(16) + 8;
				j1 = world.getHeightValue(i1, k1);
				if (random.nextBoolean()) {
					boulderGen.generate(world, random, i1, j1, k1);
					continue;
				}
				boulderGenSandstone.generate(world, random, i1, j1, k1);
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = noiseQuicksand.func_151601_a(i * 0.09, k * 0.09);
		double d2 = noiseQuicksand.func_151601_a(i * 0.6, k * 0.6);
		double d3 = noiseQuicksand2.func_151601_a(i * 0.09, k * 0.09);
		double d4 = noiseQuicksand2.func_151601_a(i * 0.6, k * 0.6);
		if (d1 + d2 > 0.5 || d3 + d4 > 0.6) {
			topBlock = GOTRegistry.quicksand;
			topBlockMeta = 0;
			fillerBlock = GOTRegistry.quicksand;
			fillerBlockMeta = 0;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_CANNIBAL_SANDS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("deathDesert");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ASSHAI;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		return new GOTBiome.GrassBlockAndMeta(GOTRegistry.aridGrass, 0);
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
