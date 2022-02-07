package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketSetPlayerRotation implements IMessage {
	private float rotYaw;

	private GOTPacketSetPlayerRotation() {
	}

	private GOTPacketSetPlayerRotation(float f) {
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
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			if (entityplayer != null) {
				entityplayer.rotationYaw = packet.rotYaw;
			}
			return null;
		}
	}

}
