package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class GOTPacketMiniquestTrackClient implements IMessage {
	private UUID questID;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketMiniquestTrackClient() {
	}

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
			if (!GOT.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				pd.setTrackingMiniQuestID(packet.questID);
			}
			return null;
		}
	}
}