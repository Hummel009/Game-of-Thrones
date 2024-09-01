package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketPledge implements IMessage {
	private GOTFaction pledgeFac;

	@SuppressWarnings("unused")
	public GOTPacketPledge() {
	}

	public GOTPacketPledge(GOTFaction f) {
		pledgeFac = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		pledgeFac = facID == -1 ? null : GOTFaction.forID(facID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = pledgeFac == null ? -1 : pledgeFac.ordinal();
		data.writeByte(facID);
	}

	public static class Handler implements IMessageHandler<GOTPacketPledge, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketPledge packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				pd.setPledgeFaction(packet.pledgeFac);
			}
			return null;
		}
	}
}