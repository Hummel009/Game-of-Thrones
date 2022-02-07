package got.common.tileentity;

import got.common.database.GOTRegistry;
import got.common.item.GOTPoisonedDrinks;
import got.common.item.other.GOTItemMug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class GOTTileEntityMug extends TileEntity {
	private ItemStack mugItem;
	private GOTItemMug.Vessel mugVessel;

	public boolean canPoisonMug() {
		ItemStack itemstack = getMugItem();
		if (itemstack != null) {
			return GOTPoisonedDrinks.canPoison(itemstack) && !GOTPoisonedDrinks.isDrinkPoisoned(itemstack);
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeToNBT(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public ItemStack getMugItem() {
		if (mugItem == null) {
			return getVessel().getEmptyVessel();
		}
		ItemStack copy = mugItem.copy();
		if (GOTItemMug.isItemFullDrink(copy)) {
			GOTItemMug.setVessel(copy, getVessel(), true);
		}
		return copy;
	}

	public ItemStack getMugItemForRender() {
		return GOTItemMug.getEquivalentDrink(getMugItem());
	}

	public GOTItemMug.Vessel getVessel() {
		if (mugVessel == null) {
			for (GOTItemMug.Vessel v : GOTItemMug.Vessel.values()) {
				if (!v.canPlace || v.getBlock() != getBlockType()) {
					continue;
				}
				return v;
			}
			return GOTItemMug.Vessel.MUG;
		}
		return mugVessel;
	}

	public boolean isEmpty() {
		return !GOTItemMug.isItemFullDrink(getMugItem());
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readFromNBT(data);
	}

	public void poisonMug(EntityPlayer entityplayer) {
		ItemStack itemstack = getMugItem();
		GOTPoisonedDrinks.setDrinkPoisoned(itemstack, true);
		GOTPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
		setMugItem(itemstack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("ItemID")) {
			Item item = Item.getItemById(nbt.getInteger("ItemID"));
			if (item != null) {
				int damage = nbt.getInteger("ItemDamage");
				mugItem = new ItemStack(item, 1, damage);
			}
		} else {
			boolean hasItem = nbt.getBoolean("HasMugItem");
			mugItem = !hasItem ? null : ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("MugItem"));
		}
		if (nbt.hasKey("Vessel")) {
			mugVessel = GOTItemMug.Vessel.forMeta(nbt.getByte("Vessel"));
		}
	}

	public void setEmpty() {
		setMugItem(null);
	}

	public void setMugItem(ItemStack itemstack) {
		if (itemstack != null && itemstack.stackSize <= 0) {
			itemstack = null;
		}
		mugItem = itemstack;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	public void setVessel(GOTItemMug.Vessel v) {
		mugVessel = v;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && isEmpty() && worldObj.canLightningStrikeAt(xCoord, yCoord, zCoord) && worldObj.rand.nextInt(6000) == 0) {
			ItemStack waterItem = new ItemStack(GOTRegistry.mugWater);
			GOTItemMug.setVessel(waterItem, getVessel(), false);
			setMugItem(waterItem);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("HasMugItem", mugItem != null);
		if (mugItem != null) {
			nbt.setTag("MugItem", mugItem.writeToNBT(new NBTTagCompound()));
		}
		if (mugVessel != null) {
			nbt.setByte("Vessel", (byte) mugVessel.id);
		}
	}
}
