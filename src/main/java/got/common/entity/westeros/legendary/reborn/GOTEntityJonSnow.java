package got.common.entity.westeros.legendary.reborn;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.entity.westeros.legendary.quest.GOTEntityRamsayBolton;
import got.common.entity.westeros.legendary.warrior.GOTEntityRooseBolton;
import got.common.faction.GOTFaction;
import got.common.quest.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityJonSnow extends GOTEntityHumanBase {
	public GOTEntityJonSnow(World world) {
		super(world);
		canBeMarried = false;
		setIsLegendaryNPC();
		setSize(0.6f, 1.8f);
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
		npcCape = GOTCapes.NIGHT;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public int getTotalArmorValue() {
		return 18;
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
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class Stage1 extends GOTEntityJonSnow {
		public Stage1(World world) {
			super(world);
			addTargetTasks(true);
		}

		@Override
		public GOTMiniQuest createMiniQuest() {
			return GOTMiniQuestFactory.JONSNOW.createQuest(this);
		}

		@Override
		public float getAlignmentBonus() {
			return 300.0f;
		}

		@Override
		public GOTMiniQuestFactory getBountyHelpSpeechDir() {
			return GOTMiniQuestFactory.JONSNOW;
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.NIGHT_WATCH;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityplayer) {
			if (isFriendly(entityplayer)) {
				return "standart/civilized/usual_friendly";
			}
			return "standart/civilized/usual_hostile";
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				GOTEntityJonSnow.Stage2 stage2 = new GOTEntityJonSnow.Stage2(worldObj);
				stage2.copyLocationAndAnglesFrom(this);
				stage2.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage2);
				setDead();
			}
		}
	}

	public static class Stage2 extends GOTEntityJonSnow {
		public Stage2(World world) {
			super(world);
			addTargetTasks();
		}

		public void addTargetTasks() {
			int target = addTargetTasks(true);
			target++;
			targetTasks.addTask(target, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityRamsayBolton.class, 0, true));
			targetTasks.addTask(++target, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityRooseBolton.class, 0, true));
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
			dropItem(GOTRegistry.bloodOfTrueKings, 1);
		}

		@Override
		public float getAlignmentBonus() {
			return 200.0f;
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.NORTH;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.KILL_JON_SNOW;
		}
	}
}
