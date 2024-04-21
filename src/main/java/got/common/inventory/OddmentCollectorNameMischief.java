package got.common.inventory;

import java.util.Random;

public class OddmentCollectorNameMischief {
	private OddmentCollectorNameMischief() {
	}

	@SuppressWarnings("StringConcatenationMissingWhitespace")
	public static String garbleName(String name, Random rand) {
		String name1 = name;
		int deletes = rand.nextInt(3);
		for (int l = 0; l < deletes; ++l) {
			int x;
			if (name1.length() <= 3 || isFormattingCharacter(name1, x = rand.nextInt(name1.length()))) {
				continue;
			}
			name1 = name1.substring(0, x) + name1.substring(x + 1);
		}
		int replaces = rand.nextInt(3);
		for (int l = 0; l < replaces; ++l) {
			char cNew;
			int x = rand.nextInt(name1.length());
			if (isFormattingCharacter(name1, x)) {
				continue;
			}
			char c = name1.charAt(x);
			String CONSONANTS = "bcdfghjklmnopqrstvwxyz";
			String VOWELS = "aeiou";
			if (VOWELS.indexOf(Character.toLowerCase(c)) >= 0) {
				cNew = VOWELS.charAt(rand.nextInt(VOWELS.length()));
				if (Character.isUpperCase(c)) {
					cNew = Character.toUpperCase(cNew);
				}
				c = cNew;
			} else if (CONSONANTS.indexOf(Character.toLowerCase(c)) >= 0) {
				cNew = CONSONANTS.charAt(rand.nextInt(CONSONANTS.length()));
				if (Character.isUpperCase(c)) {
					cNew = Character.toUpperCase(cNew);
				}
				c = cNew;
			}
			name1 = name1.substring(0, x) + c + name1.substring(x + 1);
		}
		int dupes = rand.nextInt(2);
		for (int l = 0; l < dupes; ++l) {
			char c;
			int x = rand.nextInt(name1.length());
			if (isFormattingCharacter(name1, x) || !Character.isAlphabetic(c = name1.charAt(x))) {
				continue;
			}
			name1 = name1.substring(0, x) + c + c + name1.substring(x + 1);
		}
		return name1;
	}

	private static boolean isFormattingCharacter(CharSequence s, int index) {
		char charAt = s.charAt(index);
		return charAt == '§' || index >= 1 && s.charAt(index - 1) == '§';
	}
}