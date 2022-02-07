package got.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.GOTDimension.DimensionRegion;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketClientInfo implements IMessage {
	public GOTFaction viewingFaction;
	public Map<GOTDimension.DimensionRegion, GOTFaction> changedRegionMap;
	public boolean showWP;
	public boolean showCWP;
	public boolean showHiddenSWP;

	public GOTPacketClientInfo() {
	}

	public GOTPacketClientInfo(GOTFaction f, Map<GOTDimension.DimensionRegion, GOTFaction> crMap, boolean w, boolean cw, boolean h) {
		viewingFaction = f;
		changedRegionMap = crMap;
		showWP = w;
		showCWP = cw;
		showHiddenSWP = h;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int changedRegionsSize;
		byte factionID = data.readByte();
		if (factionID >= 0) {
			viewingFaction = GOTFaction.forID(factionID);
		}
		changedRegionsSize = data.readByte();
		if (changedRegionsSize > 0) {
			changedRegionMap = new HashMap<>();
			for (int l = 0; l < changedRegionsSize; ++l) {
				GOTDimension.DimensionRegion reg = GOTDimension.DimensionRegion.forID(data.readByte());
				GOTFaction fac = GOTFaction.forID(data.readByte());
				changedRegionMap.put(reg, fac);
			}
		}
		showWP = data.readBoolean();
		showCWP = data.readBoolean();
		showHiddenSWP = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (viewingFaction == null) {
			data.writeByte(-1);
		} else {
			data.writeByte(viewingFaction.ordinal());
		}
		int changedRegionsSize = changedRegionMap != null ? changedRegionMap.size() : 0;
		data.writeByte(changedRegionsSize);
		if (changedRegionsSize > 0) {
			for (Map.Entry<GOTDimension.DimensionRegion, GOTFaction> e : changedRegionMap.entrySet()) {
				GOTDimension.DimensionRegion reg = e.getKey();
				GOTFaction fac = e.getValue();
				data.writeByte(reg.ordinal());
				data.writeByte(fac.ordinal());
			}
		}
		data.writeBoolean(showWP);
		data.writeBoolean(showCWP);
		data.writeBoolean(showHiddenSWP);
	}

	public static class Handler implements IMessageHandler<GOTPacketClientInfo, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketClientInfo packet, MessageContext context) {
			Map<DimensionRegion, GOTFaction> changedRegionMap;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (packet.viewingFaction != null) {
				GOTFaction prevFac = pd.getViewingFaction();
				GOTFaction newFac = packet.viewingFaction;
				pd.setViewingFaction(newFac);
				if (prevFac != newFac && prevFac.factionRegion == newFac.factionRegion) {
					pd.distributeMQEvent(new GOTMiniQuestEvent.CycleAlignment());
				}
				if (prevFac.factionRegion != newFac.factionRegion) {
					pd.distributeMQEvent(new GOTMiniQuestEvent.CycleAlignmentRegion());
				}
			}
			changedRegionMap = packet.changedRegionMap;
			if (changedRegionMap != null) {
				for (GOTDimension.DimensionRegion reg : changedRegionMap.keySet()) {
					GOTFaction fac = changedRegionMap.get(reg);
					pd.setRegionLastViewedFaction(reg, fac);
				}
			}
			pd.setShowWaypoints(packet.showWP);
			pd.setShowCustomWaypoints(packet.showCWP);
			pd.setShowHiddenSharedWaypoints(packet.showHiddenSWP);
			return null;
		}
	}

}
