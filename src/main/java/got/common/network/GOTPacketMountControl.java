package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTNetHandlerPlayServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class GOTPacketMountControl implements IMessage {
	private double posX;
	private double posY;
	private double posZ;
	private float rotationYaw;
	private float rotationPitch;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketMountControl() {
	}

	public GOTPacketMountControl(Entity entity) {
		posX = entity.posX;
		posY = entity.posY;
		posZ = entity.posZ;
		rotationYaw = entity.rotationYaw;
		rotationPitch = entity.rotationPitch;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();
		rotationYaw = data.readFloat();
		rotationPitch = data.readFloat();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeDouble(posX);
		data.writeDouble(posY);
		data.writeDouble(posZ);
		data.writeFloat(rotationYaw);
		data.writeFloat(rotationPitch);
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getPosZ() {
		return posZ;
	}

	public float getRotationYaw() {
		return rotationYaw;
	}

	public float getRotationPitch() {
		return rotationPitch;
	}

	public static class Handler implements IMessageHandler<GOTPacketMountControl, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMountControl packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			NetHandlerPlayServer handler = entityplayer.playerNetServerHandler;
			if (handler instanceof GOTNetHandlerPlayServer) {
				((GOTNetHandlerPlayServer) handler).processMountControl(packet);
			}
			return null;
		}
	}
}