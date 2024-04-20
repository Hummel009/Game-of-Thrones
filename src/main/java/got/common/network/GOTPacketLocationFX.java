package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.client.effect.GOTEntitySwordCommandMarker;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class GOTPacketLocationFX implements IMessage {
	private Type type;
	private double posX;
	private double posY;
	private double posZ;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketLocationFX() {
	}

	public GOTPacketLocationFX(Type t, double x, double y, double z) {
		type = t;
		posX = x;
		posY = y;
		posZ = z;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int typeID = data.readByte();
		type = Type.values()[typeID];
		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
		data.writeDouble(posX);
		data.writeDouble(posY);
		data.writeDouble(posZ);
	}

	public enum Type {
		SWORD_COMMAND
	}

	public static class Handler implements IMessageHandler<GOTPacketLocationFX, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketLocationFX packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			double x = packet.posX;
			double y = packet.posY;
			double z = packet.posZ;
			if (packet.type == GOTPacketLocationFX.Type.SWORD_COMMAND) {
				GOTEntitySwordCommandMarker marker = new GOTEntitySwordCommandMarker(world, x, y + 6.0D, z);
				world.spawnEntityInWorld(marker);
			}
			return null;
		}
	}
}