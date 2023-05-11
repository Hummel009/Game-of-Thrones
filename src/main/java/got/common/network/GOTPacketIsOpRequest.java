package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class GOTPacketIsOpRequest implements IMessage {
	@Override
	public void fromBytes(ByteBuf data) {
	}

	@Override
	public void toBytes(ByteBuf data) {
	}

	public static class Handler implements IMessageHandler<GOTPacketIsOpRequest, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketIsOpRequest packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
			GOTPacketIsOpResponse packetResponse = new GOTPacketIsOpResponse(isOp);
			GOTPacketHandler.networkWrapper.sendTo(packetResponse, entityplayer);
			return null;
		}
	}

}
