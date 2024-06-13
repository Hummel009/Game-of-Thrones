package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import got.common.entity.other.inanimate.GOTEntityFallingConcretePowder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GOTBlockConcretePowder extends BlockFalling {
	@SideOnly(Side.CLIENT)
	private IIcon[] concretePowderIcons;

	public GOTBlockConcretePowder() {
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(0.5f);
		setStepSound(soundTypeSand);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= 16) {
			j1 = 0;
		}
		return concretePowderIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		concretePowderIcons = new IIcon[16];
		for (int i = 0; i < concretePowderIcons.length; ++i) {
			int dyeMeta = BlockColored.func_150031_c(i);
			if ("lightBlue".equals(ItemDye.field_150923_a[dyeMeta])) {
				concretePowderIcons[i] = iconregister.registerIcon(getTextureName() + "_light_blue");
			} else {
				concretePowderIcons[i] = iconregister.registerIcon(getTextureName() + '_' + ItemDye.field_150923_a[dyeMeta]);
			}
		}
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);

		if (world.getBlock(i + 1, j, k).getMaterial() == Material.water || world.getBlock(i - 1, j, k).getMaterial() == Material.water || world.getBlock(i, j + 1, k).getMaterial() == Material.water || world.getBlock(i, j, k + 1).getMaterial() == Material.water || world.getBlock(i, j, k - 1).getMaterial() == Material.water) {
			world.setBlock(i, j, k, GOTBlocks.concrete, meta, 3);
		} else {
			world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		int meta = world.getBlockMetadata(i, j, k);

		if (world.getBlock(i + 1, j, k).getMaterial() == Material.water || world.getBlock(i - 1, j, k).getMaterial() == Material.water || world.getBlock(i, j + 1, k).getMaterial() == Material.water || world.getBlock(i, j, k + 1).getMaterial() == Material.water || world.getBlock(i, j, k - 1).getMaterial() == Material.water) {
			world.setBlock(i, j, k, GOTBlocks.concrete, meta, 3);
		} else {
			world.scheduleBlockUpdate(i, j, k, this, tickRate(world));
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand) {
		if (!world.isRemote) {
			int j1 = j;
			if (func_149831_e(world, i, j1 - 1, k) && j1 >= 0) {
				int b0 = 32;
				if (!fallInstantly && world.checkChunksExist(i - b0, j1 - b0, k - b0, i + b0, j1 + b0, k + b0)) {
					if (!world.isRemote) {
						GOTEntityFallingConcretePowder fallingConcrete = new GOTEntityFallingConcretePowder(world, i + 0.5f, j1 + 0.5f, k + 0.5f, this, world.getBlockMetadata(i, j1, k));
						func_149829_a(fallingConcrete);
						world.spawnEntityInWorld(fallingConcrete);
					}
				} else {
					world.setBlockToAir(i, j1, k);
					while (func_149831_e(world, i, j1 - 1, k) && j1 > 0) {
						--j1;
					}
					if (j1 > 0) {
						world.setBlock(i, j1, k, this);
					}
				}
			}
		}
	}
}