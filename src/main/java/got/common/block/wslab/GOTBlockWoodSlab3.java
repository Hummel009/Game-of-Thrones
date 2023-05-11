package got.common.block.wslab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.slab.GOTBlockSlabBase;
import got.common.database.GOTRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockWoodSlab3 extends GOTBlockSlabBase {
	public GOTBlockWoodSlab3(boolean flag) {
		super(flag, Material.wood, 8);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.planks2.getIcon(i, j &= 7);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}