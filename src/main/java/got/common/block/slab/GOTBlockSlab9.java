package got.common.block.slab;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockSlab9 extends GOTBlockSlabBase {
	public GOTBlockSlab9(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
		case 0:
			return GOTRegistry.brick4.getIcon(i, 15);
		case 1:
			return GOTRegistry.pillar2.getIcon(i, 1);
		case 2:
			return GOTRegistry.pillar2.getIcon(i, 2);
		case 3:
			return GOTRegistry.pillar2.getIcon(i, 3);
		case 4:
			return GOTRegistry.pillar2.getIcon(i, 4);
		case 5:
			return GOTRegistry.brick5.getIcon(i, 0);
		case 6:
			return GOTRegistry.brick5.getIcon(i, 1);
		case 7:
			return GOTRegistry.brick5.getIcon(i, 2);
		default:
			break;
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
		list.add(new ItemStack(item, 1, 5));
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
