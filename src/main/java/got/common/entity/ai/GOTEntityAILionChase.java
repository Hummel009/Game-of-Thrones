package got.common.entity.ai;

import got.common.entity.animal.GOTEntityLionBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class GOTEntityAILionChase extends EntityAIBase {
	private final GOTEntityLionBase theLion;
	private final double speed;

	private EntityCreature targetEntity;
	private int chaseTimer;
	private int lionRePathDelay;

	public GOTEntityAILionChase(GOTEntityLionBase lion, double d) {
		theLion = lion;
		speed = d;
		setMutexBits(1);
	}

	@Override
	public boolean continueExecuting() {
		return targetEntity != null && targetEntity.isEntityAlive() && chaseTimer > 0 && theLion.getDistanceSqToEntity(targetEntity) < 256.0;
	}

	@Override
	public void resetTask() {
		chaseTimer = 0;
		lionRePathDelay = 0;
	}

	@Override
	public boolean shouldExecute() {
		if (theLion.isChild() || theLion.isInLove() || theLion.getRNG().nextInt(800) != 0) {
			return false;
		}
		List<EntityAnimal> entities = theLion.worldObj.getEntitiesWithinAABB(EntityAnimal.class, theLion.boundingBox.expand(12.0, 12.0, 12.0));
		ArrayList<EntityAnimal> validTargets = new ArrayList<>();
		for (EntityAnimal entity : entities) {
			if (entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) == null) {
				validTargets.add(entity);
			}
		}
		if (validTargets.isEmpty()) {
			return false;
		}
		targetEntity = validTargets.get(theLion.getRNG().nextInt(validTargets.size()));
		return true;
	}

	@Override
	public void startExecuting() {
		chaseTimer = 300 + theLion.getRNG().nextInt(400);
	}

	@Override
	public void updateTask() {
		Vec3 vec3;
		--chaseTimer;
		theLion.getLookHelper().setLookPositionWithEntity(targetEntity, 30.0f, 30.0f);
		--lionRePathDelay;
		if (lionRePathDelay <= 0) {
			lionRePathDelay = 10;
			theLion.getNavigator().tryMoveToEntityLiving(targetEntity, speed);
		}
		if (targetEntity.getNavigator().noPath() && (vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(targetEntity, 16, 7, Vec3.createVectorHelper(theLion.posX, theLion.posY, theLion.posZ))) != null) {
			targetEntity.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 2.0);
		}
	}
}