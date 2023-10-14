package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.quest.GOTMiniQuest;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class GOTPacketMiniquest implements IMessage {
	public NBTTagCompound miniquestData;
	public boolean completed;

	public GOTPacketMiniquest() {
	}

	public GOTPacketMiniquest(NBTTagCompound nbt, boolean flag) {
		miniquestData = nbt;
		completed = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		try {
			miniquestData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Hummel009: Error reading miniquest data");
			e.printStackTrace();
		}
		completed = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(miniquestData);
		} catch (IOException e) {
			FMLLog.severe("Hummel009: Error writing miniquest data");
			e.printStackTrace();
		}
		data.writeBoolean(completed);
	}

	public static class Handler implements IMessageHandler<GOTPacketMiniquest, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMiniquest packet, MessageContext context) {
			if (!GOT.proxy.isSingleplayer()) {
				EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				GOTMiniQuest miniquest = GOTMiniQuest.loadQuestFromNBT(packet.miniquestData, pd);
				if (miniquest != null) {
					GOTMiniQuest existingQuest = pd.getMiniQuestForID(miniquest.questUUID, packet.completed);
					if (existingQuest == null) {
						if (packet.completed) {
							pd.addMiniQuestCompleted(miniquest);
						} else {
							pd.addMiniQuest(miniquest);
						}
					} else {
						existingQuest.readFromNBT(packet.miniquestData);
					}
				}
			}
			return null;
		}
	}

}
