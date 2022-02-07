package got.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;

public class GOTPacketAlignment implements IMessage {
	private UUID player;
	private Map<GOTFaction, Float> alignmentMap = new HashMap<>();
	private boolean hideAlignment;

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
		byte factionID = 0;
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
			if (!GOT.getProxy().isSingleplayer()) {
				GOTPlayerData pd = GOTLevelData.getData(packet.player);
				for (Map.Entry entry : packet.alignmentMap.entrySet()) {
					GOTFaction f = (GOTFaction) entry.getKey();
					float alignment = (Float) entry.getValue();
					pd.setAlignment(f, alignment);
				}
				pd.setHideAlignment(packet.hideAlignment);
			}
			return null;
		}
	}

}
