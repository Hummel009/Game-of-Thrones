package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFellowshipRespondInvite extends GOTPacketFellowshipDo {
	private boolean accept;

	@SuppressWarnings("unused")
	public GOTPacketFellowshipRespondInvite() {
	}

	public GOTPacketFellowshipRespondInvite(GOTFellowshipClient fs, boolean accepts) {
		super(fs);
		accept = accepts;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		accept = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		data.writeBoolean(accept);
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipRespondInvite, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipRespondInvite packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			GOTFellowship fellowship = packet.getActiveOrDisbandedFellowship();
			if (fellowship != null) {
				if (packet.accept) {
					playerData.acceptFellowshipInvite(fellowship, true);
				} else {
					playerData.rejectFellowshipInvite(fellowship);
				}
			} else {
				IMessage resultPacket = new GOTPacketFellowshipAcceptInviteResult(GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult.NONEXISTENT);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(resultPacket, entityplayer);
			}
			return null;
		}
	}
}