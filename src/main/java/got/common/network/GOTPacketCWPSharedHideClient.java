package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;

public class GOTPacketCWPSharedHideClient implements IMessage {
	public int cwpID;
	public UUID sharingPlayer;
	public boolean hideCWP;

	public GOTPacketCWPSharedHideClient() {
	}

	public GOTPacketCWPSharedHideClient(int id, UUID player, boolean hide) {
		cwpID = id;
		sharingPlayer = player;
		hideCWP = hide;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		sharingPlayer = new UUID(data.readLong(), data.readLong());
		hideCWP = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		data.writeLong(sharingPlayer.getMostSignificantBits());
		data.writeLong(sharingPlayer.getLeastSignificantBits());
		data.writeBoolean(hideCWP);
	}

	public static class Handler implements IMessageHandler<GOTPacketCWPSharedHideClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCWPSharedHideClient packet, MessageContext context) {
			GOTCustomWaypoint cwp;
			GOTPlayerData pd;
			if (!GOT.proxy.isSingleplayer() && (cwp = (pd = GOTLevelData.getData(GOT.proxy.getClientPlayer())).getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID)) != null) {
				pd.hideOrUnhideSharedCustomWaypoint(cwp, packet.hideCWP);
			}
			return null;
		}
	}

}
