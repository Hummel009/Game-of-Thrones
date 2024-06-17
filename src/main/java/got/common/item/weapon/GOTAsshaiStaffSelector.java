package got.common.item.weapon;

import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings("all")
public class GOTAsshaiStaffSelector {
	private GOTAsshaiStaffSelector() {
	}

	public static boolean canNpcAttackTarget(GOTEntityNPC attacker, EntityLivingBase target) {
		if (target instanceof GOTEntityNPC) {
			if (isEnemyNPCForNPC(attacker, (GOTEntityNPC) target)) {
				return true;
			}
			Entity riddenByEntity = target.riddenByEntity;
			if (riddenByEntity instanceof GOTEntityNPC) {
				if (isEnemyNPCForNPC(attacker, (GOTEntityNPC) riddenByEntity)) {
					return true;
				}
				return false;
			}
			if (riddenByEntity instanceof EntityPlayer) {
				if (isEnemyPlayerForNPC(attacker, (EntityPlayer) riddenByEntity, false)) {
					return true;
				}
				return false;
			}
			Entity ridingEntity = target.ridingEntity;
			if (ridingEntity instanceof GOTEntityNPC) {
				if (isEnemyNPCForNPC(attacker, (GOTEntityNPC) ridingEntity)) {
					return true;
				}
				return false;
			}
			return false;
		}
		if (target instanceof EntityPlayer) {
			if (isEnemyPlayerForNPC(attacker, (EntityPlayer) target, false)) {
				return true;
			}
			return false;
		}
		if (target instanceof EntityLivingBase) {
			Entity riderTarget = target.riddenByEntity;
			if (riderTarget instanceof GOTEntityNPC) {
				if (isEnemyNPCForNPC(attacker, (GOTEntityNPC) riderTarget)) {
					return true;
				}
				return false;
			}
			if (riderTarget instanceof EntityPlayer) {
				if (isEnemyPlayerForNPC(attacker, (EntityPlayer) riderTarget, false)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	private static boolean isEnemyNPCForNPC(GOTEntityNPC attacker, GOTEntityNPC target) {
		if (attacker == null || target == null) {
			return false;
		}
		if (target == attacker.getAttackTarget()) {
			return true;
		}
		EntityPlayer targetOwner = ((GOTEntityNPC) target).getHireableInfo().getHiringPlayer();
		if (isEnemyPlayerForNPC(attacker, targetOwner, true)) {
			return true;
		}
		EntityPlayer attackerOwner = ((GOTEntityNPC) attacker).getHireableInfo().getHiringPlayer();
		GOTPlayerData attackerOwnerData = GOTLevelData.getData(attackerOwner);
		GOTFaction targetFaction = target.getFaction();
		if (attackerOwnerData.getAlignment(targetFaction) < 0) {
			if (!target.isCivilianNPC() || attacker.getFaction().isApprovesWarCrimes()) {
				return true;
			}
		}
		GOTFaction attackerFaction = attacker.getFaction();
		if (attackerFaction.isBadRelation(targetFaction)) {
			return true;
		}
		return false;
	}

	private static boolean isEnemyPlayerForNPC(GOTEntityNPC attacker, EntityPlayer target, boolean escapeCreative) {
		if (attacker == null || target == null) {
			return false;
		}
		if (target == attacker.getAttackTarget()) {
			return true;
		}
		if (target.capabilities.isCreativeMode && !escapeCreative) {
			return false;
		}
		GOTPlayerData targetData = GOTLevelData.getData(target);
		GOTFaction attackerFaction = attacker.getFaction();
		if (targetData.getAlignment(attackerFaction) < 0) {
			return true;
		}
		return false;
	}
}