package got.common.entity.essos.braavos;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityBraavosSoldierArcher extends GOTEntityBraavosSoldier {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBraavosSoldierArcher(World world) {
		super(world);
		spawnRidingHorse = false;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.25, 30, 50, 20.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCArrows(i);
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.essosBow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		return entityData;
	}
}