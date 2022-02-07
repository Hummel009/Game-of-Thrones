package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.item.other.GOTItemCoin;
import got.common.quest.IPickpocketable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class GOTContainerCoinExchange extends Container {
	private IInventory coinInputInv = new InventoryCoinExchangeSlot(1);
	private IInventory exchangeInv = new InventoryCoinExchangeSlot(2);
	private GOTEntityNPC theTraderNPC;
	private boolean exchanged = false;

	public GOTContainerCoinExchange(EntityPlayer entityplayer, GOTEntityNPC npc) {
		int i;
		setTheTraderNPC(npc);
		addSlotToContainer(new Slot(coinInputInv, 0, 80, 46) {

			@Override
			public boolean isItemValid(ItemStack itemstack) {
				return super.isItemValid(itemstack) && itemstack != null && GOTContainerCoinExchange.isValidCoin(itemstack);
			}
		});
		class SlotCoinResult extends Slot {
			private SlotCoinResult(IInventory inv, int i, int j, int k) {
				super(inv, i, j, k);
			}

			@Override
			public boolean canTakeStack(EntityPlayer entityplayer) {
				return isExchanged();
			}

			@Override
			public boolean isItemValid(ItemStack itemstack) {
				return false;
			}
		}
		addSlotToContainer(new SlotCoinResult(getExchangeInv(), 0, 26, 46));
		addSlotToContainer(new SlotCoinResult(getExchangeInv(), 1, 134, 46));
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(entityplayer.inventory, i, 8 + i * 18, 164));
		}
		onCraftMatrixChanged(coinInputInv);
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		sendClientExchangedData(crafting);
		super.addCraftingToCrafters(crafting);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void detectAndSendChanges() {
		for (Object crafter : crafters) {
			ICrafting crafting = (ICrafting) crafter;
			sendClientExchangedData(crafting);
		}
		super.detectAndSendChanges();
	}

	public IInventory getExchangeInv() {
		return exchangeInv;
	}

	public GOTEntityNPC getTheTraderNPC() {
		return theTraderNPC;
	}

	public void handleExchangePacket(int slot) {
		if (!isExchanged() && coinInputInv.getStackInSlot(0) != null && slot >= 0 && slot < getExchangeInv().getSizeInventory() && getExchangeInv().getStackInSlot(slot) != null) {
			setExchanged(true);
			int coins = getExchangeInv().getStackInSlot(slot).stackSize;
			int coinsTaken = 0;
			if (slot == 0) {
				coinsTaken = coins / 4;
			} else if (slot == 1) {
				coinsTaken = coins * 4;
			}
			coinInputInv.decrStackSize(0, coinsTaken);
			for (int i = 0; i < getExchangeInv().getSizeInventory(); ++i) {
				if (i == slot) {
					continue;
				}
				getExchangeInv().setInventorySlotContents(i, null);
			}
			detectAndSendChanges();
			getTheTraderNPC().playTradeSound();
		}
	}

	public boolean isExchanged() {
		return exchanged;
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		super.onContainerClosed(entityplayer);
		if (!entityplayer.worldObj.isRemote) {
			int i;
			ItemStack itemstack;
			for (i = 0; i < coinInputInv.getSizeInventory(); ++i) {
				itemstack = coinInputInv.getStackInSlotOnClosing(i);
				if (itemstack == null) {
					continue;
				}
				entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
			}
			if (isExchanged()) {
				for (i = 0; i < getExchangeInv().getSizeInventory(); ++i) {
					itemstack = getExchangeInv().getStackInSlotOnClosing(i);
					if (itemstack == null) {
						continue;
					}
					entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inv) {
		if (inv == coinInputInv) {
			if (!isExchanged()) {
				ItemStack coin = coinInputInv.getStackInSlot(0);
				if (coin != null && coin.stackSize > 0 && GOTContainerCoinExchange.isValidCoin(coin)) {
					int coins = coin.stackSize;
					int coinType = coin.getItemDamage();
					if (coinType > 0) {
						int coinsFloor = coins;
						while (coinsFloor * 4 > getExchangeInv().getInventoryStackLimit()) {
							--coinsFloor;
						}
						getExchangeInv().setInventorySlotContents(0, new ItemStack(GOTRegistry.coin, coinsFloor * 4, coinType - 1));
					} else {
						getExchangeInv().setInventorySlotContents(0, null);
					}
					if (coinType < GOTItemCoin.values.length - 1 && coins >= 4) {
						getExchangeInv().setInventorySlotContents(1, new ItemStack(GOTRegistry.coin, coins / 4, coinType + 1));
					} else {
						getExchangeInv().setInventorySlotContents(1, null);
					}
				} else {
					getExchangeInv().setInventorySlotContents(0, null);
					getExchangeInv().setInventorySlotContents(1, null);
				}
			}
		} else if (inv == getExchangeInv() && isExchanged()) {
			boolean anyItems = false;
			for (int i = 0; i < getExchangeInv().getSizeInventory(); ++i) {
				if (getExchangeInv().getStackInSlot(i) == null) {
					continue;
				}
				anyItems = true;
			}
			if (!anyItems) {
				setExchanged(false);
				onCraftMatrixChanged(coinInputInv);
			}
		}
		super.onCraftMatrixChanged(inv);
	}

	@Override
	public void retrySlotClick(int i, int j, boolean flag, EntityPlayer entityplayer) {
	}

	private void sendClientExchangedData(ICrafting crafting) {
		crafting.sendProgressBarUpdate(this, 0, isExchanged() ? 1 : 0);
	}

	public void setExchanged(boolean exchanged) {
		this.exchanged = exchanged;
	}

	public void setExchangeInv(IInventory exchangeInv) {
		this.exchangeInv = exchangeInv;
	}

	public void setTheTraderNPC(GOTEntityNPC theTraderNPC) {
		this.theTraderNPC = theTraderNPC;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < 3) {
				if (!mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}
				onCraftMatrixChanged(slot.inventory);
			} else {
				boolean flag = false;
				Slot coinSlot = (Slot) inventorySlots.get(0);
				coinSlot.getStack();
				if (coinSlot.isItemValid(itemstack1) && mergeItemStack(itemstack1, 0, 1, true)) {
					flag = true;
				}
				if (!flag && (i >= 3 && i < 30 ? !mergeItemStack(itemstack1, 30, 39, false) : !mergeItemStack(itemstack1, 3, 30, false))) {
					return null;
				}
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
				detectAndSendChanges();
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(entityplayer, itemstack1);
		}
		return itemstack;
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			setExchanged(j == 1);
		}
	}

	private static boolean isValidCoin(ItemStack item) {
		return item.getItem() == GOTRegistry.coin && !IPickpocketable.Helper.isPickpocketed(item);
	}

	public class InventoryCoinExchangeSlot extends InventoryBasic {
		public InventoryCoinExchangeSlot(int i) {
			super("coinExchange", true, i);
		}

		@Override
		public void markDirty() {
			super.markDirty();
			onCraftMatrixChanged(this);
		}
	}

}
