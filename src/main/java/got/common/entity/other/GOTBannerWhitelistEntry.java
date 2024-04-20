package got.common.entity.other;

import com.mojang.authlib.GameProfile;
import got.common.GOTBannerProtection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class GOTBannerWhitelistEntry {
	public GameProfile profile;
	public Set<GOTBannerProtection.Permission> perms = EnumSet.noneOf(GOTBannerProtection.Permission.class);

	public GOTBannerWhitelistEntry(GameProfile p) {
		profile = p;
		if (profile == null) {
			throw new IllegalArgumentException("Banner whitelist entry cannot have a null profile!");
		}
	}

	public static List<GOTBannerProtection.Permission> static_decodePermBitFlags(int i) {
		List<GOTBannerProtection.Permission> decoded = new ArrayList<>();
		for (GOTBannerProtection.Permission p : GOTBannerProtection.Permission.values()) {
			if ((i & p.getBitFlag()) == 0) {
				continue;
			}
			decoded.add(p);
		}
		return decoded;
	}

	public static int static_encodePermBitFlags(Iterable<GOTBannerProtection.Permission> permList) {
		int i = 0;
		for (GOTBannerProtection.Permission p : permList) {
			i |= p.getBitFlag();
		}
		return i;
	}

	public void addPermission(GOTBannerProtection.Permission p) {
		perms.add(p);
	}

	public boolean allowsPermission(GOTBannerProtection.Permission p) {
		return isPermissionEnabled(GOTBannerProtection.Permission.FULL) || isPermissionEnabled(p);
	}

	public void clearPermissions() {
		perms.clear();
	}

	public void decodePermBitFlags(int i) {
		setPermissions(static_decodePermBitFlags(i));
	}

	public int encodePermBitFlags() {
		return static_encodePermBitFlags(perms);
	}

	public boolean isPermissionEnabled(GOTBannerProtection.Permission p) {
		return perms.contains(p);
	}

	public Set<GOTBannerProtection.Permission> listPermissions() {
		return perms;
	}

	public void removePermission(GOTBannerProtection.Permission p) {
		perms.remove(p);
	}

	public void setFullPerms() {
		clearPermissions();
		addPermission(GOTBannerProtection.Permission.FULL);
	}

	public void setPermissions(Iterable<GOTBannerProtection.Permission> perms) {
		clearPermissions();
		for (GOTBannerProtection.Permission p : perms) {
			addPermission(p);
		}
	}
}