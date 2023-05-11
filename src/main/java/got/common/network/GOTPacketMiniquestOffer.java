package got.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.quest.GOTMiniQuest;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

public class GOTPacketMiniquestOffer implements IMessage {
	public int entityID;
	public NBTTagCompound miniquestData;

	public GOTPacketMiniquestOffer() {
	}

	public GOTPacketMiniquestOffer(int id, NBTTagCompound nbt) {
		entityID = id;
		miniquestData = nbt;
	}

	public static void sendClosePacket(EntityPlayer entityplayer, GOTEntityNPC npc, boolean accept) {
		if (entityplayer == null) {
			FMLLog.warning("GOT Warning: Tried to send miniquest offer close packet, but player == null");
			return;
		}
		GOTPacketMiniquestOfferClose packet = new GOTPacketMiniquestOfferClose(npc.getEntityId(), accept);
		GOTPacketHandler.networkWrapper.sendToServer(packet);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		try {
			miniquestData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Error reading miniquest data");
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(miniquestData);
		} catch (IOException e) {
			FMLLog.severe("Error writing miniquest data");
			e.printStackTrace();
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketMiniquestOffer, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMiniquestOffer packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				GOTMiniQuest quest = GOTMiniQuest.loadQuestFromNBT(packet.miniquestData, pd);
				if (quest != null) {
					GOT.proxy.displayMiniquestOffer(quest, npc);
				} else {
					GOTPacketMiniquestOffer.sendClosePacket(entityplayer, npc, false);
				}
			}
			return null;
		}
	}

}
