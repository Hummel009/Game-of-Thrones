package got.common.tileentity;

import java.util.Arrays;

import got.common.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class GOTTileEntitySign extends TileEntity {
	private String[] signText = new String[getNumLines()];
	private int lineBeingEdited = -1;
	private boolean editable = true;
	private EntityPlayer editingPlayer;
	private boolean isFakeGuiSign = false;

	public GOTTileEntitySign() {
		Arrays.fill(getSignText(), "");
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound data = new NBTTagCompound();
		writeSignText(data);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, data);
	}

	public EntityPlayer getEditingPlayer() {
		return editingPlayer;
	}

	public int getLineBeingEdited() {
		return lineBeingEdited;
	}

	public abstract int getNumLines();

	public String[] getSignText() {
		return signText;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isFakeGuiSign() {
		return isFakeGuiSign;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readSignText(data);
	}

	public void openEditGUI(EntityPlayerMP entityplayer) {
		setEditingPlayer(entityplayer);
		GOTPacketOpenSignEditor packet = new GOTPacketOpenSignEditor(this);
		GOTPacketHandler.getNetworkWrapper().sendTo(packet, entityplayer);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		editable = false;
		super.readFromNBT(nbt);
		readSignText(nbt);
	}

	private void readSignText(NBTTagCompound nbt) {
		for (int i = 0; i < getSignText().length; ++i) {
			getSignText()[i] = nbt.getString("Text" + (i + 1));
			if (getSignText()[i].length() <= 15) {
				continue;
			}
			getSignText()[i] = getSignText()[i].substring(0, 15);
		}
	}

	public void setEditable(boolean flag) {
		editable = flag;
		if (!flag) {
			editingPlayer = null;
		}
	}

	private void setEditingPlayer(EntityPlayer entityplayer) {
		editingPlayer = entityplayer;
	}

	public void setFakeGuiSign(boolean isFakeGuiSign) {
		this.isFakeGuiSign = isFakeGuiSign;
	}

	public void setLineBeingEdited(int lineBeingEdited) {
		this.lineBeingEdited = lineBeingEdited;
	}

	public void setSignText(String[] signText) {
		this.signText = signText;
	}

	private void writeSignText(NBTTagCompound nbt) {
		for (int i = 0; i < getSignText().length; ++i) {
			nbt.setString("Text" + (i + 1), getSignText()[i]);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeSignText(nbt);
	}
}
