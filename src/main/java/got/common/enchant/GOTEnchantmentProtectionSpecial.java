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

	protected abstract int calcIntProtection();

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
		return super.isCompatibleWith(other) && (!(other instanceof GOTEnchantmentProtectionSpecial) || isCompatibleWithOtherProtection() || ((GOTEnchantmentProtectionSpecial) other).isCompatibleWithOtherProtection());
	}

	protected boolean isCompatibleWithOtherProtection() {
		return false;
	}

	protected abstract boolean protectsAgainst(DamageSource paramDamageSource);
}
