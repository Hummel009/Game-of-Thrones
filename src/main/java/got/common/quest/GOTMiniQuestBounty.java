package got.common.quest;

import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionBounties;
import got.common.faction.GOTReputationValues;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GOTMiniQuestBounty extends GOTMiniQuest {
	private UUID targetID;
	private String targetName;
	private boolean killed;
	private float reputationBonus;
	private int coinBonus;
	private boolean bountyClaimedByOther;
	private boolean killedByBounty;

	@SuppressWarnings("unused")
	public GOTMiniQuestBounty(GOTPlayerData pd) {
		super(pd);
	}

	private static GOTFaction getOathOrHighestReputationFaction(EntityPlayer entityplayer, float min) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (pd.getOathFaction() != null) {
			return pd.getOathFaction();
		}
		ArrayList<GOTFaction> highestFactions = new ArrayList<>();
		float highestReputation = min;
		for (GOTFaction f : GOTFaction.getPlayableReputationFactions()) {
			float reputation = pd.getReputation(f);
			if (reputation <= min) {
				continue;
			}
			if (reputation > highestReputation) {
				highestFactions.clear();
				highestFactions.add(f);
				highestReputation = reputation;
				continue;
			}
			if (reputation != highestReputation) {
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
	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		if (super.canPlayerAccept(entityplayer) && !targetID.equals(entityplayer.getUniqueID()) && GOTLevelData.getData(entityplayer).getReputation(entityFaction) >= 100.0f) {
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			List<GOTMiniQuest> active = pd.getActiveMiniQuests();
			for (GOTMiniQuest quest : active) {
				if (!(quest instanceof GOTMiniQuestBounty) || !((GOTMiniQuestBounty) quest).targetID.equals(targetID)) {
					continue;
				}
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	protected void complete(EntityPlayer entityplayer, GOTEntityNPC npc) {
		GOTPlayerData pd = playerData;
		pd.addCompletedBountyQuest();
		int bComplete = pd.getCompletedBountyQuests();
		boolean specialReward = bComplete > 0 && bComplete % 5 == 0;
		if (specialReward) {
			ItemStack trophy = new ItemStack(GOTItems.bountyTrophy);
			rewardItemTable.add(trophy);
		}
		super.complete(entityplayer, npc);
		pd.addAchievement(GOTAchievement.doMiniquestHunter);
		if (specialReward) {
			pd.addAchievement(GOTAchievement.doMiniquestHunter5);
		}
	}

	@Override
	public float getReputationBonus() {
		return reputationBonus;
	}

	protected void setReputationBonus(float reputationBonus) {
		this.reputationBonus = reputationBonus;
	}

	@Override
	public int getCoinBonus() {
		return coinBonus;
	}

	protected void setCoinBonus(int coinBonus) {
		this.coinBonus = coinBonus;
	}

	@Override
	public float getCompletionFactor() {
		return killed ? 1.0f : 0.0f;
	}

	private float getKilledReputationPenalty() {
		return -reputationBonus * 2.0f;
	}

	@Override
	public String getObjectiveInSpeech() {
		return targetName;
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return targetName;
	}

	@Override
	public String getQuestFailure() {
		if (killedByBounty) {
			return StatCollector.translateToLocalFormatted("got.miniquest.bounty.killedBy", targetName);
		}
		if (bountyClaimedByOther) {
			return StatCollector.translateToLocalFormatted("got.miniquest.bounty.claimed", targetName);
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
		return StatCollector.translateToLocalFormatted("got.miniquest.bounty", targetName);
	}

	@Override
	public String getQuestProgress() {
		if (killed) {
			return StatCollector.translateToLocal("got.miniquest.bounty.progress.slain");
		}
		return StatCollector.translateToLocal("got.miniquest.bounty.progress.notSlain");
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("got.miniquest.progressShort", killed ? 1 : 0, 1);
	}

	@Override
	public void handleEvent(GOTMiniQuestEvent event) {
	}

	@Override
	public boolean isFailed() {
		return super.isFailed() || bountyClaimedByOther || killedByBounty;
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && targetID != null;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		if (killed) {
			complete(entityplayer, npc);
		} else {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (!killed && !isFailed() && entity instanceof EntityPlayer && entity.getUniqueID().equals(targetID)) {
			EntityPlayer slainPlayer = (EntityPlayer) entity;
			GOTPlayerData slainPlayerData = GOTLevelData.getData(slainPlayer);
			killed = true;
			GOTFactionBounties.forFaction(entityFaction).forPlayer(slainPlayer).recordBountyKilled();
			playerData.updateMiniQuest(this);
			GOTFaction highestFaction = getOathOrHighestReputationFaction(slainPlayer, 100.0f);
			if (highestFaction != null) {
				float curReputation = slainPlayerData.getReputation(highestFaction);
				float reputationLoss = getKilledReputationPenalty();
				if (curReputation + reputationLoss < 100.0f) {
					reputationLoss = -(curReputation - 100.0f);
				}
				GOTReputationValues.ReputationBonus source = new GOTReputationValues.ReputationBonus(reputationLoss, "got.reputation.bountyKill");
				slainPlayerData.addReputation(slainPlayer, source, highestFaction, entityplayer);
				IChatComponent slainMsg1 = new ChatComponentTranslation("got.chat.bountyKilled1", entityplayer.getCommandSenderName(), entityFaction.factionName());
				IChatComponent slainMsg2 = new ChatComponentTranslation("got.chat.bountyKilled2", highestFaction.factionName());
				slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainPlayer.addChatMessage(slainMsg1);
				slainPlayer.addChatMessage(slainMsg2);
			}
			IChatComponent announceMsg = new ChatComponentTranslation("got.chat.bountyKill", entityplayer.getCommandSenderName(), slainPlayer.getCommandSenderName(), entityFaction.factionName());
			announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			for (ICommandSender otherPlayer : (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				if (otherPlayer == slainPlayer) {
					continue;
				}
				otherPlayer.addChatMessage(announceMsg);
			}
		}
	}

	@Override
	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
		if (!killed && !isFailed() && killer.getUniqueID().equals(targetID)) {
			GOTPlayerData pd;
			GOTPlayerData killerData = GOTLevelData.getData(killer);
			GOTFaction killerHighestFaction = getOathOrHighestReputationFaction(killer, 0.0f);
			if (killerHighestFaction != null) {
				float killerBonus = reputationBonus;
				GOTReputationValues.ReputationBonus source = new GOTReputationValues.ReputationBonus(killerBonus, "got.reputation.killedHunter");
				killerData.addReputation(killer, source, killerHighestFaction, entityplayer);
			}
			float curReputation = (pd = playerData).getReputation(entityFaction);
			if (curReputation > 100.0f) {
				float reputationLoss = getKilledReputationPenalty();
				if (curReputation + reputationLoss < 100.0f) {
					reputationLoss = -(curReputation - 100.0f);
				}
				GOTReputationValues.ReputationBonus source = new GOTReputationValues.ReputationBonus(reputationLoss, "got.reputation.killedByBounty");
				pd.addReputation(entityplayer, source, entityFaction, killer);
				IChatComponent slainMsg1 = new ChatComponentTranslation("got.chat.killedByBounty1", killer.getCommandSenderName());
				IChatComponent slainMsg2 = new ChatComponentTranslation("got.chat.killedByBounty2", entityFaction.factionName());
				slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				entityplayer.addChatMessage(slainMsg1);
				entityplayer.addChatMessage(slainMsg2);
			}
			killedByBounty = true;
			playerData.updateMiniQuest(this);
			killerData.addAchievement(GOTAchievement.killHuntingPlayer);
			IChatComponent announceMsg = new ChatComponentTranslation("got.chat.killedByBounty", entityplayer.getCommandSenderName(), killer.getCommandSenderName());
			announceMsg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
			for (ICommandSender otherPlayer : (List<EntityPlayer>) MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
				if (otherPlayer == entityplayer) {
					continue;
				}
				otherPlayer.addChatMessage(announceMsg);
			}
		}
	}

	@Override
	public void onPlayerTick() {
		if (isActive() && !killed && !bountyClaimedByOther && GOTFactionBounties.forFaction(entityFaction).forPlayer(targetID).recentlyBountyKilled()) {
			bountyClaimedByOther = true;
			playerData.updateMiniQuest(this);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("TargetID")) {
			String s = nbt.getString("TargetID");
			targetID = UUID.fromString(s);
		}
		if (nbt.hasKey("TargetName")) {
			targetName = nbt.getString("TargetName");
		}
		killed = nbt.getBoolean("Killed");
		reputationBonus = nbt.hasKey("Reputation") ? nbt.getInteger("Reputation") : nbt.getFloat("RepF");
		coinBonus = nbt.getInteger("Coins");
		bountyClaimedByOther = nbt.getBoolean("BountyClaimed");
		killedByBounty = nbt.getBoolean("KilledBy");
	}

	@Override
	public boolean shouldRandomiseCoinReward() {
		return false;
	}

	@Override
	public void start(EntityPlayer entityplayer, GOTEntityNPC npc) {
		super.start(entityplayer, npc);
		GOTLevelData.getData(targetID).placeBountyFor(npc.getFaction());
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (targetID != null) {
			nbt.setString("TargetID", targetID.toString());
		}
		if (targetName != null) {
			nbt.setString("TargetName", targetName);
		}
		nbt.setBoolean("Killed", killed);
		nbt.setFloat("RepF", reputationBonus);
		nbt.setInteger("Coins", coinBonus);
		nbt.setBoolean("BountyClaimed", bountyClaimedByOther);
		nbt.setBoolean("KilledBy", killedByBounty);
	}

	public UUID getTargetID() {
		return targetID;
	}

	protected void setTargetID(UUID targetID) {
		this.targetID = targetID;
	}

	public String getTargetName() {
		return targetName;
	}

	protected void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public boolean isKilled() {
		return killed;
	}

	public enum BountyHelp {
		BIOME("biome"), WAYPOINT("wp");

		private final String speechName;

		BountyHelp(String s) {
			speechName = s;
		}

		public static BountyHelp getRandomHelpType(Random random) {
			return values()[random.nextInt(values().length)];
		}

		public String getSpeechName() {
			return speechName;
		}
	}

	public static class QFBounty<Q extends GOTMiniQuestBounty> extends GOTMiniQuest.QuestFactoryBase<Q> {
		public QFBounty() {
			super("bounty");
		}

		@Override
		public Q createQuest(GOTEntityNPC npc, Random rand) {
			if (!GOTConfig.allowBountyQuests) {
				return null;
			}
			Q quest = super.createQuest(npc, rand);
			GOTFaction faction = npc.getFaction();
			GOTFactionBounties bounties = GOTFactionBounties.forFaction(faction);
			List<GOTFactionBounties.PlayerData> players = bounties.findBountyTargets(25);
			if (players.isEmpty()) {
				return null;
			}
			GOTFactionBounties.PlayerData targetData = players.get(rand.nextInt(players.size()));
			int reputation = (int) (float) targetData.getNumKills();
			reputation = MathHelper.clamp_int(reputation, 1, 50);
			int coins = (int) (targetData.getNumKills() * 10.0f * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
			coins = MathHelper.clamp_int(coins, 1, 1000);
			quest.setTargetID(targetData.getPlayerID());
			String username = targetData.findUsername();
			if (StringUtils.isBlank(username)) {
				username = quest.getTargetID().toString();
			}
			quest.setTargetName(username);
			quest.setReputationBonus(reputation);
			quest.setCoinBonus(coins);
			return quest;
		}

		@Override
		public Class<Q> getQuestClass() {
			return (Class<Q>) GOTMiniQuestBounty.class;
		}
	}
}