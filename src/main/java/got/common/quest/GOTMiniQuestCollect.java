package got.common.quest;

import java.util.Random;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.item.other.GOTItemMug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class GOTMiniQuestCollect extends GOTMiniQuestCollectBase {
	private ItemStack collectItem;

	public GOTMiniQuestCollect(GOTPlayerData pd) {
		super(pd);
	}

	public ItemStack getCollectItem() {
		return collectItem;
	}

	@Override
	public String getObjectiveInSpeech() {
		return getCollectTarget() + " " + getCollectItem().getDisplayName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return getCollectTarget() - getAmountGiven() + " " + getCollectItem().getDisplayName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return getCollectItem();
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("got.miniquest.collect", getCollectTarget(), getCollectItem().getDisplayName());
	}

	@Override
	public boolean isQuestItem(ItemStack itemstack) {
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			return false;
		}
		if (GOTItemMug.isItemFullDrink(getCollectItem())) {
			ItemStack collectDrink = GOTItemMug.getEquivalentDrink(getCollectItem());
			ItemStack offerDrink = GOTItemMug.getEquivalentDrink(itemstack);
			return collectDrink.getItem() == offerDrink.getItem();
		}
		return itemstack.getItem() == getCollectItem().getItem() && (getCollectItem().getItemDamage() == 32767 || itemstack.getItemDamage() == getCollectItem().getItemDamage());
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && getCollectItem() != null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("Item")) {
			NBTTagCompound itemData = nbt.getCompoundTag("Item");
			setCollectItem(ItemStack.loadItemStackFromNBT(itemData));
		}
	}

	public void setCollectItem(ItemStack collectItem) {
		this.collectItem = collectItem;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (getCollectItem() != null) {
			NBTTagCompound itemData = new NBTTagCompound();
			getCollectItem().writeToNBT(itemData);
			nbt.setTag("Item", itemData);
		}
	}

	public static class QFCollect<Q extends GOTMiniQuestCollect> extends GOTMiniQuest.QuestFactoryBase<Q> {
		private ItemStack collectItem;
		private int minTarget;
		private int maxTarget;

		public QFCollect(String name) {
			super(name);
		}

		@Override
		public Q createQuest(GOTEntityNPC npc, Random rand) {
			GOTMiniQuestCollect quest = super.createQuest(npc, rand);
			quest.setCollectItem(this.collectItem.copy());
			quest.setCollectTarget(MathHelper.getRandomIntegerInRange(rand, this.minTarget, this.maxTarget));
			return (Q) quest;
		}

		@Override
		public Class getQuestClass() {
			return GOTMiniQuestCollect.class;
		}

		public QFCollect setCollectItem(ItemStack itemstack, int min, int max) {
			this.collectItem = itemstack;
			if (this.collectItem.isItemStackDamageable()) {
				this.collectItem.setItemDamage(32767);
			}
			this.minTarget = min;
			this.maxTarget = max;
			return this;
		}
	}

}
