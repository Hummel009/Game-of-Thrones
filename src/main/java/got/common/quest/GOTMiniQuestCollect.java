package got.common.quest;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.item.other.GOTItemMug;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import java.util.Random;

public class GOTMiniQuestCollect extends GOTMiniQuestCollectBase {
	private ItemStack collectItem;

	public GOTMiniQuestCollect(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public String getObjectiveInSpeech() {
		return getCollectTarget() + " " + collectItem.getDisplayName();
	}

	@Override
	public String getProgressedObjectiveInSpeech() {
		return getCollectTarget() - amountGiven + " " + collectItem.getDisplayName();
	}

	@Override
	public ItemStack getQuestIcon() {
		return collectItem;
	}

	@Override
	public String getQuestObjective() {
		return StatCollector.translateToLocalFormatted("got.miniquest.collect", getCollectTarget(), collectItem.getDisplayName());
	}

	@Override
	public void handleEvent(GOTMiniQuestEvent event) {
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
	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
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

	@SuppressWarnings("unused")
	public ItemStack getCollectItem() {
		return collectItem;
	}

	protected void setCollectItem(ItemStack collectItem) {
		this.collectItem = collectItem;
	}

	public static class QFCollect<Q extends GOTMiniQuestCollect> extends GOTMiniQuest.QuestFactoryBase<Q> {
		protected ItemStack collectItem;
		protected int minTarget;
		protected int maxTarget;

		public QFCollect() {
			super("collect");
		}

		public QFCollect(String name) {
			super(name);
		}

		@Override
		public Q createQuest(GOTEntityNPC npc, Random rand) {
			Q quest = super.createQuest(npc, rand);
			quest.setCollectItem(collectItem.copy());
			quest.setCollectTarget(MathHelper.getRandomIntegerInRange(rand, minTarget, maxTarget));
			return quest;
		}

		@Override
		public Class<Q> getQuestClass() {
			return (Class<Q>) GOTMiniQuestCollect.class;
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