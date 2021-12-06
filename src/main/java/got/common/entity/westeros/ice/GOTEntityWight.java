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
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityWight extends GOTEntityHumanBase {
	public static ItemStack[] militiaWeapons = { new ItemStack(GOTRegistry.westerosSword), new ItemStack(GOTRegistry.westerosHammer), new ItemStack(GOTRegistry.westerosPike), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironBattleaxe), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.bronzeSword), new ItemStack(GOTRegistry.bronzeAxe), new ItemStack(GOTRegistry.bronzeBattleaxe) };
	public static int[] leatherDyes = { 10855845, 8026746, 5526612, 3684408, 8350297, 10388590, 4799795, 5330539, 4211801, 2632504 };

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
		addTargetTasks();
		isNotHuman = true;
		isImmuneToFrost = true;
	}

	public void addTargetTasks() {
		int target = addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityGoldenMan.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityBandit.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityScrapTrader.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityGendryBaratheon.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityBronn.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityGeroldDayne.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityThreeEyedRaven.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityMaester.class, 0, true));
		targetTasks.addTask(target + 1, new GOTEntityAIAttackHostiles(this, GOTEntityWhore.class, 0, true));
	}

    @Override
    public void onArtificalSpawn() {
        if (this.canBeMarried && this.getClass() == this.familyInfo.marriageEntityClass && this.rand.nextInt(7) == 0) {
            this.familyInfo.setChild();
        }
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
		if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && ((EntityLivingBase) entity).getHeldItem().getItem() instanceof GOTMaterialFinder && (((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.VALYRIAN || ((GOTMaterialFinder) itemstack.getItem()).getMaterial() == GOTMaterial.OBSIDIAN || (GOTMaterialFinder) itemstack.getItem() == GOTRegistry.crowbar)) {
			return super.attackEntityFrom(damagesource, f);
		}
		if (damagesource.getEntity() instanceof GOTEntityGregorClegane || damagesource.getEntity() instanceof GOTEntityAsshaiArchmag || damagesource.isFireDamage()) {
			return super.attackEntityFrom(damagesource, f);
		}
		if ((damageSource instanceof GOTEntitySpear && ((GOTEntitySpear) damageSource).getProjectileItem().getItem() == GOTRegistry.valyrianSpear)) {
			return super.attackEntityFrom(damagesource, f);
		}
		return super.attackEntityFrom(damagesource, 0.0f);
	}

	public ItemStack dyeLeather(ItemStack itemstack) {
		int i = rand.nextInt(leatherDyes.length);
		int color = leatherDyes[i];
		((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
		return itemstack;
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
		return GOTAchievement.KILL_WIGHT;
	}

	@Override
	public String getLivingSound() {
		return "mob.zombie.say";
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
		int i = rand.nextInt(militiaWeapons.length);
		npcItemsInv.setMeleeWeapon(militiaWeapons[i].copy());
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, dyeLeather(new ItemStack(Items.leather_boots)));
		setCurrentItemOrArmor(2, dyeLeather(new ItemStack(Items.leather_leggings)));
		setCurrentItemOrArmor(3, dyeLeather(new ItemStack(Items.leather_chestplate)));
		setCurrentItemOrArmor(4, null);
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(rand.nextBoolean());
	}
}
