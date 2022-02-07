package got.common;

import got.common.util.GOTReflection;
import net.minecraft.event.HoverEvent;
import net.minecraftforge.common.util.EnumHelper;

public class GOTChatEvents {
	private static Class[][] hoverParams = { { HoverEvent.Action.class, String.class, Boolean.TYPE } };
	private static HoverEvent.Action SHOW_GOT_ACHIEVEMENT;

	public static HoverEvent.Action getShowAchievementGOT() {
		return SHOW_GOT_ACHIEVEMENT;
	}

	public static void onInit() {
		setShowAchievementGOT(EnumHelper.addEnum(hoverParams, HoverEvent.Action.class, "SHOW_GOT_ACHIEVEMENT", "show_got_achievement", true));
		GOTReflection.getHoverEventMappings().put(getShowAchievementGOT().getCanonicalName(), getShowAchievementGOT());
	}

	public static void setShowAchievementGOT(HoverEvent.Action sHOW_GOT_ACHIEVEMENT) {
		SHOW_GOT_ACHIEVEMENT = sHOW_GOT_ACHIEVEMENT;
	}
}