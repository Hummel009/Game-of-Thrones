package got.common.fellowship;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityBanner;
import net.minecraft.util.StatCollector;

public class GOTFellowshipProfile extends GameProfile {
	public static String fellowshipPrefix = "f/";
	public String fellowshipName;

	public GOTFellowshipProfile(GOTEntityBanner banner, UUID fsID, String fsName) {
		super(fsID, fsName);
		fellowshipName = fsName;
	}

	public GOTFellowship getFellowship() {
		GOTFellowship fs = GOTFellowshipData.getFellowship(getId());
		if (fs != null && !fs.isDisbanded()) {
			return fs;
		}
		return null;
	}

	public GOTFellowshipClient getFellowshipClient() {
		return GOTLevelData.getData(GOT.proxy.getClientPlayer()).getClientFellowshipByName(fellowshipName);
	}

	@Override
	public String getName() {
		return GOTFellowshipProfile.addFellowshipCode(super.getName());
	}

	public static String addFellowshipCode(String s) {
		return fellowshipPrefix + s;
	}

	public static String getFellowshipCodeHint() {
		return StatCollector.translateToLocalFormatted("got.gui.bannerEdit.fellowshipHint", fellowshipPrefix);
	}

	public static boolean hasFellowshipCode(String s) {
		return s.toLowerCase().startsWith(fellowshipPrefix.toLowerCase());
	}

	public static String stripFellowshipCode(String s) {
		return s.substring(fellowshipPrefix.length());
	}
}
