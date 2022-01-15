package got.common.entity.westeros.legendary.reborn;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityBericDondarrion extends GOTEntityHumanBase {
	public GOTEntityBericDondarrion(World world) {
		super(world);
		canBeMarried = false;
		setIsLegendaryNPC();
		setSize(0.6f, 1.8f);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, false));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.WESTEROS, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.WESTEROS_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.RIVERLANDS;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "legendary/beric_friendly";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 12;
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
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.bericSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class LifeStage1 extends GOTEntityBericDondarrion {
		public LifeStage1(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				LifeStage2 stage = new LifeStage2(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class LifeStage2 extends GOTEntityBericDondarrion {
		public LifeStage2(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				LifeStage3 stage = new LifeStage3(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class LifeStage3 extends GOTEntityBericDondarrion {
		public LifeStage3(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				LifeStage4 stage = new LifeStage4(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class LifeStage4 extends GOTEntityBericDondarrion {
		public LifeStage4(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				LifeStage5 stage = new LifeStage5(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class LifeStage5 extends GOTEntityBericDondarrion {
		public LifeStage5(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				LifeStage6 stage = new LifeStage6(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class LifeStage6 extends GOTEntityBericDondarrion {
		public LifeStage6(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
			dropItem(GOTRegistry.bericSword, 1);
		}

		@Override
		public float getAlignmentBonus() {
			return 200.0f;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.KILL_BERIC_DONDARRION;
		}
	}
}