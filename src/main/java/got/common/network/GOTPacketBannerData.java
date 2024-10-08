package got.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTBannerProtection;
import got.common.entity.other.inanimate.GOTEntityBanner;
import got.common.entity.other.utils.GOTBannerWhitelistEntry;
import got.common.fellowship.GOTFellowshipProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import java.util.List;

public class GOTPacketBannerData implements IMessage {
	private boolean playerSpecificProtection;
	private boolean selfProtection;
	private boolean structureProtection;
	private boolean thisPlayerHasPermission;
	private boolean openGui;
	private float reputationProtection;
	private int customRange;
	private int whitelistLength;
	private int defaultPerms;
	private int entityID;

	private int[] whitelistPerms;
	private String[] whitelistSlots;

	@SuppressWarnings("unused")
	public GOTPacketBannerData() {
	}

	public GOTPacketBannerData(int id, boolean flag) {
		entityID = id;
		openGui = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		openGui = data.readBoolean();
		playerSpecificProtection = data.readBoolean();
		selfProtection = data.readBoolean();
		structureProtection = data.readBoolean();
		customRange = data.readShort();
		reputationProtection = data.readFloat();
		whitelistLength = data.readShort();
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
		defaultPerms = data.readShort();
		thisPlayerHasPermission = data.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeInt(entityID);
		data.writeBoolean(openGui);
		data.writeBoolean(playerSpecificProtection);
		data.writeBoolean(selfProtection);
		data.writeBoolean(structureProtection);
		data.writeShort(customRange);
		data.writeFloat(reputationProtection);
		data.writeShort(whitelistLength);
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
		data.writeShort(defaultPerms);
		data.writeBoolean(thisPlayerHasPermission);
	}

	public void setPlayerSpecificProtection(boolean playerSpecificProtection) {
		this.playerSpecificProtection = playerSpecificProtection;
	}

	public void setSelfProtection(boolean selfProtection) {
		this.selfProtection = selfProtection;
	}

	public void setStructureProtection(boolean structureProtection) {
		this.structureProtection = structureProtection;
	}

	public void setCustomRange(int customRange) {
		this.customRange = customRange;
	}

	public void setReputationProtection(float reputationProtection) {
		this.reputationProtection = reputationProtection;
	}

	public void setWhitelistLength(int whitelistLength) {
		this.whitelistLength = whitelistLength;
	}

	public void setWhitelistSlots(String[] whitelistSlots) {
		this.whitelistSlots = whitelistSlots;
	}

	public void setWhitelistPerms(int[] whitelistPerms) {
		this.whitelistPerms = whitelistPerms;
	}

	public void setDefaultPerms(int defaultPerms) {
		this.defaultPerms = defaultPerms;
	}

	public void setThisPlayerHasPermission(boolean thisPlayerHasPermission) {
		this.thisPlayerHasPermission = thisPlayerHasPermission;
	}

	public static class Handler implements IMessageHandler<GOTPacketBannerData, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBannerData packet, MessageContext context) {
			World world = GOT.proxy.getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityBanner) {
				GOTEntityBanner banner = (GOTEntityBanner) entity;
				banner.setPlayerSpecificProtection(packet.playerSpecificProtection);
				banner.setSelfProtection(packet.selfProtection);
				banner.setStructureProtection(packet.structureProtection);
				banner.setCustomRange(packet.customRange);
				banner.setReputationProtection(packet.reputationProtection);
				banner.resizeWhitelist(packet.whitelistLength);
				for (int index = 0; index < packet.whitelistSlots.length; ++index) {
					String username = packet.whitelistSlots[index];
					if (StringUtils.isNullOrEmpty(username)) {
						banner.whitelistPlayer(index, null);
					} else if (GOTFellowshipProfile.hasFellowshipCode(username)) {
						String fsName = GOTFellowshipProfile.stripFellowshipCode(username);
						GOTFellowshipProfile profile = new GOTFellowshipProfile(null, fsName);
						banner.whitelistPlayer(index, profile);
					} else {
						GameProfile profile = new GameProfile(null, username);
						banner.whitelistPlayer(index, profile);
					}
					GOTBannerWhitelistEntry entry = banner.getWhitelistEntry(index);
					if (entry == null) {
						continue;
					}
					entry.decodePermBitFlags(packet.whitelistPerms[index]);
				}
				List<GOTBannerProtection.Permission> defaultPerms = GOTBannerWhitelistEntry.static_decodePermBitFlags(packet.defaultPerms);
				banner.setDefaultPermissions(defaultPerms);
				banner.setClientside_playerHasPermissionInSurvival(packet.thisPlayerHasPermission);
				if (packet.openGui) {
					GOT.proxy.displayBannerGui(banner);
				}
			}
			return null;
		}
	}
}