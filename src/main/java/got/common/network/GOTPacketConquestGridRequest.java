package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.faction.GOTFaction;
import got.common.world.map.GOTConquestGrid;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketConquestGridRequest implements IMessage {
	public GOTFaction conqFac;

	public GOTPacketConquestGridRequest(GOTFaction fac) {
		conqFac = fac;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		conqFac = GOTFaction.forID(facID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = conqFac.ordinal();
		data.writeByte(facID);
	}

	public static class Handler implements IMessageHandler<GOTPacketConquestGridRequest, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketConquestGridRequest packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTFaction fac = packet.conqFac;
			if (fac != null) {
				GOTConquestGrid.ConquestViewableQuery query = GOTConquestGrid.canPlayerViewConquest(entityplayer, fac);
				if (query.result == GOTConquestGrid.ConquestViewable.CAN_VIEW) {
					GOTConquestGrid.sendConquestGridTo(entityplayer, fac);
				}
			}
			return null;
		}
	}

}
