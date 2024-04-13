package got.client;

import got.common.item.GOTWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.List;

public class GOTEntityRenderer extends EntityRenderer {
	private final Minecraft theMC;

	public GOTEntityRenderer(Minecraft mc, IResourceManager irm) {
		super(mc, irm);
		theMC = mc;
	}

	@Override
	public void getMouseOver(float partialTick) {
		if (theMC.renderViewEntity != null && theMC.theWorld != null) {
			double reach;
			theMC.pointedEntity = null;
			Entity thePointedEntity = null;
			double blockReach = theMC.playerController.getBlockReachDistance();
			float meleeReachFactor = GOTWeaponStats.getMeleeReachFactor(theMC.thePlayer.getHeldItem());
			theMC.objectMouseOver = theMC.renderViewEntity.rayTrace(blockReach * meleeReachFactor, partialTick);
			double maxDist = reach = GOTWeaponStats.getMeleeReachDistance(theMC.thePlayer);
			Vec3 posVec = theMC.renderViewEntity.getPosition(partialTick);
			if (theMC.objectMouseOver != null) {
				maxDist = theMC.objectMouseOver.hitVec.distanceTo(posVec);
			}
			Vec3 lookVec = theMC.renderViewEntity.getLook(partialTick);
			Vec3 sightVec = posVec.addVector(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach);
			Vec3 targetVec = null;
			float lookWidth = GOTWeaponStats.getMeleeExtraLookWidth();
			List<? extends Entity> entities = theMC.theWorld.getEntitiesWithinAABBExcludingEntity(theMC.renderViewEntity, theMC.renderViewEntity.boundingBox.addCoord(lookVec.xCoord * reach, lookVec.yCoord * reach, lookVec.zCoord * reach).expand(lookWidth, lookWidth, lookWidth));
			double leastDist = maxDist;
			for (Entity entity : entities) {
				double entityDist;
				if (entity.canBeCollidedWith()) {
					float f = entity.getCollisionBorderSize();
					AxisAlignedBB entityBB = entity.boundingBox.expand(f, f, f);
					MovingObjectPosition movingobjectposition = entityBB.calculateIntercept(posVec, sightVec);
					if (entityBB.isVecInside(posVec)) {
						if (leastDist <= 0.0 && leastDist != 0.0) {
							continue;
						}
						thePointedEntity = entity;
						if (movingobjectposition == null) {
							targetVec = posVec;
						} else {
							targetVec = movingobjectposition.hitVec;
						}
						leastDist = 0.0;
						continue;
					}
					if (movingobjectposition == null || (entityDist = posVec.distanceTo(movingobjectposition.hitVec)) >= leastDist && leastDist != 0.0) {
						continue;
					}
					if (entity == theMC.renderViewEntity.ridingEntity && !entity.canRiderInteract()) {
						if (leastDist != 0.0) {
							continue;
						}
						thePointedEntity = entity;
						targetVec = movingobjectposition.hitVec;
						continue;
					}
					thePointedEntity = entity;
					targetVec = movingobjectposition.hitVec;
					leastDist = entityDist;
				}
			}
			if (thePointedEntity != null && (leastDist < maxDist || theMC.objectMouseOver == null)) {
				theMC.objectMouseOver = new MovingObjectPosition(thePointedEntity, targetVec);
				if (thePointedEntity instanceof EntityLivingBase || thePointedEntity instanceof EntityItemFrame) {
					theMC.pointedEntity = thePointedEntity;
				}
			}
		}
	}

	@Override
	public void updateRenderer() {
		super.updateRenderer();
		if (Minecraft.isGuiEnabled()) {
			float wight = GOTClientFactory.getTickHandlerClient().getWightLookFactor();
			float hand = GOTReflectionClient.getHandFOV(this);
			GOTReflectionClient.setHandFOV(this, hand + wight * 0.3f);
		}
	}
}