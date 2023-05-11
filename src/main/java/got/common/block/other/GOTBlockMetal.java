package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.client.render.other.GOTConnectedTextures;
import got.common.block.GOTConnectedBlock;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

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
		return textureName + "_" + oreStorageNames[meta];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 4) {
			return GOTConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
		}
		return super.getIcon(world, i, j, k, side);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 4) {
			return GOTConnectedTextures.getConnectedIconItem(this, 4);
		}
		return super.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		oreStorageIcons = new IIcon[oreStorageNames.length];
		for (int i = 0; i < oreStorageNames.length; ++i) {
			if (i == 4) {
				GOTConnectedTextures.registerConnectedIcons(iconregister, this, 4, false);
				continue;
			}
			oreStorageIcons[i] = iconregister.registerIcon(getTextureName() + "_" + oreStorageNames[i]);
		}
	}
}
