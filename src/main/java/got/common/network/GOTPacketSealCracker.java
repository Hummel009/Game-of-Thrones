package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.inventory.GOTContainerCracker;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class GOTPacketSealCracker implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketSealCracker, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketSealCracker packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerCracker) {
				GOTContainerCracker cracker = (GOTContainerCracker) container;
				cracker.receiveSealingPacket(entityplayer);
			}
			return null;
		}
	}

}
