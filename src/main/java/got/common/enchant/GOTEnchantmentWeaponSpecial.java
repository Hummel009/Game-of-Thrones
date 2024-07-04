package got.common.enchant;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTDamage;
import got.common.item.GOTWeaponStats;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketWeaponFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

public class GOTEnchantmentWeaponSpecial extends GOTEnchantment {
	private boolean compatibleOtherSpecial;

	@SuppressWarnings("unused")
	public GOTEnchantmentWeaponSpecial(String s) {
		super(s, new GOTEnchantmentType[]{GOTEnchantmentType.MELEE, GOTEnchantmentType.THROWING_AXE, GOTEnchantmentType.RANGED_LAUNCHER});
		valueModifier = 3.0F;
		bypassAnvilLimit = true;
	}

	public static void doChillAttack(EntityLivingBase entity) {
		if (entity instanceof EntityPlayerMP) {
			GOTDamage.doFrostDamage((EntityPlayerMP) entity);
		}
		int duration = 5;
		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 1));

		IMessage packet = new GOTPacketWeaponFX(GOTPacketWeaponFX.Type.CHILLING, entity);
		GOTPacketHandler.NETWORK_WRAPPER.sendToAllAround(packet, GOTPacketHandler.nearEntity(entity, 64.0D));
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

	@SuppressWarnings("unused")
	public GOTEnchantmentWeaponSpecial setCompatibleOtherSpecial(boolean compatibleOtherSpecial) {
		this.compatibleOtherSpecial = compatibleOtherSpecial;
		return this;
	}
}