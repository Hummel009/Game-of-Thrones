package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockStainedGlass extends GOTBlockGlass {
	public IIcon[] glassIcons = new IIcon[16];

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return glassIcons[j % glassIcons.length];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < glassIcons.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		for (int i = 0; i < glassIcons.length; ++i) {
			glassIcons[i] = iconregister.registerIcon(getTextureName() + "_" + ItemDye.field_150921_b[BlockStainedGlass.func_149997_b(i)]);
		}
	}
}
