package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.AxisAlignedBB;

public class GOTEntityAIDragonWatchLiving extends EntityAIBase {

	public GOTEntityDragon dragon;
	public Entity watchedEntity;
	public float maxDist;
	public int watchTicks;
	public float watchChance;

	public GOTEntityAIDragonWatchLiving(GOTEntityDragon dragon, float maxDist, float watchChance) {
		this.dragon = dragon;
		this.maxDist = maxDist;
		this.watchChance = watchChance;
		setMutexBits(2);
	}

	@Override
	public boolean continueExecuting() {
		if (!watchedEntity.isEntityAlive() || dragon.getDistanceSqToEntity(watchedEntity) > maxDist * maxDist) {
			return false;
		}
		return watchTicks > 0;
	}

	@Override
	public void resetTask() {
		dragon.renderYawOffset = 0;
		watchedEntity = null;
	}

	@Override
	public boolean shouldExecute() {
		if (dragon.getRNG().nextFloat() >= watchChance) {
			return false;
		}
		AxisAlignedBB aabb = dragon.boundingBox.expand(maxDist, dragon.height, maxDist);
		watchedEntity = dragon.worldObj.findNearestEntityWithinAABB(EntityLiving.class, aabb, dragon);
		if (watchedEntity != null) {
			if (watchedEntity == dragon.getRidingPlayer()) {
				watchedEntity = null;
			}
			if (watchedEntity == dragon.getOwner()) {
				watchTicks *= 3;
			}
		}
		return watchedEntity != null;
	}

	@Override
	public void startExecuting() {
		watchTicks = 40 + dragon.getRNG().nextInt(40);
	}

	@Override
	public void updateTask() {
		double lx = watchedEntity.posX;
		double ly = watchedEntity.posY + watchedEntity.getEyeHeight();
		double lz = watchedEntity.posZ;
		dragon.getLookHelper().setLookPosition(lx, ly, lz, 10, dragon.getVerticalFaceSpeed());
		watchTicks--;
	}
}
