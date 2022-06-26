package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentDurability extends GOTEnchantment {
	public float durabilityFactor;

	public GOTEnchantmentDurability(String s, float f) {
		super(s, GOTEnchantmentType.BREAKABLE);
		durabilityFactor = f;
		setValueModifier(durabilityFactor);
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.durable.desc", formatMultiplicative(durabilityFactor));
	}

	@Override
	public boolean isBeneficial() {
		return durabilityFactor >= 1.0F;
	}
}
