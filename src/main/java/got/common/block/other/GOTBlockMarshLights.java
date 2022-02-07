package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class GOTBlockMarshLights extends Block {
	public GOTBlockMarshLights() {
		super(Material.circuits);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return world.getBlock(i, j - 1, k).getMaterial() == Material.water;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return canBlockStay(world, i, j, k);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return null;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			world.setBlock(i, j, k, Blocks.air, 0, 2);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (random.nextInt(3) > 0) {
			if (random.nextInt(3) == 0) {
				GOT.getProxy().spawnParticle("marshFlame", i + random.nextFloat(), j - 0.5, k + random.nextFloat(), 0.0, 0.05f + random.nextFloat() * 0.1f, 0.0);
			} else {
				GOT.getProxy().spawnParticle("marshLight", i + random.nextFloat(), j - 0.5, k + random.nextFloat(), 0.0, 0.05f + random.nextFloat() * 0.1f, 0.0);
			}
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
