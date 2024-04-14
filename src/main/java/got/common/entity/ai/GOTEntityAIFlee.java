package got.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class GOTEntityAIFlee extends EntityAIBase {
	private final EntityCreature theEntity;
	private final double speed;

	private boolean firstPath;
	private double attackerX;
	private double attackerY;
	private double attackerZ;
	private int timer;

	@SuppressWarnings("WeakerAccess")
	public GOTEntityAIFlee(EntityCreature entity, double d) {
		theEntity = entity;
		speed = d;
		setMutexBits(1);
	}

	@Override
	public boolean continueExecuting() {
		return timer > 0;
	}

	@Override
	public void resetTask() {
		theEntity.getNavigator().clearPathEntity();
		timer = 0;
		firstPath = false;
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase attacker = theEntity.getAITarget();
		if (attacker == null) {
			return false;
		}
		attackerX = attacker.posX;
		attackerY = attacker.posY;
		attackerZ = attacker.posZ;
		return true;
	}

	@Override
	public void startExecuting() {
		timer = 60 + theEntity.getRNG().nextInt(50);
	}

	@Override
	public void updateTask() {
		Vec3 vec3;
		--timer;
		if ((!firstPath || theEntity.getNavigator().noPath()) && (vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theEntity, 16, 7, Vec3.createVectorHelper(attackerX, attackerY, attackerZ))) != null && theEntity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, speed)) {
			theEntity.setRevengeTarget(null);
			firstPath = true;
		}
	}
}