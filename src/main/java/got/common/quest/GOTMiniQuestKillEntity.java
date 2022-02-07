package got.common.quest;

import java.util.Random;

import got.common.GOTPlayerData;
import got.common.entity.other.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class GOTMiniQuestKillEntity extends GOTMiniQuestKill {
	private Class entityType;

	public GOTMiniQuestKillEntity(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public String getKillTargetName() {
		String entityName = GOTEntityRegistry.getStringFromClass(entityType);
		return StatCollector.translateToLocal("entity." + entityName + ".name");
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && entityType != null && EntityLivingBase.class.isAssignableFrom(entityType);
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (getKillCount() < getKillTarget() && entityType.isAssignableFrom(entity.getClass())) {
			setKillCount(getKillCount() + 1);
			updateQuest();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		entityType = GOTEntityRegistry.getClassFromString(nbt.getString("KillClass"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("KillClass", GOTEntityRegistry.getStringFromClass(entityType));
	}

	public static class QFKillEntity extends GOTMiniQuestKill.QFKill<GOTMiniQuestKillEntity> {
		private Class entityType;

		public QFKillEntity(String name) {
			super(name);
		}

		@Override
		public GOTMiniQuestKillEntity createQuest(GOTEntityNPC npc, Random rand) {
			GOTMiniQuestKillEntity quest = super.createQuest(npc, rand);
			quest.entityType = entityType;
			return quest;
		}

		@Override
		public Class getQuestClass() {
			return GOTMiniQuestKillEntity.class;
		}

		public QFKillEntity setKillEntity(Class entityClass, int min, int max) {
			entityType = entityClass;
			setKillTarget(min, max);
			return this;
		}
	}

}
