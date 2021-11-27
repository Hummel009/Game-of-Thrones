package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.entity.other.GOTEntityCart;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityHorse;

public class GOTPacketCargocartUpdate implements IMessage {
	public int pullingId;
	public int cartId;

	public GOTPacketCargocartUpdate() {
	}

	public GOTPacketCargocartUpdate(int horseIn, int cartIn) {
		pullingId = horseIn;
		cartId = cartIn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pullingId = buf.readInt();
		cartId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pullingId);
		buf.writeInt(cartId);
	}

	public static class Handler implements IMessageHandler<GOTPacketCargocartUpdate, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCargocartUpdate message, MessageContext ctx) {
			EntityHorse pulling = null;
			GOTEntityCart cart = (GOTEntityCart) Minecraft.getMinecraft().theWorld.getEntityByID(message.cartId);
			if (message.pullingId >= 0) {
				pulling = (EntityHorse) Minecraft.getMinecraft().theWorld.getEntityByID(message.pullingId);
			}
			cart.setPulling(pulling);
			return null;
		}
	}

}
