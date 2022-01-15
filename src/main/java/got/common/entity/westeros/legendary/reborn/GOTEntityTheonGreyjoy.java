package got.common.entity.westeros.legendary.reborn;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.entity.westeros.legendary.quest.GOTEntityRamsayBolton;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityTheonGreyjoy extends GOTEntityHumanBase {
	public GOTEntityTheonGreyjoy(World world) {
		super(world);
		canBeMarried = false;
		setIsLegendaryNPC();
		setSize(0.6f, 1.8f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
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
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class Normal extends GOTEntityTheonGreyjoy {
		public Normal(World world) {
			super(world);
			canBeMarried = false;
			tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, false));
			addTargetTasks();
		}

		public void addTargetTasks() {
			int target = addTargetTasks(true);
			targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityRamsayBolton.class, 0, true));
		}

		@Override
		public float getAlignmentBonus() {
			return 300.0f;
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.IRONBORN;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.TORMENT_THEON_GREYJOY;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityplayer) {
			if (isFriendly(entityplayer)) {
				return "legendary/theon_friendly";
			}
			return "standart/civilized/usual_hostile";
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
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				GOTEntityTheonGreyjoy.Tormented tormented = new GOTEntityTheonGreyjoy.Tormented(worldObj);
				tormented.copyLocationAndAnglesFrom(this);
				tormented.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(tormented);
				setDead();
			}
		}

		@Override
		public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
			data = super.onSpawnWithEgg(data);
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosSword));
			npcItemsInv.setIdleItem(null);
			return data;
		}
	}

	public static class Tormented extends GOTEntityTheonGreyjoy {
		public Tormented(World world) {
			super(world);
			canBeMarried = false;
			addTargetTasks(false);
			tasks.addTask(2, new EntityAIPanic(this, 1.4));
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.NORTH;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityplayer) {
			if (isFriendly(entityplayer)) {
				return "legendary/theon_tormented_friendly";
			}
			return "standart/civilized/usual_hostile";
		}
	}
}