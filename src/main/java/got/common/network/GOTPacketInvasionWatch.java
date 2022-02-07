package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.GOTEntityInvasionSpawner;
import io.netty.buffer.ByteBuf;

public class GOTPacketInvasionWatch implements IMessage {
	public int invasionEntityID;
	public boolean overrideAlreadyWatched;

	public GOTPacketInvasionWatch(GOTEntityInvasionSpawner invasion, boolean override) {
		invasionEntityID = invasion.getEntityId();
		overrideAlreadyWatched = override;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		invasionEntityID = data.readInt();
		overrideAlreadyWatched = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(invasionEntityID);
		data.writeBoolean(overrideAlreadyWatched);
	}

	public static class Handler implements IMessageHandler<GOTPacketInvasionWatch, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketInvasionWatch packet, MessageContext context) {
			GOT.getProxy().handleInvasionWatch(packet.invasionEntityID, packet.overrideAlreadyWatched);
			return null;
		}
	}

}
