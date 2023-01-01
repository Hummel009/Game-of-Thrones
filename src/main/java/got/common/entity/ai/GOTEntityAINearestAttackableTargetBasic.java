package got.common.entity.ai;

import java.util.*;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.other.*;
import got.common.entity.westeros.GOTEntityWesterosBandit;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAINearestAttackableTargetBasic extends EntityAITarget {
	public Class targetClass;
	public int targetChance;
	public TargetSorter targetSorter;
	public IEntitySelector targetSelector;
	public EntityLivingBase targetEntity;

	public GOTEntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight) {
		this(entity, cls, chance, checkSight, false, null);
	}

	public GOTEntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, boolean nearby, IEntitySelector selector) {
		super(entity, checkSight, nearby);
		targetClass = cls;
		targetChance = chance;
		targetSorter = new TargetSorter(entity);
		setMutexBits(1);
		targetSelector = testEntity -> {
			if (testEntity instanceof EntityLivingBase) {
				EntityLivingBase testEntityLiving = (EntityLivingBase) testEntity;
				if (selector != null && !selector.isEntityApplicable(testEntityLiving)) {
					return false;
				}
				return GOTEntityAINearestAttackableTargetBasic.this.isSuitableTarget(testEntityLiving, false);
			}
			return false;
		};
	}

	public GOTEntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, IEntitySelector selector) {
		this(entity, cls, chance, checkSight, false, selector);
	}

	public boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
		float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOT.getNPCFaction(taskOwner));
		return alignment < 0.0f;
	}

	public boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
		return isPlayerSuitableAlignmentTarget(entityplayer);
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		if (entity == taskOwner.ridingEntity || entity == taskOwner.riddenByEntity) {
			return false;
		}
		if (super.isSuitableTarget(entity, flag)) {
			if (entity instanceof EntityPlayer) {
				return isPlayerSuitableTarget((EntityPlayer) entity);
			}
			if (entity instanceof GOTEntityWesterosBandit) {
				return taskOwner instanceof GOTEntityNPC && ((GOTEntityNPC) taskOwner).hiredNPCInfo.isActive;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldExecute() {
		GOTEntityNPCRideable rideable;
		if (targetChance > 0 && taskOwner.getRNG().nextInt(targetChance) != 0) {
			return false;
		}
		if (taskOwner instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) taskOwner;
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.isHalted() || npc.isChild()) {
				return false;
			}
		}
		if (taskOwner instanceof GOTEntityNPCRideable && ((rideable = (GOTEntityNPCRideable) taskOwner).isNPCTamed() || rideable.riddenByEntity instanceof EntityPlayer)) {
			return false;
		}
		double range = getTargetDistance();
		double rangeY = Math.min(range, 8.0);
		List entities = taskOwner.worldObj.selectEntitiesWithinAABB(targetClass, taskOwner.boundingBox.expand(range, rangeY, range), targetSelector);
		Collections.sort(entities, targetSorter);
		if (entities.isEmpty()) {
			return false;
		}
		targetEntity = (EntityLivingBase) entities.get(0);
		return true;
	}

	@Override
	public void startExecuting() {
		taskOwner.setAttackTarget(targetEntity);
		super.startExecuting();
	}

	public static class TargetSorter implements Comparator<Entity> {
		public EntityLivingBase theNPC;

		public TargetSorter(EntityLivingBase entity) {
			theNPC = entity;
		}

		@Override
		public int compare(Entity e1, Entity e2) {
			double d2;
			double d1 = distanceMetricSq(e1);
			d2 = distanceMetricSq(e2);
			if (d1 < d2) {
				return -1;
			}
			if (d1 > d2) {
				return 1;
			}
			return 0;
		}

		public double distanceMetricSq(Entity target) {
			double dSq = theNPC.getDistanceSqToEntity(target);
			double avg = 12.0;
			double avgSq = avg * avg;
			dSq /= avgSq;
			int dupes = 0;
			double nearRange = 8.0;
			List nearbyEntities = theNPC.worldObj.getEntitiesWithinAABB(GOTEntityNPC.class, theNPC.boundingBox.expand(nearRange, nearRange, nearRange));
			for (Object obj : nearbyEntities) {
				GOTEntityNPC nearby = (GOTEntityNPC) obj;
				if (nearby == theNPC || !nearby.isEntityAlive() || nearby.getAttackTarget() != target) {
					continue;
				}
				++dupes;
			}
			int dupesSq = dupes * dupes;
			return dSq + dupesSq;
		}
	}

}
