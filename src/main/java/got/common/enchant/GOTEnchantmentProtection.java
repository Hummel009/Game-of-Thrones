package got.common.enchant;

import got.common.database.GOTMaterial;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentProtection extends GOTEnchantment {
	private int protectLevel;

	public GOTEnchantmentProtection(String s, GOTEnchantmentType type, int level) {
		super(s, type);
		setProtectLevel(level);
		if (getProtectLevel() >= 0) {
			setValueModifier((2.0f + getProtectLevel()) / 2.0f);
		} else {
			setValueModifier((4.0f + getProtectLevel()) / 4.0f);
		}
	}

	public GOTEnchantmentProtection(String s, int level) {
		this(s, GOTEnchantmentType.ARMOR, level);
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			if (item instanceof ItemArmor) {
				ItemArmor armor = (ItemArmor) item;
				if (armor.getArmorMaterial() == GOTMaterial.ROYCE.toArmorMaterial()) {
					return false;
				}
				int prot = armor.damageReduceAmount;
				int total = prot + getProtectLevel();
				if (total > 0) {
					if (considering) {
						return true;
					}
					return total <= GOTMaterial.VALYRIAN.toArmorMaterial().getDamageReductionAmount(armor.armorType);
				}
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protect.desc", formatAdditiveInt(getProtectLevel()));
	}

	public int getProtectLevel() {
		return protectLevel;
	}

	@Override
	public boolean isBeneficial() {
		return getProtectLevel() >= 0;
	}

	public void setProtectLevel(int protectLevel) {
		this.protectLevel = protectLevel;
	}
}
