package got.common.tileentity;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.block.other.GOTBlockForgeBase;
import got.common.database.GOTRegistry;
import got.common.inventory.GOTSlotStackSize;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.*;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.*;
import net.minecraft.util.StatCollector;

public class GOTTileEntityAlloyForge extends TileEntity implements IInventory, ISidedInventory {
	public ItemStack[] inventory = new ItemStack[getForgeInvSize()];
	public String specialForgeName;
	public int forgeSmeltTime = 0;
	public int currentItemFuelValue = 0;
	public int currentSmeltTime = 0;
	public int[] inputSlots;
	public int[] outputSlots;
	public int fuelSlot;

	public GOTTileEntityAlloyForge() {
		setupForgeSlots();
	}

	public boolean canDoSmelting() {
		for (int i = 4; i < 8; ++i) {
			if (!canSmelt(i)) {
				continue;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		if ((side == 0) && (slot == fuelSlot)) {
			return itemstack.getItem() == Items.bucket;
		}
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return isItemValidForSlot(slot, itemstack);
	}

	public boolean canMachineInsertFuel(ItemStack itemstack) {
		return TileEntityFurnace.isItemFuel(itemstack);
	}

	public boolean canMachineInsertInput(ItemStack itemstack) {
		return itemstack != null && getSmeltingResult(itemstack) != null;
	}

	public boolean canSmelt(int i) {
		ItemStack alloyResult;
		ItemStack result;
		int resultSize;
		if (inventory[i] == null) {
			return false;
		}
		if (inventory[i - 4] != null && (alloyResult = getAlloySmeltingResult(inventory[i], inventory[i - 4])) != null) {
			if (inventory[i + 4] == null) {
				return true;
			}
			resultSize = inventory[i + 4].stackSize + alloyResult.stackSize;
			if (inventory[i + 4].isItemEqual(alloyResult) && resultSize <= getInventoryStackLimit() && resultSize <= alloyResult.getMaxStackSize()) {
				return true;
			}
		}
		if ((result = getSmeltingResult(inventory[i])) == null) {
			return false;
		}
		if (inventory[i + 4] == null) {
			return true;
		}
		if (!inventory[i + 4].isItemEqual(result)) {
			return false;
		}
		resultSize = inventory[i + 4].stackSize + result.stackSize;
		return resultSize <= getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack;
		}
		return null;
	}

	public void doSmelt() {
		for (int i = 4; i < 8; ++i) {
			smeltItemInSlot(i);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if (side == 0) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int i : outputSlots) {
				list.add(i);
			}
			list.add(fuelSlot);
			int[] temp = new int[list.size()];
			for (int i = 0; i < temp.length; ++i) {
				temp[i] = list.get(i);
			}
			return temp;
		}
		if (side == 1) {
			ArrayList<GOTSlotStackSize> slotsWithStackSize = new ArrayList<>();
			int[] temp = inputSlots;
			int i = temp.length;
			for (int j = 0; j < i; ++j) {
				int slot = temp[j];
				int size = getStackInSlot(slot) == null ? 0 : getStackInSlot(slot).stackSize;
				slotsWithStackSize.add(new GOTSlotStackSize(slot, size));
			}
			Collections.sort(slotsWithStackSize);
			int[] sortedSlots = new int[inputSlots.length];
			for (i = 0; i < sortedSlots.length; ++i) {
				GOTSlotStackSize slotAndStack = slotsWithStackSize.get(i);
				sortedSlots[i] = slotAndStack.slot;
			}
			return sortedSlots;
		}
		return new int[] { fuelSlot };
	}

