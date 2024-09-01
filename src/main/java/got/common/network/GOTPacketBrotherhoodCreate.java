package got.common.network;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketBrotherhoodCreate implements IMessage {
	private String brotherhoodName;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhoodCreate() {
	}

	public GOTPacketBrotherhoodCreate(String name) {
		brotherhoodName = name;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte nameLength = data.readByte();
		ByteBuf nameBytes = data.readBytes(nameLength);
		brotherhoodName = nameBytes.toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameBytes = brotherhoodName.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhoodCreate, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhoodCreate packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			playerData.createBrotherhood(packet.brotherhoodName, true);
			return null;
		}
	}
}