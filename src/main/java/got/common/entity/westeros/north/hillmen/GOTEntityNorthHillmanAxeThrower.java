package got.common.entity.westeros.north.hillmen;

import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.inanimate.GOTEntityThrowingAxe;
import got.common.entity.other.utils.GOTEntityUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanAxeThrower extends GOTEntityNorthHillmanWarrior {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthHillmanAxeThrower(World world) {
		super(world);
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		GOTEntityUtils.setupRangedAttackModeChange(this, mode);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.ironThrowingAxe));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());

		return entityData;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.4, 40, 60, 12.0f);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		ItemStack axeItem = npcItemsInv.getRangedWeapon();
		if (axeItem == null) {
			axeItem = new ItemStack(GOTItems.ironThrowingAxe);
		}
		GOTEntityThrowingAxe axe = new GOTEntityThrowingAxe(worldObj, this, target, axeItem, 1.0f, 1.0f);
		playSound("random.bow", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.8f));
		worldObj.spawnEntityInWorld(axe);
		swingItem();
	}
}