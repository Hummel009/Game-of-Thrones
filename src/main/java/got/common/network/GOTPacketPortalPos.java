package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;

public class GOTPacketPortalPos implements IMessage {
	public int portalX;
	public int portalY;
	public int portalZ;

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
