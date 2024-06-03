package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class GOTPacketHiredUnitInteract implements IMessage {
	private int entityID;
	private int entityAction;

	@SuppressWarnings("unused")
	public GOTPacketHiredUnitInteract() {
	}

	public GOTPacketHiredUnitInteract(int id, int a) {
		entityID = id;
		entityAction = a;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		entityAction = data.readByte();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeByte(entityAction);
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredUnitInteract, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredUnitInteract packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npc = world.getEntityByID(packet.entityID);
			if (npc instanceof GOTEntityNPC) {
				GOTEntityNPC hiredNPC = (GOTEntityNPC) npc;
				if (hiredNPC.getHireableInfo().isActive() && hiredNPC.getHireableInfo().getHiringPlayer() == entityplayer) {
					int action = packet.entityAction;
					boolean closeScreen = false;
					if (action == 0) {
						hiredNPC.setNpcTalkTick(40);
						closeScreen = hiredNPC.speakTo(entityplayer);
					} else if (action == 1) {
						hiredNPC.getHireableInfo().sendClientPacket(true);
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