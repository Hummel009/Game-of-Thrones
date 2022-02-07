package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentToolSpeed extends GOTEnchantment {
	private float speedFactor;

	public GOTEnchantmentToolSpeed(String s, float speed) {
		super(s, new GOTEnchantmentType[] { GOTEnchantmentType.TOOL, GOTEnchantmentType.SHEARS });
		setSpeedFactor(speed);
		setValueModifier(getSpeedFactor());
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.toolSpeed.desc", formatMultiplicative(getSpeedFactor()));
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
