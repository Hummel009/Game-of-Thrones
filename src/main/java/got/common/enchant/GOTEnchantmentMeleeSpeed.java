package got.common.enchant;

import got.common.item.GOTWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentMeleeSpeed extends GOTEnchantment {
	private final float speedFactor;

	@SuppressWarnings("unused")
	public GOTEnchantmentMeleeSpeed(String s, float speed) {
		super(s, GOTEnchantmentType.MELEE);
		speedFactor = speed;
		valueModifier = speedFactor;
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float speed = GOTWeaponStats.getMeleeSpeed(itemstack);
			speed *= speedFactor;
			return speed <= GOTWeaponStats.MAX_MODIFIABLE_SPEED;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.meleeSpeed.desc", formatMultiplicative(speedFactor));
	}

	@Override
	public boolean isBeneficial() {
		return speedFactor >= 1.0F;
	}

	public float getSpeedFactor() {
		return speedFactor;
	}
}