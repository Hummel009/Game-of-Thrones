package got.common.entity.ai;

import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAIHiringPlayerHurtTarget extends EntityAITarget {
	private final GOTEntityNPC theNPC;

	private EntityLivingBase theTarget;
	private int playerLastAttackerTime;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIHiringPlayerHurtTarget(GOTEntityNPC entity) {
		super(entity, false);
		theNPC = entity;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		if (!theNPC.hiredNPCInfo.isActive || theNPC.hiredNPCInfo.isHalted()) {
			return false;
		}
		EntityPlayer entityplayer = theNPC.hiredNPCInfo.getHiringPlayer();
		if (entityplayer == null) {
			return false;
		}
		theTarget = entityplayer.getLastAttacker();
		int i = entityplayer.getLastAttackerTime();
		return i != playerLastAttackerTime && GOT.canNPCAttackEntity(theNPC, theTarget, true) && isSuitableTarget(theTarget, false);
	}

	@Override
	public void startExecuting() {
		theNPC.setAttackTarget(theTarget);
		theNPC.hiredNPCInfo.wasAttackCommanded = true;
		EntityPlayer entityplayer = theNPC.hiredNPCInfo.getHiringPlayer();
		if (entityplayer != null) {
			playerLastAttackerTime = entityplayer.getLastAttackerTime();
		}
		super.startExecuting();
	}
}