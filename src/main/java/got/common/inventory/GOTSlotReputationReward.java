package got.common.inventory;

import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.iface.GOTHireableBase;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemCoin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GOTSlotReputationReward extends GOTSlotProtected {
	public static final int WARHORT_PRICE = 500;
	public static final int WARHORN_REPUTATION = 500;

	private final GOTContainerUnitTrade theContainer;
	private final GOTHireableBase theTrader;
	private final GOTEntityNPC theLivingTrader;
	private final ItemStack reputationReward;

	public GOTSlotReputationReward(GOTContainerUnitTrade container, IInventory inv, int i, int j, int k, GOTHireableBase entity, ItemStack item) {
		super(inv, i, j, k);
		theContainer = container;
		theTrader = entity;
		theLivingTrader = (GOTEntityNPC) theTrader;
		reputationReward = item.copy();
	}

	@Override
	public boolean canTakeStack(EntityPlayer entityplayer) {
		if (GOTLevelData.getData(entityplayer).getReputation(theTrader.getFaction()) < WARHORN_REPUTATION) {
			return false;
		}
		int coins = GOTItemCoin.getInventoryValue(entityplayer, false);
		return coins >= WARHORT_PRICE && super.canTakeStack(entityplayer);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack itemstack) {
		GOTFaction faction = theLivingTrader.getFaction();
		if (!entityplayer.worldObj.isRemote) {
			GOTItemCoin.takeCoins(WARHORT_PRICE, entityplayer);
			GOTLevelData.getData(entityplayer).getFactionData(faction).takeConquestHorn();
			theLivingTrader.playTradeSound();
		}
		super.onPickupFromSlot(entityplayer, itemstack);
		if (!entityplayer.worldObj.isRemote) {
			ItemStack reward = reputationReward.copy();
			putStack(reward);
			((EntityPlayerMP) entityplayer).sendContainerToPlayer(theContainer);
		}
	}
}