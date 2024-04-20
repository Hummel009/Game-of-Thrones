package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentSilkTouch extends GOTEnchantment {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEnchantmentSilkTouch(String s) {
		super(s, GOTEnchantmentType.TOOL);
		valueModifier = 3.0F;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant." + enchantName + ".desc");
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