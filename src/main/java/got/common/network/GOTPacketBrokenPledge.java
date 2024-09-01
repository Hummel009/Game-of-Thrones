package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketBrokenOath implements IMessage {
	private int cooldown;
	private int cooldownStart;
	private GOTFaction brokenFac;

	@SuppressWarnings("unused")
	public GOTPacketBrokenOath() {
	}

	public GOTPacketBrokenOath(int cd, int start, GOTFaction f) {
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

	public static class Handler implements IMessageHandler<GOTPacketBrokenOath, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrokenOath packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			pd.setOathBreakCooldown(packet.cooldown);
			pd.setOathBreakCooldownStart(packet.cooldownStart);
			pd.setBrokenOathFaction(packet.brokenFac);
			return null;
		}
	}
}