package got.common.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import got.common.GOTConfig;
import got.common.block.leaves.GOTBlockLeavesBase;
import got.common.block.other.GOTBlockFallenLeaves;
import got.common.block.other.GOTBlockStairs;
import got.common.block.other.GOTBlockTreasurePile;
import got.common.block.planks.GOTBlockPlanksBase;
import got.common.block.sapling.GOTBlockSaplingBase;
import got.common.block.slab.GOTBlockSlabBase;
import got.common.block.wood.GOTBlockWoodBase;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.item.other.GOTItemBanner;
import got.common.item.other.GOTItemBerry;
import got.common.item.other.GOTItemRobes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;
import java.util.*;

public class GOTRecipe {
	public static final List<IRecipe> HILLMEN = new ArrayList<>();
	public static final List<IRecipe> WILDLING = new ArrayList<>();
	public static final List<IRecipe> GIFT = new ArrayList<>();
	public static final List<IRecipe> SUMMER = new ArrayList<>();
	public static final List<IRecipe> LHAZAR = new ArrayList<>();

	public static final List<IRecipe> NORTH = new ArrayList<>();
	public static final List<IRecipe> ARRYN = new ArrayList<>();
	public static final List<IRecipe> DORNE = new ArrayList<>();
	public static final List<IRecipe> REACH = new ArrayList<>();
	public static final List<IRecipe> WESTERLANDS = new ArrayList<>();
	public static final List<IRecipe> CROWNLANDS = new ArrayList<>();
	public static final List<IRecipe> STORMLANDS = new ArrayList<>();
	public static final List<IRecipe> DRAGONSTONE = new ArrayList<>();
	public static final List<IRecipe> RIVERLANDS = new ArrayList<>();
	public static final List<IRecipe> IRONBORN = new ArrayList<>();

	public static final List<IRecipe> VOLANTIS = new ArrayList<>();
	public static final List<IRecipe> BRAAVOS = new ArrayList<>();
	public static final List<IRecipe> TYROSH = new ArrayList<>();
	public static final List<IRecipe> LYS = new ArrayList<>();
	public static final List<IRecipe> MYR = new ArrayList<>();
	public static final List<IRecipe> QOHOR = new ArrayList<>();
	public static final List<IRecipe> LORATH = new ArrayList<>();
	public static final List<IRecipe> NORVOS = new ArrayList<>();
	public static final List<IRecipe> PENTOS = new ArrayList<>();
	public static final List<IRecipe> QARTH = new ArrayList<>();
	public static final List<IRecipe> GHISCAR = new ArrayList<>();

	public static final List<IRecipe> SOTHORYOS = new ArrayList<>();
	public static final List<IRecipe> YI_TI = new ArrayList<>();
	public static final List<IRecipe> IBBEN = new ArrayList<>();
	public static final List<IRecipe> ASSHAI = new ArrayList<>();
	public static final List<IRecipe> JOGOS_NHAI = new ArrayList<>();
	public static final List<IRecipe> DOTHRAKI = new ArrayList<>();
	public static final List<IRecipe> MOSSOVY = new ArrayList<>();

	public static final List<IRecipe> UNSMELT = new ArrayList<>();

