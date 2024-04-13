package got.common.quest;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collection;

public abstract class GOTMiniQuestCollectBase extends GOTMiniQuest {
	protected int amountGiven;

	private int collectTarget;

	protected GOTMiniQuestCollectBase(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	protected float getAlignmentBonus() {
		float f = collectTarget;
		return Math.max(f * rewardFactor, 1.0f);
	}

	@Override
	public int getCoinBonus() {
		return Math.round(getAlignmentBonus() * 2.0f);
	}

	@Override
	public float getCompletionFactor() {
		return (float) amountGiven / collectTarget;
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("got.miniquest.collect.progress", amountGiven, collectTarget);
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("got.miniquest.progressShort", amountGiven, collectTarget);
	}

	protected abstract boolean isQuestItem(ItemStack var1);

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && collectTarget > 0;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		int prevAmountGiven = amountGiven;
		Collection<Integer> slotNumbers = new ArrayList<>();
		slotNumbers.add(entityplayer.inventory.currentItem);
		for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
			if (slotNumbers.contains(slot)) {
				continue;
			}
			slotNumbers.add(slot);
		}
		for (int slot2 : slotNumbers) {
			ItemStack itemstack = entityplayer.inventory.mainInventory[slot2];
			if (itemstack != null && isQuestItem(itemstack)) {
				int amountRemaining = collectTarget - amountGiven;
				if (itemstack.stackSize >= amountRemaining) {
					itemstack.stackSize -= amountRemaining;
					if (itemstack.stackSize <= 0) {
						itemstack = null;
					}
					entityplayer.inventory.setInventorySlotContents(slot2, itemstack);
					amountGiven += amountRemaining;
				} else {
					amountGiven += itemstack.stackSize;
					entityplayer.inventory.setInventorySlotContents(slot2, null);
				}
			}
			if (amountGiven < collectTarget) {
				continue;
			}
			complete(entityplayer, npc);
			break;
		}
		if (amountGiven > prevAmountGiven && !completed) {
			playerData.updateMiniQuest(this);
		}
		if (!completed) {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		collectTarget = nbt.getInteger("Target");
		amountGiven = nbt.getInteger("Given");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Target", collectTarget);
		nbt.setInteger("Given", amountGiven);
	}

	protected int getCollectTarget() {
		return collectTarget;
	}

	protected void setCollectTarget(int collectTarget) {
		this.collectTarget = collectTarget;
	}
}