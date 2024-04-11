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

public class GOTPacketWaypointUseCount implements IMessage {
	private boolean isCustom;
	private int wpID;
	private int useCount;
	private UUID sharingPlayer;

	@SuppressWarnings("unused")
	public GOTPacketWaypointUseCount() {
	}

	public GOTPacketWaypointUseCount(GOTAbstractWaypoint wp, int count) {
		isCustom = wp instanceof GOTCustomWaypoint;
		wpID = wp.getID();
		useCount = count;
		if (wp instanceof GOTCustomWaypoint) {
			sharingPlayer = ((GOTCustomWaypoint) wp).getSharingPlayerID();
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isCustom = data.readBoolean();
		wpID = data.readInt();
		useCount = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(isCustom);
		data.writeInt(wpID);
		data.writeInt(useCount);
		boolean shared = sharingPlayer != null;
		data.writeBoolean(shared);
		if (shared) {
			data.writeLong(sharingPlayer.getMostSignificantBits());
			data.writeLong(sharingPlayer.getLeastSignificantBits());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketWaypointUseCount, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketWaypointUseCount packet, MessageContext context) {
			boolean custom = packet.isCustom;
			int wpID = packet.wpID;
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTAbstractWaypoint waypoint = null;
			if (custom) {
				UUID sharingPlayerID = packet.sharingPlayer;
				waypoint = sharingPlayerID != null ? pd.getSharedCustomWaypointByID(sharingPlayerID, wpID) : pd.getCustomWaypointByID(wpID);
			} else if (wpID >= 0 && wpID < GOTWaypoint.values().length) {
				waypoint = GOTWaypoint.values()[wpID];
			}
			if (waypoint != null) {
				pd.setWPUseCount(waypoint, packet.useCount);
			}
			return null;
		}
	}
}