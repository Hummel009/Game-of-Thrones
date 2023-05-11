package got.common.enchant;

import got.common.GOTDamage;
import got.common.item.GOTWeaponStats;
import got.common.item.weapon.GOTItemLegendaryWhip;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketWeaponFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentWeaponSpecial extends GOTEnchantment {
	public boolean compatibleOtherSpecial;

	public GOTEnchantmentWeaponSpecial(String s) {
		super(s, new GOTEnchantmentType[]{GOTEnchantmentType.MELEE, GOTEnchantmentType.THROWING_AXE, GOTEnchantmentType.RANGED_LAUNCHER});
		setValueModifier(3.0F);
		setBypassAnvilLimit();
	}

	public static void doChillAttack(EntityLivingBase entity) {
		if (entity instanceof EntityPlayerMP) {
			GOTDamage.doFrostDamage((EntityPlayerMP) entity);
		}

		int duration = 5;
		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 1));

		GOTPacketWeaponFX packet = new GOTPacketWeaponFX(GOTPacketWeaponFX.Type.CHILLING, entity);
		GOTPacketHandler.networkWrapper.sendToAllAround(packet, GOTPacketHandler.nearEntity(entity, 64.0D));
	}

	public static int getFireAmount() {
		return 2;
	}

	@Override
	public boolean canApply(ItemStack itemstack, boolean considering) {
		if (super.canApply(itemstack, considering)) {
			Item item = itemstack.getItem();
			return !(item instanceof GOTItemLegendaryWhip) || (this != GOTEnchantment.fire && this != GOTEnchantment.chill);
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
}
