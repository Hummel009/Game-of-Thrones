package got.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class GOTWorldGenShrub extends WorldGenTrees {
	public Block woodBlock;
	public int woodMeta;
	public Block leafBlock;
	public int leafMeta;

	public GOTWorldGenShrub(Block w1, int w2, Block l1, int l2) {
		super(false);
		woodBlock = w1;
		woodMeta = w2;
		leafBlock = l1;
		leafMeta = l2;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		Block block;
		--j;
		while (((block = world.getBlock(i, j, k)).isLeaves(world, i, j, k) || block.isAir(world, i, j, k)) && j > 0) {
			--j;
		}
		Block below = world.getBlock(i, j, k);
		if (below.canSustainPlant(world, i, j, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			j++;
			setBlockAndNotifyAdequately(world, i, j, k, woodBlock, woodMeta);
			for (int j1 = j; j1 <= j + 2; ++j1) {
				int j2 = j1 - j;
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
