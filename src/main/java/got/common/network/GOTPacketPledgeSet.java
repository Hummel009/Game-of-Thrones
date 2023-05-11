package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketPledgeSet implements IMessage {
	public GOTFaction pledgeFac;

	public GOTPacketPledgeSet() {
	}

	public GOTPacketPledgeSet(GOTFaction f) {
		pledgeFac = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte facID = data.readByte();
		pledgeFac = facID == -1 ? null : GOTFaction.forID(facID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		int facID = pledgeFac == null ? -1 : pledgeFac.ordinal();
		data.writeByte(facID);
	}

	public static class Handler implements IMessageHandler<GOTPacketPledgeSet, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketPledgeSet packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTFaction fac = packet.pledgeFac;
			if (fac == null) {
				pd.revokePledgeFaction(entityplayer, true);
			} else if (pd.canPledgeTo(fac) && pd.canMakeNewPledge()) {
				pd.setPledgeFaction(fac);
			}
			return null;
		}
	}

}
