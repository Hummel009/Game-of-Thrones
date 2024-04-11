package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.inventory.GOTContainerAnvil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class GOTPacketAnvilReforge implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketAnvilReforge, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketAnvilReforge packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerAnvil) {
				GOTContainerAnvil anvil = (GOTContainerAnvil) container;
				anvil.reforgeItem();
			}
			return null;
		}
	}
}