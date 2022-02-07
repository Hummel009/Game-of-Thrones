package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
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
			GOT.getProxy().cancelItemHighlight();
			return null;
		}
	}

}
