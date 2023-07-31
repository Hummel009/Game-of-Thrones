package got.common.quest;

import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionBounties;
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
	public UUID targetID;
	public String targetName;
	public boolean killed;
	public float alignmentBonus;
	public int coinBonus;
	public boolean bountyClaimedByOther;
	public boolean killedByBounty;

	public GOTMiniQuestBounty(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		if (super.canPlayerAccept(entityplayer) && !targetID.equals(entityplayer.getUniqueID()) && GOTLevelData.getData(entityplayer).getAlignment(entityFaction) >= 100.0f) {
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
	public void complete(EntityPlayer entityplayer, GOTEntityNPC npc) {
		boolean specialReward;
		GOTPlayerData pd = getPlayerData();
		pd.addCompletedBountyQuest();
		int bComplete = pd.getCompletedBountyQuests();
		specialReward = bComplete > 0 && bComplete % 5 == 0;
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
	public float getAlignmentBonus() {
		return alignmentBonus;
	}

	@Override
	public int getCoinBonus() {
		return coinBonus;
	}

	@Override
	public float getCompletionFactor() {
		return killed ? 1.0f : 0.0f;
	}

	public float getKilledAlignmentPenalty() {
		return -alignmentBonus * 2.0f;
	}

	@Override
	public String getObjectiveInSpeech() {
		return targetName;
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
			float curAlignment;
			GOTPlayerData pd;
			GOTPlayerData killerData = GOTLevelData.getData(killer);
			GOTFaction killerHighestFaction = getPledgeOrHighestAlignmentFaction(killer, 0.0f);
			if (killerHighestFaction != null) {
				float killerBonus = alignmentBonus;
				GOTAlignmentValues.AlignmentBonus source = new GOTAlignmentValues.AlignmentBonus(killerBonus, "got.alignment.killedHunter");
				killerData.addAlignment(killer, source, killerHighestFaction, entityplayer);
			}
			curAlignment = (pd = getPlayerData()).getAlignment(entityFaction);
			if (curAlignment > 100.0f) {
				float alignmentLoss = getKilledAlignmentPenalty();
				if (curAlignment + alignmentLoss < 100.0f) {
					alignmentLoss = -(curAlignment - 100.0f);
				}
				GOTAlignmentValues.AlignmentBonus source = new GOTAlignmentValues.AlignmentBonus(alignmentLoss, "got.alignment.killedByBounty");
				pd.addAlignment(entityplayer, source, entityFaction, killer);
				IChatComponent slainMsg1 = new ChatComponentTranslation("got.chat.killedByBounty1", killer.getCommandSenderName());
				IChatComponent slainMsg2 = new ChatComponentTranslation("got.chat.killedByBounty2", entityFaction.factionName());
				slainMsg1.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				slainMsg2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				entityplayer.addChatMessage(slainMsg1);
				entityplayer.addChatMessage(slainMsg2);
			}
			killedByBounty = true;
			updateQuest();
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
	public void onPlayerTick(EntityPlayer entityplayer) {
		super.onPlayerTick(entityplayer);
		if (isActive() && !killed && !bountyClaimedByOther && GOTFactionBounties.forFaction(entityFaction).forPlayer(targetID).recentlyBountyKilled()) {
			bountyClaimedByOther = true;
			updateQuest();
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
		alignmentBonus = nbt.hasKey("Alignment") ? nbt.getInteger("Alignment") : nbt.getFloat("AlignF");
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
		nbt.setFloat("AlignF", alignmentBonus);
		nbt.setInteger("Coins", coinBonus);
		nbt.setBoolean("BountyClaimed", bountyClaimedByOther);
		nbt.setBoolean("KilledBy", killedByBounty);
	}

	public enum BountyHelp {
		BIOME("biome"), WAYPOINT("wp");

		public String speechName;

		BountyHelp(String s) {
			speechName = s;
		}

		public static BountyHelp getRandomHelpType(Random random) {
			return values()[random.nextInt(values().length)];
		}
	}

	public static class QFBounty<Q extends GOTMiniQuestBounty> extends GOTMiniQuest.QuestFactoryBase<Q> {
		public QFBounty() {
			super("bounty");
		}

		public QFBounty(String name) {
			super(name);
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
			int alignment = (int) (float) targetData.getNumKills();
			alignment = MathHelper.clamp_int(alignment, 1, 50);
			int coins = (int) (targetData.getNumKills() * 10.0f * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
			coins = MathHelper.clamp_int(coins, 1, 1000);
			quest.targetID = targetData.playerID;
			String username = targetData.findUsername();
			if (StringUtils.isBlank(username)) {
				username = quest.targetID.toString();
			}
			quest.targetName = username;
			quest.alignmentBonus = alignment;
			quest.coinBonus = coins;
			return quest;
		}

		@Override
		public Class<Q> getQuestClass() {
			return (Class<Q>) GOTMiniQuestBounty.class;
		}
	}

}
