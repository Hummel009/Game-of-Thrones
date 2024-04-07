package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTBlockChain extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon iconMiddle;
	@SideOnly(Side.CLIENT)
	public IIcon iconTop;
	@SideOnly(Side.CLIENT)
	public IIcon iconBottom;
	@SideOnly(Side.CLIENT)
	public IIcon iconSingle;

	public GOTBlockChain() {
		super(Material.circuits);
		setHardness(1.0f);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		float f = 0.2f;
		setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return canPlaceBlockAt(world, i, j, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j + 1, k);
		int meta = world.getBlockMetadata(i, j + 1, k);
		if (block instanceof GOTBlockChain || block instanceof BlockFence || block instanceof BlockWall) {
			return true;
		}
		if (block instanceof BlockSlab && !block.isOpaqueCube() && (meta & 8) == 0) {
			return true;
		}
		if (block instanceof BlockStairs && (meta & 4) == 0) {
			return true;
		}
		return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		float f = 0.01f;
		return AxisAlignedBB.getBoundingBox(i + 0.5f - f, j, k + 0.5f - f, i + 0.5f + f, j + 1, k + 0.5f + f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		Block above = world.getBlock(i, j + 1, k);
		Block below = world.getBlock(i, j - 1, k);
		boolean chainAbove = above instanceof GOTBlockChain;
		boolean chainBelow = below instanceof GOTBlockChain || below instanceof GOTBlockChandelier;
		if (chainAbove && chainBelow) {
			return iconMiddle;
		}
		if (chainAbove) {
			return iconBottom;
		}
		if (chainBelow) {
			return iconTop;
		}
		return iconSingle;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return iconMiddle;
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getChainRenderID();
	}

	@Override
	public boolean isLadder(IBlockAccess world, int i, int j, int k, EntityLivingBase entity) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SuppressWarnings("StatementWithEmptyBody")
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.getHeldItem();
		if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(this)) {
			int j1;
			Block block;
			for (j1 = j; j1 >= 0 && j1 < world.getHeight() && world.getBlock(i, j1, k) == this; --j1) {
			}
			if (j1 >= 0 && j1 < world.getHeight()) {
				block = world.getBlock(i, j1, k);
				if (canPlaceBlockOnSide(world, i, j1, k, side) && block.isReplaceable(world, i, j1, k) && !block.getMaterial().isLiquid()) {
					int thisMeta = world.getBlockMetadata(i, j, k);
					world.setBlock(i, j1, k, this, thisMeta, 3);
					world.playSoundEffect(i + 0.5f, j1 + 0.5f, k + 0.5f, stepSound.func_150496_b(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.8f);
					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
					}
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			dropBlockAsItem(world, i, j, k, meta, 0);
			world.setBlockToAir(i, j, k);
		}
		super.onNeighborBlockChange(world, i, j, k, block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		iconMiddle = iconregister.registerIcon(getTextureName() + "_mid");
		iconTop = iconregister.registerIcon(getTextureName() + "_top");
		iconBottom = iconregister.registerIcon(getTextureName() + "_bottom");
		iconSingle = iconregister.registerIcon(getTextureName() + "_single");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
