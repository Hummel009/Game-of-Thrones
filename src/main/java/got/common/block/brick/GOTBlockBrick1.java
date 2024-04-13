package got.common.block.brick;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockBrick1 extends GOTBlockBrickBase {
	private IIcon iconIbbenSide;

	public GOTBlockBrick1() {
		brickNames = new String[]{"basalt", "andesite", "andesite_mossy", "andesite_cracked", "rhyolite", "andesite_carved", "basalt", "basalt_cracked", "basalt", "basalt", "basalt", "basalt", "basalt", "basalt", "diorite", "sandstone"};
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 4 && i != 0 && i != 1) {
			return iconIbbenSide;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 7));
		list.add(new ItemStack(item, 1, 14));
		list.add(new ItemStack(item, 1, 15));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		brickIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			brickIcons[i] = iconregister.registerIcon(getTextureName() + '_' + brickNames[i]);
			if (i == 4) {
				iconIbbenSide = iconregister.registerIcon(getTextureName() + '_' + brickNames[4] + "_side");
			}
		}
	}
}