package got.common.entity.sothoryos.sothoryos;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.inanimate.GOTEntityDart;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.item.other.GOTItemDart;
import got.common.item.weapon.GOTItemSarbacane;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySothoryosBlowgunner extends GOTEntitySothoryosWarrior {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySothoryosBlowgunner(World world) {
		super(world);
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		GOTEntityUtils.setupRangedAttackModeChange(this, mode);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setRangedWeapon(new ItemStack(GOTItems.sarbacane));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());

		return entityData;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.25, 30, 50, 16.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCAmmo(GOTItems.dart, i);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		ItemStack heldItem = getHeldItem();
		float str = 1.0f + getDistanceToEntity(target) / 16.0f * 0.015f;
		GOTEntityDart dart = GOTItemDart.createDart(worldObj, this, target, new ItemStack(GOTBlocks.sarbacaneTrap), str * GOTItemSarbacane.getSarbacaneLaunchSpeedFactor(heldItem), 1.0f);
		if (heldItem != null) {
			GOTItemSarbacane.applySarbacaneModifiers(dart, heldItem);
		}
		playSound("got:item.dart", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 1.2f) + 0.5f);
		worldObj.spawnEntityInWorld(dart);
	}
}