package got.common.entity.ai;

import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTEntityAIConsumeBase extends EntityAIBase {
	public GOTEntityNPC theEntity;
	public Random rand;
	public GOTFoods foodPool;
	public int chanceToConsume;
	public int consumeTick;

	protected GOTEntityAIConsumeBase(GOTEntityNPC entity, GOTFoods foods, int chance) {
		theEntity = entity;
		rand = theEntity.getRNG();
		foodPool = foods;
		chanceToConsume = chance;
		setMutexBits(3);
	}

	public abstract void consume();

	@Override
	public boolean continueExecuting() {
		return consumeTick > 0 && theEntity.getHeldItem() != null && theEntity.getAttackTarget() == null;
	}

	public abstract ItemStack createConsumable();

	public int getConsumeTime() {
		return 32;
	}

	@Override
	public void resetTask() {
		theEntity.setCurrentItemOrArmor(0, theEntity.npcItemsInv.getEatingBackup());
		theEntity.npcItemsInv.setEatingBackup(null);
		theEntity.npcItemsInv.setIsEating(false);
		theEntity.refreshCurrentAttackMode();
		consumeTick = 0;
	}

	public boolean shouldConsume() {
		boolean needsHeal = theEntity.getHealth() < theEntity.getMaxHealth();
		return needsHeal && rand.nextInt(chanceToConsume / 4) == 0 || rand.nextInt(chanceToConsume) == 0;
	}

	@Override
	public boolean shouldExecute() {
		return !theEntity.isChild() && theEntity.getAttackTarget() == null && !theEntity.npcItemsInv.getIsEating() && shouldConsume();
	}

	@Override
	public void startExecuting() {
		theEntity.npcItemsInv.setEatingBackup(theEntity.getHeldItem());
		theEntity.npcItemsInv.setIsEating(true);
		theEntity.setCurrentItemOrArmor(0, createConsumable());
		consumeTick = getConsumeTime();
	}

	public abstract void updateConsumeTick(int var1);

	@Override
	public void updateTask() {
		--consumeTick;
		updateConsumeTick(consumeTick);
		if (consumeTick == 0) {
			consume();
		}
	}
}
