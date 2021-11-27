package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.fellowship.GOTFellowship;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketShareCWP implements IMessage {
	public int wpID;
	public String fsName;
	public boolean adding;

	public GOTPacketShareCWP() {
	}

	public GOTPacketShareCWP(GOTCustomWaypoint wp, String s, boolean add) {
		wpID = wp.getID();
		fsName = s;
		adding = add;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		wpID = data.readInt();
		short length = data.readShort();
		fsName = data.readBytes(length).toString(Charsets.UTF_8);
		adding = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(wpID);
		byte[] nameBytes = fsName.getBytes(Charsets.UTF_8);
		data.writeShort(nameBytes.length);
		data.writeBytes(nameBytes);
		data.writeBoolean(adding);
	}

	public static class Handler implements IMessageHandler<GOTPacketShareCWP, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketShareCWP packet, MessageContext context) {
			GOTFellowship fellowship;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
			if (cwp != null && (fellowship = pd.getFellowshipByName(packet.fsName)) != null) {
				if (packet.adding) {
					pd.customWaypointAddSharedFellowship(cwp, fellowship);
				} else {
					pd.customWaypointRemoveSharedFellowship(cwp, fellowship);
				}
			}
			return null;
		}
	}

}
