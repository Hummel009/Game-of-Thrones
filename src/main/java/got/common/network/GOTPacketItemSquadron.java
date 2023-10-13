package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTSquadrons;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

public class GOTPacketItemSquadron implements IMessage {
	public String squadron;

	public GOTPacketItemSquadron() {
	}

	public GOTPacketItemSquadron(String s) {
		squadron = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		int length = data.readInt();
		if (length > -1) {
			squadron = data.readBytes(length).toString(Charsets.UTF_8);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		if (StringUtils.isNullOrEmpty(squadron)) {
			data.writeInt(-1);
		} else {
			byte[] sqBytes = squadron.getBytes(Charsets.UTF_8);
			data.writeInt(sqBytes.length);
			data.writeBytes(sqBytes);
		}
	}

	public static class Handler implements IMessageHandler<GOTPacketItemSquadron, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketItemSquadron packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if (itemstack != null && itemstack.getItem() instanceof GOTSquadrons.SquadronItem) {
				String squadron = packet.squadron;
				if (StringUtils.isNullOrEmpty(squadron)) {
					GOTSquadrons.setSquadron(itemstack, "");
				} else {
					squadron = GOTSquadrons.checkAcceptableLength(squadron);
					GOTSquadrons.setSquadron(itemstack, squadron);
				}
			}
			return null;
		}
	}

}
