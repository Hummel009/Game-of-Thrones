package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.brotherhood.GOTBrotherhood;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class GOTPacketBrotherhoodRemove implements IMessage {
	private UUID brotherhoodID;
	private boolean isInvite;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodRemove() {
	}

	public GOTPacketBrotherhoodRemove(GOTBrotherhood fs, boolean invite) {
		brotherhoodID = fs.getBrotherhoodID();
		isInvite = invite;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		brotherhoodID = new UUID(data.readLong(), data.readLong());
		isInvite = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(brotherhoodID.getMostSignificantBits());
		data.writeLong(brotherhoodID.getLeastSignificantBits());
		data.writeBoolean(isInvite);
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodRemove, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodRemove packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			if (packet.isInvite) {
				GOTLevelData.getData(entityplayer).removeClientBrotherhoodInvite(packet.brotherhoodID);
			} else {
				GOTLevelData.getData(entityplayer).removeClientBrotherhood(packet.brotherhoodID);
			}
			return null;
		}
	}
}