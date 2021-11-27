package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.client.render.other.GOTConnectedTextures;
import got.common.block.GOTConnectedBlock;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class GOTBlockDaub extends Block implements GOTConnectedBlock {
	public GOTBlockDaub() {
		super(Material.grass);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(1.0f);
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
		int meta = world.getBlockMetadata(i, j, k);
		Block otherBlock = world.getBlock(i1, j1, k1);
		int otherMeta = world.getBlockMetadata(i1, j1, k1);
		return otherBlock == this && otherMeta == meta;
	}

	@Override
	public String getConnectedName(int meta) {
		return textureName;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		return GOTConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTConnectedTextures.getConnectedIconItem(this, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		GOTConnectedTextures.registerConnectedIcons(iconregister, this, 0, false);
	}
}
