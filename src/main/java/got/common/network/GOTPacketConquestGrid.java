package got.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.faction.GOTFaction;
import got.common.world.map.GOTConquestZone;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class GOTPacketConquestGrid implements IMessage {
	public GOTFaction conqFac;
	public List<GOTConquestZone> allZones;
	public long worldTime;

	public GOTPacketConquestGrid() {
	}

	public GOTPacketConquestGrid(GOTFaction fac, Collection<GOTConquestZone> grid, World world) {
		conqFac = fac;
		allZones = new ArrayList<>(grid);
		worldTime = world.getTotalWorldTime();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		conqFac = GOTFaction.forID(facID);
		allZones = new ArrayList<>();
		short gridX = 0;
		short gridZ = 0;
		float str = 0.0f;
		while ((gridX = data.readShort()) != -15000) {
			gridZ = data.readShort();
			long time = data.readLong();
			str = data.readFloat();
			GOTConquestZone zone = new GOTConquestZone(gridX, gridZ);
			zone.setClientSide();
			zone.setLastChangeTime(time);
			zone.setConquestStrengthRaw(conqFac, str);
			allZones.add(zone);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = conqFac.ordinal();
		data.writeByte(facID);
		for (GOTConquestZone zone : allZones) {
			float str = zone.getConquestStrength(conqFac, worldTime);
			if ((str <= 0.0f)) {
				continue;
			}
			float strRaw = zone.getConquestStrengthRaw(conqFac);
			data.writeShort(zone.gridX);
			data.writeShort(zone.gridZ);
			data.writeLong(zone.getLastChangeTime());
			data.writeFloat(strRaw);
		}
		data.writeShort(-15000);
	}

	public static class Handler implements IMessageHandler<GOTPacketConquestGrid, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketConquestGrid packet, MessageContext context) {
			GOT.proxy.receiveConquestGrid(packet.conqFac, packet.allZones);
			return null;
		}
	}

}
