package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.entity.other.GOTEntityBarrowWight;
import got.common.entity.other.GOTEntityNPC;
import got.common.tileentity.GOTTileEntitySpawnerChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockSpawnerChest extends BlockChest {
	private static boolean dropChestItems = true;

	private final Block chestModel;

	public GOTBlockSpawnerChest(Block block) {
		super(0);
		chestModel = block;
		setStepSound(block.stepSound);
		setCreativeTab(null);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		if (dropChestItems) {
			spawnEntity(world, i, j, k);
			super.breakBlock(world, i, j, k, block, meta);
		} else {
			world.removeTileEntity(i, j, k);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntitySpawnerChest();
	}

	@Override
	public float getBlockHardness(World world, int i, int j, int k) {
		return chestModel.getBlockHardness(world, i, j, k);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int i, int j, int k, double explosionX, double explosionY, double explosionZ) {
		return chestModel.getExplosionResistance(entity, world, i, j, k, explosionX, explosionY, explosionZ);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return chestModel.getIcon(i, j);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return Item.getItemFromBlock(chestModel);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(chestModel);
	}

	@Override
	public int getRenderType() {
		return chestModel.getRenderType();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		spawnEntity(world, i, j, k);
		dropChestItems = false;
		if (!world.isRemote) {
			int l;
			ItemStack[] chestInv = new ItemStack[27];
			TileEntity tileentity = world.getTileEntity(i, j, k);
			if (tileentity instanceof IInventory) {
				for (l = 0; l < chestInv.length; ++l) {
					chestInv[l] = ((IInventory) tileentity).getStackInSlot(l);
				}
			}
			world.setBlock(i, j, k, chestModel, world.getBlockMetadata(i, j, k), 3);
			for (l = 0; l < 27; ++l) {
				((IInventory) world.getTileEntity(i, j, k)).setInventorySlotContents(l, chestInv[l]);
			}
		}
		dropChestItems = true;
		return true;
	}

	private void spawnEntity(World world, int i, int j, int k) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (!(tileentity instanceof GOTTileEntitySpawnerChest)) {
			return;
		}
		Entity entity = ((GOTTileEntitySpawnerChest) tileentity).createMob();
		if (!(entity instanceof EntityLiving)) {
			return;
		}
		EntityLiving entityliving = (EntityLiving) entity;
		entityliving.setLocationAndAngles(i + 0.5, j + 1, k + 0.5, 0.0f, 0.0f);
		entityliving.spawnExplosionParticle();
		if (!world.isRemote) {
			entityliving.onSpawnWithEgg(null);
			if (entityliving instanceof GOTEntityNPC) {
				((GOTEntityNPC) entityliving).isNPCPersistent = true;
			}
			world.spawnEntityInWorld(entityliving);
			world.playSoundAtEntity(entityliving, "got:wraith.spawn", 1.0f, 0.7f + world.rand.nextFloat() * 0.6f);
			if (entityliving instanceof GOTEntityBarrowWight) {
				world.addWeatherEffect(new EntityLightningBolt(world, entityliving.posX, entityliving.posY, entityliving.posZ));
			}
		}
	}

	public Block getChestModel() {
		return chestModel;
	}
}