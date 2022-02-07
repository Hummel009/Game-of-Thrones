package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import io.netty.buffer.ByteBuf;

public class GOTPacketEnvironmentOverlay implements IMessage {
	public Overlay overlay;

	public GOTPacketEnvironmentOverlay(Overlay o) {
		overlay = o;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte overlayID = data.readByte();
		overlay = Overlay.values()[overlayID];
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(overlay.ordinal());
	}

	public static class Handler implements IMessageHandler<GOTPacketEnvironmentOverlay, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEnvironmentOverlay packet, MessageContext context) {
			switch (packet.overlay) {
			case BURN:
				GOT.getProxy().showBurnOverlay();
				break;
			case FROST:
				GOT.getProxy().showFrostOverlay();
				break;
			}
			return null;
		}
	}

	public enum Overlay {
		FROST, BURN;
	}

}
