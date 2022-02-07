package got.common.quest;

import java.util.*;

import got.common.*;
import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;

public class GOTMiniQuestBounty extends GOTMiniQuest {
	private UUID targetID;
	private String targetName;
	private boolean killed;
	private float alignmentBonus;
	private int coinBonus;
	private boolean bountyClaimedByOther;
	private boolean killedByBounty;

	public GOTMiniQuestBounty(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		if (super.canPlayerAccept(entityplayer) && !getTargetID().equals(entityplayer.getUniqueID()) && GOTLevelData.getData(entityplayer).getAlignment(getEntityFaction()) >= 100.0f) {
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			List<GOTMiniQuest> active = pd.getActiveMiniQuests();
			for (GOTMiniQuest quest : active) {
				if (!(quest instanceof GOTMiniQuestBounty) || !((GOTMiniQuestBounty) quest).getTargetID().equals(getTargetID())) {
					continue;
				}
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void complete(EntityPlayer entityplayer, GOTEntityNPC npc) {
		boolean specialReward;
		GOTPlayerData pd = getPlayerData();
		pd.addCompletedBountyQuest();
		int bComplete = pd.getCompletedBountyQuests();
		specialReward = bComplete > 0 && bComplete % 5 == 0;
		if (specialReward) {
			ItemStack trophy = new ItemStack(GOTRegistry.bountyTrophy);
			getRewardItemTable().add(trophy);
		}
		super.complete(entityplayer, npc);
	}

	@Override
	public float getAlignmentBonus() {
		return alignmentBonus;
	}

	@Override
	public int getCoinBonus() {
		return coinBonus;
	}

	@Override
	public float getCompletionFactor() {
		return isKilled() ? 1.0f : 0.0f;
	}

	private float getKilledAlignmentPenalty() {
		return -getAlignmentBonus() * 2.0f;
	}

	@Override
	public String getObjectiveInSpeech() {
		return getTargetName();
	}

	public GOTFaction getPledgeOrHighestAlignmentFaction(EntityPlayer entityplayer, float min) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (pd.getPledgeFaction() != null) {
			return pd.getPledgeFaction();
		}
		ArrayList<GOTFaction> highestFactions = new ArrayList<>();
		float highestAlignment = min;
		for (GOTFaction f : GOTFaction.getPlayableAlignmentFactions()) {
			float alignment = pd.getAlignment(f);
			if (alignment <= min) {
				continue;
			}
			if (alignment > highestAlignment) {
				highestFactions.clear();
				highestFactions.add(f);
				highestAlignment = alignment;
				continue;
			}
			if (alignment != highestAlignment) {
				continue;
			}
			highestFactions.add(f);
		}
		if (!highestFactions.isEmpty()) {
			Random rand = entityplayer.getRNG();
			return highestFactions.get(rand.nextInt(highestFactions.size()));
		}
		return null;
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return getTargetName();
	}

	@Override
	public String getQuestFailure() {
		if (killedByBounty) {
			return StatCollector.translateToLocalFormatted("got.miniquest.bounty.killedBy", getTargetName());
		}
		if (bountyClaimedByOther) {
			return StatCollector.translateToLocalFormatted("got.miniquest.bounty.claimed", getTargetName());
		}
		return super.getQuestFailure();
	}

	@Override
	public String getQuestFailureShorthand() {
		if (killedByBounty) {
			return StatCollector.translateToLocal("got.miniquest.bounty.killedBy.short");
		}
		if (bountyClaimedByOther) {
			return StatCollector.translateToLocal("got.miniquest.bounty.claimed.short");
		}
		return super.getQuestFailureShorthand();
	}

	@Override
	public ItemStack getQuestIcon() {
		return new ItemStack(Items.iron_sword);
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("got.miniquest.bounty", getTargetName());
	}

	@Override
	public String getQuestProgress() {
		if (isKilled()) {
			return StatCollector.translateToLocal("got.miniquest.bounty.progress.slain");
		}
		return StatCollector.translateToLocal("got.miniquest.bounty.progress.notSlain");
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("got.miniquest.progressShort", isKilled() ? 1 : 0, 1);
	}

	public UUID getTargetID() {
		return targetID;
	}

	public String getTargetName() {
		return targetName;
	}

	@Override
	public boolean isFailed() {
		return super.isFailed() || bountyClaimedByOther || killedByBounty;
	}

	public boolean isKilled() {
		return killed;
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && getTargetID() != null;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		if (isKilled()) {
			complete(entityplayer, npc);
		} else {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (!isKilled() && !isFailed() && entity instanceof EntityPlayer && ((EntityPlayer) entity).getUniqueID().equals(getTargetID())) {
			EntityPlayer slainPlayer = (EntityPlayer) entity;
			GOTPlayerData slainPlayerData = GOTLevelData.getData(slainPlayer);
			setKilled(true);
			GOTFactionBounties.forFaction(getEntityFaction()).forPlayer(slainPlayer).recordBountyKilled();
			updateQuest();
			GOTFaction highestFaction = getPledgeOrHighestAlignmentFaction(slainPlayer, 100.0f);
			if (highestFaction != null) {
				float alignmentLoss;
				float curAlignment = slainPlayerData.getAlignment(highestFaction);
				alignmentLoss = getKilledAlignmentPenalty();
				if (curAlignment + alignmentLoss < 100.0f) {
					alignmentLoss = -(curAlignment - 100.0f);
				}
				GOTAlignmentValues.AlignmentBonus source = new GOTAlignmentValues.AlignmentBonus(alignmentLoss, "got.alignment.bountyKill");
				slainPlayerData.addAlignment(slainPlayer, source, highestFaction, entityplayer);
				ChatComponentTranslation slainMsg1 = new ChatComponentTranslation("got.chat.bountyKilled1", entityplayer.getCommandSenderName(), getEntityFaction().factionName());
				ChatComponentTranslation slainMsg2 = new ChatComponentTranslation("got.chat.bountyKilled2", highestFaction.factionName());
				slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainPlayer.addChatMessage(slainMsg1);
				slainPlayer.addChatMessage(slainMsg2);
			}
			ChatComponentTranslation announceMsg = new ChatComponentTranslation("got.chat.bountyKill", entityplayer.getCommandSenderName(), slainPlayer.getCommandSenderName(), getEntityFaction().factionName());
			announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				EntityPlayer otherPlayer = (EntityPlayer) obj;
				if (otherPlayer == slainPlayer) {
					continue;
				}
				otherPlayer.addChatMessage(announceMsg);
			}
		}
	}

	@Override
	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
		if (!isKilled() && !isFailed() && killer.getUniqueID().equals(getTargetID())) {
			float curAlignment;
			GOTPlayerData pd;
			GOTPlayerData killerData = GOTLevelData.getData(killer);
			GOTFaction killerHighestFaction = getPledgeOrHighestAlignmentFaction(killer, 0.0f);
			if (killerHighestFaction != null) {
				float killerBonus = getAlignmentBonus();
				GOTAlignmentValues.AlignmentBonus source = new GOTAlignmentValues.AlignmentBonus(killerBonus, "got.alignment.killedHunter");
				killerData.addAlignment(killer, source, killerHighestFaction, entityplayer);
			}
			curAlignment = (pd = getPlayerData()).getAlignment(getEntityFaction());
			if (curAlignment > 100.0f) {
				float alignmentLoss = getKilledAlignmentPenalty();
				if (curAlignment + alignmentLoss < 100.0f) {
					alignmentLoss = -(curAlignment - 100.0f);
				}
				GOTAlignmentValues.AlignmentBonus source = new GOTAlignmentValues.AlignmentBonus(alignmentLoss, "got.alignment.killedByBounty");
				pd.addAlignment(entityplayer, source, getEntityFaction(), killer);
				ChatComponentTranslation slainMsg1 = new ChatComponentTranslation("got.chat.killedByBounty1", killer.getCommandSenderName());
				ChatComponentTranslation slainMsg2 = new ChatComponentTranslation("got.chat.killedByBounty2", getEntityFaction().factionName());
				slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				entityplayer.addChatMessage(slainMsg1);
				entityplayer.addChatMessage(slainMsg2);
			}
			killedByBounty = true;
			updateQuest();
			ChatComponentTranslation announceMsg = new ChatComponentTranslation("got.chat.killedByBounty", entityplayer.getCommandSenderName(), killer.getCommandSenderName());
			announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				EntityPlayer otherPlayer = (EntityPlayer) obj;
				if (otherPlayer == entityplayer) {
					continue;
				}
				otherPlayer.addChatMessage(announceMsg);
			}
		}
	}

