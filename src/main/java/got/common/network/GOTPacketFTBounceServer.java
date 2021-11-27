package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFTBounceServer implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketFTBounceServer, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFTBounceServer packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTLevelData.getData(entityplayer).receiveFTBouncePacket();
			return null;
		}
	}

}
