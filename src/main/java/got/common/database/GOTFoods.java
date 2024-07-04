package got.common.database;

import got.common.item.other.GOTItemMug;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class GOTFoods {
	public static final GOTFoods DEFAULT = new GOTFoods(new ItemStack[]{new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_fished), new ItemStack(Items.apple), new ItemStack(GOTItems.muttonCooked), new ItemStack(GOTItems.appleGreen), new ItemStack(Items.carrot), new ItemStack(Items.baked_potato), new ItemStack(GOTItems.corn), new ItemStack(Items.bread), new ItemStack(GOTItems.blueberry), new ItemStack(GOTItems.blackberry), new ItemStack(GOTItems.gammon), new ItemStack(GOTItems.rabbitCooked), new ItemStack(GOTItems.chestnutRoast)});
	public static final GOTFoods DEFAULT_DRINK = new GOTFoods(new ItemStack[]{new ItemStack(GOTItems.mugAle), new ItemStack(GOTItems.mugMead), new ItemStack(GOTItems.mugCider), new ItemStack(GOTItems.mugPerry), new ItemStack(GOTItems.mugSourMilk), new ItemStack(GOTItems.mugRedWine), new ItemStack(GOTItems.mugVodka), new ItemStack(GOTItems.mugWater), new ItemStack(GOTItems.mugWhiteWine), new ItemStack(GOTItems.mugRum)}).setDrinkVessels(GOTItemMug.Vessel.MUG, GOTItemMug.Vessel.MUG_CLAY, GOTItemMug.Vessel.GOBLET_COPPER, GOTItemMug.Vessel.GOBLET_BRONZE, GOTItemMug.Vessel.GOBLET_WOOD, GOTItemMug.Vessel.BOTTLE, GOTItemMug.Vessel.SKIN, GOTItemMug.Vessel.HORN);

	private final ItemStack[] foodList;

	private GOTItemMug.Vessel[] drinkVessels;
	private GOTItemMug.Vessel[] drinkVesselsPlaceable;

	@SuppressWarnings("WeakerAccess")
	public GOTFoods(ItemStack[] items) {
		foodList = items;
	}

	public GOTItemMug.Vessel[] getDrinkVessels() {
		return drinkVessels;
	}

	@SuppressWarnings("WeakerAccess")
	public GOTFoods setDrinkVessels(GOTItemMug.Vessel... vessels) {
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

	private GOTItemMug.Vessel getRandomVessel(Random random) {
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