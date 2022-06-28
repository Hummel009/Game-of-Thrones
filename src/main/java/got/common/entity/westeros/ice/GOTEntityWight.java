package got.common.entity.westeros.ice;

import got.common.GOTDamage;
import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.*;
import got.common.entity.westeros.legendary.reborn.*;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import got.common.faction.GOTFaction;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityWight extends GOTEntityHumanBase {
	public static ItemStack[] weapons = { new ItemStack(GOTRegistry.wildlingAxe), new ItemStack(GOTRegistry.wildlingBattleaxe), new ItemStack(GOTRegistry.wildlingDagger), new ItemStack(GOTRegistry.wildlingDaggerPoisoned), new ItemStack(GOTRegistry.wildlingHammer), new ItemStack(GOTRegistry.wildlingPolearm), new ItemStack(GOTRegistry.wildlingSword), new ItemStack(GOTRegistry.wildlingSword), new ItemStack(GOTRegistry.wildlingSword), new ItemStack(GOTRegistry.wildlingSword) };
	public static ItemStack[] spears = { new ItemStack(GOTRegistry.wildlingSpear) };

	public GOTEntityWight(World world) {
		super(world);
		canBeMarried = false;
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(1, new EntityAIOpenDoor(this, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityWhiteWalker.class, 5.0f, 0.02f));
		tasks.addTask(5, new EntityAIWatchClosest2(this, GOTEntityWight.class, 5.0f, 0.02f));
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		canBeMarried = false;
		spawnsInDarkness = true;
		isImmuneToFrost = true;
		isNotHuman = true;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
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
		if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && ((EntityLivingBase) entity).getHeldItem().getItem() instanceof GOTMaterialFinder && (((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.VALYRIAN_TOOL || ((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.OBSIDIAN_TOOL || (GOTMaterialFinder) itemstack.getItem() == GOTRegistry.crowbar)) {
			return super.attackEntityFrom(damagesource, f);
		}
		if (damagesource.getEntity() instanceof GOTEntityGregorClegane || damagesource.getEntity() instanceof GOTEntityAsshaiArchmag || damagesource.isFireDamage()) {
			return super.attackEntityFrom(damagesource, f);
		}
		if (damageSource instanceof GOTEntitySpear && ((GOTEntitySpear) damageSource).getProjectileItem().getItem() == GOTRegistry.valyrianSpear) {
			return super.attackEntityFrom(damagesource, f);
		}
		return super.attackEntityFrom(damagesource, 0.0f);
	}

	@Override
	public float getAlignmentBonus() {
		return 1.0f;
	}

	@Override
	public String getDeathSound() {
		return "mob.zombie.death";
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WHITE_WALKER;
	}

	@Override
	public String getHurtSound() {
		return "mob.zombie.hurt";
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killWight;
	}

	@Override
	public String getLivingSound() {
		return "mob.zombie.say";
	}

	@Override
	public void onArtificalSpawn() {
		if (canBeMarried && this.getClass() == familyInfo.marriageEntityClass && rand.nextInt(7) == 0) {
			familyInfo.setChild();
		}
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
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
			wight.familyInfo.setAge(((GOTEntityHumanBase) entity).familyInfo.getAge());
			wight.copyLocationAndAnglesFrom(entity);
			wight.npcItemsInv.setMeleeWeapon(((GOTEntityHumanBase) entity).npcItemsInv.getMeleeWeapon());
			wight.npcItemsInv.setIdleItem(((GOTEntityHumanBase) entity).npcItemsInv.getMeleeWeapon());
			wight.setCurrentItemOrArmor(1, ((GOTEntityHumanBase) entity).getEquipmentInSlot(1));
			wight.setCurrentItemOrArmor(2, ((GOTEntityHumanBase) entity).getEquipmentInSlot(2));
			wight.setCurrentItemOrArmor(3, ((GOTEntityHumanBase) entity).getEquipmentInSlot(3));
			wight.setCurrentItemOrArmor(4, ((GOTEntityHumanBase) entity).getEquipmentInSlot(4));
			wight.familyInfo.setMale(((GOTEntityHumanBase) entity).familyInfo.male);
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

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		if (rand.nextInt(8) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			i = rand.nextInt(spears.length);
			npcItemsInv.setMeleeWeapon(spears[i].copy());
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.furBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.furLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.furChestplate));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}
}
