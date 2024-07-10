package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeUlthosDesertCold extends GOTBiomeUlthosBase {
	public GOTBiomeUlthosDesertCold(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sand;

		preseter.setupDesertColdView();
		preseter.setupDesertColdFlora();
		preseter.setupDesertColdFauna();
		preseter.setupDesertColdTrees();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosDesertCold;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SANDY;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDesertNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}
}