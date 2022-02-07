package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;

public class GOTPacketEnableAlignmentZones implements IMessage {
	private boolean enable;

	public GOTPacketEnableAlignmentZones(boolean flag) {
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

	public static class Handler implements IMessageHandler<GOTPacketEnableAlignmentZones, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEnableAlignmentZones packet, MessageContext context) {
			GOTLevelData.setEnableAlignmentZones(packet.enable);
			return null;
		}
	}

}
