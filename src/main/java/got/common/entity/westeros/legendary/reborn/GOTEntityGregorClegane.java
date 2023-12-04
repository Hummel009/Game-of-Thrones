package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.ai.GOTEntityAIFollowHiringPlayer;
import got.common.entity.ai.GOTEntityAIHiredRemainStill;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.legendary.warrior.GOTEntitySandorClegane;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityGregorClegane extends GOTEntityHumanBase {
	public GOTEntityGregorClegane(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks();
		setIsLegendaryNPC();
		setSize(0.78000003f, 2.34f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, false));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
	}

	public void addTargetTasks() {
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntitySandorClegane.class, 0, true));
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
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
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.gregorCleganeSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public boolean shouldBurningPanic() {
		return false;
	}

	public static class GregorCleganeAlive extends GOTEntityGregorClegane {
		public GregorCleganeAlive(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void applyEntityAttributes() {
			super.applyEntityAttributes();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		}

		@Override
		public boolean attackEntityFrom(DamageSource damagesource, float f) {
			ItemStack itemstack;
			Entity entity = damagesource.getEntity();
			if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && itemstack.getItem() == GOTItems.sunspear) {
				return super.attackEntityFrom(damagesource, f);
			}
			if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && itemstack.getItem() == GOTItems.sandorCleganeSword) {
				return super.attackEntityFrom(damagesource, f);
			}
			if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && itemstack.getItem() == GOTItems.crowbar) {
				return super.attackEntityFrom(damagesource, f);
			}
			if (damagesource == DamageSource.fall) {
				return super.attackEntityFrom(damagesource, f);
			}
			return super.attackEntityFrom(damagesource, 1.0f);
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.WESTERLANDS;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityplayer) {
			if (isFriendly(entityplayer)) {
				return "legendary/gregor_friendly";
			}
			return "legendary/gregor_hostile";
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				GOTEntityGregorClegane.GregorCleganeDead dead = new GOTEntityGregorClegane.GregorCleganeDead(worldObj);
				dead.copyLocationAndAnglesFrom(this);
				dead.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(dead);
				setDead();
			}
		}
	}

	public static class GregorCleganeDead extends GOTEntityGregorClegane {
		public GregorCleganeDead(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void applyEntityAttributes() {
			super.applyEntityAttributes();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		}

		@Override
		public boolean attackEntityFrom(DamageSource damagesource, float f) {
			Entity entity = damagesource.getEntity();
			if (damagesource == DamageSource.fall) {
				return super.attackEntityFrom(damagesource, f);
			}
			ItemStack itemstack;
			if (entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && itemstack.getItem() == GOTItems.crowbar) {
				return super.attackEntityFrom(damagesource, f);
			}
			return super.attackEntityFrom(damagesource, 0.0f);
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
			dropItem(GOTItems.gregorCleganeSword, 1);
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.CROWNLANDS;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.killGregorClegane;
		}
	}
}