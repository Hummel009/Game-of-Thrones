package got.common.entity.other.utils;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.FishingHooks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTFishing {
	private static final Collection<FishingItem> FISH = new ArrayList<>();
	private static final Collection<FishingItem> JUNK = new ArrayList<>();
	private static final Collection<FishingItem> TREASURE = new ArrayList<>();

	static {
		FISH.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 60));
		FISH.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25));
		FISH.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2));
		FISH.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13));
		JUNK.add(new FishingItem(new ItemStack(Items.fishing_rod), 5).setMaxDurability(0.1f));
		JUNK.add(new FishingItem(new ItemStack(Items.wooden_sword), 2).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(Items.wooden_axe), 2).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(Items.wooden_pickaxe), 2).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(Items.wooden_shovel), 2).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(Items.wooden_hoe), 2).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(GOTItems.leatherHat), 10));
		JUNK.add(new FishingItem(new ItemStack(Items.leather_helmet), 5).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(Items.leather_boots), 5).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(GOTItems.boneHelmet), 2).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(GOTItems.boneBoots), 2).setMaxDurability(0.5f));
		JUNK.add(new FishingItem(new ItemStack(Items.skull, 1, 0), 5));
		JUNK.add(new FishingItem(new ItemStack(Items.bone), 20));
		JUNK.add(new FishingItem(new ItemStack(GOTBlocks.rottenLog, 1, 0), 10));
		JUNK.add(new FishingItem(new ItemStack(Items.leather), 10));
		JUNK.add(new FishingItem(new ItemStack(Items.string), 10));
		JUNK.add(new FishingItem(new ItemStack(Items.bowl), 10));
		JUNK.add(new FishingItem(new ItemStack(GOTItems.mug), 10));
		JUNK.add(new FishingItem(new ItemStack(Items.book), 5));
		JUNK.add(new FishingItem(new ItemStack(Items.stick), 10));
		JUNK.add(new FishingItem(new ItemStack(Items.feather), 10));
		JUNK.add(new FishingItem(new ItemStack(Items.dye, 1, 0), 5));
		JUNK.add(new FishingItem(new ItemStack(Items.rotten_flesh), 5));
		JUNK.add(new FishingItem(new ItemStack(GOTItems.saltedFlesh), 5));
		JUNK.add(new FishingItem(new ItemStack(Blocks.waterlily), 15));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.pearl), 200));
		TREASURE.add(new FishingItem(new ItemStack(Items.bow), 20).setMaxDurability(0.75f));
		TREASURE.add(new FishingItem(new ItemStack(Items.fishing_rod), 20).setMaxDurability(0.75f));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.ironDagger), 20).setMaxDurability(0.75f));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.bronzeDagger), 20).setMaxDurability(0.75f));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.coin, 1, 0), 100));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.coin, 1, 1), 10));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.coin, 1, 2), 1));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.pouch, 1, 0), 20));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.pouch, 1, 1), 10));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.pouch, 1, 2), 5));
		TREASURE.add(new FishingItem(new ItemStack(Items.iron_ingot), 20));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.bronzeIngot), 10));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.copperIngot), 10));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.tinIngot), 10));
		TREASURE.add(new FishingItem(new ItemStack(Items.gold_nugget), 50));
		TREASURE.add(new FishingItem(new ItemStack(Items.gold_ingot), 5));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.silverNugget), 50));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.silverIngot), 5));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.valyrianNugget), 5));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.copperRing), 10));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.bronzeRing), 10));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.silverRing), 10));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.goldRing), 5));
		TREASURE.add(new FishingItem(new ItemStack(GOTItems.valyrianRing), 1));
	}

	private GOTFishing() {
	}

	public static FishResult getFishResult(Random rand, float chance, int luck, int speed, boolean allowJunkTreasure) {
		float chance1 = chance;
		float junkChance = 0.1f - luck * 0.025f - speed * 0.01f;
		float treasureChance = 0.2f + luck * 0.01f - speed * 0.01f;
		junkChance = MathHelper.clamp_float(junkChance, 0.0f, 1.0f);
		treasureChance = MathHelper.clamp_float(treasureChance, 0.0f, 1.0f);
		if (allowJunkTreasure) {
			if (chance1 < junkChance) {
				ItemStack result = ((FishingItem) WeightedRandom.getRandomItem(rand, JUNK)).getRandomResult(rand);
				return new FishResult(FishingHooks.FishableCategory.JUNK, result);
			}
			chance1 -= junkChance;
			if (chance1 < treasureChance) {
				ItemStack result = ((FishingItem) WeightedRandom.getRandomItem(rand, TREASURE)).getRandomResult(rand);
				return new FishResult(FishingHooks.FishableCategory.TREASURE, result);
			}
		}
		ItemStack result = ((FishingItem) WeightedRandom.getRandomItem(rand, FISH)).getRandomResult(rand);
		return new FishResult(FishingHooks.FishableCategory.FISH, result);
	}

	private static class FishingItem extends WeightedRandom.Item {
		private final ItemStack theItem;
		private float maxDurability;

		private FishingItem(ItemStack item, int weight) {
			super(weight);
			theItem = item;
		}

		private ItemStack getRandomResult(Random rand) {
			ItemStack result = theItem.copy();
			if (maxDurability > 0.0f) {
				float damageF = 1.0f - rand.nextFloat() * maxDurability;
				int damage = (int) (damageF * result.getMaxDamage());
				damage = Math.min(damage, result.getMaxDamage());
				damage = Math.max(damage, 1);
				result.setItemDamage(damage);
			}
			return result;
		}

		@SuppressWarnings("unused")
		private float getMaxDurability() {
			return maxDurability;
		}

		private FishingItem setMaxDurability(float f) {
			maxDurability = f;
			return this;
		}
	}

	public static class FishResult {
		private final FishingHooks.FishableCategory category;
		private final ItemStack fishedItem;

		protected FishResult(FishingHooks.FishableCategory c, ItemStack item) {
			category = c;
			fishedItem = item;
		}

		public FishingHooks.FishableCategory getCategory() {
			return category;
		}

		public ItemStack getFishedItem() {
			return fishedItem;
		}
	}
}