package got.common.brotherhood;

import com.mojang.authlib.GameProfile;
import got.GOT;
import got.common.GOTLevelData;
import net.minecraft.util.StatCollector;

import java.util.Locale;
import java.util.UUID;

public class GOTBrotherhoodProfile extends GameProfile {
	private static final String BROTHERHOOD_PREFIX = "f/";

	private final String brotherhoodName;

	public GOTBrotherhoodProfile(UUID fsID, String fsName) {
		super(fsID, fsName);
		brotherhoodName = fsName;
	}

	public static String addBrotherhoodCode(String s) {
		return BROTHERHOOD_PREFIX + s;
	}

	public static String getBrotherhoodCodeHint() {
		return StatCollector.translateToLocalFormatted("got.gui.bannerEdit.brotherhoodHint", BROTHERHOOD_PREFIX);
	}

	public static boolean hasBrotherhoodCode(String s) {
		return s.toLowerCase(Locale.ROOT).startsWith(BROTHERHOOD_PREFIX.toLowerCase(Locale.ROOT));
	}

	public static String stripBrotherhoodCode(String s) {
		return s.substring(BROTHERHOOD_PREFIX.length());
	}

	public GOTBrotherhood getBrotherhood() {
		return GOTBrotherhoodData.getActiveBrotherhood(getId());
	}

	public GOTBrotherhoodClient getBrotherhoodClient() {
		return GOTLevelData.getData(GOT.proxy.getClientPlayer()).getClientBrotherhoodByName(brotherhoodName);
	}

	@Override
	public String getName() {
		return addBrotherhoodCode(super.getName());
	}
}