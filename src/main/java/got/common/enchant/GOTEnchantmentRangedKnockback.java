package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentRangedKnockback extends GOTEnchantment {
	private int knockback;

	public GOTEnchantmentRangedKnockback(String s, int i) {
		super(s, GOTEnchantmentType.RANGED_LAUNCHER);
		setKnockback(i);
		setValueModifier((getKnockback() + 2) / 2.0f);
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.rangedKnockback.desc", formatAdditiveInt(getKnockback()));
	}

	public int getKnockback() {
		return knockback;
	}

	@Override
	public boolean isBeneficial() {
		return getKnockback() >= 0;
	}

	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}
}
