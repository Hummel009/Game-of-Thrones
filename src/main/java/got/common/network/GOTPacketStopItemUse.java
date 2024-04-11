package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketStopItemUse implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketStopItemUse, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketStopItemUse packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			entityplayer.clearItemInUse();
			return null;
		}
	}
}