package got.common.inventory;

import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTHireableBase;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GOTSlotAlignmentReward extends GOTSlotProtected {
	public static final int REWARD_COST = 2000;

	private final GOTContainerUnitTrade theContainer;
	private final GOTHireableBase theTrader;
	private final GOTEntityNPC theLivingTrader;
	private final ItemStack alignmentReward;

	public GOTSlotAlignmentReward(GOTContainerUnitTrade container, IInventory inv, int i, int j, int k, GOTHireableBase entity, ItemStack item) {
		super(inv, i, j, k);
		theContainer = container;
		theTrader = entity;
		theLivingTrader = (GOTEntityNPC) theTrader;
		alignmentReward = item.copy();
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		if (GOTLevelData.getData(entityplayer).getAlignment(theTrader.getFaction()) < 1500.0f) {
			return false;
		}
		int coins = GOTItemCoin.getInventoryValue(entityplayer, false);
		return coins >= REWARD_COST && super.canTakeStack(entityplayer);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		GOTFaction faction = theLivingTrader.getFaction();
		if (!entityplayer.worldObj.isRemote) {
			GOTItemCoin.takeCoins(REWARD_COST, entityplayer);
			GOTLevelData.getData(entityplayer).getFactionData(faction).takeConquestHorn();
			theLivingTrader.playTradeSound();
		}
		super.onPickupFromSlot(entityplayer, itemstack);
		if (!entityplayer.worldObj.isRemote) {
			ItemStack reward = alignmentReward.copy();
			putStack(reward);
			((EntityPlayerMP) entityplayer).sendContainerToPlayer(theContainer);
		}
	}
}