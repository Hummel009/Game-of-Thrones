package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class GOTPacketMiniquestOfferClose implements IMessage {
	public int npcID;
	public boolean accept;

	public GOTPacketMiniquestOfferClose(int id, boolean flag) {
		npcID = id;
		accept = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		npcID = data.readInt();
		accept = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(npcID);
		data.writeBoolean(accept);
	}

	public static class Handler implements IMessageHandler<GOTPacketMiniquestOfferClose, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMiniquestOfferClose packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npcEntity = world.getEntityByID(packet.npcID);
			if (npcEntity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) npcEntity;
				npc.questInfo.receiveOfferResponse(entityplayer, packet.accept);
			}
			return null;
		}
	}

}
