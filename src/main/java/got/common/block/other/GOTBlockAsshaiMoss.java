package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

public class GOTBlockAsshaiMoss extends Block implements IShearable {
	public GOTBlockAsshaiMoss() {
		super(Material.plants);
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
		setTickRandomly(true);
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
		setHardness(0.2f);
		setStepSound(soundTypeGrass);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return super.canBlockStay(world, i, j, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return super.canPlaceBlockAt(world, i, j, k) && canBlockStay(world, i, j, k);
	}

	private void checkMossCanStay(World world, int i, int j, int k) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		super.onNeighborBlockChange(world, i, j, k, block);
		checkMossCanStay(world, i, j, k);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this));
		return drops;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		checkMossCanStay(world, i, j, k);
	}
}
