package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.brick.GOTBlockBrickBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class GOTBlockSmoothStoneBase extends GOTBlockBrickBase {
	@SideOnly(value = Side.CLIENT)
	public IIcon[] topIcons;
	@SideOnly(value = Side.CLIENT)
	public IIcon[] sideIcons;

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= brickNames.length) {
			j = 0;
		}
		if (i == 0 || i == 1) {
			return topIcons[j];
		}
		return sideIcons[j];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		topIcons = new IIcon[brickNames.length];
		sideIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			topIcons[i] = iconregister.registerIcon(getTextureName() + "_" + brickNames[i] + "_top");
			sideIcons[i] = iconregister.registerIcon(getTextureName() + "_" + brickNames[i] + "_side");
		}
	}
}
