package got.common.entity.dragon;

import cpw.mods.fml.relauncher.ReflectionHelper;
import got.client.model.GOTModelDragonAnimaton;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class GOTEntityFlyingTameable extends EntityTameable implements GOTBiome.ImmuneToFrost {
	public static Logger L = LogManager.getLogger();
	public static int IN_AIR_THRESH = 10;
	public static IAttribute MOVE_SPEED_AIR = new RangedAttribute("generic.movementSpeedAir", 1.5, 0.0, Double.MAX_VALUE).setDescription("Movement Speed Air").setShouldWatch(true);
	public static int INDEX_FLYING = 18;
	public static String[] ENTITYAITASKS_TICKRATE = {"tickRate", "field_75779_e"};
	public static int INDEX_CAN_FLY = 19;
	public static String NBT_FLYING = "Flying";
	public static String NBT_CAN_FLY = "CanFly";
	public EntityAITasks airTasks;
	public GOTDragonFlightWaypoint waypoint;
	public double airSpeedHorizonal = 1.5;
	public double airSpeedVertical;
	public float yawAdd;
	public int yawSpeed = 30;
	public int inAirTicks;

	protected GOTEntityFlyingTameable(World world) {
		super(world);
		waypoint = new GOTDragonFlightWaypoint(this);
		airTasks = new EntityAITasks(world != null ? world.theProfiler : null);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(MOVE_SPEED_AIR);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(INDEX_FLYING, Byte.valueOf((byte) 0));
		dataWatcher.addObject(INDEX_CAN_FLY, Byte.valueOf((byte) 0));
	}

	@Override
	public void fall(float par1) {
		if (!isCanFly()) {
			super.fall(par1);
		}
	}

	public double getAltitude() {
		int blockX = (int) (posX - 0.5);
		int blockZ = (int) (posZ - 0.5);
		return posY - worldObj.getHeightValue(blockX, blockZ);
	}

	public double getMoveSpeedAirHoriz() {
		return airSpeedHorizonal;
	}

	public void setMoveSpeedAirHoriz(double airSpeedHorizonal) {
		L.trace("setMoveSpeedAirHoriz({})", airSpeedHorizonal);
		this.airSpeedHorizonal = airSpeedHorizonal;
	}

	public double getMoveSpeedAirVert() {
		return airSpeedVertical;
	}

	public void setMoveSpeedAirVert(double airSpeedVertical) {
		L.trace("setMoveSpeedAirVert({})", airSpeedVertical);
		this.airSpeedVertical = airSpeedVertical;
	}

	public GOTDragonFlightWaypoint getWaypoint() {
		return waypoint;
	}

	public boolean isAirAIEnabled() {
		return isFlying();
	}

	public boolean isCanFly() {
		return (dataWatcher.getWatchableObjectByte(INDEX_CAN_FLY) & 1) != 0;
	}

	public void setCanFly(boolean canFly) {
		L.trace("setCanFly({})", canFly);
		dataWatcher.updateObject(INDEX_CAN_FLY, Byte.valueOf(canFly ? (byte) 1 : (byte) 0));
	}

	public boolean isClient() {
		return worldObj.isRemote;
	}

	public boolean isFlying() {
		return (dataWatcher.getWatchableObjectByte(INDEX_FLYING) & 1) != 0;
	}

	public void setFlying(boolean flying) {
		dataWatcher.updateObject(INDEX_FLYING, Byte.valueOf(flying ? (byte) 1 : (byte) 0));
	}

	public boolean isGroundAIEnabled() {
		return !isFlying();
	}

	public boolean isServer() {
		return !worldObj.isRemote;
	}

	public void liftOff() {
		L.trace("liftOff");
		if (isCanFly()) {
			jump();
			motionY += 0.5;
			inAirTicks += 20;
			waypoint.clear();
		}
	}

	@Override
	public void onLivingUpdate() {
		if (!isCanFly()) {
			if (isFlying()) {
				setFlying(false);
			}

			super.onLivingUpdate();
			return;
		}

		if (isServer()) {
			if (!onGround) {
				inAirTicks++;
			} else {
				inAirTicks = 0;
			}

			setFlying(inAirTicks > IN_AIR_THRESH);
		}

		if (isFlying()) {
			if (isClient()) {
				onUpdateFlyingClient();
			} else {
				onUpdateFlyingServer();
			}
		} else {
			super.onLivingUpdate();
		}
	}

	public void onUpdateFlyingClient() {
		if (newPosRotationIncrements > 0) {
			double px = posX + (newPosX - posX) / newPosRotationIncrements;
			double py = posY + (newPosY - posY) / newPosRotationIncrements;
			double pz = posZ + (newPosZ - posZ) / newPosRotationIncrements;
			double newYaw = GOTModelDragonAnimaton.normDeg(newRotationYaw - rotationYaw);
			rotationYaw += (float) newYaw / newPosRotationIncrements;
			rotationPitch += ((float) newRotationPitch - rotationPitch) / newPosRotationIncrements;

			--newPosRotationIncrements;

			setPosition(px, py, pz);
			setRotation(rotationYaw, rotationPitch);
		}
	}

	public void onUpdateFlyingServer() {
		float friction = 0;
		if (!waypoint.isNear()) {
			double deltaX = waypoint.posX - posX;
			double deltaY = waypoint.posY - posY;
			double deltaZ = waypoint.posZ - posZ;
			double speedAir = getEntityAttribute(MOVE_SPEED_AIR).getAttributeValue();
			double speedHoriz = airSpeedHorizonal * speedAir;
			double speedVert = airSpeedVertical;
			deltaY /= Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
			deltaY = GOTModelDragonAnimaton.clamp(deltaY, -speedHoriz, speedHoriz) / 3;
			double motionHypot = Math.hypot(motionX, motionZ) + 1;
			double newYaw = Math.toDegrees(Math.PI * 2 - Math.atan2(deltaX, deltaZ));
			double yawDelta = GOTModelDragonAnimaton.normDeg(newYaw - rotationYaw);
			yawDelta = GOTModelDragonAnimaton.clamp(yawDelta, -yawSpeed, yawSpeed);
			yawAdd *= 0.8f;
			yawAdd += yawDelta * (0.7 / motionHypot);
			rotationYaw += yawAdd * 0.1f;
			Vec3 motionVec = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();
			Vec3 deltaVec = Vec3.createVectorHelper(deltaX, deltaY, deltaZ).normalize();
			Vec3 rotVec = Vec3.createVectorHelper(-Math.sin(Math.toRadians(rotationYaw)), motionY, Math.cos(Math.toRadians(rotationYaw))).normalize();

			float tmp1 = (float) (rotVec.dotProduct(deltaVec) + 0.5) / 1.5f;
			if (tmp1 < 0) {
				tmp1 = 0;
			}
			float tmp2 = (float) (2 / (motionHypot + 1));
			float acc = 0.06f * (tmp1 * tmp2 + (1 - tmp2));
			motionY = deltaY + speedVert;
			moveFlying(0, (float) speedHoriz, acc);
			friction = (float) (motionVec.dotProduct(rotVec) + 1) / 2.0f;
		}
		friction = 0.8f + 0.15f * friction;
		if (inWater) {
			friction *= 0.8f;
		}
		motionX *= friction;
		motionY *= friction;
		motionZ *= friction;
		moveEntity(motionX, motionY, motionZ);
		if (isAIEnabled()) {
			worldObj.theProfiler.startSection("newAi");
			updateAITasks();
			worldObj.theProfiler.endSection();
		} else {
			worldObj.theProfiler.startSection("oldAi");
			updateEntityActionState();
			worldObj.theProfiler.endSection();
			rotationYawHead = rotationYaw;
		}
		List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.2, 0, 0));
		if (entities != null && !entities.isEmpty()) {
			for (Entity entity : entities) {
				if (entity.canBePushed()) {
					entity.applyEntityCollision(this);
				}
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setFlying(nbt.getBoolean(NBT_FLYING));
		setCanFly(nbt.getBoolean(NBT_CAN_FLY));
		waypoint.readFromNBT(nbt);
	}

	public void setTasksEnabled(EntityAITasks tasks, boolean flag) {
		ReflectionHelper.setPrivateValue(EntityAITasks.class, tasks, flag ? 3 : Integer.MAX_VALUE, ENTITYAITASKS_TICKRATE);
	}

	@Override
	public void updateAITasks() {
		setTasksEnabled(tasks, isGroundAIEnabled());
		setTasksEnabled(airTasks, isAirAIEnabled());

		super.updateAITasks();
		airTasks.onUpdateTasks();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean(NBT_FLYING, isFlying());
		nbt.setBoolean(NBT_CAN_FLY, isCanFly());
		waypoint.writeToNBT(nbt);
	}
}
