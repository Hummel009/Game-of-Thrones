package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class GOTPacketAlignment implements IMessage {
	private final Map<GOTFaction, Float> alignmentMap = new EnumMap<>(GOTFaction.class);

	private UUID player;
	private boolean hideAlignment;

	@SuppressWarnings("unused")
	public GOTPacketAlignment() {
	}

	public GOTPacketAlignment(UUID uuid) {
		player = uuid;
		GOTPlayerData pd = GOTLevelData.getData(player);
		for (GOTFaction f : GOTFaction.values()) {
			float al = pd.getAlignment(f);
			alignmentMap.put(f, al);
		}
		hideAlignment = pd.getHideAlignment();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		player = new UUID(data.readLong(), data.readLong());
		byte factionID;
		while ((factionID = data.readByte()) >= 0) {
			GOTFaction f = GOTFaction.forID(factionID);
			float alignment = data.readFloat();
			alignmentMap.put(f, alignment);
		}
		hideAlignment = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(player.getMostSignificantBits());
		data.writeLong(player.getLeastSignificantBits());
		for (Map.Entry<GOTFaction, Float> entry : alignmentMap.entrySet()) {
			GOTFaction f = entry.getKey();
			float alignment = entry.getValue();
			data.writeByte(f.ordinal());
			data.writeFloat(alignment);
		}
		data.writeByte(-1);
		data.writeBoolean(hideAlignment);
	}

	public static class Handler implements IMessageHandler<GOTPacketAlignment, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketAlignment packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				GOTPlayerData pd = GOTLevelData.getData(packet.player);
				for (Map.Entry<GOTFaction, Float> entry : packet.alignmentMap.entrySet()) {
					GOTFaction f = entry.getKey();
					float alignment = entry.getValue();
					pd.setAlignment(f, alignment);
				}
				pd.setHideAlignment(packet.hideAlignment);
			}
			return null;
		}
	}
}