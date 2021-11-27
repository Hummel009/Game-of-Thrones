package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.world.map.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketWaypointUseCount implements IMessage {
	public boolean isCustom;
	public int wpID;
	public int useCount;
	public UUID sharingPlayer;

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
			if (!custom) {
				if (wpID >= 0 && wpID < GOTWaypoint.values().length) {
					waypoint = GOTWaypoint.values()[wpID];
				}
			} else {
				UUID sharingPlayerID = packet.sharingPlayer;
				waypoint = sharingPlayerID != null ? pd.getSharedCustomWaypointByID(sharingPlayerID, wpID) : pd.getCustomWaypointByID(wpID);
			}
			if (waypoint != null) {
				pd.setWPUseCount(waypoint, packet.useCount);
			}
			return null;
		}
	}

}
