package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
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

	public GOTPacketMountControl(Entity entity) {
		setPosX(entity.posX);
		setPosY(entity.posY);
		setPosZ(entity.posZ);
		setRotationYaw(entity.rotationYaw);
		setRotationPitch(entity.rotationPitch);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		setPosX(data.readDouble());
		setPosY(data.readDouble());
		setPosZ(data.readDouble());
		setRotationYaw(data.readFloat());
		setRotationPitch(data.readFloat());
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

	public float getRotationPitch() {
		return rotationPitch;
	}

	public float getRotationYaw() {
		return rotationYaw;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}

	public void setRotationPitch(float rotationPitch) {
		this.rotationPitch = rotationPitch;
	}

	public void setRotationYaw(float rotationYaw) {
		this.rotationYaw = rotationYaw;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeDouble(getPosX());
		data.writeDouble(getPosY());
		data.writeDouble(getPosZ());
		data.writeFloat(getRotationYaw());
		data.writeFloat(getRotationPitch());
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
