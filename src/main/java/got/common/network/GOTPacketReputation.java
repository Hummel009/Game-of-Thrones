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

public class GOTPacketReputation implements IMessage {
	private final Map<GOTFaction, Float> reputationMap = new EnumMap<>(GOTFaction.class);

	private UUID player;
	private boolean hideReputation;

	@SuppressWarnings("unused")
	public GOTPacketReputation() {
	}

	public GOTPacketReputation(UUID uuid) {
		player = uuid;
		GOTPlayerData pd = GOTLevelData.getData(player);
		for (GOTFaction f : GOTFaction.values()) {
			float al = pd.getReputation(f);
			reputationMap.put(f, al);
		}
		hideReputation = pd.getHideReputation();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		player = new UUID(data.readLong(), data.readLong());
		byte factionID;
		while ((factionID = data.readByte()) >= 0) {
			GOTFaction f = GOTFaction.forID(factionID);
			float reputation = data.readFloat();
			reputationMap.put(f, reputation);
		}
		hideReputation = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(player.getMostSignificantBits());
		data.writeLong(player.getLeastSignificantBits());
		for (Map.Entry<GOTFaction, Float> entry : reputationMap.entrySet()) {
			GOTFaction f = entry.getKey();
			float reputation = entry.getValue();
			data.writeByte(f.ordinal());
			data.writeFloat(reputation);
		}
		data.writeByte(-1);
		data.writeBoolean(hideReputation);
	}

	public static class Handler implements IMessageHandler<GOTPacketReputation, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketReputation packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				GOTPlayerData pd = GOTLevelData.getData(packet.player);
				for (Map.Entry<GOTFaction, Float> entry : packet.reputationMap.entrySet()) {
					GOTFaction f = entry.getKey();
					float reputation = entry.getValue();
					pd.setReputation(f, reputation);
				}
				pd.setHideReputation(packet.hideReputation);
			}
			return null;
		}
	}
}