package got.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.brotherhood.GOTBrotherhood;
import got.common.brotherhood.GOTBrotherhoodClient;
import got.common.database.GOTTitle;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.UsernameCache;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

public class GOTPacketBrotherhood implements IMessage {
	private final List<GameProfile> members = new ArrayList<>();
	private final Map<UUID, GOTTitle.PlayerTitle> titleMap = new HashMap<>();
	private final Set<UUID> adminUuids = new HashSet<>();

	private UUID brotherhoodID;
	private String brotherhoodName;
	private ItemStack brotherhoodIcon;
	private GameProfile owner;

	private boolean isInvite;
	private boolean isOwned;
	private boolean isAdminned;
	private boolean preventPVP;
	private boolean preventHiredFF;
	private boolean showMapLocations;

	@SuppressWarnings("unused")
	public GOTPacketBrotherhood() {
	}

	public GOTPacketBrotherhood(GOTPlayerData playerData, GOTBrotherhood fs, boolean invite) {
		brotherhoodID = fs.getBrotherhoodID();
		isInvite = invite;
		brotherhoodName = fs.getName();
		brotherhoodIcon = fs.getIcon();
		UUID thisPlayer = playerData.getPlayerUUID();
		isOwned = fs.isOwner(thisPlayer);
		isAdminned = fs.isAdmin(thisPlayer);
		List<UUID> playerIDs = fs.getAllPlayerUUIDs();
		for (UUID player : playerIDs) {
			GameProfile profile = getPlayerProfileWithUsername(player);
			if (fs.isOwner(player)) {
				owner = profile;
			} else {
				members.add(profile);
			}
			GOTTitle.PlayerTitle title = GOTLevelData.getPlayerTitleWithOfflineCache(player);
			if (title != null) {
				titleMap.put(player, title);
			}
			if (!fs.isAdmin(player)) {
				continue;
			}
			adminUuids.add(player);
		}
		preventPVP = fs.getPreventPVP();
		preventHiredFF = fs.getPreventHiredFriendlyFire();
		showMapLocations = fs.getShowMapLocations();
	}

	public static GameProfile getPlayerProfileWithUsername(UUID player) {
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(player);
		if (profile == null || StringUtils.isBlank(profile.getName())) {
			String name = UsernameCache.getLastKnownUsername(player);
			if (name != null) {
				return new GameProfile(player, name);
			}
			profile = new GameProfile(player, "");
			MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
		}
		return profile;
	}

	public static GameProfile readPlayerUuidAndUsername(ByteBuf data) {
		UUID uuid = new UUID(data.readLong(), data.readLong());
		byte nameLength = data.readByte();
		if (nameLength >= 0) {
			ByteBuf nameBytes = data.readBytes(nameLength);
			String username = nameBytes.toString(Charsets.UTF_8);
			return new GameProfile(uuid, username);
		}
		return null;
	}

	public static void writePlayerUuidAndUsername(ByteBuf data, GameProfile profile) {
		UUID uuid = profile.getId();
		String username = profile.getName();
		data.writeLong(uuid.getMostSignificantBits());
		data.writeLong(uuid.getLeastSignificantBits());
		byte[] usernameBytes = username.getBytes(Charsets.UTF_8);
		data.writeByte(usernameBytes.length);
		data.writeBytes(usernameBytes);
	}

