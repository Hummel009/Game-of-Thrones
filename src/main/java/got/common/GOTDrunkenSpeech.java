package got.common;

import java.util.Random;

public class GOTDrunkenSpeech {
	private static Random rand = new Random();

	public static String getDrunkenSpeech(String speech, float chance) {
		StringBuilder newSpeech = new StringBuilder();
		for (int i = 0; i < speech.length(); ++i) {
			String s = speech.substring(i, i + 1);
			if (rand.nextFloat() < chance) {
				s = "";
			} else if (rand.nextFloat() < chance * 0.4f) {
				s = " *hic* ";
			}
			newSpeech.append(s);
		}
		return newSpeech.toString();
	}
}
