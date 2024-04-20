package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketNPCFX implements IMessage {
	private int entityID;
	private FXType type;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketNPCFX() {
	}

	public GOTPacketNPCFX(int i, FXType t) {
		entityID = i;
		type = t;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		byte typeID = data.readByte();
		type = FXType.values()[typeID];
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeByte(type.ordinal());
	}

	public enum FXType {
		HEARTS, EATING, SMOKE

	}

	public static class Handler implements IMessageHandler<GOTPacketNPCFX, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCFX packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			try {
				Entity entity = world.getEntityByID(packet.entityID);
				if (entity instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					if (packet.type == FXType.HEARTS) {
						npc.spawnHearts();
					} else if (packet.type == FXType.EATING) {
						npc.spawnFoodParticles();
					} else if (packet.type == FXType.SMOKE) {
						npc.spawnSmokes();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}