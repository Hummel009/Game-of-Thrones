package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.entity.other.GOTEntityBarrel;
import got.common.tileentity.GOTTileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class GOTItemBarrel extends ItemBlock {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTItemBarrel(Block block) {
		super(block);
	}

	private static NBTTagCompound getBarrelData(ItemStack itemstack) {
		if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("GOTBarrelData")) {
			return itemstack.getTagCompound().getCompoundTag("GOTBarrelData");
		}
		return null;
	}

	private static void loadBarrelDataToTE(ItemStack itemstack, GOTTileEntityBarrel barrel) {
		NBTTagCompound nbt = getBarrelData(itemstack);
		if (nbt != null) {
			barrel.readBarrelFromNBT(nbt);
		}
	}

	public static void setBarrelData(ItemStack itemstack, NBTTagCompound nbt) {
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setTag("GOTBarrelData", nbt);
	}

	public static void setBarrelDataFromTE(ItemStack itemstack, GOTTileEntityBarrel barrel) {
		NBTTagCompound nbt = new NBTTagCompound();
		barrel.writeBarrelToNBT(nbt);
		setBarrelData(itemstack, nbt);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		NBTTagCompound barrelData = getBarrelData(itemstack);
		if (barrelData != null) {
			GOTTileEntityBarrel tileEntity = new GOTTileEntityBarrel();
			tileEntity.readBarrelFromNBT(barrelData);
			list.add(tileEntity.getInvSubtitle());
		}
	}

	@Override
	public int getItemStackLimit(ItemStack itemstack) {
		NBTTagCompound nbt = getBarrelData(itemstack);
		if (nbt != null) {
			return 1;
		}
		return super.getItemStackLimit(itemstack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		MovingObjectPosition m = getMovingObjectPositionFromPlayer(world, entityplayer, true);
		if (m == null) {
			return itemstack;
		}
		if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			int i = m.blockX;
			int j = m.blockY;
			int k = m.blockZ;
			if (world.getBlock(i, j, k).getMaterial() != Material.water || world.getBlockMetadata(i, j, k) != 0) {
				return itemstack;
			}
			GOTEntityBarrel barrel = new GOTEntityBarrel(world, i + 0.5f, j + 1.0f, k + 0.5f);
			barrel.rotationYaw = ((MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3) - 1) * 90.0f;
			if (!world.getCollidingBoundingBoxes(barrel, barrel.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
				return itemstack;
			}
			if (!world.isRemote) {
				barrel.setBarrelItemData(getBarrelData(itemstack));
				world.spawnEntityInWorld(barrel);
			}
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
		}
		return itemstack;
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2, int metadata) {
		if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
			TileEntity tileentity = world.getTileEntity(i, j, k);
			if (tileentity instanceof GOTTileEntityBarrel) {
				GOTTileEntityBarrel barrel = (GOTTileEntityBarrel) tileentity;
				loadBarrelDataToTE(itemstack, barrel);
			}
			return true;
		}
		return false;
	}
}