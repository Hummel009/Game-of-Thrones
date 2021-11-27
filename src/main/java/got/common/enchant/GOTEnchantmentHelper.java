package got.common.enchant;

import java.util.*;

import com.google.common.collect.Lists;

import got.common.GOTConfig;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class GOTEnchantmentHelper {
	public static Map<UUID, ItemStack[]> lastKnownPlayerInventories = new HashMap<>();
	public static Random backupRand;

	public static void applyRandomEnchantments(ItemStack itemstack, Random random, boolean skilful, boolean keepBanes) {
		if (!keepBanes) {
			GOTEnchantmentHelper.clearEnchantsAndProgress(itemstack);
		} else {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench2 : enchants) {
				if (ench2.persistsReforge()) {
					continue;
				}
				GOTEnchantmentHelper.removeEnchant(itemstack, ench2);
			}
		}
		int enchants = 0;
		float chance = random.nextFloat();
		if (skilful) {
			if (chance < 0.15f) {
				enchants = 2;
			} else if (chance < 0.8f) {
				enchants = 1;
			}
		} else if (chance < 0.1f) {
			enchants = 2;
		} else if (chance < 0.65f) {
			enchants = 1;
		}
		ArrayList<WeightedRandomEnchant> applicable = new ArrayList<>();
		for (GOTEnchantment ench3 : GOTEnchantment.allEnchantments) {
			int weight;
			if (!ench3.canApply(itemstack, true) || ench3.isSkilful() && !skilful || (weight = ench3.getEnchantWeight()) <= 0) {
				continue;
			}
			weight = skilful ? GOTEnchantmentHelper.getSkilfulWeight(ench3) : (weight *= 100);
			if (weight > 0 && itemstack.getItem() instanceof ItemTool && !ench3.itemTypes.contains(GOTEnchantmentType.TOOL) && !ench3.itemTypes.contains(GOTEnchantmentType.BREAKABLE)) {
				weight /= 3;
				weight = Math.max(weight, 1);
			}
			WeightedRandomEnchant wre = new WeightedRandomEnchant(ench3, weight);
			applicable.add(wre);
		}
		if (!applicable.isEmpty()) {
			ArrayList<GOTEnchantment> chosenEnchants = new ArrayList<>();
			for (int l = 0; l < enchants && !applicable.isEmpty(); ++l) {
				WeightedRandomEnchant chosenWre = (WeightedRandomEnchant) WeightedRandom.getRandomItem(random, applicable);
				GOTEnchantment chosenEnch = chosenWre.theEnchant;
				chosenEnchants.add(chosenEnch);
				applicable.remove(chosenWre);
				ArrayList<WeightedRandomEnchant> nowIncompatibles = new ArrayList<>();
				for (WeightedRandomEnchant wre : applicable) {
					GOTEnchantment otherEnch = wre.theEnchant;
					if (otherEnch.isCompatibleWith(chosenEnch)) {
						continue;
					}
					nowIncompatibles.add(wre);
				}
				applicable.removeAll(nowIncompatibles);
			}
			for (GOTEnchantment ench4 : chosenEnchants) {
				if (!ench4.canApply(itemstack, false)) {
					continue;
				}
				GOTEnchantmentHelper.setHasEnchant(itemstack, ench4);
			}
		}
		if (!GOTEnchantmentHelper.getEnchantList(itemstack).isEmpty() || GOTEnchantmentHelper.canApplyAnyEnchant(itemstack)) {
			GOTEnchantmentHelper.setAppliedRandomEnchants(itemstack);
		}
	}

	public static float calcBaseMeleeDamageBoost(ItemStack itemstack) {
		float damage = 0.0f;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentDamage)) {
					continue;
				}
				damage += ((GOTEnchantmentDamage) ench).getBaseDamageBoost();
			}
		}
		return damage;
	}

	public static int calcCommonArmorProtection(ItemStack itemstack) {
		int protection = 0;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentProtection)) {
					continue;
				}
				protection += ((GOTEnchantmentProtection) ench).protectLevel;
			}
		}
		return protection;
	}

	public static float calcEntitySpecificDamage(ItemStack itemstack, EntityLivingBase entity) {
		float damage = 0.0f;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentDamage)) {
					continue;
				}
				damage += ((GOTEnchantmentDamage) ench).getEntitySpecificDamage(entity);
			}
		}
		return damage;
	}

	public static int calcExtraKnockback(ItemStack itemstack) {
		int kb = 0;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentKnockback)) {
					continue;
				}
				kb += ((GOTEnchantmentKnockback) ench).knockback;
			}
		}
		return kb;
	}

	public static int calcFireAspect(ItemStack itemstack) {
		int fire = 0;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (ench != GOTEnchantment.fire) {
					continue;
				}
				fire += GOTEnchantmentWeaponSpecial.getFireAmount();
			}
		}
		return fire;
	}

	public static int calcFireAspectForMelee(ItemStack itemstack) {
		if (itemstack != null && GOTEnchantmentType.MELEE.canApply(itemstack, false)) {
			return GOTEnchantmentHelper.calcFireAspect(itemstack);
		}
		return 0;
	}

	public static int calcLootingLevel(ItemStack itemstack) {
		int looting = 0;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentLooting)) {
					continue;
				}
				looting += ((GOTEnchantmentLooting) ench).lootLevel;
			}
		}
		return looting;
	}

	public static float calcMeleeReachFactor(ItemStack itemstack) {
		float reach = 1.0f;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentMeleeReach)) {
					continue;
				}
				reach *= ((GOTEnchantmentMeleeReach) ench).reachFactor;
			}
		}
		return reach;
	}

	public static float calcMeleeSpeedFactor(ItemStack itemstack) {
		float speed = 1.0f;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentMeleeSpeed)) {
					continue;
				}
				speed *= ((GOTEnchantmentMeleeSpeed) ench).speedFactor;
			}
		}
		return speed;
	}

	public static float calcRangedDamageFactor(ItemStack itemstack) {
		float damage = 1.0f;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentRangedDamage)) {
					continue;
				}
				damage *= ((GOTEnchantmentRangedDamage) ench).damageFactor;
			}
		}
		return damage;
	}

	public static int calcRangedKnockback(ItemStack itemstack) {
		int kb = 0;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentRangedKnockback)) {
					continue;
				}
				kb += ((GOTEnchantmentRangedKnockback) ench).knockback;
			}
		}
		return kb;
	}

	public static int calcSpecialArmorSetProtection(ItemStack[] armor, DamageSource source) {
		int protection = 0;
		if (armor != null) {
			for (ItemStack itemstack : armor) {
				if (itemstack == null) {
					continue;
				}
				List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
				for (GOTEnchantment ench : enchants) {
					if (!(ench instanceof GOTEnchantmentProtectionSpecial)) {
						continue;
					}
					protection += ((GOTEnchantmentProtectionSpecial) ench).calcSpecialProtection(source);
				}
			}
		}
		return protection;
	}

	public static float calcToolEfficiency(ItemStack itemstack) {
		float speed = 1.0f;
		if (itemstack != null) {
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				if (!(ench instanceof GOTEnchantmentToolSpeed)) {
					continue;
				}
				speed *= ((GOTEnchantmentToolSpeed) ench).speedFactor;
			}
		}
		return speed;
	}

	public static float calcTradeValueFactor(ItemStack itemstack) {
		float value = 1.0f;
		List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
		for (GOTEnchantment ench : enchants) {
			value *= ench.getValueModifier();
			if (!ench.isSkilful()) {
				continue;
			}
			value *= 1.5f;
		}
		return value;
	}

	public static boolean canApplyAnyEnchant(ItemStack itemstack) {
		for (GOTEnchantment ench : GOTEnchantment.allEnchantments) {
			if (!ench.canApply(itemstack, true)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public static boolean checkEnchantCompatible(ItemStack itemstack, GOTEnchantment ench) {
		List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
		for (GOTEnchantment itemEnch : enchants) {
			if (itemEnch.isCompatibleWith(ench) && ench.isCompatibleWith(itemEnch)) {
				continue;
			}
			return false;
		}
		return true;
	}

	public static void clearEnchants(ItemStack itemstack) {
		NBTTagCompound itemData = itemstack.getTagCompound();
		if (itemData != null && itemData.hasKey("GOTEnch")) {
			itemData.removeTag("GOTEnch");
		}
	}

	public static void clearEnchantsAndProgress(ItemStack itemstack) {
		GOTEnchantmentHelper.clearEnchants(itemstack);
		NBTTagCompound itemData = itemstack.getTagCompound();
		if (itemData != null && itemData.hasKey("GOTEnchProgress")) {
			itemData.removeTag("GOTEnchProgress");
		}
	}

	public static int getAnvilCost(ItemStack itemstack) {
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt != null && nbt.hasKey("GOTRepairCost")) {
			return nbt.getInteger("GOTRepairCost");
		}
		return 0;
	}

	public static List<GOTEnchantment> getEnchantList(ItemStack itemstack) {
		ArrayList<GOTEnchantment> enchants = new ArrayList<>();
		NBTTagList tags = GOTEnchantmentHelper.getItemEnchantTags(itemstack, false);
		if (tags != null) {
			for (int i = 0; i < tags.tagCount(); ++i) {
				String s = tags.getStringTagAt(i);
				GOTEnchantment ench = GOTEnchantment.getEnchantmentByName(s);
				if (ench == null) {
					continue;
				}
				enchants.add(ench);
			}
		}
		return enchants;
	}

	public static NBTTagList getEntityEnchantTags(Entity entity, boolean create) {
		NBTTagCompound data = entity.getEntityData();
		NBTTagList tags = null;
		if (data != null && data.hasKey("GOTEnchEntity")) {
			tags = data.getTagList("GOTEnchEntity", 8);
		} else if (create) {
			tags = new NBTTagList();
			data.setTag("GOTEnchEntity", tags);
		}
		return tags;
	}

	public static String getFullEnchantedName(ItemStack itemstack, String name) {
		List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
		enchants = Lists.reverse(enchants);
		for (GOTEnchantment ench : enchants) {
			name = StatCollector.translateToLocalFormatted("got.enchant.nameFormat", ench.getDisplayName(), name);
		}
		return name;
	}

	public static NBTTagCompound getItemEnchantProgress(ItemStack itemstack, GOTEnchantment ench, boolean create) {
		NBTTagCompound itemData = itemstack.getTagCompound();
		if (itemData != null && itemData.hasKey("GOTEnchProgress")) {
			NBTTagList tags = itemData.getTagList("GOTEnchProgress", 10);
			for (int i = 0; i < tags.tagCount(); ++i) {
				NBTTagCompound enchData = tags.getCompoundTagAt(i);
				if (!enchData.getString("Name").equals(ench.enchantName)) {
					continue;
				}
				return enchData;
			}
			if (create) {
				NBTTagCompound enchData = new NBTTagCompound();
				enchData.setString("Name", ench.enchantName);
				tags.appendTag(enchData);
				return enchData;
			}
		} else if (create) {
			if (itemData == null) {
				itemData = new NBTTagCompound();
				itemstack.setTagCompound(itemData);
			}
			NBTTagList tags = new NBTTagList();
			itemData.setTag("GOTEnchProgress", tags);
			NBTTagCompound enchData = new NBTTagCompound();
			enchData.setString("Name", ench.enchantName);
			tags.appendTag(enchData);
			return enchData;
		}
		return null;
	}

	public static NBTTagList getItemEnchantTags(ItemStack itemstack, boolean create) {
		NBTTagCompound itemData = itemstack.getTagCompound();
		NBTTagList tags = null;
		if (itemData != null && itemData.hasKey("GOTEnch")) {
			tags = itemData.getTagList("GOTEnch", 8);
		} else if (create) {
			if (itemData == null) {
				itemData = new NBTTagCompound();
				itemstack.setTagCompound(itemData);
			}
			tags = new NBTTagList();
			itemData.setTag("GOTEnch", tags);
		}
		return tags;
	}

	public static int getMaxFireProtectionLevel(ItemStack[] armor) {
		int max = 0;
		if (armor != null) {
			for (ItemStack itemstack : armor) {
				if (itemstack == null) {
					continue;
				}
				List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
				for (GOTEnchantment ench : enchants) {
					int protection;
					if (!(ench instanceof GOTEnchantmentProtectionFire) || (protection = ((GOTEnchantmentProtectionFire) ench).protectLevel) <= max) {
						continue;
					}
					max = protection;
				}
			}
		}
		return max;
	}

	public static int getSkilfulWeight(GOTEnchantment ench) {
		int weight = ench.getEnchantWeight();
		double wd = weight;
		if (ench.isBeneficial()) {
			wd = Math.pow(wd, 0.3);
		}
		wd *= 100.0;
		if (!ench.isBeneficial()) {
			wd *= 0.15;
		}
		weight = (int) Math.round(wd);
		return Math.max(weight, 1);
	}

	public static boolean hasAppliedRandomEnchants(ItemStack itemstack) {
		NBTTagCompound nbt = itemstack.getTagCompound();
		if (nbt != null && nbt.hasKey("GOTRandomEnch")) {
			return nbt.getBoolean("GOTRandomEnch");
		}
		return false;
	}

	public static boolean hasEnchant(ItemStack itemstack, GOTEnchantment ench) {
		NBTTagList tags = GOTEnchantmentHelper.getItemEnchantTags(itemstack, false);
		if (tags != null) {
			for (int i = 0; i < tags.tagCount(); ++i) {
				String s = tags.getStringTagAt(i);
				if (!s.equals(ench.enchantName)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean hasMeleeOrRangedEnchant(DamageSource source, GOTEnchantment ench) {
		ItemStack weapon;
		EntityLivingBase attackerLiving;
		Entity attacker = source.getEntity();
		Entity sourceEntity = source.getSourceOfDamage();
		if (attacker instanceof EntityLivingBase && (attackerLiving = (EntityLivingBase) attacker) == sourceEntity && (weapon = attackerLiving.getHeldItem()) != null && GOTEnchantmentType.MELEE.canApply(weapon, false) && GOTEnchantmentHelper.hasEnchant(weapon, ench)) {
			return true;
		}
		return sourceEntity != null && GOTEnchantmentHelper.hasProjectileEnchantment(sourceEntity, ench);
	}

	public static boolean hasProjectileEnchantment(Entity entity, GOTEnchantment ench) {
		NBTTagList tags = GOTEnchantmentHelper.getEntityEnchantTags(entity, false);
		if (tags != null) {
			for (int i = 0; i < tags.tagCount(); ++i) {
				String s = tags.getStringTagAt(i);
				if (!s.equals(ench.enchantName)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean isReforgeable(ItemStack itemstack) {
		return !GOTEnchantmentHelper.getEnchantList(itemstack).isEmpty() || GOTEnchantmentHelper.canApplyAnyEnchant(itemstack);
	}

	public static boolean isSilkTouch(ItemStack itemstack) {
		return itemstack != null && GOTEnchantmentHelper.hasEnchant(itemstack, GOTEnchantment.toolSilk);
	}

	public static boolean negateDamage(ItemStack itemstack, Random random) {
		if (itemstack != null) {
			if (random == null) {
				if (backupRand == null) {
					backupRand = new Random();
				}
				random = backupRand;
			}
			List<GOTEnchantment> enchants = GOTEnchantmentHelper.getEnchantList(itemstack);
			for (GOTEnchantment ench : enchants) {
				float durability;
				if (!(ench instanceof GOTEnchantmentDurability) || ((durability = ((GOTEnchantmentDurability) ench).durabilityFactor) <= 1.0f)) {
					continue;
				}
				float inv = 1.0f / durability;
				if ((random.nextFloat() <= inv)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public static void onEntityUpdate(EntityLivingBase entity) {
		Random rand = entity.getRNG();
		if (GOTConfig.enchantingGOT) {
			if (entity instanceof EntityLiving && !(entity.getEntityData().getBoolean("GOTEnchantInit"))) {
				for (int i = 0; i < entity.getLastActiveItems().length; ++i) {
					ItemStack itemstack = entity.getEquipmentInSlot(i);
					GOTEnchantmentHelper.tryApplyRandomEnchantsForEntity(itemstack, rand);
				}
				entity.getEntityData().setBoolean("GOTEnchantInit", true);
			}
			if (entity instanceof EntityPlayerMP) {
				EntityPlayerMP entityplayer = (EntityPlayerMP) entity;
				UUID playerID = entityplayer.getUniqueID();
				InventoryPlayer inv = entityplayer.inventory;
				ItemStack[] lastKnownInv = lastKnownPlayerInventories.get(playerID);
				if (lastKnownInv == null) {
					lastKnownInv = new ItemStack[inv.getSizeInventory()];
				}
				for (int i = 0; i < inv.getSizeInventory(); ++i) {
					ItemStack itemstack = inv.getStackInSlot(i);
					if (ItemStack.areItemStacksEqual(itemstack, (lastKnownInv[i]))) {
						continue;
					}
					GOTEnchantmentHelper.tryApplyRandomEnchantsForEntity(itemstack, rand);
					lastKnownInv[i] = itemstack == null ? null : itemstack.copy();
				}
				if (GOTEnchantmentHelper.tryApplyRandomEnchantsForEntity(inv.getItemStack(), rand)) {
					entityplayer.updateHeldItem();
				}
				lastKnownPlayerInventories.put(playerID, lastKnownInv);
				if (lastKnownPlayerInventories.size() > 200) {
					lastKnownPlayerInventories.clear();
				}
			}
		}
	}

	public static void removeEnchant(ItemStack itemstack, GOTEnchantment ench) {
		NBTTagList tags = GOTEnchantmentHelper.getItemEnchantTags(itemstack, true);
		if (tags != null) {
			String enchName = ench.enchantName;
			for (int i = 0; i < tags.tagCount(); ++i) {
				String s = tags.getStringTagAt(i);
				if (!s.equals(enchName)) {
					continue;
				}
				tags.removeTag(i);
				break;
			}
		}
	}

	public static void setAnvilCost(ItemStack itemstack, int cost) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("GOTRepairCost", cost);
	}

	public static void setAppliedRandomEnchants(ItemStack itemstack) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setBoolean("GOTRandomEnch", true);
	}

	public static void setEnchantList(ItemStack itemstack, List<GOTEnchantment> enchants) {
		GOTEnchantmentHelper.clearEnchants(itemstack);
		for (GOTEnchantment ench : enchants) {
			GOTEnchantmentHelper.setHasEnchant(itemstack, ench);
		}
	}

	public static void setHasEnchant(ItemStack itemstack, GOTEnchantment ench) {
		NBTTagList tags;
		if (!GOTEnchantmentHelper.hasEnchant(itemstack, ench) && (tags = GOTEnchantmentHelper.getItemEnchantTags(itemstack, true)) != null) {
			String enchName = ench.enchantName;
			tags.appendTag(new NBTTagString(enchName));
		}
	}

	public static void setProjectileEnchantment(Entity entity, GOTEnchantment ench) {
		NBTTagList tags;
		if (!GOTEnchantmentHelper.hasProjectileEnchantment(entity, ench) && (tags = GOTEnchantmentHelper.getEntityEnchantTags(entity, true)) != null) {
			String enchName = ench.enchantName;
			tags.appendTag(new NBTTagString(enchName));
		}
	}

	public static boolean tryApplyRandomEnchantsForEntity(ItemStack itemstack, Random rand) {
		if (itemstack != null && !GOTEnchantmentHelper.hasAppliedRandomEnchants(itemstack) && GOTEnchantmentHelper.canApplyAnyEnchant(itemstack)) {
			GOTEnchantmentHelper.applyRandomEnchantments(itemstack, rand, false, false);
			return true;
		}
		return false;
	}

	public static class WeightedRandomEnchant extends WeightedRandom.Item {
		public GOTEnchantment theEnchant;

		public WeightedRandomEnchant(GOTEnchantment e, int weight) {
			super(weight);
			theEnchant = e;
		}
	}

}
