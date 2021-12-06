package got.common.entity.ai;

import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.other.*;
import got.common.entity.westeros.*;
import got.common.entity.westeros.legendary.trader.GOTEntityGendryBaratheon;
import got.common.entity.westeros.legendary.warrior.*;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAINearestAttackableTargetIce extends GOTEntityAINearestAttackableTargetPatriot {
	public GOTEntityAINearestAttackableTargetIce(EntityCreature entity, Class targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
	}

	public GOTEntityAINearestAttackableTargetIce(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
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
			return true;
		}
		if (entity instanceof GOTEntityBandit || entity instanceof GOTEntityGoldenMan || entity instanceof GOTEntityScrapTrader || entity instanceof GOTEntityGendryBaratheon || entity instanceof GOTEntityBronn || entity instanceof GOTEntityGeroldDayne || entity instanceof GOTEntityThreeEyedRaven || entity instanceof GOTEntityMaester || entity instanceof GOTEntityWhore) {
			return true;
		}
		return false;
	}
}
