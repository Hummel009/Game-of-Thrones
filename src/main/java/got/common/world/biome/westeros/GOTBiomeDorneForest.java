package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntityRedScorpion;
import got.common.entity.other.GOTEntityDarkSkinBandit;
import got.common.entity.other.GOTEntityNPC;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class GOTBiomeDorneForest extends GOTBiomeWesterosBase {
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
	private static final NoiseGeneratorPerlin NOISE_SAND = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
	private static final NoiseGeneratorPerlin NOISE_RED_SAND = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);

	public GOTBiomeDorneForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupSouthernTrees(true);

		addSpawnableMonster(GOTEntityRedScorpion.class, 5, 1, 1);

		setupRuinedStructures(true);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.DORNE;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDorne;
	}

	@Override
	public Class<? extends GOTEntityNPC> getBanditEntityClass() {
		return GOTEntityDarkSkinBandit.class;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = NOISE_DIRT.func_151601_a(i * 0.002, k * 0.002);
		double d2 = NOISE_DIRT.func_151601_a(i * 0.07, k * 0.07);
		double d3 = NOISE_DIRT.func_151601_a(i * 0.25, k * 0.25);
		double d4 = NOISE_SAND.func_151601_a(i * 0.002, k * 0.002);
		double d5 = NOISE_SAND.func_151601_a(i * 0.07, k * 0.07);
		double d6 = NOISE_SAND.func_151601_a(i * 0.25, k * 0.25);
		double d7 = NOISE_RED_SAND.func_151601_a(i * 0.002, k * 0.002);
		if (d7 + NOISE_RED_SAND.func_151601_a(i * 0.07, k * 0.07) + NOISE_RED_SAND.func_151601_a(i * 0.25, k * 0.25) > 0.9) {
			topBlock = Blocks.sand;
			topBlockMeta = 1;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d4 + d5 + d6 > 1.2) {
			topBlock = Blocks.sand;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 + d3 > 0.4) {
			topBlock = Blocks.dirt;
			topBlockMeta = 1;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}
}