package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class GOTBiomeYeen extends GOTBiomeSothoryosJungle {
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(42956029606L), 1);
	private static final NoiseGeneratorPerlin NOISE_GRAVEL = new NoiseGeneratorPerlin(new Random(7185609602367L), 1);
	private static final NoiseGeneratorPerlin NOISE_OBSIDIAN_GRAVEL = new NoiseGeneratorPerlin(new Random(12480634985056L), 1);

	public GOTBiomeYeen(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();

		decorator.setTreesPerChunk(2);

		biomeColors.setFoggy(true);

		biomeAchievement = GOTAchievement.enterYeen;
		enableRiver = false;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = NOISE_DIRT.func_151601_a(i * 0.09, k * 0.09);
		double d2 = NOISE_DIRT.func_151601_a(i * 0.6, k * 0.6);
		double d3 = NOISE_GRAVEL.func_151601_a(i * 0.09, k * 0.09);
		double d4 = NOISE_GRAVEL.func_151601_a(i * 0.6, k * 0.6);
		double d5 = NOISE_OBSIDIAN_GRAVEL.func_151601_a(i * 0.09, k * 0.09);
		if (d5 + NOISE_OBSIDIAN_GRAVEL.func_151601_a(i * 0.6, k * 0.6) > 0.5) {
			topBlock = GOTBlocks.obsidianGravel;
			topBlockMeta = 0;
		} else if (d3 + d4 > 0.6) {
			topBlock = Blocks.gravel;
			topBlockMeta = 0;
		} else if (d1 + d2 > 0.6) {
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