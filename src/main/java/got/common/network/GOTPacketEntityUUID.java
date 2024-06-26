package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.entity.other.iface.GOTRandomSkinEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.UUID;

public class GOTPacketEntityUUID implements IMessage {
	private int entityID;
	private UUID entityUUID;

	@SuppressWarnings("unused")
	public GOTPacketEntityUUID() {
	}

	public GOTPacketEntityUUID(int id, UUID uuid) {
		entityID = id;
		entityUUID = uuid;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		entityUUID = new UUID(data.readLong(), data.readLong());
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeLong(entityUUID.getMostSignificantBits());
		data.writeLong(entityUUID.getLeastSignificantBits());
	}

	public static class Handler implements IMessageHandler<GOTPacketEntityUUID, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEntityUUID packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTRandomSkinEntity) {
				GOTRandomSkinEntity npc = (GOTRandomSkinEntity) entity;
				npc.setUniqueID(packet.entityUUID);
			}
			return null;
		}
	}
}