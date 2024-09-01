package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import got.common.brotherhood.GOTBrotherhoodData;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public abstract class GOTPacketBrotherhoodDo implements IMessage {
	private UUID brotherhoodID;

	protected GOTPacketBrotherhoodDo() {
	}

	protected GOTPacketBrotherhoodDo(GOTBrotherhoodClient fsClient) {
		brotherhoodID = fsClient.getBrotherhoodID();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		brotherhoodID = new UUID(data.readLong(), data.readLong());
	}

	protected GOTBrotherhood getActiveBrotherhood() {
		return GOTBrotherhoodData.getActiveBrotherhood(brotherhoodID);
	}

	protected GOTBrotherhood getActiveOrDisbandedBrotherhood() {
		return GOTBrotherhoodData.getBrotherhood(brotherhoodID);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(brotherhoodID.getMostSignificantBits());
		data.writeLong(brotherhoodID.getLeastSignificantBits());
	}
}