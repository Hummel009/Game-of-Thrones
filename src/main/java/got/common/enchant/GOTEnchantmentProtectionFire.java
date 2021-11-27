package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class GOTEnchantmentProtectionFire extends GOTEnchantmentProtectionSpecial {
	public GOTEnchantmentProtectionFire(String s, int level) {
		super(s, level);
	}

	@Override
	public int calcIntProtection() {
		return 1 + protectLevel;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protectFire.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	public boolean protectsAgainst(DamageSource source) {
		return source.isFireDamage();
	}
}
