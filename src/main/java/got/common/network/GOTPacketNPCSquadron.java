package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTSquadrons;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class GOTPacketNPCSquadron implements IMessage {
	public int npcID;
	public String squadron;

	public GOTPacketNPCSquadron() {
	}

	public GOTPacketNPCSquadron(GOTEntityNPC npc, String s) {
		npcID = npc.getEntityId();
		squadron = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		npcID = data.readInt();
		int length = data.readInt();
		if (length > -1) {
			squadron = data.readBytes(length).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(npcID);
		if (StringUtils.isNullOrEmpty(squadron)) {
			data.writeInt(-1);
		} else {
			byte[] sqBytes = squadron.getBytes(Charsets.UTF_8);
			data.writeInt(sqBytes.length);
			data.writeBytes(sqBytes);
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketNPCSquadron, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCSquadron packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity npc = world.getEntityByID(packet.npcID);
			if (npc instanceof GOTEntityNPC) {
				GOTEntityNPC hiredNPC = (GOTEntityNPC) npc;
				if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					String squadron = packet.squadron;
					if (StringUtils.isNullOrEmpty(squadron)) {
						hiredNPC.hiredNPCInfo.setSquadron("");
					} else {
						squadron = GOTSquadrons.checkAcceptableLength(squadron);
						hiredNPC.hiredNPCInfo.setSquadron(squadron);
					}
				}
			}
			return null;
		}
	}

}
