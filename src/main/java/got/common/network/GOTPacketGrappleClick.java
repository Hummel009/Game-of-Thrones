package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.util.GOTGrappleHelper;
import io.netty.buffer.ByteBuf;

public class GOTPacketGrappleClick implements IMessage {

	public int id;
	public boolean leftclick;

	public GOTPacketGrappleClick() {
	}

	public GOTPacketGrappleClick(int id, boolean leftclick) {
		this.id = id;
		this.leftclick = leftclick;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		leftclick = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeBoolean(leftclick);
	}

	public static class Handler implements IMessageHandler<GOTPacketGrappleClick, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketGrappleClick message, MessageContext ctx) {

			new runner(message, ctx).run();

			return null;
		}

		public static class runner implements Runnable {
			GOTPacketGrappleClick message;
			MessageContext ctx;

			public runner(GOTPacketGrappleClick message, MessageContext ctx) {
				this.message = message;
				this.ctx = ctx;
			}

			@Override
			public void run() {
				GOTGrappleHelper.receiveGrappleClick(message.id, message.leftclick);
			}
		}
	}
}
