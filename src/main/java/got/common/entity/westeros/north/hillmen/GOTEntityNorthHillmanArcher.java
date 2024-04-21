package got.common.entity.westeros.north.hillmen;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanArcher extends GOTEntityNorthHillmanWarrior {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillmanArcher(World world) {
		super(world);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCArrows(i);
	}

	@Override
	public EntityAIBase getNorthHillmanAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.25, 30, 50, 16.0f);
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
		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.westerosBow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		return entityData;
	}
}