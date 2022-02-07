package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketDeleteCWP implements IMessage {
	private int wpID;

	public GOTPacketDeleteCWP(GOTCustomWaypoint wp) {
		wpID = wp.getID();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		wpID = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(wpID);
	}

	public static class Handler implements IMessageHandler<GOTPacketDeleteCWP, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketDeleteCWP packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
			if (cwp != null) {
				pd.removeCustomWaypoint(cwp);
			}
			return null;
		}
	}

}
