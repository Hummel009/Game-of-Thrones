package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class GOTPacketMoneyChange implements IMessage {
	public int canChange;

	public GOTPacketMoneyChange() {
	}

	public GOTPacketMoneyChange(int canchange) {
		canChange = canchange;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		canChange = new PacketBuffer(data).readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		new PacketBuffer(data).writeInt(canChange);
	}

	public static class Handler implements IMessageHandler<GOTPacketMoneyChange, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketMoneyChange packet, MessageContext context) {
			int canChange = packet.canChange;
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			pd.balance = canChange;
			return null;
		}
	}
}