	public ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
		if (isCopper(itemstack) && isTin(alloyItem) || isTin(itemstack) && isCopper(alloyItem)) {
			return new ItemStack(GOTRegistry.bronzeIngot, 2);
		}
		if (isIron(itemstack) && isGoldNugget(alloyItem) || isGoldNugget(itemstack) && isIron(alloyItem)) {
			return new ItemStack(GOTRegistry.yitiSteelIngot);
		}
		if (isSilver(itemstack) && isValyrianNugget(alloyItem) || isValyrianNugget(itemstack) && isSilver(alloyItem)) {
			return new ItemStack(GOTRegistry.valyrianPowder);
		}
		if (isWidowWail(itemstack) && isOathkeeper(alloyItem) || isOathkeeper(itemstack) && isWidowWail(alloyItem)) {
			return new ItemStack(GOTRegistry.ice);
		}
		if (isCobalt(itemstack) && isIron(alloyItem) || isIron(itemstack) && isCobalt(alloyItem)) {
			return new ItemStack(GOTRegistry.alloySteelIgnot);
		}
		return null;
	}

	public int getForgeInvSize() {
		return 13;
	}

	public String getForgeName() {
		return StatCollector.translateToLocal("got.container.alloyForge");
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? specialForgeName : getForgeName();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	public int getSmeltingDuration() {
		return 200;
	}

	public ItemStack getSmeltingResult(ItemStack itemstack) {
		boolean isStoneMaterial = false;
		Item item = itemstack.getItem();
		Block block = Block.getBlockFromItem(item);
		if (block != null && block != Blocks.air) {
			Material material = block.getMaterial();
			if (material == Material.rock || material == Material.sand || material == Material.clay) {
				isStoneMaterial = true;
			}
		} else if (item == Items.clay_ball || item == GOTRegistry.redClayBall || item == GOTRegistry.clayMug || item == GOTRegistry.clayPlate || item == GOTRegistry.ceramicPlate) {
			isStoneMaterial = true;
		}
        if (itemstack.getItem() == Item.getItemFromBlock((Block)GOTRegistry.oreValyrian)) {
            return new ItemStack(GOTRegistry.valyrianIngot);
        }
		if (isStoneMaterial || isWood(itemstack)) {
			return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
		}
		return null;
	}

	@SideOnly(value = Side.CLIENT)
	public int getSmeltProgressScaled(int i) {
		return currentSmeltTime * i / getSmeltingDuration();
	}

	@SideOnly(value = Side.CLIENT)
	public int getSmeltTimeRemainingScaled(int i) {
		if (currentItemFuelValue == 0) {
			currentItemFuelValue = getSmeltingDuration();
		}
		return forgeSmeltTime * i / currentItemFuelValue;
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

	@Override
	public boolean hasCustomInventoryName() {
		return specialForgeName != null && specialForgeName.length() > 0;
	}

	public boolean isCoal(ItemStack itemstack) {
		return itemstack.getItem() == Items.coal;
	}

	public boolean isCobalt(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.cobaltIngot;
	}

	public boolean isCopper(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreCopper") || GOT.isOreNameEqual(itemstack, "ingotCopper");
	}

	public boolean isGoldNugget(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "nuggetGold");
	}

	public boolean isIron(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreIron") || GOT.isOreNameEqual(itemstack, "ingotIron");
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (ArrayUtils.contains(inputSlots, slot)) {
			return canMachineInsertInput(itemstack);
		}
		if (slot == fuelSlot) {
			return canMachineInsertFuel(itemstack);
		}
		return false;
	}

	public boolean isOathkeeper(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.oathkeeper;
	}

	public boolean isSilver(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreSilver") || GOT.isOreNameEqual(itemstack, "ingotSilver");
	}

	public boolean isSmelting() {
		return forgeSmeltTime > 0;
	}

	public boolean isTin(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreTin") || GOT.isOreNameEqual(itemstack, "ingotTin");
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	public boolean isValyrianNugget(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.valyrianNugget;
	}

	public boolean isWidowWail(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.widowWail;
	}

	public boolean isWood(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "logWood");
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
		if (packet.func_148857_g() != null && packet.func_148857_g().hasKey("CustomName")) {
			specialForgeName = packet.func_148857_g().getString("CustomName");
		}
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
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
		forgeSmeltTime = nbt.getShort("BurnTime");
		currentSmeltTime = nbt.getShort("SmeltTime");
		currentItemFuelValue = TileEntityFurnace.getItemBurnTime(inventory[fuelSlot]);
		if (nbt.hasKey("CustomName")) {
			specialForgeName = nbt.getString("CustomName");
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void setSpecialForgeName(String s) {
		specialForgeName = s;
	}

	public void setupForgeSlots() {
		inputSlots = new int[] { 4, 5, 6, 7 };
		outputSlots = new int[] { 8, 9, 10, 11 };
		fuelSlot = 12;
	}

	public void smeltItemInSlot(int i) {
		if (canSmelt(i)) {
			ItemStack alloyResult;
			boolean smeltedAlloyItem = false;
			if (inventory[i - 4] != null && (alloyResult = getAlloySmeltingResult(inventory[i], inventory[i - 4])) != null && (inventory[i + 4] == null || inventory[i + 4].isItemEqual(alloyResult))) {
				if (inventory[i + 4] == null) {
					inventory[i + 4] = alloyResult.copy();
				} else if (inventory[i + 4].isItemEqual(alloyResult)) {
					inventory[i + 4].stackSize += alloyResult.stackSize;
				}
				--inventory[i].stackSize;
				if (inventory[i].stackSize <= 0) {
					inventory[i] = null;
				}
				--inventory[i - 4].stackSize;
				if (inventory[i - 4].stackSize <= 0) {
					inventory[i - 4] = null;
				}
				smeltedAlloyItem = true;
			}
			if (!smeltedAlloyItem) {
				ItemStack result = getSmeltingResult(inventory[i]);
				if (inventory[i + 4] == null) {
					inventory[i + 4] = result.copy();
				} else if (inventory[i + 4].isItemEqual(result)) {
					inventory[i + 4].stackSize += result.stackSize;
				}
				--inventory[i].stackSize;
				if (inventory[i].stackSize <= 0) {
					inventory[i] = null;
				}
			}
		}
	}

	public void toggleForgeActive() {
		GOTBlockForgeBase.toggleForgeActive(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {
		boolean smelting = forgeSmeltTime > 0;
		boolean needUpdate = false;
		if (forgeSmeltTime > 0) {
			--forgeSmeltTime;
		}
		if (!worldObj.isRemote) {
			if (forgeSmeltTime == 0 && canDoSmelting()) {
				currentItemFuelValue = forgeSmeltTime = TileEntityFurnace.getItemBurnTime(inventory[fuelSlot]);
				if (forgeSmeltTime > 0) {
					needUpdate = true;
					if (inventory[fuelSlot] != null) {
						--inventory[fuelSlot].stackSize;
						if (inventory[fuelSlot].stackSize == 0) {
							inventory[fuelSlot] = inventory[fuelSlot].getItem().getContainerItem(inventory[fuelSlot]);
						}
					}
				}
			}
			if (isSmelting() && canDoSmelting()) {
				++currentSmeltTime;
				if (currentSmeltTime == getSmeltingDuration()) {
					currentSmeltTime = 0;
					doSmelt();
					needUpdate = true;
				}
			} else {
				currentSmeltTime = 0;
			}
			if (smelting != forgeSmeltTime > 0) {
				needUpdate = true;
				toggleForgeActive();
			}
		}
		if (needUpdate) {
			markDirty();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
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
		nbt.setShort("BurnTime", (short) forgeSmeltTime);
		nbt.setShort("SmeltTime", (short) currentSmeltTime);
		if (hasCustomInventoryName()) {
			nbt.setString("CustomName", specialForgeName);
		}
	}
}
