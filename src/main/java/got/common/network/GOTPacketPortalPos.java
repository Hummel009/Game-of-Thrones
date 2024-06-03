package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;

public class GOTPacketPortalPos implements IMessage {
	private int portalX;
	private int portalY;
	private int portalZ;

	@SuppressWarnings("unused")
	public GOTPacketPortalPos() {
	}

	public GOTPacketPortalPos(int i, int j, int k) {
		portalX = i;
		portalY = j;
		portalZ = k;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		portalX = data.readInt();
		portalY = data.readInt();
		portalZ = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(portalX);
		data.writeInt(portalY);
		data.writeInt(portalZ);
	}

	public static class Handler implements IMessageHandler<GOTPacketPortalPos, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketPortalPos packet, MessageContext context) {
			GOTLevelData.setGameOfThronesPortalX(packet.portalX);
			GOTLevelData.setGameOfThronesPortalY(packet.portalY);
			GOTLevelData.setGameOfThronesPortalZ(packet.portalZ);
			return null;
		}
	}
}