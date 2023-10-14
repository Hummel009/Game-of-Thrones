package got.common.entity.ai;

import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.IBandit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

import java.util.List;

public class GOTEntityAIBanditFlee extends EntityAIBase {
	public IBandit theBandit;
	public GOTEntityNPC theBanditAsNPC;
	public double speed;
	public double range;
	public EntityPlayer targetPlayer;

	public GOTEntityAIBanditFlee(IBandit bandit, double d) {
		theBandit = bandit;
		theBanditAsNPC = theBandit.getBanditAsNPC();
		speed = d;
		range = theBanditAsNPC.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue();
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (targetPlayer == null || !targetPlayer.isEntityAlive() || targetPlayer.capabilities.isCreativeMode) {
			return false;
		}
		return theBanditAsNPC.getAttackTarget() == null && theBanditAsNPC.getDistanceSqToEntity(targetPlayer) < range * range;
	}

	public EntityPlayer findNearestPlayer() {
		List<EntityPlayer> players = theBanditAsNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theBanditAsNPC.boundingBox.expand(range, range, range));
		double distance = range;
		EntityPlayer ret = null;
		for (EntityPlayer player : players) {
			double d;
			if (player.capabilities.isCreativeMode || (d = theBanditAsNPC.getDistanceToEntity(player)) >= distance) {
				continue;
			}
			distance = d;
			ret = player;
		}
		return ret;
	}

	@Override
	public void resetTask() {
		targetPlayer = null;
	}

	@Override
	public boolean shouldExecute() {
		if (theBanditAsNPC.getAttackTarget() != null || theBandit.getBanditInventory().isEmpty()) {
			return false;
		}
		targetPlayer = findNearestPlayer();
		return targetPlayer != null;
	}

	@Override
	public void updateTask() {
		if (theBanditAsNPC.getNavigator().noPath()) {
			Vec3 away = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theBanditAsNPC, (int) range, 10, Vec3.createVectorHelper(targetPlayer.posX, targetPlayer.posY, targetPlayer.posZ));
			if (away != null) {
				theBanditAsNPC.getNavigator().tryMoveToXYZ(away.xCoord, away.yCoord, away.zCoord, speed);
			}
			targetPlayer = findNearestPlayer();
		}
	}
}
