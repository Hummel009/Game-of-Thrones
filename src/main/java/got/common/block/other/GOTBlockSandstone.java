package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockSandstone extends Block {
	@SideOnly(value = Side.CLIENT)
	public IIcon iconTop;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconBottom;

	public GOTBlockSandstone() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setStepSound(Block.soundTypeStone);
		setHardness(0.8f);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 0) {
			return iconBottom;
		}
		if (i == 1) {
			return iconTop;
		}
		return blockIcon;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
		iconTop = iconregister.registerIcon(getTextureName() + "_top");
		iconBottom = iconregister.registerIcon(getTextureName() + "_bottom");
	}
}
