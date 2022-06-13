package got.common.quest;

import java.util.Random;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public abstract class GOTMQKill extends GOTMQ {
	public int killTarget;
	public int killCount;

	public GOTMQKill(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public float getAlignmentBonus() {
		return killTarget * rewardFactor;
	}

	@Override
	public int getCoinBonus() {
		return Math.round(killTarget * 2.0f * rewardFactor);
	}

	@Override
	public float getCompletionFactor() {
		return (float) killCount / (float) killTarget;
	}

	public abstract String getKillTargetName();

	@Override
	public String getObjectiveInSpeech() {
		return killTarget + " " + getKillTargetName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return killTarget - killCount + " " + getKillTargetName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return new ItemStack(Items.iron_sword);
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("got.miniquest.kill", killTarget, getKillTargetName());
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("got.miniquest.kill.progress", killCount, killTarget);
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("got.miniquest.progressShort", killCount, killTarget);
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && killTarget > 0;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		if (killCount >= killTarget) {
			complete(entityplayer, npc);
		} else {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		killTarget = nbt.getInteger("Target");
		killCount = nbt.getInteger("Count");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Target", killTarget);
		nbt.setInteger("Count", killCount);
	}

	public static abstract class QFKill<Q extends GOTMQKill> extends GOTMQ.QuestFactoryBase<Q> {
		public int minTarget;
		public int maxTarget;

		public QFKill(String name) {
			super(name);
		}

		@Override
		public Q createQuest(GOTEntityNPC npc, Random rand) {
			GOTMQKill quest = super.createQuest(npc, rand);
			quest.killTarget = MathHelper.getRandomIntegerInRange(rand, this.minTarget, this.maxTarget);
			return (Q) quest;
		}

		public QFKill setKillTarget(int min, int max) {
			this.minTarget = min;
			this.maxTarget = max;
			return this;
		}
	}

}
