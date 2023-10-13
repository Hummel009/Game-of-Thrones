package got.common.block.slab;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTBlockSlab12 extends GOTBlockSlabBase {
	public GOTBlockSlab12(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
		case 0:
			return GOTBlocks.brick5.getIcon(i, 11);
		case 1:
			return GOTBlocks.brick5.getIcon(i, 13);
		case 2:
			return GOTBlocks.brick5.getIcon(i, 14);
		case 3:
			return GOTBlocks.brick5.getIcon(i, 15);
		case 4:
			return GOTBlocks.pillar2.getIcon(i, 8);
		case 5:
			return GOTBlocks.brick6.getIcon(i, 1);
		case 6:
			return GOTBlocks.pillar2.getIcon(i, 9);
		case 7:
			return GOTBlocks.brick6.getIcon(i, 10);
		default:
			break;
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
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 6));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
