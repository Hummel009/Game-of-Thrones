package got.common.network;

import java.util.*;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;

public class GOTPacketAlignmentSee implements IMessage {
	private String username;
	private Map<GOTFaction, Float> alignmentMap = new HashMap<>();

	public GOTPacketAlignmentSee(String name, GOTPlayerData pd) {
		username = name;
		for (GOTFaction f : GOTFaction.getPlayableAlignmentFactions()) {
			float al = pd.getAlignment(f);
			alignmentMap.put(f, al);
		}
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte length = data.readByte();
		ByteBuf nameBytes = data.readBytes(length);
		username = nameBytes.toString(Charsets.UTF_8);
		byte factionID = 0;
		while ((factionID = data.readByte()) >= 0) {
			GOTFaction f = GOTFaction.forID(factionID);
			float alignment = data.readFloat();
			alignmentMap.put(f, alignment);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameBytes = username.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
		for (Map.Entry<GOTFaction, Float> entry : alignmentMap.entrySet()) {
			GOTFaction f = entry.getKey();
			float alignment = entry.getValue();
			data.writeByte(f.ordinal());
			data.writeFloat(alignment);
		}
		data.writeByte(-1);
	}

	public static class Handler implements IMessageHandler<GOTPacketAlignmentSee, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketAlignmentSee packet, MessageContext context) {
			GOT.getProxy().displayAlignmentSee(packet.username, packet.alignmentMap);
			return null;
		}
	}

}
