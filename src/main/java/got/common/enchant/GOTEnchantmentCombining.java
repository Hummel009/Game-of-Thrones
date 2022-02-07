package got.common.enchant;

import java.util.*;

import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemModifierTemplate;
import net.minecraft.item.ItemStack;

public class GOTEnchantmentCombining {
	public static List<CombineRecipe> allCombineRecipes = new ArrayList<>();

	public static void combine(GOTEnchantment in, GOTEnchantment out, int cost) {
		if (!in.hasTemplateItem() || !out.hasTemplateItem()) {
			throw new IllegalArgumentException("Cannot create a modifier combining recipe for modifiers which lack scroll items!");
		}
		if (cost < 0) {
			throw new IllegalArgumentException("Cost must not be negative!");
		}
		allCombineRecipes.add(new CombineRecipe(in, out, cost));
	}

	public static CombineRecipe getCombinationResult(ItemStack item1, ItemStack item2) {
		GOTEnchantment mod1;
		if (item1 != null && item2 != null && item1.getItem() instanceof GOTItemModifierTemplate && item2.getItem() instanceof GOTItemModifierTemplate && (mod1 = GOTItemModifierTemplate.getModifier(item1)) == GOTItemModifierTemplate.getModifier(item2)) {
			for (CombineRecipe recipe : allCombineRecipes) {
				if (recipe.inputMod != mod1) {
					continue;
				}
				return recipe;
			}
		}
		return null;
	}

	public static void onInit() {
		GOTEnchantmentCombining.combine(GOTEnchantment.strong1, GOTEnchantment.strong2, 200);
		GOTEnchantmentCombining.combine(GOTEnchantment.strong2, GOTEnchantment.strong3, 800);
		GOTEnchantmentCombining.combine(GOTEnchantment.strong3, GOTEnchantment.strong4, 1600);
		GOTEnchantmentCombining.combine(GOTEnchantment.durable1, GOTEnchantment.durable2, 300);
		GOTEnchantmentCombining.combine(GOTEnchantment.durable2, GOTEnchantment.durable3, 1500);
		GOTEnchantmentCombining.combine(GOTEnchantment.knockback1, GOTEnchantment.knockback2, 2500);
		GOTEnchantmentCombining.combine(GOTEnchantment.toolSpeed1, GOTEnchantment.toolSpeed2, 200);
		GOTEnchantmentCombining.combine(GOTEnchantment.toolSpeed2, GOTEnchantment.toolSpeed3, 800);
		GOTEnchantmentCombining.combine(GOTEnchantment.toolSpeed3, GOTEnchantment.toolSpeed4, 1500);
		GOTEnchantmentCombining.combine(GOTEnchantment.looting1, GOTEnchantment.looting2, 400);
		GOTEnchantmentCombining.combine(GOTEnchantment.looting2, GOTEnchantment.looting3, 1500);
		GOTEnchantmentCombining.combine(GOTEnchantment.protect1, GOTEnchantment.protect2, 2000);
		GOTEnchantmentCombining.combine(GOTEnchantment.protectFire1, GOTEnchantment.protectFire2, 400);
		GOTEnchantmentCombining.combine(GOTEnchantment.protectFire2, GOTEnchantment.protectFire3, 1500);
		GOTEnchantmentCombining.combine(GOTEnchantment.protectFall1, GOTEnchantment.protectFall2, 400);
		GOTEnchantmentCombining.combine(GOTEnchantment.protectFall2, GOTEnchantment.protectFall3, 1500);
		GOTEnchantmentCombining.combine(GOTEnchantment.protectRanged1, GOTEnchantment.protectRanged2, 400);
		GOTEnchantmentCombining.combine(GOTEnchantment.protectRanged2, GOTEnchantment.protectRanged3, 1500);
		GOTEnchantmentCombining.combine(GOTEnchantment.rangedStrong1, GOTEnchantment.rangedStrong2, 400);
		GOTEnchantmentCombining.combine(GOTEnchantment.rangedStrong2, GOTEnchantment.rangedStrong3, 1500);
		GOTEnchantmentCombining.combine(GOTEnchantment.rangedKnockback1, GOTEnchantment.rangedKnockback2, 2500);
	}

	public static class CombineRecipe {
		public GOTEnchantment inputMod;
		public GOTEnchantment outputMod;
		public int cost;

		public CombineRecipe(GOTEnchantment in, GOTEnchantment out, int c) {
			inputMod = in;
			outputMod = out;
			cost = c;
		}

		public ItemStack createOutputItem() {
			ItemStack item = new ItemStack(GOTRegistry.smithScroll);
			GOTItemModifierTemplate.setModifier(item, outputMod);
			return item;
		}
	}

}
