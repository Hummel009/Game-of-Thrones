package got.common;

import net.minecraft.util.StatCollector;

public enum GOTGuiMessageTypes {
	FRIENDLY_FIRE("friendlyFire");

	public String messageName;

	GOTGuiMessageTypes(String s) {
		messageName = s;
	}

	public String getMessage() {
		return StatCollector.translateToLocal("got.gui.message." + messageName);
	}

	public String getSaveName() {
		return messageName;
	}

	public static GOTGuiMessageTypes forSaveName(String name) {
		for (GOTGuiMessageTypes message : GOTGuiMessageTypes.values()) {
			if (!message.getSaveName().equals(name)) {
				continue;
			}
			return message;
		}
		return null;
	}
}
