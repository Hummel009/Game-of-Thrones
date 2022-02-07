package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketNPCIsOfferingQuest implements IMessage {
	public int entityID;
	public boolean offering;
	public int offerColor;

	public GOTPacketNPCIsOfferingQuest() {
	}

	public GOTPacketNPCIsOfferingQuest(int id, boolean flag, int color) {
		entityID = id;
		offering = flag;
		offerColor = color;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		offering = data.readBoolean();
		offerColor = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(offering);
		data.writeInt(offerColor);
	}

	public static class Handler implements IMessageHandler<GOTPacketNPCIsOfferingQuest, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCIsOfferingQuest packet, MessageContext context) {
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).questInfo.receiveData(packet);
			}
			return null;
		}
	}

}
