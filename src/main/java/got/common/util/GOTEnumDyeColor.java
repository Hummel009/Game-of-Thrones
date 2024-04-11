package got.common.util;

@SuppressWarnings("unused")
public enum GOTEnumDyeColor {
	WHITE(15, "white", "white"), ORANGE(14, "orange", "orange"), MAGENTA(13, "magenta", "magenta"), LIGHT_BLUE(12, "light_blue", "lightBlue"), YELLOW(11, "yellow", "yellow"), LIME(10, "lime", "lime"), PINK(9, "pink", "pink"), GRAY(8, "gray", "gray"), SILVER(7, "silver", "silver"), CYAN(6, "cyan", "cyan"), PURPLE(5, "purple", "purple"), BLUE(4, "blue", "blue"), BROWN(3, "brown", "brown"), GREEN(2, "green", "green"), RED(1, "red", "red"), BLACK(0, "black", "black");

	private final int dyeDamage;
	private final String name;
	private final String unlocalizedName;

	GOTEnumDyeColor(int dyeDamage, String name, String unlocalizedName) {
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