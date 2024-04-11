package got.common.util;

public enum GOTEnumDyeColor {
	WHITE(0, 15, "white", "white"), ORANGE(1, 14, "orange", "orange"), MAGENTA(2, 13, "magenta", "magenta"), LIGHT_BLUE(3, 12, "light_blue", "lightBlue"), YELLOW(4, 11, "yellow", "yellow"), LIME(5, 10, "lime", "lime"), PINK(6, 9, "pink", "pink"), GRAY(7, 8, "gray", "gray"), SILVER(8, 7, "silver", "silver"), CYAN(9, 6, "cyan", "cyan"), PURPLE(10, 5, "purple", "purple"), BLUE(11, 4, "blue", "blue"), BROWN(12, 3, "brown", "brown"), GREEN(13, 2, "green", "green"), RED(14, 1, "red", "red"), BLACK(15, 0, "black", "black");

	private final int dyeDamage;
	private final String name;
	private final String unlocalizedName;

	GOTEnumDyeColor(int meta, int dyeDamage, String name, String unlocalizedName) {
		this.dyeDamage = dyeDamage;
		this.name = name;
		this.unlocalizedName = unlocalizedName;
	}

	public int getDyeDamage() {
		return dyeDamage;
	}

	public String getName() {
		return name;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	@Override
	public String toString() {
		return unlocalizedName;
	}
}