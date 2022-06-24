package got.common.entity.animal;

import got.common.block.other.GOTBlockBomb;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GOTEntityBallLightning extends EntityLiving implements GOTBiome.ImmuneToFrost, GOTBiome.ImmuneToHeat {
	public ChunkCoordinates currentFlightTarget;
	 
	public GOTEntityBallLightning(World world) {
		super(world);
		setSize(0.5f, 0.5f);
		yOffset = 2;
	}

	@Override
	public boolean allowLeashing() {
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(rand, 0.08, 0.12));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		return super.attackEntityFrom(damagesource, 0);
	}

	@Override
	public boolean canDespawn() {
		return false;
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void collideWithEntity(Entity entity) {
		if (!worldObj.isRemote) {
			explode();
		}
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		if (!worldObj.isRemote) {
			explode();
		}
	}

	public void explode() {
		int strength = GOTBlockBomb.getBombStrengthLevel(1);
		worldObj.newExplosion(this, posX, posY, posZ, (strength + 1) * 4.0f, false, false);
		this.setDead();
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	public void fall(float f) {
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			if (j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		motionY *= 0.6;
	}

	@Override
	public void updateAITasks() {
		super.updateAITasks();
		if (currentFlightTarget != null && (!worldObj.isAirBlock(currentFlightTarget.posX, currentFlightTarget.posY, currentFlightTarget.posZ) || currentFlightTarget.posY < 1)) {
			currentFlightTarget = null;
		}
		if (currentFlightTarget == null || rand.nextInt(30) == 0 || currentFlightTarget.getDistanceSquared((int) posX, (int) posY, (int) posZ) < 4.0f) {
			currentFlightTarget = new ChunkCoordinates((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
		}
		double speed = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		double d0 = currentFlightTarget.posX + 0.5 - posX;
		double d1 = currentFlightTarget.posY + 0.5 - posY;
		double d2 = currentFlightTarget.posZ + 0.5 - posZ;
		motionX += (Math.signum(d0) * 0.5 - motionX) * speed;
		motionY += (Math.signum(d1) * 0.7 - motionY) * speed;
		motionZ += (Math.signum(d2) * 0.5 - motionZ) * speed;
		float f = (float) (Math.atan2(motionZ, motionX) * 180.0 / 3.141592653589793) - 90.0f;
		float f1 = MathHelper.wrapAngleTo180_float(f - rotationYaw);
		moveForward = 0.5f;
		rotationYaw += f1;
	}

	@Override
	public void updateFallState(double d, boolean flag) {
	}

}
