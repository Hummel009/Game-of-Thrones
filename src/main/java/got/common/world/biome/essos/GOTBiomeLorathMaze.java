package got.common.world.biome.essos;

import got.GOT;
import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class GOTBiomeLorathMaze extends GOTBiomeEssosBase {
	private static final NoiseGeneratorPerlin NOISE_PATHS_1 = new NoiseGeneratorPerlin(new Random(22L), 1);
	private static final NoiseGeneratorPerlin NOISE_PATHS_2 = new NoiseGeneratorPerlin(new Random(11L), 1);

	public GOTBiomeLorathMaze(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.stone;
		fillerBlock = Blocks.stone;

		preseter.setupForestFlora();
		preseter.setupMiderateTrees();
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.LORATH;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLorathMaze;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int l;
		super.decorate(world, random, i, k);
		for (l = 0; l < 10; ++l) {
			Block block = Blocks.stone;
			for (int l2 = 0; l2 < 10; ++l2) {
				int k3;
				int j1;
				int i3 = i + random.nextInt(16) + 8;
				if (world.getBlock(i3, (j1 = world.getHeightValue(i3, k3 = k + random.nextInt(16) + 8)) - 1, k3) == block) {
					for (int j2 = j1; j2 < 90 && !GOT.isOpaque(world, i3, j2, k3); ++j2) {
						world.setBlock(i3, j2, k3, Blocks.stone, 0, 3);
					}
				}
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		double d1 = NOISE_PATHS_1.func_151601_a(i * 0.008, k * 0.008);
		double d2 = NOISE_PATHS_2.func_151601_a(i * 0.008, k * 0.008);
		if (d1 > 0.0 && d1 < 0.1 || d2 > 0.0 && d2 < 0.1) {
			topBlock = Blocks.grass;
			fillerBlock = Blocks.dirt;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
	}
}