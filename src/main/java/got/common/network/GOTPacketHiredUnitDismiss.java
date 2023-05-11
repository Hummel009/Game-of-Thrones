package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class GOTPacketHiredUnitDismiss implements IMessage {
	public int entityID;
	public int action;

	public GOTPacketHiredUnitDismiss() {
	}

	public GOTPacketHiredUnitDismiss(int id, int a) {
		entityID = id;
		action = a;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		action = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeByte(action);
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredUnitDismiss, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredUnitDismiss packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npc = world.getEntityByID(packet.entityID);
			if (npc instanceof GOTEntityNPC) {
				GOTEntityNPC hiredNPC = (GOTEntityNPC) npc;
				if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					int action = packet.action;
					boolean closeScreen = false;
					if (action == 0) {
						hiredNPC.hiredNPCInfo.dismissUnit(false);
						Entity mount = hiredNPC.ridingEntity;
						Entity rider = hiredNPC.riddenByEntity;
						if (mount instanceof GOTEntityNPC) {
							GOTEntityNPC mountNPC = (GOTEntityNPC) mount;
							if (mountNPC.hiredNPCInfo.isActive && mountNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
								mountNPC.hiredNPCInfo.dismissUnit(false);
							}
						}
						if (rider instanceof GOTEntityNPC) {
							GOTEntityNPC riderNPC = (GOTEntityNPC) rider;
							if (riderNPC.hiredNPCInfo.isActive && riderNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
								riderNPC.hiredNPCInfo.dismissUnit(false);
							}
						}
						closeScreen = true;
					}
					if (closeScreen) {
						entityplayer.closeScreen();
					}
				}
			}
			return null;
		}
	}

}
