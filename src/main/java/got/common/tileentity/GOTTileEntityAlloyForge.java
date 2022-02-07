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
	private ItemStack[] inventory = new ItemStack[getForgeInvSize()];
	private String specialForgeName;
	private int forgeSmeltTime = 0;
	private int currentItemFuelValue = 0;
	private int currentSmeltTime = 0;
	private int[] inputSlots;
	private int[] outputSlots;
	private int fuelSlot;

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
		if (side == 0 && slot == getFuelSlot()) {
			return itemstack.getItem() == Items.bucket;
		}
		return true;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return isItemValidForSlot(slot, itemstack);
	}

	private boolean canMachineInsertFuel(ItemStack itemstack) {
		return TileEntityFurnace.isItemFuel(itemstack);
	}

	public boolean canMachineInsertInput(ItemStack itemstack) {
		return itemstack != null && getSmeltingResult(itemstack) != null;
	}

	private boolean canSmelt(int i) {
		ItemStack alloyResult;
		ItemStack result;
		int resultSize;
		if (getInventory()[i] == null) {
			return false;
		}
		if (getInventory()[i - 4] != null && (alloyResult = getAlloySmeltingResult(getInventory()[i], getInventory()[i - 4])) != null) {
			if (getInventory()[i + 4] == null) {
				return true;
			}
			resultSize = getInventory()[i + 4].stackSize + alloyResult.stackSize;
			if (getInventory()[i + 4].isItemEqual(alloyResult) && resultSize <= getInventoryStackLimit() && resultSize <= alloyResult.getMaxStackSize()) {
				return true;
			}
		}
		result = getSmeltingResult(getInventory()[i]);
		if (result == null) {
			return false;
		}
		if (getInventory()[i + 4] == null) {
			return true;
		}
		if (!getInventory()[i + 4].isItemEqual(result)) {
			return false;
		}
		resultSize = getInventory()[i + 4].stackSize + result.stackSize;
		return resultSize <= getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (getInventory()[i] != null) {
			if (getInventory()[i].stackSize <= j) {
				ItemStack itemstack = getInventory()[i];
				getInventory()[i] = null;
				return itemstack;
			}
			ItemStack itemstack = getInventory()[i].splitStack(j);
			if (getInventory()[i].stackSize == 0) {
				getInventory()[i] = null;
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
			for (int i : getOutputSlots()) {
				list.add(i);
			}
			list.add(getFuelSlot());
			int[] temp = new int[list.size()];
			for (int i = 0; i < temp.length; ++i) {
				temp[i] = list.get(i);
			}
			return temp;
		}
		if (side == 1) {
			ArrayList<GOTSlotStackSize> slotsWithStackSize = new ArrayList<>();
			int[] temp = getInputSlots();
			int i = temp.length;
			for (int j = 0; j < i; ++j) {
				int slot = temp[j];
				int size = getStackInSlot(slot) == null ? 0 : getStackInSlot(slot).stackSize;
				slotsWithStackSize.add(new GOTSlotStackSize(slot, size));
			}
			Collections.sort(slotsWithStackSize);
			int[] sortedSlots = new int[getInputSlots().length];
			for (i = 0; i < sortedSlots.length; ++i) {
				GOTSlotStackSize slotAndStack = slotsWithStackSize.get(i);
				sortedSlots[i] = slotAndStack.getSlot();
			}
			return sortedSlots;
		}
		return new int[] { getFuelSlot() };
	}

	private ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
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

	public int getCurrentItemFuelValue() {
		return currentItemFuelValue;
	}

	public int getCurrentSmeltTime() {
		return currentSmeltTime;
	}

	public int getForgeInvSize() {
		return 13;
	}

	public String getForgeName() {
		return StatCollector.translateToLocal("got.container.alloyForge");
	}

	public int getForgeSmeltTime() {
		return forgeSmeltTime;
	}

	public int getFuelSlot() {
		return fuelSlot;
	}

	public int[] getInputSlots() {
		return inputSlots;
	}

	public ItemStack[] getInventory() {
		return inventory;
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? specialForgeName : getForgeName();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public int[] getOutputSlots() {
		return outputSlots;
	}

	@Override
	public int getSizeInventory() {
		return getInventory().length;
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
		if (itemstack.getItem() == Item.getItemFromBlock(GOTRegistry.oreValyrian)) {
			return new ItemStack(GOTRegistry.valyrianIngot);
		}
		if (isStoneMaterial || isWood(itemstack)) {
			return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
		}
		return null;
	}

	@SideOnly(value = Side.CLIENT)
	public int getSmeltProgressScaled(int i) {
		return getCurrentSmeltTime() * i / getSmeltingDuration();
	}

	@SideOnly(value = Side.CLIENT)
	public int getSmeltTimeRemainingScaled(int i) {
		if (getCurrentItemFuelValue() == 0) {
			setCurrentItemFuelValue(getSmeltingDuration());
		}
		return getForgeSmeltTime() * i / getCurrentItemFuelValue();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return getInventory()[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (getInventory()[i] != null) {
			ItemStack itemstack = getInventory()[i];
			getInventory()[i] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return specialForgeName != null && specialForgeName.length() > 0;
	}

	private boolean isCobalt(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.cobaltIngot;
	}

	private boolean isCopper(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreCopper") || GOT.isOreNameEqual(itemstack, "ingotCopper");
	}

	private boolean isGoldNugget(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "nuggetGold");
	}

	private boolean isIron(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreIron") || GOT.isOreNameEqual(itemstack, "ingotIron");
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (ArrayUtils.contains(getInputSlots(), slot)) {
			return canMachineInsertInput(itemstack);
		}
		if (slot == getFuelSlot()) {
			return canMachineInsertFuel(itemstack);
		}
		return false;
	}

	private boolean isOathkeeper(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.oathkeeper;
	}

	private boolean isSilver(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreSilver") || GOT.isOreNameEqual(itemstack, "ingotSilver");
	}

	public boolean isSmelting() {
		return getForgeSmeltTime() > 0;
	}

	private boolean isTin(ItemStack itemstack) {
		return GOT.isOreNameEqual(itemstack, "oreTin") || GOT.isOreNameEqual(itemstack, "ingotTin");
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	private boolean isValyrianNugget(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.valyrianNugget;
	}

	private boolean isWidowWail(ItemStack itemstack) {
		return itemstack.getItem() == GOTRegistry.widowWail;
	}

	private boolean isWood(ItemStack itemstack) {
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
		setInventory(new ItemStack[getSizeInventory()]);
		for (int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound itemData = items.getCompoundTagAt(i);
			byte slot = itemData.getByte("Slot");
			if (slot < 0 || slot >= getInventory().length) {
				continue;
			}
			getInventory()[slot] = ItemStack.loadItemStackFromNBT(itemData);
		}
		setForgeSmeltTime(nbt.getShort("BurnTime"));
		setCurrentSmeltTime(nbt.getShort("SmeltTime"));
		setCurrentItemFuelValue(TileEntityFurnace.getItemBurnTime(getInventory()[getFuelSlot()]));
		if (nbt.hasKey("CustomName")) {
			specialForgeName = nbt.getString("CustomName");
		}
	}

	public void setCurrentItemFuelValue(int currentItemFuelValue) {
		this.currentItemFuelValue = currentItemFuelValue;
	}

	public void setCurrentSmeltTime(int currentSmeltTime) {
		this.currentSmeltTime = currentSmeltTime;
	}

	public int setForgeSmeltTime(int forgeSmeltTime) {
		this.forgeSmeltTime = forgeSmeltTime;
		return forgeSmeltTime;
	}

	public void setFuelSlot(int fuelSlot) {
		this.fuelSlot = fuelSlot;
	}

	public void setInputSlots(int[] inputSlots) {
		this.inputSlots = inputSlots;
	}

	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		getInventory()[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void setOutputSlots(int[] outputSlots) {
		this.outputSlots = outputSlots;
	}

	public void setSpecialForgeName(String s) {
		specialForgeName = s;
	}

	public void setupForgeSlots() {
		setInputSlots(new int[] { 4, 5, 6, 7 });
		setOutputSlots(new int[] { 8, 9, 10, 11 });
		setFuelSlot(12);
	}

	private void smeltItemInSlot(int i) {
		if (canSmelt(i)) {
			ItemStack alloyResult;
			boolean smeltedAlloyItem = false;
			if (getInventory()[i - 4] != null && (alloyResult = getAlloySmeltingResult(getInventory()[i], getInventory()[i - 4])) != null && (getInventory()[i + 4] == null || getInventory()[i + 4].isItemEqual(alloyResult))) {
				if (getInventory()[i + 4] == null) {
					getInventory()[i + 4] = alloyResult.copy();
				} else if (getInventory()[i + 4].isItemEqual(alloyResult)) {
					getInventory()[i + 4].stackSize += alloyResult.stackSize;
				}
				--getInventory()[i].stackSize;
				if (getInventory()[i].stackSize <= 0) {
					getInventory()[i] = null;
				}
				--getInventory()[i - 4].stackSize;
				if (getInventory()[i - 4].stackSize <= 0) {
					getInventory()[i - 4] = null;
				}
				smeltedAlloyItem = true;
			}
			if (!smeltedAlloyItem) {
				ItemStack result = getSmeltingResult(getInventory()[i]);
				if (getInventory()[i + 4] == null) {
					getInventory()[i + 4] = result.copy();
				} else if (getInventory()[i + 4].isItemEqual(result)) {
					getInventory()[i + 4].stackSize += result.stackSize;
				}
				--getInventory()[i].stackSize;
				if (getInventory()[i].stackSize <= 0) {
					getInventory()[i] = null;
				}
			}
		}
	}

	private void toggleForgeActive() {
		GOTBlockForgeBase.toggleForgeActive(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity() {
		boolean smelting = getForgeSmeltTime() > 0;
		boolean needUpdate = false;
		if (getForgeSmeltTime() > 0) {
			setForgeSmeltTime(getForgeSmeltTime() - 1);
		}
		if (!worldObj.isRemote) {
			if (getForgeSmeltTime() == 0 && canDoSmelting()) {
				setCurrentItemFuelValue(setForgeSmeltTime(TileEntityFurnace.getItemBurnTime(getInventory()[getFuelSlot()])));
				if (getForgeSmeltTime() > 0) {
					needUpdate = true;
					if (getInventory()[getFuelSlot()] != null) {
						--getInventory()[getFuelSlot()].stackSize;
						if (getInventory()[getFuelSlot()].stackSize == 0) {
							getInventory()[getFuelSlot()] = getInventory()[getFuelSlot()].getItem().getContainerItem(getInventory()[getFuelSlot()]);
						}
					}
				}
			}
			if (isSmelting() && canDoSmelting()) {
				setCurrentSmeltTime(getCurrentSmeltTime() + 1);
				if (getCurrentSmeltTime() == getSmeltingDuration()) {
					setCurrentSmeltTime(0);
					doSmelt();
					needUpdate = true;
				}
			} else {
				setCurrentSmeltTime(0);
			}
			if (smelting != getForgeSmeltTime() > 0) {
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
		for (int i = 0; i < getInventory().length; ++i) {
			if (getInventory()[i] == null) {
				continue;
			}
			NBTTagCompound itemData = new NBTTagCompound();
			itemData.setByte("Slot", (byte) i);
			getInventory()[i].writeToNBT(itemData);
			items.appendTag(itemData);
		}
		nbt.setTag("Items", items);
		nbt.setShort("BurnTime", (short) getForgeSmeltTime());
		nbt.setShort("SmeltTime", (short) getCurrentSmeltTime());
		if (hasCustomInventoryName()) {
			nbt.setString("CustomName", specialForgeName);
		}
	}
}
