package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTCustomWaypoint;
import got.common.world.map.GOTWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;

import java.util.UUID;

public class GOTPacketFastTravel implements IMessage {
	private boolean isCustom;
	private int wpID;
	private UUID sharingPlayer;

	@SuppressWarnings("unused")
	public GOTPacketFastTravel() {
	}

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
			if (GOTConfig.enableFastTravel) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				boolean isCustom = packet.isCustom;
				int waypointID = packet.wpID;
				GOTAbstractWaypoint waypoint = null;
				if (isCustom) {
					UUID sharingPlayer = packet.sharingPlayer;
					waypoint = sharingPlayer != null ? playerData.getSharedCustomWaypointByID(sharingPlayer, waypointID) : playerData.getCustomWaypointByID(waypointID);
				} else if (waypointID >= 0 && waypointID < GOTWaypoint.values().length) {
					waypoint = GOTWaypoint.values()[waypointID];
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
			} else {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.ftDisabled"));
			}
			return null;
		}
	}
}