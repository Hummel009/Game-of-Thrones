package got.common.entity.ai;

import got.common.entity.dragon.GOTEntityDragon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityAIDragonFollowOwner extends EntityAIBase {

	public GOTEntityDragon dragon;
	public EntityLivingBase owner;
	public World world;
	public double speed;
	public PathNavigate nav;
	public int updateTicks;
	public float maxDist;
	public float minDist;
	public boolean avoidWater;

	public GOTEntityAIDragonFollowOwner(GOTEntityDragon dragon, double speed, float minDist, float maxDist) {
		this.dragon = dragon;
		this.speed = speed;
		this.minDist = minDist;
		this.maxDist = maxDist;

		nav = dragon.getNavigator();
		world = dragon.worldObj;

		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (nav.noPath() || dragon.isSitting()) {
			return false;
		}

		return true;
	}

	@Override
	public void resetTask() {
		owner = null;
		nav.clearPathEntity();
		dragon.getNavigator().setAvoidsWater(avoidWater);
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase ownerCurrent = dragon.getOwner();

		if (ownerCurrent == null || dragon.isSitting() || dragon.getDistanceSqToEntity(ownerCurrent) < minDist * minDist) {
			return false;
		}

		owner = ownerCurrent;
		return true;
	}

	@Override
	public void startExecuting() {
		updateTicks = 0;
		avoidWater = dragon.getNavigator().getAvoidsWater();
		dragon.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void updateTask() {
		if (dragon.isSitting()) {
			return;
		}
		dragon.getLookHelper().setLookPositionWithEntity(owner, 10, dragon.getVerticalFaceSpeed());
		if (--updateTicks > 0) {
			return;
		}
		updateTicks = 10;
		if (nav.tryMoveToEntityLiving(owner, speed) || dragon.getLeashed() || dragon.getDistanceSqToEntity(owner) < maxDist * maxDist) {
			return;
		}
		int minX = MathHelper.floor_double(owner.posX) - 2;
		int minY = MathHelper.floor_double(owner.posZ) - 2;
		int minZ = MathHelper.floor_double(owner.boundingBox.minY);
		for (int bx = 0; bx <= 4; ++bx) {
			for (int by = 0; by <= 4; ++by) {
				if ((bx < 1 || by < 1 || bx > 3 || by > 3) && World.doesBlockHaveSolidTopSurface(world, minX + bx, minZ - 1, minY + by) && !world.getBlock(minX + bx, minZ, minY + by).isNormalCube() && !world.getBlock(minX + bx, minZ + 1, minY + by).isNormalCube()) {
					dragon.setLocationAndAngles(minX + bx + 0.5, minZ, minY + by + 0.5, dragon.rotationYaw, dragon.rotationPitch);
					nav.clearPathEntity();
					return;
				}
			}
		}
	}
}
