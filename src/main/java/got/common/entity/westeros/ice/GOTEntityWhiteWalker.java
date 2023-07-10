package got.common.entity.westeros.ice;

import got.common.GOTConfig;
import got.common.GOTDamage;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntitySpear;
import got.common.entity.other.GOTEntitySpiderBase;
import got.common.entity.westeros.legendary.reborn.GOTEntityBericDondarrion;
import got.common.entity.westeros.legendary.reborn.GOTEntityGregorClegane;
import got.common.entity.westeros.legendary.reborn.GOTEntityLancelLannister;
import got.common.entity.westeros.legendary.reborn.GOTEntityTheonGreyjoy;
import got.common.entity.westeros.wildling.GOTEntityGiant;
import got.common.faction.GOTFaction;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityWhiteWalker extends GOTEntityNPC {
	public static ItemStack[] weapons = {new ItemStack(GOTItems.iceSword), new ItemStack(GOTItems.iceHeavySword), new ItemStack(GOTItems.iceSpear)};

	public GOTEntityWhiteWalker(World world) {
		super(world);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(1, new EntityAIOpenDoor(this, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		canBeMarried = false;
		spawnsInDarkness = true;
		isImmuneToFrost = true;
		isNotHuman = true;
		isChilly = true;
		isImmuneToFire = true;
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
			Item item = ((GOTEntitySpear) damageSource).getProjectileItem().getItem();
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
		if (rand.nextFloat() <= 0.525f) {
			dropItem(GOTItems.iceShard, rand.nextInt(2) + 1);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 10.0f;
	}

	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			if (liftSpawnRestrictions) {
				return true;
			}
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			return j > 62 && j < 140 && worldObj.getBlock(i, j - 1, k) == worldObj.getBiomeGenForCoords(i, k).topBlock;
		}
		return false;
	}

	@Override
	public String getDeathSound() {
		return "got:walker.death";
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WHITE_WALKER;
	}

	@Override
	public String getHurtSound() {
		return "got:walker.hurt";
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killWhiteWalker;
	}

	@Override
	public String getLivingSound() {
		return "got:walker.say";
	}

	@Override
	public void onArtificalSpawn() {
		if (canBeMarried && getClass() == familyInfo.marriageEntityClass && rand.nextInt(7) == 0) {
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
			wight.setCurrentItemOrArmor(1, entity.getEquipmentInSlot(1));
			wight.setCurrentItemOrArmor(2, entity.getEquipmentInSlot(2));
			wight.setCurrentItemOrArmor(3, entity.getEquipmentInSlot(3));
			wight.setCurrentItemOrArmor(4, entity.getEquipmentInSlot(4));
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
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(8) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(GOTItems.whiteWalkersBoots));
			setCurrentItemOrArmor(2, new ItemStack(GOTItems.whiteWalkersLeggings));
			setCurrentItemOrArmor(3, new ItemStack(GOTItems.whiteWalkersChestplate));
		}
		return data;
	}
}
