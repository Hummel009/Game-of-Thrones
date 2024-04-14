package got.common.entity.ai;

import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class GOTEntityAINPCAvoidEvilPlayer extends EntityAIBase {
	private final PathNavigate entityPathNavigate;
	private final GOTEntityNPC theNPC;
	private final double farSpeed;
	private final double nearSpeed;
	private final float distanceFromEntity;

	private Entity closestLivingEntity;
	private PathEntity entityPathEntity;

	@SuppressWarnings("WeakerAccess")
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
		List<EntityPlayer> list = theNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theNPC.boundingBox.expand(distanceFromEntity, distanceFromEntity / 2.0, distanceFromEntity));
		if (list.isEmpty()) {
			return false;
		}
		for (EntityPlayer element : list) {
			if (element.capabilities.isCreativeMode) {
				continue;
			}
			float alignment = GOTLevelData.getData(element).getAlignment(theNPC.getFaction());
			if ((theNPC.familyInfo.getAge() >= 0 || alignment >= 0.0f) && alignment > -100.0f) {
				continue;
			}
			validPlayers.add(element);
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
		return entityPathEntity != null && entityPathEntity.isDestinationSame(fleePath);
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