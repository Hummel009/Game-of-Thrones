package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.GOTMountFunctions;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketMountControlServerEnforce implements IMessage {
	public double posX;
	public double posY;
	public double posZ;
	public float rotationYaw;
	public float rotationPitch;

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

	public static class Handler implements IMessageHandler<GOTPacketMountControlServerEnforce, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMountControlServerEnforce packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			GOTMountFunctions.sendControlToServer(entityplayer, packet);
			return null;
		}
	}

}
