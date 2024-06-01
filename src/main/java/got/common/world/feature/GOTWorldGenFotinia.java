package got.common.world.feature;

import got.common.database.GOTBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class GOTWorldGenFotinia extends WorldGenAbstractTree {
	private static final Block WOOD_BLOCK = GOTBlocks.wood7;
	private static final Block LEAF_BLOCK = GOTBlocks.leaves7;
	private static final int LEAF_META = 2;

	public GOTWorldGenFotinia(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below;
		int maxHeight = 24;
		int minHeight = 12;
		int height = MathHelper.getRandomIntegerInRange(random, minHeight, maxHeight);
		boolean flag = true;
		if (j >= 1 && height + 1 <= 256) {
			for (int j1 = j; j1 <= j + height + 1; ++j1) {
				int range = 1;
				if (j1 == j) {
					range = 0;
				}
				if (j1 >= j + height - 1) {
					range = 2;
				}
				for (int i1 = i - range; i1 <= i + range && flag; ++i1) {
					for (int k1 = k - range; k1 <= k + range && flag; ++k1) {
						if (j1 >= 0 && j1 < 256 && isReplaceable(world, i1, j1, k1)) {
							continue;
						}
						flag = false;
					}
				}
			}
		} else {
			flag = false;
		}
		if (!(below = world.getBlock(i, j - 1, k)).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			flag = false;
		}
		if (!flag) {
			return false;
		}
		below.onPlantGrow(world, i, j - 1, k, i, j, k);
		setBlockAndNotifyAdequately(world, i, j + height, k, LEAF_BLOCK, LEAF_META);
		generateLeafLayer(world, i, j + height - 1, k, 1);
		int leafHeight = j + height - 3;
		int minLeafHeight = j + (int) (height * 0.5f);
		while (leafHeight > minLeafHeight) {
			int r = random.nextInt(3);
			switch (r) {
				case 0:
					generateLeafLayer(world, i, leafHeight, k, 1);
					leafHeight -= 2;
					continue;
				case 1:
					leafHeight--;
					generateLeafLayer(world, i, leafHeight + 1, k, 1);
					generateLeafLayer(world, i, leafHeight, k, 2);
					generateLeafLayer(world, i, leafHeight - 1, k, 1);
					leafHeight -= 3;
					continue;
				case 2:
					leafHeight--;
					generateLeafLayer(world, i, leafHeight + 1, k, 2);
					generateLeafLayer(world, i, leafHeight, k, 3);
					generateLeafLayer(world, i, leafHeight - 1, k, 2);
					leafHeight -= 3;
			}
		}
		generateLeafLayer(world, i, leafHeight, k, 1);
		int lastDir = -1;
		for (int j1 = j; j1 < j + height; ++j1) {
			int i1;
			int dir;
			int k1;
			int woodMeta = 2;
			setBlockAndNotifyAdequately(world, i, j1, k, WOOD_BLOCK, woodMeta);
			if (j1 < j + 3 || j1 >= minLeafHeight || random.nextInt(3) != 0 || (dir = random.nextInt(4)) == lastDir) {
				continue;
			}
			lastDir = dir;
			int length = 1;
			for (int l = 1; l <= length && isReplaceable(world, i1 = i + Direction.offsetX[dir] * l, j1, k1 = k + Direction.offsetZ[dir] * l); ++l) {
				if (dir == 0 || dir == 2) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, WOOD_BLOCK, woodMeta | 8);
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j1, k1, WOOD_BLOCK, woodMeta | 4);
			}
		}
		return true;
	}

	private void generateLeafLayer(World world, int i, int j, int k, int range) {
		for (int i1 = i - range; i1 <= i + range; ++i1) {
			for (int k1 = k - range; k1 <= k + range; ++k1) {
				Block block;
				int i2 = Math.abs(i1 - i);
				if (i2 + Math.abs(k1 - k) > range || !(block = world.getBlock(i1, j, k1)).isReplaceable(world, i1, j, k1) && !block.isLeaves(world, i1, j, k1)) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j, k1, LEAF_BLOCK, LEAF_META);
			}
		}
	}
}