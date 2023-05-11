package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketPlayerMovement implements IMessage {

	public int entityId;
	public double x;
	public double y;
	public double z;
	public double mx;
	public double my;
	public double mz;

	public GOTPacketPlayerMovement() {
	}

	public GOTPacketPlayerMovement(int entityId, double x, double y, double z, double mx, double my, double mz) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.mx = mx;
		this.my = my;
		this.mz = mz;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			entityId = buf.readInt();
			x = buf.readDouble();
			y = buf.readDouble();
			z = buf.readDouble();
			mx = buf.readDouble();
			my = buf.readDouble();
			mz = buf.readDouble();
		} catch (Exception ignored) {
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeDouble(mx);
		buf.writeDouble(my);
		buf.writeDouble(mz);

	}

	public static class Handler implements IMessageHandler<GOTPacketPlayerMovement, IMessage> {

		@Override
		public IMessage onMessage(GOTPacketPlayerMovement message, MessageContext ctx) {
			new runner(message, ctx).run();

			return null;
		}

		public static class runner implements Runnable {
			GOTPacketPlayerMovement message;
			MessageContext ctx;

			public runner(GOTPacketPlayerMovement message, MessageContext ctx) {
				this.message = message;
				this.ctx = ctx;
			}

			@Override
			public void run() {
				World world = ctx.getServerHandler().playerEntity.worldObj;
				Entity entity = world.getEntityByID(message.entityId);
				entity.posX = message.x;
				entity.posY = message.y;
				entity.posZ = message.z;
				entity.motionX = message.mx;
				entity.motionY = message.my;
				entity.motionZ = message.mz;
			}
		}
	}
}