	private static final String[] DYE_ORE_NAMES = {"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	private static final Collection<IRecipe> SLAB = new ArrayList<>();

	private GOTRecipe() {
	}

	public static void onInit() {
		registerOres();
		RecipeSorter.register("got:armorDyes", GOTRecipeArmorDyes.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:pipe", GOTRecipePipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:pouch", GOTRecipePouch.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:leatherHatDye", GOTRecipeLeatherHatDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:featherDye", GOTRecipeFeatherDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:leatherHatFeather", GOTRecipeLeatherHatFeather.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:robesDye", GOTRecipeRobesDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:banners", GOTRecipeBanners.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:poisonDrink", GOTRecipePoisonDrinks.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:treasurePile", GOTRecipeTreasurePile.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("got:turban", GOTRecipeTurbanOrnament.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		modifyStandardRecipes();
		createStandardRecipes();
		createWoodenSlabRecipes();
		CraftingManager.getInstance().getRecipeList().addAll(0, SLAB);
		createArrynRecipes();
		createAsshaiRecipes();
		createBraavosRecipes();
		createCrownlandsRecipes();
		createDorneRecipes();
		createDothrakiRecipes();
		createDragonstoneRecipes();
		createGhiscarRecipes();
		createGiftRecipes();
		createHillmanRecipes();
		createIbbenRecipes();
		createIronbornRecipes();
		createJogosNhaiRecipes();
		createLhazarRecipes();
		createLorathRecipes();
		createLysRecipes();
		createMossovyRecipes();
		createMyrRecipes();
		createNorthRecipes();
		createNorvosRecipes();
		createPentosRecipes();
		createQarthRecipes();
		createQohorRecipes();
		createReachRecipes();
		createRiverlandsRecipes();
		createSmeltingRecipes();
		createSothoryosRecipes();
		createStormlandsRecipes();
		createSummerRecipes();
		createTyroshRecipes();
		createUnsmeltingRecipes();
		createVolantisRecipes();
		createWesterlandsRecipes();
		createWildlingRecipes();
		createYiTiRecipes();
	}

	private static void registerOres() {
		for (Object obj : Block.blockRegistry) {
			Block block = (Block) obj;
			if (block instanceof GOTBlockPlanksBase) {
				OreDictionary.registerOre("plankWood", new ItemStack(block, 1, 32767));
			}
			if (block instanceof GOTBlockSlabBase && block.getMaterial() == Material.wood) {
				OreDictionary.registerOre("slabWood", new ItemStack(block, 1, 32767));
			}
			if (block instanceof GOTBlockStairs && block.getMaterial() == Material.wood) {
				OreDictionary.registerOre("stairWood", new ItemStack(block, 1, 32767));
			}
			if (block instanceof GOTBlockWoodBase) {
				OreDictionary.registerOre("logWood", new ItemStack(block, 1, 32767));
			}
			if (!(block instanceof GOTBlockSaplingBase)) {
				continue;
			}
			OreDictionary.registerOre("treeSapling", new ItemStack(block, 1, 32767));
		}
		for (Object obj : Item.itemRegistry) {
			Item item = (Item) obj;
			if (item == Items.bone) {
				OreDictionary.registerOre("bone", item);
			}
			if (!(item instanceof GOTItemBerry)) {
				continue;
			}
			OreDictionary.registerOre("berry", item);
		}
		OreDictionary.registerOre("clayBall", Items.clay_ball);
		OreDictionary.registerOre("clayBall", GOTItems.redClayBall);
		OreDictionary.registerOre("dyeYellow", new ItemStack(GOTItems.dye, 1, 0));
		OreDictionary.registerOre("dyeWhite", new ItemStack(GOTItems.dye, 1, 1));
		OreDictionary.registerOre("dyeBlue", new ItemStack(GOTItems.dye, 1, 2));
		OreDictionary.registerOre("dyeGreen", new ItemStack(GOTItems.dye, 1, 3));
		OreDictionary.registerOre("dyeBlack", new ItemStack(GOTItems.dye, 1, 4));
		OreDictionary.registerOre("dyeBrown", new ItemStack(GOTItems.dye, 1, 5));
		OreDictionary.registerOre("sand", new ItemStack(GOTBlocks.whiteSand, 1, 32767));
		OreDictionary.registerOre("sandstone", new ItemStack(GOTBlocks.redSandstone, 1, 32767));
		OreDictionary.registerOre("sandstone", new ItemStack(GOTBlocks.whiteSandstone, 1, 32767));
		OreDictionary.registerOre("apple", Items.apple);
		OreDictionary.registerOre("apple", GOTItems.appleGreen);
		OreDictionary.registerOre("horn", GOTItems.rhinoHorn);
		OreDictionary.registerOre("horn", GOTItems.whiteBisonHorn);
		OreDictionary.registerOre("horn", GOTItems.horn);
		OreDictionary.registerOre("arrowTip", Items.flint);
		OreDictionary.registerOre("arrowTip", GOTItems.rhinoHorn);
		OreDictionary.registerOre("arrowTip", GOTItems.whiteBisonHorn);
		OreDictionary.registerOre("arrowTip", GOTItems.horn);
		OreDictionary.registerOre("vine", Blocks.vine);
		OreDictionary.registerOre("vine", GOTBlocks.willowVines);
		OreDictionary.registerOre("vine", GOTBlocks.mirkVines);
	}

	private static void createHillmanRecipes() {
		HILLMEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenHelmet), "XXX", "X X", 'X', "ingotIron"));
		HILLMEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		HILLMEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		HILLMEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenBoots), "X X", "X X", 'X', "ingotIron"));

		HILLMEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.HILLMEN.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		HILLMEN.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableHillTribes), "XX", "XX", 'X', "plankWood"));
	}

	private static void createArrynRecipes() {
		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));

		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynHelmet), "XXX", "X X", 'X', "ingotIron"));
		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynBoots), "X X", "X X", 'X', "ingotIron"));

		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ARRYN.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		ARRYN.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableArryn), "XX", "XX", 'X', "plankWood"));
	}

	private static void createCrownlandsRecipes() {
		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));

		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsBoots), "X X", "X X", 'X', "ingotIron"));

		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ROBERT.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		CROWNLANDS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableCrownlands), "XX", "XX", 'X', "plankWood"));
	}

	private static void createDorneRecipes() {
		DORNE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneHelmet), "XXX", "X X", 'X', "ingotIron"));
		DORNE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		DORNE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		DORNE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneBoots), "X X", "X X", 'X', "ingotIron"));

		DORNE.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.MARTELL.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		DORNE.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableDorne), "XX", "XX", 'X', "plankWood"));
	}

	private static void createDragonstoneRecipes() {
		DRAGONSTONE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneHelmet), "XXX", "X X", 'X', "ingotIron"));
		DRAGONSTONE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		DRAGONSTONE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		DRAGONSTONE.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneBoots), "X X", "X X", 'X', "ingotIron"));

		DRAGONSTONE.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.STANNIS.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		DRAGONSTONE.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableDragonstone), "XX", "XX", 'X', "plankWood"));
	}

	private static void createGiftRecipes() {
		GIFT.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftHelmet), "XXX", "X X", 'X', "ingotIron"));
		GIFT.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		GIFT.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		GIFT.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftBoots), "X X", "X X", 'X', "ingotIron"));

		GIFT.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.NIGHT.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		GIFT.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableGift), "XX", "XX", 'X', "plankWood"));
	}

	private static void createIronbornRecipes() {
		IRONBORN.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornHelmet), "XXX", "X X", 'X', "ingotIron"));
		IRONBORN.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		IRONBORN.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		IRONBORN.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornBoots), "X X", "X X", 'X', "ingotIron"));

		IRONBORN.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.GREYJOY.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		IRONBORN.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableIronborn), "XX", "XX", 'X', "plankWood"));
	}

	private static void createNorthRecipes() {
		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));

		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northHelmet), "XXX", "X X", 'X', "ingotIron"));
		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.northBoots), "X X", "X X", 'X', "ingotIron"));

		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ROBB.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		NORTH.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableNorth), "XX", "XX", 'X', "plankWood"));
	}

	private static void createReachRecipes() {
		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));

		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachHelmet), "XXX", "X X", 'X', "ingotIron"));
		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachBoots), "X X", "X X", 'X', "ingotIron"));

		REACH.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.TYRELL.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		REACH.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableReach), "XX", "XX", 'X', "plankWood"));
	}

	private static void createRiverlandsRecipes() {
		RIVERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		RIVERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		RIVERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		RIVERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsBoots), "X X", "X X", 'X', "ingotIron"));

		RIVERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.TULLY.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		RIVERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableRiverlands), "XX", "XX", 'X', "plankWood"));
	}

	private static void createStormlandsRecipes() {
		STORMLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		STORMLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		STORMLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		STORMLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsBoots), "X X", "X X", 'X', "ingotIron"));

		STORMLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.RENLY.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		STORMLANDS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableStormlands), "XX", "XX", 'X', "plankWood"));
	}

	private static void createWesterlandsRecipes() {
		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));

		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsBoots), "X X", "X X", 'X', "ingotIron"));

		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LANNISTER.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		WESTERLANDS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableWesterlands), "XX", "XX", 'X', "plankWood"));
	}

	private static void createLorathRecipes() {
		LORATH.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathHelmet), "XXX", "X X", 'X', "ingotIron"));
		LORATH.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		LORATH.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		LORATH.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathBoots), "X X", "X X", 'X', "ingotIron"));

		LORATH.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LORATH.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		LORATH.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableLorath), "XX", "XX", 'X', "plankWood"));
	}

	private static void createLysRecipes() {
		LYS.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysHelmet), "XXX", "X X", 'X', "ingotIron"));
		LYS.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		LYS.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		LYS.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysBoots), "X X", "X X", 'X', "ingotIron"));

		LYS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LYS.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		LYS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableLys), "XX", "XX", 'X', "plankWood"));
	}

	private static void createMyrRecipes() {
		MYR.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrHelmet), "XXX", "X X", 'X', "ingotIron"));
		MYR.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		MYR.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		MYR.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrBoots), "X X", "X X", 'X', "ingotIron"));

		MYR.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.MYR.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		MYR.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableMyr), "XX", "XX", 'X', "plankWood"));
	}

	private static void createNorvosRecipes() {
		NORVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosHelmet), "XXX", "X X", 'X', "ingotIron"));
		NORVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		NORVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		NORVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosBoots), "X X", "X X", 'X', "ingotIron"));

		NORVOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableNorvos), "XX", "XX", 'X', "plankWood"));

		NORVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.NORVOS.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
	}

	private static void createPentosRecipes() {
		PENTOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosHelmet), "XXX", "X X", 'X', "ingotIron"));
		PENTOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		PENTOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		PENTOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosBoots), "X X", "X X", 'X', "ingotIron"));

		PENTOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.PENTOS.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		PENTOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tablePentos), "XX", "XX", 'X', "plankWood"));
	}

	private static void createQarthRecipes() {
		QARTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthHelmet), "XXX", "X X", 'X', "ingotIron"));
		QARTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		QARTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		QARTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthBoots), "X X", "X X", 'X', "ingotIron"));

		QARTH.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.QARTH.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		QARTH.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableQarth), "XX", "XX", 'X', "plankWood"));
	}

	private static void createQohorRecipes() {
		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));

		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorHelmet), "XXX", "X X", 'X', "ingotIron"));
		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorBoots), "X X", "X X", 'X', "ingotIron"));

		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.QOHOR.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		QOHOR.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableQohor), "XX", "XX", 'X', "plankWood"));
	}

	private static void createTyroshRecipes() {
		TYROSH.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshHelmet), "XXX", "X X", 'X', "ingotIron"));
		TYROSH.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		TYROSH.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		TYROSH.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshBoots), "X X", "X X", 'X', "ingotIron"));

		TYROSH.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.TYROSH.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		TYROSH.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableTyrosh), "XX", "XX", 'X', "plankWood"));
	}

	private static void createVolantisRecipes() {
		VOLANTIS.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisHelmet), "XXX", "X X", 'X', "ingotIron"));
		VOLANTIS.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		VOLANTIS.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		VOLANTIS.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisBoots), "X X", "X X", 'X', "ingotIron"));

		VOLANTIS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.VOLANTIS.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		VOLANTIS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableVolantis), "XX", "XX", 'X', "plankWood"));
	}

	private static void createBraavosRecipes() {
		BRAAVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosHelmet), "XXX", "X X", 'X', "ingotIron"));
		BRAAVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		BRAAVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		BRAAVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosBoots), "X X", "X X", 'X', "ingotIron"));

		BRAAVOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.BRAAVOS.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		BRAAVOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableBraavos), "XX", "XX", 'X', "plankWood"));
	}

	private static void createGhiscarRecipes() {
		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));

		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarHelmet), "XXX", "X X", 'X', "ingotIron"));
		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarBoots), "X X", "X X", 'X', "ingotIron"));

		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.GHISCAR.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.harpy), "XXX", "X X", 'X', Items.gold_ingot));

		GHISCAR.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableGhiscar), "XX", "XX", 'X', "plankWood"));
	}

	private static void createLhazarRecipes() {
		LHAZAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarHelmet), "XXX", "X X", 'X', "ingotIron"));
		LHAZAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		LHAZAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		LHAZAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarBoots), "X X", "X X", 'X', "ingotIron"));

		LHAZAR.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LHAZAR.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		LHAZAR.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableLhazar), "XX", "XX", 'X', "plankWood"));
	}

	private static void createSummerRecipes() {
		SUMMER.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerHelmet), "XXX", "X X", 'X', "ingotIron"));
		SUMMER.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		SUMMER.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		SUMMER.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerBoots), "X X", "X X", 'X', "ingotIron"));

		SUMMER.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.SUMMER.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		SUMMER.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableSummer), "XX", "XX", 'X', "plankWood"));
	}

	private static void createJogosNhaiRecipes() {
		JOGOS_NHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosNhaiHelmet), "XXX", "X X", 'X', "ingotIron"));
		JOGOS_NHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosNhaiChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		JOGOS_NHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosNhaiLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		JOGOS_NHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosNhaiBoots), "X X", "X X", 'X', "ingotIron"));

		JOGOS_NHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.JOGOS_NHAI.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		JOGOS_NHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableJogosNhai), "XX", "XX", 'X', "plankWood"));
	}

	private static void createDothrakiRecipes() {
		DOTHRAKI.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiHelmet), "XXX", "X X", 'X', GOTBlocks.driedReeds));
		DOTHRAKI.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiChestplate), "X X", "XXX", "XXX", 'X', GOTBlocks.driedReeds));
		DOTHRAKI.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiLeggings), "XXX", "X X", "X X", 'X', GOTBlocks.driedReeds));
		DOTHRAKI.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiBoots), "X X", "X X", 'X', GOTBlocks.driedReeds));

		DOTHRAKI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableDothraki), "XX", "XX", 'X', "plankWood"));
	}

	private static void createMossovyRecipes() {
		MOSSOVY.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovyChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		MOSSOVY.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovyLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		MOSSOVY.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovyBoots), "X X", "X X", 'X', "ingotIron"));

		MOSSOVY.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.MOSSOVY.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		MOSSOVY.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableMossovy), "XX", "XX", 'X', "plankWood"));
	}

	private static void createWildlingRecipes() {
		WILDLING.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.WILDLING.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		WILDLING.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.THENN.getBannerID()), "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "stickWood"));

		WILDLING.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableWildling), "XX", "XX", 'X', "plankWood"));
	}

	private static void createIbbenRecipes() {
		IBBEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		IBBEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		IBBEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenBoots), "X X", "X X", 'X', "ingotIron"));

		IBBEN.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.IBBEN.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		IBBEN.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableIbben), "XX", "XX", 'X', "plankWood"));
	}

	private static void createSothoryosRecipes() {
		SOTHORYOS.add(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick4, 1, 1), new ItemStack(GOTBlocks.brick4, 1, 0), "vine"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 4, 0), "XX", "XX", 'X', Blocks.stone));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 0)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 0)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 0)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 1)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 1)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 1), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 1)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 2)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 2)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 2)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 4, 3), "XX", "XX", 'X', "ingotGold"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 3)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickGold, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 3)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 3)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 4, 4), "XX", "XX", 'X', GOTItems.obsidianShard));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 4)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickObsidian, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 4)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 4), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 4)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 14), "X", "X", "X", 'X', Blocks.stone));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 14)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.sarbacaneTrap), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 0), 'Y', new ItemStack(GOTItems.sarbacane, 1, 0)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.sarbacaneTrapGold), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 3), 'Y', new ItemStack(GOTItems.sarbacane, 1, 0)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.sarbacaneTrapObsidian), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 4), 'Y', new ItemStack(GOTItems.sarbacane, 1, 0)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 11), "X", "X", "X", 'X', "ingotGold"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 12), "X", "X", "X", 'X', GOTItems.obsidianShard));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle6, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 11)));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle6, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 12)));

		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosDoubleTorch, 2), "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood"));

		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosHelmet), "XXX", "X X", 'X', "ingotIron"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosBoots), "X X", "X X", 'X', "ingotIron"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosHelmetChieftain), "X", "Y", 'X', new ItemStack(GOTBlocks.doubleFlower, 1, 3), 'Y', new ItemStack(GOTItems.sothoryosHelmet, 1, 0)));

		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.sarbacane), "XYY", 'X', "stickWood", 'Y', GOTBlocks.reeds));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.dart, 4), "X", "Y", "Z", 'X', GOTItems.obsidianShard, 'Y', "stickWood", 'Z', Items.feather));
		SOTHORYOS.add(new ShapelessOreRecipe(GOTItems.dartPoisoned, GOTItems.dart, GOTItems.bottlePoison));

		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableSothoryos), "XX", "XX", 'X', "plankWood"));
		SOTHORYOS.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.SOTHORYOS.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
	}

	private static void createYiTiRecipes() {
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick5, 1, 12), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick5, 4, 11), "XX", "XX", 'X', new ItemStack(Blocks.stone, 1, 0)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 0), " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(GOTBlocks.brick5, 1, 11)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 2), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 4, 1), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 4)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 8), "X", "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 9), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 4)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 13)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 14)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 15)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 8)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 9)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 14)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickFlowers, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 15)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 13)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickRed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 15), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 10), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 13)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 11), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 14)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 12), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 15)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 13), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		YI_TI.add(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick5, 1, 13), new ItemStack(GOTBlocks.brick5, 1, 11), "vine"));
		YI_TI.add(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick5, 1, 15), new ItemStack(GOTBlocks.brick5, 1, 11), new ItemStack(GOTBlocks.chrysanthemum, 1, 1)));

		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiBoots), "X X", "X X", 'X', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiBombardierBoots), "X X", "Y Y", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiSamuraiBoots), "Y Y", "X X", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiBombardierChestplate), "X X", "YXY", "XYX", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiSamuraiChestplate), "X X", "XYX", "YXY", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiHelmet), "XXX", "X X", 'X', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiBombardierHelmet), "XYX", "Y Y", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiSamuraiHelmet), "YYY", "X X", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiHelmetCaptain), "XYX", 'X', GOTItems.whiteBisonHorn, 'Y', new ItemStack(GOTItems.yiTiSamuraiHelmet, 1, 0)));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiBombardierLeggings), "XXX", "Y Y", "X X", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));
		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.yiTiSamuraiLeggings), "XXX", "X X", "Y Y", 'X', GOTItems.alloySteelIngot, 'Y', "ingotIron"));

		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.YI_TI.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		YI_TI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableYiTi), "XX", "XX", 'X', "plankWood"));
	}

	private static void createAsshaiRecipes() {
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick1, 4, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 7), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsBasaltBrickAsshai, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 1), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 7)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsBasaltBrickAsshaiCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 7)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 9), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 7)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.chandelier, 2, 6), " X ", "YZY", 'X', "stickWood", 'Y', GOTItems.fuse, 'Z', "ingotIron"));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 7)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick2, 1, 10), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.asshaiTorch), "X", "Y", 'X', Items.coal, 'Y', "stickWood"));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.asshaiBars, 16), "XXX", "XXX", 'X', "ingotIron"));

		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiHelmet), "XXX", "X X", 'X', "ingotIron"));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiBoots), "X X", "X X", 'X', "ingotIron"));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiShadowbinderStaff), "  X", " Y ", "Y  ", 'X', GOTItems.ruby, 'Y', "stickWood"));
		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiMask), "XXX", "X X", 'X', "plankWood"));

		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ASSHAI.getBannerID()), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));

		ASSHAI.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableAsshai), "XX", "XX", 'X', "plankWood"));
	}

	private static void createWoodenSlabRecipes() {
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6)));
		SLAB.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.rottenSlabSingle, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0)));
	}

	private static void createUnsmeltingRecipes() {
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiArchmagStaff), "  X", " Y ", "Y  ", 'X', GOTItems.sapphire, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.blacksmithHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldenCompanyHelmet), "XXX", "X X", 'X', "ingotIron"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldenCompanyChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldenCompanyLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldenCompanyBoots), "X X", "X X", 'X', "ingotIron"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.targaryenHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.targaryenChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.targaryenLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.targaryenBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.petyrBaelishDagger), "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.gregorCleganeSword), "XX ", "XX ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.ice), "XX ", "XX ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.ardrianCeltigarAxe), "XXX", "XYX", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.robertBaratheonHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.darkstar), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.dawn), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.bericDondarrionSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.needle), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.nightKingSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.sandorCleganeSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.sunspear), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.katana), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackArakh), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackfyre), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.brightroar), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.darkSister), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.heartsbane), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.justMaid), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.ladyForlorn), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.lamentation), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.lightbringer), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.longclaw), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.nightfall), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.oathkeeper), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.orphanMaker), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.redRain), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.truth), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.vigilance), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		UNSMELT.add(new ShapedOreRecipe(new ItemStack(GOTItems.widowWail), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
	}

	private static void addShapedRecipe(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(result, recipe));
	}

	private static void addShapelessRecipe(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(result, recipe));
	}

	private static void createStandardRecipes() {
		GOTBlockTreasurePile.generateTreasureRecipes(GOTBlocks.treasureCopper, GOTItems.copperIngot);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTBlocks.treasureGold, Items.gold_ingot);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTBlocks.treasureSilver, GOTItems.silverIngot);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTBlocks.treasureValyrian, GOTItems.valyrianIngot);

		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugAppleJuice), new Object[]{"apple", "apple"});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugBlackberryJuice), new Object[]{GOTItems.blackberry, GOTItems.blackberry, GOTItems.blackberry});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugBlueberryJuice), new Object[]{GOTItems.blueberry, GOTItems.blueberry, GOTItems.blueberry});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugChocolate), GOTItems.mugMilk, new Object[]{Items.sugar, new ItemStack(Items.dye, 1, 3)});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugCranberryJuice), new Object[]{GOTItems.cranberry, GOTItems.cranberry, GOTItems.cranberry});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugElderberryJuice), new Object[]{GOTItems.elderberry, GOTItems.elderberry, GOTItems.elderberry});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugLemonade), GOTItems.mugWater, new Object[]{GOTItems.lemon, Items.sugar});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugMangoJuice), new Object[]{GOTItems.mango, GOTItems.mango});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugOrangeJuice), new Object[]{GOTItems.orange, GOTItems.orange});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugPomegranateJuice), new Object[]{GOTItems.pomegranate, GOTItems.pomegranate});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugRaspberryJuice), new Object[]{GOTItems.raspberry, GOTItems.raspberry, GOTItems.raspberry});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugRedGrapeJuice), new Object[]{GOTItems.grapeRed, GOTItems.grapeRed, GOTItems.grapeRed});
		GOTRecipeVessels.addRecipes(new ItemStack(GOTItems.mugWhiteGrapeJuice), new Object[]{GOTItems.grapeWhite, GOTItems.grapeWhite, GOTItems.grapeWhite});

		GameRegistry.addRecipe(new GOTRecipeBanners());
		GameRegistry.addRecipe(new GOTRecipeFeatherDye());
		GameRegistry.addRecipe(new GOTRecipeLeatherHatDye());
		GameRegistry.addRecipe(new GOTRecipeLeatherHatFeather());
		GameRegistry.addRecipe(new GOTRecipePipe());
		GameRegistry.addRecipe(new GOTRecipePoisonDrinks());
		GameRegistry.addRecipe(new GOTRecipePouch());
		GameRegistry.addRecipe(new GOTRecipeRobesDye(GOTMaterial.ROBES));
		GameRegistry.addRecipe(new GOTRecipeTurbanOrnament());

		addShapedRecipe(new ItemStack(Blocks.dirt, 4, 1), "XY", "YX", 'X', new ItemStack(Blocks.dirt, 1, 0), 'Y', Blocks.gravel);
		addShapedRecipe(new ItemStack(Blocks.fence_gate, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 0));
		addShapedRecipe(new ItemStack(Blocks.obsidian, 1), "XXX", "XXX", "XXX", 'X', GOTItems.obsidianShard);
		addShapedRecipe(new ItemStack(Blocks.packed_ice), "XX", "XX", 'X', Blocks.ice);
		addShapedRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		addShapedRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStoneV, 1, 0));
		addShapedRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		addShapedRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "XX", "XX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.alloyForge), "XXX", "X X", "XXX", 'X', GOTBlocks.scorchedStone);
		addShapedRecipe(new ItemStack(GOTBlocks.barrel), "XXX", "YZY", "XXX", 'X', "plankWood", 'Y', "ingotIron", 'Z', Items.bucket);
		addShapedRecipe(new ItemStack(GOTBlocks.beacon), "XXX", "XXX", "YYY", 'X', "logWood", 'Y', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(GOTBlocks.birdCage, 1, 0), "YYY", "Y Y", "XXX", 'X', GOTItems.copperIngot, 'Y', GOTBlocks.copperBars);
		addShapedRecipe(new ItemStack(GOTBlocks.birdCage, 1, 1), "YYY", "Y Y", "XXX", 'X', GOTItems.bronzeIngot, 'Y', GOTBlocks.bronzeBars);
		addShapedRecipe(new ItemStack(GOTBlocks.birdCage, 1, 2), "YYY", "Y Y", "XXX", 'X', "ingotIron", 'Y', Blocks.iron_bars);
		addShapedRecipe(new ItemStack(GOTBlocks.birdCage, 1, 3), "YYY", "Y Y", "XXX", 'X', GOTItems.silverIngot, 'Y', GOTBlocks.silverBars);
		addShapedRecipe(new ItemStack(GOTBlocks.birdCage, 1, 4), "YYY", "Y Y", "XXX", 'X', "ingotGold", 'Y', GOTBlocks.goldBars);
		addShapedRecipe(new ItemStack(GOTBlocks.birdCage, 1, 5), "YYY", "Y Y", "XXX", 'X', GOTItems.valyrianIngot, 'Y', GOTBlocks.valyrianBars);
		addShapedRecipe(new ItemStack(GOTBlocks.birdCageWood, 1, 0), "YYY", "Y Y", "XXX", 'X', "plankWood", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 0), "XXX", "XXX", "XXX", 'X', GOTItems.topaz);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 1), "XXX", "XXX", "XXX", 'X', GOTItems.amethyst);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 2), "XXX", "XXX", "XXX", 'X', GOTItems.sapphire);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 3), "XXX", "XXX", "XXX", 'X', GOTItems.ruby);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 4), "XXX", "XXX", "XXX", 'X', GOTItems.amber);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 5), "XXX", "XXX", "XXX", 'X', GOTItems.diamond);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 6), "XXX", "XXX", "XXX", 'X', GOTItems.pearl);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 7), "XXX", "XXX", "XXX", 'X', GOTItems.opal);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 8), "XX", "XX", 'X', GOTItems.coral);
		addShapedRecipe(new ItemStack(GOTBlocks.blockGem, 1, 9), "XXX", "XXX", "XXX", 'X', GOTItems.emerald);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 0), "XXX", "XXX", "XXX", 'X', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 1), "XXX", "XXX", "XXX", 'X', GOTItems.tinIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 13), "XXX", "XXX", "XXX", 'X', GOTItems.sulfur);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 14), "XXX", "XXX", "XXX", 'X', GOTItems.saltpeter);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 2), "XXX", "XXX", "XXX", 'X', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 3), "XXX", "XXX", "XXX", 'X', GOTItems.silverIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 4), "XXX", "XXX", "XXX", 'X', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal2, 1, 3), "XXX", "XXX", "XXX", 'X', GOTItems.salt);
		addShapedRecipe(new ItemStack(GOTBlocks.blockMetal2, 1, 4), "XXX", "XXX", "XXX", 'X', GOTItems.alloySteelIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.bomb, 4), "XYX", "YXY", "XYX", 'X', Items.gunpowder, 'Y', "ingotIron");
		addShapedRecipe(new ItemStack(GOTBlocks.boneBlock, 1, 0), "XX", "XX", 'X', "bone");
		addShapedRecipe(new ItemStack(GOTBlocks.brick1, 1, 5), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.brick1, 4, 1), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.brick1, 4, 14), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.brick1, 4, 15), "XX", "XX", 'X', new ItemStack(Blocks.sandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.brick1, 4, 4), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.brick2, 4, 11), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.brick2, 4, 2), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.brick3, 1, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.brick3, 1, 1), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.brick3, 1, 15), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.brick3, 1, 8), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.brick3, 4, 13), "XX", "XX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.brick4, 1, 6), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.brick4, 1, 7), " X ", "XYX", " X ", 'X', "gemLapis", 'Y', new ItemStack(GOTBlocks.brick1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.brick4, 4, 15), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.brick5, 1, 3), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.brick5, 4, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.mud, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.brick6, 1, 3), "XXX", "XYX", "XXX", 'X', Items.iron_sword, 'Y', Items.lava_bucket);
		addShapedRecipe(new ItemStack(GOTBlocks.brick6, 1, 4), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.brick6, 1, 5), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.brickIce, 1), "XX", "XX", 'X', new ItemStack(Blocks.packed_ice, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.bronzeBars, 16), "XXX", "XXX", 'X', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.copperBars, 16), "XXX", "XXX", 'X', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.butterflyJar), "X", "Y", 'X', "plankWood", 'Y', Blocks.glass);
		addShapedRecipe(new ItemStack(GOTBlocks.chain, 8), "X", "X", "X", 'X', "ingotIron");
		addShapedRecipe(new ItemStack(GOTBlocks.chandelier, 2, 0), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.chandelier, 2, 1), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.chandelier, 2, 2), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotIron");
		addShapedRecipe(new ItemStack(GOTBlocks.chandelier, 2, 3), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', GOTItems.silverIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.chandelier, 2, 4), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotGold");
		addShapedRecipe(new ItemStack(GOTBlocks.chandelier, 2, 5), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.chestBasket), "XXX", "XYX", "XXX", 'X', GOTBlocks.driedReeds, 'Y', Blocks.chest);
		addShapedRecipe(new ItemStack(GOTBlocks.chestSandstone), "XXX", "XYX", "XXX", 'X', Blocks.sandstone, 'Y', Blocks.chest);
		addShapedRecipe(new ItemStack(GOTBlocks.chestStone), "XXX", "XYX", "XXX", 'X', Blocks.cobblestone, 'Y', Blocks.chest);
		addShapedRecipe(new ItemStack(GOTBlocks.clayTile, 4, 0), "XX", "XX", 'X', new ItemStack(Blocks.hardened_clay, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.cobblebrick, 4, 0), "XX", "XX", 'X', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(GOTBlocks.commandTable), "XXX", "YYY", "ZZZ", 'X', Items.paper, 'Y', "plankWood", 'Z', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.daub, 4), "XYX", "YXY", "XYX", 'X', "stickWood", 'Y', Blocks.dirt);
		addShapedRecipe(new ItemStack(GOTBlocks.daub, 4), "XYX", "YXY", "XYX", 'X', GOTBlocks.driedReeds, 'Y', Blocks.dirt);
		addShapedRecipe(new ItemStack(GOTBlocks.dirtPath, 2, 0), "XX", 'X', Blocks.dirt);
		addShapedRecipe(new ItemStack(GOTBlocks.dirtPath, 2, 1), "XX", 'X', GOTBlocks.mud);
		addShapedRecipe(new ItemStack(GOTBlocks.dirtPath, 3, 2), "XYZ", 'X', Blocks.dirt, 'Y', Blocks.gravel, 'Z', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(GOTBlocks.doorAcacia), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.doorAlmond), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.doorApple), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.doorAramant), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.doorAspen), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.doorBanana), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.doorBaobab), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.doorBeech), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.doorBirch), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.doorCatalpa), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.doorCedar), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.doorCharred), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.doorCherry), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.doorChestnut), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.doorCypress), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.doorDarkOak), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.doorDatePalm), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.doorDragon), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.doorFir), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.doorFotinia), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.doorGreenOak), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.doorHolly), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.doorIbbinia), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.doorJungle), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.doorKanuka), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.doorLarch), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.doorLemon), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.doorLime), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.doorMahogany), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.doorMango), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.doorMangrove), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.doorMaple), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.doorOlive), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.doorOrange), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.doorPalm), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.doorPear), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.doorPine), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.doorPlum), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.doorPomegranate), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.doorRedwood), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.doorRotten), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.doorSpruce), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.doorUlthos), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.doorWeirwood), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.doorWillow), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.fence, 3, 1), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1), 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateAcacia, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateAlmond, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateApple, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateAramant, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateAspen, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateBanana, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateBaobab, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateBeech, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateBirch, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateCatalpa, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateCedar, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateCharred, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateCherry, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateChestnut, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateCypress, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateDarkOak, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateDatePalm, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateDragon, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateFir, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateFotinia, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateGreenOak, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateHolly, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateIbbinia, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateJungle, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateKanuka, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateLarch, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateLemon, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateLime, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateMahogany, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateMango, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateMangrove, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateMaple, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateOlive, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateOrange, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGatePalm, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGatePear, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGatePine, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGatePlum, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGatePomegranate, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateRedwood, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateRotten, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planksRotten, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateSpruce, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateUlthos, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateWeirwood, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceGateWillow, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.fenceRotten, 3, 0), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0), 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTBlocks.gateBronzeBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.gateCopperBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.gateGoldBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "ingotGold");
		addShapedRecipe(new ItemStack(GOTBlocks.gateIronBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "ingotIron");
		addShapedRecipe(new ItemStack(GOTBlocks.gateSilverBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', GOTItems.silverIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.gateValyrianBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.gateWooden, 4), "ZYZ", "YXY", "ZYZ", 'X', GOTItems.gateGear, 'Y', "plankWood", 'Z', "ingotIron");
		addShapedRecipe(new ItemStack(GOTBlocks.gateWoodenBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTBlocks.glass, 4), "XX", "XX", 'X', Blocks.glass);
		addShapedRecipe(new ItemStack(GOTBlocks.glassPane, 16), "XXX", "XXX", 'X', GOTBlocks.glass);
		addShapedRecipe(new ItemStack(GOTBlocks.goldBars, 16), "XXX", "XXX", 'X', "ingotGold");
		addShapedRecipe(new ItemStack(GOTBlocks.grapevine), "X", "X", "X", 'X', "stickWood");
		addShapedRecipe(new ItemStack(GOTBlocks.hearth, 3), "XXX", "YYY", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', Items.brick);
		addShapedRecipe(new ItemStack(GOTBlocks.ironBank), "XXX", "XYX", "XXX", 'X', Blocks.cobblestone, 'Y', GOTItems.coin);
		addShapedRecipe(new ItemStack(GOTBlocks.kebabBlock, 1, 0), "XXX", "XXX", "XXX", 'X', GOTItems.kebab);
		addShapedRecipe(new ItemStack(GOTBlocks.kebabStand), " X ", " Y ", "ZZZ", 'X', "plankWood", 'Y', "stickWood", 'Z', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(GOTBlocks.kebabStandSand), " X ", " Y ", "ZZZ", 'X', "plankWood", 'Y', "stickWood", 'Z', Blocks.sandstone);
		addShapedRecipe(new ItemStack(GOTBlocks.marzipanBlock), "XXX", 'X', GOTItems.marzipan);
		addShapedRecipe(new ItemStack(GOTBlocks.millstone), "XYX", "XZX", "XXX", 'X', Blocks.cobblestone, 'Y', "ingotIron", 'Z', "stickWood");
		addShapedRecipe(new ItemStack(GOTBlocks.millstone), "XYX", "XZX", "XXX", 'X', Blocks.cobblestone, 'Y', GOTItems.bronzeIngot, 'Z', "stickWood");
		addShapedRecipe(new ItemStack(GOTBlocks.mud, 4, 1), "XY", "YX", 'X', new ItemStack(GOTBlocks.mud, 1, 0), 'Y', Blocks.gravel);
		addShapedRecipe(new ItemStack(GOTBlocks.oreGlowstone), "XXX", "XYX", "XXX", 'X', Items.glowstone_dust, 'Y', new ItemStack(Blocks.stone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.oven), "XXX", "X X", "XXX", 'X', Blocks.brick_block);
		addShapedRecipe(new ItemStack(GOTBlocks.pillar1, 3, 15), "X", "X", "X", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar1, 3, 3), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar1, 3, 4), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar1, 3, 5), "X", "X", "X", 'X', new ItemStack(Blocks.sandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar1, 3, 6), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar1, 3, 8), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar1, 3, 9), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar2, 3, 0), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar2, 3, 1), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar2, 3, 2), "X", "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.pillar2, 3, 3), "X", "X", "X", 'X', Blocks.brick_block);
		addShapedRecipe(new ItemStack(GOTBlocks.pressurePlateAndesite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.pressurePlateBasalt), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.pressurePlateChalk), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.pressurePlateDiorite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.pressurePlateGranite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.pressurePlateLabradorite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.pressurePlateRhyolite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.redClay), "XX", "XX", 'X', GOTItems.redClayBall);
		addShapedRecipe(new ItemStack(GOTBlocks.redSandstone, 1, 0), "XX", "XX", 'X', new ItemStack(Blocks.sand, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.reedBars, 16), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.rope, 3), "X", "X", "X", 'X', Items.string);
		addShapedRecipe(new ItemStack(GOTBlocks.scorchedSlabSingle, 6, 0), "XXX", 'X', GOTBlocks.scorchedStone);
		addShapedRecipe(new ItemStack(GOTBlocks.scorchedWall, 6), "XXX", "XXX", 'X', GOTBlocks.scorchedStone);
		addShapedRecipe(new ItemStack(GOTBlocks.silverBars, 16), "XXX", "XXX", 'X', GOTItems.silverIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.slabBoneSingle, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.boneBlock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabClayTileSingle, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle10, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.whiteSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle10, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle4, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle4, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 0), "XXX", 'X', new ItemStack(Blocks.dirt, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.dirtPath, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.mud, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.asshaiDirt, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.dirtPath, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.dirtPath, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleGravel, 6, 0), "XXX", 'X', new ItemStack(Blocks.gravel, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleGravel, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.basaltGravel, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleGravel, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.obsidianGravel, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleSand, 6, 0), "XXX", 'X', new ItemStack(Blocks.sand, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleSand, 6, 1), "XXX", 'X', new ItemStack(Blocks.sand, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleSand, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.whiteSand, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleThatch, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleThatch, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 0), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 1), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 4), "XXX", 'X', Blocks.mossy_cobblestone);
		addShapedRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 5), "XXX", 'X', new ItemStack(Blocks.stone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 0), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 1), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 2), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 3), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 4), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 5), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 6), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.smoothStoneV, 2, 0), "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsAlmond, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsAndesite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsAndesiteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsAndesiteBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsAndesiteBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsApple, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsAramant, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsAspen, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBanana, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBaobab, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBasalt, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBasaltBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBeech, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.boneBlock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsCatalpa, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsCedar, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsChalk, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsChalkBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsCharred, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsCherry, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsChestnut, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTile, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedBlack, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedBlue, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedBrown, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedCyan, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedGray, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedGreen, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedLightBlue, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedLightGray, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedLime, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedMagenta, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedOrange, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedPink, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedPurple, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedRed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedWhite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedYellow, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsCobblestoneMossy, 4), "X  ", "XX ", "XXX", 'X', Blocks.mossy_cobblestone);
		addShapedRecipe(new ItemStack(GOTBlocks.stairsCypress, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsDatePalm, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsDiorite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsDioriteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsDragon, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsFir, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsFotinia, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsGranite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsGraniteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsGreenOak, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsHolly, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsIbbinia, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsKanuka, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsLabradorite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsLabradoriteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsLabradoriteBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsLabradoriteBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsLarch, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsLemon, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsLime, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsMahogany, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsMango, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsMangrove, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsMaple, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsMudBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsOlive, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsOrange, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsPalm, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsPear, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsPine, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsPlum, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsPomegranate, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsRedSandstone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsRedwood, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsReed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsRhyolite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsRhyoliteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsRotten, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrickRed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrickRedCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsScorchedStone, 4), "X  ", "XX ", "XXX", 'X', GOTBlocks.scorchedStone);
		addShapedRecipe(new ItemStack(GOTBlocks.stairsStone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsStoneBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsStoneBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsThatch, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsUlthos, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsWeirwood, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsWhiteSandstone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.whiteSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.stairsWillow, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.termiteMound, 1, 1), " X ", "XYX", " X ", 'X', GOTItems.termite, 'Y', Blocks.stone);
		addShapedRecipe(new ItemStack(GOTBlocks.thatch, 4, 1), "XX", "XX", 'X', GOTBlocks.driedReeds);
		addShapedRecipe(new ItemStack(GOTBlocks.thatch, 6, 0), "XYX", "YXY", "XYX", 'X', Items.wheat, 'Y', Blocks.dirt);
		addShapedRecipe(new ItemStack(GOTBlocks.thatchFloor, 3), "XX", 'X', new ItemStack(GOTBlocks.thatch, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorAcacia, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorAlmond, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorApple, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorAramant, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorAspen, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorBanana, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorBaobab, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorBeech, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorBirch, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorCatalpa, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorCedar, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorCharred, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorCherry, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorChestnut, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorCypress, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorDarkOak, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorDatePalm, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorDragon, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorFir, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorFotinia, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorGreenOak, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorHolly, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorIbbinia, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorJungle, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorKanuka, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorLarch, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorLemon, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorLime, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorMahogany, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorMango, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorMangrove, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorMaple, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorOlive, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorOrange, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorPalm, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorPear, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorPine, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorPlum, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorPomegranate, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorRedwood, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorRotten, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorSpruce, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorUlthos, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorWeirwood, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.trapdoorWillow, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9));
		addShapedRecipe(new ItemStack(GOTBlocks.unsmeltery), "X X", "YXY", "ZZZ", 'X', "ingotIron", 'Y', "stickWood", 'Z', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(GOTBlocks.valyrianBars, 16), "XXX", "XXX", 'X', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTBlocks.wallBone, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.boneBlock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallClayTile, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 13), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 14), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 15), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 4), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 3));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 6), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 8), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone2, 6, 10), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone2, 6, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone2, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 14), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.whiteSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 11));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 4), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 14));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 6), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 7), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 15));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 8), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 6), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 7), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 7));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 8), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 0), "XXX", "XXX", 'X', new ItemStack(Blocks.stone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 1), "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 3), "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 4), "XXX", "XXX", 'X', new ItemStack(Blocks.sandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 6), "XXX", "XXX", 'X', new ItemStack(Blocks.brick_block, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 7), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 8), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.wasteBlock, 4), "XY", "YZ", 'X', Items.rotten_flesh, 'Y', Blocks.dirt, 'Z', "bone");
		addShapedRecipe(new ItemStack(GOTBlocks.weaponRack), "X X", "YYY", 'X', "stickWood", 'Y', "plankWood");
		addShapedRecipe(new ItemStack(GOTBlocks.whiteSandstone, 1, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.whiteSand, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.wildFireJar), "XYX", "YZY", "XYX", 'X', "ingotIron", 'Y', Items.gunpowder, 'Z', GOTItems.fuse);
		addShapedRecipe(new ItemStack(GOTBlocks.woodBeam1, 3, 1), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood1, 1, 1));
		addShapedRecipe(new ItemStack(GOTBlocks.woodBeamRotten, 3, 0), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rottenLog, 1, 0));
		addShapedRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1));

		addShapedRecipe(new ItemStack(GOTItems.aleHorn), "X", "Y", 'X', "horn", 'Y', GOTItems.tinIngot);
		addShapedRecipe(new ItemStack(GOTItems.aleHornGold), "X", "Y", 'X', "horn", 'Y', "ingotGold");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelAxe), "XX", "XY", " Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelChainmailBoots), "Y Y", "X X", 'X', GOTItems.alloySteelIngot, 'Y', GOTItems.alloySteelNugget);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelChainmailChestplate), "X X", "YYY", "XXX", 'X', GOTItems.alloySteelIngot, 'Y', GOTItems.alloySteelNugget);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelChainmailHelmet), "XXX", "Y Y", 'X', GOTItems.alloySteelIngot, 'Y', GOTItems.alloySteelNugget);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelChainmailLeggings), "XXX", "Y Y", "X X", 'X', GOTItems.alloySteelIngot, 'Y', GOTItems.alloySteelNugget);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelCrossbow), "XXY", "ZYX", "YZX", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood", 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelDagger), "X", "Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelHammer), "XYX", "XYX", " Y ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelHoe), "XX", " Y", " Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelIngot), "XXX", "XXX", "XXX", 'X', GOTItems.alloySteelNugget);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot);
		addShapedRecipe(new ItemStack(GOTItems.alloySteelMattock), "XXX", "XY ", " Y ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelPickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelPike), "  X", " YX", "Y  ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelShovel), "X", "Y", "Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelSpear), "  X", " Y ", "Y  ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelSword), "X", "X", "Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.alloySteelThrowingAxe), " X ", " YX", "Y  ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.anonymousMask), "XXX", "X X", 'X', Items.paper);
		addShapedRecipe(new ItemStack(GOTItems.appleCrumble), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', "apple", 'C', Items.sugar, 'D', Items.wheat);
		addShapedRecipe(new ItemStack(GOTItems.armorStand), " X ", " X ", "YYY", 'X', "stickWood", 'Y', Blocks.stone);
		addShapedRecipe(new ItemStack(GOTItems.arrowFire, 4), " X ", "XYX", " X ", 'X', Items.arrow, 'Y', GOTItems.sulfur);
		addShapedRecipe(new ItemStack(GOTItems.arrowPoisoned, 4), " X ", "XYX", " X ", 'X', Items.arrow, 'Y', GOTItems.bottlePoison);
		addShapedRecipe(new ItemStack(GOTItems.bananaBread), "XYX", 'X', Items.wheat, 'Y', GOTItems.banana);
		addShapedRecipe(new ItemStack(GOTItems.bananaCake), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', GOTItems.banana, 'C', Items.egg, 'D', Items.wheat);
		addShapedRecipe(new ItemStack(GOTItems.berryPie), "AAA", "BBB", "CCC", 'A', Items.milk_bucket, 'B', "berry", 'C', Items.wheat);
		addShapedRecipe(new ItemStack(GOTItems.boneBoots), "X X", "X X", 'X', "bone");
		addShapedRecipe(new ItemStack(GOTItems.boneChestplate), "X X", "XXX", "XXX", 'X', "bone");
		addShapedRecipe(new ItemStack(GOTItems.boneHelmet), "XXX", "X X", 'X', "bone");
		addShapedRecipe(new ItemStack(GOTItems.boneLeggings), "XXX", "X X", "X X", 'X', "bone");
		addShapedRecipe(new ItemStack(GOTItems.brandingIron), "  X", " Y ", "X  ", 'X', "ingotIron", 'Y', GOTItems.gemsbokHide);
		addShapedRecipe(new ItemStack(GOTItems.brandingIron), "  X", " Y ", "X  ", 'X', "ingotIron", 'Y', Items.leather);
		addShapedRecipe(new ItemStack(GOTItems.bronzeAxe), "XX", "XY", " Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeBoots), "X X", "X X", 'X', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTItems.bronzeChainmailBoots), "Y Y", "X X", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget);
		addShapedRecipe(new ItemStack(GOTItems.bronzeChainmailChestplate), "X X", "YYY", "XXX", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget);
		addShapedRecipe(new ItemStack(GOTItems.bronzeChainmailHelmet), "XXX", "Y Y", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget);
		addShapedRecipe(new ItemStack(GOTItems.bronzeChainmailLeggings), "XXX", "Y Y", "X X", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget);
		addShapedRecipe(new ItemStack(GOTItems.bronzeChestplate), "X X", "XXX", "XXX", 'X', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTItems.bronzeCrossbow), "XXY", "ZYX", "YZX", 'X', GOTItems.bronzeIngot, 'Y', "stickWood", 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.bronzeDagger), "X", "Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeHammer), "XYX", "XYX", " Y ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeHelmet), "XXX", "X X", 'X', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTItems.bronzeHoe), "XX", " Y", " Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeIngot), "XXX", "XXX", "XXX", 'X', GOTItems.bronzeNugget);
		addShapedRecipe(new ItemStack(GOTItems.bronzeLeggings), "XXX", "X X", "X X", 'X', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTItems.bronzeMattock), "XXX", "XY ", " Y ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzePickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzePike), "  X", " YX", "Y  ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeRing), "XXX", "X X", "XXX", 'X', GOTItems.bronzeNugget);
		addShapedRecipe(new ItemStack(GOTItems.bronzeShovel), "X", "Y", "Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeSpear), "  X", " Y ", "Y  ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeSword), "X", "X", "Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.bronzeThrowingAxe), " X ", " YX", "Y  ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.cargocart), "X X", "X X", "YZY", 'X', Blocks.fence, 'Y', GOTItems.wheel, 'Z', Blocks.planks);
		addShapedRecipe(new ItemStack(GOTItems.cherryPie), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', GOTItems.cherry, 'C', Items.sugar, 'D', Items.wheat);
		addShapedRecipe(new ItemStack(GOTItems.chisel), "XY", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.clayMug, 2), "X", "Y", "X", 'X', GOTItems.tinIngot, 'Y', "clayBall");
		addShapedRecipe(new ItemStack(GOTItems.clayPlate, 2), "XX", 'X', "clayBall");
		addShapedRecipe(new ItemStack(GOTItems.club), "X", "X", "X", 'X', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.commandHorn), "XYX", 'X', GOTItems.bronzeIngot, 'Y', "horn");
		addShapedRecipe(new ItemStack(GOTItems.commandSword), "X", "Y", "Z", 'X', "ingotIron", 'Y', GOTItems.bronzeIngot, 'Z', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperAxe), "XX", "XY", " Y", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperBoots), "X X", "X X", 'X', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTItems.copperChainmailBoots), "Y Y", "X X", 'X', GOTItems.copperIngot, 'Y', GOTItems.copperNugget);
		addShapedRecipe(new ItemStack(GOTItems.copperChainmailChestplate), "X X", "YYY", "XXX", 'X', GOTItems.copperIngot, 'Y', GOTItems.copperNugget);
		addShapedRecipe(new ItemStack(GOTItems.copperChainmailHelmet), "XXX", "Y Y", 'X', GOTItems.copperIngot, 'Y', GOTItems.copperNugget);
		addShapedRecipe(new ItemStack(GOTItems.copperChainmailLeggings), "XXX", "Y Y", "X X", 'X', GOTItems.copperIngot, 'Y', GOTItems.copperNugget);
		addShapedRecipe(new ItemStack(GOTItems.copperChestplate), "X X", "XXX", "XXX", 'X', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTItems.copperCrossbow), "XXY", "ZYX", "YZX", 'X', GOTItems.copperIngot, 'Y', "stickWood", 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.copperDagger), "X", "Y", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperHammer), "XYX", "XYX", " Y ", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperHelmet), "XXX", "X X", 'X', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTItems.copperHoe), "XX", " Y", " Y", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperIngot), "XXX", "XXX", "XXX", 'X', GOTItems.copperNugget);
		addShapedRecipe(new ItemStack(GOTItems.copperLeggings), "XXX", "X X", "X X", 'X', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTItems.copperMattock), "XXX", "XY ", " Y ", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperPickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperPike), "  X", " YX", "Y  ", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperRing), "XXX", "X X", "XXX", 'X', GOTItems.copperNugget);
		addShapedRecipe(new ItemStack(GOTItems.copperShovel), "X", "Y", "Y", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperSpear), "  X", " Y ", "Y  ", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperSword), "X", "X", "Y", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.copperThrowingAxe), " X ", " YX", "Y  ", 'X', GOTItems.copperIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.cornBread), "XXX", 'X', GOTItems.corn);
		addShapedRecipe(new ItemStack(GOTItems.crossbowBolt, 4), "X", "Y", "Z", 'X', "ingotIron", 'Y', "stickWood", 'Z', Items.feather);
		addShapedRecipe(new ItemStack(GOTItems.crossbowBolt, 4), "X", "Y", "Z", 'X', GOTItems.bronzeIngot, 'Y', "stickWood", 'Z', Items.feather);
		addShapedRecipe(new ItemStack(GOTItems.crossbowBoltPoisoned, 4), " X ", "XYX", " X ", 'X', GOTItems.crossbowBolt, 'Y', GOTItems.bottlePoison);
		addShapedRecipe(new ItemStack(GOTItems.diamondHorseArmor), "X  ", "XYX", "XXX", 'X', Items.diamond, 'Y', Items.leather);
		addShapedRecipe(new ItemStack(GOTItems.diamondMattock), "XXX", "XY ", " Y ", 'X', Items.diamond, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.firePot, 4), "Z", "Y", "X", 'X', "ingotIron", 'Y', Items.gunpowder, 'Z', GOTItems.fuse);
		addShapedRecipe(new ItemStack(GOTItems.furBed), "XXX", "YYY", 'X', GOTItems.fur, 'Y', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.lionFurBed), "XXX", "YYY", 'X', GOTItems.fur, 'Y', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.furBoots), "X X", "X X", 'X', GOTItems.fur);
		addShapedRecipe(new ItemStack(GOTItems.furChestplate), "X X", "XXX", "XXX", 'X', GOTItems.fur);
		addShapedRecipe(new ItemStack(GOTItems.furHelmet), "XXX", "X X", 'X', GOTItems.fur);
		addShapedRecipe(new ItemStack(GOTItems.furLeggings), "XXX", "X X", "X X", 'X', GOTItems.fur);
		addShapedRecipe(new ItemStack(GOTItems.fuse, 2), "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.gateGear, 4), " X ", "XYX", " X ", 'X', "ingotIron", 'Y', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.gemsbokBoots), "X X", "X X", 'X', GOTItems.gemsbokHide);
		addShapedRecipe(new ItemStack(GOTItems.gemsbokChestplate), "X X", "XXX", "XXX", 'X', GOTItems.gemsbokHide);
		addShapedRecipe(new ItemStack(GOTItems.gemsbokHelmet), "Y Y", "XXX", "X X", 'X', GOTItems.gemsbokHide, 'Y', GOTItems.gemsbokHorn);
		addShapedRecipe(new ItemStack(GOTItems.gemsbokLeggings), "XXX", "X X", "X X", 'X', GOTItems.gemsbokHide);
		addShapedRecipe(new ItemStack(GOTItems.gobletValyrian, 2), "X X", " X ", " X ", 'X', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTItems.gobletBronze, 2), "X X", " X ", " X ", 'X', GOTItems.bronzeIngot);
		addShapedRecipe(new ItemStack(GOTItems.gobletCopper, 2), "X X", " X ", " X ", 'X', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(GOTItems.gobletGold, 2), "X X", " X ", " X ", 'X', "ingotGold");
		addShapedRecipe(new ItemStack(GOTItems.gobletSilver, 2), "X X", " X ", " X ", 'X', GOTItems.silverIngot);
		addShapedRecipe(new ItemStack(GOTItems.gobletWood, 2), "X X", " X ", " X ", 'X', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.goldBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotGold", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.goldChainmailBoots), "Y Y", "X X", 'X', Items.gold_ingot, 'Y', Items.gold_nugget);
		addShapedRecipe(new ItemStack(GOTItems.goldChainmailChestplate), "X X", "YYY", "XXX", 'X', Items.gold_ingot, 'Y', Items.gold_nugget);
		addShapedRecipe(new ItemStack(GOTItems.goldChainmailHelmet), "XXX", "Y Y", 'X', Items.gold_ingot, 'Y', Items.gold_nugget);
		addShapedRecipe(new ItemStack(GOTItems.goldChainmailLeggings), "XXX", "Y Y", "X X", 'X', Items.gold_ingot, 'Y', Items.gold_nugget);
		addShapedRecipe(new ItemStack(GOTItems.goldCrossbow), "XXY", "ZYX", "YZX", 'X', "ingotGold", 'Y', "stickWood", 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.goldDagger), "X", "Y", 'X', "ingotGold", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.goldHammer), "XYX", "XYX", " Y ", 'X', "ingotGold", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.goldHorseArmor), "X  ", "XYX", "XXX", 'X', "ingotGold", 'Y', Items.leather);
		addShapedRecipe(new ItemStack(GOTItems.goldMattock), "XXX", "XY ", " Y ", 'X', "ingotGold", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.goldPike), "  X", " YX", "Y  ", 'X', "ingotGold", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.goldRing), "XXX", "X X", "XXX", 'X', "nuggetGold");
		addShapedRecipe(new ItemStack(GOTItems.goldSpear), "  X", " Y ", "Y  ", 'X', "ingotGold", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.goldThrowingAxe), " X ", " YX", "Y  ", 'X', "ingotGold", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.harpoon), "  X", " Y ", "YZ ", 'X', "ingotIron", 'Y', "stickWood", 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.iceHeavySword), " XX", " X ", "Y  ", 'X', GOTItems.iceShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.iceSpear), "  X", " Y ", "Y  ", 'X', GOTItems.iceShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.iceSword), "X", "X", "Y", 'X', GOTItems.iceShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.ironBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.ironCrossbow), "XXY", "ZYX", "YZX", 'X', "ingotIron", 'Y', "stickWood", 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.ironDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.ironHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.ironHorseArmor), "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather);
		addShapedRecipe(new ItemStack(GOTItems.ironMattock), "XXX", "XY ", " Y ", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.ironPike), "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.ironSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.ironThrowingAxe), " X ", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.leatherHat), " X ", "XXX", 'X', Items.leather);
		addShapedRecipe(new ItemStack(GOTItems.lemonCake), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', GOTItems.lemon, 'C', Items.sugar, 'D', Items.wheat);
		addShapedRecipe(new ItemStack(GOTItems.lionBoots), "X X", "X X", 'X', GOTItems.lionFur);
		addShapedRecipe(new ItemStack(GOTItems.lionChestplate), "X X", "XXX", "XXX", 'X', GOTItems.lionFur);
		addShapedRecipe(new ItemStack(GOTItems.lionHelmet), "XXX", "X X", 'X', GOTItems.lionFur);
		addShapedRecipe(new ItemStack(GOTItems.lionLeggings), "XXX", "X X", "X X", 'X', GOTItems.lionFur);
		addShapedRecipe(new ItemStack(GOTItems.longbow), " XY", "Z Y", " XY", 'X', "stickWood", 'Y', Items.string, 'Z', "ingotIron");
		addShapedRecipe(new ItemStack(GOTItems.mug, 2), "X", "Y", "X", 'X', GOTItems.tinIngot, 'Y', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianAxe), "XX", "XY", " Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianDagger), "X", "Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianHammer), "XYX", "XYX", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianHoe), "XX", " Y", " Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianMattock), "XXX", "XY ", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianPickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianPike), "  X", " YX", "Y  ", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianShovel), "X", "Y", "Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianSpear), "  X", " Y ", "Y  ", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianSword), "X", "X", "Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.obsidianThrowingAxe), " X ", " YX", "Y  ", 'X', GOTItems.obsidianShard, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.oliveBread), "XYX", 'X', Items.wheat, 'Y', GOTItems.olive);
		addShapedRecipe(new ItemStack(GOTItems.pipe), "X  ", " XX", 'X', Items.clay_ball);
		addShapedRecipe(new ItemStack(GOTItems.plowcart), "fff", "php", "wpw", 'f', Blocks.fence, 'h', Items.iron_hoe, 'p', Blocks.planks, 'w', GOTItems.wheel);
		addShapedRecipe(new ItemStack(GOTItems.pouch, 1, 0), "X X", "X X", "XXX", 'X', Items.leather);
		addShapedRecipe(new ItemStack(GOTItems.rhinoArmor), "X  ", "XYX", "XXX", 'X', Items.leather, 'Y', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.rollingPin), "XYX", 'X', "stickWood", 'Y', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.shishKebab, 2), "  X", " X ", "Y  ", 'X', GOTItems.kebab, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.silverIngot), "XXX", "XXX", "XXX", 'X', GOTItems.silverNugget);
		addShapedRecipe(new ItemStack(GOTItems.silverRing), "XXX", "X X", "XXX", 'X', GOTItems.silverNugget);
		addShapedRecipe(new ItemStack(GOTItems.skullCup), "X", "Y", 'X', new ItemStack(Items.skull, 1, 0), 'Y', GOTItems.tinIngot);
		addShapedRecipe(new ItemStack(GOTItems.skullStaff), "X", "Y", "Y", 'X', Items.skull, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.sling), "XYX", "XZX", " X ", 'X', "stickWood", 'Y', Items.leather, 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.stoneBattleaxe), "XXX", "XYX", " Y ", 'X', Blocks.cobblestone, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.stoneDagger), "X", "Y", 'X', Blocks.cobblestone, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.stoneHammer), "XYX", "XYX", " Y ", 'X', Blocks.cobblestone, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.stoneMattock), "XXX", "XY ", " Y ", 'X', "cobblestone", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.stonePike), "  X", " YX", "Y  ", 'X', Blocks.cobblestone, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.stoneSpear), "  X", " Y ", "Y  ", 'X', Blocks.cobblestone, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.stoneThrowingAxe), " X ", " YX", "Y  ", 'X', Blocks.cobblestone, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.strawBed), "XXX", "YYY", 'X', Items.wheat, 'Y', "plankWood");
		addShapedRecipe(new ItemStack(GOTItems.sulfurMatch, 4), "X", "Y", 'X', GOTItems.sulfur, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.trident), " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianAxe), "XX", "XY", " Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianBoots), "X X", "X X", 'X', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTItems.valyrianChainmailBoots), "Y Y", "X X", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget);
		addShapedRecipe(new ItemStack(GOTItems.valyrianChainmailChestplate), "X X", "YYY", "XXX", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget);
		addShapedRecipe(new ItemStack(GOTItems.valyrianChainmailHelmet), "XXX", "Y Y", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget);
		addShapedRecipe(new ItemStack(GOTItems.valyrianChainmailLeggings), "XXX", "Y Y", "X X", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget);
		addShapedRecipe(new ItemStack(GOTItems.valyrianChestplate), "X X", "XXX", "XXX", 'X', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTItems.valyrianChisel), "XY", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianCrossbow), "XXY", "ZYX", "YZX", 'X', GOTItems.valyrianIngot, 'Y', "stickWood", 'Z', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.valyrianDagger), "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianHammer), "XYX", "XYX", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianHelmet), "XXX", "X X", 'X', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTItems.valyrianHoe), "XX", " Y", " Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianIngot), "XXX", "XXX", "XXX", 'X', GOTItems.valyrianNugget);
		addShapedRecipe(new ItemStack(GOTItems.valyrianLeggings), "XXX", "X X", "X X", 'X', GOTItems.valyrianIngot);
		addShapedRecipe(new ItemStack(GOTItems.valyrianMattock), "XXX", "XY ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianPickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianPike), "  X", " YX", "Y  ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianRing), "XXX", "X X", "XXX", 'X', GOTItems.valyrianNugget);
		addShapedRecipe(new ItemStack(GOTItems.valyrianShovel), "X", "Y", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianSpear), "  X", " Y ", "Y  ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianSword), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.valyrianThrowingAxe), " X ", " YX", "Y  ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', GOTItems.fur, 'Y', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', GOTItems.lionFur, 'Y', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', GOTItems.gemsbokHide, 'Y', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', Items.leather, 'Y', Items.string);
		addShapedRecipe(new ItemStack(GOTItems.wheel), "sss", "sps", "sss", 's', "stickWood", 'p', Blocks.planks);
		addShapedRecipe(new ItemStack(GOTItems.whiteWalkersBoots), "X X", "X X", 'X', GOTItems.iceShard);
		addShapedRecipe(new ItemStack(GOTItems.whiteWalkersChestplate), "X X", "XXX", "XXX", 'X', GOTItems.iceShard);
		addShapedRecipe(new ItemStack(GOTItems.whiteWalkersLeggings), "XXX", "X X", "X X", 'X', GOTItems.iceShard);
		addShapedRecipe(new ItemStack(GOTItems.wineGlass, 2), "X X", " X ", " X ", 'X', Blocks.glass);
		addShapedRecipe(new ItemStack(GOTItems.woodBattleaxe), "XXX", "XYX", " Y ", 'X', "plankWood", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.woodDagger), "X", "Y", 'X', "plankWood", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.woodHammer), "XYX", "XYX", " Y ", 'X', "plankWood", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.woodMattock), "XXX", "XY ", " Y ", 'X', "plankWood", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.woodPike), "  X", " YX", "Y  ", 'X', "plankWood", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.woodSpear), "  X", " Y ", "Y  ", 'X', "plankWood", 'Y', "stickWood");
		addShapedRecipe(new ItemStack(GOTItems.woodThrowingAxe), " X ", " YX", "Y  ", 'X', "plankWood", 'Y', "stickWood");

		addShapedRecipe(new ItemStack(Items.arrow, 4), "X", "Y", "Z", 'X', "arrowTip", 'Y', "stickWood", 'Z', Items.feather);
		addShapedRecipe(new ItemStack(Items.brewing_stand, 1), " X ", "YYY", 'X', "stickWood", 'Y', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(Items.chainmail_boots), "Y Y", "X X", 'X', "ingotIron", 'Y', GOTItems.ironNugget);
		addShapedRecipe(new ItemStack(Items.chainmail_chestplate), "X X", "YYY", "XXX", 'X', "ingotIron", 'Y', GOTItems.ironNugget);
		addShapedRecipe(new ItemStack(Items.chainmail_helmet), "XXX", "Y Y", 'X', "ingotIron", 'Y', GOTItems.ironNugget);
		addShapedRecipe(new ItemStack(Items.chainmail_leggings), "XXX", "Y Y", "X X", 'X', "ingotIron", 'Y', GOTItems.ironNugget);
		addShapedRecipe(new ItemStack(Items.clock), " X ", "XYX", " X ", 'X', "ingotGold", 'Y', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(Items.compass), " X ", "XYX", " X ", 'X', "ingotIron", 'Y', GOTItems.copperIngot);
		addShapedRecipe(new ItemStack(Items.iron_ingot), "XXX", "XXX", "XXX", 'X', GOTItems.ironNugget);
		addShapedRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', GOTItems.lionFur);
		addShapedRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', GOTItems.fur);
		addShapedRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', GOTItems.gemsbokHide);
		addShapedRecipe(new ItemStack(Items.paper, 3), "XXX", 'X', GOTBlocks.cornStalk);
		addShapedRecipe(new ItemStack(Items.paper, 3), "XXX", 'X', GOTBlocks.reeds);
		addShapedRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", 'X', GOTItems.fur, 'Y', "ingotIron");
		addShapedRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", 'X', GOTItems.lionFur, 'Y', "ingotIron");
		addShapedRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", 'X', GOTItems.gemsbokHide, 'Y', "ingotIron");
		addShapedRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", 'X', Items.leather, 'Y', "ingotIron");
		addShapedRecipe(new ItemStack(Items.wooden_door), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 0));

		addShapelessRecipe(new ItemStack(Blocks.dirt, 1, 0), new ItemStack(Blocks.dirt, 1, 1), Items.wheat_seeds);
		addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0), "vine");
		addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick, 1, 0), "vine");

		addShapelessRecipe(new ItemStack(GOTBlocks.bomb, 1, 1), new ItemStack(GOTBlocks.bomb, 1, 0), Items.gunpowder, "ingotIron");
		addShapelessRecipe(new ItemStack(GOTBlocks.bomb, 1, 2), new ItemStack(GOTBlocks.bomb, 1, 1), Items.gunpowder, "ingotIron");
		addShapelessRecipe(new ItemStack(GOTBlocks.brick1, 1, 2), new ItemStack(GOTBlocks.brick1, 1, 1), "vine");
		addShapelessRecipe(new ItemStack(GOTBlocks.brick6, 1, 7), new ItemStack(GOTBlocks.brick6, 1, 4), "vine");
		addShapelessRecipe(new ItemStack(GOTBlocks.buttonAndesite), new ItemStack(GOTBlocks.rock, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.buttonBasalt), new ItemStack(GOTBlocks.rock, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.buttonChalk), new ItemStack(GOTBlocks.rock, 1, 5));
		addShapelessRecipe(new ItemStack(GOTBlocks.buttonDiorite), new ItemStack(GOTBlocks.rock, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.buttonGranite), new ItemStack(GOTBlocks.rock, 1, 4));
		addShapelessRecipe(new ItemStack(GOTBlocks.buttonLabradorite), new ItemStack(GOTBlocks.rock, 1, 6));
		addShapelessRecipe(new ItemStack(GOTBlocks.buttonRhyolite), new ItemStack(GOTBlocks.rock, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.mud, 1, 0), new ItemStack(GOTBlocks.mud, 1, 1), Items.wheat_seeds);
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 0), new ItemStack(GOTBlocks.wood1, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 1), new ItemStack(GOTBlocks.wood1, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 10), new ItemStack(GOTBlocks.wood2, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 11), new ItemStack(GOTBlocks.wood2, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 12), new ItemStack(GOTBlocks.wood3, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 13), new ItemStack(GOTBlocks.wood3, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 14), new ItemStack(GOTBlocks.wood3, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 15), new ItemStack(GOTBlocks.wood3, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 2), new ItemStack(GOTBlocks.wood1, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 3), new ItemStack(GOTBlocks.wood1, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 4), new ItemStack(GOTBlocks.fruitWood, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 5), new ItemStack(GOTBlocks.fruitWood, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 6), new ItemStack(GOTBlocks.fruitWood, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 7), new ItemStack(GOTBlocks.fruitWood, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 8), new ItemStack(GOTBlocks.wood2, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 9), new ItemStack(GOTBlocks.wood2, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 0), new ItemStack(GOTBlocks.wood4, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 1), new ItemStack(GOTBlocks.wood4, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 10), new ItemStack(GOTBlocks.wood6, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 11), new ItemStack(GOTBlocks.wood6, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 12), new ItemStack(GOTBlocks.wood7, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 13), new ItemStack(GOTBlocks.wood7, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 14), new ItemStack(GOTBlocks.wood7, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 15), new ItemStack(GOTBlocks.wood7, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 2), new ItemStack(GOTBlocks.wood4, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 3), new ItemStack(GOTBlocks.wood4, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 4), new ItemStack(GOTBlocks.wood5, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 5), new ItemStack(GOTBlocks.wood5, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 6), new ItemStack(GOTBlocks.wood5, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 7), new ItemStack(GOTBlocks.wood5, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 8), new ItemStack(GOTBlocks.wood6, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 9), new ItemStack(GOTBlocks.wood6, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 0), new ItemStack(GOTBlocks.wood8, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 1), new ItemStack(GOTBlocks.wood8, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 2), new ItemStack(GOTBlocks.wood8, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 3), new ItemStack(GOTBlocks.wood8, 1, 3));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 4), new ItemStack(GOTBlocks.wood9, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 5), new ItemStack(GOTBlocks.wood9, 1, 1));
		addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 6), new ItemStack(GOTBlocks.wood9, 1, 2));
		addShapelessRecipe(new ItemStack(GOTBlocks.planksRotten, 4, 0), new ItemStack(GOTBlocks.rottenLog, 1, 0));
		addShapelessRecipe(new ItemStack(GOTBlocks.redBrick, 1, 0), new ItemStack(Blocks.brick_block, 1, 0), "vine");

		addShapelessRecipe(new ItemStack(GOTItems.alloySteelDaggerPoisoned), GOTItems.alloySteelDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.alloySteelGreatsword), GOTItems.alloySteelLongsword, GOTItems.alloySteelIngot);
		addShapelessRecipe(new ItemStack(GOTItems.alloySteelIngot, 9), new ItemStack(GOTBlocks.blockMetal2, 1, 4));
		addShapelessRecipe(new ItemStack(GOTItems.alloySteelLongsword), GOTItems.alloySteelSword, GOTItems.alloySteelIngot);
		addShapelessRecipe(new ItemStack(GOTItems.alloySteelNugget, 9), GOTItems.alloySteelIngot);
		addShapelessRecipe(new ItemStack(GOTItems.alloySteelScimitar), GOTItems.alloySteelSword);
		addShapelessRecipe(new ItemStack(GOTItems.alloySteelSword), GOTItems.alloySteelScimitar);
		addShapelessRecipe(new ItemStack(GOTItems.amber, 9), new ItemStack(GOTBlocks.blockGem, 1, 4));
		addShapelessRecipe(new ItemStack(GOTItems.amethyst, 9), new ItemStack(GOTBlocks.blockGem, 1, 1));
		addShapelessRecipe(new ItemStack(GOTItems.bottlePoison), Items.glass_bottle, GOTItems.wildberry);
		addShapelessRecipe(new ItemStack(GOTItems.brandingIron), new ItemStack(GOTItems.brandingIron, 1, 32767), "ingotIron");
		addShapelessRecipe(new ItemStack(GOTItems.bronzeDaggerPoisoned), GOTItems.bronzeDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.bronzeGreatsword), GOTItems.bronzeLongsword, GOTItems.bronzeIngot);
		addShapelessRecipe(new ItemStack(GOTItems.bronzeIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 2));
		addShapelessRecipe(new ItemStack(GOTItems.bronzeLongsword), GOTItems.bronzeSword, GOTItems.bronzeIngot);
		addShapelessRecipe(new ItemStack(GOTItems.bronzeNugget, 9), GOTItems.bronzeIngot);
		addShapelessRecipe(new ItemStack(GOTItems.bronzeScimitar), GOTItems.bronzeSword);
		addShapelessRecipe(new ItemStack(GOTItems.bronzeSword), GOTItems.bronzeScimitar);
		addShapelessRecipe(new ItemStack(GOTItems.copperDaggerPoisoned), GOTItems.copperDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.copperGreatsword), GOTItems.copperLongsword, GOTItems.copperIngot);
		addShapelessRecipe(new ItemStack(GOTItems.copperIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 0));
		addShapelessRecipe(new ItemStack(GOTItems.copperLongsword), GOTItems.copperSword, GOTItems.copperIngot);
		addShapelessRecipe(new ItemStack(GOTItems.copperNugget, 9), GOTItems.copperIngot);
		addShapelessRecipe(new ItemStack(GOTItems.copperScimitar), GOTItems.copperSword);
		addShapelessRecipe(new ItemStack(GOTItems.copperSword), GOTItems.copperScimitar);
		addShapelessRecipe(new ItemStack(GOTItems.coral, 4), new ItemStack(GOTBlocks.blockGem, 1, 8));
		addShapelessRecipe(new ItemStack(GOTItems.diamond, 9), new ItemStack(GOTBlocks.blockGem, 1, 5));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 0), new ItemStack(GOTBlocks.chrysanthemum, 1, 3));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 0), new ItemStack(GOTBlocks.southernFlower, 1, 1));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 1), new ItemStack(GOTBlocks.chrysanthemum, 1, 4));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 2), GOTBlocks.bluebell);
		addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 2), new ItemStack(GOTBlocks.chrysanthemum, 1, 0));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 3), new ItemStack(GOTBlocks.clover, 1, 32767));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 4), new ItemStack(Items.coal, 1, 1));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 2, 0), new ItemStack(GOTBlocks.doubleFlower, 1, 1));
		addShapelessRecipe(new ItemStack(GOTItems.dye, 2, 5), "dyeOrange", "dyeBlack");
		addShapelessRecipe(new ItemStack(GOTItems.dye, 3, 5), "dyeRed", "dyeYellow", "dyeBlack");
		addShapelessRecipe(new ItemStack(GOTItems.emerald, 9), new ItemStack(GOTBlocks.blockGem, 1, 9));
		addShapelessRecipe(new ItemStack(GOTItems.flaxSeeds, 2), GOTBlocks.flaxPlant);
		addShapelessRecipe(new ItemStack(GOTItems.gammon), Items.cooked_porkchop, GOTItems.salt);
		addShapelessRecipe(new ItemStack(GOTItems.goldDaggerPoisoned), GOTItems.goldDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.goldGreatsword), GOTItems.goldLongsword, "ingotGold");
		addShapelessRecipe(new ItemStack(GOTItems.goldLongsword), Items.golden_sword, "ingotGold");
		addShapelessRecipe(new ItemStack(GOTItems.goldScimitar), Items.golden_sword);
		addShapelessRecipe(new ItemStack(GOTItems.ironDaggerPoisoned), GOTItems.ironDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.ironGreatsword), GOTItems.ironLongsword, "ingotIron");
		addShapelessRecipe(new ItemStack(GOTItems.ironLongsword), Items.iron_sword, "ingotIron");
		addShapelessRecipe(new ItemStack(GOTItems.ironNugget, 9), Items.iron_ingot);
		addShapelessRecipe(new ItemStack(GOTItems.ironScimitar), Items.iron_sword);
		addShapelessRecipe(new ItemStack(GOTItems.kebab, 9), new ItemStack(GOTBlocks.kebabBlock, 1, 0));
		addShapelessRecipe(new ItemStack(GOTItems.leekSoup), Items.bowl, GOTItems.leek, GOTItems.leek, Items.potato);
		addShapelessRecipe(new ItemStack(GOTItems.mapleSyrup), new ItemStack(GOTBlocks.wood3, 1, 0), Items.bowl);
		addShapelessRecipe(new ItemStack(GOTItems.marzipan), GOTItems.almond, GOTItems.almond, Items.sugar);
		addShapelessRecipe(new ItemStack(GOTItems.marzipanChocolate), GOTItems.marzipan, new ItemStack(Items.dye, 1, 3));
		addShapelessRecipe(new ItemStack(GOTItems.melonSoup), Items.bowl, Items.melon, Items.melon, Items.sugar);
		addShapelessRecipe(new ItemStack(GOTItems.mushroomPie), Items.egg, Blocks.red_mushroom, Blocks.brown_mushroom);
		addShapelessRecipe(new ItemStack(GOTItems.obsidianDaggerPoisoned), GOTItems.obsidianDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.obsidianScimitar), GOTItems.obsidianSword);
		addShapelessRecipe(new ItemStack(GOTItems.obsidianShard, 9), Blocks.obsidian);
		addShapelessRecipe(new ItemStack(GOTItems.obsidianSword), GOTItems.obsidianScimitar);
		addShapelessRecipe(new ItemStack(GOTItems.opal, 9), new ItemStack(GOTBlocks.blockGem, 1, 7));
		addShapelessRecipe(new ItemStack(GOTItems.pearl, 9), new ItemStack(GOTBlocks.blockGem, 1, 6));
		addShapelessRecipe(new ItemStack(GOTItems.pebble, 4), Blocks.gravel);
		addShapelessRecipe(new ItemStack(GOTItems.pipeweedSeeds), GOTItems.pipeweedLeaf);
		addShapelessRecipe(new ItemStack(GOTItems.pipeweedSeeds, 2), GOTBlocks.pipeweedPlant);
		addShapelessRecipe(new ItemStack(GOTItems.questBook), Items.book, new ItemStack(Items.dye, 1, 1), "nuggetGold");
		addShapelessRecipe(new ItemStack(GOTItems.rabbitStew), Items.bowl, GOTItems.rabbitCooked, Items.potato, Items.potato);
		addShapelessRecipe(new ItemStack(GOTItems.ruby, 9), new ItemStack(GOTBlocks.blockGem, 1, 3));
		addShapelessRecipe(new ItemStack(GOTItems.salt, 9), new ItemStack(GOTBlocks.blockMetal2, 1, 3));
		addShapelessRecipe(new ItemStack(GOTItems.saltedFlesh), Items.rotten_flesh, GOTItems.salt);
		addShapelessRecipe(new ItemStack(GOTItems.saltpeter, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 14));
		addShapelessRecipe(new ItemStack(GOTItems.sapphire, 9), new ItemStack(GOTBlocks.blockGem, 1, 2));
		addShapelessRecipe(new ItemStack(GOTItems.seedsGrapeRed), GOTItems.grapeRed);
		addShapelessRecipe(new ItemStack(GOTItems.seedsGrapeWhite), GOTItems.grapeWhite);
		addShapelessRecipe(new ItemStack(GOTItems.silverIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 3));
		addShapelessRecipe(new ItemStack(GOTItems.silverNugget, 9), GOTItems.silverIngot);
		addShapelessRecipe(new ItemStack(GOTItems.stoneDaggerPoisoned), GOTItems.stoneDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.stoneScimitar), Items.stone_sword);
		addShapelessRecipe(new ItemStack(GOTItems.sulfur, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 13));
		addShapelessRecipe(new ItemStack(GOTItems.tinIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 1));
		addShapelessRecipe(new ItemStack(GOTItems.topaz, 9), new ItemStack(GOTBlocks.blockGem, 1, 0));
		addShapelessRecipe(new ItemStack(GOTItems.valyrianDaggerPoisoned), GOTItems.valyrianDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.valyrianGreatsword), GOTItems.valyrianLongsword, GOTItems.valyrianIngot);
		addShapelessRecipe(new ItemStack(GOTItems.valyrianIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 4));
		addShapelessRecipe(new ItemStack(GOTItems.valyrianLongsword), GOTItems.valyrianSword, GOTItems.valyrianIngot);
		addShapelessRecipe(new ItemStack(GOTItems.valyrianNugget, 9), GOTItems.valyrianIngot);
		addShapelessRecipe(new ItemStack(GOTItems.valyrianScimitar), GOTItems.valyrianSword);
		addShapelessRecipe(new ItemStack(GOTItems.valyrianSword), GOTItems.valyrianScimitar);
		addShapelessRecipe(new ItemStack(GOTItems.woodDaggerPoisoned), GOTItems.woodDagger, GOTItems.bottlePoison);
		addShapelessRecipe(new ItemStack(GOTItems.woodScimitar), Items.wooden_sword);

		addShapelessRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(GOTBlocks.southernFlower, 1, 0));
		addShapelessRecipe(new ItemStack(Items.dye, 1, 13), new ItemStack(GOTBlocks.southernFlower, 1, 3));
		addShapelessRecipe(new ItemStack(Items.dye, 1, 14), GOTBlocks.marigold);
		addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(GOTBlocks.chrysanthemum, 1, 1));
		addShapelessRecipe(new ItemStack(Items.dye, 1, 5), new ItemStack(GOTBlocks.southernFlower, 1, 2));
		addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(GOTBlocks.chrysanthemum, 1, 2));
		addShapelessRecipe(new ItemStack(Items.dye, 2, 1), new ItemStack(GOTBlocks.doubleFlower, 1, 3));
		addShapelessRecipe(new ItemStack(Items.dye, 2, 13), new ItemStack(GOTBlocks.doubleFlower, 1, 2));
		addShapelessRecipe(new ItemStack(Items.dye, 2, 15), GOTItems.saltpeter, Blocks.dirt);
		addShapelessRecipe(new ItemStack(Items.dye, 2, 5), new ItemStack(GOTBlocks.doubleFlower, 1, 0));
		addShapelessRecipe(new ItemStack(Items.dye, 4, 1), new ItemStack(GOTBlocks.wood9, 1, 0), new ItemStack(GOTBlocks.wood9, 1, 0));
		addShapelessRecipe(new ItemStack(Items.dye, 8, 15), GOTBlocks.boneBlock);
		addShapelessRecipe(new ItemStack(Items.golden_sword), GOTItems.goldScimitar);
		addShapelessRecipe(new ItemStack(Items.gunpowder, 2), GOTItems.sulfur, GOTItems.saltpeter, new ItemStack(Items.coal, 1, 1));
		addShapelessRecipe(new ItemStack(Items.gunpowder, 2), GOTItems.termite);
		addShapelessRecipe(new ItemStack(Items.iron_sword), GOTItems.ironScimitar);
		addShapelessRecipe(new ItemStack(Items.stone_sword), GOTItems.stoneScimitar);
		addShapelessRecipe(new ItemStack(Items.string), GOTItems.flax);
		addShapelessRecipe(new ItemStack(Items.wooden_sword), GOTItems.woodScimitar);

		for (int i = 0; i <= 15; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.concretePowder, 8, i), "ZXX", "XXY", "YYY", 'X', Blocks.sand, 'Y', Blocks.gravel, 'Z', DYE_ORE_NAMES[BlockColored.func_150032_b(i)]);
		}
		for (int i = 0; i <= 2; ++i) {
			addShapelessRecipe(new ItemStack(GOTBlocks.bomb, 1, i + 8), new ItemStack(GOTBlocks.bomb, 1, i), Items.lava_bucket);
		}

		for (int i = 0; i <= 3; ++i) {
			if (i != 1) {
				addShapedRecipe(new ItemStack(GOTBlocks.woodBeam1, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood1, 1, i));
			}
		}
		for (int i = 0; i <= 3; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam2, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood2, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam3, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood3, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam4, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood4, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam5, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood5, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam6, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood6, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam7, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood7, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam8, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood8, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeamFruit, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.fruitWood, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeamV1, 3, i), "X", "X", "X", 'X', new ItemStack(Blocks.log, 1, i));
		}
		for (int i = 0; i <= 1; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeamV2, 3, i), "X", "X", "X", 'X', new ItemStack(Blocks.log2, 1, i));
		}
		for (int i = 0; i <= 2; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.woodBeam9, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood9, 1, i));
		}


		for (int i = 0; i <= 15; ++i) {
			if (i != 1) {
				addShapedRecipe(new ItemStack(GOTBlocks.fence, 3, i), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks1, 1, i), 'Y', "stickWood");
			}
		}
		for (int i = 0; i <= 5; ++i) {
			addShapedRecipe(new ItemStack(Blocks.fence, 3, i), "XYX", "XYX", 'X', new ItemStack(Blocks.planks, 1, i), 'Y', "stickWood");
		}
		for (int i = 0; i <= 6; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.fence3, 3, i), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks3, 1, i), 'Y', "stickWood");
		}
		for (int i = 0; i <= 15; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.fence2, 3, i), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks2, 1, i), 'Y', "stickWood");
		}

		for (int i = 0; i <= 7; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.slabClayTileDyedSingle1, 6, i), "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.slabClayTileDyedSingle2, 6, i), "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, i + 8));
		}
		for (int i = 0; i <= 15; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.wallClayTileDyed, 6, i), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.clayTileDyed, 8, i), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0), 'Y', DYE_ORE_NAMES[BlockColored.func_150032_b(i)]);
			addShapedRecipe(new ItemStack(GOTBlocks.clayTileDyed, 4, i), "XX", "XX", 'X', new ItemStack(Blocks.stained_hardened_clay, 1, i));
		}

		for (int i = 0; i <= 15; ++i) {
			addShapedRecipe(new ItemStack(GOTBlocks.stainedGlass, 4, i), "XX", "XX", 'X', new ItemStack(Blocks.stained_glass, 1, i));
			addShapedRecipe(new ItemStack(GOTBlocks.stainedGlass, 8, i), "XXX", "XYX", "XXX", 'X', GOTBlocks.glass, 'Y', DYE_ORE_NAMES[BlockColored.func_150031_c(i)]);
			addShapedRecipe(new ItemStack(GOTBlocks.stainedGlassPane, 16, i), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.stainedGlass, 1, i));
		}

		for (GOTBlockFallenLeaves fallenLeafBlock : GOTBlockFallenLeaves.ALL_FALLEN_LEAVES) {
			for (Block leafBlock : fallenLeafBlock.getLeafBlocks()) {
				if (leafBlock instanceof GOTBlockLeavesBase) {
					String[] leafNames = ((GOTBlockLeavesBase) leafBlock).getAllLeafNames();
					for (int leafMeta = 0; leafMeta < leafNames.length; ++leafMeta) {
						Object[] fallenBlockMeta = GOTBlockFallenLeaves.fallenBlockMetaFromLeafBlockMeta(leafBlock, leafMeta);
						if (fallenBlockMeta != null) {
							Block fallenBlock = (Block) fallenBlockMeta[0];
							int fallenMeta = (Integer) fallenBlockMeta[1];
							if (fallenBlock != null) {
								addShapedRecipe(new ItemStack(fallenBlock, 6, fallenMeta), "XXX", 'X', new ItemStack(leafBlock, 1, leafMeta));
							}
						}
					}
				}
			}
		}

		registerDyeableWoolRobeRecipes(new ItemStack(GOTItems.robesBoots), "X X", "X X", 'X', Blocks.wool);
		registerDyeableWoolRobeRecipes(new ItemStack(GOTItems.robesChestplate), "X X", "XXX", "XXX", 'X', Blocks.wool);
		registerDyeableWoolRobeRecipes(new ItemStack(GOTItems.robesHelmet), "XXX", "X X", 'X', Blocks.wool);
		registerDyeableWoolRobeRecipes(new ItemStack(GOTItems.robesLeggings), "XXX", "X X", "X X", 'X', Blocks.wool);
	}

	private static void createSmeltingRecipes() {
		for (Object obj : Block.blockRegistry) {
			Block block = (Block) obj;
			if (block instanceof GOTBlockWoodBase) {
				GameRegistry.addSmelting(block, new ItemStack(Items.coal, 1, 1), 0.15f);
			}
		}
		GameRegistry.addSmelting(Blocks.stone, new ItemStack(GOTBlocks.scorchedStone), 0.1f);
		GameRegistry.addSmelting(GOTBlocks.rock, new ItemStack(GOTBlocks.scorchedStone), 0.1f);
		GameRegistry.addSmelting(GOTBlocks.whiteSand, new ItemStack(Blocks.glass), 0.1f);
		GameRegistry.addSmelting(GOTBlocks.oreCopper, new ItemStack(GOTItems.copperIngot), 0.35f);
		GameRegistry.addSmelting(GOTBlocks.oreTin, new ItemStack(GOTItems.tinIngot), 0.35f);
		GameRegistry.addSmelting(GOTBlocks.oreSilver, new ItemStack(GOTItems.silverIngot), 0.8f);
		GameRegistry.addSmelting(GOTBlocks.oreCobalt, new ItemStack(GOTItems.cobaltBlue), 0.8f);
		GameRegistry.addSmelting(GOTBlocks.oreGlowstone, new ItemStack(Items.glowstone_dust), 1.0f);
		GameRegistry.addSmelting(GOTBlocks.oreSulfur, new ItemStack(GOTItems.sulfur), 1.0f);
		GameRegistry.addSmelting(GOTBlocks.oreSaltpeter, new ItemStack(GOTItems.saltpeter), 1.0f);
		GameRegistry.addSmelting(GOTBlocks.oreSalt, new ItemStack(GOTItems.salt), 1.0f);
		GameRegistry.addSmelting(GOTItems.clayMug, new ItemStack(GOTItems.ceramicMug), 0.3f);
		GameRegistry.addSmelting(GOTItems.clayPlate, new ItemStack(GOTItems.ceramicPlate), 0.3f);
		GameRegistry.addSmelting(GOTItems.ceramicPlate, new ItemStack(GOTItems.plate), 0.3f);
		GameRegistry.addSmelting(GOTItems.redClayBall, new ItemStack(Items.brick), 0.3f);
		GameRegistry.addSmelting(GOTBlocks.redClay, new ItemStack(Blocks.hardened_clay), 0.35f);
		GameRegistry.addSmelting(GOTItems.rabbitRaw, new ItemStack(GOTItems.rabbitCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.lionRaw, new ItemStack(GOTItems.lionCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.zebraRaw, new ItemStack(GOTItems.zebraCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.rhinoRaw, new ItemStack(GOTItems.rhinoCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.walrusLardRaw, new ItemStack(GOTItems.walrusLardCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.elephantRaw, new ItemStack(GOTItems.elephantCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.beaverRaw, new ItemStack(GOTItems.beaverCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.muttonRaw, new ItemStack(GOTItems.muttonCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.deerRaw, new ItemStack(GOTItems.deerCooked), 0.35f);
		GameRegistry.addSmelting(GOTItems.camelRaw, new ItemStack(GOTItems.camelCooked), 0.35f);
		GameRegistry.addSmelting(new ItemStack(GOTBlocks.reeds, 1, 0), new ItemStack(GOTBlocks.driedReeds), 0.25f);
		GameRegistry.addSmelting(GOTItems.pipeweedLeaf, new ItemStack(GOTItems.pipeweed), 0.25f);
		GameRegistry.addSmelting(GOTItems.chestnut, new ItemStack(GOTItems.chestnutRoast), 0.3f);
		GameRegistry.addSmelting(GOTItems.corn, new ItemStack(GOTItems.cornCooked), 0.3f);
		GameRegistry.addSmelting(GOTItems.turnip, new ItemStack(GOTItems.turnipCooked), 0.3f);
		GameRegistry.addSmelting(GOTItems.yam, new ItemStack(GOTItems.yamRoast), 0.3f);
		GameRegistry.addSmelting(GOTItems.grapeRed, new ItemStack(GOTItems.raisins), 0.3f);
		GameRegistry.addSmelting(GOTItems.grapeWhite, new ItemStack(GOTItems.raisins), 0.3f);
		addSmeltingXPForItem(GOTItems.bronzeIngot, 0.7f);
		addSmeltingXPForItem(GOTItems.copperIngot, 0.7f);
		addSmeltingXPForItem(GOTItems.alloySteelIngot, 0.7f);
		addSmeltingXPForItem(GOTItems.valyrianIngot, 1.0f);
	}

	private static void modifyStandardRecipes() {
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence));
		removeRecipesItemStack(recipeList, new ItemStack(Blocks.sandstone, 1, 2));
		addShapedRecipe(new ItemStack(Blocks.sandstone, 2, 2), "X", "X", 'X', new ItemStack(Blocks.sandstone, 1, 0));
		for (int i = 0; i <= 5; ++i) {
			addShapedRecipe(new ItemStack(Blocks.fence, 3, i), "XYX", "XYX", 'X', new ItemStack(Blocks.planks, 1, i), 'Y', "stickWood");
		}
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence_gate));
		addShapedRecipe(new ItemStack(Blocks.fence_gate, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 0));
		removeRecipesItem(recipeList, Items.wooden_door);
		addShapedRecipe(new ItemStack(Items.wooden_door), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 0));
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.trapdoor));
		addShapedRecipe(new ItemStack(Blocks.trapdoor, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 0));
		if (GOTConfig.removeGoldenAppleRecipes) {
			removeRecipesItem(recipeList, Items.golden_apple);
		}
		if (GOTConfig.removeDiamondArmorRecipes) {
			removeRecipesItem(recipeList, Items.diamond_helmet);
			removeRecipesItem(recipeList, Items.diamond_chestplate);
			removeRecipesItem(recipeList, Items.diamond_leggings);
			removeRecipesItem(recipeList, Items.diamond_boots);
		}
		removeRecipesItemStack(recipeList, new ItemStack(Blocks.stone_slab, 1, 0));
		addShapedRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStoneV, 1, 0));
		removeRecipesItemStack(recipeList, new ItemStack(Blocks.stone_slab, 1, 5));
		addShapedRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.stone_brick_stairs));
		addShapedRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.anvil));
		addShapedRecipe(new ItemStack(Blocks.anvil), "XXX", " Y ", "XXX", 'X', "ingotIron", 'Y', "blockIron");
		removeRecipesClass(recipeList, RecipesArmorDyes.class);
		GameRegistry.addRecipe(new GOTRecipeArmorDyes());
	}

	public static ItemStack findMatchingRecipe(Iterable<IRecipe> recipeList, InventoryCrafting inv, World world) {
		for (IRecipe recipe : recipeList) {
			if (!recipe.matches(inv, world)) {
				continue;
			}
			return recipe.getCraftingResult(inv);
		}
		return null;
	}

	private static void removeRecipesClass(Collection<IRecipe> recipeList, Class<? extends IRecipe> recipeClass) {
		Collection<IRecipe> recipesToRemove = new ArrayList<>();
		for (IRecipe recipe : recipeList) {
			if (!recipeClass.isAssignableFrom(recipe.getClass())) {
				continue;
			}
			recipesToRemove.add(recipe);
		}
		recipeList.removeAll(recipesToRemove);
	}

	private static void removeRecipesItem(Collection<IRecipe> recipeList, Item outputItem) {
		Collection<IRecipe> recipesToRemove = new ArrayList<>();
		for (IRecipe recipe : recipeList) {
			ItemStack output = recipe.getRecipeOutput();
			if (output == null || output.getItem() != outputItem) {
				continue;
			}
			recipesToRemove.add(recipe);
		}
		recipeList.removeAll(recipesToRemove);
	}

	private static void removeRecipesItemStack(Collection<IRecipe> recipeList, ItemStack outputItemStack) {
		Collection<IRecipe> recipesToRemove = new ArrayList<>();
		for (IRecipe recipe : recipeList) {
			ItemStack output = recipe.getRecipeOutput();
			if (output == null || !output.isItemEqual(outputItemStack)) {
				continue;
			}
			recipesToRemove.add(recipe);
		}
		recipeList.removeAll(recipesToRemove);
	}

	private static void registerDyeableWoolRobeRecipes(ItemStack result, Object... params) {
		for (int i = 0; i <= 15; ++i) {
			Object[] paramsDyed = Arrays.copyOf(params, params.length);
			ItemStack wool = new ItemStack(Blocks.wool, 1, i);
			for (int l = 0; l < paramsDyed.length; ++l) {
				Object param = paramsDyed[l];
				if (param instanceof Block && param == Block.getBlockFromItem(wool.getItem())) {
					paramsDyed[l] = wool.copy();
					continue;
				}
				if (!(param instanceof ItemStack) || ((ItemStack) param).getItem() != wool.getItem()) {
					continue;
				}
				paramsDyed[l] = wool.copy();
			}
			ItemStack resultDyed = result.copy();
			float[] colors = EntitySheep.fleeceColorTable[i];
			float r = colors[0];
			float g = colors[1];
			float b = colors[2];
			if (r != 1.0f || g != 1.0f || b != 1.0f) {
				int rI = (int) (r * 255.0f);
				int gI = (int) (g * 255.0f);
				int bI = (int) (b * 255.0f);
				int rgb = rI << 16 | gI << 8 | bI;
				GOTItemRobes.setRobesColor(resultDyed, rgb);
			}
			addShapedRecipe(resultDyed, paramsDyed);
		}
	}

	private static void addSmeltingXPForItem(Item item, float xp) {
		try {
			Field field = FurnaceRecipes.class.getDeclaredFields()[2];
			field.setAccessible(true);
			Map<ItemStack, Float> map = (Map<ItemStack, Float>) field.get(FurnaceRecipes.smelting());
			map.put(new ItemStack(item, 1, 32767), xp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean checkItemEquals(ItemStack target, ItemStack input) {
		return target.getItem().equals(input.getItem()) && (target.getItemDamage() == 32767 || target.getItemDamage() == input.getItemDamage());
	}
}