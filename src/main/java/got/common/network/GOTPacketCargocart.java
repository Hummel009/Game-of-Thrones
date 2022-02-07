package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.entity.other.GOTEntityCargocart;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class GOTPacketCargocart implements IMessage {
	public int load;
	public int cartId;

	public GOTPacketCargocart(int loadIn, int cartIn) {
		load = loadIn;
		cartId = cartIn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		load = buf.readInt();
		cartId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(load);
		buf.writeInt(cartId);
	}

	public static class Handler implements IMessageHandler<GOTPacketCargocart, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCargocart message, MessageContext ctx) {
			GOTEntityCargocart cart = (GOTEntityCargocart) Minecraft.getMinecraft().theWorld.getEntityByID(message.cartId);
			cart.setLoad(message.load);
			return null;
		}
	}

}
