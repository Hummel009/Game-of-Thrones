package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.entity.other.GOTMountFunctions;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketMountControlServerEnforce implements IMessage {
	private double posX;
	private double posY;
	private double posZ;
	private float rotationYaw;
	private float rotationPitch;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketMountControlServerEnforce() {
	}

	public GOTPacketMountControlServerEnforce(Entity entity) {
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

	public float getRotationPitch() {
		return rotationPitch;
	}

	public float getRotationYaw() {
		return rotationYaw;
	}

	public double getPosZ() {
		return posZ;
	}

	public double getPosY() {
		return posY;
	}

	public double getPosX() {
		return posX;
	}

	public static class Handler implements IMessageHandler<GOTPacketMountControlServerEnforce, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMountControlServerEnforce packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTMountFunctions.sendControlToServer(entityplayer, packet);
			return null;
		}
	}
}