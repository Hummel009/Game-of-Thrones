package got.common.fellowship;

import java.util.*;

import got.common.database.GOTTitle;
import net.minecraft.item.ItemStack;

public class GOTFellowshipClient {
	private UUID fellowshipID;
	private String fellowshipName;
	private ItemStack fellowshipIcon;
	private boolean isOwned;
	private boolean isAdminned;
	private String ownerName;
	private List<String> memberNames = new ArrayList<>();
	private Map<String, GOTTitle.PlayerTitle> titleMap = new HashMap<>();
	private Set<String> adminNames = new HashSet<>();
	private boolean preventPVP;
	private boolean preventHiredFF;
	private boolean showMapLocations;

	public GOTFellowshipClient(UUID id, String name, boolean owned, boolean admin, String owner, List<String> members) {
		fellowshipID = id;
		fellowshipName = name;
		isOwned = owned;
		isAdminned = admin;
		ownerName = owner;
		memberNames = members;
	}

	public void addMember(String member, GOTTitle.PlayerTitle title) {
		if (!memberNames.contains(member)) {
			memberNames.add(member);
			titleMap.put(member, title);
		}
	}

	public List<String> getAllPlayerNames() {
		ArrayList<String> allPlayers = new ArrayList<>();
		allPlayers.add(ownerName);
		allPlayers.addAll(memberNames);
		return allPlayers;
	}

	public UUID getFellowshipID() {
		return fellowshipID;
	}

	public ItemStack getIcon() {
		return fellowshipIcon;
	}

	public int getMemberCount() {
		return memberNames.size() + 1;
	}

	public List<String> getMemberNames() {
		return memberNames;
	}

	public String getName() {
		return fellowshipName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public boolean getPreventHiredFriendlyFire() {
		return preventHiredFF;
	}

	public boolean getPreventPVP() {
		return preventPVP;
	}

	public boolean getShowMapLocations() {
		return showMapLocations;
	}

	public GOTTitle.PlayerTitle getTitleFor(String name) {
		return titleMap.get(name);
	}

	public boolean isAdmin(String name) {
		return adminNames.contains(name);
	}

	public boolean isAdminned() {
		return isAdminned;
	}

	public boolean isOwned() {
		return isOwned;
	}

	public boolean isPlayerIn(String name) {
		return ownerName.equals(name) || memberNames.contains(name);
	}

	public void removeAdmin(String admin, boolean adminned) {
		if (adminNames.contains(admin)) {
			adminNames.remove(admin);
			isAdminned = adminned;
		}
	}

	public void removeMember(String member) {
		if (memberNames.contains(member)) {
			memberNames.remove(member);
			if (adminNames.contains(member)) {
				adminNames.remove(member);
			}
			titleMap.remove(member);
		}
	}

	public void setAdmin(String admin, boolean adminned) {
		if (!adminNames.contains(admin)) {
			adminNames.add(admin);
			isAdminned = adminned;
		}
	}

	public void setAdmins(Set<String> admins) {
		adminNames = admins;
	}

	public void setIcon(ItemStack itemstack) {
		fellowshipIcon = itemstack;
	}

	public void setName(String name) {
		fellowshipName = name;
	}

	public void setOwner(String newOwner, boolean owned) {
		String prevOwner = ownerName;
		if (!prevOwner.equals(newOwner)) {
			if (!memberNames.contains(prevOwner)) {
				memberNames.add(0, prevOwner);
			}
			ownerName = newOwner;
			if (memberNames.contains(newOwner)) {
				memberNames.remove(newOwner);
			}
			if (adminNames.contains(newOwner)) {
				adminNames.remove(newOwner);
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

	public void setTitles(Map<String, GOTTitle.PlayerTitle> titles) {
		titleMap = titles;
	}

	public void updateDataFrom(GOTFellowshipClient other) {
		fellowshipName = other.fellowshipName;
		fellowshipIcon = other.fellowshipIcon;
		isOwned = other.isOwned;
		isAdminned = other.isAdminned;
		ownerName = other.ownerName;
		memberNames = other.memberNames;
		titleMap = other.titleMap;
		adminNames = other.adminNames;
		preventPVP = other.preventPVP;
		preventHiredFF = other.preventHiredFF;
		showMapLocations = other.showMapLocations;
	}

	public void updatePlayerTitle(String player, GOTTitle.PlayerTitle title) {
		if (title == null) {
			titleMap.remove(player);
		} else {
			titleMap.put(player, title);
		}
	}
}
