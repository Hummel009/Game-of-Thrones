package got.common.entity.ai;

import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.IBandit;
import got.common.item.other.GOTItemCoin;
import got.common.item.other.GOTItemGem;
import got.common.item.other.GOTItemPouch;
import got.common.item.other.GOTItemRing;
import got.common.recipe.GOTRecipe;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GOTEntityAIBanditSteal extends EntityAIBase {
	public IBandit theBandit;
	public GOTEntityNPC theBanditAsNPC;
	public EntityPlayer targetPlayer;
	public EntityPlayer prevTargetPlayer;
	public double speed;
	public int chaseTimer;
	public int rePathDelay;

	public GOTEntityAIBanditSteal(IBandit bandit, double d) {
		theBandit = bandit;
		theBanditAsNPC = theBandit.getBanditAsNPC();
		speed = d;
		setMutexBits(3);
	}

	@Override
	public boolean continueExecuting() {
		if (targetPlayer == null || !targetPlayer.isEntityAlive() || targetPlayer.capabilities.isCreativeMode || !IBandit.Helper.canStealFromPlayerInv(theBandit, targetPlayer)) {
			return false;
		}
		return chaseTimer > 0 && theBanditAsNPC.getDistanceSqToEntity(targetPlayer) < 256.0;
	}

	public int getRandomTheftAmount(ItemStack itemstack) {
		return MathHelper.getRandomIntegerInRange(theBanditAsNPC.getRNG(), 1, 8);
	}

	@Override
	public void resetTask() {
		chaseTimer = 0;
		rePathDelay = 0;
		if (targetPlayer != null) {
			prevTargetPlayer = targetPlayer;
		}
		targetPlayer = null;
	}

	@Override
	public boolean shouldExecute() {
		if (!theBandit.getBanditInventory().isEmpty()) {
			return false;
		}
		double range = 32.0;
		List<EntityPlayer> players = theBanditAsNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, theBanditAsNPC.boundingBox.expand(range, range, range));
		ArrayList<EntityPlayer> validTargets = new ArrayList<>();
		for (EntityPlayer player : players) {
			if (player.capabilities.isCreativeMode || !theBandit.canTargetPlayerForTheft(player) || !IBandit.Helper.canStealFromPlayerInv(theBandit, player)) {
				continue;
			}
			validTargets.add(player);
		}
		if (validTargets.isEmpty()) {
			return false;
		}
		targetPlayer = validTargets.get(theBanditAsNPC.getRNG().nextInt(validTargets.size()));
		if (targetPlayer != prevTargetPlayer) {
			theBanditAsNPC.sendSpeechBank(targetPlayer, theBandit.getTheftSpeechBank(targetPlayer));
		}
		return true;
	}

	@Override
	public void startExecuting() {
		chaseTimer = 600;
	}

	public void steal() {
		InventoryPlayer inv = targetPlayer.inventory;
		int thefts = MathHelper.getRandomIntegerInRange(theBanditAsNPC.getRNG(), 1, theBandit.getMaxThefts());
		boolean stolenSomething = false;
		for (int i = 0; i < thefts; ++i) {
			if (tryStealItem(inv, GOTItemCoin.class)) {
				stolenSomething = true;
			}
			if (tryStealItem(inv, GOTItemGem.class) || tryStealItem(inv, GOTItemRing.class) || tryStealItem(inv, ItemArmor.class)) {
				stolenSomething = true;
				continue;
			}
			if (tryStealItem(inv, ItemSword.class) || tryStealItem(inv, ItemTool.class) || tryStealItem(inv, GOTItemPouch.class)) {
				stolenSomething = true;
				continue;
			}
			if (!tryStealItem(inv)) {
				continue;
			}
			stolenSomething = true;
		}
		if (stolenSomething) {
			targetPlayer.addChatMessage(theBandit.getTheftChatMsg(targetPlayer));
			theBanditAsNPC.playSound("mob.horse.leather", 0.5f, 1.0f);
			if (theBanditAsNPC.getAttackTarget() != null) {
				theBanditAsNPC.setAttackTarget(null);
			}
			GOTLevelData.getData(targetPlayer).cancelFastTravel();
		}
	}

	public boolean stealItem(InventoryPlayer inv, int slot) {
		ItemStack playerItem = inv.getStackInSlot(slot);
		int theft = getRandomTheftAmount(playerItem);
		if (theft > playerItem.stackSize) {
			theft = playerItem.stackSize;
		}
		int banditSlot = 0;
		while (theBandit.getBanditInventory().getStackInSlot(banditSlot) != null) {
			banditSlot++;
			if (banditSlot < theBandit.getBanditInventory().getSizeInventory()) {
				continue;
			}
			return false;
		}
		ItemStack stolenItem = playerItem.copy();
		stolenItem.stackSize = theft;
		theBandit.getBanditInventory().setInventorySlotContents(banditSlot, stolenItem);
		playerItem.stackSize -= theft;
		if (playerItem.stackSize <= 0) {
			inv.setInventorySlotContents(slot, null);
		}
		theBanditAsNPC.isNPCPersistent = true;
		return true;
	}

	public boolean tryStealItem(InventoryPlayer inv) {
		return tryStealItem_do(inv, new BanditItemFilter() {

			@Override
			public boolean isApplicable(ItemStack itemstack) {
				return true;
			}
		});
	}

	public boolean tryStealItem(InventoryPlayer inv, Class<? extends Item> itemclass) {
		return tryStealItem_do(inv, new BanditItemFilter() {

			@Override
			public boolean isApplicable(ItemStack itemstack) {
				return itemclass.isAssignableFrom(itemstack.getItem().getClass());
			}
		});
	}

	public boolean tryStealItem(InventoryPlayer inv, List<ItemStack> itemList) {
		return tryStealItem_do(inv, new BanditItemFilter() {

			@Override
			public boolean isApplicable(ItemStack itemstack) {
				for (ItemStack listItem : itemList) {
					if (!GOTRecipe.checkItemEquals(listItem, itemstack)) {
						continue;
					}
					return true;
				}
				return false;
			}
		});
	}

	public boolean tryStealItem_do(InventoryPlayer inv, BanditItemFilter filter) {
		Integer[] inventorySlots = new Integer[inv.mainInventory.length];
		for (int l = 0; l < inventorySlots.length; ++l) {
			inventorySlots[l] = l;
		}
		List<Integer> slotsAsList = Arrays.asList(inventorySlots);
		Collections.shuffle(slotsAsList);
		Integer[] arrinteger = slotsAsList.toArray(inventorySlots);
		for (Integer integer : arrinteger) {
			ItemStack itemstack;
			int slot = integer;
			if (slot == inv.currentItem || (itemstack = inv.getStackInSlot(slot)) == null || !filter.isApplicable(itemstack) || !stealItem(inv, slot)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public void updateTask() {
		--chaseTimer;
		theBanditAsNPC.getLookHelper().setLookPositionWithEntity(targetPlayer, 30.0f, 30.0f);
		--rePathDelay;
		if (rePathDelay <= 0) {
			rePathDelay = 10;
			theBanditAsNPC.getNavigator().tryMoveToEntityLiving(targetPlayer, speed);
		}
		if (theBanditAsNPC.getDistanceSqToEntity(targetPlayer) <= 2.0) {
			chaseTimer = 0;
			steal();
		}
	}

	public interface BanditItemFilter {
		boolean isApplicable(ItemStack var1);
	}

}
