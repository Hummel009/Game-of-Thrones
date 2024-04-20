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

public class GOTBlockSlab6 extends GOTBlockSlabBase {
	public GOTBlockSlab6(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		j1 &= 7;
		switch (j1) {
			case 0:
				return GOTBlocks.brick6.getIcon(i, 3);
			case 1:
				return GOTBlocks.brick6.getIcon(i, 4);
			case 2:
				return GOTBlocks.brick6.getIcon(i, 6);
			case 3:
				return GOTBlocks.brick6.getIcon(i, 7);
			case 4:
				return GOTBlocks.pillar2.getIcon(i, 10);
			case 5:
				return GOTBlocks.pillar2.getIcon(i, 11);
			case 6:
				return GOTBlocks.pillar2.getIcon(i, 12);
			default:
				return GOTBlocks.pillar2.getIcon(i, 13);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 6));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}