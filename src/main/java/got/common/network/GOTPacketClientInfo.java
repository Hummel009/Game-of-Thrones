package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.EnumMap;
import java.util.Map;

public class GOTPacketClientInfo implements IMessage {
	private GOTFaction viewingFaction;
	private Map<GOTDimension.DimensionRegion, GOTFaction> changedRegionMap;
	private boolean showWP;
	private boolean showCWP;
	private boolean showHiddenSWP;

	@SuppressWarnings("unused")
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
		byte factionID = data.readByte();
		if (factionID >= 0) {
			viewingFaction = GOTFaction.forID(factionID);
		}
		int changedRegionsSize = data.readByte();
		if (changedRegionsSize > 0) {
			changedRegionMap = new EnumMap<>(GOTDimension.DimensionRegion.class);
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
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (packet.viewingFaction != null) {
				GOTFaction prevFac = pd.getViewingFaction();
				GOTFaction newFac = packet.viewingFaction;
				pd.setViewingFaction(newFac);
				if (prevFac != newFac && prevFac.getFactionRegion() == newFac.getFactionRegion()) {
					pd.distributeMQEvent(new GOTMiniQuestEvent.CycleReputation());
				}
				if (prevFac.getFactionRegion() != newFac.getFactionRegion()) {
					pd.distributeMQEvent(new GOTMiniQuestEvent.CycleReputationRegion());
				}
			}
			Map<GOTDimension.DimensionRegion, GOTFaction> changedRegionMap = packet.changedRegionMap;
			if (changedRegionMap != null) {
				for (Map.Entry<GOTDimension.DimensionRegion, GOTFaction> entry : changedRegionMap.entrySet()) {
					GOTFaction fac = entry.getValue();
					pd.setRegionLastViewedFaction(entry.getKey(), fac);
				}
			}
			pd.setShowWaypoints(packet.showWP);
			pd.setShowCustomWaypoints(packet.showCWP);
			pd.setShowHiddenSharedWaypoints(packet.showHiddenSWP);
			return null;
		}
	}
}