package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockBrick1 extends GOTBlockBrickBase {
	private IIcon iconIbbenSide;

	public GOTBlockBrick1() {
		setBrickNames("basalt", "andesite", "andesiteMossy", "andesiteCracked", "rhyolite", "andesiteCarved", "basalt", "basaltCracked", "basalt", "basalt", "basalt", "basalt", "basalt", "basalt", "diorite", "sandstone");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 4 && i != 0 && i != 1) {
			return iconIbbenSide;
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
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 7));
		list.add(new ItemStack(item, 1, 14));
		list.add(new ItemStack(item, 1, 15));
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		brickIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			brickIcons[i] = iconregister.registerIcon(getTextureName() + "_" + brickNames[i]);
			if (i == 4) {
				iconIbbenSide = iconregister.registerIcon(getTextureName() + "_" + brickNames[i] + "_side");
			}
		}
	}
}