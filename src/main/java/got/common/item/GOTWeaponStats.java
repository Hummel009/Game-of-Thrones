package got.common.item;

import got.common.enchant.GOTEnchantmentHelper;
import got.common.item.weapon.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ISpecialArmor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GOTWeaponStats {
	public static int basePlayerMeleeTime = 15;
	public static int baseMobMeleeTime = 20;
	public static Map<Object, Float> meleeSpeed = new HashMap<>();
	public static Map<Object, Float> meleeReach = new HashMap<>();
	public static Map<Object, Integer> meleeExtraKnockback = new HashMap<>();
	public static float MAX_MODIFIABLE_REACH = 2.0f;
	public static float MAX_MODIFIABLE_SPEED = 1.6f;
	public static int MAX_MODIFIABLE_KNOCKBACK = 2;

	static {
		registerMeleeSpeed(GOTItemGreatsword.class, 0.667f);
		registerMeleeSpeed(GOTItemLongsword.class, 0.8f);
		registerMeleeReach(GOTItemGreatsword.class, 1.5f);
		registerMeleeReach(GOTItemLongsword.class, 1.25f);
		registerMeleeSpeed(GOTItemDagger.class, 1.5f);
		registerMeleeSpeed(GOTItemSpear.class, 0.833f);
		registerMeleeSpeed(GOTItemPolearm.class, 0.667f);
		registerMeleeSpeed(GOTItemPolearmLong.class, 0.5f);
		registerMeleeSpeed(GOTItemLance.class, 0.5f);
		registerMeleeSpeed(GOTItemBattleaxe.class, 0.75f);
		registerMeleeSpeed(GOTItemHammer.class, 0.667f);
		registerMeleeReach(GOTItemDagger.class, 0.75f);
		registerMeleeReach(GOTItemSpear.class, 1.5f);
		registerMeleeReach(GOTItemPolearm.class, 1.5f);
		registerMeleeReach(GOTItemPolearmLong.class, 2.0f);
		registerMeleeReach(GOTItemLance.class, 2.0f);
		registerMeleeExtraKnockback(GOTItemHammer.class, 1);
		registerMeleeExtraKnockback(GOTItemLance.class, 1);
	}

	public static int getArmorProtection(ItemStack itemstack) {
		Item item;
		if (itemstack != null && (item = itemstack.getItem()) instanceof ItemArmor) {
			ItemArmor armor = (ItemArmor) item;
			int i = armor.damageReduceAmount;
			return i + GOTEnchantmentHelper.calcCommonArmorProtection(itemstack);
		}
		return 0;
	}

	public static int getAttackTimeMob(ItemStack itemstack) {
		return getAttackTimeWithBase(itemstack, baseMobMeleeTime);
	}

	public static int getAttackTimePlayer(ItemStack itemstack) {
		return getAttackTimeWithBase(itemstack, basePlayerMeleeTime);
	}

	public static int getAttackTimeWithBase(ItemStack itemstack, int baseTime) {
		float time = baseTime;
		Float factor = (Float) getClassOrItemProperty(itemstack, meleeSpeed);
		if (factor != null) {
			time /= factor;
		}
		time /= GOTEnchantmentHelper.calcMeleeSpeedFactor(itemstack);
		time = Math.max(time, 1.0f);
		return Math.round(time);
	}

	public static int getBaseExtraKnockback(ItemStack itemstack) {
		Integer extra = (Integer) getClassOrItemProperty(itemstack, meleeExtraKnockback);
		if (extra != null) {
			return extra;
		}
		return 0;
	}

	public static Object getClassOrItemProperty(ItemStack itemstack, Map<?, ?> propertyMap) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (propertyMap.containsKey(item)) {
				return propertyMap.get(item);
			}
			Class<?> itemClass = item.getClass();
			do {
				if (propertyMap.containsKey(itemClass)) {
					return propertyMap.get(itemClass);
				}
				if (itemClass == Item.class) {
					break;
				}
				itemClass = itemClass.getSuperclass();
			} while (true);
		}
		return null;
	}

	public static float getMeleeDamageBonus(ItemStack itemstack) {
		Collection<AttributeModifier> weaponAttributes;
		float damage = 0.0f;
		if (itemstack != null && (weaponAttributes = itemstack.getAttributeModifiers().values()) != null) {
			for (AttributeModifier mod : weaponAttributes) {
				if (mod.getID() == GOTItemSword.accessWeaponDamageModifier()) {
					damage = (float) (damage + mod.getAmount());
					damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
				}
			}
		}
		return damage;
	}

	public static float getMeleeExtraLookWidth() {
		return 1.0f;
	}

	public static float getMeleeReachDistance(EntityPlayer entityplayer) {
		float reach = 3.0f;
		reach *= getMeleeReachFactor(entityplayer.getHeldItem());
		if (entityplayer.capabilities.isCreativeMode) {
			return (float) (reach + 3.0);
		}
		return reach;
	}

	public static float getMeleeReachFactor(ItemStack itemstack) {
		float reach = 1.0f;
		Float factor = (Float) getClassOrItemProperty(itemstack, meleeReach);
		if (factor != null) {
			reach *= factor;
		}
		return reach * GOTEnchantmentHelper.calcMeleeReachFactor(itemstack);
	}

	public static float getMeleeSpeed(ItemStack itemstack) {
		int base = basePlayerMeleeTime;
		return 1.0f / ((float) getAttackTimeWithBase(itemstack, base) / base);
	}

	public static float getRangedDamageFactor(ItemStack itemstack, boolean launchSpeedOnly) {
		float baseArrowFactor = 2.0f;
		float weaponFactor = 0.0f;
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemCrossbow) {
				weaponFactor = baseArrowFactor * (float) ((GOTItemCrossbow) item).boltDamageFactor;
				weaponFactor *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
				if (!launchSpeedOnly) {
					int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
					if (power > 0) {
						weaponFactor = (float) (weaponFactor + (power * 0.5 + 0.5));
					}
					weaponFactor *= 2.0f;
				}
			} else if (item instanceof ItemBow) {
				int power;
				weaponFactor = baseArrowFactor;
				if (item instanceof GOTItemBow) {
					weaponFactor *= (float) ((GOTItemBow) item).arrowDamageFactor;
				}
				weaponFactor *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
				if (!launchSpeedOnly && (power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack)) > 0) {
					weaponFactor = (float) (weaponFactor + (power * 0.5 + 0.5));
				}
			} else if (item instanceof GOTItemSarbacane) {
				weaponFactor = baseArrowFactor;
				if (!launchSpeedOnly) {
					weaponFactor *= 1.0f / baseArrowFactor;
				}
				weaponFactor *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
			} else if (item instanceof GOTItemSpear) {
				weaponFactor = ((GOTItemSpear) item).getRangedDamageMultiplier(itemstack, null, null);
			} else if (item instanceof GOTItemThrowingAxe) {
				weaponFactor = ((GOTItemThrowingAxe) item).getRangedDamageMultiplier(itemstack, null, null);
			}
		}
		if (weaponFactor > 0.0f) {
			return weaponFactor / baseArrowFactor;
		}
		return 0.0f;
	}

	public static int getRangedKnockback(ItemStack itemstack) {
		if (isMeleeWeapon(itemstack) || itemstack != null && itemstack.getItem() instanceof GOTItemThrowingAxe) {
			return getTotalKnockback(itemstack);
		}
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack) + GOTEnchantmentHelper.calcRangedKnockback(itemstack);
	}

	public static float getRangedSpeed(ItemStack itemstack) {
		int base = 20;
		int time = 0;
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemCrossbow) {
				time = ((GOTItemCrossbow) item).getMaxDrawTime();
			} else if (item instanceof GOTItemBow) {
				time = ((GOTItemBow) item).getMaxDrawTime();
			} else if (item == Items.bow) {
				time = 20;
			}
			if (item instanceof GOTItemSpear) {
				time = ((GOTItemSpear) item).getMaxDrawTime();
			}
			if (item instanceof GOTItemSarbacane) {
				time = ((GOTItemSarbacane) item).getMaxDrawTime();
			}
		}
		if (time > 0) {
			return 1.0f / ((float) time / base);
		}
		return 0.0f;
	}

	public static int getTotalArmorValue(EntityPlayer entityplayer) {
		int protection = 0;
		for (int i = 0; i < entityplayer.inventory.armorInventory.length; ++i) {
			ItemStack stack = entityplayer.inventory.armorInventory[i];
			if (stack != null && stack.getItem() instanceof ISpecialArmor) {
				protection += ((ISpecialArmor) stack.getItem()).getArmorDisplay(entityplayer, stack, i);
				continue;
			}
			if (stack == null || !(stack.getItem() instanceof ItemArmor)) {
				continue;
			}
			protection += getArmorProtection(stack);
		}
		return protection;
	}

	public static int getTotalKnockback(ItemStack itemstack) {
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemstack) + getBaseExtraKnockback(itemstack) + GOTEnchantmentHelper.calcExtraKnockback(itemstack);
	}

	public static boolean isMeleeWeapon(ItemStack itemstack) {
		Collection<AttributeModifier> weaponAttributes;
		if (itemstack != null && (weaponAttributes = itemstack.getAttributeModifiers().values()) != null) {
			for (AttributeModifier mod : weaponAttributes) {
				if (mod.getID() == GOTItemSword.accessWeaponDamageModifier()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isPoisoned(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			return item instanceof GOTItemDagger && ((GOTItemSword) item).getHitEffect() == GOTItemSword.HitEffect.POISON;
		}
		return false;
	}

	public static boolean isRangedWeapon(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			return item instanceof ItemBow || item instanceof GOTItemSpear || item instanceof GOTItemSarbacane || item instanceof GOTItemThrowingAxe;
		}
		return false;
	}

	public static void registerMeleeExtraKnockback(Object obj, int i) {
		meleeExtraKnockback.put(obj, i);
	}

	public static void registerMeleeReach(Object obj, float f) {
		meleeReach.put(obj, f);
	}

	public static void registerMeleeSpeed(Object obj, float f) {
		meleeSpeed.put(obj, f);
	}
}
