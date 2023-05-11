package got.common.fellowship;

import com.mojang.authlib.GameProfile;
import got.GOT;
import got.common.GOTLevelData;
import got.common.entity.other.GOTEntityBanner;
import net.minecraft.util.StatCollector;

import java.util.UUID;

public class GOTFellowshipProfile extends GameProfile {
	public static String fellowshipPrefix = "f/";
	public String fellowshipName;

	public GOTFellowshipProfile(GOTEntityBanner banner, UUID fsID, String fsName) {
		super(fsID, fsName);
		fellowshipName = fsName;
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

	public GOTFellowship getFellowship() {
		return GOTFellowshipData.getActiveFellowship(getId());
	}

	public GOTFellowshipClient getFellowshipClient() {
		return GOTLevelData.getData(GOT.proxy.getClientPlayer()).getClientFellowshipByName(fellowshipName);
	}

	@Override
	public String getName() {
		return addFellowshipCode(super.getName());
	}
}
