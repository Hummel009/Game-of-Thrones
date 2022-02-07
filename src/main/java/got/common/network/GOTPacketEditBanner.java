package got.common.network;

import java.util.List;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.common.GOTBannerProtection;
import got.common.entity.other.*;
import got.common.fellowship.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class GOTPacketEditBanner implements IMessage {
	public int bannerID;
	public boolean playerSpecificProtection;
	public boolean selfProtection;
	public float alignmentProtection;
	public int whitelistLength;
	public String[] whitelistSlots;
	public int[] whitelistPerms;
	public int defaultPerms;

	public GOTPacketEditBanner(GOTEntityBanner banner) {
		bannerID = banner.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		bannerID = data.readInt();
		setPlayerSpecificProtection(data.readBoolean());
		setSelfProtection(data.readBoolean());
		setAlignmentProtection(data.readFloat());
		setWhitelistLength(data.readShort());
		boolean sendWhitelist = data.readBoolean();
		if (sendWhitelist) {
			setWhitelistSlots(new String[data.readShort()]);
			setWhitelistPerms(new int[getWhitelistSlots().length]);
			short index = 0;
			while ((index = data.readShort()) >= 0) {
				byte length = data.readByte();
				if (length == -1) {
					getWhitelistSlots()[index] = null;
					continue;
				}
				ByteBuf usernameBytes = data.readBytes(length);
				getWhitelistSlots()[index] = usernameBytes.toString(Charsets.UTF_8);
				getWhitelistPerms()[index] = data.readShort();
			}
		}
		setDefaultPerms(data.readShort());
	}

	public float getAlignmentProtection() {
		return alignmentProtection;
	}

	public int getDefaultPerms() {
		return defaultPerms;
	}

	public int getWhitelistLength() {
		return whitelistLength;
	}

	public int[] getWhitelistPerms() {
		return whitelistPerms;
	}

	public String[] getWhitelistSlots() {
		return whitelistSlots;
	}

	public boolean isPlayerSpecificProtection() {
		return playerSpecificProtection;
	}

	public boolean isSelfProtection() {
		return selfProtection;
	}

	public void setAlignmentProtection(float alignmentProtection) {
		this.alignmentProtection = alignmentProtection;
	}

	public void setDefaultPerms(int defaultPerms) {
		this.defaultPerms = defaultPerms;
	}

	public void setPlayerSpecificProtection(boolean playerSpecificProtection) {
		this.playerSpecificProtection = playerSpecificProtection;
	}

	public void setSelfProtection(boolean selfProtection) {
		this.selfProtection = selfProtection;
	}

	public void setWhitelistLength(int whitelistLength) {
		this.whitelistLength = whitelistLength;
	}

	public void setWhitelistPerms(int[] whitelistPerms) {
		this.whitelistPerms = whitelistPerms;
	}

	public void setWhitelistSlots(String[] whitelistSlots) {
		this.whitelistSlots = whitelistSlots;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(bannerID);
		data.writeBoolean(isPlayerSpecificProtection());
		data.writeBoolean(isSelfProtection());
		data.writeFloat(getAlignmentProtection());
		data.writeShort(getWhitelistLength());
		boolean sendWhitelist = getWhitelistSlots() != null;
		data.writeBoolean(sendWhitelist);
		if (sendWhitelist) {
			data.writeShort(getWhitelistSlots().length);
			for (int index = 0; index < getWhitelistSlots().length; ++index) {
				data.writeShort(index);
				String username = getWhitelistSlots()[index];
				if (StringUtils.isNullOrEmpty(username)) {
					data.writeByte(-1);
					continue;
				}
				byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
				data.writeByte(usernameBytes.length);
				data.writeBytes(usernameBytes);
				data.writeShort(getWhitelistPerms()[index]);
			}
			data.writeShort(-1);
		}
		data.writeShort(getDefaultPerms());
	}

	public static class Handler implements IMessageHandler<GOTPacketEditBanner, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketEditBanner packet, MessageContext context) {
			GOTEntityBanner banner;
			EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
			World world = entityplayer.worldObj;
			Entity bEntity = world.getEntityByID(packet.bannerID);
			if (bEntity instanceof GOTEntityBanner && (banner = (GOTEntityBanner) bEntity).canPlayerEditBanner(entityplayer)) {
				banner.setPlayerSpecificProtection(packet.isPlayerSpecificProtection());
				banner.setSelfProtection(packet.isSelfProtection());
				banner.setAlignmentProtection(packet.getAlignmentProtection());
				banner.resizeWhitelist(packet.getWhitelistLength());
				if (packet.getWhitelistSlots() != null) {
					for (int index = 0; index < packet.getWhitelistSlots().length; ++index) {
						if (index == 0) {
							continue;
						}
						String username = packet.getWhitelistSlots()[index];
						int perms = packet.getWhitelistPerms()[index];
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
				List<GOTBannerProtection.Permission> defaultPerms = GOTBannerWhitelistEntry.static_decodePermBitFlags(packet.getDefaultPerms());
				banner.setDefaultPermissions(defaultPerms);
			}
			return null;
		}
	}

}
