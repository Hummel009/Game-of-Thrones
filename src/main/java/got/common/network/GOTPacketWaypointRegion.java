package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.world.map.GOTWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketWaypointRegion implements IMessage {
	private GOTWaypoint.Region region;
	private boolean unlocking;

	public GOTPacketWaypointRegion(GOTWaypoint.Region r, boolean flag) {
		region = r;
		unlocking = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		region = GOTWaypoint.regionForID(data.readByte());
		unlocking = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(region.ordinal());
		data.writeBoolean(unlocking);
	}

	public static class Handler implements IMessageHandler<GOTPacketWaypointRegion, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketWaypointRegion packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTWaypoint.Region region = packet.region;
			if (region != null) {
				if (packet.unlocking) {
					pd.unlockFTRegion(region);
				} else {
					pd.lockFTRegion(region);
				}
			}
			return null;
		}
	}

}
