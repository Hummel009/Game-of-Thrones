package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.inventory.GOTContainerCoinExchange;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class GOTPacketCoinExchange implements IMessage {
	private int button;

	public GOTPacketCoinExchange(int i) {
		button = i;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		button = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(button);
	}

	public static class Handler implements IMessageHandler<GOTPacketCoinExchange, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCoinExchange packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerCoinExchange) {
				GOTContainerCoinExchange coinExchange = (GOTContainerCoinExchange) container;
				coinExchange.handleExchangePacket(packet.button);
			}
			return null;
		}
	}

}
