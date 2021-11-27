package got.common.entity.ai;

import java.util.List;

import got.common.GOTConfig;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import net.minecraft.entity.EntityLivingBase;

public class GOTEntityAIDothrakiSkirmish extends GOTEntityAINearestAttackableTargetBasic {
	public GOTEntityDothraki theDothraki;

	public GOTEntityAIDothrakiSkirmish(GOTEntityDothraki dothraki, boolean flag) {
		super(dothraki, GOTEntityDothraki.class, 0, flag, null);
		theDothraki = dothraki;
	}

	public boolean canDothrakiSkirmish(EntityLivingBase entity) {
		if (entity instanceof GOTEntityDothraki) {
			GOTEntityDothraki orc = (GOTEntityDothraki) entity;
			return !orc.isTrader() && !orc.hiredNPCInfo.isActive && orc.ridingEntity == null && orc.canDothrakiSkirmish();
		}
		return false;
	}

	@Override
	public boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
		return canDothrakiSkirmish(entity) && super.isSuitableTarget(entity, flag);
	}

	@Override
	public boolean shouldExecute() {
		if (!GOTConfig.enableDothrakiSkirmish || !canDothrakiSkirmish(theDothraki)) {
			return false;
		}
		if (!theDothraki.isOrcSkirmishing()) {
			int chance = 20000;
			List nearbyOrcs = theDothraki.worldObj.getEntitiesWithinAABB(GOTEntityDothraki.class, theDothraki.boundingBox.expand(16.0, 8.0, 16.0));
			for (Object nearbyOrc : nearbyOrcs) {
				GOTEntityDothraki orc = (GOTEntityDothraki) nearbyOrc;
				if (!orc.isOrcSkirmishing()) {
					continue;
				}
				chance /= 10;
			}
			if (chance < 40) {
				chance = 40;
			}
			if (theDothraki.getRNG().nextInt(chance) != 0) {
				return false;
			}
		}
		return super.shouldExecute();
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		theDothraki.setOrcSkirmishing();
	}
}
