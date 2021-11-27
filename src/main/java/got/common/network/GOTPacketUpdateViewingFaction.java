package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketUpdateViewingFaction implements IMessage {
	public GOTFaction viewingFaction;

	public GOTPacketUpdateViewingFaction() {
	}

	public GOTPacketUpdateViewingFaction(GOTFaction f) {
		viewingFaction = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		viewingFaction = GOTFaction.forID(facID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = viewingFaction.ordinal();
		data.writeByte(facID);
	}

	public static class Handler implements IMessageHandler<GOTPacketUpdateViewingFaction, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketUpdateViewingFaction packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			pd.setViewingFaction(packet.viewingFaction);
			return null;
		}
	}

}
