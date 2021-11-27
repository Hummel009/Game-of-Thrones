package got.common.enchant;

import got.common.database.GOTMaterial;
import got.common.item.GOTWeaponStats;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class GOTEnchantmentProtectionValyrian extends GOTEnchantmentProtectionSpecial {
	public GOTEnchantmentProtectionValyrian(String s) {
		super(s, 1);
		setValueModifier(2.0f);
	}

	@Override
	public int calcIntProtection() {
		return 4;
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return item instanceof ItemArmor && ((ItemArmor) item).getArmorMaterial() == GOTMaterial.VALYRIAN.toArmorMaterial();
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protectValyrian.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	public boolean protectsAgainst(DamageSource source) {
		ItemStack weapon;
		Entity attacker = source.getEntity();
		Entity entity = source.getSourceOfDamage();
		if (attacker instanceof EntityLivingBase && attacker == entity && (weapon = ((EntityLivingBase) attacker).getHeldItem()) != null) {
			ItemStack weaponBase = weapon.copy();
			GOTEnchantmentHelper.clearEnchants(weaponBase);
			float range = GOTWeaponStats.getMeleeReachFactor(weaponBase);
			if (range >= 1.3f) {
				return true;
			}
		}
		return false;
	}
}
