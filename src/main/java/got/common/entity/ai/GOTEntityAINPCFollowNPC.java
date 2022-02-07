package got.common.entity.ai;

import java.util.List;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class GOTEntityAINPCFollowNPC extends EntityAIBase {
	public GOTEntityNPC entityFollowing;
	public GOTEntityNPC entityToFollow;
	public Class entityClassToFollow;
	public int followDelay;

	public GOTEntityAINPCFollowNPC(GOTEntityNPC npc, Class target) {
		entityFollowing = npc;
		entityClassToFollow = target;
	}

	@Override
	public boolean continueExecuting() {
		double distanceSq = entityFollowing.getDistanceSqToEntity(entityToFollow);
		if (!entityToFollow.isEntityAlive()) {
			return false;
		}
		return distanceSq >= 9.0 && distanceSq <= 256.0;
	}

	@Override
	public void resetTask() {
		entityToFollow = null;
	}

	@Override
	public boolean shouldExecute() {
		List<GOTEntityNPC> list = entityFollowing.worldObj.getEntitiesWithinAABB(entityClassToFollow, entityFollowing.boundingBox.expand(64.0f, 3.0, 64.0f));
		GOTEntityNPC entityNPC = null;
		double distanceSq = Double.MAX_VALUE;
		for (GOTEntityNPC npcCandidate : list) {
			double d = entityFollowing.getDistanceSqToEntity(npcCandidate);
			if (d > distanceSq) {
				continue;
			}
			distanceSq = d;
			entityNPC = npcCandidate;
		}
		if (entityNPC == null || entityFollowing.currentAttackMode != GOTEntityNPC.AttackMode.IDLE || distanceSq < 9.0) {
			return false;
		}
		entityToFollow = entityNPC;
		return true;
	}

	@Override
	public void startExecuting() {
		followDelay = 0;
	}

	@Override
	public void updateTask() {
		if (--followDelay <= 0) {
			followDelay = 10;
			entityFollowing.getNavigator().tryMoveToEntityLiving(entityToFollow, 1.0);
		}
	}
}