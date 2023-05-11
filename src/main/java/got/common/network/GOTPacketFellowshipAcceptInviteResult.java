package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.fellowship.GOTFellowship;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GOTPacketFellowshipAcceptInviteResult implements IMessage {
	public UUID fellowshipID;
	public String fellowshipName;
	public AcceptInviteResult result;

	public GOTPacketFellowshipAcceptInviteResult() {
	}

	public GOTPacketFellowshipAcceptInviteResult(AcceptInviteResult result) {
		fellowshipID = null;
		fellowshipName = null;
		this.result = result;
	}

	public GOTPacketFellowshipAcceptInviteResult(GOTFellowship fs, AcceptInviteResult result) {
		fellowshipID = fs.getFellowshipID();
		fellowshipName = fs.getName();
		this.result = result;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		boolean hasFellowship = data.readBoolean();
		if (hasFellowship) {
			fellowshipID = new UUID(data.readLong(), data.readLong());
			byte fsNameLength = data.readByte();
			ByteBuf fsNameBytes = data.readBytes(fsNameLength);
			fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
		}
		result = AcceptInviteResult.values()[data.readByte()];
	}

	@Override
	public void toBytes(ByteBuf data) {
		boolean hasFellowship = fellowshipID != null && fellowshipName != null;
		data.writeBoolean(hasFellowship);
		if (hasFellowship) {
			data.writeLong(fellowshipID.getMostSignificantBits());
			data.writeLong(fellowshipID.getLeastSignificantBits());
			byte[] fsNameBytes = fellowshipName.getBytes(Charsets.UTF_8);
			data.writeByte(fsNameBytes.length);
			data.writeBytes(fsNameBytes);
		}
		data.writeByte(result.ordinal());
	}

	public enum AcceptInviteResult {
		JOINED, DISBANDED, TOO_LARGE, NONEXISTENT;

	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipAcceptInviteResult, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipAcceptInviteResult packet, MessageContext context) {
			GOT.proxy.displayFellowshipAcceptInvitationResult(packet.fellowshipID, packet.fellowshipName, packet.result);
			return null;
		}
	}

}