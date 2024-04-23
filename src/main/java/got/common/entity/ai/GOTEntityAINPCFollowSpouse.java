package got.common.entity.ai;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public class GOTEntityAINPCFollowSpouse extends EntityAIBase {
	private final GOTEntityNPC theNPC;
	private final double moveSpeed;

	private GOTEntityNPC theSpouse;
	private int followTick;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAINPCFollowSpouse(GOTEntityNPC npc, double d) {
		theNPC = npc;
		moveSpeed = d;
	}

	@Override
	public boolean continueExecuting() {
		if (!theSpouse.isEntityAlive()) {
			return false;
		}
		double d = theNPC.getDistanceSqToEntity(theSpouse);
		return d >= 36.0 && d <= 256.0;
	}

	@Override
	public void resetTask() {
		theSpouse = null;
	}

	@Override
	public boolean shouldExecute() {
		GOTEntityNPC spouse = theNPC.getFamilyInfo().getSpouse();
		if (spouse == null) {
			return false;
		}
		if (!spouse.isEntityAlive() || theNPC.getDistanceSqToEntity(spouse) < 36.0 || theNPC.getDistanceSqToEntity(spouse) >= 256.0) {
			return false;
		}
		theSpouse = spouse;
		return true;
	}

	@Override
	public void startExecuting() {
		followTick = 200;
	}

	@Override
	public void updateTask() {
		--followTick;
		if (theNPC.getDistanceSqToEntity(theSpouse) > 144.0 || followTick <= 0) {
			followTick = 200;
			theNPC.getNavigator().tryMoveToEntityLiving(theSpouse, moveSpeed);
		}
	}
}