package got.common.enchant;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentProtectionRanged extends GOTEnchantmentProtectionSpecial {
	@SuppressWarnings("unused")
	public GOTEnchantmentProtectionRanged(String s, int level) {
		super(s, level);
	}

	@Override
	protected int calcIntProtection() {
		return protectLevel;
	}

	@Override
	@SuppressWarnings("unused")
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return !(item instanceof ItemArmor);
		}
		return false;
	}

	@Override
	@SuppressWarnings("unused")
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protectRanged.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	@SuppressWarnings("unused")
	public boolean protectsAgainst(DamageSource source) {
		return source.isProjectile();
	}
}