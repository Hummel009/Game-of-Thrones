package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentRangedKnockback extends GOTEnchantment {
	public int knockback;

	public GOTEnchantmentRangedKnockback(String s, int i) {
		super(s, GOTEnchantmentType.RANGED_LAUNCHER);
		knockback = i;
		setValueModifier((knockback + 2) / 2.0F);
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.rangedKnockback.desc", formatAdditiveInt(knockback));
	}

	@Override
	public boolean isBeneficial() {
		return knockback >= 0;
	}
}
