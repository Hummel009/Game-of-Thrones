package got.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class GOTWorldGenShrub extends WorldGenTrees {
	private final Block woodBlock;
	private final int woodMeta;
	private final Block leafBlock;
	private final int leafMeta;

	public GOTWorldGenShrub(Block w1, int w2, Block l1, int l2) {
		super(false);
		woodBlock = w1;
		woodMeta = w2;
		leafBlock = l1;
		leafMeta = l2;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		int j3 = j;
		Block block;
		do {
			--j3;
		} while (((block = world.getBlock(i, j3, k)).isLeaves(world, i, j3, k) || block.isAir(world, i, j3, k)) && j3 > 0);
		Block below = world.getBlock(i, j3, k);
		if (below.canSustainPlant(world, i, j3, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			j3++;
			setBlockAndNotifyAdequately(world, i, j3, k, woodBlock, woodMeta);
			for (int j1 = j3; j1 <= j3 + 2; ++j1) {
				int j2 = j1 - j3;
				int range = 2 - j2;
				for (int i1 = i - range; i1 <= i + range; ++i1) {
					for (int k1 = k - range; k1 <= k + range; ++k1) {
						int i2 = i1 - i;
						int k2 = k1 - k;
						if (Math.abs(i2) == range && Math.abs(k2) == range && random.nextInt(2) == 0 || !world.getBlock(i1, j1, k1).canBeReplacedByLeaves(world, i1, j1, k1)) {
							continue;
						}
						setBlockAndNotifyAdequately(world, i1, j1, k1, leafBlock, leafMeta);
					}
				}
			}
		}
		return true;
	}
}