package got.client.model;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.EntityBodyHelper;

public class GOTModelDragonBodyHelper extends EntityBodyHelper {

	private GOTEntityDragon dragon;
	private int turnTicks;
	private int turnTicksLimit = 20;
	private float prevRotationYawHead;

	public GOTModelDragonBodyHelper(GOTEntityDragon dragon) {
		super(dragon);
		this.dragon = dragon;
	}

	@Override
	public void func_75664_a() {
		double deltaX = dragon.posX - dragon.prevPosX;
		double deltaY = dragon.posZ - dragon.prevPosZ;
		double dist = deltaX * deltaX + deltaY * deltaY;

		float yawSpeed = 90;

		if (dragon.isFlying() || dist > 0.0001) {
			dragon.renderYawOffset = dragon.rotationYaw;
			dragon.rotationYawHead = GOTModelDragonAnimaton.updateRotation(dragon.renderYawOffset, dragon.rotationYawHead, yawSpeed);
			prevRotationYawHead = dragon.rotationYawHead;
			turnTicks = 0;
			return;
		}

		double yawDiff = Math.abs(dragon.rotationYawHead - prevRotationYawHead);

		if (dragon.isSitting() || yawDiff > 15) {
			turnTicks = 0;
			prevRotationYawHead = dragon.rotationYawHead;
		} else {
			turnTicks++;

			if (turnTicks > turnTicksLimit) {
				yawSpeed = Math.max(1 - (float) (turnTicks - turnTicksLimit) / turnTicksLimit, 0) * 75;
			}
		}

		dragon.renderYawOffset = GOTModelDragonAnimaton.updateRotation(dragon.rotationYawHead, dragon.renderYawOffset, yawSpeed);
	}
}
