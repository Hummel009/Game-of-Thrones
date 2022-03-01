package got.common.fellowship;

import java.util.*;

import com.mojang.authlib.GameProfile;

import got.common.database.GOTTitle;
import net.minecraft.item.ItemStack;

public class GOTFellowshipClient {
	public UUID fellowshipID;
	public String fellowshipName;
	public ItemStack fellowshipIcon;
	public boolean isOwned;
	public boolean isAdminned;
	private UUID ownerUUID;
	private List<UUID> memberUUIDs = new ArrayList<>();
	private Map<UUID, String> usernameMap = new HashMap<>();
	private Map<UUID, GOTTitle.PlayerTitle> titleMap = new HashMap<>();
	private Set<UUID> adminUUIDs = new HashSet<>();
	public boolean preventPVP;
	public boolean preventHiredFF;
	public boolean showMapLocations;

	public GOTFellowshipClient(UUID id, String name, boolean owned, boolean admin, GameProfile owner, List<GameProfile> members) {
		fellowshipID = id;
		fellowshipName = name;
		isOwned = owned;
		isAdminned = admin;
		ownerUUID = owner.getId();
		usernameMap.put(ownerUUID, owner.getName());
		for (GameProfile member : members) {
			memberUUIDs.add(member.getId());
			usernameMap.put(member.getId(), member.getName());
		}
	}

	public void addMember(GameProfile member, GOTTitle.PlayerTitle title) {
		UUID memberUuid = member.getId();
		if (!memberUUIDs.contains(memberUuid)) {
			memberUUIDs.add(memberUuid);
			usernameMap.put(memberUuid, member.getName());
			titleMap.put(memberUuid, title);
		}
	}

	public boolean containsPlayer(UUID playerUuid) {
		return ownerUUID.equals(playerUuid) || memberUUIDs.contains(playerUuid);
	}

	public boolean containsPlayerUsername(String username) {
		return usernameMap.containsValue(username);
	}

	public List<GameProfile> getAllPlayerProfiles() {
		return getProfilesFor(getAllPlayerUuids());
	}

	public List<UUID> getAllPlayerUuids() {
		ArrayList<UUID> allPlayers = new ArrayList<>();
		allPlayers.add(ownerUUID);
		allPlayers.addAll(memberUUIDs);
		return allPlayers;
	}

	public UUID getFellowshipID() {
		return fellowshipID;
	}

	public ItemStack getIcon() {
		return fellowshipIcon;
	}

	public List<GameProfile> getMemberProfiles() {
		return getProfilesFor(memberUUIDs);
	}

	public List<UUID> getMemberUuids() {
		return memberUUIDs;
	}

	public String getName() {
		return fellowshipName;
	}

	public GameProfile getOwnerProfile() {
		return getProfileFor(ownerUUID);
	}

	public UUID getOwnerUuid() {
		return ownerUUID;
	}

	public int getPlayerCount() {
		return memberUUIDs.size() + 1;
	}

	public boolean getPreventHiredFriendlyFire() {
		return preventHiredFF;
	}

	public boolean getPreventPVP() {
		return preventPVP;
	}

	private GameProfile getProfileFor(UUID playerUuid) {
		return new GameProfile(playerUuid, getUsernameFor(playerUuid));
	}

	private List<GameProfile> getProfilesFor(List<UUID> playerUuids) {
		ArrayList<GameProfile> list = new ArrayList<>();
		for (UUID playerUuid : playerUuids) {
			list.add(getProfileFor(playerUuid));
		}
		return list;
	}

	public boolean getShowMapLocations() {
		return showMapLocations;
	}

	public GOTTitle.PlayerTitle getTitleFor(UUID playerUuid) {
		return titleMap.get(playerUuid);
	}

	public String getUsernameFor(UUID playerUuid) {
		return usernameMap.get(playerUuid);
	}

	public boolean isAdmin(UUID playerUuid) {
		return adminUUIDs.contains(playerUuid);
	}

	public boolean isAdminned() {
		return isAdminned;
	}

	public boolean isOwned() {
		return isOwned;
	}

	public void removeAdmin(UUID playerUuid, boolean adminned) {
		if (adminUUIDs.contains(playerUuid)) {
			adminUUIDs.remove(playerUuid);
			isAdminned = adminned;
		}
	}

	public void removeMember(GameProfile member) {
		UUID memberUuid = member.getId();
		if (memberUUIDs.contains(memberUuid)) {
			memberUUIDs.remove(memberUuid);
			usernameMap.remove(memberUuid);
			if (adminUUIDs.contains(memberUuid)) {
				adminUUIDs.remove(memberUuid);
			}
			titleMap.remove(memberUuid);
		}
	}

	public void setAdmin(UUID playerUuid, boolean adminned) {
		if (!adminUUIDs.contains(playerUuid)) {
			adminUUIDs.add(playerUuid);
			isAdminned = adminned;
		}
	}

	public void setAdmins(Set<UUID> admins) {
		adminUUIDs = admins;
	}

	public void setIcon(ItemStack itemstack) {
		fellowshipIcon = itemstack;
	}

	public void setName(String name) {
		fellowshipName = name;
	}

	public void setOwner(GameProfile newOwner, boolean owned) {
		UUID prevOwnerUuid = ownerUUID;
		UUID newOwnerUuid = newOwner.getId();
		if (!prevOwnerUuid.equals(newOwnerUuid)) {
			if (!memberUUIDs.contains(prevOwnerUuid)) {
				memberUUIDs.add(0, prevOwnerUuid);
			}
			ownerUUID = newOwnerUuid;
			usernameMap.put(ownerUUID, newOwner.getName());
			if (memberUUIDs.contains(newOwnerUuid)) {
				memberUUIDs.remove(newOwnerUuid);
			}
			if (adminUUIDs.contains(newOwnerUuid)) {
				adminUUIDs.remove(newOwnerUuid);
			}
			isOwned = owned;
			if (isOwned) {
				isAdminned = false;
			}
		}
	}

	public void setPreventHiredFriendlyFire(boolean flag) {
		preventHiredFF = flag;
	}

	public void setPreventPVP(boolean flag) {
		preventPVP = flag;
	}

	public void setShowMapLocations(boolean flag) {
		showMapLocations = flag;
	}

	public void setTitles(Map<UUID, GOTTitle.PlayerTitle> titles) {
		titleMap = titles;
	}

	public void updateDataFrom(GOTFellowshipClient other) {
		fellowshipName = other.fellowshipName;
		fellowshipIcon = other.fellowshipIcon;
		isOwned = other.isOwned;
		isAdminned = other.isAdminned;
		ownerUUID = other.ownerUUID;
		memberUUIDs = other.memberUUIDs;
		usernameMap = other.usernameMap;
		titleMap = other.titleMap;
		adminUUIDs = other.adminUUIDs;
		preventPVP = other.preventPVP;
		preventHiredFF = other.preventHiredFF;
		showMapLocations = other.showMapLocations;
	}

	public void updatePlayerTitle(UUID playerUuid, GOTTitle.PlayerTitle title) {
		if (title == null) {
			titleMap.remove(playerUuid);
		} else {
			titleMap.put(playerUuid, title);
		}
	}
}
