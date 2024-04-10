package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockSlabClayTileDyed2 extends GOTBlockSlabBase {
	public GOTBlockSlabClayTileDyed2(boolean flag) {
		super(flag, Material.rock, 8);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(1.25f);
		setResistance(7.0f);
		setStepSound(soundTypeStone);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		j1 &= 7;
		return GOTBlocks.clayTileDyed.getIcon(i, j1 + 8);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}