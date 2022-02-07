package got.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.entity.other.GOTEntityBanner;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GOTPacketBannerValidate implements IMessage {
	public int entityID;
	public int slot;
	public String prevText;
	public boolean valid;

	public GOTPacketBannerValidate(int id, int s, String pre, boolean val) {
		entityID = id;
		slot = s;
		prevText = pre;
		valid = val;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		slot = data.readInt();
		byte length = data.readByte();
		ByteBuf textBytes = data.readBytes(length);
		prevText = textBytes.toString(Charsets.UTF_8);
		valid = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeInt(slot);
		byte[] textBytes = prevText.getBytes(Charsets.UTF_8);
		data.writeByte(textBytes.length);
		data.writeBytes(textBytes);
		data.writeBoolean(valid);
	}

	public static class Handler implements IMessageHandler<GOTPacketBannerValidate, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBannerValidate packet, MessageContext context) {
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityBanner) {
				GOTEntityBanner banner = (GOTEntityBanner) entity;
				GOT.getProxy().validateBannerUsername(banner, packet.slot, packet.prevText, packet.valid);
			}
			return null;
		}
	}

}
