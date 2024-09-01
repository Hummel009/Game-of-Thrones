package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketBrotherhoodDisband extends GOTPacketBrotherhoodDo {
	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodDisband() {
	}

	public GOTPacketBrotherhoodDisband(GOTBrotherhoodClient fs) {
		super(fs);
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodDisband, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodDisband packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTBrotherhood brotherhood = packet.getActiveBrotherhood();
			if (brotherhood != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				playerData.disbandBrotherhood(brotherhood, entityplayer.getCommandSenderName());
			}
			return null;
		}
	}
}