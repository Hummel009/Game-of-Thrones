package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.entity.other.GOTEntityGrapplingArrow;
import got.common.util.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketGrappleAttach implements cpw.mods.fml.common.network.simpleimpl.IMessage {

	public int id;
	public double x;
	public double y;
	public double z;
	public int controlid;
	public int entityid;
	public int maxlen;
	public GOTBlockPos blockpos;

	public GOTPacketGrappleAttach() {
	}

	public GOTPacketGrappleAttach(int id, double x, double y, double z, int controlid, int entityid, int maxlen, GOTBlockPos blockpos) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.controlid = controlid;
		this.entityid = entityid;
		this.maxlen = maxlen;
		this.blockpos = blockpos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		controlid = buf.readInt();
		entityid = buf.readInt();
		maxlen = buf.readInt();
		int blockx = buf.readInt();
		int blocky = buf.readInt();
		int blockz = buf.readInt();
		blockpos = new GOTBlockPos(blockx, blocky, blockz);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(controlid);
		buf.writeInt(entityid);
		buf.writeInt(maxlen);
		buf.writeInt(blockpos.getX());
		buf.writeInt(blockpos.getY());
		buf.writeInt(blockpos.getZ());
	}

	public static class Handler implements IMessageHandler<GOTPacketGrappleAttach, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketGrappleAttach message, MessageContext ctx) {

			new runner(message, ctx).run();

			return null;
		}

		public static class runner implements Runnable {
			GOTPacketGrappleAttach message;
			MessageContext ctx;

			public runner(GOTPacketGrappleAttach message, MessageContext ctx) {
				this.message = message;
				this.ctx = ctx;
			}

			@Override
			public void run() {
				World world = Minecraft.getMinecraft().theWorld;
				Entity grapple = world.getEntityByID(message.id);
				if (grapple instanceof GOTEntityGrapplingArrow) {
					((GOTEntityGrapplingArrow) grapple).clientAttach(message.x, message.y, message.z);
				} else {
				}

				GOTGrappleHelper.createControl(message.controlid, message.id, message.entityid, world, new GOTVec(message.x, message.y, message.z), message.maxlen, message.blockpos);
			}
		}
	}
}
