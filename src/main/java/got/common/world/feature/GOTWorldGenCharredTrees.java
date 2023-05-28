package got.common.world.feature;

import got.common.database.GOTBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class GOTWorldGenCharredTrees extends WorldGenAbstractTree {
	public GOTWorldGenCharredTrees() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		world.getBlockMetadata(i, j - 1, k);
		if (below != Blocks.stone && below != Blocks.sand && below != Blocks.gravel && !below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (BlockSapling) Blocks.sapling)) {
			return false;
		}
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
		int height = 2 + random.nextInt(5);
		for (int j1 = j; j1 < j + height; ++j1) {
			world.setBlock(i, j1, k, GOTBlocks.wood1, 3, 2);
		}
		if (height >= 4) {
			for (int branch = 0; branch < 4; ++branch) {
				int branchLength = 2 + random.nextInt(4);
				int branchHorizontalPos = 0;
				int branchVerticalPos = j + height - random.nextInt(2);
				for (int l = 0; l < branchLength; ++l) {
					if (random.nextInt(4) == 0) {
						++branchHorizontalPos;
					}
					if (random.nextInt(3) != 0) {
						++branchVerticalPos;
					}
					switch (branch) {
						case 0: {
							world.setBlock(i - branchHorizontalPos, branchVerticalPos, k, GOTBlocks.wood1, 15, 2);
							continue;
						}
						case 1: {
							world.setBlock(i, branchVerticalPos, k + branchHorizontalPos, GOTBlocks.wood1, 15, 2);
							continue;
						}
						case 2: {
							world.setBlock(i + branchHorizontalPos, branchVerticalPos, k, GOTBlocks.wood1, 15, 2);
							continue;
						}
						case 3: {
							world.setBlock(i, branchVerticalPos, k - branchHorizontalPos, GOTBlocks.wood1, 15, 2);
						}
					}
				}
			}
		}
		return true;
	}
}
