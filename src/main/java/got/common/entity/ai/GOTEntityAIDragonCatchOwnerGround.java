package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;

public class GOTEntityAIDragonCatchOwnerGround extends GOTEntityAIDragonCatchOwner {
	@SuppressWarnings("WeakerAccess")
	public GOTEntityAIDragonCatchOwnerGround(GOTEntityDragon dragon) {
		super(dragon);
		setMutexBits(0xffffffff);
	}

	@Override
	public void updateTask() {
		dragon.liftOff();
	}
}