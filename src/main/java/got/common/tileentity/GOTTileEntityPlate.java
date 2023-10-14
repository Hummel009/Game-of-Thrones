package got.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.entity.other.GOTPlateFallingInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class GOTTileEntityPlate extends TileEntity {
	public ItemStack foodItem;
	public GOTPlateFallingInfo plateFallInfo;

	public static boolean isValidFoodItem(ItemStack itemstack) {
		Item item;
		if (itemstack != null && (item = itemstack.getItem()) instanceof ItemFood) {
			if (item instanceof ItemSoup) {
				return false;
			}
			return !item.hasContainerItem(itemstack);
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public ItemStack getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(ItemStack item) {
		if (item != null && item.stackSize <= 0) {
			item = null;
		}
		foodItem = item;
		if (worldObj != null) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		markDirty();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		AxisAlignedBB bb = super.getRenderBoundingBox();
		if (foodItem != null) {
			bb = bb.addCoord(0.0, foodItem.stackSize * 0.03125f, 0.0);
		}
		return bb;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readFromNBT(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("FoodID")) {
			Item item = Item.getItemById(nbt.getInteger("FoodID"));
			if (item != null) {
				int damage = nbt.getInteger("FoodDamage");
				foodItem = new ItemStack(item, 1, damage);
			}
		} else {
			boolean empty = nbt.getBoolean("PlateEmpty");
			foodItem = empty ? null : ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("FoodItem"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("PlateEmpty", foodItem == null);
		if (foodItem != null) {
			nbt.setTag("FoodItem", foodItem.writeToNBT(new NBTTagCompound()));
		}
	}
}
