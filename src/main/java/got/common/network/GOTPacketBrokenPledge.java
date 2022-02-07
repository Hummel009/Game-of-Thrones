package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketBrokenPledge implements IMessage {
	public int cooldown;
	public int cooldownStart;
	public GOTFaction brokenFac;

	public GOTPacketBrokenPledge(int cd, int start, GOTFaction f) {
		cooldown = cd;
		cooldownStart = start;
		brokenFac = f;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cooldown = data.readInt();
		cooldownStart = data.readInt();
		byte facID = data.readByte();
		if (facID >= 0) {
			brokenFac = GOTFaction.forID(facID);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cooldown);
		data.writeInt(cooldownStart);
		if (brokenFac == null) {
			data.writeByte(-1);
		} else {
			data.writeByte(brokenFac.ordinal());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketBrokenPledge, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrokenPledge packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			pd.setPledgeBreakCooldown(packet.cooldown);
			pd.setPledgeBreakCooldownStart(packet.cooldownStart);
			pd.setBrokenPledgeFaction(packet.brokenFac);
			return null;
		}
	}

}
