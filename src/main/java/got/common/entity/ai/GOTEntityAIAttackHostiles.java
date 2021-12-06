/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.IEntitySelector
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.ai.EntityAITarget
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.world.World
 */
package got.common.entity.ai;

import java.util.*;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.other.*;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAIAttackHostiles
extends EntityAITarget {
    public Class targetClass;
    public int targetChance;
    public TargetSorter targetSorter;
    public IEntitySelector targetSelector;
    public EntityLivingBase targetEntity;

    public GOTEntityAIAttackHostiles(EntityCreature entity, Class cls, int chance, boolean checkSight) {
        this(entity, cls, chance, checkSight, false, null);
    }

    public GOTEntityAIAttackHostiles(EntityCreature entity, Class cls, int chance, boolean checkSight, boolean nearby, final IEntitySelector selector) {
        super(entity, checkSight, nearby);
        this.targetClass = cls;
        this.targetChance = chance;
        this.targetSorter = new TargetSorter((EntityLivingBase)entity);
        this.setMutexBits(1);
        this.targetSelector = new IEntitySelector(){

            public boolean isEntityApplicable(Entity testEntity) {
                if (testEntity instanceof EntityLivingBase) {
                    EntityLivingBase testEntityLiving = (EntityLivingBase)testEntity;
                    if (selector != null && !selector.isEntityApplicable((Entity)testEntityLiving)) {
                        return false;
                    }
                    return GOTEntityAIAttackHostiles.this.isSuitableTarget(testEntityLiving, false);
                }
                return false;
            }
        };
    }

    public GOTEntityAIAttackHostiles(EntityCreature entity, Class cls, int chance, boolean checkSight, IEntitySelector selector) {
        this(entity, cls, chance, checkSight, false, selector);
    }

    public boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
        float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOT.getNPCFaction((Entity)this.taskOwner));
        return alignment < 0.0f;
    }

    public boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
        return this.isPlayerSuitableAlignmentTarget(entityplayer);
    }

    public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
        if (entity == this.taskOwner.ridingEntity || entity == this.taskOwner.riddenByEntity) {
            return false;
        }
        if (super.isSuitableTarget(entity, flag)) {
            if (entity instanceof EntityPlayer) {
                return this.isPlayerSuitableTarget((EntityPlayer)entity);
            }
            return true;
        }
        return false;
    }

    public boolean shouldExecute() {
        GOTEntityNPCRideable rideable;
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        if (this.taskOwner instanceof GOTEntityNPC) {
            GOTEntityNPC npc = (GOTEntityNPC)this.taskOwner;
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.isHalted() || npc.isChild()) {
                return false;
            }
        }
        if (this.taskOwner instanceof GOTEntityNPCRideable && ((rideable = (GOTEntityNPCRideable)this.taskOwner).isNPCTamed() || rideable.riddenByEntity instanceof EntityPlayer)) {
            return false;
        }
        double range = this.getTargetDistance();
        double rangeY = Math.min(range, 8.0);
        List entities = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(range, rangeY, range), this.targetSelector);
        Collections.sort(entities, this.targetSorter);
        if (entities.isEmpty()) {
            return false;
        }
        this.targetEntity = (EntityLivingBase)entities.get(0);
        return true;
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    public static class TargetSorter
    implements Comparator<Entity> {
        public EntityLivingBase theNPC;

        public TargetSorter(EntityLivingBase entity) {
            this.theNPC = entity;
        }

        @Override
        public int compare(Entity e1, Entity e2) {
            double d = 0;
            double d1 = this.distanceMetricSq(e1);
            double d2 = this.distanceMetricSq(e2);
            if (d1 < d) {
                return -1;
            }
            if (d1 > d2) {
                return 1;
            }
            return 0;
        }

        public double distanceMetricSq(Entity target) {
            double dSq = this.theNPC.getDistanceSqToEntity(target);
            double avg = 12.0;
            double avgSq = avg * avg;
            dSq /= avgSq;
            int dupes = 0;
            double nearRange = 8.0;
            List nearbyEntities = this.theNPC.worldObj.getEntitiesWithinAABB(GOTEntityNPC.class, this.theNPC.boundingBox.expand(nearRange, nearRange, nearRange));
            for (Object obj : nearbyEntities) {
                GOTEntityNPC nearby = (GOTEntityNPC)obj;
                if (nearby == this.theNPC || !nearby.isEntityAlive() || nearby.getAttackTarget() != target) continue;
                ++dupes;
            }
            int dupesSq = dupes * dupes;
            return dSq + (double)dupesSq;
        }
    }

}

