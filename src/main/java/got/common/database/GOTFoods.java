package got.common.database;

import java.util.*;

import got.common.item.other.GOTItemMug;
import net.minecraft.init.Items;
import net.minecraft.item.*;

public class GOTFoods {
	public static GOTFoods WESTEROS = new GOTFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(GOTRegistry.muttonCooked), new ItemStack(GOTRegistry.deerCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(GOTRegistry.appleGreen), new ItemStack(GOTRegistry.pear), new ItemStack(GOTRegistry.olive), new ItemStack(GOTRegistry.plum), new ItemStack(Items.bread), new ItemStack(GOTRegistry.oliveBread), new ItemStack(GOTRegistry.blueberry), new ItemStack(GOTRegistry.blackberry), new ItemStack(GOTRegistry.cranberry) });
	public static GOTFoods WESTEROS_DRINK = new GOTFoods(new ItemStack[] { new ItemStack(GOTRegistry.mugAle), new ItemStack(GOTRegistry.mugAle), new ItemStack(GOTRegistry.mugAle), new ItemStack(GOTRegistry.mugMead), new ItemStack(GOTRegistry.mugCider), new ItemStack(GOTRegistry.mugPerry), new ItemStack(GOTRegistry.mugAppleJuice) }).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_SILVER, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.BOTTLE, GOTItemMug.Vessel.HORN);
	public static GOTFoods WILD = new GOTFoods(new ItemStack[] { new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(GOTRegistry.muttonCooked), new ItemStack(GOTRegistry.gammon), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(GOTRegistry.appleGreen), new ItemStack(GOTRegistry.pear), new ItemStack(Items.bread), new ItemStack(GOTRegistry.rabbitCooked), new ItemStack(GOTRegistry.chestnutRoast) });
	public static GOTFoods WILD_DRINK = new GOTFoods(new ItemStack[] { new ItemStack(GOTRegistry.mugAle), new ItemStack(GOTRegistry.mugAle), new ItemStack(GOTRegistry.mugMead), new ItemStack(GOTRegistry.mugCider), new ItemStack(GOTRegistry.mugRum) }).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.SKULL, GOTItemMug.Vessel.SKIN, GOTItemMug.Vessel.HORN);
	public static GOTFoods YITI = new GOTFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(GOTRegistry.oliveBread), new ItemStack(Items.carrot), new ItemStack(GOTRegistry.lettuce), new ItemStack(GOTRegistry.turnip), new ItemStack(GOTRegistry.turnipCooked), new ItemStack(GOTRegistry.rabbitCooked), new ItemStack(GOTRegistry.deerCooked), new ItemStack(GOTRegistry.olive), new ItemStack(GOTRegistry.plum), new ItemStack(GOTRegistry.almond), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_porkchop), new ItemStack(GOTRegistry.grapeRed), new ItemStack(GOTRegistry.grapeWhite), new ItemStack(GOTRegistry.raisins), new ItemStack(GOTRegistry.date), new ItemStack(GOTRegistry.pomegranate) });
	public static GOTFoods YITI_DRINK = new GOTFoods(new ItemStack[] { new ItemStack(GOTRegistry.mugSourMilk), new ItemStack(GOTRegistry.mugSourMilk), new ItemStack(GOTRegistry.mugSourMilk), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugAle), new ItemStack(GOTRegistry.mugRedWine), new ItemStack(GOTRegistry.mugWhiteWine), new ItemStack(GOTRegistry.mugRedGrapeJuice), new ItemStack(GOTRegistry.mugWhiteGrapeJuice), new ItemStack(GOTRegistry.mugPomegranateWine), new ItemStack(GOTRegistry.mugPomegranateJuice) }).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_SILVER, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.GLASS, GOTItemMug.Vessel.BOTTLE, GOTItemMug.Vessel.SKIN);
	public static GOTFoods ESSOS = new GOTFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(GOTRegistry.oliveBread), new ItemStack(Items.apple), new ItemStack(GOTRegistry.appleGreen), new ItemStack(GOTRegistry.pear), new ItemStack(GOTRegistry.date), new ItemStack(GOTRegistry.lemon), new ItemStack(GOTRegistry.orange), new ItemStack(GOTRegistry.lime), new ItemStack(GOTRegistry.olive), new ItemStack(GOTRegistry.almond), new ItemStack(GOTRegistry.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(GOTRegistry.lettuce), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(GOTRegistry.muttonCooked), new ItemStack(GOTRegistry.kebab), new ItemStack(GOTRegistry.shishKebab), new ItemStack(GOTRegistry.camelCooked) });
	public static GOTFoods ESSOS_DRINK = new GOTFoods(new ItemStack[] { new ItemStack(GOTRegistry.mugWater), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugCactusLiqueur), new ItemStack(GOTRegistry.mugOrangeJuice), new ItemStack(GOTRegistry.mugLemonLiqueur), new ItemStack(GOTRegistry.mugLemonade), new ItemStack(GOTRegistry.mugLimeLiqueur), new ItemStack(GOTRegistry.mugAle), new ItemStack(GOTRegistry.mugCider) }).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.BOTTLE, GOTItemMug.Vessel.SKIN);
	public static GOTFoods NOMAD = new GOTFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(GOTRegistry.oliveBread), new ItemStack(GOTRegistry.date), new ItemStack(Items.cooked_beef), new ItemStack(GOTRegistry.muttonCooked), new ItemStack(GOTRegistry.kebab), new ItemStack(GOTRegistry.shishKebab), new ItemStack(GOTRegistry.camelCooked), new ItemStack(GOTRegistry.camelCooked), new ItemStack(GOTRegistry.camelCooked), new ItemStack(GOTRegistry.camelCooked), new ItemStack(GOTRegistry.saltedFlesh) });
	public static GOTFoods NOMAD_DRINK = new GOTFoods(new ItemStack[] { new ItemStack(GOTRegistry.mugWater), new ItemStack(GOTRegistry.mugWater), new ItemStack(GOTRegistry.mugWater), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugAraq), new ItemStack(GOTRegistry.mugCactusLiqueur), new ItemStack(GOTRegistry.mugAle) }).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.SKIN);
	public static GOTFoods SOTHORYOS = new GOTFoods(new ItemStack[] { new ItemStack(Items.bread), new ItemStack(GOTRegistry.bananaBread), new ItemStack(GOTRegistry.cornBread), new ItemStack(GOTRegistry.corn), new ItemStack(GOTRegistry.cornCooked), new ItemStack(Items.baked_potato), new ItemStack(GOTRegistry.banana), new ItemStack(GOTRegistry.mango), new ItemStack(Items.melon), new ItemStack(GOTRegistry.melonSoup), new ItemStack(Items.cooked_fished) });
	public static GOTFoods SOTHORYOS_DRINK = new GOTFoods(new ItemStack[] { new ItemStack(GOTRegistry.mugChocolate), new ItemStack(GOTRegistry.mugMangoJuice), new ItemStack(GOTRegistry.mugBananaBeer), new ItemStack(GOTRegistry.mugMelonLiqueur), new ItemStack(GOTRegistry.mugCornLiquor) }).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_WOOD);
	public static GOTFoods RICH_DRINK = new GOTFoods(new ItemStack[] { new ItemStack(GOTRegistry.mugRedWine) }).setDrinkVessels(GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.BOTTLE);
	public ItemStack[] foodList;
	public GOTItemMug.Vessel[] drinkVessels;
	public GOTItemMug.Vessel[] drinkVesselsPlaceable;

	public GOTFoods(ItemStack[] items) {
		foodList = items;
	}

	public GOTItemMug.Vessel[] getDrinkVessels() {
		return drinkVessels;
	}

	public GOTItemMug.Vessel[] getPlaceableDrinkVessels() {
		return drinkVesselsPlaceable;
	}

	public ItemStack getRandomBrewableDrink(Random random) {
		ArrayList<ItemStack> alcohols = new ArrayList<>();
		for (ItemStack itemstack : foodList) {
			Item item = itemstack.getItem();
			if (!(item instanceof GOTItemMug) || !((GOTItemMug) item).isBrewable) {
				continue;
			}
			alcohols.add(itemstack.copy());
		}
		ItemStack drink = alcohols.get(random.nextInt(alcohols.size()));
		setDrinkVessel(drink, random, false);
		return drink;
	}

	public ItemStack getRandomFood(Random random) {
		ItemStack food = foodList[random.nextInt(foodList.length)].copy();
		setDrinkVessel(food, random, false);
		return food;
	}

	public ItemStack getRandomFoodForPlate(Random random) {
		ArrayList<ItemStack> foodsNoContainer = new ArrayList<>();
		for (ItemStack itemstack : foodList) {
			Item item = itemstack.getItem();
			if (item.hasContainerItem(itemstack)) {
				continue;
			}
			foodsNoContainer.add(itemstack.copy());
		}
		return foodsNoContainer.get(random.nextInt(foodsNoContainer.size()));
	}

	public ItemStack getRandomPlaceableDrink(Random random) {
		ItemStack food = foodList[random.nextInt(foodList.length)].copy();
		setDrinkVessel(food, random, true);
		return food;
	}

	public GOTItemMug.Vessel getRandomPlaceableVessel(Random random) {
		return drinkVesselsPlaceable[random.nextInt(drinkVesselsPlaceable.length)];
	}

	public GOTItemMug.Vessel getRandomVessel(Random random) {
		return drinkVessels[random.nextInt(drinkVessels.length)];
	}

	public void setDrinkVessel(ItemStack itemstack, Random random, boolean requirePlaceable) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug && ((GOTItemMug) item).isFullMug) {
			GOTItemMug.Vessel v = requirePlaceable ? getRandomPlaceableVessel(random) : getRandomVessel(random);
			GOTItemMug.setVessel(itemstack, v, true);
		}
	}

	public GOTFoods setDrinkVessels(GOTItemMug.Vessel... vessels) {
		drinkVessels = vessels;
		ArrayList<GOTItemMug.Vessel> placeable = new ArrayList<>();
		for (GOTItemMug.Vessel v : drinkVessels) {
			if (!v.canPlace) {
				continue;
			}
			placeable.add(v);
		}
		drinkVesselsPlaceable = !placeable.isEmpty() ? placeable.toArray(new GOTItemMug.Vessel[0]) : new GOTItemMug.Vessel[] { GOTItemMug.Vessel.MUG };
		return this;
	}
}
