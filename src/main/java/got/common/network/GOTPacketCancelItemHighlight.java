package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;

public class GOTPacketCancelItemHighlight implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketCancelItemHighlight, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCancelItemHighlight packet, MessageContext context) {
			GOT.proxy.cancelItemHighlight();
			return null;
		}
	}

}
