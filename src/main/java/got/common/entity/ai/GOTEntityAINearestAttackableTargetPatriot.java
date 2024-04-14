package got.common.entity.ai;

import got.GOT;
import got.common.GOTLevelData;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class GOTEntityAINearestAttackableTargetPatriot extends GOTEntityAINearestAttackableTargetBasic {
	@SuppressWarnings("WeakerAccess")
	public GOTEntityAINearestAttackableTargetPatriot(EntityCreature entity, Class<? extends Entity> targetClass, int chance, boolean flag) {
		super(entity, targetClass, chance, flag);
	}

	@SuppressWarnings("WeakerAccess")
	public GOTEntityAINearestAttackableTargetPatriot(EntityCreature entity, Class<? extends Entity> targetClass, int chance, boolean flag, IEntitySelector selector) {
		super(entity, targetClass, chance, flag, selector);
	}

	@Override
	public boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
		float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOT.getNPCFaction(taskOwner));
		return alignment < 50.0f;
	}
}