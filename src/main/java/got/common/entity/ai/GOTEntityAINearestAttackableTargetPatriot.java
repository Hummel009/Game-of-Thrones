package got.common.entity.ai;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAINearestAttackableTargetPatriot extends GOTEntityAINearestAttackableTargetBasic {
	public GOTEntityAINearestAttackableTargetPatriot(EntityCreature entity, Class targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
	}

	public GOTEntityAINearestAttackableTargetPatriot(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
	}

	@Override
	public boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
		float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOT.getNPCFaction(taskOwner));
		return alignment < 50.0f;
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		if (entity instanceof GOTEntityNPC) {
			return true;
		}
		if (entity instanceof EntityPlayer) {
			return isPlayerSuitableTarget((EntityPlayer) entity);
		}
		return false;
	}
}
