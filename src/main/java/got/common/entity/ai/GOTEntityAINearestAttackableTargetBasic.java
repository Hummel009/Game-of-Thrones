package got.common.entity.ai;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityBanditBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntitySpiderBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Comparator;
import java.util.List;

public class GOTEntityAINearestAttackableTargetBasic extends EntityAITarget {
	private final Class<? extends Entity> targetClass;
	private final int targetChance;
	private final TargetSorter targetSorter;
	private final IEntitySelector targetSelector;

	private EntityLivingBase targetEntity;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAINearestAttackableTargetBasic(EntityCreature entity, Class<? extends Entity> cls, int chance, boolean checkSight) {
		this(entity, cls, chance, checkSight, false, null);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAINearestAttackableTargetBasic(EntityCreature entity, Class<? extends Entity> cls, int chance, boolean checkSight, boolean nearby, IEntitySelector selector) {
		super(entity, checkSight, nearby);
		targetClass = cls;
		targetChance = chance;
		targetSorter = new TargetSorter(entity);
		setMutexBits(1);
		targetSelector = new EntitySelectorImpl(selector, this);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAINearestAttackableTargetBasic(EntityCreature entity, Class<? extends Entity> cls, int chance, boolean checkSight, IEntitySelector selector) {
		this(entity, cls, chance, checkSight, false, selector);
	}

	protected boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
		float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOT.getNPCFaction(taskOwner));
		return alignment < 0.0f;
	}

	protected boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
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
			return !(entity instanceof GOTEntityBanditBase) || taskOwner instanceof GOTEntityNPC && ((GOTEntityNPC) taskOwner).getHireableInfo().isActive();
		}
		return false;
	}

	@Override
	public boolean shouldExecute() {
		GOTEntitySpiderBase rideable;
		if (targetChance > 0 && taskOwner.getRNG().nextInt(targetChance) != 0) {
			return false;
		}
		if (taskOwner instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) taskOwner;
			if (npc.getHireableInfo().isActive() && npc.getHireableInfo().isHalted() || npc.isChild()) {
				return false;
			}
		}
		if (taskOwner instanceof GOTEntitySpiderBase && ((rideable = (GOTEntitySpiderBase) taskOwner).isNPCTamed() || rideable.riddenByEntity instanceof EntityPlayer)) {
			return false;
		}
		double range = getTargetDistance();
		double rangeY = Math.min(range, 8.0);
		List<? extends Entity> entities = taskOwner.worldObj.selectEntitiesWithinAABB(targetClass, taskOwner.boundingBox.expand(range, rangeY, range), targetSelector);
		entities.sort(targetSorter);
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
		protected final EntityLivingBase theNPC;

		public TargetSorter(EntityLivingBase entity) {
			theNPC = entity;
		}

		@Override
		public int compare(Entity e1, Entity e2) {
			double d1 = distanceMetricSq(e1);
			double d2 = distanceMetricSq(e2);
			return Double.compare(d1, d2);
		}

		protected double distanceMetricSq(Entity target) {
			double dSq = theNPC.getDistanceSqToEntity(target);
			double avg = 12.0;
			double avgSq = avg * avg;
			dSq /= avgSq;
			int dupes = 0;
			double nearRange = 8.0;
			List<GOTEntityNPC> nearbyEntities = theNPC.worldObj.getEntitiesWithinAABB(GOTEntityNPC.class, theNPC.boundingBox.expand(nearRange, nearRange, nearRange));
			for (GOTEntityNPC obj : nearbyEntities) {
				if (obj == theNPC || !obj.isEntityAlive() || obj.getAttackTarget() != target) {
					continue;
				}
				++dupes;
			}
			int dupesSq = dupes * dupes;
			return dSq + dupesSq;
		}
	}

	private static class EntitySelectorImpl implements IEntitySelector {
		private final GOTEntityAINearestAttackableTargetBasic ai;
		private final IEntitySelector selector;

		private EntitySelectorImpl(IEntitySelector selector, GOTEntityAINearestAttackableTargetBasic ai) {
			this.selector = selector;
			this.ai = ai;
		}

		@Override
		public boolean isEntityApplicable(Entity testEntity) {
			if (testEntity instanceof EntityLivingBase) {
				EntityLivingBase testEntityLiving = (EntityLivingBase) testEntity;
				return (selector == null || selector.isEntityApplicable(testEntityLiving)) && ai.isSuitableTarget(testEntityLiving, false);
			}
			return false;
		}
	}
}