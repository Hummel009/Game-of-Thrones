package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import got.common.quest.GOTMiniQuest;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketDeleteMiniquest implements IMessage {
	public UUID questUUID;
	public boolean completed;

	public GOTPacketDeleteMiniquest() {
	}

	public GOTPacketDeleteMiniquest(GOTMiniQuest quest) {
		questUUID = quest.questUUID;
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
