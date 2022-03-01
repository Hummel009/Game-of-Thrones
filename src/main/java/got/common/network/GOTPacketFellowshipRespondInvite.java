package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.fellowship.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFellowshipRespondInvite extends GOTPacketFellowshipDo {
	public boolean accept;

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
				GOTPacketFellowshipAcceptInviteResult resultPacket = new GOTPacketFellowshipAcceptInviteResult(GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult.NONEXISTENT);
				GOTPacketHandler.networkWrapper.sendTo(resultPacket, entityplayer);
			}
			return null;
		}
	}

}
