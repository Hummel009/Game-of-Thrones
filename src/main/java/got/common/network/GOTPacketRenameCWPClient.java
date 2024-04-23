package got.common.network;

import com.google.common.base.Charsets;
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

public class GOTPacketRenameCWPClient implements IMessage {
	private int cwpID;
	private String name;
	private UUID sharingPlayer;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketRenameCWPClient() {
	}

	public GOTPacketRenameCWPClient(int id, String s) {
		cwpID = id;
		name = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cwpID = data.readInt();
		short length = data.readShort();
		name = data.readBytes(length).toString(Charsets.UTF_8);
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cwpID);
		byte[] nameBytes = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameBytes.length);
		data.writeBytes(nameBytes);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public UUID getSharingPlayer() {
		return sharingPlayer;
	}

	public GOTPacketRenameCWPClient setSharingPlayer(UUID player) {
		sharingPlayer = player;
		return this;
	}

	public static class Handler implements IMessageHandler<GOTPacketRenameCWPClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketRenameCWPClient packet, MessageContext context) {
			GOTCustomWaypoint cwp;
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (packet.sharingPlayer != null) {
				GOTCustomWaypoint cwp2;
				if (!GOT.proxy.isSingleplayer() && (cwp2 = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID)) != null) {
					pd.renameSharedCustomWaypoint(cwp2, packet.name);
				}
			} else if (!GOT.proxy.isSingleplayer() && (cwp = pd.getCustomWaypointByID(packet.cwpID)) != null) {
				GOTPlayerData.renameCustomWaypointClient(cwp, packet.name);
			}
			return null;
		}
	}
}