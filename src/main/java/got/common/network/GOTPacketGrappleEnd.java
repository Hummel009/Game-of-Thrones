package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.util.GOTGrappleHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class GOTPacketGrappleEnd implements IMessage {

	public int entityid;
	public int arrowid;

	public GOTPacketGrappleEnd() {
	}

	public GOTPacketGrappleEnd(int entityid, int arrowid) {
		this.entityid = entityid;
		this.arrowid = arrowid;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityid = buf.readInt();
		arrowid = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityid);
		buf.writeInt(arrowid);
	}

	public static class Handler implements IMessageHandler<GOTPacketGrappleEnd, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketGrappleEnd message, MessageContext ctx) {

			new runner(message, ctx).run();

			return null;
		}

		public static class runner implements Runnable {
			GOTPacketGrappleEnd message;
			MessageContext ctx;

			public runner(GOTPacketGrappleEnd message, MessageContext ctx) {
				this.message = message;
				this.ctx = ctx;
			}

			@Override
			public void run() {

				int id = message.entityid;

				World w = ctx.getServerHandler().playerEntity.worldObj;

				GOTGrappleHelper.receiveGrappleEnd(id, w, message.arrowid);
			}
		}
	}
}
