package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;

public class GOTPacketCWPSharedUnlockClient implements IMessage {
	public int cwpID;
	public UUID sharingPlayer;

	public GOTPacketCWPSharedUnlockClient() {
	}

	public GOTPacketCWPSharedUnlockClient(int id, UUID player) {
		cwpID = id;
		sharingPlayer = player;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		sharingPlayer = new UUID(data.readLong(), data.readLong());
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		data.writeLong(sharingPlayer.getMostSignificantBits());
		data.writeLong(sharingPlayer.getLeastSignificantBits());
	}

	public static class Handler implements IMessageHandler<GOTPacketCWPSharedUnlockClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCWPSharedUnlockClient packet, MessageContext context) {
			GOTCustomWaypoint cwp;
			GOTPlayerData pd;
			if (!GOT.proxy.isSingleplayer() && (cwp = (pd = GOTLevelData.getData(GOT.proxy.getClientPlayer())).getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID)) != null) {
				pd.unlockSharedCustomWaypoint(cwp);
			}
			return null;
		}
	}

}
