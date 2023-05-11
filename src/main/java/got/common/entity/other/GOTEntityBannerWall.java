package got.common.entity.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.GOTBannerProtection;
import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemBanner;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityBannerWall extends EntityHanging {
	public NBTTagCompound protectData;
	public boolean updatedClientBB;

	public GOTEntityBannerWall(World world) {
		super(world);
		setSize(0.0f, 0.0f);
	}

	public GOTEntityBannerWall(World world, int i, int j, int k, int dir) {
		super(world, i, j, k, dir);
		setSize(0.0f, 0.0f);
		setDirection(dir);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && GOTBannerProtection.isProtected(worldObj, this, GOTBannerProtection.forPlayer((EntityPlayer) damagesource.getEntity()), true)) {
			return false;
		}
		return super.attackEntityFrom(damagesource, f);
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(10, 0);
		dataWatcher.addObject(11, 0);
		dataWatcher.addObject(12, 0);
		dataWatcher.addObject(13, (short) 0);
		dataWatcher.addObject(18, (short) 0);
	}

	public ItemStack getBannerItem() {
		ItemStack item = new ItemStack(GOTRegistry.banner, 1, getBannerType().bannerID);
		if (protectData != null) {
			GOTItemBanner.setProtectionData(item, protectData);
		}
		return item;
	}

	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.forID(getBannerTypeID());
	}

	public void setBannerType(GOTItemBanner.BannerType type) {
		setBannerTypeID(type.bannerID);
	}

	public int getBannerTypeID() {
		return dataWatcher.getWatchableObjectShort(18);
	}

	public void setBannerTypeID(int i) {
		dataWatcher.updateObject(18, (short) i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float f) {
		int k;
		int i;
		if (!updatedClientBB) {
			getWatchedDirection();
			setDirection(hangingDirection);
			updatedClientBB = true;
		}
		if (worldObj.blockExists(i = MathHelper.floor_double(posX), 0, k = MathHelper.floor_double(posZ))) {
			int j = MathHelper.floor_double(posY);
			return worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
		}
		return 0;
	}

	@Override
	public int getHeightPixels() {
		return 32;
	}

	@Override
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return getBannerItem();
	}

	public void getWatchedDirection() {
		field_146063_b = dataWatcher.getWatchableObjectInt(10);
		field_146064_c = dataWatcher.getWatchableObjectInt(11);
		field_146062_d = dataWatcher.getWatchableObjectInt(12);
		hangingDirection = dataWatcher.getWatchableObjectShort(13);
	}

	@Override
	public int getWidthPixels() {
		return 16;
	}

	@Override
	public void onBroken(Entity entity) {
		worldObj.playSoundAtEntity(this, Blocks.planks.stepSound.getBreakSound(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
		boolean flag = true;
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
			flag = false;
		}
		if (flag) {
			entityDropItem(getBannerItem(), 0.0f);
		}
	}

	@Override
	public void onUpdate() {
		if (worldObj.isRemote && !updatedClientBB) {
			getWatchedDirection();
			setDirection(hangingDirection);
			updatedClientBB = true;
		}
		super.onUpdate();
	}

	@Override
	public boolean onValidSurface() {
		if (!worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty()) {
			return false;
		}
		int i = field_146063_b;
		int j = field_146064_c;
		int k = field_146062_d;
		Block block = worldObj.getBlock(i, j, k);
		if (!block.getMaterial().isSolid()) {
			return false;
		}
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
		for (Object obj : list) {
			if (!(obj instanceof EntityHanging)) {
				continue;
			}
			return false;
		}
		return true;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setBannerTypeID(nbt.getShort("BannerType"));
		if (nbt.hasKey("ProtectData")) {
			protectData = nbt.getCompoundTag("ProtectData");
		}
	}

	@Override
	public void setDirection(int dir) {
		float edge;
		float zSize;
		float xSize;
		float zEdge;
		float xEdge;
		if (dir < 0 || dir >= Direction.directions.length) {
			dir = 0;
		}
		hangingDirection = dir;
		prevRotationYaw = rotationYaw = Direction.rotateOpposite[dir] * 90.0f;
		float width = 1.0f;
		float thickness = 0.0625f;
		float yEdge = edge = 0.01f;
		if (dir == 0 || dir == 2) {
			xSize = width;
			zSize = thickness;
			xEdge = thickness + edge;
			zEdge = edge;
		} else {
			xSize = thickness;
			zSize = width;
			xEdge = edge;
			zEdge = thickness + edge;
		}
		float f = field_146063_b + 0.5f;
		float f1 = field_146064_c + 0.5f;
		float f2 = field_146062_d + 0.5f;
		float f3 = 0.5f + thickness / 2.0f;
		setPosition(f += Direction.offsetX[dir] * f3, f1, f2 += Direction.offsetZ[dir] * f3);
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		boundingBox.setBounds(f - xSize / 2.0f, f1 - 1.5f, f2 - zSize / 2.0f, f + xSize / 2.0f, f1 + 0.5f, f2 + zSize / 2.0f);
		boundingBox.setBB(boundingBox.contract(xEdge, yEdge, zEdge));
		if (!worldObj.isRemote) {
			updateWatchedDirection();
		}
	}

	public void setProtectData(NBTTagCompound nbt) {
		protectData = nbt;
	}

	public void updateWatchedDirection() {
		dataWatcher.updateObject(10, field_146063_b);
		dataWatcher.updateObject(11, field_146064_c);
		dataWatcher.updateObject(12, field_146062_d);
		dataWatcher.updateObject(13, (short) hangingDirection);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setShort("BannerType", (short) getBannerTypeID());
		if (protectData != null) {
			nbt.setTag("ProtectData", protectData);
		}
	}
}
