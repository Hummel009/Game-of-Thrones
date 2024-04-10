package got.common.database;

import got.common.item.other.GOTItemMug;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class GOTFoods {
	public static final GOTFoods WESTEROS = new GOTFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(GOTItems.muttonCooked), new ItemStack(GOTItems.deerCooked), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(GOTItems.appleGreen), new ItemStack(GOTItems.pear), new ItemStack(GOTItems.olive), new ItemStack(GOTItems.plum), new ItemStack(Items.bread), new ItemStack(GOTItems.oliveBread), new ItemStack(GOTItems.blueberry), new ItemStack(GOTItems.blackberry), new ItemStack(GOTItems.cranberry)});
	public static final GOTFoods WESTEROS_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugMead), new ItemStack(GOTItems.mugCider), new ItemStack(GOTItems.mugPerry), new ItemStack(GOTItems.mugAppleJuice)}).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_SILVER, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.BOTTLE, GOTItemMug.Vessel.HORN);
	public static final GOTFoods WILD = new GOTFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(GOTItems.muttonCooked), new ItemStack(GOTItems.gammon), new ItemStack(Items.baked_potato), new ItemStack(Items.apple), new ItemStack(GOTItems.appleGreen), new ItemStack(GOTItems.pear), new ItemStack(Items.bread), new ItemStack(GOTItems.rabbitCooked), new ItemStack(GOTItems.chestnutRoast)});
	public static final GOTFoods WILD_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugMead), new ItemStack(GOTItems.mugCider), new ItemStack(GOTItems.mugRum)}).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.SKULL, GOTItemMug.Vessel.SKIN, GOTItemMug.Vessel.HORN);
	public static final GOTFoods YITI = new GOTFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(GOTItems.oliveBread), new ItemStack(Items.carrot), new ItemStack(GOTItems.lettuce), new ItemStack(GOTItems.turnip), new ItemStack(GOTItems.turnipCooked), new ItemStack(GOTItems.rabbitCooked), new ItemStack(GOTItems.deerCooked), new ItemStack(GOTItems.olive), new ItemStack(GOTItems.plum), new ItemStack(GOTItems.almond), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_porkchop), new ItemStack(GOTItems.grapeRed), new ItemStack(GOTItems.grapeWhite), new ItemStack(GOTItems.raisins), new ItemStack(GOTItems.date), new ItemStack(GOTItems.pomegranate)});
	public static final GOTFoods YITI_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugSourMilk), new ItemStack(GOTItems.mugSourMilk), new ItemStack(GOTItems.mugSourMilk), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugRedWine), new ItemStack(GOTItems.mugWhiteWine), new ItemStack(GOTItems.mugRedGrapeJuice), new ItemStack(GOTItems.mugWhiteGrapeJuice), new ItemStack(GOTItems.mugPomegranateWine), new ItemStack(GOTItems.mugPomegranateJuice)}).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_SILVER, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.GLASS, GOTItemMug.Vessel.BOTTLE, GOTItemMug.Vessel.SKIN);
	public static final GOTFoods ESSOS = new GOTFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(GOTItems.oliveBread), new ItemStack(Items.apple), new ItemStack(GOTItems.appleGreen), new ItemStack(GOTItems.pear), new ItemStack(GOTItems.date), new ItemStack(GOTItems.lemon), new ItemStack(GOTItems.orange), new ItemStack(GOTItems.lime), new ItemStack(GOTItems.olive), new ItemStack(GOTItems.almond), new ItemStack(GOTItems.plum), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(GOTItems.lettuce), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_fished), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(GOTItems.muttonCooked), new ItemStack(GOTItems.kebab), new ItemStack(GOTItems.shishKebab), new ItemStack(GOTItems.camelCooked)});
	public static final GOTFoods ESSOS_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugWater), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugCactusLiqueur), new ItemStack(GOTItems.mugOrangeJuice), new ItemStack(GOTItems.mugLemonLiqueur), new ItemStack(GOTItems.mugLemonade), new ItemStack(GOTItems.mugLimeLiqueur), new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugCider)}).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.BOTTLE, GOTItemMug.Vessel.SKIN);
	public static final GOTFoods NOMAD = new GOTFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(GOTItems.oliveBread), new ItemStack(GOTItems.date), new ItemStack(Items.cooked_beef), new ItemStack(GOTItems.muttonCooked), new ItemStack(GOTItems.kebab), new ItemStack(GOTItems.shishKebab), new ItemStack(GOTItems.camelCooked), new ItemStack(GOTItems.camelCooked), new ItemStack(GOTItems.camelCooked), new ItemStack(GOTItems.camelCooked), new ItemStack(GOTItems.saltedFlesh)});
	public static final GOTFoods NOMAD_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugWater), new ItemStack(GOTItems.mugWater), new ItemStack(GOTItems.mugWater), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugAraq), new ItemStack(GOTItems.mugCactusLiqueur), new ItemStack(GOTItems.mugAle)}).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.SKIN);
	public static final GOTFoods SOTHORYOS = new GOTFoods(new ItemStack[]{new ItemStack(Items.bread), new ItemStack(GOTItems.bananaBread), new ItemStack(GOTItems.cornBread), new ItemStack(GOTItems.corn), new ItemStack(GOTItems.cornCooked), new ItemStack(Items.baked_potato), new ItemStack(GOTItems.banana), new ItemStack(GOTItems.mango), new ItemStack(Items.melon), new ItemStack(GOTItems.melonSoup), new ItemStack(Items.cooked_fished)});
	public static final GOTFoods SOTHORYOS_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugChocolate), new ItemStack(GOTItems.mugMangoJuice), new ItemStack(GOTItems.mugBananaBeer), new ItemStack(GOTItems.mugMelonLiqueur), new ItemStack(GOTItems.mugCornLiquor)}).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.GOBLET_WOOD);
	public static final GOTFoods RICH_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugRedWine)}).setDrinkVessels(GOTItemMug.Vessel.GOBLET_GOLD, GOTItemMug.Vessel.BOTTLE);

	private final ItemStack[] foodList;

	private GOTItemMug.Vessel[] drinkVessels;
	private GOTItemMug.Vessel[] drinkVesselsPlaceable;

	private GOTFoods(ItemStack[] items) {
		foodList = items;
	}

	public GOTItemMug.Vessel[] getDrinkVessels() {
		return drinkVessels;
	}

	private GOTFoods setDrinkVessels(GOTItemMug.Vessel... vessels) {
		drinkVessels = vessels;
		ArrayList<GOTItemMug.Vessel> placeable = new ArrayList<>();
		for (GOTItemMug.Vessel v : drinkVessels) {
			if (!v.isCanPlace()) {
				continue;
			}
			placeable.add(v);
		}
		if (placeable.isEmpty()) {
			drinkVesselsPlaceable = new GOTItemMug.Vessel[]{GOTItemMug.Vessel.MUG};
		} else {
			drinkVesselsPlaceable = placeable.toArray(new GOTItemMug.Vessel[0]);
		}
		return this;
	}

	public GOTItemMug.Vessel[] getPlaceableDrinkVessels() {
		return drinkVesselsPlaceable;
	}

	public ItemStack getRandomBrewableDrink(Random random) {
		ArrayList<ItemStack> alcohols = new ArrayList<>();
		for (ItemStack itemstack : foodList) {
			Item item = itemstack.getItem();
			if (!(item instanceof GOTItemMug) || !((GOTItemMug) item).isBrewable()) {
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

	private GOTItemMug.Vessel getRandomPlaceableVessel(Random random) {
		return drinkVesselsPlaceable[random.nextInt(drinkVesselsPlaceable.length)];
	}

	public GOTItemMug.Vessel getRandomVessel(Random random) {
		return drinkVessels[random.nextInt(drinkVessels.length)];
	}

	private void setDrinkVessel(ItemStack itemstack, Random random, boolean requirePlaceable) {
		Item item = itemstack.getItem();
		if (item instanceof GOTItemMug && ((GOTItemMug) item).isFullMug()) {
			GOTItemMug.Vessel v;
			if (requirePlaceable) {
				v = getRandomPlaceableVessel(random);
			} else {
				v = getRandomVessel(random);
			}
			GOTItemMug.setVessel(itemstack, v, true);
		}
	}
}
