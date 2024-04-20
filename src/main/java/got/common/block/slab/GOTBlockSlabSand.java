package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockSlabSand extends GOTBlockSlabFalling {
	public GOTBlockSlabSand(boolean flag) {
		super(flag, Material.sand, 3);
		setHardness(0.5f);
		setStepSound(soundTypeSand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		j1 &= 7;
		switch (j1) {
			case 0:
				return Blocks.sand.getIcon(i, 0);
			case 1:
				return Blocks.sand.getIcon(i, 1);
			default:
				return GOTBlocks.whiteSand.getIcon(i, 0);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}