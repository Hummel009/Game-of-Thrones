package got.common.enchant;

import net.minecraft.util.DamageSource;

public abstract class GOTEnchantmentProtectionSpecial extends GOTEnchantment {
	private int protectLevel;

	public GOTEnchantmentProtectionSpecial(String s, GOTEnchantmentType type, int level) {
		super(s, type);
		setProtectLevel(level);
		setValueModifier((2.0f + getProtectLevel()) / 2.0f);
	}

	public GOTEnchantmentProtectionSpecial(String s, int level) {
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

	public int getProtectLevel() {
		return protectLevel;
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

	public abstract boolean protectsAgainst(DamageSource var1);

	public void setProtectLevel(int protectLevel) {
		this.protectLevel = protectLevel;
	}
}
