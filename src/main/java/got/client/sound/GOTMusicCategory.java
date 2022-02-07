package got.client.sound;

import net.minecraft.world.World;

public enum GOTMusicCategory {
	DAY("day"), NIGHT("night"), CAVE("cave");

	private String categoryName;

	GOTMusicCategory(String s) {
		setCategoryName(s);
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public static GOTMusicCategory forName(String s) {
		for (GOTMusicCategory cat : GOTMusicCategory.values()) {
			if (!s.equalsIgnoreCase(cat.getCategoryName())) {
				continue;
			}
			return cat;
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
