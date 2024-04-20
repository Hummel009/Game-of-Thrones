package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.slab.GOTBlockSlabBase;
import got.common.database.GOTBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockScorchedSlab extends GOTBlockSlabBase {
	public GOTBlockScorchedSlab(boolean flag) {
		super(flag, Material.rock, 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTBlocks.scorchedStone.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}