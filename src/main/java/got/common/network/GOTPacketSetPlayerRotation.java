package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketSetPlayerRotation implements IMessage {
	public float rotYaw;

	public GOTPacketSetPlayerRotation() {
	}

	public GOTPacketSetPlayerRotation(float f) {
		rotYaw = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		rotYaw = data.readFloat();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeFloat(rotYaw);
	}

	public static class Handler implements IMessageHandler<GOTPacketSetPlayerRotation, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketSetPlayerRotation packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			if (entityplayer != null) {
				entityplayer.rotationYaw = packet.rotYaw;
			}
			return null;
		}
	}

}
