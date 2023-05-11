package got.common.entity.other;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockSkull;
import net.minecraft.world.World;

public class GOTScarecrows {
	public static int RANGE = 16;
	public static int Y_RANGE = 8;

	public static boolean anyScarecrowsNearby(World world, int i, int j, int k) {
		for (int i1 = i - RANGE; i1 <= i + RANGE; ++i1) {
			for (int k1 = k - RANGE; k1 <= k + RANGE; ++k1) {
				for (int j1 = j - Y_RANGE; j1 <= j + Y_RANGE; ++j1) {
					if (!isScarecrowBase(world, i1, j1, k1)) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isScarecrowBase(World world, int i, int j, int k) {
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
