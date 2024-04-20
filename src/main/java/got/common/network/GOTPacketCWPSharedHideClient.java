package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GOTPacketCWPSharedHideClient implements IMessage {
	private int cwpID;
	private UUID sharingPlayer;
	private boolean hideCWP;

	@SuppressWarnings({"WeakerAccess", "unused"})
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