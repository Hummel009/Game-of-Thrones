package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTPacketHiredGui implements IMessage {
	public int entityID;
	public boolean openGui;
	public boolean isActive;
	public boolean canMove;
	public boolean teleportAutomatically;
	public int mobKills;
	public int xp;
	public float alignmentRequired;
	public GOTUnitTradeEntry.PledgeType pledgeType;
	public boolean inCombat;
	public boolean guardMode;
	public int guardRange;

	public GOTPacketHiredGui() {
	}

	public GOTPacketHiredGui(int i, boolean flag) {
		entityID = i;
		openGui = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		openGui = data.readBoolean();
		isActive = data.readBoolean();
		canMove = data.readBoolean();
		teleportAutomatically = data.readBoolean();
		mobKills = data.readInt();
		xp = data.readInt();
		alignmentRequired = data.readFloat();
		pledgeType = GOTUnitTradeEntry.PledgeType.forID(data.readByte());
		inCombat = data.readBoolean();
		guardMode = data.readBoolean();
		guardRange = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(openGui);
		data.writeBoolean(isActive);
		data.writeBoolean(canMove);
		data.writeBoolean(teleportAutomatically);
		data.writeInt(mobKills);
		data.writeInt(xp);
		data.writeFloat(alignmentRequired);
		data.writeByte(pledgeType.typeID);
		data.writeBoolean(inCombat);
		data.writeBoolean(guardMode);
		data.writeInt(guardRange);
	}

	public static class Handler implements IMessageHandler<GOTPacketHiredGui, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketHiredGui packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) entity;
				if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					npc.hiredNPCInfo.receiveClientPacket(packet);
					if (packet.openGui) {
						GOT.getProxy().openHiredNPCGui(npc);
					}
				}
			}
			return null;
		}
	}

}
