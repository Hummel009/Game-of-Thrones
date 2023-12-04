package got.common.world.feature;

import got.common.database.GOTBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class GOTWorldGenBanana extends WorldGenAbstractTree {
	public GOTWorldGenBanana(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int height = 2 + random.nextInt(3);
		int[] leaves = new int[4];
		for (int l2 = 0; l2 < 4; ++l2) {
			leaves[l2] = 1 + random.nextInt(3);
		}
		if (j < 1 || j + height + 5 > 256 || !isReplaceable(world, i, j, k)) {
			return false;
		}
		Block below = world.getBlock(i, j - 1, k);
		if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) GOTBlocks.sapling2)) {
			return false;
		}
		int l;
		for (l = 0; l < height + 2; ++l) {
			if (isReplaceable(world, i, j + l, k)) {
				continue;
			}
			return false;
		}
		int l1;
		ForgeDirection dir;
		for (l = 0; l < 4; ++l) {
			dir = ForgeDirection.getOrientation(l + 2);
			for (l1 = -1; l1 < leaves[l]; ++l1) {
				if (isReplaceable(world, i + dir.offsetX, j + height + l1, k + dir.offsetZ)) {
					continue;
				}
				return false;
			}
			for (l1 = -1; l1 < 1; ++l1) {
				if (isReplaceable(world, i + dir.offsetX * 2, j + height + leaves[l] + l1, k + dir.offsetZ * 2)) {
					continue;
				}
				return false;
			}
		}
		for (l = 0; l < height + 2; ++l) {
			setBlockAndNotifyAdequately(world, i, j + l, k, GOTBlocks.wood2, 3);
		}
		for (l = 0; l < 4; ++l) {
			dir = ForgeDirection.getOrientation(l + 2);
			for (l1 = 0; l1 < leaves[l]; ++l1) {
				setBlockAndNotifyAdequately(world, i + dir.offsetX, j + height + l1, k + dir.offsetZ, GOTBlocks.leaves2, 3);
			}
			setBlockAndNotifyAdequately(world, i + dir.getOpposite().offsetX, j + height - 1, k + dir.getOpposite().offsetZ, GOTBlocks.banana, l);
			for (l1 = -1; l1 < 1; ++l1) {
				setBlockAndNotifyAdequately(world, i + dir.offsetX * 2, j + height + leaves[l] + l1, k + dir.offsetZ * 2, GOTBlocks.leaves2, 3);
			}
		}
		world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
		return true;
	}
}
