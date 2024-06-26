package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentProtectionFall extends GOTEnchantmentProtectionSpecial {
	@SuppressWarnings("unused")
	public GOTEnchantmentProtectionFall(String s, int level) {
		super(s, GOTEnchantmentType.ARMOR_FEET, level);
	}

	@Override
	protected int calcIntProtection() {
		float f = protectLevel * (protectLevel + 1) / 2.0F;
		return 3 + MathHelper.floor_float(f);
	}

	@Override
	@SuppressWarnings("unused")
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protectFall.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	@SuppressWarnings("unused")
	public boolean isCompatibleWithOtherProtection() {
		return true;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean protectsAgainst(DamageSource source) {
		return source == DamageSource.fall;
	}
}