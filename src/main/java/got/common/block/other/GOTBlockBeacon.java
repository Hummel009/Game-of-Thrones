package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.tileentity.GOTTileEntityBeacon;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTBlockBeacon extends BlockContainer {
	public GOTBlockBeacon() {
		super(Material.wood);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
		setHardness(0.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
	}

	private boolean canItemLightBeacon(ItemStack itemstack) {
		if (itemstack == null) {
			return false;
		}
		Item item = itemstack.getItem();
		return item == Items.flint_and_steel || item instanceof ItemBlock && ((ItemBlock) item).field_150939_a instanceof BlockTorch;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return canBlockStay(world, i, j, k);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityBeacon();
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		return Blocks.planks.getIcon(i, 0);
	}

	@Override
	public int getLightValue(IBlockAccess world, int i, int j, int k) {
		return GOTBlockBeacon.isFullyLit(world, i, j, k) ? 15 : 0;
	}

	@Override
	public int getRenderType() {
		return GOT.getProxy().getBeaconRenderID();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (canItemLightBeacon(itemstack) && !GOTBlockBeacon.isLit(world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() != Material.water) {
			world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, world.rand.nextFloat() * 0.4f + 0.8f);
			if (itemstack.getItem().isDamageable()) {
				itemstack.damageItem(1, entityplayer);
			}
			if (!world.isRemote) {
				GOTBlockBeacon.setLit(world, i, j, k, true);
			}
			return true;
		}
		if (itemstack != null && itemstack.getItem() == Items.water_bucket && GOTBlockBeacon.isLit(world, i, j, k)) {
			world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
			if (!entityplayer.capabilities.isCreativeMode) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bucket));
			}
			if (!world.isRemote) {
				GOTBlockBeacon.setLit(world, i, j, k, false);
			}
			return true;
		}
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if (entity.isBurning() && !GOTBlockBeacon.isLit(world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() != Material.water) {
			world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, world.rand.nextFloat() * 0.4f + 0.8f);
			if (!world.isRemote) {
				GOTBlockBeacon.setLit(world, i, j, k, true);
				entity.setDead();
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		} else if (GOTBlockBeacon.isLit(world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() == Material.water) {
			world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
			if (!world.isRemote) {
				GOTBlockBeacon.setLit(world, i, j, k, false);
			}
		}
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (!GOTBlockBeacon.isLit(world, i, j, k)) {
			return;
		}
		if (random.nextInt(24) == 0) {
			world.playSound(i + 0.5, j + 0.5, k + 0.5, "fire.fire", 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
		}
		for (int l = 0; l < 3; ++l) {
			double d = i + random.nextFloat();
			double d1 = j + random.nextFloat() * 0.5 + 0.5;
			double d2 = k + random.nextFloat();
			world.spawnParticle("largesmoke", d, d1, d2, 0.0, 0.0, 0.0);
		}
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public static boolean isFullyLit(IBlockAccess world, int i, int j, int k) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityBeacon) {
			GOTTileEntityBeacon beacon = (GOTTileEntityBeacon) tileentity;
			return beacon.isFullyLit();
		}
		return false;
	}

	private static boolean isLit(IBlockAccess world, int i, int j, int k) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityBeacon) {
			GOTTileEntityBeacon beacon = (GOTTileEntityBeacon) tileentity;
			return beacon.isLit();
		}
		return false;
	}

	private static void setLit(World world, int i, int j, int k, boolean lit) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity instanceof GOTTileEntityBeacon) {
			GOTTileEntityBeacon beacon = (GOTTileEntityBeacon) tileentity;
			beacon.setLit(lit);
		}
	}
}
