package got.common.enchant;

import java.text.DecimalFormat;
import java.util.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public abstract class GOTEnchantment {
	public static List<GOTEnchantment> allEnchantments = new ArrayList<>();
	public static Map<String, GOTEnchantment> enchantsByName = new HashMap<>();
	public static GOTEnchantment strong1 = new GOTEnchantmentDamage("strong1", 0.5f).setEnchantWeight(10);
	public static GOTEnchantment strong2 = new GOTEnchantmentDamage("strong2", 1.0f).setEnchantWeight(5);
	public static GOTEnchantment strong3 = new GOTEnchantmentDamage("strong3", 2.0f).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment strong4 = new GOTEnchantmentDamage("strong4", 3.0f).setEnchantWeight(1).setSkilful();
	public static GOTEnchantment weak1 = new GOTEnchantmentDamage("weak1", -0.5f).setEnchantWeight(6);
	public static GOTEnchantment weak2 = new GOTEnchantmentDamage("weak2", -1.0f).setEnchantWeight(4);
	public static GOTEnchantment weak3 = new GOTEnchantmentDamage("weak3", -2.0f).setEnchantWeight(2);
	public static GOTEnchantment durable1 = new GOTEnchantmentDurability("durable1", 1.25f).setEnchantWeight(15);
	public static GOTEnchantment durable2 = new GOTEnchantmentDurability("durable2", 1.5f).setEnchantWeight(8);
	public static GOTEnchantment durable3 = new GOTEnchantmentDurability("durable3", 2.0f).setEnchantWeight(4).setSkilful();
	public static GOTEnchantment meleeSpeed1 = new GOTEnchantmentMeleeSpeed("meleeSpeed1", 1.25f).setEnchantWeight(6);
	public static GOTEnchantment meleeSlow1 = new GOTEnchantmentMeleeSpeed("meleeSlow1", 0.75f).setEnchantWeight(4);
	public static GOTEnchantment meleeReach1 = new GOTEnchantmentMeleeReach("meleeReach1", 1.25f).setEnchantWeight(6);
	public static GOTEnchantment meleeUnreach1 = new GOTEnchantmentMeleeReach("meleeUnreach1", 0.75f).setEnchantWeight(4);
	public static GOTEnchantment knockback1 = new GOTEnchantmentKnockback("knockback1", 1).setEnchantWeight(6);
	public static GOTEnchantment knockback2 = new GOTEnchantmentKnockback("knockback2", 2).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment toolSpeed1 = new GOTEnchantmentToolSpeed("toolSpeed1", 1.5f).setEnchantWeight(20);
	public static GOTEnchantment toolSpeed2 = new GOTEnchantmentToolSpeed("toolSpeed2", 2.0f).setEnchantWeight(10);
	public static GOTEnchantment toolSpeed3 = new GOTEnchantmentToolSpeed("toolSpeed3", 3.0f).setEnchantWeight(5).setSkilful();
	public static GOTEnchantment toolSpeed4 = new GOTEnchantmentToolSpeed("toolSpeed4", 4.0f).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment toolSlow1 = new GOTEnchantmentToolSpeed("toolSlow1", 0.75f).setEnchantWeight(10);
	public static GOTEnchantment toolSilk = new GOTEnchantmentSilkTouch("toolSilk").setEnchantWeight(10).setSkilful();
	public static GOTEnchantment looting1 = new GOTEnchantmentLooting("looting1", 1).setEnchantWeight(6);
	public static GOTEnchantment looting2 = new GOTEnchantmentLooting("looting2", 2).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment looting3 = new GOTEnchantmentLooting("looting3", 3).setEnchantWeight(1).setSkilful();
	public static GOTEnchantment protect1 = new GOTEnchantmentProtection("protect1", 1).setEnchantWeight(10);
	public static GOTEnchantment protect2 = new GOTEnchantmentProtection("protect2", 2).setEnchantWeight(3).setSkilful();
	public static GOTEnchantment protectWeak1 = new GOTEnchantmentProtection("protectWeak1", -1).setEnchantWeight(5);
	public static GOTEnchantment protectWeak2 = new GOTEnchantmentProtection("protectWeak2", -2).setEnchantWeight(2);
	public static GOTEnchantment protectFire1 = new GOTEnchantmentProtectionFire("protectFire1", 1).setEnchantWeight(5);
	public static GOTEnchantment protectFire2 = new GOTEnchantmentProtectionFire("protectFire2", 2).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment protectFire3 = new GOTEnchantmentProtectionFire("protectFire3", 3).setEnchantWeight(1).setSkilful();
	public static GOTEnchantment protectFall1 = new GOTEnchantmentProtectionFall("protectFall1", 1).setEnchantWeight(5);
	public static GOTEnchantment protectFall2 = new GOTEnchantmentProtectionFall("protectFall2", 2).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment protectFall3 = new GOTEnchantmentProtectionFall("protectFall3", 3).setEnchantWeight(1).setSkilful();
	public static GOTEnchantment protectRanged1 = new GOTEnchantmentProtectionRanged("protectRanged1", 1).setEnchantWeight(5);
	public static GOTEnchantment protectRanged2 = new GOTEnchantmentProtectionRanged("protectRanged2", 2).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment protectRanged3 = new GOTEnchantmentProtectionRanged("protectRanged3", 3).setEnchantWeight(1).setSkilful();
	public static GOTEnchantment protectValyrian = new GOTEnchantmentProtectionValyrian("protectValyrian").setEnchantWeight(0);
	public static GOTEnchantment rangedStrong1 = new GOTEnchantmentRangedDamage("rangedStrong1", 1.1f).setEnchantWeight(10);
	public static GOTEnchantment rangedStrong2 = new GOTEnchantmentRangedDamage("rangedStrong2", 1.2f).setEnchantWeight(3);
	public static GOTEnchantment rangedStrong3 = new GOTEnchantmentRangedDamage("rangedStrong3", 1.3f).setEnchantWeight(1).setSkilful();
	public static GOTEnchantment rangedWeak1 = new GOTEnchantmentRangedDamage("rangedWeak1", 0.75f).setEnchantWeight(8);
	public static GOTEnchantment rangedWeak2 = new GOTEnchantmentRangedDamage("rangedWeak2", 0.5f).setEnchantWeight(3);
	public static GOTEnchantment rangedKnockback1 = new GOTEnchantmentRangedKnockback("rangedKnockback1", 1).setEnchantWeight(6);
	public static GOTEnchantment rangedKnockback2 = new GOTEnchantmentRangedKnockback("rangedKnockback2", 2).setEnchantWeight(2).setSkilful();
	public static GOTEnchantment fire = new GOTEnchantmentWeaponSpecial("fire").setEnchantWeight(0).setApplyToProjectile();
	public static GOTEnchantment chill = new GOTEnchantmentWeaponSpecial("chill").setEnchantWeight(0).setApplyToProjectile();
	public static GOTEnchantment headhunting = new GOTEnchantmentWeaponSpecial("headhunting").setCompatibleOtherSpecial().setIncompatibleBane().setEnchantWeight(0).setApplyToProjectile();
	public String enchantName;
	public List<GOTEnchantmentType> itemTypes;
	public int enchantWeight = 0;
	public float valueModifier = 1.0f;
	public boolean skilful = false;
	public boolean persistsReforge = false;
	public boolean bypassAnvilLimit = false;
	public boolean applyToProjectile = false;

	public GOTEnchantment(String s, GOTEnchantmentType type) {
		this(s, new GOTEnchantmentType[] { type });
	}

	public GOTEnchantment(String s, GOTEnchantmentType[] types) {
		enchantName = s;
		itemTypes = Arrays.asList(types);
		allEnchantments.add(this);
		enchantsByName.put(enchantName, this);
	}

	public boolean applyToProjectile() {
		return applyToProjectile;
	}

	public boolean bypassAnvilLimit() {
		return bypassAnvilLimit;
	}

	public boolean canApply(ItemStack itemstack, boolean considering) {
		for (GOTEnchantmentType type : itemTypes) {
			if (!type.canApply(itemstack, considering)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public String formatAdditive(float f) {
		String s = formatDecimalNumber(f);
		if (f >= 0.0f) {
			s = "+" + s;
		}
		return s;
	}

	public String formatAdditiveInt(int i) {
		String s = String.valueOf(i);
		if (i >= 0) {
			s = "+" + s;
		}
		return s;
	}

	public String formatDecimalNumber(float f) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(1);
		return df.format(f);
	}

	public String formatMultiplicative(float f) {
		String s = formatDecimalNumber(f);
		return "x" + s;
	}

	public abstract String getDescription(ItemStack var1);

	public String getDisplayName() {
		return StatCollector.translateToLocal("got.enchant." + enchantName);
	}

	public IChatComponent getEarnMessage(ItemStack itemstack) {
		ChatComponentTranslation msg = new ChatComponentTranslation("got.enchant." + enchantName + ".earn", itemstack.getDisplayName());
		msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		return msg;
	}

	public IChatComponent getEarnMessageWithName(EntityPlayer entityplayer, ItemStack itemstack) {
		ChatComponentTranslation msg = new ChatComponentTranslation("got.enchant." + enchantName + ".earnName", entityplayer.getCommandSenderName(), itemstack.getDisplayName());
		msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		return msg;
	}

	public int getEnchantWeight() {
		return enchantWeight;
	}

	public String getNamedFormattedDescription(ItemStack itemstack) {
		String s = StatCollector.translateToLocalFormatted("got.enchant.descFormat", getDisplayName(), getDescription(itemstack));
		return isBeneficial() ? (EnumChatFormatting.GRAY) + s : (EnumChatFormatting.DARK_GRAY) + s;
	}

	public float getValueModifier() {
		return valueModifier;
	}

	public boolean hasTemplateItem() {
		return getEnchantWeight() > 0 && isBeneficial();
	}

	public abstract boolean isBeneficial();

	public boolean isCompatibleWith(GOTEnchantment other) {
		return this.getClass() != other.getClass();
	}

	public boolean isSkilful() {
		return skilful;
	}

	public boolean persistsReforge() {
		return persistsReforge;
	}

	public GOTEnchantment setApplyToProjectile() {
		applyToProjectile = true;
		return this;
	}

	public GOTEnchantment setBypassAnvilLimit() {
		bypassAnvilLimit = true;
		return this;
	}

	public GOTEnchantment setEnchantWeight(int i) {
		enchantWeight = i;
		return this;
	}

	public GOTEnchantment setPersistsReforge() {
		persistsReforge = true;
		return this;
	}

	public GOTEnchantment setSkilful() {
		skilful = true;
		return this;
	}

	public GOTEnchantment setValueModifier(float f) {
		valueModifier = f;
		return this;
	}

	public static GOTEnchantment getEnchantmentByName(String s) {
		return enchantsByName.get(s);
	}
}
