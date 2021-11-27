package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import io.netty.buffer.ByteBuf;

public class GOTPacketIsOpResponse implements IMessage {
	public boolean isOp;

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
