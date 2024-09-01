package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhoodClient;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GOTPacketShareCWPClient implements IMessage {
	private int cwpID;
	private UUID brotherhoodID;
	private boolean adding;

	@SuppressWarnings("unused")
	public GOTPacketShareCWPClient() {
	}

	public GOTPacketShareCWPClient(int id, UUID fsID, boolean add) {
		cwpID = id;
		brotherhoodID = fsID;
		adding = add;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		brotherhoodID = new UUID(data.readLong(), data.readLong());
		adding = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		data.writeLong(brotherhoodID.getMostSignificantBits());
		data.writeLong(brotherhoodID.getLeastSignificantBits());
		data.writeBoolean(adding);
	}

	public static class Handler implements IMessageHandler<GOTPacketShareCWPClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketShareCWPClient packet, MessageContext context) {
			GOTCustomWaypoint cwp;
			GOTPlayerData pd;
			if (!GOT.proxy.isSingleplayer() && (cwp = (pd = GOTLevelData.getData(GOT.proxy.getClientPlayer())).getCustomWaypointByID(packet.cwpID)) != null) {
				if (packet.adding) {
					GOTBrotherhoodClient fsClient = pd.getClientBrotherhoodByID(packet.brotherhoodID);
					if (fsClient != null) {
						GOTPlayerData.customWaypointAddSharedBrotherhoodClient(cwp, fsClient);
					}
				} else {
					GOTPlayerData.customWaypointRemoveSharedBrotherhoodClient(cwp, packet.brotherhoodID);
				}
			}
			return null;
		}
	}
}