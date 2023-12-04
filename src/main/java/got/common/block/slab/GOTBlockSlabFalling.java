package got.common.block.slab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTBlockSlabFalling extends GOTBlockSlabBase {
	protected GOTBlockSlabFalling(boolean flag, Material material, int n) {
		super(flag, material, n);
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
	}

	@Override
	public int tickRate(World world) {
		return 2;
	}

	public void tryBlockFall(World world, int i, int j, int k) {
		int j1 = j;
		if (BlockFalling.func_149831_e(world, i, j1 - 1, k) && j1 >= 0) {
			int range = 32;
			if (!BlockFalling.fallInstantly && world.checkChunksExist(i - range, j1 - range, k - range, i + range, j1 + range, k + range)) {
				if (!world.isRemote) {
					EntityFallingBlock fallingBlock = new EntityFallingBlock(world, i + 0.5f, j1 + 0.5f, k + 0.5f, this, world.getBlockMetadata(i, j1, k) & 7);
					world.spawnEntityInWorld(fallingBlock);
				}
			} else {
				world.setBlockToAir(i, j1, k);
				while (BlockFalling.func_149831_e(world, i, j1 - 1, k) && j1 > 0) {
					--j1;
				}
				if (j1 > 0) {
					world.setBlock(i, j1, k, this);
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		if (!world.isRemote) {
			tryBlockFall(world, i, j, k);
		}
	}
}
