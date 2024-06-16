package got.common.entity.essos.legendary.quest;

import got.common.GOTJaqenHgharTracker;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestWelcome;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GOTEntityJaqenHghar extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJaqenHghar(World world) {
		super(world);
		setupLegendaryNPC(false);
	}

	private boolean addMQOfferFor(EntityPlayer entityplayer) {
		GOTMiniQuestWelcome quest;
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (pd.getMiniQuestsForEntity(this, true).isEmpty() && ((GOTMiniQuest) (quest = new GOTMiniQuestWelcome(null, this))).canPlayerAccept(entityplayer)) {
			questInfo.setPlayerSpecificOffer(entityplayer, quest);
			return true;
		}
		return false;
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

	private void depart() {
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

	private void doJaqenFX() {
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
	public String getSpeechBank(EntityPlayer entityPlayer) {
		if (isFriendly(entityPlayer)) {
			return "legendary/jaqen_friendly";
		}
		return "legendary/jaqen_hostile";
	}

	@Override
	public void onArtificalSpawn() {
		GOTJaqenHgharTracker.addNewJaqenHghar(getUniqueID());
		arriveAt(null);
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
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
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