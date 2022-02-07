package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketNPCCombatStance implements IMessage {
	public int entityID;
	public boolean combatStance;

	public GOTPacketNPCCombatStance() {
	}

	public GOTPacketNPCCombatStance(int id, boolean flag) {
		entityID = id;
		combatStance = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		combatStance = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(combatStance);
	}

	public static class Handler implements IMessageHandler<GOTPacketNPCCombatStance, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCCombatStance packet, MessageContext context) {
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).clientCombatStance = packet.combatStance;
			}
			return null;
		}
	}

}
