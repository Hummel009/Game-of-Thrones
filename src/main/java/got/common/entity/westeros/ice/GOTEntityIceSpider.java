package got.common.entity.westeros.ice;

import got.common.GOTConfig;
import got.common.GOTDamage;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityBericDondarrion;
import got.common.entity.westeros.legendary.reborn.GOTEntityGregorClegane;
import got.common.entity.westeros.legendary.reborn.GOTEntityLancelLannister;
import got.common.entity.westeros.legendary.reborn.GOTEntityTheonGreyjoy;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import got.common.faction.GOTFaction;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityIceSpider extends GOTEntitySpiderBase {
	public GOTEntityIceSpider(World world) {
		super(world);
		canBeMarried = false;
		spawnsInDarkness = true;
		isImmuneToFrost = true;
		isChilly = true;
		isImmuneToFire = true;
		isNotHuman = true;
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityPlayerMP) {
				GOTDamage.doFrostDamage((EntityPlayerMP) entity);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		Entity entity = damagesource.getEntity();
		Entity damageSource = damagesource.getSourceOfDamage();
		boolean causeDamage = false;
		if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage()) {
			ItemStack itemstack = ((EntityLivingBase) entity).getHeldItem();
			if (itemstack != null) {
				Item item = itemstack.getItem();
				if (item instanceof GOTMaterialFinder && (((GOTMaterialFinder) item).getMaterial() == GOTMaterial.VALYRIAN_TOOL || ((GOTMaterialFinder) item).getMaterial() == GOTMaterial.OBSIDIAN_TOOL)) {
					causeDamage = true;
				}
				if (item == GOTItems.crowbar) {
					causeDamage = true;
				}
			}
		}
		if (entity instanceof GOTEntityGregorClegane || entity instanceof GOTEntityAsshaiArchmag) {
			causeDamage = true;
		}
		if (damageSource instanceof GOTEntitySpear) {
			Item item = ((GOTEntityProjectileBase) damageSource).getProjectileItem().getItem();
			if (item instanceof GOTMaterialFinder && (((GOTMaterialFinder) item).getMaterial() == GOTMaterial.VALYRIAN_TOOL || ((GOTMaterialFinder) item).getMaterial() == GOTMaterial.OBSIDIAN_TOOL)) {
				causeDamage = true;
			}
		}
		if (GOTConfig.walkerFireDamage && damagesource.isFireDamage()) {
			causeDamage = true;
		}
		if (causeDamage) {
			return super.attackEntityFrom(damagesource, f);
		}
		return super.attackEntityFrom(damagesource, 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public float getAlignmentBonus() {
		return 10.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WHITE_WALKER;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killIceSpider;
	}

	@Override
	public int getRandomSpiderScale() {
		return rand.nextInt(4);
	}

	@Override
	public int getRandomSpiderType() {
		return VENOM_SLOWNESS;
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		GOTEntityWight wight = new GOTEntityWight(worldObj);
		GOTEntityWightGiant giant = new GOTEntityWightGiant(worldObj);
		GOTEntityIceSpider spider = new GOTEntityIceSpider(worldObj);
		if (entity instanceof GOTEntityBericDondarrion || entity instanceof GOTEntityGregorClegane || entity instanceof GOTEntityLancelLannister || entity instanceof GOTEntityTheonGreyjoy) {
			super.onKillEntity(entity);
		} else if (entity instanceof GOTEntityHumanBase) {
			super.onKillEntity(entity);
			wight.familyInfo.setAge(((GOTEntityNPC) entity).familyInfo.getAge());
			wight.copyLocationAndAnglesFrom(entity);
			wight.npcItemsInv.setMeleeWeapon(((GOTEntityNPC) entity).npcItemsInv.getMeleeWeapon());
			wight.npcItemsInv.setIdleItem(((GOTEntityNPC) entity).npcItemsInv.getMeleeWeapon());
			wight.setCurrentItemOrArmor(1, entity.getEquipmentInSlot(1));
			wight.setCurrentItemOrArmor(2, entity.getEquipmentInSlot(2));
			wight.setCurrentItemOrArmor(3, entity.getEquipmentInSlot(3));
			wight.setCurrentItemOrArmor(4, entity.getEquipmentInSlot(4));
			wight.familyInfo.setMale(((GOTEntityNPC) entity).familyInfo.male);
			worldObj.removeEntity(entity);
			worldObj.spawnEntityInWorld(wight);
		} else if (entity instanceof GOTEntityGiant) {
			super.onKillEntity(entity);
			giant.copyLocationAndAnglesFrom(entity);
			worldObj.removeEntity(entity);
			giant.onSpawnWithEgg(null);
			worldObj.spawnEntityInWorld(giant);
		} else if (entity instanceof GOTEntitySpiderBase) {
			super.onKillEntity(entity);
			spider.copyLocationAndAnglesFrom(entity);
			worldObj.removeEntity(entity);
			spider.onSpawnWithEgg(null);
			worldObj.spawnEntityInWorld(spider);
		}
	}
}
