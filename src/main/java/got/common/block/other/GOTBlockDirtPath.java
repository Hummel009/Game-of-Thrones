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

public class GOTBlockDirtPath extends Block {
	@SideOnly(value = Side.CLIENT)
	private IIcon[] pathIcons;
	private String[] pathNames = { "dirt", "mud", "brus" };

	public GOTBlockDirtPath() {
		super(Material.ground);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGravel);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= pathNames.length) {
			j = 0;
		}
		return pathIcons[j];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < pathNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		pathIcons = new IIcon[pathNames.length];
		for (int i = 0; i < pathNames.length; ++i) {
			pathIcons[i] = iconregister.registerIcon(getTextureName() + "_" + pathNames[i]);
		}
	}
}
