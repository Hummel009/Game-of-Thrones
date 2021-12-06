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
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityWhiteWalker extends GOTEntityNPC {
    public static ItemStack[] weapons = new ItemStack[]{new ItemStack(GOTRegistry.iceSword), new ItemStack(GOTRegistry.iceHeavySword), new ItemStack(GOTRegistry.iceSpear)};

	public GOTEntityWhiteWalker(World world) {
		super(world);
		canBeMarried = false;
		isImmuneToFrost = true;
		isImmuneToFire = true;
		isChilly = true;
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		addTargetTasks();
		tasks.addTask(0, new GOTEntityAIAttackOnCollide(this, 1.4, true));
		tasks.addTask(1, new EntityAIOpenDoor(this, true));
		tasks.addTask(2, new EntityAIWander(this, 1.0));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(4, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
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
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0);
        getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.8);
	}

    @Override
    public void onArtificalSpawn() {
        if (this.canBeMarried && this.getClass() == this.familyInfo.marriageEntityClass && this.rand.nextInt(7) == 0) {
            this.familyInfo.setChild();
        }
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
	public void dropFewItems(boolean flag, int i) {
        if (this.rand.nextFloat() <= 0.525f) {
			dropItem(GOTRegistry.iceShard, rand.nextInt(2) + 1);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 10.0f;
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
		return GOTAchievement.KILL_WHITE_WALKER;
	}

	@Override
	public String getLivingSound() {
		return "got:walker.say";
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

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(weapons.length);
		npcItemsInv.setMeleeWeapon(weapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(8) == 0) {
			setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.whiteWalkersBoots));
			setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.whiteWalkersLeggings));
			setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.whiteWalkersChestplate));
		}
		return data;
	}
}
