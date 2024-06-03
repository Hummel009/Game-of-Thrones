package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.quest.GOTMiniQuest;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

public class GOTPacketDeleteMiniquest implements IMessage {
	private UUID questUUID;
	private boolean completed;

	@SuppressWarnings("unused")
	public GOTPacketDeleteMiniquest() {
	}

	public GOTPacketDeleteMiniquest(GOTMiniQuest quest) {
		questUUID = quest.getQuestUUID();
		completed = quest.isCompleted();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		questUUID = new UUID(data.readLong(), data.readLong());
		completed = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(questUUID.getMostSignificantBits());
		data.writeLong(questUUID.getLeastSignificantBits());
		data.writeBoolean(completed);
	}

	public static class Handler implements IMessageHandler<GOTPacketDeleteMiniquest, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketDeleteMiniquest packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTMiniQuest removeQuest = pd.getMiniQuestForID(packet.questUUID, packet.completed);
			if (removeQuest != null) {
				pd.removeMiniQuest(removeQuest, packet.completed);
			} else {
				FMLLog.warning("Tried to remove a GOT miniquest that doesn't exist");
			}
			return null;
		}
	}
}