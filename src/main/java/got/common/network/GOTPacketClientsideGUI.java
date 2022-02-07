package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketClientsideGUI implements IMessage {
	public int guiID;
	public int guiX;
	public int guiY;
	public int guiZ;

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
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			entityplayer.openGui(GOT.getInstance(), packet.guiID, entityplayer.worldObj, packet.guiX, packet.guiY, packet.guiZ);
			return null;
		}
	}

}
