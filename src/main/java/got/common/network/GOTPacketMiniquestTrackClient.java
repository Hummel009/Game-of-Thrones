package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketMiniquestTrackClient implements IMessage {
	private UUID questID;

	public GOTPacketMiniquestTrackClient(UUID uuid) {
		questID = uuid;
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

	public static class Handler implements IMessageHandler<GOTPacketMiniquestTrackClient, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMiniquestTrackClient packet, MessageContext context) {
			if (!GOT.getProxy().isSingleplayer()) {
				EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				pd.setTrackingMiniQuestID(packet.questID);
			}
			return null;
		}
	}

}
