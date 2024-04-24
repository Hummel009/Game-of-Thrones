package got.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.block.other.GOTBlockForgeBase;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.inventory.GOTSlotStackSize;
import got.common.util.GOTLog;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GOTTileEntityAlloyForge extends TileEntity implements ISidedInventory {
	public static final Collection<CraftingSnapshot> CRAFTING_SNAPSHOTS = new ArrayList<>();

	static {
		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{GOTBlocks.oreCopper, GOTItems.copperIngot}, new Object[]{GOTBlocks.oreTin, GOTItems.tinIngot}, GOTItems.bronzeIngot));
		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{GOTBlocks.oreTin, GOTItems.tinIngot}, new Object[]{GOTBlocks.oreCopper, GOTItems.copperIngot}, GOTItems.bronzeIngot));

		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{Blocks.iron_ore, Items.iron_ingot}, new Object[]{Items.gold_nugget}, GOTItems.yitiSteelIngot));
		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{Items.gold_nugget}, new Object[]{Blocks.iron_ore, Items.iron_ingot}, GOTItems.yitiSteelIngot));

		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{GOTBlocks.oreSilver, GOTItems.silverIngot}, new Object[]{GOTItems.valyrianNugget}, GOTItems.valyrianPowder));
		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{GOTItems.valyrianNugget}, new Object[]{GOTBlocks.oreSilver, GOTItems.silverIngot}, GOTItems.valyrianPowder));

		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{GOTItems.widowWail}, new Object[]{GOTItems.oathkeeper}, GOTItems.ice));
		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{GOTItems.oathkeeper}, new Object[]{GOTItems.widowWail}, GOTItems.ice));

		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{GOTItems.cobaltIngot, GOTBlocks.oreCobalt}, new Object[]{Blocks.iron_ore, Items.iron_ingot}, GOTItems.alloySteelIngot));
		CRAFTING_SNAPSHOTS.add(new CraftingSnapshot(new Object[]{Blocks.iron_ore, Items.iron_ingot}, new Object[]{GOTItems.cobaltIngot, GOTBlocks.oreCobalt}, GOTItems.alloySteelIngot));
	}

	protected ItemStack[] inventory = new ItemStack[getForgeInvSize()];
	protected int[] inputSlots;
	protected int[] outputSlots;
	protected int fuelSlot;
	private int forgeSmeltTime;
	private int currentItemFuelValue;
	private int currentSmeltTime;
	private String specialForgeName;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTTileEntityAlloyForge() {
		setupForgeSlots();
	}

	private static ItemStack getAlloySmeltingResult(ItemStack itemStackUpper, ItemStack itemStackLower) {
		for (CraftingSnapshot craftingSnapshot : CRAFTING_SNAPSHOTS) {
			if (craftingSnapshot.upperContains(itemStackUpper) && craftingSnapshot.lowerContains(itemStackLower)) {
				return craftingSnapshot.getResult();
			}
		}
		return null;
	}

	public static ItemStack getSmeltingResult(ItemStack itemstack) {
		boolean isStoneMaterial = false;
		Item item = itemstack.getItem();
		Block block = Block.getBlockFromItem(item);
		if (block != null && block != Blocks.air) {
			Material material = block.getMaterial();
			if (material == Material.rock || material == Material.sand || material == Material.clay) {
				isStoneMaterial = true;
			}
		} else if (item == Items.clay_ball || item == GOTItems.redClayBall || item == GOTItems.clayMug || item == GOTItems.clayPlate || item == GOTItems.ceramicPlate) {
			isStoneMaterial = true;
		}
		if (itemstack.getItem() == Item.getItemFromBlock(GOTBlocks.oreValyrian)) {
			return new ItemStack(GOTItems.valyrianIngot);
		}
		if (isStoneMaterial || GOT.isOreNameEqual(itemstack, "logWood")) {
			return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
		}
		return null;
	}

	protected boolean canDoSmelting() {
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
		return side != 0 || slot != fuelSlot || itemstack.getItem() == Items.bucket;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return isItemValidForSlot(slot, itemstack);
	}

	protected boolean canMachineInsertInput(ItemStack itemstack) {
		return itemstack != null && getSmeltingResult(itemstack) != null;
	}

	private boolean canSmelt(int i) {
		ItemStack alloyResult;
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
		ItemStack result = getSmeltingResult(inventory[i]);
		if (result == null) {
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

	protected void doSmelt() {
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
				sortedSlots[i] = slotAndStack.getSlot();
			}
			return sortedSlots;
		}
		return new int[]{fuelSlot};
	}

	protected int getForgeInvSize() {
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

	protected int getSmeltingDuration() {
		return 200;
	}

	@SideOnly(Side.CLIENT)
	public int getSmeltProgressScaled(int i) {
		return currentSmeltTime * i / getSmeltingDuration();
	}

	@SideOnly(Side.CLIENT)
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
		return specialForgeName != null && !specialForgeName.isEmpty();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if (ArrayUtils.contains(inputSlots, slot)) {
			return canMachineInsertInput(itemstack);
		}
		return slot == fuelSlot && TileEntityFurnace.isItemFuel(itemstack);
	}

	public boolean isSmelting() {
		return forgeSmeltTime > 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
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

	protected void setupForgeSlots() {
		inputSlots = new int[]{4, 5, 6, 7};
		outputSlots = new int[]{8, 9, 10, 11};
		fuelSlot = 12;
	}

	private void smeltItemInSlot(int i) {
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

	private void toggleForgeActive() {
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

	public int getForgeSmeltTime() {
		return forgeSmeltTime;
	}

	public void setForgeSmeltTime(int forgeSmeltTime) {
		this.forgeSmeltTime = forgeSmeltTime;
	}

	public int getCurrentItemFuelValue() {
		return currentItemFuelValue;
	}

	public void setCurrentItemFuelValue(int currentItemFuelValue) {
		this.currentItemFuelValue = currentItemFuelValue;
	}

	public int getCurrentSmeltTime() {
		return currentSmeltTime;
	}

	public void setCurrentSmeltTime(int currentSmeltTime) {
		this.currentSmeltTime = currentSmeltTime;
	}

	@SuppressWarnings("unused")
	public String getSpecialForgeName() {
		return specialForgeName;
	}

	public void setSpecialForgeName(String s) {
		specialForgeName = s;
	}

	public static class CraftingSnapshot {
		private final ItemStack[] rowUpper;
		private final ItemStack[] rowLower;
		private final ItemStack result;

		protected CraftingSnapshot(Object[] rowUpper, Object[] rowLower, Object result) {
			this.rowUpper = wrapStackArray(rowUpper);
			this.rowLower = wrapStackArray(rowLower);
			this.result = wrapStack(result);
		}

		private static ItemStack wrapStack(Object obj) {
			if (obj instanceof Block) {
				return new ItemStack((Block) obj);
			}

			if (obj instanceof Item) {
				return new ItemStack((Item) obj);
			}

			GOTLog.getLogger().error("wrapStack");

			return null;
		}

		private static ItemStack[] wrapStackArray(Object[] objArray) {
			ItemStack[] stackArray = new ItemStack[objArray.length];

			for (int i = 0; i < objArray.length; i++) {
				Object obj = objArray[i];

				if (obj instanceof Block) {
					stackArray[i] = new ItemStack((Block) obj);
				} else if (obj instanceof Item) {
					stackArray[i] = new ItemStack((Item) obj);
				} else {
					GOTLog.getLogger().error("wrapStackArray");
				}
			}

			return stackArray;
		}

		protected boolean upperContains(ItemStack item) {
			for (ItemStack obj : rowUpper) {
				if (obj != null && obj.getItem().equals(item.getItem())) {
					return true;
				}
			}
			return false;
		}

		protected boolean lowerContains(ItemStack item) {
			for (ItemStack obj : rowLower) {
				if (obj != null && obj.getItem().equals(item.getItem())) {
					return true;
				}
			}
			return false;
		}

		public ItemStack[] getRowUpper() {
			return rowUpper;
		}

		public ItemStack[] getRowLower() {
			return rowLower;
		}

		public ItemStack getResult() {
			return result;
		}
	}
}