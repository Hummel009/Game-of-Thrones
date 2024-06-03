package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketNPCIsEating implements IMessage {
	private int entityID;
	private boolean isEating;

	@SuppressWarnings("unused")
	public GOTPacketNPCIsEating() {
	}

	public GOTPacketNPCIsEating(int id, boolean flag) {
		entityID = id;
		isEating = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		isEating = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(isEating);
	}

	public static class Handler implements IMessageHandler<GOTPacketNPCIsEating, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCIsEating packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).setClientIsEating(packet.isEating);
			}
			return null;
		}
	}
}