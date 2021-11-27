package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentLooting extends GOTEnchantment {
	public int lootLevel;

	public GOTEnchantmentLooting(String s, int level) {
		super(s, new GOTEnchantmentType[] { GOTEnchantmentType.TOOL, GOTEnchantmentType.MELEE });
		lootLevel = level;
		setValueModifier(1.0f + lootLevel);
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.looting.desc", formatAdditiveInt(lootLevel));
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(GOTEnchantment other) {
		return super.isCompatibleWith(other) && !(other instanceof GOTEnchantmentSilkTouch);
	}
}
