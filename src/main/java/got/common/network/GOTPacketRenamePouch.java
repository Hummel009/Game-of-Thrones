package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.inventory.GOTContainerPouch;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class GOTPacketRenamePouch implements IMessage {
	public String name;

	public GOTPacketRenamePouch(String s) {
		name = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		short length = data.readShort();
		name = data.readBytes(length).toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		byte[] nameData = name.getBytes(Charsets.UTF_8);
		data.writeShort(nameData.length);
		data.writeBytes(nameData);
	}

	public static class Handler implements IMessageHandler<GOTPacketRenamePouch, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketRenamePouch packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			Container container = entityplayer.openContainer;
			if (container instanceof GOTContainerPouch) {
				GOTContainerPouch pouch = (GOTContainerPouch) container;
				pouch.renamePouch(packet.name);
			}
			return null;
		}
	}

}
