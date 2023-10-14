package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class GOTPacketDeleteCWPClient implements IMessage {
	public int cwpID;
	public UUID sharingPlayer;

	public GOTPacketDeleteCWPClient() {
	}

	public GOTPacketDeleteCWPClient(int id) {
		cwpID = id;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
	}

	public GOTPacketDeleteCWPClient setSharingPlayer(UUID player) {
		sharingPlayer = player;
		return this;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketDeleteCWPClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketDeleteCWPClient packet, MessageContext context) {
			GOTCustomWaypoint cwp;
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (packet.sharingPlayer != null) {
				GOTCustomWaypoint cwp2;
				if (!GOT.proxy.isSingleplayer() && (cwp2 = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID)) != null) {
					pd.removeSharedCustomWaypoint(cwp2);
				}
			} else if (!GOT.proxy.isSingleplayer() && (cwp = pd.getCustomWaypointByID(packet.cwpID)) != null) {
				pd.removeCustomWaypointClient(cwp);
			}
			return null;
		}
	}

}
