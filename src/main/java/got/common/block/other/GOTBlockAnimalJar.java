package got.common.block.other;

import java.util.*;

import got.common.database.GOTCreativeTabs;
import got.common.item.other.GOTItemAnimalJar;
import got.common.tileentity.GOTTileEntityAnimalJar;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class GOTBlockAnimalJar extends BlockContainer {
	public GOTBlockAnimalJar(Material material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabDeco);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
	}

	public abstract boolean canCapture(Entity var1);

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return canBlockStay(world, i, j, k);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityAnimalJar();
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		if ((meta & 8) == 0) {
			ItemStack itemstack = getJarDrop(world, i, j, k, meta);
			GOTTileEntityAnimalJar jar = (GOTTileEntityAnimalJar) world.getTileEntity(i, j, k);
			if (jar != null) {
				drops.add(itemstack);
			}
		}
		return drops;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}

	private ItemStack getJarDrop(World world, int i, int j, int k, int metadata) {
		ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(metadata));
		GOTTileEntityAnimalJar jar = (GOTTileEntityAnimalJar) world.getTileEntity(i, j, k);
		if (jar != null) {
			GOTItemAnimalJar.setEntityData(itemstack, jar.getEntityData());
		}
		return itemstack;
	}

	public abstract float getJarEntityHeight();

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		int light;
		TileEntity te = world.getTileEntity(i, j, k);
		if (te instanceof GOTTileEntityAnimalJar && (light = ((GOTTileEntityAnimalJar) te).getLightValue()) > 0) {
			return light;
		}
		return super.getLightValue(world, i, j, k);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
		world.markBlockForUpdate(i, j, k);
		return getJarDrop(world, i, j, k, world.getBlockMetadata(i, j, k));
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			world.setBlockMetadataWithNotify(i, j, k, meta |= 8, 4);
		}
		this.dropBlockAsItem(world, i, j, k, meta, 0);
		super.onBlockHarvested(world, i, j, k, meta, entityplayer);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			int meta = world.getBlockMetadata(i, j, k);
			this.dropBlockAsItem(world, i, j, k, meta, 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
