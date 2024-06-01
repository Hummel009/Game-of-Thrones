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

public class GOTBlockSlab10 extends GOTBlockSlabBase {
	public GOTBlockSlab10(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		j1 &= 7;
		switch (j1) {
			case 0:
				return GOTBlocks.pillar2.getIcon(i, 5);
			case 1:
				return GOTBlocks.brick5.getIcon(i, 4);
			case 2:
				return GOTBlocks.brick5.getIcon(i, 5);
			case 3:
				return GOTBlocks.brick5.getIcon(i, 7);
			case 4:
				return GOTBlocks.pillar2.getIcon(i, 6);
			case 5:
				return GOTBlocks.pillar2.getIcon(i, 7);
			case 6:
				return GOTBlocks.whiteSandstone.getIcon(i, 0);
			case 7:
				return GOTBlocks.rock.getIcon(i, 0);
			default:
				return null;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 6));
		list.add(new ItemStack(item, 1, 7));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}