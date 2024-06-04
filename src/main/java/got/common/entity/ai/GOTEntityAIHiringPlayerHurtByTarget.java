package got.common.entity.ai;

import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAIHiringPlayerHurtByTarget extends EntityAITarget {
	private final GOTEntityNPC theNPC;

	private EntityLivingBase theTarget;
	private int playerRevengeTimer;

	public GOTEntityAIHiringPlayerHurtByTarget(GOTEntityNPC entity) {
		super(entity, false);
		theNPC = entity;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (!theNPC.getHireableInfo().isActive() || theNPC.getHireableInfo().isHalted()) {
			return false;
		}
		EntityPlayer entityplayer = theNPC.getHireableInfo().getHiringPlayer();
		if (entityplayer == null) {
			return false;
		}
		theTarget = entityplayer.getAITarget();
		int i = entityplayer.func_142015_aE();
		return i != playerRevengeTimer && GOT.canNPCAttackEntity(theNPC, theTarget, true) && isSuitableTarget(theTarget, false);
	}

	@Override
	public void startExecuting() {
		theNPC.setAttackTarget(theTarget);
		EntityPlayer entityplayer = theNPC.getHireableInfo().getHiringPlayer();
		if (entityplayer != null) {
			playerRevengeTimer = entityplayer.func_142015_aE();
		}
		super.startExecuting();
	}
}