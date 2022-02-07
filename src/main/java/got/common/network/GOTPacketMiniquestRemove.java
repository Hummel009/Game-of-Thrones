package got.common.network;

import java.util.UUID;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.quest.GOTMiniQuest;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketMiniquestRemove implements IMessage {
	public UUID questUUID;
	public boolean wasCompleted;
	public boolean addToCompleted;

	public GOTPacketMiniquestRemove() {
	}

	public GOTPacketMiniquestRemove(GOTMiniQuest quest, boolean wc, boolean atc) {
		questUUID = quest.getQuestUUID();
		wasCompleted = wc;
		addToCompleted = atc;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		questUUID = new UUID(data.readLong(), data.readLong());
		wasCompleted = data.readBoolean();
		addToCompleted = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(questUUID.getMostSignificantBits());
		data.writeLong(questUUID.getLeastSignificantBits());
		data.writeBoolean(wasCompleted);
		data.writeBoolean(addToCompleted);
	}

	public static class Handler implements IMessageHandler<GOTPacketMiniquestRemove, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMiniquestRemove packet, MessageContext context) {
			if (!GOT.getProxy().isSingleplayer()) {
				EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				GOTMiniQuest removeQuest = pd.getMiniQuestForID(packet.questUUID, packet.wasCompleted);
				if (removeQuest != null) {
					if (packet.addToCompleted) {
						pd.completeMiniQuest(removeQuest);
					} else {
						pd.removeMiniQuest(removeQuest, packet.wasCompleted);
					}
				} else {
					FMLLog.warning("Tried to remove a GOT miniquest that doesn't exist");
				}
			}
			return null;
		}
	}

}
