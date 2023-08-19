package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.ai.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
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
	public void dropFewItems(boolean flag, int i) {
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
		return "standard/civilized/usual_hostile";
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
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bericSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class BericDondarrionLife1 extends GOTEntityBericDondarrion {
		public BericDondarrionLife1(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife2 stage = new BericDondarrionLife2(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife2 extends GOTEntityBericDondarrion {
		public BericDondarrionLife2(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife3 stage = new BericDondarrionLife3(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife3 extends GOTEntityBericDondarrion {
		public BericDondarrionLife3(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife4 stage = new BericDondarrionLife4(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife4 extends GOTEntityBericDondarrion {
		public BericDondarrionLife4(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife5 stage = new BericDondarrionLife5(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife5 extends GOTEntityBericDondarrion {
		public BericDondarrionLife5(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife6 stage = new BericDondarrionLife6(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife6 extends GOTEntityBericDondarrion {
		public BericDondarrionLife6(World world) {
			super(world);
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
			dropItem(GOTItems.bericSword, 1);
		}

		@Override
		public float getAlignmentBonus() {
			return 200.0f;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.killBericDondarrion;
		}
	}
}