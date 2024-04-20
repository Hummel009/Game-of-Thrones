package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTCustomWaypoint;
import got.common.world.map.GOTWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class GOTPacketFTScreen implements IMessage {
	private boolean isCustom;
	private int wpID;
	private UUID sharingPlayer;
	private int startX;
	private int startZ;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketFTScreen() {
	}

	public GOTPacketFTScreen(GOTAbstractWaypoint wp, int x, int z) {
		isCustom = wp instanceof GOTCustomWaypoint;
		wpID = wp.getID();
		if (wp instanceof GOTCustomWaypoint) {
			sharingPlayer = ((GOTCustomWaypoint) wp).getSharingPlayerID();
		}
		startX = x;
		startZ = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isCustom = data.readBoolean();
		wpID = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
		startX = data.readInt();
		startZ = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(isCustom);
		data.writeInt(wpID);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
		data.writeInt(startX);
		data.writeInt(startZ);
	}

	public static class Handler implements IMessageHandler<GOTPacketFTScreen, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFTScreen packet, MessageContext context) {
			boolean custom = packet.isCustom;
			int wpID = packet.wpID;
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			GOTAbstractWaypoint waypoint = null;
			if (custom) {
				UUID sharingPlayerID = packet.sharingPlayer;
				waypoint = sharingPlayerID != null ? playerData.getSharedCustomWaypointByID(sharingPlayerID, wpID) : playerData.getCustomWaypointByID(wpID);
			} else if (wpID >= 0 && wpID < GOTWaypoint.values().length) {
				waypoint = GOTWaypoint.values()[wpID];
			}
			if (waypoint != null) {
				GOT.proxy.displayFTScreen(waypoint, packet.startX, packet.startZ);
			}
			return null;
		}
	}
}