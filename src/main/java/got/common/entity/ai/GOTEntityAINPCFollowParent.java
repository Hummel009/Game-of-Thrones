package got.common.entity.ai;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class GOTEntityAINPCFollowParent extends EntityAIBase {
	public GOTEntityNPC theNPC;
	public GOTEntityNPC parentNPC;
	public double moveSpeed;
	public int followTick;

	public GOTEntityAINPCFollowParent(GOTEntityNPC npc, double d) {
		theNPC = npc;
		moveSpeed = d;
	}

	@Override
	public boolean continueExecuting() {
		if (!parentNPC.isEntityAlive()) {
			return false;
		}
		double d = theNPC.getDistanceSqToEntity(parentNPC);
		return d >= 9.0 && d <= 256.0;
	}

	@Override
	public void resetTask() {
		parentNPC = null;
	}

	@Override
	public boolean shouldExecute() {
		if (theNPC.familyInfo.getAge() >= 0) {
			return false;
		}
		GOTEntityNPC parent = theNPC.familyInfo.getParentToFollow();
		if (parent == null || theNPC.getDistanceSqToEntity(parent) < 9.0 || theNPC.getDistanceSqToEntity(parent) >= 256.0) {
			return false;
		}
		parentNPC = parent;
		return true;
	}

	@Override
	public void startExecuting() {
		followTick = 0;
	}

	@Override
	public void updateTask() {
		if (followTick <= 0) {
			followTick = 10;
			theNPC.getNavigator().tryMoveToEntityLiving(parentNPC, moveSpeed);
		}
		followTick--;
	}
}
