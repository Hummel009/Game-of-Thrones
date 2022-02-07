package got.common.enchant;

import got.common.database.GOTMaterial;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class GOTEnchantmentProtectionRanged extends GOTEnchantmentProtectionSpecial {
	public GOTEnchantmentProtectionRanged(String s, int level) {
		super(s, level);
	}

	@Override
	public int calcIntProtection() {
		return getProtectLevel();
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return !(item instanceof ItemArmor) || ((ItemArmor) item).getArmorMaterial() != GOTMaterial.ROYCE.toArmorMaterial();
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protectRanged.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	public boolean protectsAgainst(DamageSource source) {
		return source.isProjectile();
	}
}
