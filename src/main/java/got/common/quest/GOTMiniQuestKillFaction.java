package got.common.quest;

import got.GOT;
import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class GOTMiniQuestKillFaction extends GOTMiniQuestKill {
	private GOTFaction killFaction;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTMiniQuestKillFaction(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public void handleEvent(GOTMiniQuestEvent event) {
	}

	@Override
	public String getKillTargetName() {
		return killFaction.factionEntityName();
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && killFaction != null;
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (killCount < killTarget && GOT.getNPCFaction(entity) == killFaction) {
			++killCount;
			playerData.updateMiniQuest(this);
		}
	}

	@Override
	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
	}

	@Override
	public void onPlayerTick() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		killFaction = GOTFaction.forName(nbt.getString("KillFaction"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("KillFaction", killFaction.codeName());
	}

	public static class QFKillFaction extends GOTMiniQuestKill.QFKill<GOTMiniQuestKillFaction> {
		protected GOTFaction killFaction;

		@SuppressWarnings({"WeakerAccess", "unused"})
		public QFKillFaction() {
			super("kill");
		}

		@Override
		public GOTMiniQuestKillFaction createQuest(GOTEntityNPC npc, Random rand) {
			GOTMiniQuestKillFaction quest = super.createQuest(npc, rand);
			quest.killFaction = killFaction;
			return quest;
		}

		@Override
		public Class<GOTMiniQuestKillFaction> getQuestClass() {
			return GOTMiniQuestKillFaction.class;
		}

		public QFKillFaction setKillFaction(GOTFaction faction, int min, int max) {
			killFaction = faction;
			setKillTarget(min, max);
			return this;
		}
	}
}