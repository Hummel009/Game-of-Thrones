package got.common.block.other;

import got.common.database.GOTBlocks;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class GOTBlockKelp extends Block {
	public GOTBlockKelp() {
		super(Material.water);
		float f = 0.375f;
		setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
		setHardness(0.0f);
		setStepSound(soundTypeGrass);
		setCreativeTab(GOTCreativeTabs.tabDeco);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		for (int i = 1; i <= 8; i++) {
			if (par1World.getBlock(par2, par3 + i, par4) == GOTBlocks.kelp) {
				par1World.func_147480_a(par2, par3 + i, par4, true);
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return canPlaceBlockAt(world, i, j, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		Block below = world.getBlock(i, j - 1, k);
		return below == this || below == Blocks.sand || below == Blocks.dirt;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}