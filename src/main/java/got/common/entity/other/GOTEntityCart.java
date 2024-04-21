package got.common.entity.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.network.GOTPacketCargocartUpdate;
import got.common.network.GOTPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GOTEntityCart extends Entity {
	private Entity pulling;
	private double factor;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYaw;
	private double lerpPitch;
	private float wheelrot;
	private int lerpSteps;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCart(World worldIn) {
		super(worldIn);
		setSize(1.5f, 1.4f);
		stepHeight = 1.0f;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCart(World worldIn, double x, double y, double z) {
		this(worldIn);
		setPosition(x, y, z);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public void entityInit() {
	}

	@Override
	public void fall(float distance) {
	}

	@Override
	public Vec3 getLookVec() {
		float f = MathHelper.cos(-rotationYaw * 0.017453292f - 3.1415927f);
		float f1 = MathHelper.sin(-rotationYaw * 0.017453292f - 3.1415927f);
		float f2 = -MathHelper.cos(-rotationPitch * 0.017453292f);
		float f3 = MathHelper.sin(-rotationPitch * 0.017453292f);
		return Vec3.createVectorHelper(f1 * f2, f3, f * f2);
	}

	public Entity getPulling() {
		return pulling;
	}

	public void setPulling(Entity pullingIn) {
		pulling = pullingIn;
		if (pulling == null) {
			factor = 0.0;
		}
	}

	@SideOnly(Side.CLIENT)
	public float getWheelRotation() {
		if (worldObj.isRemote && Minecraft.getMinecraft().isGamePaused()) {
			factor = 0.0;
		}
		wheelrot = (float) (wheelrot - 0.11999999731779099 * factor);
		return wheelrot;
	}

	@Override
	public void mountEntity(Entity passenger) {
		super.mountEntity(passenger);
		if (pulling.riddenByEntity != null && lerpSteps > 0) {
			lerpSteps = 0;
			posX = lerpX;
			posY = lerpY;
			posZ = lerpZ;
			rotationYaw = (float) lerpYaw;
			rotationPitch = (float) lerpPitch;
		}
	}

	@Override
	public void onUpdate() {
		if (pulling != null) {
			rotationYaw = (float) Math.toDegrees(-Math.atan2(pulling.posX - posX, pulling.posZ - posZ));
			Vec3 moveVec = Vec3.createVectorHelper(pulling.posX - posX - getLookVec().xCoord * 2.4, pulling.posY - posY, pulling.posZ - posZ - getLookVec().zCoord * 2.4);
			factor = -Math.sqrt(moveVec.xCoord * moveVec.xCoord + moveVec.zCoord * moveVec.zCoord);
			motionX = moveVec.xCoord;
			motionY = moveVec.yCoord;
			motionZ = moveVec.zCoord;
			tickLerp();
			moveEntity(motionX, motionY, motionZ);
			if (moveVec.subtract(getLookVec()).lengthVector() > 1.0 && posY == pulling.posY) {
				factor = -factor;
			}
			if (factor > 3.0 && !worldObj.isRemote) {
				setPulling(null);
				((WorldServer) worldObj).getEntityTracker().func_151247_a(this, GOTPacketHandler.NETWORK_WRAPPER.getPacketFrom(new GOTPacketCargocartUpdate(-1, getEntityId())));
			}
		}
		super.onUpdate();
		if (!onGround) {
			moveEntity(0.0, -0.8, 0.0);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements) {
		lerpX = x;
		lerpY = y;
		lerpZ = z;
		lerpYaw = yaw;
		lerpPitch = pitch;
		lerpSteps = 10;
	}

	private void tickLerp() {
		if (worldObj.isRemote && lerpSteps > 0 && pulling.riddenByEntity == null) {
			double d0 = posX + (lerpX - posX) / lerpSteps;
			double d1 = posY + (lerpY - posY) / lerpSteps;
			double d2 = posZ + (lerpZ - posZ) / lerpSteps;
			double d3 = MathHelper.wrapAngleTo180_double(lerpYaw - rotationYaw);
			rotationYaw = (float) (rotationYaw + d3 / lerpSteps);
			rotationPitch = (float) (rotationPitch + (lerpPitch - rotationPitch) / lerpSteps);
			--lerpSteps;
			setPosition(d0, d1, d2);
			setRotation(rotationYaw, rotationPitch);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
	}
}