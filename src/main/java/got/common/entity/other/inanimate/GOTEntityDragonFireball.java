package got.common.entity.other.inanimate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityDragonFireball extends EntityLargeFireball {
	private EntityLivingBase shootingEntity;
	private int field_145795_e = -1;
	private int field_145793_f = -1;
	private int field_145794_g = -1;
	private Block field_145796_h;
	private boolean inGround;
	private int ticksAlive;
	private int ticksInAir;
	private EntityPlayer player;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDragonFireball(World world) {
		super(world);
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDragonFireball(World world, double posX, double posY, double posZ, double accelerationX, double accelerationY, double accelerationZ) {
		super(world, posX, posY, posZ, accelerationX, accelerationY, accelerationZ);
		setSize(1.0f, 1.0f);
		setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
		setPosition(posX, posY, posZ);
		double d6 = MathHelper.sqrt_double(accelerationX * accelerationX + accelerationY * accelerationY + accelerationZ * accelerationZ);
		this.accelerationX = accelerationX / d6 * 0.1;
		this.accelerationY = accelerationY / d6 * 0.1;
		this.accelerationZ = accelerationZ / d6 * 0.1;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDragonFireball(World world, EntityLivingBase entity, double posX, double posY, double posZ) {
		super(world, entity, posX, posY, posZ);
		double z = posZ;
		double y = posY;
		double x = posX;
		shootingEntity = entity;
		setSize(1.0f, 1.0f);
		setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
		setPosition(this.posX, this.posY, this.posZ);
		yOffset = 0.0f;
		motionZ = 0.0;
		motionY = 0.0;
		motionX = 0.0;
		double d3 = MathHelper.sqrt_double((x += rand.nextGaussian() * 0.4) * x + (y += rand.nextGaussian() * 0.4) * y + (z += rand.nextGaussian() * 0.4) * z);
		accelerationX = x / d3 * 0.1;
		accelerationY = y / d3 * 0.1;
		accelerationZ = z / d3 * 0.1;
	}

	@Override
	protected void onImpact(MovingObjectPosition rayTrace) {
		if (rayTrace.entityHit instanceof GOTEntityDragon || rayTrace.entityHit instanceof GOTEntityDragonFireball) {
			return;
		}
		if (!(worldObj.isRemote || rayTrace.entityHit != null && rayTrace.entityHit.equals(player))) {
			if (rayTrace.entityHit != null) {
				rayTrace.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, shootingEntity), 6.0f);
			}
			boolean doTerrainDamage = GOT.canGrief(worldObj);
			worldObj.newExplosion(null, posX, posY, posZ, 15.0f, true, doTerrainDamage);
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		if (!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int) posX, (int) posY, (int) posZ))) {
			setDead();
		} else {
			super.onUpdate();
			setFire(1);
			if (inGround) {
				if (worldObj.getBlock(field_145795_e, field_145793_f, field_145794_g) == field_145796_h) {
					++ticksAlive;
					if (ticksAlive == 600) {
						setDead();
					}
					return;
				}
				inGround = false;
				motionX *= rand.nextFloat() * 0.2f;
				motionY *= rand.nextFloat() * 0.2f;
				motionZ *= rand.nextFloat() * 0.2f;
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
			List<? extends Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
			double d0 = 0.0;
			for (Entity element : list) {
				double d1;
				float f;
				MovingObjectPosition movingobjectposition1;
				if (!element.canBeCollidedWith() || element.isEntityEqual(shootingEntity) && element instanceof GOTEntityDragon && ticksInAir < 25 || (movingobjectposition1 = element.boundingBox.expand(f = 0.3f, f, f).calculateIntercept(vec3, vec31)) == null || !((d1 = vec3.distanceTo(movingobjectposition1.hitVec)) < d0) && d0 != 0.0) {
					continue;
				}
				entity = element;
				d0 = d1;
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
			rotationYaw = (float) (Math.atan2(motionZ, motionX) * 180.0 / Math.PI) + 90.0f;
			rotationPitch = (float) (Math.atan2(f1, motionY) * 180.0 / Math.PI) - 90.0f;
			while (rotationPitch - prevRotationPitch < -180.0f) {
				prevRotationPitch -= 360.0f;
			}
			while (rotationPitch - prevRotationPitch >= 180.0f) {
				prevRotationPitch += 360.0f;
			}
			while (rotationYaw - prevRotationYaw < -180.0f) {
				prevRotationYaw -= 360.0f;
			}
			while (rotationYaw - prevRotationYaw >= 180.0f) {
				prevRotationYaw += 360.0f;
			}
			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2f;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2f;
			float f2 = getMotionFactor();
			if (isInWater()) {
				for (int j = 0; j < 4; ++j) {
					float f3 = 0.25f;
					worldObj.spawnParticle("bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ);
				}
				f2 = 0.8f;
			}
			motionX += accelerationX;
			motionY += accelerationY;
			motionZ += accelerationZ;
			motionX *= f2;
			motionY *= f2;
			motionZ *= f2;
			worldObj.spawnParticle("smoke", posX, posY + 0.5, posZ, 0.0, 0.0, 0.0);
			setPosition(posX, posY, posZ);
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

	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		field_145795_e = p_70037_1_.getShort("xTile");
		field_145793_f = p_70037_1_.getShort("yTile");
		field_145794_g = p_70037_1_.getShort("zTile");
		field_145796_h = Block.getBlockById(p_70037_1_.getByte("inTile") & 0xFF);
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
	public boolean attackEntityFrom(DamageSource source, float f) {
		return !(source.getEntity() instanceof GOTEntityDragonFireball) && super.attackEntityFrom(source, f);
	}

	public void setPlayer(EntityPlayer player) {
		this.player = player;
	}
}

