package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTLevelData;
import got.common.fellowship.GOTFellowship;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketFellowshipRemove implements IMessage {
	public UUID fellowshipID;
	public boolean isInvite;

	public GOTPacketFellowshipRemove() {
	}

	public GOTPacketFellowshipRemove(GOTFellowship fs, boolean invite) {
		fellowshipID = fs.getFellowshipID();
		isInvite = invite;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		fellowshipID = new UUID(data.readLong(), data.readLong());
		isInvite = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
		data.writeBoolean(isInvite);
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipRemove, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipRemove packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			if (packet.isInvite) {
				GOTLevelData.getData(entityplayer).removeClientFellowshipInvite(packet.fellowshipID);
			} else {
				GOTLevelData.getData(entityplayer).removeClientFellowship(packet.fellowshipID);
			}
			return null;
		}
	}

}
