package got.common.entity.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public abstract class GOTEntityFireball extends Entity {
	public int field_145795_e = -1;
	public int field_145793_f = -1;
	public int field_145794_g = -1;
	public Block field_145796_h;
	public boolean inGround;
	public EntityLivingBase shootingEntity;
	public int ticksAlive;
	public int ticksInAir;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;

	protected GOTEntityFireball(World p_i1759_1_) {
		super(p_i1759_1_);
		setSize(1.0F, 1.0F);
	}

	protected GOTEntityFireball(World p_i1760_1_, double p_i1760_2_, double p_i1760_4_, double p_i1760_6_, double p_i1760_8_, double p_i1760_10_, double p_i1760_12_) {
		super(p_i1760_1_);
		setSize(1.0F, 1.0F);
		setLocationAndAngles(p_i1760_2_, p_i1760_4_, p_i1760_6_, rotationYaw, rotationPitch);
		setPosition(p_i1760_2_, p_i1760_4_, p_i1760_6_);
		double d6 = MathHelper.sqrt_double(p_i1760_8_ * p_i1760_8_ + p_i1760_10_ * p_i1760_10_ + p_i1760_12_ * p_i1760_12_);
		accelerationX = p_i1760_8_ / d6 * 0.1D;
		accelerationY = p_i1760_10_ / d6 * 0.1D;
		accelerationZ = p_i1760_12_ / d6 * 0.1D;
	}

	protected GOTEntityFireball(World p_i1761_1_, EntityLivingBase p_i1761_2_, double p_i1761_3_, double p_i1761_5_, double p_i1761_7_) {
		super(p_i1761_1_);
		shootingEntity = p_i1761_2_;
		setSize(1.0F, 1.0F);
		setLocationAndAngles(p_i1761_2_.posX, p_i1761_2_.posY, p_i1761_2_.posZ, p_i1761_2_.rotationYaw, p_i1761_2_.rotationPitch);
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = motionY = motionZ = 0.0D;
		p_i1761_3_ += rand.nextGaussian() * 0.4D;
		p_i1761_5_ += rand.nextGaussian() * 0.4D;
		p_i1761_7_ += rand.nextGaussian() * 0.4D;
		double d3 = MathHelper.sqrt_double(p_i1761_3_ * p_i1761_3_ + p_i1761_5_ * p_i1761_5_ + p_i1761_7_ * p_i1761_7_);
		accelerationX = p_i1761_3_ / d3 * 0.1D;
		accelerationY = p_i1761_5_ / d3 * 0.1D;
		accelerationZ = p_i1761_7_ / d3 * 0.1D;
	}

	@Override
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if (isEntityInvulnerable()) {
			return false;
		}
		setBeenAttacked();

		if (p_70097_1_.getEntity() == null) {
			return false;
		}
		Vec3 vec3 = p_70097_1_.getEntity().getLookVec();

		if (vec3 != null) {
			motionX = vec3.xCoord;
			motionY = vec3.yCoord;
			motionZ = vec3.zCoord;
			accelerationX = motionX * 0.1D;
			accelerationY = motionY * 0.1D;
			accelerationZ = motionZ * 0.1D;
		}

		if (p_70097_1_.getEntity() instanceof EntityLivingBase) {
			shootingEntity = (EntityLivingBase) p_70097_1_.getEntity();
		}

		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public void entityInit() {
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
	}

	public float getMotionFactor() {
		return 0.95F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double p_70112_1_) {
		double d1 = boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return p_70112_1_ < d1 * d1;
	}

	public abstract void onImpact(MovingObjectPosition p_70227_1_);

	@Override
	public void onUpdate() {
		if (!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int) posX, (int) posY, (int) posZ))) {
			setDead();
		} else {
			super.onUpdate();

			if (inGround) {
				if (worldObj.getBlock(field_145795_e, field_145793_f, field_145794_g) == field_145796_h) {
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
				onImpact(movingobjectposition);
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
			float f2 = getMotionFactor();

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
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		field_145795_e = p_70037_1_.getShort("xTile");
		field_145793_f = p_70037_1_.getShort("yTile");
		field_145794_g = p_70037_1_.getShort("zTile");
		field_145796_h = Block.getBlockById(p_70037_1_.getByte("inTile") & 255);
		inGround = p_70037_1_.getByte("inGround") == 1;

		if (p_70037_1_.hasKey("direction", 9)) {
			NBTTagList nbttaglist = p_70037_1_.getTagList("direction", 6);
			motionX = nbttaglist.func_150309_d(0);
			motionY = nbttaglist.func_150309_d(1);
			motionZ = nbttaglist.func_150309_d(2);
		} else {
			setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("xTile", (short) field_145795_e);
		p_70014_1_.setShort("yTile", (short) field_145793_f);
		p_70014_1_.setShort("zTile", (short) field_145794_g);
		p_70014_1_.setByte("inTile", (byte) Block.getIdFromBlock(field_145796_h));
		p_70014_1_.setByte("inGround", (byte) (inGround ? 1 : 0));
		p_70014_1_.setTag("direction", newDoubleNBTList(motionX, motionY, motionZ));
	}
}