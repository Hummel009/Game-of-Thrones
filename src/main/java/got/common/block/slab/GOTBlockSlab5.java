package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockSlab5 extends GOTBlockSlabBase {
	public GOTBlockSlab5(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
			case 0:
				return GOTBlocks.pillar1.getIcon(i, 6);
			case 1:
				return GOTBlocks.pillar1.getIcon(i, 7);
			case 2:
				return GOTBlocks.pillar1.getIcon(i, 8);
			case 3:
				return GOTBlocks.brick2.getIcon(i, 11);
			case 4:
				return GOTBlocks.pillar1.getIcon(i, 9);
			case 5:
				return GOTBlocks.brick3.getIcon(i, 2);
			case 6:
				return GOTBlocks.brick3.getIcon(i, 3);
			case 7:
				return GOTBlocks.brick3.getIcon(i, 4);
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
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
