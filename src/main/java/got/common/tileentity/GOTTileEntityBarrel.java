package got.common.tileentity;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import got.common.inventory.GOTSlotStackSize;
import got.common.item.GOTPoisonedDrinks;
import got.common.item.other.GOTItemMug;
import got.common.recipe.GOTRecipeBrewing;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class GOTTileEntityBarrel extends TileEntity implements ISidedInventory {
	private static int[] INGREDIENT_SLOTS = { 0, 1, 2, 3, 4, 5 };
	private static int[] BUCKET_SLOTS = { 6, 7, 8 };
	private ItemStack[] inventory = new ItemStack[10];
	private int barrelMode;
	private int brewingTime;
	private int brewingAnim;
	private int brewingAnimPrev;
	private String specialBarrelName;
	private List players = new ArrayList();

	@Override
	public boolean canExtractItem(int slot, ItemStack extractItem, int side) {
		if (ArrayUtils.contains(BUCKET_SLOTS, slot)) {
			return !isItemValidForSlot(slot, extractItem);
		}
		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack insertItem, int side) {
		return isItemValidForSlot(slot, insertItem);
	}

	public boolean canPoisonBarrel() {
		if (getBarrelMode() != 0 && inventory[9] != null) {
			ItemStack itemstack = inventory[9];
			return GOTPoisonedDrinks.canPoison(itemstack) && !GOTPoisonedDrinks.isDrinkPoisoned(itemstack);
		}
		return false;
	}

	@Override
	public void closeInventory() {
	}

	public void consumeMugRefill() {
		if (getBarrelMode() == 2 && inventory[9] != null) {
			--inventory[9].stackSize;
			if (inventory[9].stackSize <= 0) {
				inventory[9] = null;
				setBarrelMode(0);
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				if (i != 9) {
					updateBrewingRecipe();
				}
				return itemstack;
			}
			ItemStack itemstack = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			if (i != 9) {
				updateBrewingRecipe();
			}
			return itemstack;
		}
		return null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (side == 0) {
			return BUCKET_SLOTS;
		}
		if (side == 1) {
			ArrayList<GOTSlotStackSize> slotsWithStackSize = new ArrayList<>();
			for (int slot : INGREDIENT_SLOTS) {
				int size = getStackInSlot(slot) == null ? 0 : getStackInSlot(slot).stackSize;
				slotsWithStackSize.add(new GOTSlotStackSize(slot, size));
			}
			Collections.sort(slotsWithStackSize);
			int[] sortedSlots = new int[INGREDIENT_SLOTS.length];
			for (int i = 0; i < sortedSlots.length; ++i) {
				GOTSlotStackSize slotAndStack = slotsWithStackSize.get(i);
				sortedSlots[i] = slotAndStack.getSlot();
			}
			return sortedSlots;
		}
		return BUCKET_SLOTS;
	}

	public int getBarrelFullAmountScaled(int i) {
		return inventory[9] == null ? 0 : inventory[9].stackSize * i / GOTRecipeBrewing.getBarrelCapacity();
	}

	public int getBarrelMode() {
		return barrelMode;
	}

	public float getBrewAnimationProgressScaledF(int i, float f) {
		float f1 = (float) brewingAnimPrev * (float) i / 32.0f;
		float f2 = (float) brewingAnim * (float) i / 32.0f;
		return f1 + (f2 - f1) * f;
	}

	public ItemStack getBrewedDrink() {
		if (getBarrelMode() == 2 && inventory[9] != null) {
			return inventory[9].copy();
		}
		return null;
	}

	public int getBrewingTime() {
		return brewingTime;
	}

	public int getBrewProgressScaled(int i) {
		return getBrewingTime() * i / 12000;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeBarrelToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? specialBarrelName : StatCollector.translateToLocal("got.container.barrel");
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public String getInvSubtitle() {
		ItemStack brewingItem = getStackInSlot(9);
		if (getBarrelMode() == 0) {
			return StatCollector.translateToLocal("got.container.barrel.empty");
		}
		if (getBarrelMode() == 1 && brewingItem != null) {
			return StatCollector.translateToLocalFormatted("got.container.barrel.brewing", brewingItem.getDisplayName(), GOTItemMug.getStrengthSubtitle(brewingItem));
		}
		if (getBarrelMode() == 2 && brewingItem != null) {
			return StatCollector.translateToLocalFormatted("got.container.barrel.full", brewingItem.getDisplayName(), GOTItemMug.getStrengthSubtitle(brewingItem), brewingItem.stackSize);
		}
		return "";
	}

	public List getPlayers() {
		return players;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (inventory[i] != null) {
			ItemStack itemstack = inventory[i];
			inventory[i] = null;
			return itemstack;
		}
		return null;
	}

	public void handleBrewingButtonPress() {
		if (getBarrelMode() == 0 && inventory[9] != null) {
			int i;
			setBarrelMode(1);
			for (i = 0; i < 9; ++i) {
				if (inventory[i] == null) {
					continue;
				}
				ItemStack containerItem = null;
				if (inventory[i].getItem().hasContainerItem(inventory[i]) && (containerItem = inventory[i].getItem().getContainerItem(inventory[i])).isItemStackDamageable() && containerItem.getItemDamage() > containerItem.getMaxDamage()) {
					containerItem = null;
				}
				--inventory[i].stackSize;
				if (inventory[i].stackSize > 0) {
					continue;
				}
				inventory[i] = null;
				if (containerItem == null) {
					continue;
				}
				inventory[i] = containerItem;
			}
			if (!worldObj.isRemote) {
				for (i = 0; i < getPlayers().size(); ++i) {
					EntityPlayerMP entityplayer = (EntityPlayerMP) getPlayers().get(i);
					entityplayer.openContainer.detectAndSendChanges();
					entityplayer.sendContainerToPlayer(entityplayer.openContainer);
				}
			}
		} else if (getBarrelMode() == 1 && inventory[9] != null && inventory[9].getItemDamage() > 0) {
			setBarrelMode(2);
			setBrewingTime(0);
			ItemStack itemstack = inventory[9].copy();
			itemstack.setItemDamage(itemstack.getItemDamage() - 1);
			inventory[9] = itemstack;
		}
	}

	@Override
	public boolean hasCustomInventoryName() {
		return specialBarrelName != null && specialBarrelName.length() > 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (ArrayUtils.contains(INGREDIENT_SLOTS, slot)) {
			return true;
		}
		if (ArrayUtils.contains(BUCKET_SLOTS, slot)) {
			return GOTRecipeBrewing.isWaterSource(itemstack);
		}
		return false;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readBarrelFromNBT(data);
	}

	@Override
	public void openInventory() {
	}

	public void poisonBarrel(EntityPlayer entityplayer) {
		ItemStack itemstack = inventory[9];
		GOTPoisonedDrinks.setDrinkPoisoned(itemstack, true);
		GOTPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
	}

	public void readBarrelFromNBT(NBTTagCompound nbt) {
		NBTTagList items = nbt.getTagList("Items", 10);
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemData = items.getCompoundTagAt(i);
			byte slot = itemData.getByte("Slot");
			if (slot < 0 || slot >= inventory.length) {
				continue;
			}
			inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
		}
		setBarrelMode(nbt.getByte("BarrelMode"));
		setBrewingTime(nbt.getInteger("BrewingTime"));
		if (nbt.hasKey("CustomName")) {
			specialBarrelName = nbt.getString("CustomName");
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readBarrelFromNBT(nbt);
	}

	public void setBarrelMode(int barrelMode) {
		this.barrelMode = barrelMode;
	}

	public void setBarrelName(String s) {
		specialBarrelName = s;
	}

	public void setBrewingTime(int brewingTime) {
		this.brewingTime = brewingTime;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		if (i != 9) {
			updateBrewingRecipe();
		}
	}

	public void setPlayers(List players) {
		this.players = players;
	}

	private void updateBrewingRecipe() {
		if (getBarrelMode() == 0) {
			inventory[9] = GOTRecipeBrewing.findMatchingRecipe(this);
		}
	}

	@Override
	public void updateEntity() {
		boolean needUpdate = false;
		if (!worldObj.isRemote) {
			if (getBarrelMode() == 1) {
				if (inventory[9] != null) {
					setBrewingTime(getBrewingTime() + 1);
					if (getBrewingTime() >= 12000) {
						setBrewingTime(0);
						if (inventory[9].getItemDamage() < 4) {
							inventory[9].setItemDamage(inventory[9].getItemDamage() + 1);
							needUpdate = true;
						} else {
							setBarrelMode(2);
						}
					}
				} else {
					setBarrelMode(0);
				}
			} else {
				setBrewingTime(0);
			}
			if (getBarrelMode() == 2 && inventory[9] == null) {
				setBarrelMode(0);
			}
		} else {
			brewingAnimPrev = brewingAnim++;
			if (getBarrelMode() == 1) {
				if (brewingAnim >= 32) {
					brewingAnimPrev = brewingAnim = 0;
				}
			} else {
				brewingAnimPrev = brewingAnim = 0;
			}
		}
		if (needUpdate) {
			markDirty();
		}
	}

	public void writeBarrelToNBT(NBTTagCompound nbt) {
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < inventory.length; ++i) {
			if (inventory[i] == null) {
				continue;
			}
			NBTTagCompound itemData = new NBTTagCompound();
			itemData.setByte("Slot", (byte) i);
			inventory[i].writeToNBT(itemData);
			items.appendTag(itemData);
		}
		nbt.setTag("Items", items);
		nbt.setByte("BarrelMode", (byte) getBarrelMode());
		nbt.setInteger("BrewingTime", getBrewingTime());
		if (hasCustomInventoryName()) {
			nbt.setString("CustomName", specialBarrelName);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeBarrelToNBT(nbt);
	}
}
