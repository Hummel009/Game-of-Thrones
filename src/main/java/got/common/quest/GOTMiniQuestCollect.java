package got.common.quest;

import java.util.Random;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.item.other.GOTItemMug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public class GOTMiniQuestCollect extends GOTMiniQuestCollectBase {
	public ItemStack collectItem;

	public GOTMiniQuestCollect(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public String getObjectiveInSpeech() {
		return collectTarget + " " + collectItem.getDisplayName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return collectTarget - amountGiven + " " + collectItem.getDisplayName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return collectItem;
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("got.miniquest.collect", collectTarget, collectItem.getDisplayName());
	}

	@Override
	public boolean isQuestItem(ItemStack itemstack) {
		if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
			return false;
		}
		if (GOTItemMug.isItemFullDrink(collectItem)) {
			ItemStack collectDrink = GOTItemMug.getEquivalentDrink(collectItem);
			ItemStack offerDrink = GOTItemMug.getEquivalentDrink(itemstack);
			return collectDrink.getItem() == offerDrink.getItem();
		}
		return itemstack.getItem() == collectItem.getItem() && (collectItem.getItemDamage() == 32767 || itemstack.getItemDamage() == collectItem.getItemDamage());
	}

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && collectItem != null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("Item")) {
			NBTTagCompound itemData = nbt.getCompoundTag("Item");
			collectItem = ItemStack.loadItemStackFromNBT(itemData);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (collectItem != null) {
			NBTTagCompound itemData = new NBTTagCompound();
			collectItem.writeToNBT(itemData);
			nbt.setTag("Item", itemData);
		}
	}

	public static class QFCollect<Q extends GOTMiniQuestCollect> extends GOTMiniQuest.QuestFactoryBase<Q> {
		public ItemStack collectItem;
		public int minTarget;
		public int maxTarget;

		public QFCollect() {
			super("collect");
		}

		public QFCollect(String name) {
			super(name);
		}

		@Override
		public Q createQuest(GOTEntityNPC npc, Random rand) {
			GOTMiniQuestCollect quest = super.createQuest(npc, rand);
			quest.collectItem = this.collectItem.copy();
			quest.collectTarget = MathHelper.getRandomIntegerInRange(rand, this.minTarget, this.maxTarget);
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
