package got.common.entity.ai;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTHiredNPCInfo;
import got.common.faction.GOTFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class GOTNPCTargetSelector implements IEntitySelector {
	private final EntityLiving owner;
	private final GOTFaction ownerFaction;

	@SuppressWarnings("WeakerAccess")
	public GOTNPCTargetSelector(EntityLiving entity) {
		owner = entity;
		ownerFaction = GOT.getNPCFaction(entity);
	}

	@Override
	public boolean isEntityApplicable(Entity target) {
		if ((ownerFaction == GOTFaction.WHITE_WALKER || ownerFaction == GOTFaction.HOSTILE) && target instanceof GOTEntityNPC && GOT.getNPCFaction(target) == GOTFaction.UNALIGNED) {
			return true;
		}
		if (ownerFaction == GOTFaction.HOSTILE && (target.getClass().isAssignableFrom(owner.getClass()) || owner.getClass().isAssignableFrom(target.getClass()))) {
			return false;
		}
		if (target.isEntityAlive()) {
			if (target instanceof GOTEntityNPC && !((GOTEntityNPC) target).canBeFreelyTargetedBy(owner)) {
				return false;
			}
			if (!ownerFaction.isApprovesWarCrimes() && target instanceof GOTEntityNPC && ((GOTEntityNPC) target).isCivilianNPC()) {
				return false;
			}
			GOTFaction targetFaction = GOT.getNPCFaction(target);
			if (ownerFaction.isBadRelation(targetFaction)) {
				return true;
			}
			if (ownerFaction.isNeutral(targetFaction)) {
				EntityPlayer hiringPlayer = null;
				if (owner instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) owner;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.hiredTask != GOTHiredNPCInfo.Task.FARMER) {
						hiringPlayer = npc.hiredNPCInfo.getHiringPlayer();
					}
				}
				return hiringPlayer != null && GOTLevelData.getData(hiringPlayer).getAlignment(targetFaction) < 0.0f;
			}
		}
		return false;
	}
}