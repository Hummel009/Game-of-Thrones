package got.common.enchant;

import got.common.item.GOTWeaponStats;
import got.common.item.weapon.GOTItemThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentDamage extends GOTEnchantment {
	private float baseDamageBoost;

	public GOTEnchantmentDamage(String s, float boost) {
		super(s, new GOTEnchantmentType[] { GOTEnchantmentType.MELEE, GOTEnchantmentType.THROWING_AXE });
		baseDamageBoost = boost;
		if (baseDamageBoost >= 0.0f) {
			setValueModifier((7.0f + baseDamageBoost * 5.0f) / 7.0f);
		} else {
			setValueModifier((7.0f + baseDamageBoost) / 7.0f);
		}
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float dmg = GOTWeaponStats.getMeleeDamageBonus(itemstack);
			return (dmg += baseDamageBoost) > 0.0f;
		}
		return false;
	}

	public float getBaseDamageBoost() {
		return baseDamageBoost;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		if (itemstack != null && itemstack.getItem() instanceof GOTItemThrowingAxe) {
			return StatCollector.translateToLocalFormatted("got.enchant.damage.desc.throw", formatAdditive(baseDamageBoost));
		}
		return StatCollector.translateToLocalFormatted("got.enchant.damage.desc", formatAdditive(baseDamageBoost));
	}

	public float getEntitySpecificDamage(EntityLivingBase entity) {
		return 0.0f;
	}

	@Override
	public boolean isBeneficial() {
		return baseDamageBoost >= 0.0f;
	}
}
