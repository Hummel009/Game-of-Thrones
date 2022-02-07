package got.common.tileentity;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.block.other.GOTBlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class GOTTileEntityChest extends TileEntity implements IInventory {
	private ItemStack[] chestContents = new ItemStack[getSizeInventory()];
	private float lidAngle;
	private float prevLidAngle;
	private String textureName;
	private int numPlayersUsing;
	private int ticksSinceSync;
	private String customName;

	@Override
	public void closeInventory() {
		if (getBlockType() instanceof GOTBlockChest) {
			--numPlayersUsing;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numPlayersUsing);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (chestContents[i] != null) {
			if (chestContents[i].stackSize <= j) {
				ItemStack itemstack = chestContents[i];
				chestContents[i] = null;
				markDirty();
				return itemstack;
			}
			ItemStack itemstack = chestContents[i].splitStack(j);
			if (chestContents[i].stackSize == 0) {
				chestContents[i] = null;
			}
			markDirty();
			return itemstack;
		}
		return null;
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? customName : "container.chest";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public float getLidAngle() {
		return lidAngle;
	}

	public float getPrevLidAngle() {
		return prevLidAngle;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return chestContents[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (chestContents[i] != null) {
			ItemStack itemstack = chestContents[i];
			chestContents[i] = null;
			return itemstack;
		}
		return null;
	}

	public String getTextureName() {
		return textureName;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return customName != null && customName.length() > 0;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		updateContainingBlockInfo();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void openInventory() {
		if (numPlayersUsing < 0) {
			numPlayersUsing = 0;
		}
		++numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, numPlayersUsing);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList itemTags = nbt.getTagList("Items", 10);
		chestContents = new ItemStack[getSizeInventory()];
		for (int i = 0; i < itemTags.tagCount(); ++i) {
			NBTTagCompound slotData = itemTags.getCompoundTagAt(i);
			int slot = slotData.getByte("Slot") & 0xFF;
			if (slot < 0 || slot >= chestContents.length) {
				continue;
			}
			chestContents[slot] = ItemStack.loadItemStackFromNBT(slotData);
		}
		if (nbt.hasKey("CustomName", 8)) {
			customName = nbt.getString("CustomName");
		}
	}

	@Override
	public boolean receiveClientEvent(int i, int j) {
		if (i == 1) {
			numPlayersUsing = j;
			return true;
		}
		return super.receiveClientEvent(i, j);
	}

	public void setCustomName(String s) {
		customName = s;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		chestContents[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	public float setLidAngle(float lidAngle) {
		this.lidAngle = lidAngle;
		return lidAngle;
	}

	public void setPrevLidAngle(float prevLidAngle) {
		this.prevLidAngle = prevLidAngle;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		setPrevLidAngle(getLidAngle());
		++ticksSinceSync;
		if (!worldObj.isRemote && numPlayersUsing != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0) {
			numPlayersUsing = 0;
			float range = 5.0f;
			List players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + 1 + range, yCoord + 1 + range, zCoord + 1 + range));
			for (Object obj : players) {
				EntityPlayer entityplayer = (EntityPlayer) obj;
				if (!(entityplayer.openContainer instanceof ContainerChest) || ((ContainerChest) entityplayer.openContainer).getLowerChestInventory() != this) {
					continue;
				}
				++numPlayersUsing;
			}
		}
		if (numPlayersUsing > 0 && getLidAngle() == 0.0f) {
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.chestopen", 0.5f, worldObj.rand.nextFloat() * 0.1f + 0.9f);
		}
		if (numPlayersUsing == 0 && getLidAngle() > 0.0f || numPlayersUsing > 0 && getLidAngle() < 1.0f) {
			float pre = getLidAngle();
			float incr = 0.1f;
			setLidAngle(numPlayersUsing > 0 ? setLidAngle(getLidAngle() + incr) : setLidAngle(getLidAngle() - incr));
			setLidAngle(Math.min(getLidAngle(), 1.0f));
			setLidAngle(Math.max(getLidAngle(), 0.0f));
			float thr = 0.5f;
			if (getLidAngle() < thr && pre >= thr) {
				worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.chestclosed", 0.5f, worldObj.rand.nextFloat() * 0.1f + 0.9f);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList itemTags = new NBTTagList();
		for (int i = 0; i < chestContents.length; ++i) {
			if (chestContents[i] == null) {
				continue;
			}
			NBTTagCompound slotData = new NBTTagCompound();
			slotData.setByte("Slot", (byte) i);
			chestContents[i].writeToNBT(slotData);
			itemTags.appendTag(slotData);
		}
		nbt.setTag("Items", itemTags);
		if (hasCustomInventoryName()) {
			nbt.setString("CustomName", customName);
		}
	}
}
