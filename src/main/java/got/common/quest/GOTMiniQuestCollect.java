package got.common.quest;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.item.other.GOTItemMug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import java.util.Random;

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
			quest.collectItem = collectItem.copy();
			quest.collectTarget = MathHelper.getRandomIntegerInRange(rand, minTarget, maxTarget);
			return (Q) quest;
		}

		@Override
		public Class getQuestClass() {
			return GOTMiniQuestCollect.class;
		}

		public QFCollect<Q> setCollectItem(ItemStack itemstack, int min, int max) {
			collectItem = itemstack;
			if (collectItem.isItemStackDamageable()) {
				collectItem.setItemDamage(32767);
			}
			minTarget = min;
			maxTarget = max;
			return this;
		}
	}

}
