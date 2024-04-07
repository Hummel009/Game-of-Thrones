package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.brick.GOTBlockBrickBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class GOTBlockSmoothStoneBase extends GOTBlockBrickBase {
	@SideOnly(Side.CLIENT)
	public IIcon[] topIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] sideIcons;

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= brickNames.length) {
			j1 = 0;
		}
		if (i == 0 || i == 1) {
			return topIcons[j1];
		}
		return sideIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		topIcons = new IIcon[brickNames.length];
		sideIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			topIcons[i] = iconregister.registerIcon(getTextureName() + '_' + brickNames[i] + "_top");
			sideIcons[i] = iconregister.registerIcon(getTextureName() + '_' + brickNames[i] + "_side");
		}
	}
}
