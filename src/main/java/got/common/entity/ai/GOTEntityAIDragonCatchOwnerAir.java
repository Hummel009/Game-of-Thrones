package got.common.entity.ai;

import got.client.model.GOTModelDragonAnimaton;
import got.common.entity.dragon.*;

public class GOTEntityAIDragonCatchOwnerAir extends GOTEntityAIDragonCatchOwner {

	public GOTEntityAIDragonCatchOwnerAir(GOTEntityDragon dragon) {
		super(dragon);
	}

	@Override
	public void updateTask() {
		GOTDragonFlightWaypoint wp = dragon.getWaypoint();
		wp.setEntity(owner);

		double dist = wp.getDistance();
		double yOfs = GOTModelDragonAnimaton.clamp(dist, 0, 64);

		wp.posY -= (int) yOfs;

		if (wp.isNear()) {
			owner.mountEntity(dragon);
		}

		dragon.setMoveSpeedAirHoriz(1);
	}
}
