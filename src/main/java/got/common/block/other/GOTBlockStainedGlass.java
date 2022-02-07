package got.common.block.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockStainedGlass extends GOTBlockGlass {
	private IIcon[] glassIcons = new IIcon[16];

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return glassIcons[j % glassIcons.length];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < glassIcons.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		for (int i = 0; i < glassIcons.length; ++i) {
			glassIcons[i] = iconregister.registerIcon(getTextureName() + "_" + ItemDye.field_150921_b[BlockStainedGlass.func_149997_b(i)]);
		}
	}
}
