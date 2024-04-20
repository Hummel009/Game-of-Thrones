package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class GOTPacketCWPSharedHide implements IMessage {
	private int cwpID;
	private UUID sharingPlayer;
	private boolean hideCWP;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketCWPSharedHide() {
	}

	public GOTPacketCWPSharedHide(GOTCustomWaypoint cwp, boolean hide) {
		cwpID = cwp.getID();
		sharingPlayer = cwp.getSharingPlayerID();
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

	public static class Handler implements IMessageHandler<GOTPacketCWPSharedHide, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCWPSharedHide packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTCustomWaypoint cwp = pd.getSharedCustomWaypointByID(packet.sharingPlayer, packet.cwpID);
			if (cwp != null) {
				pd.hideOrUnhideSharedCustomWaypoint(cwp, packet.hideCWP);
			}
			return null;
		}
	}
}