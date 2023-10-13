package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;

public class GOTPacketMenuPrompt implements IMessage {
	public Type type;

	public GOTPacketMenuPrompt() {
	}

	public GOTPacketMenuPrompt(Type t) {
		type = t;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		type = Type.values()[typeID];
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
	}

	public static class Handler implements IMessageHandler<GOTPacketMenuPrompt, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMenuPrompt packet, MessageContext context) {
			GOT.proxy.displayMenuPrompt(packet.type);
			return null;
		}
	}

	public enum Type {
		MENU
	}
}
