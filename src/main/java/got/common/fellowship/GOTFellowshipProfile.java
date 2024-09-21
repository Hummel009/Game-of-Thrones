package got.common.fellowship;

import com.mojang.authlib.GameProfile;
import got.GOT;
import got.common.GOTLevelData;
import net.minecraft.util.StatCollector;

import java.util.Locale;
import java.util.UUID;

public class GOTFellowshipProfile extends GameProfile {
	private static final String FELLOWSHIP_PREFIX = "f/";

	private final String fellowshipName;

	public GOTFellowshipProfile(UUID fsID, String fsName) {
		super(fsID, fsName);
		fellowshipName = fsName;
	}

	public static String addFellowshipCode(String s) {
		return FELLOWSHIP_PREFIX + s;
	}

	public static String getFellowshipCodeHint() {
		return StatCollector.translateToLocalFormatted("got.gui.bannerEdit.fellowshipHint", FELLOWSHIP_PREFIX);
	}

	public static boolean hasFellowshipCode(String s) {
		return s.toLowerCase(Locale.ROOT).startsWith(FELLOWSHIP_PREFIX.toLowerCase(Locale.ROOT));
	}

	public static String stripFellowshipCode(String s) {
		return s.substring(FELLOWSHIP_PREFIX.length());
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