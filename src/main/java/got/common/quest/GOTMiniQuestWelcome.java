package got.common.quest;

import got.client.GOTKeyHandler;
import got.common.GOTJaqenHgharTracker;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.essos.legendary.quest.GOTEntityJaqenHghar;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GOTMiniQuestWelcome extends GOTMiniQuest {
	private static final String SPEECHBANK = "legendary/jaqen_quest";

	private boolean movedOn;
	private int stage;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTMiniQuestWelcome(GOTPlayerData pd) {
		super(pd);
	}

	public GOTMiniQuestWelcome(GOTPlayerData pd, GOTEntityJaqenHghar jaqen) {
		this(pd);
		setNPCInfo(jaqen);
		speechBankStart = "";
		speechBankProgress = "";
		speechBankComplete = "";
		speechBankTooMany = "";
		quoteStart = GOTSpeech.getSpeechAtLine(SPEECHBANK, 2);
		quoteComplete = GOTSpeech.getSpeechAtLine(SPEECHBANK, 12);
	}

	public static boolean[] forceMenuMapFactions(EntityPlayer entityplayer) {
		boolean[] flags = {false, false};
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		List<GOTMiniQuest> activeQuests = pd.getActiveMiniQuests();
		for (GOTMiniQuest quest : activeQuests) {
			if (quest instanceof GOTMiniQuestWelcome) {
				GOTMiniQuestWelcome qw = (GOTMiniQuestWelcome) quest;
				if (qw.stage == 5) {
					flags[0] = true;
					return flags;
				}
				if (qw.stage == 11) {
					flags[1] = true;
					return flags;
				}
			}
		}
		return flags;
	}

	@Override
	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		return !pd.hasAnyJHQuest();
	}

	@Override
	public boolean canRewardVariousExtraItems() {
		return false;
	}

	@Override
	protected void complete(EntityPlayer entityplayer, GOTEntityNPC npc) {
		super.complete(entityplayer, npc);
		updateJaqenHghar();
	}

	@Override
	public float getReputationBonus() {
		return 0.0f;
	}

	@Override
	public int getCoinBonus() {
		return 0;
	}

	@Override
	public float getCompletionFactor() {
		return stage / 15.0f;
	}

	@Override
	public String getFactionSubtitle() {
		return "";
	}

	@Override
	public String getObjectiveInSpeech() {
		return "OBJECTIVE_SPEECH";
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return "OBJECTIVE_SPEECH_PROGRESSED";
	}

	@Override
	public String getQuestFailure() {
		if (movedOn) {
			return StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.movedOn", entityName);
		}
		return super.getQuestFailure();
	}

	@Override
	public String getQuestFailureShorthand() {
		if (movedOn) {
			return StatCollector.translateToLocal("got.gui.redBook.mq.movedOn");
		}
		return super.getQuestFailureShorthand();
	}

	@Override
	public ItemStack getQuestIcon() {
		return new ItemStack(GOTItems.questBook);
	}

	@Override
	public String getQuestObjective() {
		KeyBinding keyMenu = GOTKeyHandler.KEY_BINDING_MENU;
		switch (stage) {
			case 2:
				return StatCollector.translateToLocal("got.miniquest.welcome.book");
			case 5:
				return StatCollector.translateToLocalFormatted("got.miniquest.welcome.map", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
			case 8:
				KeyBinding keyLeft = GOTKeyHandler.KEY_BINDING_REPUTATION_CYCLE_LEFT;
				KeyBinding keyRight = GOTKeyHandler.KEY_BINDING_REPUTATION_CYCLE_RIGHT;
				return StatCollector.translateToLocalFormatted("got.miniquest.welcome.rep", GameSettings.getKeyDisplayString(keyLeft.getKeyCode()), GameSettings.getKeyDisplayString(keyRight.getKeyCode()));
			case 9:
				KeyBinding keyUp = GOTKeyHandler.KEY_BINDING_REPUTATION_GROUP_PREV;
				KeyBinding keyDown = GOTKeyHandler.KEY_BINDING_REPUTATION_GROUP_NEXT;
				return StatCollector.translateToLocalFormatted("got.miniquest.welcome.repRegions", GameSettings.getKeyDisplayString(keyUp.getKeyCode()), GameSettings.getKeyDisplayString(keyDown.getKeyCode()));
			case 11:
				return StatCollector.translateToLocalFormatted("got.miniquest.welcome.factions", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
			default:
				return StatCollector.translateToLocal("got.miniquest.welcome.speak");
		}
	}

	@Override
	public String getQuestProgress() {
		return getQuestProgressShorthand();
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("got.miniquest.progressShort", stage, 15);
	}

	@Override
	public void handleEvent(GOTMiniQuestEvent event) {
		switch (stage) {
			case 2:
				if (event instanceof GOTMiniQuestEvent.OpenRedBook) {
					stage = 3;
					playerData.updateMiniQuest(this);
					updateJaqenHghar();
				}
				break;
			case 5:
				if (event instanceof GOTMiniQuestEvent.ViewMap) {
					stage = 6;
					playerData.updateMiniQuest(this);
					updateJaqenHghar();
				}
				break;
			case 8:
				if (event instanceof GOTMiniQuestEvent.CycleReputation) {
					stage = 9;
					playerData.updateMiniQuest(this);
					updateJaqenHghar();
				}
				break;
			case 9:
				if (event instanceof GOTMiniQuestEvent.CycleReputationRegion) {
					stage = 10;
					playerData.updateMiniQuest(this);
					updateJaqenHghar();
				}
				break;
			case 11:
				if (event instanceof GOTMiniQuestEvent.ViewFactions) {
					stage = 12;
					playerData.updateMiniQuest(this);
					updateJaqenHghar();
				}
				break;
		}
	}

	@Override
	public boolean isFailed() {
		return super.isFailed() || movedOn;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		updateJaqenHghar();
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		String line;
		Collection<ItemStack> dropItems = new ArrayList<>();
		switch (stage) {
			case 1:
				dropItems.add(new ItemStack(GOTItems.questBook));
				npc.dropItemList(dropItems, true);
				dropItems.clear();
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 4);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 2;
				playerData.updateMiniQuest(this);
				break;
			case 2:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 4);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			case 3:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 5);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 4;
				playerData.updateMiniQuest(this);
				break;
			case 4:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 6);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 5;
				playerData.updateMiniQuest(this);
				break;
			case 5:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 6);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			case 6:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 7);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 7;
				playerData.updateMiniQuest(this);
				break;
			case 7:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 8);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 8;
				playerData.updateMiniQuest(this);
				break;
			case 8:
			case 9:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 8);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			case 10:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 9);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 11;
				playerData.updateMiniQuest(this);
				break;
			case 11:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 9);
				sendQuoteSpeech(entityplayer, npc, line);
				break;
			case 12:
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 10);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 13;
				playerData.updateMiniQuest(this);
				break;
			case 13:
				if (!pd.getQuestData().getGivenFirstPouches()) {
					dropItems.add(new ItemStack(GOTItems.pouch, 1, 0));
					dropItems.add(new ItemStack(GOTItems.pouch, 1, 0));
					dropItems.add(new ItemStack(GOTItems.valyrianDagger));
					pd.getQuestData().setGivenFirstPouches(true);
				}
				npc.dropItemList(dropItems, true);
				dropItems.clear();
				line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 11);
				sendQuoteSpeech(entityplayer, npc, line);
				quotesStages.add(line);
				stage = 14;
				playerData.updateMiniQuest(this);
				break;
			case 14:
				stage = 15;
				playerData.updateMiniQuest(this);
				complete(entityplayer, npc);
				break;
		}
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
	}

	@Override
	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
	}

	@Override
	public void onPlayerTick() {
		if (!GOTJaqenHgharTracker.isJaqenHgharActive(entityUUID)) {
			movedOn = true;
			playerData.updateMiniQuest(this);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		stage = nbt.getByte("WStage");
		movedOn = nbt.getBoolean("WMovedOn");
	}

	@Override
	public void start(EntityPlayer entityplayer, GOTEntityNPC npc) {
		super.start(entityplayer, npc);
		String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 3);
		sendQuoteSpeech(entityplayer, npc, line);
		quotesStages.add(line);
		stage = 1;
		playerData.updateMiniQuest(this);
		updateJaqenHghar();
	}

	private void updateJaqenHghar() {
		GOTJaqenHgharTracker.setJaqenHgharActive(entityUUID);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("WStage", (byte) stage);
		nbt.setBoolean("WMovedOn", movedOn);
	}
}