package got.common.entity.ai;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAIFollowHiringPlayer extends EntityAIBase {
	private final GOTEntityNPC theNPC;
	private final double moveSpeed;
	private final float maxNearDist;
	private final float minFollowDist;

	private EntityLiving bannerBearerTarget;
	private EntityPlayer theHiringPlayer;
	private boolean avoidsWater;
	private int followTick;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIFollowHiringPlayer(GOTEntityNPC entity) {
		theNPC = entity;
		double entityMoveSpeed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
		moveSpeed = 1.0 / entityMoveSpeed * 0.37;
		minFollowDist = 8.0f;
		maxNearDist = 6.0f;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (theNPC.getHireableInfo().isActive() && theNPC.getHireableInfo().getHiringPlayer() != null && theNPC.getHireableInfo().shouldFollowPlayer() && !theNPC.getNavigator().noPath()) {
			EntityLivingBase target = bannerBearerTarget != null ? bannerBearerTarget : theHiringPlayer;
			return theNPC.getDistanceSqToEntity(target) > maxNearDist * maxNearDist;
		}
		return false;
	}

	@Override
	public void resetTask() {
		theHiringPlayer = null;
		bannerBearerTarget = null;
		theNPC.getNavigator().clearPathEntity();
		theNPC.getNavigator().setAvoidsWater(avoidsWater);
	}

	@Override
	public boolean shouldExecute() {
		if (!theNPC.getHireableInfo().isActive()) {
			return false;
		}
		EntityPlayer entityplayer = theNPC.getHireableInfo().getHiringPlayer();
		if (entityplayer == null) {
			return false;
		}
		theHiringPlayer = entityplayer;
		return theNPC.getHireableInfo().shouldFollowPlayer() && theNPC.getDistanceSqToEntity(entityplayer) >= minFollowDist * minFollowDist;
	}

	@Override
	public void startExecuting() {
		followTick = 0;
		avoidsWater = theNPC.getNavigator().getAvoidsWater();
		theNPC.getNavigator().setAvoidsWater(false);
	}

	@Override
	public void updateTask() {
		EntityLivingBase target = bannerBearerTarget != null ? bannerBearerTarget : theHiringPlayer;
		theNPC.getLookHelper().setLookPositionWithEntity(target, 10.0f, theNPC.getVerticalFaceSpeed());
		if (theNPC.getHireableInfo().shouldFollowPlayer()) {
			--followTick;
			if (followTick <= 0) {
				followTick = 10;
				if (!theNPC.getNavigator().tryMoveToEntityLiving(target, moveSpeed) && theNPC.getHireableInfo().isTeleportAutomatically()) {
					double d = theNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
					d = Math.max(d, 24.0);
					if (theNPC.getDistanceSqToEntity(theHiringPlayer) > d * d) {
						theNPC.getHireableInfo().tryTeleportToHiringPlayer(false);
					}
				}
			}
		}
	}
}