package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import io.netty.buffer.ByteBuf;

public class GOTPacketEnvironmentOverlay implements IMessage {
	public Overlay overlay;

	public GOTPacketEnvironmentOverlay() {
	}

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
			if (packet.overlay == Overlay.FROST) {
				GOT.getProxy().showFrostOverlay();
			} else if (packet.overlay == Overlay.BURN) {
				GOT.getProxy().showBurnOverlay();
			}
			return null;
		}
	}

	public enum Overlay {
		FROST, BURN;

	}

}
