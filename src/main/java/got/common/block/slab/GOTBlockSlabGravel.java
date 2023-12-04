package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockSlabGravel extends GOTBlockSlabFalling {
	public GOTBlockSlabGravel(boolean flag) {
		super(flag, Material.sand, 3);
		setHardness(0.6f);
		setStepSound(Block.soundTypeGravel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		j1 &= 7;
		switch (j1) {
			case 0:
				return Blocks.gravel.getIcon(i, 0);
			case 1:
				return GOTBlocks.basaltGravel.getIcon(i, 0);
			case 2:
				return GOTBlocks.obsidianGravel.getIcon(i, 0);
			default:
				break;
		}
		return super.getIcon(i, j1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
