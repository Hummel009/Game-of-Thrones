package got.common.network;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.entity.other.GOTEntityCart;
import got.common.util.GOTVec3d;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketCargocartControl implements IMessage {
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<GOTPacketCargocartControl, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCargocartControl message, MessageContext ctx) {
			EntityPlayerMP sender = ctx.getServerHandler().playerEntity;
			if (sender.isRiding() && sender.ridingEntity instanceof net.minecraft.entity.passive.EntityHorse) {
				List<GOTEntityCart> result = sender.getServerForPlayer().getEntitiesWithinAABB(GOTEntityCart.class, sender.boundingBox.expand(3.0D, 3.0D, 3.0D));
				if (!result.isEmpty()) {
					GOTEntityCart closest = result.get(0);
					for (GOTEntityCart cart : result) {
						if (cart.getPulling() == sender.ridingEntity) {
							cart.setPulling(null);
							sender.getServerForPlayer().getEntityTracker().func_151247_a(cart, GOTPacketHandler.networkWrapper.getPacketFrom(new GOTPacketCargocartUpdate(-1, cart.getEntityId())));
							return null;
						}
						if (new GOTVec3d(cart.posX, cart.posY, cart.posZ).distanceTo(new GOTVec3d(sender.posX, sender.posY, sender.posZ)) < new GOTVec3d(closest.posX, closest.posY, closest.posZ).distanceTo(new GOTVec3d(sender.posX, sender.posY, sender.posZ))) {
							closest = cart;
						}
					}
					closest.setPulling(sender.ridingEntity);
					sender.getServerForPlayer().getEntityTracker().func_151247_a(closest, GOTPacketHandler.networkWrapper.getPacketFrom(new GOTPacketCargocartUpdate(closest.getPulling().getEntityId(), closest.getEntityId())));
				}
			}
			return null;
		}
	}
}
