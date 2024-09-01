package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;

import java.util.EnumMap;
import java.util.Map;

public class GOTPacketReputationSee implements IMessage {
	private final Map<GOTFaction, Float> reputationMap = new EnumMap<>(GOTFaction.class);

	private String username;

	@SuppressWarnings("unused")
	public GOTPacketReputationSee() {
	}

	public GOTPacketReputationSee(String name, GOTPlayerData pd) {
		username = name;
		for (GOTFaction f : GOTFaction.getPlayableReputationFactions()) {
			float al = pd.getReputation(f);
			reputationMap.put(f, al);
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte length = data.readByte();
		ByteBuf nameBytes = data.readBytes(length);
		username = nameBytes.toString(Charsets.UTF_8);
		byte factionID;
		while ((factionID = data.readByte()) >= 0) {
			GOTFaction f = GOTFaction.forID(factionID);
			float reputation = data.readFloat();
			reputationMap.put(f, reputation);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameBytes = username.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
		for (Map.Entry<GOTFaction, Float> entry : reputationMap.entrySet()) {
			GOTFaction f = entry.getKey();
			float reputation = entry.getValue();
			data.writeByte(f.ordinal());
			data.writeFloat(reputation);
		}
		data.writeByte(-1);
	}

	public static class Handler implements IMessageHandler<GOTPacketReputationSee, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketReputationSee packet, MessageContext context) {
			GOT.proxy.displayReputationSee(packet.username, packet.reputationMap);
			return null;
		}
	}
}