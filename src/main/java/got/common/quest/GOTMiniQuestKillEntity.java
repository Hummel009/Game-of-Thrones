package got.common.quest;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.Random;

public class GOTMiniQuestKillEntity extends GOTMiniQuestKill {
	public Class<? extends Entity> entityType;

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
		if (killCount < killTarget && entityType.isAssignableFrom(entity.getClass())) {
			++killCount;
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

	public static class QFKillEntity extends QFKill<GOTMiniQuestKillEntity> {
		public Class<? extends Entity> entityType;

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
		public Class<GOTMiniQuestKillEntity> getQuestClass() {
			return GOTMiniQuestKillEntity.class;
		}

		public QFKillEntity setKillEntity(Class<? extends Entity> entityClass, int min, int max) {
			entityType = entityClass;
			setKillTarget(min, max);
			return this;
		}
	}

}
