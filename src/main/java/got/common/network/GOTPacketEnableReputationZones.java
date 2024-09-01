package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;

public class GOTPacketEnableReputationZones implements IMessage {
	private boolean enable;

	@SuppressWarnings("unused")
	public GOTPacketEnableReputationZones() {
	}

	public GOTPacketEnableReputationZones(boolean flag) {
		enable = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		enable = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeBoolean(enable);
	}

	public static class Handler implements IMessageHandler<GOTPacketEnableReputationZones, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEnableReputationZones packet, MessageContext context) {
			GOTLevelData.setEnableReputationZones(packet.enable);
			return null;
		}
	}
}