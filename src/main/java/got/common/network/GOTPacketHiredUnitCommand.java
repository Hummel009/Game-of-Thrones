package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.database.GOTGuiId;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTHiredNPCInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class GOTPacketHiredUnitCommand implements IMessage {
	public int entityID;
	public int page;
	public int action;
	public int value;

	public GOTPacketHiredUnitCommand() {
	}

	public GOTPacketHiredUnitCommand(int eid, int p, int a, int v) {
		entityID = eid;
		page = p;
		action = a;
		value = v;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		page = data.readByte();
		action = data.readByte();
		value = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeByte(page);
		data.writeByte(action);
		data.writeByte(value);
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredUnitCommand, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredUnitCommand packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npc = world.getEntityByID(packet.entityID);
			if (npc instanceof GOTEntityNPC) {
				GOTEntityNPC hiredNPC = (GOTEntityNPC) npc;
				if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					int page = packet.page;
					int action = packet.action;
					int value = packet.value;
					if (action == -1) {
						hiredNPC.hiredNPCInfo.isGuiOpen = false;
					} else {
						GOTHiredNPCInfo.Task task = hiredNPC.hiredNPCInfo.getTask();
						if (task == GOTHiredNPCInfo.Task.WARRIOR) {
							if (page == 0) {
								entityplayer.openGui(GOT.instance, GOTGuiId.HIRED_WARRIOR_INVENTORY.ordinal(), world, hiredNPC.getEntityId(), 0, 0);
							} else if (page == 1) {
								switch (action) {
									case 0:
										hiredNPC.hiredNPCInfo.teleportAutomatically = !hiredNPC.hiredNPCInfo.teleportAutomatically;
										break;
									case 1:
										hiredNPC.hiredNPCInfo.setGuardMode(!hiredNPC.hiredNPCInfo.isGuardMode());
										break;
									case 2:
										hiredNPC.hiredNPCInfo.setGuardRange(value);
										break;
									default:
										break;
								}
							}
						} else if (task == GOTHiredNPCInfo.Task.FARMER) {
							switch (action) {
								case 0:
									hiredNPC.hiredNPCInfo.setGuardMode(!hiredNPC.hiredNPCInfo.isGuardMode());
									break;
								case 1:
									hiredNPC.hiredNPCInfo.setGuardRange(value);
									break;
								case 2:
									entityplayer.openGui(GOT.instance, GOTGuiId.HIRED_FARMER_INVENTORY.ordinal(), world, hiredNPC.getEntityId(), 0, 0);
									break;
								default:
									break;
							}
						}
						hiredNPC.hiredNPCInfo.sendClientPacket(false);
					}
				}
			}
			return null;
		}
	}

}
