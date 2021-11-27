package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockSmoothStoneV extends GOTBlockSmoothStoneBase {
	public GOTBlockSmoothStoneV() {
		setBrickNames("stone");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j == 0) {
			return Blocks.stone_slab.getIcon(i, 0);
		}
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
