package got.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class GOTTileEntityFlowerPot extends TileEntity {
	private Item item;
	private int meta;

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public Item getItem() {
		return item;
	}

	public int getMeta() {
		return meta;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setItem(Item.getItemById(nbt.getInteger("PlantID")));
		setMeta(nbt.getInteger("PlantMeta"));
		if (Block.getBlockFromItem(getItem()) == null) {
			setItem(null);
			setMeta(0);
		}
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("PlantID", Item.getIdFromItem(getItem()));
		nbt.setInteger("PlantMeta", getMeta());
	}
}
