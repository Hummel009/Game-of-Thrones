package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTBlockWaste extends Block {
	public static Random wasteRand = new Random();
	@SideOnly(value = Side.CLIENT)
	public IIcon[] randomIcons;

	public GOTBlockWaste() {
		super(Material.ground);
		setHardness(0.5f);
		setStepSound(Block.soundTypeSand);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		float f = 0.125f;
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1 - f, k + 1);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		int hash = i * 25799626 ^ k * 6879038 ^ j;
		wasteRand.setSeed(hash += side);
		wasteRand.setSeed(wasteRand.nextLong());
		return randomIcons[wasteRand.nextInt(randomIcons.length)];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int hash = i * 334224425 ^ i;
		hash = hash * hash * 245256 + hash * 113549945;
		wasteRand.setSeed(hash);
		wasteRand.setSeed(wasteRand.nextLong());
		return randomIcons[wasteRand.nextInt(randomIcons.length)];
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getWasteRenderID();
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
		return side == ForgeDirection.UP;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		double slow = 0.4;
		entity.motionX *= slow;
		entity.motionZ *= slow;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		randomIcons = new IIcon[4];
		for (int l = 0; l < randomIcons.length; ++l) {
			randomIcons[l] = iconregister.registerIcon(getTextureName() + "_var" + l);
		}
	}
}
