package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockYiTiFlower extends GOTBlockFlower {
	private static final String[] FLOWER_NAMES = {"chrys_blue", "chrys_orange", "chrys_pink", "chrys_yellow", "chrys_white"};

	@SideOnly(Side.CLIENT)
	private IIcon[] flowerIcons;

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= FLOWER_NAMES.length) {
			j1 = 0;
		}
		return flowerIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < FLOWER_NAMES.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		flowerIcons = new IIcon[FLOWER_NAMES.length];
		for (int i = 0; i < FLOWER_NAMES.length; ++i) {
			flowerIcons[i] = iconregister.registerIcon(getTextureName() + '_' + FLOWER_NAMES[i]);
		}
	}
}