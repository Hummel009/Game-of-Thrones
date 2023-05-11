package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockSlabThatch extends GOTBlockSlabBase {
	public GOTBlockSlabThatch(boolean flag) {
		super(flag, Material.grass, 2);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGrass);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.thatch.getIcon(i, j &= 7);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
