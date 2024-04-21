package got.common.entity.ai;

import got.common.entity.dragon.GOTDragonLifeStage;
import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityAIDragonDragonMate extends EntityAIBase {
	private final GOTEntityDragon dragon;
	private final World theWorld;
	private final double speed;

	private GOTEntityDragon dragonMate;
	private int spawnBabyDelay;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIDragonDragonMate(GOTEntityDragon dragon, double speed) {
		this.dragon = dragon;
		this.speed = speed;
		theWorld = dragon.worldObj;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return dragonMate.isEntityAlive() && dragonMate.isInLove() && spawnBabyDelay < 60;
	}

	private GOTEntityDragon getNearbyMate() {
		double range = 12;
		List<GOTEntityDragon> nearbyEntities = theWorld.getEntitiesWithinAABB(GOTEntityDragon.class, dragon.boundingBox.expand(range, range, range));

		for (GOTEntityDragon nearbyDragon : nearbyEntities) {
			if (dragon.canMateWith(nearbyDragon)) {
				return nearbyDragon;
			}
		}
		return null;
	}

	@Override
	public void resetTask() {
		dragonMate = null;
		spawnBabyDelay = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (!dragon.isInLove()) {
			return false;
		}
		dragonMate = getNearbyMate();
		return dragonMate != null;
	}

	private void spawnBaby() {
		GOTEntityDragon dragonBaby = (GOTEntityDragon) dragon.createChild(dragonMate);

		if (dragonBaby != null) {
			dragon.setGrowingAge(6000);
			dragonMate.setGrowingAge(6000);

			dragon.resetInLove();
			dragonMate.resetInLove();

			dragonBaby.setLocationAndAngles(dragon.posX, dragon.posY, dragon.posZ, 0, 0);
			dragonBaby.getLifeStageHelper().setLifeStage(GOTDragonLifeStage.EGG);

			theWorld.spawnEntityInWorld(dragonBaby);

		}
	}

	@Override
	public void updateTask() {
		dragon.getLookHelper().setLookPositionWithEntity(dragonMate, 10.0F, dragon.getVerticalFaceSpeed());
		dragon.getNavigator().tryMoveToEntityLiving(dragonMate, speed);

		++spawnBabyDelay;

		if (spawnBabyDelay == 60) {
			spawnBaby();
		}
	}
}