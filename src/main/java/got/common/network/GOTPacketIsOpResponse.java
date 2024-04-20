package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;

public class GOTPacketIsOpResponse implements IMessage {
	private boolean isOp;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketIsOpResponse() {
	}

	public GOTPacketIsOpResponse(boolean flag) {
		isOp = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		isOp = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(isOp);
	}

	public static class Handler implements IMessageHandler<GOTPacketIsOpResponse, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketIsOpResponse packet, MessageContext context) {
			GOT.proxy.setMapIsOp(packet.isOp);
			return null;
		}
	}
}