	@Override
	public void onPlayerTick(EntityPlayer entityplayer) {
		super.onPlayerTick(entityplayer);
		if (isActive() && !isKilled() && !bountyClaimedByOther && GOTFactionBounties.forFaction(getEntityFaction()).forPlayer(getTargetID()).recentlyBountyKilled()) {
			bountyClaimedByOther = true;
			updateQuest();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("TargetID")) {
			String s = nbt.getString("TargetID");
			setTargetID(UUID.fromString(s));
		}
		if (nbt.hasKey("TargetName")) {
			setTargetName(nbt.getString("TargetName"));
		}
		setKilled(nbt.getBoolean("Killed"));
		alignmentBonus = nbt.hasKey("Alignment") ? (float) nbt.getInteger("Alignment") : nbt.getFloat("AlignF");
		coinBonus = nbt.getInteger("Coins");
		bountyClaimedByOther = nbt.getBoolean("BountyClaimed");
		killedByBounty = nbt.getBoolean("KilledBy");
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}

	public void setTargetID(UUID targetID) {
		this.targetID = targetID;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Override
	public boolean shouldRandomiseCoinReward() {
		return false;
	}

	@Override
	public void start(EntityPlayer entityplayer, GOTEntityNPC npc) {
		super.start(entityplayer, npc);
		GOTLevelData.getData(getTargetID()).placeBountyFor(npc.getFaction());
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (getTargetID() != null) {
			nbt.setString("TargetID", getTargetID().toString());
		}
		if (getTargetName() != null) {
			nbt.setString("TargetName", getTargetName());
		}
		nbt.setBoolean("Killed", isKilled());
		nbt.setFloat("AlignF", alignmentBonus);
		nbt.setInteger("Coins", coinBonus);
		nbt.setBoolean("BountyClaimed", bountyClaimedByOther);
		nbt.setBoolean("KilledBy", killedByBounty);
	}

	public enum BountyHelp {
		BIOME("biome"), WAYPOINT("wp");

		private String speechName;

		BountyHelp(String s) {
			setSpeechName(s);
		}

		public String getSpeechName() {
			return speechName;
		}

		public void setSpeechName(String speechName) {
			this.speechName = speechName;
		}

		public static BountyHelp getRandomHelpType(Random random) {
			return BountyHelp.values()[random.nextInt(BountyHelp.values().length)];
		}
	}

}
