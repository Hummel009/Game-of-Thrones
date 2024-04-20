package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentRangedDamage extends GOTEnchantment {
	private final float damageFactor;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEnchantmentRangedDamage(String s, float damage) {
		super(s, GOTEnchantmentType.RANGED_LAUNCHER);
		damageFactor = damage;
		if (damageFactor > 1.0F) {
			valueModifier = damageFactor * 2.0F;
		} else {
			valueModifier = damageFactor;
		}
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.rangedDamage.desc", formatMultiplicative(damageFactor));
	}

	@Override
	public boolean isBeneficial() {
		return damageFactor >= 1.0F;
	}

	public float getDamageFactor() {
		return damageFactor;
	}
}