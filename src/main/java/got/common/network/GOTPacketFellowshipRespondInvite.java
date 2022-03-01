package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.fellowship.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFellowshipRespondInvite extends GOTPacketFellowshipDo {
	public boolean acceptOrReject;

	public GOTPacketFellowshipRespondInvite() {
	}

	public GOTPacketFellowshipRespondInvite(GOTFellowshipClient fs, boolean accept) {
		super(fs);
		acceptOrReject = accept;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		super.fromBytes(data);
		acceptOrReject = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		super.toBytes(data);
		data.writeBoolean(acceptOrReject);
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipRespondInvite, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipRespondInvite packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTFellowship fellowship = packet.getFellowship();
			if (fellowship != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				if (packet.acceptOrReject) {
					playerData.acceptFellowshipInvite(fellowship);
				} else {
					playerData.rejectFellowshipInvite(fellowship);
				}
			}
			return null;
		}
	}

}
