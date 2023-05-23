package got.common.entity.ai;

import got.common.entity.other.IBandit;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAINearestAttackableTargetBandit extends GOTEntityAINearestAttackableTargetBasic {
	public IBandit taskOwnerAsBandit;

	public GOTEntityAINearestAttackableTargetBandit(EntityCreature entity, Class<? extends Entity> targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
		taskOwnerAsBandit = (IBandit) entity;
	}

	public GOTEntityAINearestAttackableTargetBandit(EntityCreature entity, Class<? extends Entity> targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
		taskOwnerAsBandit = (IBandit) entity;
	}

	@Override
	public boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
		if (IBandit.Helper.canStealFromPlayerInv(taskOwnerAsBandit, entityplayer)) {
			return false;
		}
		return super.isPlayerSuitableTarget(entityplayer);
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		return entity instanceof EntityPlayer && super.isSuitableTarget(entity, flag);
	}

	@Override
	public boolean shouldExecute() {
		if (!taskOwnerAsBandit.getBanditInventory().isEmpty()) {
			return false;
		}
		return super.shouldExecute();
	}
}
