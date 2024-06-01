package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockSlabDirt extends GOTBlockSlabBase {
	public GOTBlockSlabDirt(boolean flag) {
		super(flag, Material.ground, 6);
		setHardness(0.5f);
		setStepSound(soundTypeGravel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		j1 &= 7;
		switch (j1) {
			case 0:
				return Blocks.dirt.getIcon(i, 0);
			case 1:
				return GOTBlocks.dirtPath.getIcon(i, 0);
			case 2:
				return GOTBlocks.mud.getIcon(i, 0);
			case 3:
				return GOTBlocks.asshaiDirt.getIcon(i, 0);
			case 4:
				return GOTBlocks.dirtPath.getIcon(i, 1);
			case 5:
				return GOTBlocks.dirtPath.getIcon(i, 2);
			default:
				return null;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}