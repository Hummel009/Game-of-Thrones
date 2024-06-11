package got.common.entity.ai;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.item.weapon.GOTItemAsshaiArchmagStaff;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityAIAsshaiArchmagUseStaff extends EntityAIBase {
	private final GOTEntityAsshaiArchmag wizard;
	private final World theWorld;

	private int attackTick;

	public GOTEntityAIAsshaiArchmagUseStaff(GOTEntityAsshaiArchmag archmag) {
		wizard = archmag;
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
		List<EntityLivingBase> nearbyEntities = theWorld.getEntitiesWithinAABB(EntityLivingBase.class, wizard.boundingBox.expand(64.0, 64.0, 64.0));
		for (EntityLivingBase element : nearbyEntities) {
			if (!element.isEntityAlive()) {
				continue;
			}
			if (element instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) element;
				if (entityplayer.capabilities.isCreativeMode || GOTLevelData.getData(entityplayer).getAlignment(wizard.getFaction()) >= 0.0f && wizard.getAttackTarget() != entityplayer) {
					continue;
				}
				++targets;
				continue;
			}
			if (wizard.getFaction().isBadRelation(GOT.getNPCFaction(element))) {
				++targets;
				continue;
			}
			if (wizard.getAttackTarget() != element && (!(element instanceof EntityLiving) || ((EntityLiving) element).getAttackTarget() != wizard)) {
				continue;
			}
			++targets;
		}
		return targets >= 1;
	}

	@Override
	public void startExecuting() {
		attackTick = 40;
		wizard.setIsUsingStaff(true);
	}

	@Override
	public void updateTask() {
		attackTick = Math.max(attackTick - 1, 0);
		if (attackTick == 0) {
			attackTick = 40;
			GOTItemAsshaiArchmagStaff.useStaff(theWorld, wizard);
			wizard.setIsUsingStaff(false);
		}
	}
}