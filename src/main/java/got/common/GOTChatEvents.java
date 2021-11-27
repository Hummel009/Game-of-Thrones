package got.common;

import got.common.util.GOTReflection;
import net.minecraft.event.HoverEvent;
import net.minecraftforge.common.util.EnumHelper;

public class GOTChatEvents {
	public static Class[][] hoverParams = { { HoverEvent.Action.class, String.class, Boolean.TYPE } };
	public static HoverEvent.Action SHOW_GOT_ACHIEVEMENT;

	public static void onInit() {
		SHOW_GOT_ACHIEVEMENT = EnumHelper.addEnum(hoverParams, HoverEvent.Action.class, "SHOW_GOT_ACHIEVEMENT", "show_got_achievement", true);
		GOTReflection.getHoverEventMappings().put(SHOW_GOT_ACHIEVEMENT.getCanonicalName(), SHOW_GOT_ACHIEVEMENT);
	}
}