package got.common.tileentity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.other.GOTBlockSignCarved;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketOpenSignEditor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Arrays;

public class GOTTileEntitySignCarved extends TileEntity {
	private final String[] signText = new String[8];

	private EntityPlayer editingPlayer;

	private int lineBeingEdited = -1;
	private boolean isFakeGuiSign;
	private boolean editable = true;

	protected GOTTileEntitySignCarved() {
		Arrays.fill(signText, "");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 1600.0;
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

	@SuppressWarnings("StringConcatenationMissingWhitespace")
	private void readSignText(NBTTagCompound nbt) {
		for (int i = 0; i < signText.length; ++i) {
			signText[i] = nbt.getString("Text" + (i + 1));
			if (signText[i].length() <= 15) {
				continue;
			}
			signText[i] = signText[i].substring(0, 15);
		}
	}

	@SuppressWarnings("StringConcatenationMissingWhitespace")
	private void writeSignText(NBTTagCompound nbt) {
		for (int i = 0; i < signText.length; ++i) {
			nbt.setString("Text" + (i + 1), signText[i]);
		}
	}

	public IIcon getOnBlockIcon() {
		World world = getWorldObj();
		Block block = getBlockType();
		if (block instanceof GOTBlockSignCarved) {
			GOTBlockSignCarved signBlock = (GOTBlockSignCarved) block;
			int meta = getBlockMetadata();
			int i = xCoord;
			int j = yCoord;
			int k = zCoord;
			return signBlock.getOnBlockIcon(world, i, j, k, meta);
		}
		return Blocks.stone.getIcon(0, 0);
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