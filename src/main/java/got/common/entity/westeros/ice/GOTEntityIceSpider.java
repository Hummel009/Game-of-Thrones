package got.common.entity.westeros.ice;

import got.common.GOTDamage;
import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.*;
import got.common.entity.westeros.*;
import got.common.entity.westeros.legendary.reborn.*;
import got.common.entity.westeros.legendary.trader.GOTEntityGendryBaratheon;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import got.common.faction.GOTFaction;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityIceSpider extends GOTEntitySpiderBase {
	public GOTEntityIceSpider(World world) {
		super(world);
		canBeMarried = false;
		spawnsInDarkness = false;
		isImmuneToFrost = true;
		isChilly = true;
		isImmuneToFire = true;
		addTargetTasks();
		isNotHuman = true;
	}

	public void addTargetTasks() {
		int target = addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGoldenMan.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityBandit.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityScrapTrader.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGendryBaratheon.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityBronn.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGeroldDayne.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityThreeEyedRaven.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityMaester.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityWhore.class, 0, true));
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
		ItemStack itemstack;
		Entity entity = damagesource.getEntity();
		Entity damageSource = damagesource.getSourceOfDamage();
		if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && ((EntityLivingBase) entity).getHeldItem().getItem() instanceof GOTMaterialFinder && (((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.VALYRIAN || ((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.OBSIDIAN || (GOTMaterialFinder) itemstack.getItem() == GOTRegistry.crowbar)) {
			return super.attackEntityFrom(damagesource, f);
		}
		if (damagesource.getEntity() instanceof GOTEntityGregorClegane || damagesource.getEntity() instanceof GOTEntityAsshaiArchmag || (damageSource instanceof GOTEntitySpear && ((GOTEntitySpear) damageSource).getProjectileItem().getItem() == GOTRegistry.valyrianSpear)) {
			return super.attackEntityFrom(damagesource, f);
		}
		return super.attackEntityFrom(damagesource, 0.0f);
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
		return GOTAchievement.KILL_ICE_SPIDER;
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
		}
		if (entity instanceof GOTEntityHumanBase) {
			super.onKillEntity(entity);
			wight.familyInfo.setAge(((GOTEntityHumanBase) entity).familyInfo.getAge());
			wight.copyLocationAndAnglesFrom(entity);
			wight.npcItemsInv.setMeleeWeapon(((GOTEntityHumanBase) entity).npcItemsInv.getMeleeWeapon());
			wight.npcItemsInv.setIdleItem(((GOTEntityHumanBase) entity).npcItemsInv.getMeleeWeapon());
			wight.setCurrentItemOrArmor(1, ((GOTEntityHumanBase) entity).getEquipmentInSlot(1));
			wight.setCurrentItemOrArmor(2, ((GOTEntityHumanBase) entity).getEquipmentInSlot(2));
			wight.setCurrentItemOrArmor(3, ((GOTEntityHumanBase) entity).getEquipmentInSlot(3));
			wight.setCurrentItemOrArmor(4, ((GOTEntityHumanBase) entity).getEquipmentInSlot(4));
			wight.familyInfo.setMale(((GOTEntityHumanBase) entity).familyInfo.male);
			((GOTEntityHumanBase) entity).becameWight = true;
			worldObj.removeEntity(entity);
			worldObj.spawnEntityInWorld(wight);
		}
		if (entity instanceof GOTEntityGiant) {
			super.onKillEntity(entity);
			giant.copyLocationAndAnglesFrom(entity);
			worldObj.removeEntity(entity);
			giant.onSpawnWithEgg(null);
			worldObj.spawnEntityInWorld(giant);
		}
		if (entity instanceof GOTEntitySpiderBase) {
			super.onKillEntity(entity);
			spider.copyLocationAndAnglesFrom(entity);
			worldObj.removeEntity(entity);
			spider.onSpawnWithEgg(null);
			worldObj.spawnEntityInWorld(spider);
		}
	}
}
