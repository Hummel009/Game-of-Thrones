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

public class GOTWorldGenIbbenPine extends WorldGenAbstractTree {
	public Block woodBlock = GOTBlocks.wood1;
	public int woodMeta;
	public Block leafBlock = GOTBlocks.leaves1;
	public int leafMeta;
	public int minHeight = 12;
	public int maxHeight = 24;

	public GOTWorldGenIbbenPine(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block below;
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
		setBlockAndNotifyAdequately(world, i, j + height, k, leafBlock, leafMeta);
		generateLeafLayer(world, random, i, j + height - 1, k, 1);
		int leafHeight = j + height - 3;
		int minLeafHeight = j + (int) (height * 0.5f);
		while (leafHeight > minLeafHeight) {
			int r = random.nextInt(3);
			switch (r) {
				case 0:
					generateLeafLayer(world, random, i, leafHeight, k, 1);
					leafHeight -= 2;
					continue;
				case 1:
					leafHeight--;
					generateLeafLayer(world, random, i, leafHeight + 1, k, 1);
					generateLeafLayer(world, random, i, leafHeight, k, 2);
					generateLeafLayer(world, random, i, leafHeight - 1, k, 1);
					leafHeight -= 3;
					continue;
				default:
					leafHeight--;
					generateLeafLayer(world, random, i, leafHeight + 1, k, 2);
					generateLeafLayer(world, random, i, leafHeight, k, 3);
					generateLeafLayer(world, random, i, leafHeight - 1, k, 2);
					leafHeight -= 3;
			}
		}
		generateLeafLayer(world, random, i, leafHeight, k, 1);
		int lastDir = -1;
		for (int j1 = j; j1 < j + height; ++j1) {
			int i1;
			int dir;
			int k1;
			setBlockAndNotifyAdequately(world, i, j1, k, woodBlock, woodMeta);
			if (j1 < j + 3 || j1 >= minLeafHeight || random.nextInt(3) != 0 || (dir = random.nextInt(4)) == lastDir) {
				continue;
			}
			lastDir = dir;
			int length = 1;
			for (int l = 1; l <= length && isReplaceable(world, i1 = i + Direction.offsetX[dir] * l, j1, k1 = k + Direction.offsetZ[dir] * l); ++l) {
				if (dir == 0 || dir == 2) {
					setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 8);
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j1, k1, woodBlock, woodMeta | 4);
			}
		}
		return true;
	}

	public void generateLeafLayer(World world, Random random, int i, int j, int k, int range) {
		for (int i1 = i - range; i1 <= i + range; ++i1) {
			for (int k1 = k - range; k1 <= k + range; ++k1) {
				Block block;
				int i2 = Math.abs(i1 - i);
				if (i2 + Math.abs(k1 - k) > range || !(block = world.getBlock(i1, j, k1)).isReplaceable(world, i1, j, k1) && !block.isLeaves(world, i1, j, k1)) {
					continue;
				}
				setBlockAndNotifyAdequately(world, i1, j, k1, leafBlock, leafMeta);
			}
		}
	}

}
