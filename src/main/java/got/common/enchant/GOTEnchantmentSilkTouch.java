package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentSilkTouch extends GOTEnchantment {
	public GOTEnchantmentSilkTouch(String s) {
		super(s, GOTEnchantmentType.TOOL);
		setValueModifier(3.0F);
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant." + getEnchantName() + ".desc");
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(GOTEnchantment other) {
		return super.isCompatibleWith(other) && !(other instanceof GOTEnchantmentLooting);
	}
}
