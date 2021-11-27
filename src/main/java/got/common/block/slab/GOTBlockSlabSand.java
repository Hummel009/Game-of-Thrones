package got.common.block.slab;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockSlabSand extends GOTBlockSlabFalling {
	public GOTBlockSlabSand(boolean flag) {
		super(flag, Material.sand, 3);
		setHardness(0.5f);
		setStepSound(Block.soundTypeSand);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if ((j &= 7) == 0) {
			return Blocks.sand.getIcon(i, 0);
		}
		if (j == 1) {
			return Blocks.sand.getIcon(i, 1);
		}
		if (j == 2) {
			return GOTRegistry.whiteSand.getIcon(i, 0);
		}
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
