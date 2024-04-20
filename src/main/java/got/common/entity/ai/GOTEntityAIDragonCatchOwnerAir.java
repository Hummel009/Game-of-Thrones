package got.common.entity.ai;

import got.client.model.GOTModelDragonAnimaton;
import got.common.entity.dragon.GOTDragonFlightWaypoint;
import got.common.entity.dragon.GOTEntityDragon;

public class GOTEntityAIDragonCatchOwnerAir extends GOTEntityAIDragonCatchOwner {
	@SuppressWarnings("WeakerAccess")
	public GOTEntityAIDragonCatchOwnerAir(GOTEntityDragon dragon) {
		super(dragon);
	}

	@Override
	public void updateTask() {
		GOTDragonFlightWaypoint wp = dragon.getWaypoint();
		wp.setEntity(owner);

		double dist = wp.getDistance();
		double yOfs = GOTModelDragonAnimaton.clamp(dist, 0, 64);

		wp.setPosY(wp.getPosY() - (int) yOfs);

		if (wp.isNear()) {
			owner.mountEntity(dragon);
		}
		dragon.setMoveSpeedAirHoriz(1);
	}
}