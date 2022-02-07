package got.common.block.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockThatch extends Block {
	private static String[] thatchNames = { "thatch", "reed" };
	@SideOnly(value = Side.CLIENT)
	private IIcon[] thatchIcons;

	public GOTBlockThatch() {
		super(Material.grass);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGrass);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= thatchNames.length) {
			j = 0;
		}
		return thatchIcons[j];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < thatchNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		thatchIcons = new IIcon[thatchNames.length];
		for (int i = 0; i < thatchNames.length; ++i) {
			thatchIcons[i] = iconregister.registerIcon(getTextureName() + "_" + thatchNames[i]);
		}
	}
}
