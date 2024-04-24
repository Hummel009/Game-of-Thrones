package got.common.entity.other.inanimate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.GOTDamage;
import got.common.entity.westeros.ice.IceUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntitySnowball extends Entity {
	private EntityLivingBase shootingEntity;
	private Block block;

	private boolean inGround;

	private double accelerationX;
	private double accelerationY;
	private double accelerationZ;

	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int ticksAlive;
	private int ticksInAir;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySnowball(World world) {
		super(world);
		setSize(0.3125F, 0.3125F);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySnowball(World world, double d1, double d2, double d3, double d4, double d5, double d6) {
		super(world);
		setSize(0.3125F, 0.3125F);
		setLocationAndAngles(d1, d2, d3, rotationYaw, rotationPitch);
		setPosition(d1, d2, d3);
		double d7 = MathHelper.sqrt_double(d4 * d4 + d5 * d5 + d6 * d6);
		accelerationX = d4 / d7 * 0.1D;
		accelerationY = d5 / d7 * 0.1D;
		accelerationZ = d6 / d7 * 0.1D;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySnowball(World world, EntityLivingBase entity, double d1, double d2, double d3) {
		super(world);
		double d11 = d1;
		double d21 = d2;
		double d31 = d3;
		shootingEntity = entity;
		setSize(0.3125F, 0.3125F);
		setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = motionY = motionZ = 0.0D;
		d11 += rand.nextGaussian() * 0.4D;
		d21 += rand.nextGaussian() * 0.4D;
		d31 += rand.nextGaussian() * 0.4D;
		double d4 = MathHelper.sqrt_double(d11 * d11 + d21 * d21 + d31 * d31);
		accelerationX = d11 / d4 * 0.1D;
		accelerationY = d21 / d4 * 0.1D;
		accelerationZ = d31 / d4 * 0.1D;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float f) {
		return false;
	}

	@Override
	public void entityInit() {
	}

	@Override
	public float getBrightness(float f) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double d) {
		double d1 = boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return d < d1 * d1;
	}

	@Override
	@SuppressWarnings("StatementWithEmptyBody")
	public void onUpdate() {
		if (!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int) posX, (int) posY, (int) posZ))) {
			setDead();
		} else {
			super.onUpdate();

			if (inGround) {
				if (worldObj.getBlock(xTile, yTile, zTile) == block) {
					++ticksAlive;

					if (ticksAlive == 600) {
						setDead();
					}
					return;
				}
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksAlive = 0;
				ticksInAir = 0;
			} else {
				++ticksInAir;
			}
			Vec3 vec3 = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 vec31 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3, vec31);
			vec3 = Vec3.createVectorHelper(posX, posY, posZ);
			vec31 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);

			if (movingobjectposition != null) {
				vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}
			Entity entity = null;
			List<? extends Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;

			for (Entity element : list) {

				if (element.canBeCollidedWith() && (!element.isEntityEqual(shootingEntity) || ticksInAir >= 25)) {
					float f = 0.3F;
					AxisAlignedBB axisalignedbb = element.boundingBox.expand(f, f, f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

					if (movingobjectposition1 != null) {
						double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = element;
							d0 = d1;
						}
					}
				}
			}
			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}
			if (movingobjectposition != null) {
				if (!worldObj.isRemote) {
					if (movingobjectposition.entityHit != null) {
						DamageSource damagesource;

						if (shootingEntity == null) {
							damagesource = DamageSource.causeThrownDamage(this, this);
						} else {
							damagesource = DamageSource.causeThrownDamage(this, shootingEntity);
						}

						if (movingobjectposition.entityHit.attackEntityFrom(damagesource, 5.0f)) {
							if (movingobjectposition.entityHit instanceof EntityPlayerMP) {
								GOTDamage.doFrostDamage((EntityPlayerMP) movingobjectposition.entityHit);
							}
						} else {
							motionX *= -0.10000000149011612D;
							motionY *= -0.10000000149011612D;
							motionZ *= -0.10000000149011612D;
							prevRotationYaw += 180.0F;
							ticksInAir = 0;
						}
					}
					setDead();
				}
			}
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) + 90.0F;

			for (rotationPitch = (float) (Math.atan2(f1, motionY) * 180.0D / Math.PI) - 90.0F; rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
			}
			while (rotationPitch - prevRotationPitch >= 180.0F) {
				prevRotationPitch += 360.0F;
			}
			while (rotationYaw - prevRotationYaw < -180.0F) {
				prevRotationYaw -= 360.0F;
			}
			while (rotationYaw - prevRotationYaw >= 180.0F) {
				prevRotationYaw += 360.0F;
			}
			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float f2 = 0.95F;

			if (isInWater()) {
				for (int j = 0; j < 4; ++j) {
					float f3 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ);
				}
				f2 = 0.8F;
			}
			motionX += accelerationX;
			motionY += accelerationY;
			motionZ += accelerationZ;
			motionX *= f2;
			motionY *= f2;
			motionZ *= f2;
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
			setPosition(posX, posY, posZ);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		xTile = nbt.getShort("xTile");
		yTile = nbt.getShort("yTile");
		zTile = nbt.getShort("zTile");
		block = Block.getBlockById(nbt.getByte("inTile") & 255);
		inGround = nbt.getByte("inGround") == 1;

		if (nbt.hasKey("direction", 9)) {
			NBTTagList nbttaglist = nbt.getTagList("direction", 6);
			motionX = nbttaglist.func_150309_d(0);
			motionY = nbttaglist.func_150309_d(1);
			motionZ = nbttaglist.func_150309_d(2);
		} else {
			setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("xTile", (short) xTile);
		nbt.setShort("yTile", (short) yTile);
		nbt.setShort("zTile", (short) zTile);
		nbt.setByte("inTile", (byte) Block.getIdFromBlock(block));
		nbt.setByte("inGround", (byte) (inGround ? 1 : 0));
		nbt.setTag("direction", newDoubleNBTList(motionX, motionY, motionZ));
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		IceUtils.createNewWight(this, entity, worldObj);
	}
}