package got.common.enchant;

import got.common.item.GOTWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentMeleeReach extends GOTEnchantment {
	private final float reachFactor;

	public GOTEnchantmentMeleeReach(String s, float reach) {
		super(s, GOTEnchantmentType.MELEE);
		reachFactor = reach;
		setValueModifier(reachFactor);
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float reach = GOTWeaponStats.getMeleeReachFactor(itemstack);
			reach *= reachFactor;
			return reach <= GOTWeaponStats.MAX_MODIFIABLE_REACH;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.meleeReach.desc", formatMultiplicative(reachFactor));
	}

	@Override
	public boolean isBeneficial() {
		return reachFactor >= 1.0F;
	}

	public float getReachFactor() {
		return reachFactor;
	}
}