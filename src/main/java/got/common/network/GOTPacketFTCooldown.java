package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import io.netty.buffer.ByteBuf;

public class GOTPacketFTCooldown implements IMessage {
	private int cooldownMax;
	private int cooldownMin;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketFTCooldown() {
	}

	public GOTPacketFTCooldown(int max, int min) {
		cooldownMax = max;
		cooldownMin = min;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		cooldownMax = data.readInt();
		cooldownMin = data.readInt();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(cooldownMax);
		data.writeInt(cooldownMin);
	}

	public static class Handler implements IMessageHandler<GOTPacketFTCooldown, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFTCooldown packet, MessageContext context) {
			GOTLevelData.setWaypointCooldown(packet.cooldownMax, packet.cooldownMin);
			return null;
		}
	}
}