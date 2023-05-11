package got.common.quest;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.UUID;

public class GOTMiniQuestRetrieve extends GOTMiniQuestCollect {
	public Class<? extends Entity> killEntityType;
	public boolean hasDropped;

	public GOTMiniQuestRetrieve(GOTPlayerData pd) {
		super(pd);
	}

	public static UUID getRetrieveQuestID(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("GOTRetrieveID")) {
			String id = itemstack.getTagCompound().getString("GOTRetrieveID");
			return UUID.fromString(id);
		}
		return null;
	}

	public static void setRetrieveQuest(ItemStack itemstack, GOTMiniQuest quest) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("GOTRetrieveID", quest.questUUID.toString());
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		if (collectTarget == 1) {
			return collectItem.getDisplayName();
		}
		return collectTarget + " " + collectItem.getDisplayName();
	}

	@Override
	public String getQuestObjective() {
		if (collectTarget == 1) {
			return StatCollector.translateToLocalFormatted("got.miniquest.retrieve1", collectItem.getDisplayName());
		}
		return StatCollector.translateToLocalFormatted("got.miniquest.retrieveMany", collectTarget, collectItem.getDisplayName());
	}

	@Override
	public boolean isQuestItem(ItemStack itemstack) {
		if (super.isQuestItem(itemstack)) {
			UUID retrieveQuestID = getRetrieveQuestID(itemstack);
			return retrieveQuestID.equals(questUUID);
		}
		return false;
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && killEntityType != null && EntityLivingBase.class.isAssignableFrom(killEntityType);
	}

	@Override
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
		if (!hasDropped && killEntityType.isAssignableFrom(entity.getClass())) {
			ItemStack itemstack = collectItem.copy();
			setRetrieveQuest(itemstack, this);
			hasDropped = true;
			updateQuest();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		killEntityType = GOTEntityRegistry.getClassFromString(nbt.getString("KillClass"));
		hasDropped = nbt.getBoolean("HasDropped");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("KillClass", GOTEntityRegistry.getStringFromClass(killEntityType));
		nbt.setBoolean("HasDropped", hasDropped);
	}

}
