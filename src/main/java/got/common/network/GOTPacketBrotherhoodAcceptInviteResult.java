package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.brotherhood.GOTBrotherhood;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GOTPacketBrotherhoodAcceptInviteResult implements IMessage {
	private UUID brotherhoodID;
	private String brotherhoodName;
	private AcceptInviteResult result;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodAcceptInviteResult() {
	}

	public GOTPacketBrotherhoodAcceptInviteResult(AcceptInviteResult result) {
		brotherhoodID = null;
		brotherhoodName = null;
		this.result = result;
	}

	public GOTPacketBrotherhoodAcceptInviteResult(GOTBrotherhood fs, AcceptInviteResult result) {
		brotherhoodID = fs.getBrotherhoodID();
		brotherhoodName = fs.getName();
		this.result = result;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		boolean hasBrotherhood = data.readBoolean();
		if (hasBrotherhood) {
			brotherhoodID = new UUID(data.readLong(), data.readLong());
			byte fsNameLength = data.readByte();
			ByteBuf fsNameBytes = data.readBytes(fsNameLength);
			brotherhoodName = fsNameBytes.toString(Charsets.UTF_8);
		}
		result = AcceptInviteResult.values()[data.readByte()];
	}

	@Override
	public void toBytes(ByteBuf data) {
		boolean hasBrotherhood = brotherhoodID != null && brotherhoodName != null;
		data.writeBoolean(hasBrotherhood);
		if (hasBrotherhood) {
			data.writeLong(brotherhoodID.getMostSignificantBits());
			data.writeLong(brotherhoodID.getLeastSignificantBits());
			byte[] fsNameBytes = brotherhoodName.getBytes(Charsets.UTF_8);
			data.writeByte(fsNameBytes.length);
			data.writeBytes(fsNameBytes);
		}
		data.writeByte(result.ordinal());
	}

	public enum AcceptInviteResult {
		JOINED, DISBANDED, TOO_LARGE, NONEXISTENT
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodAcceptInviteResult, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodAcceptInviteResult packet, MessageContext context) {
			GOT.proxy.displayBrotherhoodAcceptInvitationResult(packet.brotherhoodID, packet.brotherhoodName, packet.result);
			return null;
		}
	}
}