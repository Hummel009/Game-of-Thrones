package got.common.entity.other;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntitySmokeRing extends EntityThrowable {
	private static final int MAX_AGE = 300;

	private int renderSmokeAge = -1;
	private int prevRenderSmokeAge = -1;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySmokeRing(World world) {
		super(world);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySmokeRing(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySmokeRing(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public float func_70182_d() {
		return 0.1f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.0f;
	}

	public float getRenderSmokeAge(float f) {
		float smokeAge = prevRenderSmokeAge + (renderSmokeAge - prevRenderSmokeAge) * f;
		return smokeAge / MAX_AGE;
	}

	private int getSmokeAge() {
		return dataWatcher.getWatchableObjectInt(16);
	}

	private void setSmokeAge(int age) {
		dataWatcher.updateObject(16, age);
	}

	public int getSmokeColour() {
		return dataWatcher.getWatchableObjectByte(17);
	}

	public void setSmokeColour(int colour) {
		dataWatcher.updateObject(17, (byte) colour);
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && m.entityHit == getThrower()) {
			return;
		}
		if (!worldObj.isRemote) {
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (isInWater() && !worldObj.isRemote) {
			setDead();
		}
		prevRenderSmokeAge = renderSmokeAge == -1 ? (renderSmokeAge = getSmokeAge()) : renderSmokeAge;
		if (!worldObj.isRemote) {
			setSmokeAge(getSmokeAge() + 1);
			if (getSmokeAge() >= MAX_AGE) {
				setDead();
			}
		}
		renderSmokeAge = getSmokeAge();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setSmokeAge(nbt.getInteger("SmokeAge"));
		setSmokeColour(nbt.getInteger("SmokeColour"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("SmokeAge", getSmokeAge());
		nbt.setInteger("SmokeColour", getSmokeColour());
	}
}