package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeSothoryosMountains extends GOTBiomeSothoryosBase implements GOTBiome.Mountains {
	public GOTBiomeSothoryosMountains(int i, boolean major) {
		super(i, major);
		preseter.setupMountainsView();
		preseter.setupMountainsFlora();
		preseter.setupMountainsFauna();
		preseter.setupSouthernTrees(true);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosMountains;
	}

	@Override
	public boolean isEnableRocky() {
		return true;
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
		int snowHeight = 150 - rockDepth;
		int stoneHeight = snowHeight - 40;
		for (int j = ySize - 1; j >= stoneHeight; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (j >= snowHeight && block == topBlock) {
				blocks[index] = Blocks.snow;
				meta[index] = 0;
			} else if (block == topBlock || block == fillerBlock) {
				blocks[index] = Blocks.stone;
				meta[index] = 0;
			}
			block = blocks[index];
			if (block == Blocks.stone) {
				if (random.nextInt(6) == 0) {
					int h = 1 + random.nextInt(6);
					for (int j1 = j; j1 > j - h && j1 > 0; --j1) {
						int indexH = xzIndex * ySize + j1;
						if (blocks[indexH] != Blocks.stone) {
							continue;
						}
						blocks[indexH] = GOTBlocks.rock;
						meta[indexH] = 6;
					}
				} else {
					if (random.nextInt(16) == 0) {
						blocks[index] = GOTBlocks.rock;
						meta[index] = 6;
					}
				}
			}
		}
	}
}