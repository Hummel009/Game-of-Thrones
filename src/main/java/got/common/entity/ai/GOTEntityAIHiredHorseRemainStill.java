package got.common.entity.ai;

import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTNPCMount;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class GOTEntityAIHiredHorseRemainStill extends EntityAIBase {
	public GOTNPCMount theHorse;
	public EntityCreature livingHorse;

	public GOTEntityAIHiredHorseRemainStill(GOTNPCMount entity) {
		theHorse = entity;
		livingHorse = (EntityCreature) theHorse;
		setMutexBits(5);
	}

	@Override
	public boolean shouldExecute() {
		if (!theHorse.getBelongsToNPC()) {
			return false;
		}
		Entity rider = livingHorse.riddenByEntity;
		if (rider == null || !rider.isEntityAlive() || !(rider instanceof GOTEntityNPC)) {
			return false;
		}
		GOTEntityNPC ridingNPC = (GOTEntityNPC) rider;
		if (!ridingNPC.hiredNPCInfo.isActive || livingHorse.isInWater() || !livingHorse.onGround) {
			return false;
		}
		return ridingNPC.hiredNPCInfo.isHalted() && (ridingNPC.getAttackTarget() == null || !ridingNPC.getAttackTarget().isEntityAlive());
	}

	@Override
	public void startExecuting() {
		livingHorse.getNavigator().clearPathEntity();
	}
}
