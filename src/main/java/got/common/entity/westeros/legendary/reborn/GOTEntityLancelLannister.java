package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityLancelLannister extends GOTEntityHumanBase {
	public GOTEntityLancelLannister(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(false);
		setIsLegendaryNPC();
		setSize(0.6f, 1.8f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new EntityAIPanic(this, 1.4));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));

	}

	public static class Normal extends GOTEntityLancelLannister {
		public Normal(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void applyEntityAttributes() {
			super.applyEntityAttributes();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		}

		@Override
		public float getAlignmentBonus() {
			return 100.0f;
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.CROWNLANDS;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityplayer) {
			if (isFriendly(entityplayer)) {
				return "legendary/lancel_friendly";
			}
			return "legendary/lancel_hostile";
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				GOTEntityLancelLannister.Religious religious = new GOTEntityLancelLannister.Religious(worldObj);
				religious.copyLocationAndAnglesFrom(this);
				religious.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(religious);
				setDead();
			}
		}
	}

	public static class Religious extends GOTEntityLancelLannister {
		public Religious(World world) {
			super(world);
			canBeMarried = false;
		}

		@Override
		public void applyEntityAttributes() {
			super.applyEntityAttributes();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.CROWNLANDS;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.KILL_LANCEL_LANNISTER;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityplayer) {
			return "legendary/lancel_religious";
		}
	}
}