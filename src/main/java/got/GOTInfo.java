package got;

import net.minecraft.util.StatCollector;

public class GOTInfo {
	private static String[] description = { "\u00A7b" + StatCollector.translateToLocal("got.gui.authors") + " Hummel009, VVVIP2, TIGASA, \u8D3E\u6606\u00B7\u8D6B\u52A0\u5C1401, RoobimWu, \u5B89\u5317\u90FD\u62A4\u5E9C\u5927\u90FD\u62A4-\u81F3\u9AD8\u65E0\u4E0A\u7684Xiang SiMa, Ness, beautifulrobloxgirl01, Sword of the Morning, DDPAT Knife Series " + StatCollector.translateToLocal("got.gui.authors.others") };

	public static String concatenateDescription(int startIndex) {
		StringBuilder s = new StringBuilder();
		for (int i = startIndex; i < getDescription().length; ++i) {
			s.append(getDescription()[i]).append("\n\n");
		}
		return s.toString();
	}

	public static String[] getDescription() {
		return description.clone();
	}

	public static void setDescription(String[] description) {
		GOTInfo.description = description.clone();
	}
}