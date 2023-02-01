package got.common.entity.other;

import java.util.*;

import got.common.database.GOTRegistry;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.FishingHooks;

public class GOTFishing {
	public static List<FishingItem> fish = new ArrayList<>();
	public static List<FishingItem> junk = new ArrayList<>();
	public static List<FishingItem> treasure = new ArrayList<>();

	static {
		fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 60));
		fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25));
		fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2));
		fish.add(new FishingItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13));
		junk.add(new FishingItem(new ItemStack(Items.fishing_rod), 5).setMaxDurability(0.1f));
		junk.add(new FishingItem(new ItemStack(Items.wooden_sword), 2).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(Items.wooden_axe), 2).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(Items.wooden_pickaxe), 2).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(Items.wooden_shovel), 2).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(Items.wooden_hoe), 2).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(GOTRegistry.leatherHat), 10));
		junk.add(new FishingItem(new ItemStack(Items.leather_helmet), 5).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(Items.leather_boots), 5).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(GOTRegistry.boneHelmet), 2).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(GOTRegistry.boneBoots), 2).setMaxDurability(0.5f));
		junk.add(new FishingItem(new ItemStack(Items.skull, 1, 0), 5));
		junk.add(new FishingItem(new ItemStack(Items.bone), 20));
		junk.add(new FishingItem(new ItemStack(GOTRegistry.rottenLog, 1, 0), 10));
		junk.add(new FishingItem(new ItemStack(Items.leather), 10));
		junk.add(new FishingItem(new ItemStack(Items.string), 10));
		junk.add(new FishingItem(new ItemStack(Items.bowl), 10));
		junk.add(new FishingItem(new ItemStack(GOTRegistry.mug), 10));
		junk.add(new FishingItem(new ItemStack(Items.book), 5));
		junk.add(new FishingItem(new ItemStack(Items.stick), 10));
		junk.add(new FishingItem(new ItemStack(Items.feather), 10));
		junk.add(new FishingItem(new ItemStack(Items.dye, 1, 0), 5));
		junk.add(new FishingItem(new ItemStack(Items.rotten_flesh), 5));
		junk.add(new FishingItem(new ItemStack(GOTRegistry.saltedFlesh), 5));
		junk.add(new FishingItem(new ItemStack(Blocks.waterlily), 15));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.pearl), 200));
		treasure.add(new FishingItem(new ItemStack(Items.bow), 20).setMaxDurability(0.75f));
		treasure.add(new FishingItem(new ItemStack(Items.fishing_rod), 20).setMaxDurability(0.75f));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.ironDagger), 20).setMaxDurability(0.75f));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.bronzeDagger), 20).setMaxDurability(0.75f));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.coin, 1, 0), 100));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.coin, 1, 1), 10));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.coin, 1, 2), 1));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.pouch, 1, 0), 20));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.pouch, 1, 1), 10));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.pouch, 1, 2), 5));
		treasure.add(new FishingItem(new ItemStack(Items.iron_ingot), 20));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.bronzeIngot), 10));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.copperIngot), 10));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.tinIngot), 10));
		treasure.add(new FishingItem(new ItemStack(Items.gold_nugget), 50));
		treasure.add(new FishingItem(new ItemStack(Items.gold_ingot), 5));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.silverNugget), 50));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.silverIngot), 5));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.valyrianNugget), 5));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.silverRing), 10));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.goldRing), 5));
		treasure.add(new FishingItem(new ItemStack(GOTRegistry.valyrianRing), 1));
	}

	public static FishResult getFishResult(Random rand, float chance, int luck, int speed, boolean allowJunkTreasure) {
		float junkChance = 0.1f - luck * 0.025f - speed * 0.01f;
		float treasureChance = 0.2f + luck * 0.01f - speed * 0.01f;
		junkChance = MathHelper.clamp_float(junkChance, 0.0f, 1.0f);
		treasureChance = MathHelper.clamp_float(treasureChance, 0.0f, 1.0f);
		if (allowJunkTreasure) {
			if (chance < junkChance) {
				ItemStack result = ((FishingItem) WeightedRandom.getRandomItem(rand, junk)).getRandomResult(rand);
				return new FishResult(FishingHooks.FishableCategory.JUNK, result);
			}
			chance -= junkChance;
			if (chance < treasureChance) {
				ItemStack result = ((FishingItem) WeightedRandom.getRandomItem(rand, treasure)).getRandomResult(rand);
				return new FishResult(FishingHooks.FishableCategory.TREASURE, result);
			}
		}
		ItemStack result = ((FishingItem) WeightedRandom.getRandomItem(rand, fish)).getRandomResult(rand);
		return new FishResult(FishingHooks.FishableCategory.FISH, result);
	}

	public static class FishingItem extends WeightedRandom.Item {
		public ItemStack theItem;
		public float maxDurability;

		public FishingItem(ItemStack item, int weight) {
			super(weight);
			theItem = item;
		}

		public ItemStack getRandomResult(Random rand) {
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

		public FishingItem setMaxDurability(float f) {
			maxDurability = f;
			return this;
		}
	}

	public static class FishResult {
		public FishingHooks.FishableCategory category;
		public ItemStack fishedItem;

		public FishResult(FishingHooks.FishableCategory c, ItemStack item) {
			category = c;
			fishedItem = item;
		}
	}

}