	@Override
	public void fromBytes(ByteBuf data) {
		brotherhoodID = new UUID(data.readLong(), data.readLong());
		isInvite = data.readBoolean();
		byte fsNameLength = data.readByte();
		ByteBuf fsNameBytes = data.readBytes(fsNameLength);
		brotherhoodName = fsNameBytes.toString(Charsets.UTF_8);
		NBTTagCompound iconData = new NBTTagCompound();
		try {
			iconData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
		} catch (IOException e) {
			FMLLog.severe("Hummel009: Error reading brotherhood icon data");
			e.printStackTrace();
		}
		brotherhoodIcon = ItemStack.loadItemStackFromNBT(iconData);
		isOwned = data.readBoolean();
		isAdminned = data.readBoolean();
		owner = readPlayerUuidAndUsername(data);
		readTitleForPlayer(data, owner.getId());
		int numMembers = data.readInt();
		for (int i = 0; i < numMembers; ++i) {
			GameProfile member = readPlayerUuidAndUsername(data);
			if (member == null) {
				continue;
			}
			members.add(member);
			UUID memberUuid = member.getId();
			readTitleForPlayer(data, memberUuid);
			boolean admin = data.readBoolean();
			if (!admin) {
				continue;
			}
			adminUuids.add(memberUuid);
		}
		preventPVP = data.readBoolean();
		preventHiredFF = data.readBoolean();
		showMapLocations = data.readBoolean();
	}

	private void readTitleForPlayer(ByteBuf data, UUID playerUuid) {
		GOTTitle.PlayerTitle playerTitle = GOTTitle.PlayerTitle.readNullableTitle(data);
		if (playerTitle != null) {
			titleMap.put(playerUuid, playerTitle);
		}
	}

	@Override
	public void toBytes(ByteBuf data) {
		data.writeLong(brotherhoodID.getMostSignificantBits());
		data.writeLong(brotherhoodID.getLeastSignificantBits());
		data.writeBoolean(isInvite);
		byte[] fsNameBytes = brotherhoodName.getBytes(Charsets.UTF_8);
		data.writeByte(fsNameBytes.length);
		data.writeBytes(fsNameBytes);
		NBTTagCompound iconData = new NBTTagCompound();
		if (brotherhoodIcon != null) {
			brotherhoodIcon.writeToNBT(iconData);
		}
		try {
			new PacketBuffer(data).writeNBTTagCompoundToBuffer(iconData);
		} catch (IOException e) {
			FMLLog.severe("Hummel009: Error writing brotherhood icon data");
			e.printStackTrace();
		}
		data.writeBoolean(isOwned);
		data.writeBoolean(isAdminned);
		writePlayerUuidAndUsername(data, owner);
		GOTTitle.PlayerTitle.writeNullableTitle(data, titleMap.get(owner.getId()));
		data.writeInt(members.size());
		for (GameProfile member : members) {
			UUID memberUuid = member.getId();
			GOTTitle.PlayerTitle title = titleMap.get(memberUuid);
			boolean admin = adminUuids.contains(memberUuid);
			writePlayerUuidAndUsername(data, member);
			GOTTitle.PlayerTitle.writeNullableTitle(data, title);
			data.writeBoolean(admin);
		}
		data.writeBoolean(preventPVP);
		data.writeBoolean(preventHiredFF);
		data.writeBoolean(showMapLocations);
	}

	public static class Handler implements IMessageHandler<GOTPacketBrotherhood, IMessage> {
		@Override
		public IMessage onMessage(GOTPacketBrotherhood packet, MessageContext context) {
			GOTBrotherhoodClient brotherhood = new GOTBrotherhoodClient(packet.brotherhoodID, packet.brotherhoodName, packet.isOwned, packet.isAdminned, packet.owner, packet.members);
			brotherhood.setTitles(packet.titleMap);
			brotherhood.setAdmins(packet.adminUuids);
			brotherhood.setIcon(packet.brotherhoodIcon);
			brotherhood.setPreventPVP(packet.preventPVP);
			brotherhood.setPreventHiredFriendlyFire(packet.preventHiredFF);
			brotherhood.setShowMapLocations(packet.showMapLocations);
			EntityPlayer entityplayer = GOT.proxy.getClientPlayer();
			if (packet.isInvite) {
				GOTLevelData.getData(entityplayer).addOrUpdateClientBrotherhoodInvite(brotherhood);
			} else {
				GOTLevelData.getData(entityplayer).addOrUpdateClientBrotherhood(brotherhood);
			}
			return null;
		}
	}
}