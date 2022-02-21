package got.common.quest;

import got.GOT;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GOTMiniQuestKillFaction extends GOTMiniQuestKill {
	public GOTFaction killFaction;

	public GOTMiniQuestKillFaction(GOTPlayerData pd) {
		super(pd);
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
			updateQuest();
		}
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
}
