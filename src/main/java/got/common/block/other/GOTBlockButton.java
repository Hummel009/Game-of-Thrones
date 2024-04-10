package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;

public class GOTBlockButton extends BlockButton {
	private final String iconPath;

	public GOTBlockButton(boolean flag, String s) {
		super(flag);
		iconPath = s;
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcon = iconregister.registerIcon(iconPath);
	}
}
