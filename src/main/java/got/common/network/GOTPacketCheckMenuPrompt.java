package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class GOTPacketCheckMenuPrompt implements IMessage {
	private GOTPacketMenuPrompt.Type type;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketCheckMenuPrompt() {
	}

	public GOTPacketCheckMenuPrompt(GOTPacketMenuPrompt.Type t) {
		type = t;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		byte typeID = data.readByte();
		type = GOTPacketMenuPrompt.Type.values()[typeID];
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeByte(type.ordinal());
	}

	public static class Handler implements IMessageHandler<GOTPacketCheckMenuPrompt, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketCheckMenuPrompt packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			if (packet.type == GOTPacketMenuPrompt.Type.MENU) {
				pd.setCheckedMenu(true);
			}
			return null;
		}
	}
}