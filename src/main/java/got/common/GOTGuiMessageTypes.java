package got.common;

import net.minecraft.util.StatCollector;

public enum GOTGuiMessageTypes {
	ENCHANTING("enchanting"), FRIENDLY_FIRE("friendlyFire");

	public String messageName;

	GOTGuiMessageTypes(String s) {
		messageName = s;
	}

	public static GOTGuiMessageTypes forSaveName(String name) {
		for (GOTGuiMessageTypes message : values()) {
			if (message.messageName.equals(name)) {
				return message;
			}
		}
		return null;
	}

	public String getMessage() {
		return StatCollector.translateToLocal("got.gui.message." + messageName);
	}

	public String getSaveName() {
		return messageName;
	}
}
