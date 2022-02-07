package got.common.quest;

import java.util.Random;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public abstract class GOTMiniQuestKill extends GOTMiniQuest {
	private int killTarget;
	private int killCount;

	public GOTMiniQuestKill(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public float getAlignmentBonus() {
		return getKillTarget() * getRewardFactor();
	}

	@Override
	public int getCoinBonus() {
		return Math.round(getKillTarget() * 2.0f * getRewardFactor());
	}

	@Override
	public float getCompletionFactor() {
		return (float) getKillCount() / (float) getKillTarget();
	}

	public int getKillCount() {
		return killCount;
	}

	public int getKillTarget() {
		return killTarget;
	}

	public abstract String getKillTargetName();

	@Override
	public String getObjectiveInSpeech() {
		return getKillTarget() + " " + getKillTargetName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return getKillTarget() - getKillCount() + " " + getKillTargetName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return new ItemStack(Items.iron_sword);
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("got.miniquest.kill", getKillTarget(), getKillTargetName());
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("got.miniquest.kill.progress", getKillCount(), getKillTarget());
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("got.miniquest.progressShort", getKillCount(), getKillTarget());
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && getKillTarget() > 0;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		if (getKillCount() >= getKillTarget()) {
			complete(entityplayer, npc);
		} else {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setKillTarget(nbt.getInteger("Target"));
		setKillCount(nbt.getInteger("Count"));
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public void setKillTarget(int killTarget) {
		this.killTarget = killTarget;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Target", getKillTarget());
		nbt.setInteger("Count", getKillCount());
	}

	public static abstract class QFKill<Q extends GOTMiniQuestKill> extends GOTMiniQuest.QuestFactoryBase<Q> {
		private int minTarget;
		private int maxTarget;

		public QFKill(String name) {
			super(name);
		}

		@Override
		public Q createQuest(GOTEntityNPC npc, Random rand) {
			GOTMiniQuestKill quest = super.createQuest(npc, rand);
			quest.setKillTarget(MathHelper.getRandomIntegerInRange(rand, this.minTarget, this.maxTarget));
			return (Q) quest;
		}

		public QFKill setKillTarget(int min, int max) {
			this.minTarget = min;
			this.maxTarget = max;
			return this;
		}
	}

}
