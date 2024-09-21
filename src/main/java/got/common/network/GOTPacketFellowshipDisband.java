package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipClient;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFellowshipDisband extends GOTPacketFellowshipDo {
	@SuppressWarnings("unused")
	public GOTPacketFellowshipDisband() {
	}

	public GOTPacketFellowshipDisband(GOTFellowshipClient fs) {
		super(fs);
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipDisband, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipDisband packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTFellowship fellowship = packet.getActiveFellowship();
			if (fellowship != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				playerData.disbandFellowship(fellowship, entityplayer.getCommandSenderName());
			}
			return null;
		}
	}
}