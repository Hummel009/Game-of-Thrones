package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFellowshipDisband extends GOTPacketFellowshipDo {
	public GOTPacketFellowshipDisband() {
	}

	public GOTPacketFellowshipDisband(GOTFellowshipClient fs) {
		super(fs);
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipDisband, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipDisband packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTFellowship fellowship = packet.getFellowship();
			if (fellowship != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				playerData.disbandFellowship(fellowship);
			}
			return null;
		}
	}

}
