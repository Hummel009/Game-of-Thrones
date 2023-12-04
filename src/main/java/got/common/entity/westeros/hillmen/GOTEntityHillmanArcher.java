package got.common.entity.westeros.hillmen;

import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityHillmanArcher extends GOTEntityHillmanWarrior {
	public GOTEntityHillmanArcher(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCArrows(i);
	}

	@Override
	public EntityAIBase getHillmanAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.4, 30, 50, 16.0f);
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setRangedWeapon(new ItemStack(Items.bow));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		return data1;
	}
}
