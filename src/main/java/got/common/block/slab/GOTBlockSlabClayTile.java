package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockSlabClayTile extends GOTBlockSlabBase {
	public GOTBlockSlabClayTile(boolean flag) {
		super(flag, Material.rock, 1);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(1.25f);
		setResistance(7.0f);
		setStepSound(soundTypeStone);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTBlocks.clayTile.getIcon(i, j & 7);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
