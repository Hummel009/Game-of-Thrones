package got.common.block.slab;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockSlab4 extends GOTBlockSlabBase {
	public GOTBlockSlab4(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
		case 0:
			return GOTRegistry.brick1.getIcon(i, 15);
		case 1:
			return GOTRegistry.brick2.getIcon(i, 3);
		case 2:
			return GOTRegistry.brick2.getIcon(i, 4);
		case 3:
			return GOTRegistry.brick2.getIcon(i, 5);
		case 4:
			return GOTRegistry.brick2.getIcon(i, 7);
		case 5:
			return GOTRegistry.brick2.getIcon(i, 8);
		case 6:
			return GOTRegistry.brick2.getIcon(i, 9);
		case 7:
			return GOTRegistry.pillar1.getIcon(i, 5);
		default:
			break;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 7));
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
