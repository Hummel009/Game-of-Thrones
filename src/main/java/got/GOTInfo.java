package got;

import net.minecraft.util.StatCollector;

public class GOTInfo {
	public static String[] description = {"§b" + StatCollector.translateToLocal("got.gui.authors") + " Hummel009, Octobrine, Agripas, VVVIP2, TIGASA, 安北都护府大都护-至高无上的Xiang SiMa, 贾昆·赫加尔01, RoobimWu, Ness, Axel Snow, Sword of the Morning, sashar2000r (Starvation), iliamakar, beautifulrobloxgirl01 " + StatCollector.translateToLocal("got.gui.authors.others")};

	public static String concatenateDescription(int startIndex) {
		StringBuilder sb = new StringBuilder();
		for (int i = startIndex; i < description.length; ++i) {
			sb.append(description[i]).append("\n\n");
		}
		return sb.toString();
	}
}
