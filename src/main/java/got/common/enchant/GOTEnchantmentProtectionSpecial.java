package got.common.enchant;

import net.minecraft.util.DamageSource;

public abstract class GOTEnchantmentProtectionSpecial extends GOTEnchantment {
	public int protectLevel;

	protected GOTEnchantmentProtectionSpecial(String s, GOTEnchantmentType type, int level) {
		super(s, type);
		protectLevel = level;
		setValueModifier((2.0F + protectLevel) / 2.0F);
	}

	protected GOTEnchantmentProtectionSpecial(String s, int level) {
		this(s, GOTEnchantmentType.ARMOR, level);
	}

	public abstract int calcIntProtection();

	public int calcSpecialProtection(DamageSource source) {
		if (source.canHarmInCreative()) {
			return 0;
		}
		if (protectsAgainst(source)) {
			return calcIntProtection();
		}
		return 0;
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(GOTEnchantment other) {
		if (super.isCompatibleWith(other)) {
			if (other instanceof GOTEnchantmentProtectionSpecial) {
				return isCompatibleWithOtherProtection() || ((GOTEnchantmentProtectionSpecial) other).isCompatibleWithOtherProtection();
			}
			return true;
		}
		return false;
	}

	public boolean isCompatibleWithOtherProtection() {
		return false;
	}

	public abstract boolean protectsAgainst(DamageSource paramDamageSource);
}
