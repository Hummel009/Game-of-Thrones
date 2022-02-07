package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.fellowship.GOTFellowshipClient;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;

public class GOTPacketShareCWPClient implements IMessage {
	public int cwpID;
	public UUID fellowshipID;
	public boolean adding;

	public GOTPacketShareCWPClient(int id, UUID fsID, boolean add) {
		cwpID = id;
		fellowshipID = fsID;
		adding = add;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		fellowshipID = new UUID(data.readLong(), data.readLong());
		adding = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
		data.writeBoolean(adding);
	}

	public static class Handler implements IMessageHandler<GOTPacketShareCWPClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketShareCWPClient packet, MessageContext context) {
			GOTCustomWaypoint cwp;
			GOTPlayerData pd;
			if (!GOT.getProxy().isSingleplayer() && (cwp = (pd = GOTLevelData.getData(GOT.getProxy().getClientPlayer())).getCustomWaypointByID(packet.cwpID)) != null) {
				if (packet.adding) {
					GOTFellowshipClient fsClient = pd.getClientFellowshipByID(packet.fellowshipID);
					if (fsClient != null) {
						pd.customWaypointAddSharedFellowshipClient(cwp, fsClient);
					}
				} else {
					pd.customWaypointRemoveSharedFellowshipClient(cwp, packet.fellowshipID);
				}
			}
			return null;
		}
	}

}
