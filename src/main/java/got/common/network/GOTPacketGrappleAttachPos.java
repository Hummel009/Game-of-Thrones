package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.entity.other.GOTEntityGrapplingArrow;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketGrappleAttachPos implements IMessage {

	public int id;
	public double x;
	public double y;
	public double z;

	public GOTPacketGrappleAttachPos() {
	}

	public GOTPacketGrappleAttachPos(int id, double x, double y, double z) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}

	public static class Handler implements IMessageHandler<GOTPacketGrappleAttachPos, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketGrappleAttachPos message, MessageContext ctx) {

			new runner(message, ctx).run();

			return null;
		}

		public static class runner implements Runnable {
			GOTPacketGrappleAttachPos message;
			MessageContext ctx;

			public runner(GOTPacketGrappleAttachPos message, MessageContext ctx) {
				this.message = message;
				this.ctx = ctx;
			}

			@Override
			public void run() {
				World world = Minecraft.getMinecraft().theWorld;
				Entity grapple = world.getEntityByID(message.id);
				if (grapple instanceof GOTEntityGrapplingArrow) {
					((GOTEntityGrapplingArrow) grapple).setAttachPos(message.x, message.y, message.z);
				}
			}
		}
	}
}
