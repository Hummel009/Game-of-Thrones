package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class GOTBlockPlantain extends GOTBlockFlower {
	@SideOnly(value = Side.CLIENT)
	public static IIcon stemIcon;
	@SideOnly(value = Side.CLIENT)
	public static IIcon petalIcon;

	public GOTBlockPlantain() {
		setBlockBounds(0.2f, 0.0f, 0.2f, 0.8f, 0.4f, 0.8f);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return world.getBiomeGenForCoords(i, k).getBiomeGrassColor(i, j, k);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getBlockColor() {
		return ColorizerGrass.getGrassColor(1.0, 1.0);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return petalIcon;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		return getBlockColor();
	}

	@Override
	public int getRenderType() {
		return GOT.getProxy().getPlantainRenderID();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		double posX = i;
		double posY = j;
		double posZ = k;
		long seed = i * 3129871 ^ k * 116129781L ^ j;
		seed = seed * seed * 42317861L + seed * 11L;
		return AxisAlignedBB.getBoundingBox((posX += ((seed >> 16 & 0xFL) / 15.0f - 0.5) * 0.5) + minX, posY + minY, (posZ += ((seed >> 24 & 0xFL) / 15.0f - 0.5) * 0.5) + minZ, posX + maxX, posY + maxY, posZ + maxZ);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		stemIcon = iconregister.registerIcon(getTextureName() + "_stem");
		petalIcon = iconregister.registerIcon(getTextureName() + "_petal");
	}
}
