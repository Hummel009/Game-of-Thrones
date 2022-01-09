package got.common.entity.westeros;

import got.common.database.*;
import got.common.entity.ai.*;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityWhore extends GOTEntityHumanBase {
	public GOTEntityWhore(World world) {
		super(world);
		canBeMarried = false;
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		addTargetTasks(false);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, createWhoreAttackAI());
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(4, new GOTEntityAIEat(this, GOTFoods.WESTEROS, 8000));
		tasks.addTask(5, new GOTEntityAIDrink(this, GOTFoods.WESTEROS_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	public EntityAIBase createWhoreAttackAI() {
		return new EntityAIPanic(this, 1.4);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.UNALIGNED;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.KILL_WHORE;
	}

	@Override
	public String getNPCName() {
		return familyInfo.getName();
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "standart/special/whore_friendly";
		}
		return "standart/special/whore_hostile";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}

	@Override
	public void setupNPCName() {
		familyInfo.setName(GOTNames.getWesterosName(rand, familyInfo.isMale()));
	}
}
