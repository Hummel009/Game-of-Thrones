package got.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTTitle;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class GOTPacketTitle implements IMessage {
	private GOTTitle.PlayerTitle playerTitle;

	@SuppressWarnings("unused")
	public GOTPacketTitle() {
	}

	public GOTPacketTitle(GOTTitle.PlayerTitle t) {
		playerTitle = t;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		short titleID = data.readShort();
		GOTTitle title = GOTTitle.forID(titleID);
		byte colorID = data.readByte();
		EnumChatFormatting color = GOTTitle.PlayerTitle.colorForID(colorID);
		if (title != null && color != null) {
			playerTitle = new GOTTitle.PlayerTitle(title, color);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (playerTitle == null) {
			data.writeShort(-1);
			data.writeByte(-1);
		} else {
			data.writeShort(playerTitle.getTitle().getTitleID());
			data.writeByte(playerTitle.getColor().getFormattingCode());
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketTitle, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketTitle packet, MessageContext context) {
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			GOTTitle.PlayerTitle title = packet.playerTitle;
			pd.setPlayerTitle(title);
			return null;
		}
	}
}