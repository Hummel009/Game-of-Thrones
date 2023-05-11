package got.client.sound;

import net.minecraft.world.World;

public enum GOTMusicCategory {
	DAY("day"), NIGHT("night"), CAVE("cave");

	public String categoryName;

	GOTMusicCategory(String s) {
		categoryName = s;
	}

	public static GOTMusicCategory forName(String s) {
		for (GOTMusicCategory cat : values()) {
			if (s.equalsIgnoreCase(cat.categoryName)) {
				return cat;
			}
		}
		return null;
	}

	public static boolean isCave(World world, int i, int j, int k) {
		return j < 50 && !world.canBlockSeeTheSky(i, j, k);
	}

	public static boolean isDay(World world) {
		return world.calculateSkylightSubtracted(1.0f) < 5;
	}
}
