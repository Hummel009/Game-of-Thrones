package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketClientsideGUI implements IMessage {
	private int guiID;
	private int guiX;
	private int guiY;
	private int guiZ;

	@SuppressWarnings("unused")
	public GOTPacketClientsideGUI() {
	}

	public GOTPacketClientsideGUI(int id, int x, int y, int z) {
		guiID = id;
		guiX = x;
		guiY = y;
		guiZ = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		guiID = data.readInt();
		guiX = data.readInt();
		guiY = data.readInt();
		guiZ = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(guiID);
		data.writeInt(guiX);
		data.writeInt(guiY);
		data.writeInt(guiZ);
	}

	public static class Handler implements IMessageHandler<GOTPacketClientsideGUI, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketClientsideGUI packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			entityplayer.openGui(GOT.instance, packet.guiID, entityplayer.worldObj, packet.guiX, packet.guiY, packet.guiZ);
			return null;
		}
	}
}