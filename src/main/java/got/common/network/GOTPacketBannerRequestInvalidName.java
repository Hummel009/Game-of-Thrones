package got.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.entity.other.GOTEntityBanner;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class GOTPacketBannerRequestInvalidName implements IMessage {
	private int bannerID;
	private int slot;
	private String username;

	@SuppressWarnings("unused")
	public GOTPacketBannerRequestInvalidName() {
	}

	public GOTPacketBannerRequestInvalidName(GOTEntityBanner banner, int i, String s) {
		bannerID = banner.getEntityId();
		slot = i;
		username = s;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		bannerID = data.readInt();
		slot = data.readShort();
		byte length = data.readByte();
		username = data.readBytes(length).toString(Charsets.UTF_8);
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(bannerID);
		data.writeShort(slot);
		byte[] nameBytes = username.getBytes(Charsets.UTF_8);
		data.writeByte(nameBytes.length);
		data.writeBytes(nameBytes);
	}

	public static class Handler implements IMessageHandler<GOTPacketBannerRequestInvalidName, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBannerRequestInvalidName packet, MessageContext context) {
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity bEntity = world.getEntityByID(packet.bannerID);
			if (bEntity instanceof GOTEntityBanner) {
				GOTEntityBanner banner = (GOTEntityBanner) bEntity;
				String username = packet.username;
				boolean valid = false;
				if (GOTFellowshipProfile.hasFellowshipCode(username)) {
					String fsName = GOTFellowshipProfile.stripFellowshipCode(username);
					GOTFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
					if (fellowship != null) {
						valid = true;
					}
				} else {
					GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(packet.username);
					if (profile != null) {
						valid = true;
					}
				}
				IMessage packetResponse = new GOTPacketBannerValidate(banner.getEntityId(), packet.slot, packet.username, valid);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packetResponse, entityplayer);
			}
			return null;
		}
	}
}