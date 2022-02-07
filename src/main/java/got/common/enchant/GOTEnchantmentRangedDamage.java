package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentRangedDamage extends GOTEnchantment {
	private float damageFactor;

	public GOTEnchantmentRangedDamage(String s, float damage) {
		super(s, GOTEnchantmentType.RANGED_LAUNCHER);
		setDamageFactor(damage);
		if (getDamageFactor() > 1.0f) {
			setValueModifier(getDamageFactor() * 2.0f);
		} else {
			setValueModifier(getDamageFactor());
		}
	}

	public float getDamageFactor() {
		return damageFactor;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.rangedDamage.desc", formatMultiplicative(getDamageFactor()));
	}

	@Override
	public boolean isBeneficial() {
		return getDamageFactor() >= 1.0f;
	}

	public void setDamageFactor(float damageFactor) {
		this.damageFactor = damageFactor;
	}
}
