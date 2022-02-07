package got.common.util;

import net.minecraft.util.IIcon;

public class GOTCommonIcons {
	private static IIcon iconEmptyBlock;
	private static IIcon iconEmptyItem;
	private static IIcon iconMeleeWeapon;
	private static IIcon iconBomb;
	private static IIcon iconStoneSnow;

	public static IIcon getIconBomb() {
		return iconBomb;
	}

	public static IIcon getIconEmptyBlock() {
		return iconEmptyBlock;
	}

	public static IIcon getIconEmptyItem() {
		return iconEmptyItem;
	}

	public static IIcon getIconMeleeWeapon() {
		return iconMeleeWeapon;
	}

	public static IIcon getIconStoneSnow() {
		return iconStoneSnow;
	}

	public static void setIconBomb(IIcon iconBomb) {
		GOTCommonIcons.iconBomb = iconBomb;
	}

	public static void setIconEmptyBlock(IIcon iconEmptyBlock) {
		GOTCommonIcons.iconEmptyBlock = iconEmptyBlock;
	}

	public static void setIconEmptyItem(IIcon iconEmptyItem) {
		GOTCommonIcons.iconEmptyItem = iconEmptyItem;
	}

	public static void setIconMeleeWeapon(IIcon iconMeleeWeapon) {
		GOTCommonIcons.iconMeleeWeapon = iconMeleeWeapon;
	}

	public static void setIconStoneSnow(IIcon iconStoneSnow) {
		GOTCommonIcons.iconStoneSnow = iconStoneSnow;
	}
}
