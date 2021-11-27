package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.inventory.GOTContainerAnvil;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class GOTPacketAnvilEngraveOwner implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketAnvilEngraveOwner, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketAnvilEngraveOwner packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerAnvil) {
				GOTContainerAnvil anvil = (GOTContainerAnvil) container;
				anvil.engraveOwnership();
			}
			return null;
		}
	}

}
