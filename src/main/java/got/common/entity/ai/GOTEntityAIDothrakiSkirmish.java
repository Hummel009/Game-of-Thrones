package got.common.entity.ai;

import got.common.GOTConfig;
import got.common.entity.essos.dothraki.GOTEntityDothraki;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;

public class GOTEntityAIDothrakiSkirmish extends GOTEntityAINearestAttackableTargetBasic {
	public GOTEntityDothraki theDothraki;

	public GOTEntityAIDothrakiSkirmish(GOTEntityDothraki dothraki, boolean flag) {
		super(dothraki, GOTEntityDothraki.class, 0, flag, null);
		theDothraki = dothraki;
	}

	public boolean canDothrakiSkirmish(EntityLivingBase entity) {
		if (entity instanceof GOTEntityDothraki) {
			GOTEntityDothraki mob = (GOTEntityDothraki) entity;
			return theDothraki.familyInfo.isMale() && theDothraki.familyInfo.age >= 0 && mob.familyInfo.isMale() && mob.familyInfo.age >= 0 && !mob.isTrader() && !mob.hiredNPCInfo.isActive && mob.ridingEntity == null && mob.canDothrakiSkirmish();
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
		if (!theDothraki.isDothrakSkirmishing()) {
			int chance = 20000;
			List<GOTEntityDothraki> nearbyMobs = theDothraki.worldObj.getEntitiesWithinAABB(GOTEntityDothraki.class, theDothraki.boundingBox.expand(16.0, 8.0, 16.0));
			for (GOTEntityDothraki nearbyMob : nearbyMobs) {
				if (nearbyMob.isDothrakSkirmishing()) {
					chance /= 10;
				}
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
		theDothraki.setDothrakiSkirmishing();
	}
}
