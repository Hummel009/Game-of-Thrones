package got.common.network;

import java.util.List;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.GOTBannerProtection;
import got.common.entity.other.*;
import got.common.fellowship.GOTFellowshipProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class GOTPacketBannerData implements IMessage {
	private int entityID;
	private boolean openGui;
	private boolean playerSpecificProtection;
	private boolean selfProtection;
	private boolean structureProtection;
	private int customRange;
	private float alignmentProtection;
	private int whitelistLength;
	private String[] whitelistSlots;
	private int[] whitelistPerms;
	private int defaultPerms;
	private boolean thisPlayerHasPermission;

	public GOTPacketBannerData(int id, boolean flag) {
		entityID = id;
		openGui = flag;
	}

	@Override
	public void fromBytes(ByteBuf data) {
		entityID = data.readInt();
		openGui = data.readBoolean();
		setPlayerSpecificProtection(data.readBoolean());
		setSelfProtection(data.readBoolean());
		setStructureProtection(data.readBoolean());
		setCustomRange(data.readShort());
		setAlignmentProtection(data.readFloat());
		setWhitelistLength(data.readShort());
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
		setDefaultPerms(data.readShort());
		setThisPlayerHasPermission(data.readBoolean());
	}

	public float getAlignmentProtection() {
		return alignmentProtection;
	}

	public int getCustomRange() {
		return customRange;
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

	public boolean isStructureProtection() {
		return structureProtection;
	}

	public boolean isThisPlayerHasPermission() {
		return thisPlayerHasPermission;
	}

	public void setAlignmentProtection(float alignmentProtection) {
		this.alignmentProtection = alignmentProtection;
	}

	public void setCustomRange(int customRange) {
		this.customRange = customRange;
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

	public void setStructureProtection(boolean structureProtection) {
		this.structureProtection = structureProtection;
	}

	public void setThisPlayerHasPermission(boolean thisPlayerHasPermission) {
		this.thisPlayerHasPermission = thisPlayerHasPermission;
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
		data.writeInt(entityID);
		data.writeBoolean(openGui);
		data.writeBoolean(isPlayerSpecificProtection());
		data.writeBoolean(isSelfProtection());
		data.writeBoolean(isStructureProtection());
		data.writeShort(getCustomRange());
		data.writeFloat(getAlignmentProtection());
		data.writeShort(getWhitelistLength());
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
		data.writeShort(getDefaultPerms());
		data.writeBoolean(isThisPlayerHasPermission());
	}

	public static class Handler implements IMessageHandler<GOTPacketBannerData, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBannerData packet, MessageContext context) {
			World world = GOT.getProxy().getClientWorld();
			Entity entity = world.getEntityByID(packet.entityID);
			if (entity instanceof GOTEntityBanner) {
				GOTEntityBanner banner = (GOTEntityBanner) entity;
				banner.setPlayerSpecificProtection(packet.isPlayerSpecificProtection());
				banner.setSelfProtection(packet.isSelfProtection());
				banner.setStructureProtection(packet.isStructureProtection());
				banner.setCustomRange(packet.getCustomRange());
				banner.setAlignmentProtection(packet.getAlignmentProtection());
				banner.resizeWhitelist(packet.getWhitelistLength());
				for (int index = 0; index < packet.getWhitelistSlots().length; ++index) {
					String username = packet.getWhitelistSlots()[index];
					if (StringUtils.isNullOrEmpty(username)) {
						banner.whitelistPlayer(index, null);
					} else if (GOTFellowshipProfile.hasFellowshipCode(username)) {
						String fsName = GOTFellowshipProfile.stripFellowshipCode(username);
						GOTFellowshipProfile profile = new GOTFellowshipProfile(banner, null, fsName);
						banner.whitelistPlayer(index, profile);
					} else {
						GameProfile profile = new GameProfile(null, username);
						banner.whitelistPlayer(index, profile);
					}
					GOTBannerWhitelistEntry entry = banner.getWhitelistEntry(index);
					if (entry == null) {
						continue;
					}
					entry.decodePermBitFlags(packet.getWhitelistPerms()[index]);
				}
				List<GOTBannerProtection.Permission> defaultPerms = GOTBannerWhitelistEntry.static_decodePermBitFlags(packet.getDefaultPerms());
				banner.setDefaultPermissions(defaultPerms);
				banner.setClientside_playerHasPermissionInSurvival(packet.isThisPlayerHasPermission());
				if (packet.openGui) {
					GOT.getProxy().displayBannerGui(banner);
				}
			}
			return null;
		}
	}

}
