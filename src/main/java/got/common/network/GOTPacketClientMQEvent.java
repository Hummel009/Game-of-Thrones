package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.quest.GOTMiniQuestEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketClientMQEvent implements IMessage {
	private ClientMQEvent type;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketClientMQEvent() {
	}

	public GOTPacketClientMQEvent(ClientMQEvent t) {
		type = t;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		if (typeID >= 0 && typeID < ClientMQEvent.values().length) {
			type = ClientMQEvent.values()[typeID];
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
	}

	public enum ClientMQEvent {
		MAP, FACTIONS
	}

	public static class Handler implements IMessageHandler<GOTPacketClientMQEvent, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketClientMQEvent packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (packet.type == ClientMQEvent.MAP) {
				pd.distributeMQEvent(new GOTMiniQuestEvent.ViewMap());
			} else if (packet.type == ClientMQEvent.FACTIONS) {
				pd.distributeMQEvent(new GOTMiniQuestEvent.ViewFactions());
			}
			return null;
		}
	}
}