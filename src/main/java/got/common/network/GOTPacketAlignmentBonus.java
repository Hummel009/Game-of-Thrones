package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.faction.GOTAlignmentBonusMap;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.StatCollector;

import java.util.Map;

public class GOTPacketAlignmentBonus implements IMessage {
	private GOTAlignmentBonusMap factionBonusMap = new GOTAlignmentBonusMap();
	private GOTFaction mainFaction;
	private String name;

	private float prevMainAlignment;
	private float conquestBonus;
	private double posX;
	private double posY;
	private double posZ;
	private boolean needsTranslation;
	private boolean isKill;
	private boolean isHiredKill;

	@SuppressWarnings("unused")
	public GOTPacketAlignmentBonus() {
	}

	public GOTPacketAlignmentBonus(GOTFaction f, float pre, GOTAlignmentBonusMap fMap, float conqBonus, double x, double y, double z, GOTAlignmentValues.AlignmentBonus source) {
		mainFaction = f;
		prevMainAlignment = pre;
		factionBonusMap = fMap;
		conquestBonus = conqBonus;
		posX = x;
		posY = y;
		posZ = z;
		name = source.getName();
		needsTranslation = source.isNeedsTranslation();
		isKill = source.isKill();
		isHiredKill = source.isKillByHiredUnit();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		mainFaction = GOTFaction.forID(data.readByte());
		prevMainAlignment = data.readFloat();
		byte factionID;
		while ((factionID = data.readByte()) >= 0) {
			GOTFaction faction = GOTFaction.forID(factionID);
			float bonus = data.readFloat();
			factionBonusMap.put(faction, bonus);
		}
		conquestBonus = data.readFloat();
		posX = data.readDouble();
		posY = data.readDouble();
		posZ = data.readDouble();
		short length = data.readShort();
		name = data.readBytes(length).toString(Charsets.UTF_8);
		needsTranslation = data.readBoolean();
		isKill = data.readBoolean();
		isHiredKill = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(mainFaction.ordinal());
		data.writeFloat(prevMainAlignment);
		if (!factionBonusMap.isEmpty()) {
			for (Map.Entry<GOTFaction, Float> e : factionBonusMap.entrySet()) {
				GOTFaction faction = e.getKey();
				float bonus = e.getValue();
				data.writeByte(faction.ordinal());
				data.writeFloat(bonus);
			}
		}
		data.writeByte(-1);
		data.writeFloat(conquestBonus);
		data.writeDouble(posX);
		data.writeDouble(posY);
		data.writeDouble(posZ);
		byte[] nameData = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameData.length);
		data.writeBytes(nameData);
		data.writeBoolean(needsTranslation);
		data.writeBoolean(isKill);
		data.writeBoolean(isHiredKill);
	}

	public static class Handler implements IMessageHandler<GOTPacketAlignmentBonus, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketAlignmentBonus packet, MessageContext context) {
			String name = packet.name;
			if (packet.needsTranslation) {
				name = StatCollector.translateToLocal(name);
			}
			GOT.proxy.spawnAlignmentBonus(packet.mainFaction, packet.prevMainAlignment, packet.factionBonusMap, name, packet.isKill, packet.isHiredKill, packet.conquestBonus, packet.posX, packet.posY, packet.posZ);
			return null;
		}
	}
}