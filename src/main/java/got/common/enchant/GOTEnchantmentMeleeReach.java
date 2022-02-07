package got.common.enchant;

import got.common.item.GOTWeaponStats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentMeleeReach extends GOTEnchantment {
	private float reachFactor;

	public GOTEnchantmentMeleeReach(String s, float reach) {
		super(s, GOTEnchantmentType.MELEE);
		setReachFactor(reach);
		setValueModifier(getReachFactor());
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			float reach = GOTWeaponStats.getMeleeReachFactor(itemstack);
			return (reach *= getReachFactor()) <= GOTWeaponStats.MAX_MODIFIABLE_REACH;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.meleeReach.desc", formatMultiplicative(getReachFactor()));
	}

	public float getReachFactor() {
		return reachFactor;
	}

	@Override
	public boolean isBeneficial() {
		return getReachFactor() >= 1.0f;
	}

	public void setReachFactor(float reachFactor) {
		this.reachFactor = reachFactor;
	}
}
