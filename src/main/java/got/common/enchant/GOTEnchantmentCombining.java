package got.common.enchant;

import got.common.database.GOTItems;
import got.common.item.other.GOTItemModifierTemplate;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class GOTEnchantmentCombining {
	private static final Collection<CombineRecipe> ALL_COMBINE_RECIPES = new ArrayList<>();

	private GOTEnchantmentCombining() {
	}

	private static void combine(GOTEnchantment in, GOTEnchantment out, int cost) {
		if (!in.hasTemplateItem() || !out.hasTemplateItem()) {
			throw new IllegalArgumentException("Cannot create a modifier combining recipe for modifiers which lack scroll items!");
		}
		if (cost < 0) {
			throw new IllegalArgumentException("Cost must not be negative!");
		}
		ALL_COMBINE_RECIPES.add(new CombineRecipe(in, out, cost));
	}

	public static CombineRecipe getCombinationResult(ItemStack item1, ItemStack item2) {
		if (item1 != null && item2 != null && item1.getItem() instanceof GOTItemModifierTemplate && item2.getItem() instanceof GOTItemModifierTemplate) {
			GOTEnchantment mod1 = GOTItemModifierTemplate.getModifier(item1);
			GOTEnchantment mod2 = GOTItemModifierTemplate.getModifier(item2);
			if (mod1 == mod2) {
				for (CombineRecipe recipe : ALL_COMBINE_RECIPES) {
					if (recipe.getInputMod() == mod1) {
						return recipe;
					}
				}
			}
		}
		return null;
	}

	public static void onInit() {
		combine(GOTEnchantment.STRONG_1, GOTEnchantment.STRONG_2, 200);
		combine(GOTEnchantment.STRONG_2, GOTEnchantment.STRONG_3, 800);
		combine(GOTEnchantment.STRONG_3, GOTEnchantment.STRONG_4, 1600);

		combine(GOTEnchantment.DURABLE_1, GOTEnchantment.DURABLE_2, 300);
		combine(GOTEnchantment.DURABLE_2, GOTEnchantment.DURABLE_3, 1500);

		combine(GOTEnchantment.KNOCKBACK_1, GOTEnchantment.KNOCKBACK_2, 2500);

		combine(GOTEnchantment.TOOL_SPEED_1, GOTEnchantment.TOOL_SPEED_2, 200);
		combine(GOTEnchantment.TOOL_SPEED_2, GOTEnchantment.TOOL_SPEED_3, 800);
		combine(GOTEnchantment.TOOL_SPEED_3, GOTEnchantment.TOOL_SPEED_4, 1500);

		combine(GOTEnchantment.LOOTING_1, GOTEnchantment.LOOTING_2, 400);
		combine(GOTEnchantment.LOOTING_2, GOTEnchantment.LOOTING_3, 1500);

		combine(GOTEnchantment.PROTECT_1, GOTEnchantment.PROTECT_2, 2000);

		combine(GOTEnchantment.PROTECT_FIRE_1, GOTEnchantment.PROTECT_FIRE_2, 400);
		combine(GOTEnchantment.PROTECT_FIRE_2, GOTEnchantment.PROTECT_FIRE_3, 1500);

		combine(GOTEnchantment.PROTECT_FALL_1, GOTEnchantment.PROTECT_FALL_2, 400);
		combine(GOTEnchantment.PROTECT_FALL_2, GOTEnchantment.PROTECT_FALL_3, 1500);

		combine(GOTEnchantment.PROTECT_RANGED_1, GOTEnchantment.PROTECT_RANGED_2, 400);
		combine(GOTEnchantment.PROTECT_RANGED_2, GOTEnchantment.PROTECT_RANGED_3, 1500);

		combine(GOTEnchantment.RANGED_STRONG_1, GOTEnchantment.RANGED_STRONG_2, 400);
		combine(GOTEnchantment.RANGED_STRONG_2, GOTEnchantment.RANGED_STRONG_3, 1500);

		combine(GOTEnchantment.RANGED_KNOCKBACK_1, GOTEnchantment.RANGED_KNOCKBACK_2, 2500);
	}

	public static class CombineRecipe {
		private final GOTEnchantment inputMod;
		private final GOTEnchantment outputMod;
		private final int cost;

		protected CombineRecipe(GOTEnchantment in, GOTEnchantment out, int c) {
			inputMod = in;
			outputMod = out;
			cost = c;
		}

		public ItemStack createOutputItem() {
			ItemStack item = new ItemStack(GOTItems.smithScroll);
			GOTItemModifierTemplate.setModifier(item, outputMod);
			return item;
		}

		public int getCost() {
			return cost;
		}

		protected GOTEnchantment getInputMod() {
			return inputMod;
		}
	}
}