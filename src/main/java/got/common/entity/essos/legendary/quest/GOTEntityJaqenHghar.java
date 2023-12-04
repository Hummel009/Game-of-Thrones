package got.common.entity.essos.legendary.quest;

import got.common.GOTJaqenHgharTracker;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.ai.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestWelcome;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GOTEntityJaqenHghar extends GOTEntityHumanBase {
	public GOTEntityJaqenHghar(World world) {
		super(world);
		isImmuneToFrost = true;
		canBeMarried = false;
		addTargetTasks(false);
		setSize(0.6f, 1.8f);
		getNavigator().setAvoidsWater(true);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new GOTEntityAIHiredRemainStill(this));
		tasks.addTask(2, new GOTEntityAIAttackOnCollide(this, 1.4, false));
		tasks.addTask(3, new GOTEntityAIFollowHiringPlayer(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIWander(this, 1.0));
		tasks.addTask(6, new GOTEntityAIEat(this, GOTFoods.ESSOS, 8000));
		tasks.addTask(6, new GOTEntityAIDrink(this, GOTFoods.ESSOS_DRINK, 8000));
		tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
		tasks.addTask(7, new EntityAIWatchClosest2(this, GOTEntityNPC.class, 5.0f, 0.02f));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
		tasks.addTask(9, new EntityAILookIdle(this));
	}

	public boolean addMQOfferFor(EntityPlayer entityplayer) {
		GOTMiniQuestWelcome quest;
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (pd.getMiniQuestsForEntity(this, true).isEmpty() && ((GOTMiniQuest) (quest = new GOTMiniQuestWelcome(null, this))).canPlayerAccept(entityplayer)) {
			questInfo.setPlayerSpecificOffer(entityplayer, quest);
			return true;
		}
		return false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
	}

	public void arriveAt(EntityPlayer entityplayer) {
		Collection<EntityPlayer> msgPlayers = new ArrayList<>();
		if (entityplayer != null) {
			msgPlayers.add(entityplayer);
		}
		List<EntityPlayer> worldPlayers = worldObj.playerEntities;
		for (EntityPlayer obj : worldPlayers) {
			if (msgPlayers.contains(obj)) {
				continue;
			}
			double d = 64.0;
			double dSq = d * d;
			if (getDistanceSqToEntity(obj) >= dSq) {
				continue;
			}
			msgPlayers.add(obj);
		}
		for (EntityPlayer player : msgPlayers) {
			GOTSpeech.sendSpeechAndChatMessage(player, this, "legendary/jaqen_arrive");
		}
		doJaqenFX();
	}

	public void depart() {
		Collection<EntityPlayer> msgPlayers = new ArrayList<>();
		List<EntityPlayer> worldPlayers = worldObj.playerEntities;
		for (EntityPlayer obj : worldPlayers) {
			if (!msgPlayers.contains(obj)) {
				double d = 64.0;
				double dSq = d * d;
				if (getDistanceSqToEntity(obj) < dSq) {
					msgPlayers.add(obj);
				}
			}
		}
		for (EntityPlayer player : msgPlayers) {
			GOTSpeech.sendSpeechAndChatMessage(player, this, "legendary/jaqen_depart");
		}
		doJaqenFX();
		setDead();
	}

	public void doJaqenFX() {
		playSound("random.pop", 2.0f, 0.5f + rand.nextFloat() * 0.5f);
		worldObj.setEntityState(this, (byte) 16);
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.LORATH;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killJaqenHghar;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			return "legendary/jaqen_friendly";
		}
		return "legendary/jaqen_hostile";
	}

	@Override
	public int getTotalArmorValue() {
		return 15;
	}

	@Override
	public void onArtificalSpawn() {
		GOTJaqenHgharTracker.addNewJaqenHghar(getUniqueID());
		arriveAt(null);
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
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && !GOTJaqenHgharTracker.isJaqenHgharActive(getUniqueID()) && getAttackTarget() == null) {
			depart();
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data1;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public boolean speakTo(EntityPlayer entityplayer) {
		if (GOTJaqenHgharTracker.isJaqenHgharActive(getUniqueID())) {
			if (questInfo.getOfferFor(entityplayer) != null) {
				return super.speakTo(entityplayer);
			}
			if (addMQOfferFor(entityplayer)) {
				GOTJaqenHgharTracker.setJaqenHgharActive(getUniqueID());
				String speechBank = "legendary/jaqen_welcome";
				sendSpeechBank(entityplayer, speechBank);
				return true;
			}
		}
		return super.speakTo(entityplayer);
	}
}
