package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketBrotherhoodRespondInvite extends GOTPacketBrotherhoodDo {
	private boolean accept;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodRespondInvite() {
	}

	public GOTPacketBrotherhoodRespondInvite(GOTBrotherhoodClient fs, boolean accepts) {
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

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodRespondInvite, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodRespondInvite packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			GOTBrotherhood brotherhood = packet.getActiveOrDisbandedBrotherhood();
			if (brotherhood != null) {
				if (packet.accept) {
					playerData.acceptBrotherhoodInvite(brotherhood, true);
				} else {
					playerData.rejectBrotherhoodInvite(brotherhood);
				}
			} else {
				IMessage resultPacket = new GOTPacketBrotherhoodAcceptInviteResult(GOTPacketBrotherhoodAcceptInviteResult.AcceptInviteResult.NONEXISTENT);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(resultPacket, entityplayer);
			}
			return null;
		}
	}
}