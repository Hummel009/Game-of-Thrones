package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.database.GOTGuiId;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.info.GOTHireableInfo;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class GOTPacketHiredUnitCommand implements IMessage {
	private int entityID;
	private int page;
	private int action;
	private int value;

	@SuppressWarnings("unused")
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
				if (hiredNPC.getHireableInfo().isActive() && hiredNPC.getHireableInfo().getHiringPlayer() == entityplayer) {
					int page = packet.page;
					int action = packet.action;
					int value = packet.value;
					if (action == -1) {
						hiredNPC.getHireableInfo().setGuiOpen(false);
					} else {
						GOTHireableInfo.Task task = hiredNPC.getHireableInfo().getHiredTask();
						if (task == GOTHireableInfo.Task.WARRIOR) {
							if (page == 0) {
								entityplayer.openGui(GOT.instance, GOTGuiId.HIRED_WARRIOR_INVENTORY.ordinal(), world, hiredNPC.getEntityId(), 0, 0);
							} else if (page == 1) {
								switch (action) {
									case 0:
										hiredNPC.getHireableInfo().setTeleportAutomatically(!hiredNPC.getHireableInfo().isTeleportAutomatically());
										break;
									case 1:
										hiredNPC.getHireableInfo().setGuardMode(!hiredNPC.getHireableInfo().isGuardMode());
										break;
									case 2:
										hiredNPC.getHireableInfo().setGuardRange(value);
										break;
								}
							}
						} else if (task == GOTHireableInfo.Task.FARMER) {
							switch (action) {
								case 0:
									hiredNPC.getHireableInfo().setGuardMode(!hiredNPC.getHireableInfo().isGuardMode());
									break;
								case 1:
									hiredNPC.getHireableInfo().setGuardRange(value);
									break;
								case 2:
									entityplayer.openGui(GOT.instance, GOTGuiId.HIRED_FARMER_INVENTORY.ordinal(), world, hiredNPC.getEntityId(), 0, 0);
									break;
							}
						}
						hiredNPC.getHireableInfo().sendClientPacket(false);
					}
				}
			}
			return null;
		}
	}
}