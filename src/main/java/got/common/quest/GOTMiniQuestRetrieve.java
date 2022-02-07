package got.common.quest;

import java.util.UUID;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class GOTMiniQuestRetrieve extends GOTMiniQuestCollect {
	private Class killEntityType;
	private boolean hasDropped = false;

	public GOTMiniQuestRetrieve(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		if (getCollectTarget() == 1) {
			return getCollectItem().getDisplayName();
		}
		return getCollectTarget() + " " + getCollectItem().getDisplayName();
	}

	@Override
	public String getQuestObjective() {
		if (getCollectTarget() == 1) {
			return StatCollector.translateToLocalFormatted("got.miniquest.retrieve1", getCollectItem().getDisplayName());
		}
		return StatCollector.translateToLocalFormatted("got.miniquest.retrieveMany", getCollectTarget(), getCollectItem().getDisplayName());
	}

	@Override
	public boolean isQuestItem(ItemStack itemstack) {
		if (super.isQuestItem(itemstack)) {
			UUID retrieveQuestID = GOTMiniQuestRetrieve.getRetrieveQuestID(itemstack);
			return retrieveQuestID.equals(getQuestUUID());
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
			ItemStack itemstack = getCollectItem().copy();
			GOTMiniQuestRetrieve.setRetrieveQuest(itemstack, this);
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

	private static UUID getRetrieveQuestID(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("GOTRetrieveID")) {
			String id = itemstack.getTagCompound().getString("GOTRetrieveID");
			return UUID.fromString(id);
		}
		return null;
	}

	private static void setRetrieveQuest(ItemStack itemstack, GOTMiniQuest quest) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("GOTRetrieveID", quest.getQuestUUID().toString());
	}

}
