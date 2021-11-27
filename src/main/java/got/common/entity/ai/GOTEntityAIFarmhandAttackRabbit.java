package got.common.entity.ai;

import got.common.entity.animal.GOTEntityRabbit;
import got.common.entity.other.GOTEntityNPC;

public class GOTEntityAIFarmhandAttackRabbit extends GOTEntityAINearestAttackableTargetBasic {
	public GOTEntityNPC theNPC;

	public GOTEntityAIFarmhandAttackRabbit(GOTEntityNPC npc) {
		super(npc, GOTEntityRabbit.class, 0, false);
		theNPC = npc;
	}

	@Override
	public double getTargetDistance() {
		return 8.0;
	}

	@Override
	public boolean shouldExecute() {
		if (theNPC.hiredNPCInfo.isActive && !theNPC.hiredNPCInfo.isGuardMode()) {
			return false;
		}
		return super.shouldExecute();
	}
}
