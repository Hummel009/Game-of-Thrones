package got.common.block.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import got.common.entity.animal.GOTEntityTermite;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class GOTBlockTermite extends Block {
	public GOTBlockTermite() {
		super(Material.ground);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(0.5f);
		setResistance(3.0f);
	}

	@Override
	public ItemStack createStackedBlock(int i) {
		return new ItemStack(this, 1, 1);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 1; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int meta) {
		if (!world.isRemote && meta == 0 && world.rand.nextBoolean()) {
			int termites = 1 + world.rand.nextInt(3);
			for (int l = 0; l < termites; ++l) {
				spawnTermite(world, i, j, k);
			}
		}
	}

	@Override
	public void onBlockExploded(World world, int i, int j, int k, Explosion explosion) {
		int meta = world.getBlockMetadata(i, j, k);
		if (!world.isRemote && meta == 0 && world.rand.nextBoolean()) {
			spawnTermite(world, i, j, k);
		}
		super.onBlockExploded(world, i, j, k, explosion);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return meta == 1 ? 1 : 0;
	}

	private void spawnTermite(World world, int i, int j, int k) {
		GOTEntityTermite termite = new GOTEntityTermite(world);
		termite.setLocationAndAngles(i + 0.5, j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
		world.spawnEntityInWorld(termite);
	}
}
