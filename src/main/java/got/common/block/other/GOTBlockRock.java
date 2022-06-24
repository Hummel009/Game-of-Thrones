package got.common.block.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class GOTBlockRock extends Block {
	@SideOnly(value = Side.CLIENT)
	public IIcon[] rockIcons;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconBasaltSide;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconBasaltMoss;
	public String[] rockNames = { "basalt", "andesite", "rhyolite", "diorite", "granite", "chalk", "labradorite" };

	public GOTBlockRock() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(1.5f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 0 && side != 1 && side != 0 && world.getBlock(i, j + 1, k) == GOTRegistry.asshaiMoss) {
			return iconBasaltMoss;
		}
		return super.getIcon(world, i, j, k, side);
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta >= rockNames.length) {
			meta = 0;
		}
		if (meta == 0 && side != 1 && side != 0) {
			return iconBasaltSide;
		}
		return rockIcons[meta];
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < rockNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isReplaceableOreGen(World world, int i, int j, int k, Block target) {
		if (target == this) {
			return world.getBlockMetadata(i, j, k) == 0;
		}
		return false;
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == 0 && random.nextInt(10) == 0) {
			world.spawnParticle("smoke", i + random.nextFloat(), j + 1.1f, k + random.nextFloat(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		rockIcons = new IIcon[rockNames.length];
		for (int i = 0; i < rockNames.length; ++i) {
			String subName = getTextureName() + "_" + rockNames[i];
			rockIcons[i] = iconregister.registerIcon(subName);
			if (i == 0) {
				iconBasaltSide = iconregister.registerIcon(subName + "_side");
				iconBasaltMoss = iconregister.registerIcon(subName + "_moss");
			}
		}
	}
}
