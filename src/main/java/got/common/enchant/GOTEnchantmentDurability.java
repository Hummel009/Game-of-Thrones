package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentDurability extends GOTEnchantment {
	private float durabilityFactor;

	public GOTEnchantmentDurability(String s, float f) {
		super(s, GOTEnchantmentType.BREAKABLE);
		setDurabilityFactor(f);
		setValueModifier(getDurabilityFactor());
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.durable.desc", formatMultiplicative(getDurabilityFactor()));
	}

	public float getDurabilityFactor() {
		return durabilityFactor;
	}

	@Override
	public boolean isBeneficial() {
		return getDurabilityFactor() >= 1.0f;
	}

	public void setDurabilityFactor(float durabilityFactor) {
		this.durabilityFactor = durabilityFactor;
	}
}
