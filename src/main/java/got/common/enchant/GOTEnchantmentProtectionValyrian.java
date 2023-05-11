package got.common.enchant;

import got.common.database.GOTMaterial;
import got.common.item.GOTWeaponStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentProtectionValyrian extends GOTEnchantmentProtectionSpecial {
	public GOTEnchantmentProtectionValyrian(String s) {
		super(s, 1);
		setValueModifier(2.0F);
	}

	@Override
	public int calcIntProtection() {
		return 4;
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			if (item instanceof ItemArmor && ((ItemArmor) item).getArmorMaterial() == GOTMaterial.VALYRIAN) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		return StatCollector.translateToLocalFormatted("got.enchant.protectValyrian.desc", formatAdditiveInt(calcIntProtection()));
	}

	@Override
	public boolean protectsAgainst(DamageSource source) {
		Entity attacker = source.getEntity();
		Entity entity = source.getSourceOfDamage();
		if (attacker instanceof EntityLivingBase && attacker == entity) {
			ItemStack weapon = ((EntityLivingBase) attacker).getHeldItem();
			if (weapon != null) {
				ItemStack weaponBase = weapon.copy();
				GOTEnchantmentHelper.clearEnchants(weaponBase);

				float range = GOTWeaponStats.getMeleeReachFactor(weaponBase);
				if (range >= 1.3F) {
					return true;
				}
			}
		}
		return false;
	}
}
