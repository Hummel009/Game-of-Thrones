package got.common.block.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockYiTiFlower extends GOTBlockFlower {
	public static String[] flowerNames = { "chrys_blue", "chrys_orange", "chrys_pink", "chrys_yellow", "chrys_white" };
	@SideOnly(value = Side.CLIENT)
	public IIcon[] flowerIcons;

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= flowerNames.length) {
			j = 0;
		}
		return flowerIcons[j];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < flowerNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		flowerIcons = new IIcon[flowerNames.length];
		for (int i = 0; i < flowerNames.length; ++i) {
			flowerIcons[i] = iconregister.registerIcon(getTextureName() + "_" + flowerNames[i]);
		}
	}
}
