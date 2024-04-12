package got.common;

import got.common.util.GOTReflection;
import net.minecraft.event.HoverEvent;
import net.minecraftforge.common.util.EnumHelper;

@SuppressWarnings("PublicField")
public class GOTChatEvents {
	private static final Class<?>[][] HOVER_PARAMS = new Class[][]{{HoverEvent.Action.class, String.class, Boolean.TYPE}};

	public static HoverEvent.Action showGotAchievement;

	private GOTChatEvents() {
	}

	public static void onInit() {
		showGotAchievement = EnumHelper.addEnum(HOVER_PARAMS, HoverEvent.Action.class, "SHOW_GOT_ACHIEVEMENT", "show_got_achievement", true);
		GOTReflection.getHoverEventMappings().put(showGotAchievement.getCanonicalName(), showGotAchievement);
	}
}