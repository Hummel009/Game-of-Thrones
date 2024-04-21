package got.common.entity.essos.legendary.quest;

import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.ai.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityDaenerysTargaryen extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDaenerysTargaryen(World world) {
		super(world);
		addTargetTasks(false);
		setupLegendaryNPC(true);
		setSize(0.6f, 1.8f);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, false));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.ESSOS, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.RICH_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
		isImmuneToFire = true;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	@Override
	public GOTMiniQuest createMiniQuest() {
		return GOTMiniQuestFactory.DAENERYS.createQuest(this);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		dropItem(GOTItems.rhaegarSword, 1);
		dropItem(GOTItems.targaryenBoots, 1);
		dropItem(GOTItems.targaryenLeggings, 1);
		dropItem(GOTItems.targaryenChestplate, 1);
		dropItem(GOTItems.targaryenHelmet, 1);
		dropItem(GOTItems.bloodOfTrueKings, 1);
		dropItem(GOTItems.blackArakh, 1);
	}

	@Override
	public float getAlignmentBonus() {
		return 500.0f;
	}

	@Override
	public GOTMiniQuestFactory getBountyHelpSpeechDir() {
		return GOTMiniQuestFactory.DAENERYS;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.DOTHRAKI;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killDaenerysTargaryen;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "legendary/daenerys_friendly";
		}
		return "legendary/daenerys_hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(false);
	}
}