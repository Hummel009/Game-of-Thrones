package got.common.entity.ai;

import java.util.List;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.essos.asshai.GOTEntityAsshaiShadowbinder;
import got.common.item.weapon.GOTItemAsshaiShadowbinderStaff;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityAIAsshaiShadowbinderUseStaff extends EntityAIBase {
	public GOTEntityAsshaiShadowbinder wizard;
	public int attackTick = 0;
	public World theWorld;

	public GOTEntityAIAsshaiShadowbinderUseStaff(GOTEntityAsshaiShadowbinder shadowbinder) {
		wizard = shadowbinder;
		theWorld = wizard.worldObj;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		return wizard.getIsUsingStaff();
	}

	@Override
	public void resetTask() {
		attackTick = 40;
		wizard.setIsUsingStaff(false);
	}

	@Override
	public boolean shouldExecute() {
		int targets = 0;
		List nearbyEntities = theWorld.getEntitiesWithinAABB(EntityLivingBase.class, wizard.boundingBox.expand(10.0, 10.0, 10.0));
		for (Object element : nearbyEntities) {
			EntityLivingBase entity = (EntityLivingBase) element;
			if (!entity.isEntityAlive()) {
				continue;
			}
			if (entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				if (entityplayer.capabilities.isCreativeMode || GOTLevelData.getData(entityplayer).getAlignment(wizard.getFaction()) >= 0.0f && wizard.getAttackTarget() != entityplayer) {
					continue;
				}
				++targets;
				continue;
			}
			if (wizard.getFaction().isBadRelation(GOT.getNPCFaction(entity))) {
				++targets;
				continue;
			}
			if (wizard.getAttackTarget() != entity && (!(entity instanceof EntityLiving) || ((EntityLiving) entity).getAttackTarget() != wizard)) {
				continue;
			}
			++targets;
		}
		if (targets >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		attackTick = 40;
		wizard.setIsUsingStaff(true);
	}

	@Override
	public void updateTask() {
		attackTick = Math.max(attackTick - 1, 0);
		if (attackTick <= 0) {
			attackTick = 40;
			GOTItemAsshaiShadowbinderStaff.wizardUseStaff(wizard.getEquipmentInSlot(0), theWorld, wizard);
			wizard.setIsUsingStaff(false);
		}
	}
}
