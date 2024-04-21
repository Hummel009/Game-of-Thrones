package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.entity.other.GOTEntityNPC;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketNPCIsOfferingQuest implements IMessage {
	private boolean offering;
	private int offerColor;
	private int entityID;

	@SuppressWarnings({"WeakerAccess", "unused"})
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

	public int getOfferColor() {
		return offerColor;
	}

	public boolean isOffering() {
		return offering;
	}

	public static class Handler implements IMessageHandler<GOTPacketNPCIsOfferingQuest, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketNPCIsOfferingQuest packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).getQuestInfo().receiveData(packet);
			}
			return null;
		}
	}
}