package got.common.network;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRelations;
import io.netty.buffer.ByteBuf;

public class GOTPacketFactionRelations implements IMessage {
	public Type packetType;
	public Map<GOTFactionRelations.FactionPair, GOTFactionRelations.Relation> fullMap;
	public GOTFactionRelations.FactionPair singleKey;
	public GOTFactionRelations.Relation singleRelation;

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		packetType = Type.forID(typeID);
		if (packetType == Type.FULL_MAP) {
			fullMap = new HashMap<>();
			byte fac1ID;
			while ((fac1ID = data.readByte()) >= 0) {
				byte fac2ID = data.readByte();
				byte relID = data.readByte();
				GOTFaction f1 = GOTFaction.forID(fac1ID);
				GOTFaction f2 = GOTFaction.forID(fac2ID);
				GOTFactionRelations.FactionPair key = new GOTFactionRelations.FactionPair(f1, f2);
				GOTFactionRelations.Relation rel = GOTFactionRelations.Relation.forID(relID);
				fullMap.put(key, rel);
			}
		} else if (packetType != Type.RESET && packetType == Type.ONE_ENTRY) {
			byte fac1ID = data.readByte();
			byte fac2ID = data.readByte();
			byte relID = data.readByte();
			GOTFaction f1 = GOTFaction.forID(fac1ID);
			GOTFaction f2 = GOTFaction.forID(fac2ID);
			singleKey = new GOTFactionRelations.FactionPair(f1, f2);
			singleRelation = GOTFactionRelations.Relation.forID(relID);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(packetType.ordinal());
		if (packetType == Type.FULL_MAP) {
			for (Map.Entry<GOTFactionRelations.FactionPair, GOTFactionRelations.Relation> e : fullMap.entrySet()) {
				GOTFactionRelations.FactionPair key = e.getKey();
				GOTFactionRelations.Relation rel = e.getValue();
				data.writeByte(key.getLeft().ordinal());
				data.writeByte(key.getRight().ordinal());
				data.writeByte(rel.ordinal());
			}
			data.writeByte(-1);
		} else if (packetType != Type.RESET && packetType == Type.ONE_ENTRY) {
			data.writeByte(singleKey.getLeft().ordinal());
			data.writeByte(singleKey.getRight().ordinal());
			data.writeByte(singleRelation.ordinal());
		}
	}

	public static GOTPacketFactionRelations fullMap(Map<GOTFactionRelations.FactionPair, GOTFactionRelations.Relation> map) {
		GOTPacketFactionRelations pkt = new GOTPacketFactionRelations();
		pkt.packetType = Type.FULL_MAP;
		pkt.fullMap = map;
		return pkt;
	}

	public static GOTPacketFactionRelations oneEntry(GOTFactionRelations.FactionPair pair, GOTFactionRelations.Relation rel) {
		GOTPacketFactionRelations pkt = new GOTPacketFactionRelations();
		pkt.packetType = Type.ONE_ENTRY;
		pkt.singleKey = pair;
		pkt.singleRelation = rel;
		return pkt;
	}

	public static GOTPacketFactionRelations reset() {
		GOTPacketFactionRelations pkt = new GOTPacketFactionRelations();
		pkt.packetType = Type.RESET;
		return pkt;
	}

	public static class Handler implements IMessageHandler<GOTPacketFactionRelations, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFactionRelations packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				Type t = packet.packetType;
				if (t == Type.FULL_MAP) {
					GOTFactionRelations.resetAllRelations();
					for (Map.Entry<GOTFactionRelations.FactionPair, GOTFactionRelations.Relation> e : packet.fullMap.entrySet()) {
						GOTFactionRelations.FactionPair key = e.getKey();
						GOTFactionRelations.Relation rel = e.getValue();
						GOTFactionRelations.overrideRelations(key.getLeft(), key.getRight(), rel);
					}
				} else if (t == Type.RESET) {
					GOTFactionRelations.resetAllRelations();
				} else if (t == Type.ONE_ENTRY) {
					GOTFactionRelations.FactionPair key = packet.singleKey;
					GOTFactionRelations.Relation rel = packet.singleRelation;
					GOTFactionRelations.overrideRelations(key.getLeft(), key.getRight(), rel);
				}
			}
			return null;
		}
	}

	public enum Type {
		FULL_MAP, RESET, ONE_ENTRY;

		public static Type forID(int id) {
			for (Type t : values()) {
				if (t.ordinal() != id) {
					continue;
				}
				return t;
			}
			return null;
		}
	}

}
