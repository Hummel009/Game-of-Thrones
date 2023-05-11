package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;

public class GOTPacketFTBounceClient implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketFTBounceClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFTBounceClient packet, MessageContext context) {
			GOT.proxy.getClientPlayer();
			GOTPacketFTBounceServer packetResponse = new GOTPacketFTBounceServer();
			GOTPacketHandler.networkWrapper.sendToServer(packetResponse);
			return null;
		}
	}

}
