package got.common.entity.essos.legendary;

import got.common.database.GOTFoods;
import got.common.entity.ai.*;
import got.common.entity.essos.legendary.captain.GOTEntityKraznysMoNakloz;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityMissandei extends GOTEntityHumanBase {
	public GOTEntityMissandei(World world) {
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
		tasks.addTask(5, new GOTEntityAIEat(this, GOTFoods.ESSOS, 8000));
		tasks.addTask(5, new GOTEntityAIDrink(this, GOTFoods.ESSOS_DRINK, 8000));
		tasks.addTask(6, new GOTEntityAINPCFollowNPC(this, GOTEntityKraznysMoNakloz.class));
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
	public float getAlignmentBonus() {
		return 0.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.GHISCAR;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "legendary/missandei_friendly";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}