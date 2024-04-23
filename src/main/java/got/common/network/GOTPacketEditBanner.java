package got.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.common.GOTBannerProtection;
import got.common.entity.other.utils.GOTBannerWhitelistEntry;
import got.common.entity.other.inanimate.GOTEntityBanner;
import got.common.fellowship.GOTFellowship;
import got.common.fellowship.GOTFellowshipProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import java.util.List;

public class GOTPacketEditBanner implements IMessage {
	private boolean playerSpecificProtection;
	private boolean selfProtection;
	private float alignmentProtection;
	private int whitelistLength;
	private int defaultPerms;
	private int bannerID;

	private int[] whitelistPerms;
	private String[] whitelistSlots;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTPacketEditBanner() {
	}

	public GOTPacketEditBanner(GOTEntityBanner banner) {
		bannerID = banner.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		bannerID = data.readInt();
		playerSpecificProtection = data.readBoolean();
		selfProtection = data.readBoolean();
		alignmentProtection = data.readFloat();
		whitelistLength = data.readShort();
		boolean sendWhitelist = data.readBoolean();
		if (sendWhitelist) {
			whitelistSlots = new String[data.readShort()];
			whitelistPerms = new int[whitelistSlots.length];
			short index;
			while ((index = data.readShort()) >= 0) {
				byte length = data.readByte();
				if (length == -1) {
					whitelistSlots[index] = null;
					continue;
				}
				ByteBuf usernameBytes = data.readBytes(length);
				whitelistSlots[index] = usernameBytes.toString(Charsets.UTF_8);
				whitelistPerms[index] = data.readShort();
			}
		}
		defaultPerms = data.readShort();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(bannerID);
		data.writeBoolean(playerSpecificProtection);
		data.writeBoolean(selfProtection);
		data.writeFloat(alignmentProtection);
		data.writeShort(whitelistLength);
		boolean sendWhitelist = whitelistSlots != null;
		data.writeBoolean(sendWhitelist);
		if (sendWhitelist) {
			data.writeShort(whitelistSlots.length);
			for (int index = 0; index < whitelistSlots.length; ++index) {
				data.writeShort(index);
				String username = whitelistSlots[index];
				if (StringUtils.isNullOrEmpty(username)) {
					data.writeByte(-1);
					continue;
				}
				byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
				data.writeByte(usernameBytes.length);
				data.writeBytes(usernameBytes);
				data.writeShort(whitelistPerms[index]);
			}
			data.writeShort(-1);
		}
		data.writeShort(defaultPerms);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public int getDefaultPerms() {
		return defaultPerms;
	}

	public void setDefaultPerms(int defaultPerms) {
		this.defaultPerms = defaultPerms;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public int[] getWhitelistPerms() {
		return whitelistPerms;
	}

	public void setWhitelistPerms(int[] whitelistPerms) {
		this.whitelistPerms = whitelistPerms;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public String[] getWhitelistSlots() {
		return whitelistSlots;
	}

	public void setWhitelistSlots(String[] whitelistSlots) {
		this.whitelistSlots = whitelistSlots;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public int getWhitelistLength() {
		return whitelistLength;
	}

	public void setWhitelistLength(int whitelistLength) {
		this.whitelistLength = whitelistLength;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public float getAlignmentProtection() {
		return alignmentProtection;
	}

	public void setAlignmentProtection(float alignmentProtection) {
		this.alignmentProtection = alignmentProtection;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public boolean isSelfProtection() {
		return selfProtection;
	}

	public void setSelfProtection(boolean selfProtection) {
		this.selfProtection = selfProtection;
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public boolean isPlayerSpecificProtection() {
		return playerSpecificProtection;
	}

	public void setPlayerSpecificProtection(boolean playerSpecificProtection) {
		this.playerSpecificProtection = playerSpecificProtection;
	}

	public static class Handler implements IMessageHandler<GOTPacketEditBanner, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEditBanner packet, MessageContext context) {
			GOTEntityBanner banner;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity bEntity = world.getEntityByID(packet.bannerID);
			if (bEntity instanceof GOTEntityBanner && (banner = (GOTEntityBanner) bEntity).canPlayerEditBanner(entityplayer)) {
				banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
				banner.setSelfProtection(packet.selfProtection);
				banner.setAlignmentProtection(packet.alignmentProtection);
				banner.resizeWhitelist(packet.whitelistLength);
				if (packet.whitelistSlots != null) {
					for (int index = 0; index < packet.whitelistSlots.length; ++index) {
						if (index == 0) {
							continue;
						}
						String username = packet.whitelistSlots[index];
						int perms = packet.whitelistPerms[index];
						if (StringUtils.isNullOrEmpty(username)) {
							banner.whitelistPlayer(index, null);
							continue;
						}
						List<GOTBannerProtection.Permission> decodedPerms = GOTBannerWhitelistEntry.static_decodePermBitFlags(perms);
						if (GOTFellowshipProfile.hasFellowshipCode(username)) {
							String fsName = GOTFellowshipProfile.stripFellowshipCode(username);
							GOTFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
							if (fellowship == null) {
								continue;
							}
							banner.whitelistFellowship(index, fellowship, decodedPerms);
							continue;
						}
						GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
						if (profile == null) {
							continue;
						}
						banner.whitelistPlayer(index, profile, decodedPerms);
					}
				}
				List<GOTBannerProtection.Permission> defaultPerms = GOTBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
				banner.setDefaultPermissions(defaultPerms);
			}
			return null;
		}
	}
}