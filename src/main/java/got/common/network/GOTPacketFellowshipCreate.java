package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketFellowshipCreate implements IMessage {
	public String fellowshipName;

	public GOTPacketFellowshipCreate() {
	}

	public GOTPacketFellowshipCreate(String name) {
		fellowshipName = name;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte nameLength = data.readByte();
		ByteBuf nameBytes = data.readBytes(nameLength);
		fellowshipName = nameBytes.toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameBytes = fellowshipName.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowshipCreate, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowshipCreate packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
			playerData.createFellowship(packet.fellowshipName, true);
			return null;
		}
	}

}
