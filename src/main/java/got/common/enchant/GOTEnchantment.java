package got.common.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.text.DecimalFormat;
import java.util.*;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class GOTEnchantment {
	public static final Collection<GOTEnchantment> CONTENT = new ArrayList<>();
	public static final Map<String, GOTEnchantment> ENCHANTS_BY_NAME = new HashMap<>();

	public static final GOTEnchantment STRONG_1 = new GOTEnchantmentDamage("strong1", 0.5F).setEnchantWeight(10);
	public static final GOTEnchantment STRONG_2 = new GOTEnchantmentDamage("strong2", 1.0F).setEnchantWeight(5);
	public static final GOTEnchantment STRONG_3 = new GOTEnchantmentDamage("strong3", 2.0F).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment STRONG_4 = new GOTEnchantmentDamage("strong4", 3.0F).setEnchantWeight(1).setSkilful();
	public static final GOTEnchantment WEAK_1 = new GOTEnchantmentDamage("weak1", -0.5F).setEnchantWeight(6);
	public static final GOTEnchantment WEAK_2 = new GOTEnchantmentDamage("weak2", -1.0F).setEnchantWeight(4);
	public static final GOTEnchantment WEAK_3 = new GOTEnchantmentDamage("weak3", -2.0F).setEnchantWeight(2);
	public static final GOTEnchantment DURABLE_1 = new GOTEnchantmentDurability("durable1", 1.25F).setEnchantWeight(15);
	public static final GOTEnchantment DURABLE_2 = new GOTEnchantmentDurability("durable2", 1.5F).setEnchantWeight(8);
	public static final GOTEnchantment DURABLE_3 = new GOTEnchantmentDurability("durable3", 2.0F).setEnchantWeight(4).setSkilful();
	public static final GOTEnchantment MELEE_SPEED_1 = new GOTEnchantmentMeleeSpeed("meleeSpeed1", 1.25F).setEnchantWeight(6);
	public static final GOTEnchantment MELEE_SLOW_1 = new GOTEnchantmentMeleeSpeed("meleeSlow1", 0.75F).setEnchantWeight(4);
	public static final GOTEnchantment MELEE_REACH_1 = new GOTEnchantmentMeleeReach("meleeReach1", 1.25F).setEnchantWeight(6);
	public static final GOTEnchantment MELEE_UNREACH_1 = new GOTEnchantmentMeleeReach("meleeUnreach1", 0.75F).setEnchantWeight(4);
	public static final GOTEnchantment KNOCKBACK_1 = new GOTEnchantmentKnockback("knockback1", 1).setEnchantWeight(6);
	public static final GOTEnchantment KNOCKBACK_2 = new GOTEnchantmentKnockback("knockback2", 2).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment TOOL_SPEED_1 = new GOTEnchantmentToolSpeed("toolSpeed1", 1.5F).setEnchantWeight(20);
	public static final GOTEnchantment TOOL_SPEED_2 = new GOTEnchantmentToolSpeed("toolSpeed2", 2.0F).setEnchantWeight(10);
	public static final GOTEnchantment TOOL_SPEED_3 = new GOTEnchantmentToolSpeed("toolSpeed3", 3.0F).setEnchantWeight(5).setSkilful();
	public static final GOTEnchantment TOOL_SPEED_4 = new GOTEnchantmentToolSpeed("toolSpeed4", 4.0F).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment TOOL_SLOW_1 = new GOTEnchantmentToolSpeed("toolSlow1", 0.75F).setEnchantWeight(10);
	public static final GOTEnchantment TOOL_SILK = new GOTEnchantmentSilkTouch("toolSilk").setEnchantWeight(10).setSkilful();
	public static final GOTEnchantment LOOTING_1 = new GOTEnchantmentLooting("looting1", 1).setEnchantWeight(6);
	public static final GOTEnchantment LOOTING_2 = new GOTEnchantmentLooting("looting2", 2).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment LOOTING_3 = new GOTEnchantmentLooting("looting3", 3).setEnchantWeight(1).setSkilful();
	public static final GOTEnchantment PROTECT_1 = new GOTEnchantmentProtection("protect1", 1).setEnchantWeight(10);
	public static final GOTEnchantment PROTECT_2 = new GOTEnchantmentProtection("protect2", 2).setEnchantWeight(3).setSkilful();
	public static final GOTEnchantment PROTECT_WEAK_1 = new GOTEnchantmentProtection("protectWeak1", -1).setEnchantWeight(5);
	public static final GOTEnchantment PROTECT_WEAK_2 = new GOTEnchantmentProtection("protectWeak2", -2).setEnchantWeight(2);
	public static final GOTEnchantment PROTECT_FIRE_1 = new GOTEnchantmentProtectionFire("protectFire1", 1).setEnchantWeight(5);
	public static final GOTEnchantment PROTECT_FIRE_2 = new GOTEnchantmentProtectionFire("protectFire2", 2).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment PROTECT_FIRE_3 = new GOTEnchantmentProtectionFire("protectFire3", 3).setEnchantWeight(1).setSkilful();
	public static final GOTEnchantment PROTECT_FALL_1 = new GOTEnchantmentProtectionFall("protectFall1", 1).setEnchantWeight(5);
	public static final GOTEnchantment PROTECT_FALL_2 = new GOTEnchantmentProtectionFall("protectFall2", 2).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment PROTECT_FALL_3 = new GOTEnchantmentProtectionFall("protectFall3", 3).setEnchantWeight(1).setSkilful();
	public static final GOTEnchantment PROTECT_RANGED_1 = new GOTEnchantmentProtectionRanged("protectRanged1", 1).setEnchantWeight(5);
	public static final GOTEnchantment PROTECT_RANGED_2 = new GOTEnchantmentProtectionRanged("protectRanged2", 2).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment PROTECT_RANGED_3 = new GOTEnchantmentProtectionRanged("protectRanged3", 3).setEnchantWeight(1).setSkilful();
	public static final GOTEnchantment PROTECT_VALYRIAN = new GOTEnchantmentProtectionValyrian("protectValyrian").setEnchantWeight(0);
	public static final GOTEnchantment RANGED_STRONG_1 = new GOTEnchantmentRangedDamage("rangedStrong1", 1.1F).setEnchantWeight(10);
	public static final GOTEnchantment RANGED_STRONG_2 = new GOTEnchantmentRangedDamage("rangedStrong2", 1.2F).setEnchantWeight(3);
	public static final GOTEnchantment RANGED_STRONG_3 = new GOTEnchantmentRangedDamage("rangedStrong3", 1.3F).setEnchantWeight(1).setSkilful();
	public static final GOTEnchantment RANGED_WEAK_1 = new GOTEnchantmentRangedDamage("rangedWeak1", 0.75F).setEnchantWeight(8);
	public static final GOTEnchantment RANGED_WEAK_2 = new GOTEnchantmentRangedDamage("rangedWeak2", 0.5F).setEnchantWeight(3);
	public static final GOTEnchantment RANGED_KNOCKBACK_1 = new GOTEnchantmentRangedKnockback("rangedKnockback1", 1).setEnchantWeight(6);
	public static final GOTEnchantment RANGED_KNOCKBACK_2 = new GOTEnchantmentRangedKnockback("rangedKnockback2", 2).setEnchantWeight(2).setSkilful();
	public static final GOTEnchantment FIRE = new GOTEnchantmentWeaponSpecial("fire").setEnchantWeight(0).setApplyToProjectile();
	public static final GOTEnchantment CHILL = new GOTEnchantmentWeaponSpecial("chill").setEnchantWeight(0).setApplyToProjectile();
	public static final GOTEnchantment HEADHUNTING = new GOTEnchantmentWeaponSpecial("headhunting").setCompatibleOtherSpecial().setEnchantWeight(0).setApplyToProjectile();

	private String enchantName;
	private List<GOTEnchantmentType> itemTypes;

	private int enchantWeight;
	private float valueModifier = 1.0F;
	private boolean skilful;
	private boolean bypassAnvilLimit;
	private boolean applyToProjectile;

	protected GOTEnchantment(String s, GOTEnchantmentType type) {
		this(s, new GOTEnchantmentType[]{type});
	}

	protected GOTEnchantment(String s, GOTEnchantmentType[] types) {
		enchantName = s;
		itemTypes = Arrays.asList(types);
		CONTENT.add(this);
		ENCHANTS_BY_NAME.put(enchantName, this);
	}

	public static GOTEnchantment getEnchantmentByName(String s) {
		return ENCHANTS_BY_NAME.get(s);
	}

	public boolean applyToProjectile() {
		return applyToProjectile;
	}

	public boolean bypassAnvilLimit() {
		return bypassAnvilLimit;
	}

	public boolean canApply(ItemStack itemstack, boolean considering) {
		for (GOTEnchantmentType type : itemTypes) {
			if (type.canApply(itemstack)) {
				return true;
			}
		}
		return false;
	}

	protected String formatAdditive(float f) {
		String s = formatDecimalNumber(f);
		if (f >= 0.0F) {
			return '+' + s;
		}
		return s;
	}

	protected String formatAdditiveInt(int i) {
		String s = String.valueOf(i);
		if (i >= 0) {
			return '+' + s;
		}
		return s;
	}

	private String formatDecimalNumber(float f) {
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(1);
		return df.format(f);
	}

	protected String formatMultiplicative(float f) {
		String s = formatDecimalNumber(f);
		return 'x' + s;
	}

	protected abstract String getDescription(ItemStack paramItemStack);

	public String getDisplayName() {
		return StatCollector.translateToLocal("got.enchant." + enchantName);
	}

	public int getEnchantWeight() {
		return enchantWeight;
	}

	protected GOTEnchantment setEnchantWeight(int i) {
		enchantWeight = i;
		return this;
	}

	public String getNamedFormattedDescription(ItemStack itemstack) {
		String s = StatCollector.translateToLocalFormatted("got.enchant.descFormat", getDisplayName(), getDescription(itemstack));
		if (isBeneficial()) {
			return EnumChatFormatting.GRAY + s;
		}
		return EnumChatFormatting.DARK_GRAY + s;
	}

	public float getValueModifier() {
		return valueModifier;
	}

	protected void setValueModifier(float f) {
		valueModifier = f;
	}

	public boolean hasTemplateItem() {
		return enchantWeight > 0 && isBeneficial();
	}

	public abstract boolean isBeneficial();

	public boolean isCompatibleWith(GOTEnchantment other) {
		return getClass() != other.getClass();
	}

	public boolean isSkilful() {
		return skilful;
	}

	public boolean persistsReforge() {
		return false;
	}

	private GOTEnchantment setApplyToProjectile() {
		applyToProjectile = true;
		return this;
	}

	protected void setBypassAnvilLimit() {
		bypassAnvilLimit = true;
	}

	private GOTEnchantment setSkilful() {
		skilful = true;
		return this;
	}

	public String getEnchantName() {
		return enchantName;
	}

	public void setEnchantName(String enchantName) {
		this.enchantName = enchantName;
	}

	public List<GOTEnchantmentType> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<GOTEnchantmentType> itemTypes) {
		this.itemTypes = itemTypes;
	}
}