package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentLooting extends GOTEnchantment {
	private int lootLevel;

	public GOTEnchantmentLooting(String s, int level) {
		super(s, new GOTEnchantmentType[] { GOTEnchantmentType.TOOL, GOTEnchantmentType.MELEE });
		setLootLevel(level);
		setValueModifier(1.0f + getLootLevel());
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.looting.desc", formatAdditiveInt(getLootLevel()));
	}

	public int getLootLevel() {
		return lootLevel;
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(GOTEnchantment other) {
		return super.isCompatibleWith(other) && !(other instanceof GOTEnchantmentSilkTouch);
	}

	public void setLootLevel(int lootLevel) {
		this.lootLevel = lootLevel;
	}
}
