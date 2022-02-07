package got.common.quest;

import java.util.*;

import got.client.GOTKeyHandler;
import got.common.*;
import got.common.database.*;
import got.common.entity.essos.legendary.quest.GOTEntityJaqenHghar;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class GOTMiniQuestWelcome extends GOTMiniQuest {
	private static String SPEECHBANK = "legendary/jaqen_quest";
	private int stage = 0;
	private boolean movedOn;

	public GOTMiniQuestWelcome(GOTPlayerData pd) {
		super(pd);
	}

	public GOTMiniQuestWelcome(GOTPlayerData pd, GOTEntityJaqenHghar gandalf) {
		this(pd);
		setNPCInfo(gandalf);
		setSpeechBankStart("");
		setSpeechBankProgress("");
		setSpeechBankComplete("");
		setSpeechBankTooMany("");
		setQuoteStart(GOTSpeech.getSpeechAtLine(SPEECHBANK, 2));
		setQuoteComplete(GOTSpeech.getSpeechAtLine(SPEECHBANK, 12));
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
	public void complete(EntityPlayer entityplayer, GOTEntityNPC npc) {
		super.complete(entityplayer, npc);
		updateJaqenHghar();
	}

	@Override
	public float getAlignmentBonus() {
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
			return StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.movedOn", getEntityName());
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
		return new ItemStack(GOTRegistry.questBook);
	}

	@Override
	public String getQuestObjective() {
		KeyBinding keyMenu = GOTKeyHandler.getKeyBindingMenu();
		switch (stage) {
		case 2:
			return StatCollector.translateToLocal("got.miniquest.welcome.book");
		case 5:
			return StatCollector.translateToLocalFormatted("got.miniquest.welcome.map", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
		case 8:
			KeyBinding keyLeft = GOTKeyHandler.getKeyBindingAlignmentCycleLeft();
			KeyBinding keyRight = GOTKeyHandler.getKeyBindingAlignmentCycleRight();
			return StatCollector.translateToLocalFormatted("got.miniquest.welcome.align", GameSettings.getKeyDisplayString(keyLeft.getKeyCode()), GameSettings.getKeyDisplayString(keyRight.getKeyCode()));
		case 9:
			KeyBinding keyUp = GOTKeyHandler.getKeyBindingAlignmentGroupPrev();
			KeyBinding keyDown = GOTKeyHandler.getKeyBindingAlignmentGroupNext();
			return StatCollector.translateToLocalFormatted("got.miniquest.welcome.alignRegions", GameSettings.getKeyDisplayString(keyUp.getKeyCode()), GameSettings.getKeyDisplayString(keyDown.getKeyCode()));
		case 11:
			return StatCollector.translateToLocalFormatted("got.miniquest.welcome.factions", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
		default:
			break;
		}
		return StatCollector.translateToLocal("got.miniquest.welcome.speak");
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
			if (event instanceof GOTMiniQuestEvent.OpenQuestBook) {
				stage = 3;
				updateQuest();
				updateJaqenHghar();
			}
			break;
		case 5:
			if (event instanceof GOTMiniQuestEvent.ViewMap) {
				stage = 6;
				updateQuest();
				updateJaqenHghar();
			}
			break;
		case 8:
			if (event instanceof GOTMiniQuestEvent.CycleAlignment) {
				stage = 9;
				updateQuest();
				updateJaqenHghar();
			}
			break;
		case 9:
			if (event instanceof GOTMiniQuestEvent.CycleAlignmentRegion) {
				stage = 10;
				updateQuest();
				updateJaqenHghar();
			}
			break;
		case 11:
			if (event instanceof GOTMiniQuestEvent.ViewFactions) {
				stage = 12;
				updateQuest();
				updateJaqenHghar();
			}
			break;
		default:
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
		ArrayList<ItemStack> dropItems = new ArrayList<>();
		switch (stage) {
		case 1:
			dropItems.add(new ItemStack(GOTRegistry.questBook));
			npc.dropItemList(dropItems);
			dropItems.clear();
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 4);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 2;
			updateQuest();
			break;
		case 2:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 4);
			sendQuoteSpeech(entityplayer, npc, line);
			break;
		case 3:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 5);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 4;
			updateQuest();
			break;
		case 4:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 6);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 5;
			updateQuest();
			break;
		case 5:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 6);
			sendQuoteSpeech(entityplayer, npc, line);
			break;
		case 6:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 7);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 7;
			updateQuest();
			break;
		case 7:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 8);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 8;
			updateQuest();
			break;
		case 8:
		case 9:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 8);
			sendQuoteSpeech(entityplayer, npc, line);
			break;
		case 10:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 9);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 11;
			updateQuest();
			break;
		case 11:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 9);
			sendQuoteSpeech(entityplayer, npc, line);
			break;
		case 12:
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 10);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 13;
			updateQuest();
			break;
		case 13:
			if (!pd.getQuestData().getGivenFirstPouches()) {
				dropItems.add(new ItemStack(GOTRegistry.pouch, 1, 0));
				dropItems.add(new ItemStack(GOTRegistry.pouch, 1, 0));
				dropItems.add(new ItemStack(GOTRegistry.valyrianDagger));
				pd.getQuestData().setGivenFirstPouches(true);
			}
			npc.dropItemList(dropItems);
			dropItems.clear();
			line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 11);
			sendQuoteSpeech(entityplayer, npc, line);
			getQuotesStages().add(line);
			stage = 14;
			updateQuest();
			break;
		case 14:
			stage = 15;
			updateQuest();
			complete(entityplayer, npc);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPlayerTick(EntityPlayer entityplayer) {
		if (!GOTJaqenHgharTracker.isFacelessActive(getEntityUUID())) {
			movedOn = true;
			updateQuest();
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
		getQuotesStages().add(line);
		stage = 1;
		updateQuest();
		updateJaqenHghar();
	}

	private void updateJaqenHghar() {
		GOTJaqenHgharTracker.setFacelessActive(getEntityUUID());
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("WStage", (byte) stage);
		nbt.setBoolean("WMovedOn", movedOn);
	}

	public static boolean[] forceMenuMapFactions(EntityPlayer entityplayer) {
		boolean[] flags = { false, false };
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		List<GOTMiniQuest> activeQuests = pd.getActiveMiniQuests();
		for (GOTMiniQuest quest : activeQuests) {
			if (quest instanceof GOTMiniQuestWelcome) {
				GOTMiniQuestWelcome qw = (GOTMiniQuestWelcome) quest;
				switch (qw.stage) {
				case 5:
					flags[0] = true;
					break;
				case 11:
					continue;
				default:
					break;
				}
				flags[1] = true;
				break;
			}
		}
		return flags;
	}
}
