package got.common.entity.other.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockSkull;
import net.minecraft.world.IBlockAccess;

public class GOTScarecrows {
	private GOTScarecrows() {
	}

	public static boolean anyScarecrowsNearby(IBlockAccess world, int i, int j, int k) {
		int RANGE = 16;
		for (int i1 = i - RANGE; i1 <= i + RANGE; ++i1) {
			for (int k1 = k - RANGE; k1 <= k + RANGE; ++k1) {
				int y_RANGE = 8;
				for (int j1 = j - y_RANGE; j1 <= j + y_RANGE; ++j1) {
					if (!isScarecrowBase(world, i1, j1, k1)) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isScarecrowBase(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		if (block instanceof BlockFence) {
			for (int j1 = j + 2; j1 <= j + 6; ++j1) {
				Block above = world.getBlock(i, j1, k);
				if (!(above instanceof BlockPumpkin) && !(above instanceof BlockSkull) || !world.getBlock(i, j1 - 1, k).isNormalCube()) {
					continue;
				}
				return true;
			}
		}
		return false;
	}
}