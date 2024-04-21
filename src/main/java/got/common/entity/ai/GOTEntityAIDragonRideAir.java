package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.init.Items;
import net.minecraft.util.Vec3;

public class GOTEntityAIDragonRideAir extends GOTEntityAIDragonRide {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIDragonRideAir(GOTEntityDragon dragon) {
		super(dragon);
	}

	@Override
	public void updateTask() {
		super.updateTask();

		double dist = 100;

		if (GOTEntityDragon.hasEquipped(rider, Items.carrot_on_a_stick)) {
			Vec3 wp = rider.getLookVec();

			wp.xCoord *= dist;
			wp.yCoord *= dist;
			wp.zCoord *= dist;

			wp.xCoord += dragon.posX;
			wp.yCoord += dragon.posY;
			wp.zCoord += dragon.posZ;

			dragon.getWaypoint().setVector(wp);

			dragon.setMoveSpeedAirHoriz(1);
			dragon.setMoveSpeedAirVert(0);
		} else {
			Vec3 wp = dragon.getLookVec();

			wp.xCoord *= dist;
			wp.yCoord *= dist;
			wp.zCoord *= dist;

			wp.xCoord += dragon.posX;
			wp.yCoord += dragon.posY;
			wp.zCoord += dragon.posZ;

			dragon.getWaypoint().setVector(wp);

			double speedAir = 0;

			if (rider.moveForward != 0) {
				speedAir = 1;

				if (rider.moveForward < 0) {
					speedAir *= 0.5;
				}
				speedAir *= rider.moveForward;
			}
			dragon.setMoveSpeedAirHoriz(speedAir);

			if (rider.moveStrafing != 0) {
				dragon.rotationYaw -= rider.moveStrafing * 6;
			}
			double verticalSpeed = 0;

			if (isFlyUp()) {
				verticalSpeed = 0.5f;
			} else if (isFlyDown()) {
				verticalSpeed = -0.5f;
			}
			dragon.setMoveSpeedAirVert(verticalSpeed);
		}
	}
}