package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class GOTEntityAIDragonLand extends EntityAIBase {
	private final GOTEntityDragon dragon;

	private Vec3 landTarget;

	@SuppressWarnings("WeakerAccess")
	public GOTEntityAIDragonLand(GOTEntityDragon dragon) {
		this.dragon = dragon;
	}

	@Override
	public boolean shouldExecute() {
		if (!dragon.isFlying() || dragon.getRidingPlayer() != null) {
			return false;
		}
		landTarget = RandomPositionGenerator.findRandomTarget(dragon, 16, 256);

		return landTarget != null;
	}

	@Override
	public void startExecuting() {
		landTarget.yCoord = 0;
		dragon.getWaypoint().setVector(landTarget);
		dragon.setMoveSpeedAirHoriz(1);
		dragon.setMoveSpeedAirVert(0);
	}
}