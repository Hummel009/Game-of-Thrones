package got.common.quest;

import java.util.*;

import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public abstract class GOTMiniQuestCollectBase extends GOTMiniQuest {
	private int collectTarget;
	private int amountGiven;

	public GOTMiniQuestCollectBase(GOTPlayerData pd) {
		super(pd);
	}

	@Override
	public float getAlignmentBonus() {
		float f = getCollectTarget();
		return Math.max(f *= getRewardFactor(), 1.0f);
	}

	public int getAmountGiven() {
		return amountGiven;
	}

	@Override
	public int getCoinBonus() {
		return Math.round(getAlignmentBonus() * 2.0f);
	}

	public int getCollectTarget() {
		return collectTarget;
	}

	@Override
	public float getCompletionFactor() {
		return (float) getAmountGiven() / (float) getCollectTarget();
	}

	@Override
	public String getQuestProgress() {
		return StatCollector.translateToLocalFormatted("got.miniquest.collect.progress", getAmountGiven(), getCollectTarget());
	}

	@Override
	public String getQuestProgressShorthand() {
		return StatCollector.translateToLocalFormatted("got.miniquest.progressShort", getAmountGiven(), getCollectTarget());
	}

	public abstract boolean isQuestItem(ItemStack var1);

	@Override
	public boolean isValidQuest() {
		return super.isValidQuest() && getCollectTarget() > 0;
	}

	@Override
	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
		int prevAmountGiven = getAmountGiven();
		ArrayList<Integer> slotNumbers = new ArrayList<>();
		slotNumbers.add(entityplayer.inventory.currentItem);
		for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
			if (slotNumbers.contains(slot)) {
				continue;
			}
			slotNumbers.add(slot);
		}
		Iterator slot = slotNumbers.iterator();
		while (slot.hasNext()) {
			int slot2 = (Integer) slot.next();
			ItemStack itemstack = entityplayer.inventory.mainInventory[slot2];
			if (itemstack != null && isQuestItem(itemstack)) {
				int amountRemaining = getCollectTarget() - getAmountGiven();
				if (itemstack.stackSize >= amountRemaining) {
					itemstack.stackSize -= amountRemaining;
					if (itemstack.stackSize <= 0) {
						itemstack = null;
					}
					entityplayer.inventory.setInventorySlotContents(slot2, itemstack);
					setAmountGiven(getAmountGiven() + amountRemaining);
				} else {
					setAmountGiven(getAmountGiven() + itemstack.stackSize);
					entityplayer.inventory.setInventorySlotContents(slot2, null);
				}
			}
			if (getAmountGiven() < getCollectTarget()) {
				continue;
			}
			complete(entityplayer, npc);
			break;
		}
		if (getAmountGiven() > prevAmountGiven && !isCompleted()) {
			updateQuest();
		}
		if (!isCompleted()) {
			sendProgressSpeechbank(entityplayer, npc);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setCollectTarget(nbt.getInteger("Target"));
		setAmountGiven(nbt.getInteger("Given"));
	}

	public void setAmountGiven(int amountGiven) {
		this.amountGiven = amountGiven;
	}

	public void setCollectTarget(int collectTarget) {
		this.collectTarget = collectTarget;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Target", getCollectTarget());
		nbt.setInteger("Given", getAmountGiven());
	}
}
