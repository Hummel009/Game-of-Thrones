package got.common.entity.sothoryos.sothoryos;

import got.common.database.GOTRegistry;
import got.common.entity.ai.GOTEntityAIRangedAttack;
import got.common.entity.other.*;
import got.common.item.other.GOTItemDart;
import got.common.item.weapon.GOTItemSarbacane;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GOTEntitySothoryosBlowgunner extends GOTEntitySothoryosMan {
	public GOTEntitySothoryosBlowgunner(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
		ItemStack heldItem = getHeldItem();
		float str = 1.0f + getDistanceToEntity(target) / 16.0f * 0.015f;
		GOTEntityDart dart = ((GOTItemDart) GOTRegistry.dart).createDart(worldObj, this, target, new ItemStack(GOTRegistry.sarbacaneTrap), str *= GOTItemSarbacane.getSarbacaneLaunchSpeedFactor(heldItem), 1.0f);
		if (heldItem != null) {
			GOTItemSarbacane.applySarbacaneModifiers(dart, heldItem);
		}
		playSound("got:item.dart", 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 1.2f) + 0.5f);
		worldObj.spawnEntityInWorld(dart);
	}

	@Override
	public EntityAIBase createSothoryosAttackAI() {
		return new GOTEntityAIRangedAttack(this, 1.5, 10, 30, 16.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		dropNPCAmmo(GOTRegistry.dart, i);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
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
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setRangedWeapon(new ItemStack(GOTRegistry.sarbacane));
		npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.sothoryosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.sothoryosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.sothoryosChestplate));
		return data;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if (npcItemsInv.getRangedWeapon() == null) {
			npcItemsInv.setRangedWeapon(new ItemStack(GOTRegistry.sarbacane));
			npcItemsInv.setIdleItem(npcItemsInv.getRangedWeapon());
		}
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
