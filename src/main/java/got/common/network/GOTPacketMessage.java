package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTGuiMessageTypes;
import io.netty.buffer.ByteBuf;

public class GOTPacketMessage implements IMessage {
	public GOTGuiMessageTypes message;

	public GOTPacketMessage() {
	}

	public GOTPacketMessage(GOTGuiMessageTypes m) {
		message = m;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte messageID = data.readByte();
		if (messageID < 0 || messageID >= GOTGuiMessageTypes.values().length) {
			FMLLog.severe("Failed to display GOT message: There is no message with ID " + messageID);
			message = null;
		} else {
			message = GOTGuiMessageTypes.values()[messageID];
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(message.ordinal());
	}

	public static class Handler implements IMessageHandler<GOTPacketMessage, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMessage packet, MessageContext context) {
			if (packet.message != null) {
				GOT.getProxy().displayMessage(packet.message);
			}
			return null;
		}
	}

}
