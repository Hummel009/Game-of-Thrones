package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBiomeFrostfangs extends GOTBiomeWesterosBase implements GOTBiome.Mountains {
	public GOTBiomeFrostfangs(int i, boolean major) {
		super(i, major);
		topBlock = Blocks.snow;
		fillerBlock = Blocks.packed_ice;

		preseter.setupFrostView();
		preseter.setupFrostFlora();
		preseter.setupFrostFauna();
		preseter.setupPolarTrees(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.BEYOND_WALL;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterFrostfangs;
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
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
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
			if (j >= snowHeight && block == topBlock || block == topBlock || block == fillerBlock) {
				blocks[index] = Blocks.snow;
				meta[index] = 0;
			}
			block = blocks[index];
			if (block == Blocks.stone) {
				if (random.nextInt(6) == 0) {
					int h = 1 + random.nextInt(6);
					for (int j1 = j; j1 > j - h && j1 > 0; --j1) {
						int indexH = xzIndex * ySize + j1;
						if (blocks[indexH] == Blocks.stone) {
							blocks[indexH] = Blocks.packed_ice;
							meta[indexH] = 0;
						}
					}
				} else {
					if (random.nextInt(16) == 0) {
						blocks[index] = Blocks.packed_ice;
						meta[index] = 0;
					}
				}
			}
		}
	}
}