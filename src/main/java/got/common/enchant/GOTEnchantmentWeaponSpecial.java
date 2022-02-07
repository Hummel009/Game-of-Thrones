package got.common.enchant;

import got.common.GOTDamage;
import got.common.database.GOTRegistry;
import got.common.item.GOTWeaponStats;
import got.common.network.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentWeaponSpecial extends GOTEnchantment {
	public boolean compatibleBane = true;
	public boolean compatibleOtherSpecial = false;

	public GOTEnchantmentWeaponSpecial(String s) {
		super(s, new GOTEnchantmentType[] { GOTEnchantmentType.MELEE, GOTEnchantmentType.THROWING_AXE, GOTEnchantmentType.RANGED_LAUNCHER });
		setValueModifier(3.0f);
		setBypassAnvilLimit();
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return item != GOTRegistry.bericSword || this != GOTEnchantment.fire && this != GOTEnchantment.chill;
		}
		return false;
	}

	@Override
	public String getDescription(ItemStack itemstack) {
		if (GOTWeaponStats.isMeleeWeapon(itemstack)) {
			return StatCollector.translateToLocalFormatted("got.enchant." + enchantName + ".desc.melee");
		}
		return StatCollector.translateToLocalFormatted("got.enchant." + enchantName + ".desc.ranged");
	}

	@Override
	public boolean isBeneficial() {
		return true;
	}

	@Override
	public boolean isCompatibleWith(GOTEnchantment other) {
		return compatibleOtherSpecial || !(other instanceof GOTEnchantmentWeaponSpecial) || ((GOTEnchantmentWeaponSpecial) other).compatibleOtherSpecial;
	}

	public GOTEnchantmentWeaponSpecial setCompatibleOtherSpecial() {
		compatibleOtherSpecial = true;
		return this;
	}

	public GOTEnchantmentWeaponSpecial setIncompatibleBane() {
		compatibleBane = false;
		return this;
	}

	public static void doChillAttack(EntityLivingBase entity) {
		if (entity instanceof EntityPlayerMP) {
			GOTDamage.doFrostDamage((EntityPlayerMP) entity);
		}
		int duration = 5;
		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 1));
		GOTPacketWeaponFX packet = new GOTPacketWeaponFX(GOTPacketWeaponFX.Type.CHILLING, entity);
		GOTPacketHandler.networkWrapper.sendToAllAround(packet, GOTPacketHandler.nearEntity(entity, 64.0));
	}

	public static int getFireAmount() {
		return 2;
	}
}
