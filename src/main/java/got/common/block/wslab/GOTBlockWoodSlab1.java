package got.common.block.wslab;

import cpw.mods.fml.relauncher.*;
import got.common.block.slab.GOTBlockSlabBase;
import got.common.database.GOTRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockWoodSlab1 extends GOTBlockSlabBase {
	public GOTBlockWoodSlab1(boolean flag) {
		super(flag, Material.wood, 8);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.planks1.getIcon(i, j &= 7);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
