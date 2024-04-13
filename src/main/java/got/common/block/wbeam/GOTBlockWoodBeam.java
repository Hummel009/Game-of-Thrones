package got.common.block.wbeam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public abstract class GOTBlockWoodBeam extends BlockRotatedPillar {
	@SideOnly(Side.CLIENT)
	private IIcon[] sideIcons;

	@SideOnly(Side.CLIENT)
	private IIcon[] topIcons;

	protected String[] woodNames;

	protected GOTBlockWoodBeam() {
		super(Material.wood);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(2.0f);
		setStepSound(soundTypeWood);
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getBeamRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getSideIcon(int i) {
		int i1 = i;
		if (i1 < 0 || i1 >= woodNames.length) {
			i1 = 0;
		}
		return sideIcons[i1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < woodNames.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getTopIcon(int i) {
		int i1 = i;
		if (i1 < 0 || i1 >= woodNames.length) {
			i1 = 0;
		}
		return topIcons[i1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		sideIcons = new IIcon[woodNames.length];
		topIcons = new IIcon[woodNames.length];
		for (int i = 0; i < woodNames.length; ++i) {
			topIcons[i] = iconregister.registerIcon(getTextureName() + '_' + woodNames[i] + "_top");
			sideIcons[i] = iconregister.registerIcon(getTextureName() + '_' + woodNames[i] + "_side");
		}
	}
}