package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentToolSpeed extends GOTEnchantment {
	private final float speedFactor;

	public GOTEnchantmentToolSpeed(String s, float speed) {
		super(s, new GOTEnchantmentType[]{GOTEnchantmentType.TOOL, GOTEnchantmentType.SHEARS});
		speedFactor = speed;
		valueModifier = speedFactor;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.toolSpeed.desc", formatMultiplicative(speedFactor));
	}

	@Override
	public boolean isBeneficial() {
		return speedFactor >= 1.0F;
	}

	public float getSpeedFactor() {
		return speedFactor;
	}
}