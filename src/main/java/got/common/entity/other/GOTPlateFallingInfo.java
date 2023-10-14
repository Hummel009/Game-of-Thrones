package got.common.entity.other;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class GOTPlateFallingInfo implements IExtendedEntityProperties {
	public static String propID = "got_plateFall";
	public static int tickDelayFactor = 1;
	public static int stackSize = 64;
	public static int numFallers = 65;
	public Entity theEntity;
	public int updateTick;
	public float[] posXTicksAgo = new float[65];
	public boolean[] isFalling = new boolean[65];
	public float[] fallerPos = new float[65];
	public float[] prevFallerPos = new float[65];
	public float[] fallerSpeed = new float[65];

	public GOTPlateFallingInfo(Entity entity) {
		theEntity = entity;
	}

	public static GOTPlateFallingInfo getOrCreateFor(Entity entity, boolean create) {
		GOTPlateFallingInfo props = (GOTPlateFallingInfo) entity.getExtendedProperties(propID);
		if (props == null && create) {
			props = new GOTPlateFallingInfo(entity);
			entity.registerExtendedProperties(propID, props);
		}
		return props;
	}

	public float getFoodOffsetY(int food, float f) {
		return getOffsetY(food - 1, f);
	}

	public float getOffsetY(int index, float f) {
		index = MathHelper.clamp_int(index, 0, fallerPos.length - 1);
		float pos = prevFallerPos[index] + (fallerPos[index] - prevFallerPos[index]) * f;
		float offset = pos - (float) (theEntity.prevPosY + (theEntity.posY - theEntity.prevPosY) * f);
		return Math.max(offset, 0.0f);
	}

	public float getPlateOffsetY(float f) {
		return getOffsetY(0, f);
	}

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
	}

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
	}

	public void update() {
		float curPos = (float) theEntity.posY;
		if (!theEntity.onGround && theEntity.motionY > 0.0) {
			for (int l = 0; l < posXTicksAgo.length; ++l) {
				posXTicksAgo[l] = Math.max(posXTicksAgo[l], curPos);
			}
		}
		for (int l = posXTicksAgo.length - 1; l > 0; --l) {
			posXTicksAgo[l] = posXTicksAgo[l - 1];
		}
		posXTicksAgo[0] = curPos;
		++updateTick;
		for (int l = 0; l < fallerPos.length; ++l) {
			prevFallerPos[l] = fallerPos[l];
			float pos = fallerPos[l];
			float speed = fallerSpeed[l];
			boolean fall = isFalling[l];
			if (!fall && pos > posXTicksAgo[l]) {
				fall = true;
			}
			isFalling[l] = fall;
			if (fall) {
				speed = (float) (speed + 0.08);
				pos -= speed;
				speed = (float) (speed * 0.98);
			} else {
				speed = 0.0f;
			}
			if (pos < curPos) {
				pos = curPos;
				speed = 0.0f;
				isFalling[l] = false;
			}
			fallerPos[l] = pos;
			fallerSpeed[l] = speed;
		}
	}
}
