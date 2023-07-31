package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.world.map.GOTAbstractWaypoint;
import got.common.world.map.GOTCustomWaypoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketRenameCWP implements IMessage {
	public int wpID;
	public String name;

	public GOTPacketRenameCWP() {
	}

	public GOTPacketRenameCWP(GOTAbstractWaypoint wp, String s) {
		wpID = wp.getID();
		name = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		wpID = data.readInt();
		short length = data.readShort();
		name = data.readBytes(length).toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(wpID);
		byte[] nameBytes = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<GOTPacketRenameCWP, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketRenameCWP packet, MessageContext context) {
			String wpName;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTCustomWaypoint cwp = pd.getCustomWaypointByID(packet.wpID);
			if (cwp != null && (wpName = GOTCustomWaypoint.validateCustomName(packet.name)) != null) {
				pd.renameCustomWaypoint(cwp, wpName);
			}
			return null;
		}
	}

}
