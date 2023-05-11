package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTRegistry;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockStainedGlassPane extends GOTBlockGlassPane {
	public IIcon[] paneIcons = new IIcon[16];

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon func_149735_b(int i, int j) {
		return paneIcons[j % paneIcons.length];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon func_150097_e() {
		return ((GOTBlockPane) GOTRegistry.glassPane).func_150097_e();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return func_149735_b(i, ~j & 0xF);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < paneIcons.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		for (int i = 0; i < paneIcons.length; ++i) {
			paneIcons[i] = iconregister.registerIcon("got:stained_glass_" + ItemDye.field_150921_b[BlockStainedGlassPane.func_150103_c(i)]);
		}
	}
}
