package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GOTPacketFTTimer implements IMessage {
	public int timer;

	public GOTPacketFTTimer() {
	}

	public GOTPacketFTTimer(int i) {
		timer = i;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		timer = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(timer);
	}

	public static class Handler implements IMessageHandler<GOTPacketFTTimer, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFTTimer packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			GOTLevelData.getData(entityplayer).setTimeSinceFT(packet.timer);
			return null;
		}
	}

}
