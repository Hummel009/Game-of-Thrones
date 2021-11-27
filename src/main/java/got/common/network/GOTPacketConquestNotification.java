package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;

public class GOTPacketConquestNotification implements IMessage {
	public GOTFaction conqFac;
	public float conqVal;
	public boolean isCleansing;

	public GOTPacketConquestNotification() {
	}

	public GOTPacketConquestNotification(GOTFaction fac, float f, boolean clean) {
		conqFac = fac;
		conqVal = f;
		isCleansing = clean;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		conqFac = GOTFaction.forID(facID);
		conqVal = data.readFloat();
		isCleansing = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(conqFac.ordinal());
		data.writeFloat(conqVal);
		data.writeBoolean(isCleansing);
	}

	public static class Handler implements IMessageHandler<GOTPacketConquestNotification, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketConquestNotification packet, MessageContext context) {
			if (packet.conqFac != null) {
				GOT.proxy.queueConquestNotification(packet.conqFac, packet.conqVal, packet.isCleansing);
			}
			return null;
		}
	}

}
