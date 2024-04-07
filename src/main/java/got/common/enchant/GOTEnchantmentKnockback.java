package got.common.enchant;

import got.common.item.GOTWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentKnockback extends GOTEnchantment {
	private final int knockback;

	public GOTEnchantmentKnockback(String s, int i) {
		super(s, new GOTEnchantmentType[]{GOTEnchantmentType.MELEE, GOTEnchantmentType.THROWING_AXE});
		knockback = i;
		setValueModifier((knockback + 2) / 2.0F);
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		return super.canApply(itemstack, considering) && GOTWeaponStats.getBaseExtraKnockback(itemstack) + knockback <= GOTWeaponStats.MAX_MODIFIABLE_KNOCKBACK;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.knockback.desc", formatAdditiveInt(knockback));
	}

	@Override
	public boolean isBeneficial() {
		return knockback >= 0;
	}

	public int getKnockback() {
		return knockback;
	}
}