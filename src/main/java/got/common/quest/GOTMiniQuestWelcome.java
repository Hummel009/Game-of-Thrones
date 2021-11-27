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
	public static String SPEECHBANK = "legendary/jaqen_quest";
	public static int STAGE_GET_ITEMS = 1;
	public static int STAGE_READ_BOOK = 2;
	public static int STAGE_EXPLAIN_BOOK = 3;
	public static int STAGE_EXPLAIN_MAP = 4;
	public static int STAGE_OPEN_MAP = 5;
	public static int STAGE_EXPLAIN_FACTIONS = 6;
	public static int STAGE_EXPLAIN_ALIGNMENT = 7;
	public static int STAGE_CYCLE_ALIGNMENT = 8;
	public static int STAGE_CYCLE_REGIONS = 9;
	public static int STAGE_EXPLAIN_FACTION_GUIDE = 10;
	public static int STAGE_OPEN_FACTIONS = 11;
	public static int STAGE_TALK_ADVENTURES = 12;
	public static int STAGE_GET_POUCHES = 13;
	public static int STAGE_TALK_FINAL = 14;
	public static int STAGE_COMPLETE = 15;
	public static int NUM_STAGES = 15;
	public int stage = 0;
	public boolean movedOn;

	public GOTMiniQuestWelcome(GOTPlayerData pd) {
		super(pd);
	}

	public GOTMiniQuestWelcome(GOTPlayerData pd, GOTEntityJaqenHghar gandalf) {
		this(pd);
		setNPCInfo(gandalf);
		speechBankStart = "";
		speechBankProgress = "";
		speechBankComplete = "";
		speechBankTooMany = "";
		quoteStart = GOTSpeech.getSpeechAtLine(SPEECHBANK, 2);
		quoteComplete = GOTSpeech.getSpeechAtLine(SPEECHBANK, 12);
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
		updateGreyWanderer();
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
		return new ItemStack(GOTRegistry.questBook);
	}

	@Override
	public String getQuestObjective() {
		if (stage == 2) {
			return StatCollector.translateToLocal("got.miniquest.welcome.book");
		}
		if (stage == 5) {
			KeyBinding keyMenu = GOTKeyHandler.keyBindingMenu;
			return StatCollector.translateToLocalFormatted("got.miniquest.welcome.map", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
		}
		switch (stage) {
		case 8:
			KeyBinding keyLeft = GOTKeyHandler.keyBindingAlignmentCycleLeft;
			KeyBinding keyRight = GOTKeyHandler.keyBindingAlignmentCycleRight;
			return StatCollector.translateToLocalFormatted("got.miniquest.welcome.align", GameSettings.getKeyDisplayString(keyLeft.getKeyCode()), GameSettings.getKeyDisplayString(keyRight.getKeyCode()));
		case 9:
			KeyBinding keyUp = GOTKeyHandler.keyBindingAlignmentGroupPrev;
			KeyBinding keyDown = GOTKeyHandler.keyBindingAlignmentGroupNext;
			return StatCollector.translateToLocalFormatted("got.miniquest.welcome.alignRegions", GameSettings.getKeyDisplayString(keyUp.getKeyCode()), GameSettings.getKeyDisplayString(keyDown.getKeyCode()));
		case 11:
			KeyBinding keyMenu = GOTKeyHandler.keyBindingMenu;
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
		if (event instanceof GOTMiniQuestEvent.OpenRedBook && stage == 2) {
			stage = 3;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof GOTMiniQuestEvent.ViewMap && stage == 5) {
			stage = 6;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof GOTMiniQuestEvent.CycleAlignment && stage == 8) {
			stage = 9;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof GOTMiniQuestEvent.CycleAlignmentRegion && stage == 9) {
			stage = 10;
			updateQuest();
			updateGreyWanderer();
		}
		if (event instanceof GOTMiniQuestEvent.ViewFactions && stage == 11) {
			stage = 12;
			updateQuest();
			updateGreyWanderer();
		}
	}

	@Override
	public boolean isFailed() {
		return super.isFailed() || movedOn;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		updateGreyWanderer();
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (stage == 1) {
			ArrayList<ItemStack> dropItems = new ArrayList<>();
			dropItems.add(new ItemStack(GOTRegistry.questBook));
			npc.dropItemList(dropItems);
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 4);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 2;
			updateQuest();
		} else if (stage == 2) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 4);
			sendQuoteSpeech(entityplayer, npc, line);
		} else if (stage == 3) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 5);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 4;
			updateQuest();
		} else if (stage == 4) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 6);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 5;
			updateQuest();
		} else if (stage == 5) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 6);
			sendQuoteSpeech(entityplayer, npc, line);
		} else if (stage == 6) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 7);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 7;
			updateQuest();
		} else if (stage == 7) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 8);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 8;
			updateQuest();
		} else if ((stage == 8) || (stage == 9)) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 8);
			sendQuoteSpeech(entityplayer, npc, line);
		} else if (stage == 10) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 9);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 11;
			updateQuest();
		} else if (stage == 11) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 9);
			sendQuoteSpeech(entityplayer, npc, line);
		} else if (stage == 12) {
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 10);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 13;
			updateQuest();
		} else if (stage == 13) {
			ArrayList<ItemStack> dropItems = new ArrayList<>();
			if (!pd.getQuestData().getGivenFirstPouches()) {
				dropItems.add(new ItemStack(GOTRegistry.pouch, 1, 0));
				dropItems.add(new ItemStack(GOTRegistry.pouch, 1, 0));
				dropItems.add(new ItemStack(GOTRegistry.valyrianDagger));
				pd.getQuestData().setGivenFirstPouches(true);
			}
			npc.dropItemList(dropItems);
			String line = GOTSpeech.getSpeechAtLine(SPEECHBANK, 11);
			sendQuoteSpeech(entityplayer, npc, line);
			quotesStages.add(line);
			stage = 14;
			updateQuest();
		} else if (stage == 14) {
			stage = 15;
			updateQuest();
			complete(entityplayer, npc);
		}
	}

	@Override
	public void onPlayerTick(EntityPlayer entityplayer) {
		if (!GOTJaqenHgharTracker.isWandererActive(entityUUID)) {
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
		quotesStages.add(line);
		stage = 1;
		updateQuest();
		updateGreyWanderer();
	}

	public void updateGreyWanderer() {
		GOTJaqenHgharTracker.setWandererActive(entityUUID);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("WStage", (byte) stage);
		nbt.setBoolean("WMovedOn", movedOn);
	}

	public static boolean[] forceMenu_Map_Factions(EntityPlayer entityplayer) {
		boolean[] flags = { false, false };
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		List<GOTMiniQuest> activeQuests = pd.getActiveMiniQuests();
		for (GOTMiniQuest quest : activeQuests) {
			if (!(quest instanceof GOTMiniQuestWelcome)) {
				continue;
			}
			GOTMiniQuestWelcome qw = (GOTMiniQuestWelcome) quest;
			if (qw.stage == 5) {
				flags[0] = true;
				break;
			}
			if (qw.stage != 11) {
				continue;
			}
			flags[1] = true;
			break;
		}
		return flags;
	}
}
