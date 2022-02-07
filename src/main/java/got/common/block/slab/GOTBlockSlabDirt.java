package got.common.block.slab;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockSlabDirt extends GOTBlockSlabBase {
	public GOTBlockSlabDirt(boolean flag) {
		super(flag, Material.ground, 6);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGravel);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
		case 0:
			return Blocks.dirt.getIcon(i, 0);
		case 1:
			return GOTRegistry.dirtPath.getIcon(i, 0);
		case 2:
			return GOTRegistry.mud.getIcon(i, 0);
		case 3:
			return GOTRegistry.asshaiDirt.getIcon(i, 0);
		case 4:
			return GOTRegistry.dirtPath.getIcon(i, 1);
		case 5:
			return GOTRegistry.dirtPath.getIcon(i, 2);
		default:
			break;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
