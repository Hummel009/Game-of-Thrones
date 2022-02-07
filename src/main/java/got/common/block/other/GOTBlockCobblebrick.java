package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.client.render.other.GOTConnectedTextures;
import got.common.block.GOTConnectedBlock;
import got.common.block.brick.GOTBlockBrickBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class GOTBlockCobblebrick extends GOTBlockBrickBase implements GOTConnectedBlock {
	public GOTBlockCobblebrick() {
		setBrickNames("cob");
	}

	@Override
	public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
		return GOTConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
	}

	@Override
	public String getConnectedName(int meta) {
		return textureName + "_" + getBrickNames()[meta];
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		return GOTConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		boolean[][] adjacentFlags = i == 0 || i == 1 ? new boolean[][] { { false, false, false }, { false, true, false }, { false, false, false } } : new boolean[][] { { false, true, false }, { false, true, false }, { false, true, false } };
		return GOTConnectedTextures.getConnectedIconItem(this, j, adjacentFlags);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		setBrickIcons(new IIcon[getBrickNames().length]);
		for (int i = 0; i < getBrickNames().length; ++i) {
			GOTConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
		}
	}
}
