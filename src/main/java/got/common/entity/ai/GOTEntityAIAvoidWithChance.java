package got.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class GOTEntityAIAvoidWithChance extends EntityAIAvoidEntity {
	private final EntityCreature theEntity;
	private final float chance;
	private final String soundEffect;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIAvoidWithChance(EntityCreature entity, Class<?> avoidClass, float f, double d, double d1, float c) {
		this(entity, avoidClass, f, d, d1, c, null);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAIAvoidWithChance(EntityCreature entity, Class<?> avoidClass, float f, double d, double d1, float c, String s) {
		super(entity, avoidClass, f, d, d1);
		theEntity = entity;
		chance = c;
		soundEffect = s;
	}

	@Override
	public boolean shouldExecute() {
		return theEntity.getRNG().nextFloat() < chance && super.shouldExecute();
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
		if (soundEffect != null) {
			theEntity.playSound(soundEffect, 0.5f, (theEntity.getRNG().nextFloat() - theEntity.getRNG().nextFloat()) * 0.2f + 1.0f);
		}
	}
}