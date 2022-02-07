package got.common.network;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import got.GOT;
import got.common.*;
import got.common.database.GOTTitle;
import got.common.fellowship.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;

public class GOTPacketFellowship implements IMessage {
	public UUID fellowshipID;
	public boolean isInvite;
	public String fellowshipName;
	public ItemStack fellowshipIcon;
	public boolean isOwned;
	public boolean isAdminned;
	public String ownerName;
	public List<String> memberNames = new ArrayList<>();
	public Map<String, GOTTitle.PlayerTitle> titleMap = new HashMap<>();
	public Set<String> adminNames = new HashSet<>();
	public boolean preventPVP;
	public boolean preventHiredFF;
	public boolean showMapLocations;

	public GOTPacketFellowship() {
	}

	public GOTPacketFellowship(GOTPlayerData playerData, GOTFellowship fs, boolean invite) {
		fellowshipID = fs.getFellowshipID();
		isInvite = invite;
		fellowshipName = fs.getName();
		fellowshipIcon = fs.getIcon();
		UUID thisPlayer = playerData.getPlayerUUID();
		isOwned = fs.isOwner(thisPlayer);
		isAdminned = fs.isAdmin(thisPlayer);
		List<UUID> playerIDs = fs.getAllPlayerUUIDs();
		for (UUID player : playerIDs) {
			String username = GOTPacketFellowship.getPlayerUsername(player);
			if (fs.isOwner(player)) {
				ownerName = username;
			} else {
				memberNames.add(username);
			}
			GOTPlayerData data = GOTLevelData.getData(player);
			GOTTitle.PlayerTitle title = data.getPlayerTitle();
			if (title != null) {
				titleMap.put(username, title);
			}
			if (!fs.isAdmin(player)) {
				continue;
			}
			adminNames.add(username);
		}
		preventPVP = fs.getPreventPVP();
		preventHiredFF = fs.getPreventHiredFriendlyFire();
		showMapLocations = fs.getShowMapLocations();
	}

	@Override
	public void fromBytes(ByteBuf data) {
		fellowshipID = new UUID(data.readLong(), data.readLong());
		isInvite = data.readBoolean();
		byte fsNameLength = data.readByte();
		ByteBuf fsNameBytes = data.readBytes(fsNameLength);
		fellowshipName = fsNameBytes.toString(Charsets.UTF_8);
		NBTTagCompound iconData = new NBTTagCompound();
		try {
			iconData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("GOT: Error reading fellowship icon data");
			e.printStackTrace();
		}
		fellowshipIcon = ItemStack.loadItemStackFromNBT(iconData);
		isOwned = data.readBoolean();
		isAdminned = data.readBoolean();
		ownerName = readUsername(data);
		readTitleForUsername(data, ownerName);
		String memberName = null;
		while ((memberName = readUsername(data)) != null) {
			memberNames.add(memberName);
			readTitleForUsername(data, memberName);
			boolean admin = data.readBoolean();
			if (!admin) {
				continue;
			}
			adminNames.add(memberName);
		}
		preventPVP = data.readBoolean();
		preventHiredFF = data.readBoolean();
		showMapLocations = data.readBoolean();
	}

	public void readTitleForUsername(ByteBuf data, String username) {
		GOTTitle.PlayerTitle playerTitle = GOTTitle.PlayerTitle.readNullableTitle(data);
		if (playerTitle != null) {
			titleMap.put(username, playerTitle);
		}
	}

	public String readUsername(ByteBuf data) {
		byte nameLength = data.readByte();
		if (nameLength >= 0) {
			ByteBuf nameBytes = data.readBytes(nameLength);
			return nameBytes.toString(Charsets.UTF_8);
		}
		return null;
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(fellowshipID.getMostSignificantBits());
		data.writeLong(fellowshipID.getLeastSignificantBits());
		data.writeBoolean(isInvite);
		byte[] fsNameBytes = fellowshipName.getBytes(Charsets.UTF_8);
		data.writeByte(fsNameBytes.length);
		data.writeBytes(fsNameBytes);
		NBTTagCompound iconData = new NBTTagCompound();
		if (fellowshipIcon != null) {
			fellowshipIcon.writeToNBT(iconData);
		}
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(iconData);
		} catch (IOException e) {
			FMLLog.severe("GOT: Error writing fellowship icon data");
			e.printStackTrace();
		}
		data.writeBoolean(isOwned);
		data.writeBoolean(isAdminned);
		writeUsername(data, ownerName);
		GOTTitle.PlayerTitle.writeNullableTitle(data, titleMap.get(ownerName));
		for (String memberName : memberNames) {
			GOTTitle.PlayerTitle title = titleMap.get(memberName);
			boolean admin = adminNames.contains(memberName);
			writeUsername(data, memberName);
			GOTTitle.PlayerTitle.writeNullableTitle(data, title);
			data.writeBoolean(admin);
		}
		data.writeByte(-1);
		data.writeBoolean(preventPVP);
		data.writeBoolean(preventHiredFF);
		data.writeBoolean(showMapLocations);
	}

	public void writeUsername(ByteBuf data, String username) {
		byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
		data.writeByte(usernameBytes.length);
		data.writeBytes(usernameBytes);
	}

	public static String getPlayerUsername(UUID player) {
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(player);
		if (profile == null || StringUtils.isBlank(profile.getName())) {
			String name = UsernameCache.getLastKnownUsername(player);
			if (name != null) {
				profile = new GameProfile(player, name);
			} else {
				profile = new GameProfile(player, "");
				MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
			}
		}
		return profile.getName();
	}

	public static class Handler implements IMessageHandler<GOTPacketFellowship, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketFellowship packet, MessageContext context) {
			GOTFellowshipClient fellowship = new GOTFellowshipClient(packet.fellowshipID, packet.fellowshipName, packet.isOwned, packet.isAdminned, packet.ownerName, packet.memberNames);
			fellowship.setTitles(packet.titleMap);
			fellowship.setAdmins(packet.adminNames);
			fellowship.setIcon(packet.fellowshipIcon);
			fellowship.setPreventPVP(packet.preventPVP);
			fellowship.setPreventHiredFriendlyFire(packet.preventHiredFF);
			fellowship.setShowMapLocations(packet.showMapLocations);
			EntityPlayer entityplayer = GOT.getProxy().getClientPlayer();
			if (packet.isInvite) {
				GOTLevelData.getData(entityplayer).addOrUpdateClientFellowshipInvite(fellowship);
			} else {
				GOTLevelData.getData(entityplayer).addOrUpdateClientFellowship(fellowship);
			}
			return null;
		}
	}

}
