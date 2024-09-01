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

public class GOTPacketOath implements IMessage {
	private GOTFaction oathFac;

	@SuppressWarnings("unused")
	public GOTPacketOath() {
	}

	public GOTPacketOath(GOTFaction f) {
		oathFac = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		oathFac = facID == -1 ? null : GOTFaction.forID(facID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = oathFac == null ? -1 : oathFac.ordinal();
		data.writeByte(facID);
	}

	public static class Handler implements IMessageHandler<GOTPacketOath, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketOath packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				pd.setOathFaction(packet.oathFac);
			}
			return null;
		}
	}
}