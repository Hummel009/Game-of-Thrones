package got.common.enchant;

import got.common.item.GOTWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentKnockback extends GOTEnchantment {
	private int knockback;

	public GOTEnchantmentKnockback(String s, int i) {
		super(s, new GOTEnchantmentType[] { GOTEnchantmentType.MELEE, GOTEnchantmentType.THROWING_AXE });
		setKnockback(i);
		setValueModifier((getKnockback() + 2) / 2.0f);
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		return super.canApply(itemstack, considering) && GOTWeaponStats.getBaseExtraKnockback(itemstack) + getKnockback() <= GOTWeaponStats.MAX_MODIFIABLE_KNOCKBACK;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.knockback.desc", formatAdditiveInt(getKnockback()));
	}

	public int getKnockback() {
		return knockback;
	}

	@Override
	public boolean isBeneficial() {
		return getKnockback() >= 0;
	}

	public void setKnockback(int knockback) {
		this.knockback = knockback;
	}
}
