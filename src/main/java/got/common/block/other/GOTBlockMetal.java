package got.common.block.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.client.render.other.GOTConnectedTextures;
import got.common.block.GOTConnectedBlock;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTBlockMetal extends GOTBlockOreStorageBase implements GOTConnectedBlock {
	public GOTBlockMetal() {
		setOreStorageNames("copper", "tin", "bronze", "silver", "valyrian", "copper", "copper", "copper", "copper", "copper", "copper", "copper", "copper", "sulfur", "saltpeter", "copper");
	}

	@Override
	public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
		return GOTConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
	}

	@Override
	public String getConnectedName(int meta) {
		return textureName + "_" + getOreStorageNames()[meta];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 4) {
			return GOTConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
		}
		return super.getIcon(world, i, j, k, side);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 4) {
			return GOTConnectedTextures.getConnectedIconItem(this, j);
		}
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 13));
		list.add(new ItemStack(item, 1, 14));
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
		return world.getBlockMetadata(i, j, k) == 13 && side == ForgeDirection.UP;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		setOreStorageIcons(new IIcon[getOreStorageNames().length]);
		for (int i = 0; i < getOreStorageNames().length; ++i) {
			if (i == 4) {
				GOTConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
				continue;
			}
			getOreStorageIcons()[i] = iconregister.registerIcon(getTextureName() + "_" + getOreStorageNames()[i]);
		}
	}
}
