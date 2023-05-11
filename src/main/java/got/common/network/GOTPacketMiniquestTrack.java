package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.quest.GOTMiniQuest;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class GOTPacketMiniquestTrack implements IMessage {
	public UUID questID;

	public GOTPacketMiniquestTrack() {
	}

	public GOTPacketMiniquestTrack(GOTMiniQuest quest) {
		questID = quest == null ? null : quest.questUUID;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		boolean hasQuest = data.readBoolean();
		questID = hasQuest ? new UUID(data.readLong(), data.readLong()) : null;
	}

	@Override
	public void toBytes(ByteBuf data) {
		boolean hasQuest = questID != null;
		data.writeBoolean(hasQuest);
		if (hasQuest) {
			data.writeLong(questID.getMostSignificantBits());
			data.writeLong(questID.getLeastSignificantBits());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketMiniquestTrack, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMiniquestTrack packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (packet.questID == null) {
				pd.setTrackingMiniQuestID(null);
			} else {
				pd.setTrackingMiniQuestID(packet.questID);
			}
			return null;
		}
	}

}
