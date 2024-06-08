package got.common.enchant;

import got.common.database.GOTMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentProtection extends GOTEnchantment {
	private final int protectLevel;

	@SuppressWarnings("unused")
	public GOTEnchantmentProtection(String s, int level) {
		this(s, GOTEnchantmentType.ARMOR, level);
	}

	private GOTEnchantmentProtection(String s, GOTEnchantmentType type, int level) {
		super(s, type);
		protectLevel = level;
		if (protectLevel >= 0) {
			valueModifier = (2.0F + protectLevel) / 2.0F;
		} else {
			valueModifier = (4.0F + protectLevel) / 4.0F;
		}
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			if (item instanceof ItemArmor) {
				ItemArmor armor = (ItemArmor) item;
				if (armor.getArmorMaterial() == GOTMaterial.ROYCE) {
					return false;
				}
				int prot = armor.damageReduceAmount;
				int total = prot + protectLevel;
				return total > 0 && (considering || total <= GOTMaterial.VALYRIAN.getDamageReductionAmount(armor.armorType));
			}
			return true;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protect.desc", formatAdditiveInt(protectLevel));
	}

	@Override
	public boolean isBeneficial() {
		return protectLevel >= 0;
	}

	public int getProtectLevel() {
		return protectLevel;
	}
}