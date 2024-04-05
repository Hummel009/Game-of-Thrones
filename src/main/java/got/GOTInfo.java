package got;

import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Collection;

public class GOTInfo {
	private static final Collection<String> AUTHORS = new ArrayList<>();

	static {
		AUTHORS.add("Hummel009");
		AUTHORS.add("Mon1tor");
		AUTHORS.add("Octobrine");
		AUTHORS.add("安北都护府大都护-至高无上的Xiang SiMa");
		AUTHORS.add("贾昆·赫加尔01");
		AUTHORS.add("RoobimWu");
		AUTHORS.add("DAndMaster");
		AUTHORS.add("sashar2000r");
		AUTHORS.add("Danvintius Bookix");
		AUTHORS.add("iliamakar");
		AUTHORS.add("Tabula");
		AUTHORS.add("beautifulrobloxgirl01");
		AUTHORS.add("Agripas");
		AUTHORS.add("Ness");
		AUTHORS.add("Axel Snow");
		AUTHORS.add("Sword of the Morning");
		AUTHORS.add("Valence");
		AUTHORS.add("Alqualindё");
		AUTHORS.add("Jaehaerys");
		AUTHORS.add("VVVIP2");
		AUTHORS.add("TIGASA");
		AUTHORS.add("Alex.Tollar");
		AUTHORS.add("Maglor");
		AUTHORS.add("StalkerKir");
		AUTHORS.add("Coolaga/GualaBoy");
		AUTHORS.add("Amandil");
		AUTHORS.add("Arbeit");
	}

	public static String[] description = {"§b" + StatCollector.translateToLocal("got.gui.authors") + " " + String.join(", ", AUTHORS) + " " + StatCollector.translateToLocal("got.gui.authors.others")};

	public static String concatenateDescription(int startIndex) {
		StringBuilder sb = new StringBuilder();
		for (int i = startIndex; i < description.length; ++i) {
			sb.append(description[i]).append("\n\n");
		}
		return sb.toString();
	}
}