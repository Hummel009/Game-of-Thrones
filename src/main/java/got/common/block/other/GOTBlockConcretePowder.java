package got.common.block.other;

import java.util.Random;

import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import got.common.entity.other.GOTEntityFallingConcrete;
import got.common.util.GOTEnumDyeColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class GOTBlockConcretePowder extends BlockFalling {
	public GOTEnumDyeColor color;

	public GOTBlockConcretePowder(GOTEnumDyeColor color) {
		this.color = color;
		setBlockName("concrete_powder_" + this.color);
		setStepSound(soundTypeSand);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(0.5f);
		setBlockTextureName("got:concrete_powder_" + this.color.getName());
	}

	public void func_149830_m(World world, int i, int j, int k) {
		if (BlockFalling.func_149831_e(world, i, j - 1, k) && j >= 0) {
			int b0 = 32;
			if (!fallInstantly && world.checkChunksExist(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
				if (!world.isRemote) {
					GOTEntityFallingConcrete entityfallingblock = new GOTEntityFallingConcrete(world, i + 0.5f, j + 0.5f, k + 0.5f, this, world.getBlockMetadata(i, j, k));
					func_149829_a(entityfallingblock);
					world.spawnEntityInWorld(entityfallingblock);
				}
			} else {
				world.setBlockToAir(i, j, k);
				while (BlockFalling.func_149831_e(world, i, j - 1, k) && j > 0) {
					--j;
				}
				if (j > 0) {
					world.setBlock(i, j, k, this);
				}
			}
		}
	}

	public GOTEnumDyeColor getColor() {
		return color;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if (world.getBlock(i + 1, j, k).getMaterial() == Material.water || world.getBlock(i - 1, j, k).getMaterial() == Material.water || world.getBlock(i, j + 1, k).getMaterial() == Material.water || world.getBlock(i, j - 1, k).getMaterial() == Material.water || world.getBlock(i, j, k + 1).getMaterial() == Material.water || world.getBlock(i, j, k - 1).getMaterial() == Material.water) {
			world.setBlock(i, j, k, getConcreteFromColor(color));
		} else {
			world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (world.getBlock(i + 1, j, k).getMaterial() == Material.water || world.getBlock(i - 1, j, k).getMaterial() == Material.water || world.getBlock(i, j + 1, k).getMaterial() == Material.water || world.getBlock(i, j - 1, k).getMaterial() == Material.water || world.getBlock(i, j, k + 1).getMaterial() == Material.water || world.getBlock(i, j, k - 1).getMaterial() == Material.water) {
			world.setBlock(i, j, k, getConcreteFromColor(color));
		} else {
			world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand) {
		if (!world.isRemote) {
			func_149830_m(world, i, j, k);
		}
	}

	public static GOTBlockConcrete getConcreteFromColor(GOTEnumDyeColor dye) {
		return GOTBlocks.concrete.get(dye);
	}
}
