package got.common.tileentity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketOpenSignEditor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.Arrays;

public abstract class GOTTileEntitySign extends TileEntity {
	private final String[] signText = new String[getNumLines()];

	private EntityPlayer editingPlayer;

	private int lineBeingEdited = -1;
	private boolean isFakeGuiSign;
	private boolean editable = true;

	protected GOTTileEntitySign() {
		Arrays.fill(signText, "");
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

	public abstract int getNumLines();

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean flag) {
		editable = flag;
		if (!flag) {
			editingPlayer = null;
		}
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		NBTTagCompound data = packet.func_148857_g();
		readSignText(data);
	}

	public void openEditGUI(EntityPlayerMP entityplayer) {
		editingPlayer = entityplayer;
		IMessage packet = new GOTPacketOpenSignEditor(this);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		editable = false;
		super.readFromNBT(nbt);
		readSignText(nbt);
	}

	private void readSignText(NBTTagCompound nbt) {
		for (int i = 0; i < signText.length; ++i) {
			signText[i] = nbt.getString("Text" + (i + 1));
			if (signText[i].length() <= 15) {
				continue;
			}
			signText[i] = signText[i].substring(0, 15);
		}
	}

	private void writeSignText(NBTTagCompound nbt) {
		for (int i = 0; i < signText.length; ++i) {
			nbt.setString("Text" + (i + 1), signText[i]);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeSignText(nbt);
	}

	public String[] getSignText() {
		return signText;
	}

	public int getLineBeingEdited() {
		return lineBeingEdited;
	}

	public void setLineBeingEdited(int lineBeingEdited) {
		this.lineBeingEdited = lineBeingEdited;
	}

	protected boolean isFakeGuiSign() {
		return isFakeGuiSign;
	}

	public void setFakeGuiSign(boolean fakeGuiSign) {
		isFakeGuiSign = fakeGuiSign;
	}
}