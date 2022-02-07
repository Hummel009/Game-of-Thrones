package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.world.map.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

public class GOTPacketFastTravel implements IMessage {
	public boolean isCustom;
	public int wpID;
	public UUID sharingPlayer;

	public GOTPacketFastTravel(GOTAbstractWaypoint wp) {
		isCustom = wp instanceof GOTCustomWaypoint;
		wpID = wp.getID();
		if (wp instanceof GOTCustomWaypoint) {
			sharingPlayer = ((GOTCustomWaypoint) wp).getSharingPlayerID();
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isCustom = data.readBoolean();
		wpID = data.readInt();
		boolean shared = data.readBoolean();
		if (shared) {
			sharingPlayer = new UUID(data.readLong(), data.readLong());
		}
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
	}

	public static class Handler implements IMessageHandler<GOTPacketFastTravel, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFastTravel packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			if (!GOTConfig.isEnableFastTravel()) {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.ftDisabled"));
			} else {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				boolean isCustom = packet.isCustom;
				int waypointID = packet.wpID;
				GOTAbstractWaypoint waypoint = null;
				if (!isCustom) {
					if (waypointID >= 0 && waypointID < GOTWaypoint.values().length) {
						waypoint = GOTWaypoint.values()[waypointID];
					}
				} else {
					UUID sharingPlayer = packet.sharingPlayer;
					waypoint = sharingPlayer != null ? playerData.getSharedCustomWaypointByID(sharingPlayer, waypointID) : playerData.getCustomWaypointByID(waypointID);
				}
				if (waypoint != null && waypoint.hasPlayerUnlocked(entityplayer)) {
					if (playerData.getTimeSinceFT() < playerData.getWaypointFTTime(waypoint, entityplayer)) {
						entityplayer.closeScreen();
						entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.moreTime", waypoint.getDisplayName()));
					} else {
						boolean canTravel = playerData.canFastTravel();
						if (!canTravel) {
							entityplayer.closeScreen();
							entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.underAttack"));
						} else if (entityplayer.isPlayerSleeping()) {
							entityplayer.closeScreen();
							entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.inBed"));
						} else {
							playerData.setTargetFTWaypoint(waypoint);
						}
					}
				}
			}
			return null;
		}
	}

}
