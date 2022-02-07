package got.common.entity.ai;

import java.util.*;

import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.*;
import net.minecraft.util.Vec3;

public class GOTEntityAINPCAvoidEvilPlayer extends EntityAIBase {
	public GOTEntityNPC theNPC;
	public double farSpeed;
	public double nearSpeed;
	public Entity closestLivingEntity;
	public float distanceFromEntity;
	public PathEntity entityPathEntity;
	public PathNavigate entityPathNavigate;

	public GOTEntityAINPCAvoidEvilPlayer(GOTEntityNPC npc, float f, double d, double d1) {
		theNPC = npc;
		distanceFromEntity = f;
		farSpeed = d;
		nearSpeed = d1;
		entityPathNavigate = npc.getNavigator();
		setMutexBits(1);
	}

	@Override
	public boolean continueExecuting() {
		return !entityPathNavigate.noPath();
	}

	@Override
	public void resetTask() {
		closestLivingEntity = null;
	}

	@Override
	public boolean shouldExecute() {
		ArrayList<EntityPlayer> validPlayers = new ArrayList<>();
		List list = theNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theNPC.boundingBox.expand(distanceFromEntity, distanceFromEntity / 2.0, distanceFromEntity));
		if (list.isEmpty()) {
			return false;
		}
		for (Object element : list) {
			EntityPlayer entityplayer = (EntityPlayer) element;
			if (entityplayer.capabilities.isCreativeMode) {
				continue;
			}
			float alignment = GOTLevelData.getData(entityplayer).getAlignment(theNPC.getFaction());
			if ((theNPC.familyInfo.getAge() >= 0 || alignment >= 0.0f) && alignment > -100.0f) {
				continue;
			}
			validPlayers.add(entityplayer);
		}
		if (validPlayers.isEmpty()) {
			return false;
		}
		closestLivingEntity = validPlayers.get(0);
		Vec3 fleePath = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theNPC, 16, 7, Vec3.createVectorHelper(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));
		if (fleePath == null || closestLivingEntity.getDistanceSq(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord) < closestLivingEntity.getDistanceSqToEntity(theNPC)) {
			return false;
		}
		entityPathEntity = entityPathNavigate.getPathToXYZ(fleePath.xCoord, fleePath.yCoord, fleePath.zCoord);
		return entityPathEntity == null ? false : entityPathEntity.isDestinationSame(fleePath);
	}

	@Override
	public void startExecuting() {
		entityPathNavigate.setPath(entityPathEntity, farSpeed);
	}

	@Override
	public void updateTask() {
		if (theNPC.getDistanceSqToEntity(closestLivingEntity) < 49.0) {
			theNPC.getNavigator().setSpeed(nearSpeed);
		} else {
			theNPC.getNavigator().setSpeed(farSpeed);
		}
	}
}
