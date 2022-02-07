package got.common.enchant;

import got.common.item.GOTWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentMeleeSpeed extends GOTEnchantment {
	private float speedFactor;

	public GOTEnchantmentMeleeSpeed(String s, float speed) {
		super(s, GOTEnchantmentType.MELEE);
		setSpeedFactor(speed);
		setValueModifier(getSpeedFactor());
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float speed = GOTWeaponStats.getMeleeSpeed(itemstack);
			return (speed *= getSpeedFactor()) <= GOTWeaponStats.MAX_MODIFIABLE_SPEED;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.meleeSpeed.desc", formatMultiplicative(getSpeedFactor()));
	}

	public float getSpeedFactor() {
		return speedFactor;
	}

	@Override
	public boolean isBeneficial() {
		return getSpeedFactor() >= 1.0f;
	}

	public void setSpeedFactor(float speedFactor) {
		this.speedFactor = speedFactor;
	}
}
