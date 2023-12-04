package got.common.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import got.common.GOTConfig;
import got.common.block.leaves.GOTBlockLeavesBase;
import got.common.block.other.GOTBlockConcretePowder;
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
import got.common.item.other.GOTItemBone;
import got.common.item.other.GOTItemRobes;
import got.common.util.GOTEnumDyeColor;
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
	public static Collection<IRecipe> slab = new ArrayList<>();
	public static List<IRecipe> north = new ArrayList<>();
	public static List<IRecipe> hillmen = new ArrayList<>();
	public static List<IRecipe> wildling = new ArrayList<>();
	public static List<IRecipe> summer = new ArrayList<>();
	public static List<IRecipe> gift = new ArrayList<>();
	public static List<IRecipe> lhazar = new ArrayList<>();
	public static List<IRecipe> sothoryos = new ArrayList<>();
	public static List<IRecipe> yiti = new ArrayList<>();
	public static List<IRecipe> ibben = new ArrayList<>();
	public static List<IRecipe> unsmelt = new ArrayList<>();
	public static List<IRecipe> arryn = new ArrayList<>();
	public static Collection<IRecipe> tinyBasalt = new ArrayList<>();
	public static List<IRecipe> dorne = new ArrayList<>();
	public static List<IRecipe> reach = new ArrayList<>();
	public static List<IRecipe> westerlands = new ArrayList<>();
	public static List<IRecipe> crownlands = new ArrayList<>();
	public static List<IRecipe> stormlands = new ArrayList<>();
	public static List<IRecipe> dragonstone = new ArrayList<>();
	public static List<IRecipe> riverlands = new ArrayList<>();
	public static List<IRecipe> ironborn = new ArrayList<>();
	public static List<IRecipe> volantis = new ArrayList<>();
	public static List<IRecipe> braavos = new ArrayList<>();
	public static List<IRecipe> tyrosh = new ArrayList<>();
	public static List<IRecipe> lys = new ArrayList<>();
	public static List<IRecipe> myr = new ArrayList<>();
	public static List<IRecipe> qohor = new ArrayList<>();
	public static List<IRecipe> lorath = new ArrayList<>();
	public static List<IRecipe> norvos = new ArrayList<>();
	public static List<IRecipe> pentos = new ArrayList<>();
	public static List<IRecipe> qarth = new ArrayList<>();
	public static List<IRecipe> ghiscar = new ArrayList<>();
	public static List<IRecipe> asshai = new ArrayList<>();
	public static List<IRecipe> jogos = new ArrayList<>();
	public static List<IRecipe> dothraki = new ArrayList<>();
	public static Collection<IRecipe> commonWesteros = new ArrayList<>();
	public static Collection<IRecipe> commonEssos = new ArrayList<>();
	public static String[] dyeOreNames = {"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	public static List<IRecipe> mossovy = new ArrayList<>();

	public static void addDyeableWoolRobeRecipes(Collection<IRecipe> recipeList, ItemStack result, Object... params) {
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
			recipeList.add(new ShapedOreRecipe(resultDyed, paramsDyed));
		}
	}

	public static void addSmeltingXPForItem(Item item, float xp) {
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

	public static void createArrynRecipes() {
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableArryn), "XX", "XX", 'X', "plankWood"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynHelmet), "XXX", "X X", 'X', "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.arrynBoots), "X X", "X X", 'X', "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ARRYN.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		arryn.addAll(commonWesteros);
		arryn.addAll(tinyBasalt);
	}

	public static void createAsshaiRecipes() {
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick1, 4, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableAsshai), "XX", "XX", 'X', "plankWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle10, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 0), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsBasaltBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 1), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsBasaltBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 9), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.chandelier, 2, 12), " X ", "YZY", 'X', "stickWood", 'Y', GOTItems.fuse, 'Z', "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 7), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick2, 1, 10), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsBasalt, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiBow), " XY", "X Y", " XY", 'X', "ingotIron", 'Y', Items.string));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.asshaiTorch), "X", "Y", 'X', Items.coal, 'Y', "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiHelmet), "XXX", "X X", 'X', "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiBoots), "X X", "X X", 'X', "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiStaff), "  Y", " Y ", "Y  ", 'Y', "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.asshaiMask), "XXX", "X X", 'X', "plankWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.asshaiBars, 16), "XXX", "XXX", 'X', "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ASSHAI.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
	}

	public static void createBraavosRecipes() {
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableBraavos), "XX", "XX", 'X', "plankWood"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosHelmet), "XXX", "X X", 'X', "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTItems.braavosBoots), "X X", "X X", 'X', "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.BRAAVOS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		braavos.addAll(commonEssos);
		braavos.addAll(tinyBasalt);
	}

	public static void createCommonEssosRecipes() {
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosHorseArmor), "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateEssos, 4), "ZYZ", "YXY", "ZYZ", 'X', GOTItems.gateGear, 'Y', "plankWood", 'Z', "ingotIron"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosBow), " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosHammer), " XX", " XX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosPike), "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosPolearm), " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		commonEssos.add(new GOTRecipeRobesDye(GOTMaterial.ROBES));
	}

	public static void createCommonWesterosRecipes() {
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateWesteros, 4), "ZYZ", "YXY", "ZYZ", 'X', GOTItems.gateGear, 'Y', "plankWood", 'Z', "ingotIron"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosBow), " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosHorseArmor), "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosLance), "  X", " X ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosPike), "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosLongsword), "X", "Y", 'X', "ingotIron", 'Y', GOTItems.westerosSword));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosGreatsword), "X", "X", "Y", 'X', "ingotIron", 'Y', GOTItems.westerosSword));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerosGreatsword), "X", "Y", 'X', "ingotIron", 'Y', GOTItems.westerosLongsword));
	}

	public static void createCrownlandsRecipes() {
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.kingsguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableCrownlands), "XX", "XX", 'X', "plankWood"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.crownlandsBoots), "X X", "X X", 'X', "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ROBERT.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		crownlands.addAll(commonWesteros);
		crownlands.addAll(tinyBasalt);
	}

	public static void createDorneRecipes() {
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableDorne), "XX", "XX", 'X', "plankWood"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneHelmet), "XXX", "X X", 'X', "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTItems.dorneBoots), "X X", "X X", 'X', "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.MARTELL.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		dorne.addAll(commonWesteros);
		dorne.addAll(tinyBasalt);
	}

	public static void createDothrakiRecipes() {
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiHorseArmor), "X  ", "XXX", "XXX", 'X', Items.leather));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiHelmet), "XXX", "X X", 'X', GOTBlocks.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiChestplate), "X X", "XXX", "XXX", 'X', GOTBlocks.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiLeggings), "XXX", "X X", "X X", 'X', GOTBlocks.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.dothrakiBoots), "X X", "X X", 'X', GOTBlocks.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadBow), " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string));
	}

	public static void createDragonstoneRecipes() {
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableDragonstone), "XX", "XX", 'X', "plankWood"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneHelmet), "XXX", "X X", 'X', "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTItems.dragonstoneBoots), "X X", "X X", 'X', "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.STANNIS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		dragonstone.addAll(commonWesteros);
		dragonstone.addAll(tinyBasalt);
	}

	public static void createGhiscarRecipes() {
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableGhiscar), "XX", "XX", 'X', "plankWood"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarHelmet), "XXX", "X X", 'X', "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.ghiscarBoots), "X X", "X X", 'X', "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.GHISCAR.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTItems.harpy), "XXX", "X X", 'X', Items.gold_ingot));
		ghiscar.addAll(commonEssos);
	}

	public static void createGiftRecipes() {
		gift.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableGift), "XX", "XX", 'X', "plankWood"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftHelmet), "XXX", "X X", 'X', "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTItems.giftBoots), "X X", "X X", 'X', "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.NIGHT.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		gift.addAll(commonWesteros);
	}

	public static void createHillmanRecipes() {
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableHillTribes), "XX", "XX", 'X', "plankWood"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenHelmet), "XXX", "X X", 'X', "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTItems.hillmenBoots), "X X", "X X", 'X', "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.HILLMEN.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
	}

	public static void createIbbenRecipes() {
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenBoots), "X X", "X X", 'X', "ingotIron"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTItems.ibbenHarpoon), "  X", " Y ", "YZ ", 'X', "ingotIron", 'Y', "stickWood", 'Z', Items.string));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableIbben), "XX", "XX", 'X', "plankWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 2), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick1, 4, 4), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsRhyoliteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 8), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 6), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.IBBEN.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 8), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 8)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadBow), " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsRhyolite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick5, 1, 3), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeamS, 3, 0), "X", "X", "X", 'X', "logWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeamS, 1, 1), " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(GOTBlocks.woodBeamS, 1, 0)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateIbben, 4), "ZYZ", "YXY", "ZYZ", 'X', GOTItems.gateGear, 'Y', "plankWood", 'Z', "ingotIron"));
	}

	public static void createIronbornRecipes() {
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableIronborn), "XX", "XX", 'X', "plankWood"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornHelmet), "XXX", "X X", 'X', "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTItems.ironbornBoots), "X X", "X X", 'X', "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.GREYJOY.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		ironborn.addAll(commonWesteros);
		ironborn.addAll(tinyBasalt);
	}

	public static void createJogosRecipes() {
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.JOGOS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosHelmet), "XXX", "X X", 'X', "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.jogosBoots), "X X", "X X", 'X', "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTItems.nomadBow), " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string));
	}

	public static void createLhazarRecipes() {
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableLhazar), "XX", "XX", 'X', "plankWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LHAZAR.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarHelmet), "XXX", "X X", 'X', GOTItems.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarChestplate), "X X", "XXX", "XXX", 'X', GOTItems.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarLeggings), "XXX", "X X", "X X", 'X', GOTItems.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarBoots), "X X", "X X", 'X', GOTItems.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick3, 4, 10), "XX", "XX", 'X', GOTBlocks.redClay));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick3, 4, 10)));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsLhazarBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 4, 10)));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone2, 6, 15), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 4, 10)));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.rhinoHorn, 'Y', "stickWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarDagger), "X", "Y", 'X', GOTItems.rhinoHorn, 'Y', "stickWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarSpear), "  X", " X ", "X  ", 'X', GOTItems.rhinoHorn));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarHelmetLion), "XXX", "X X", 'X', GOTItems.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarChestplateLion), "X X", "XXX", "XXX", 'X', GOTItems.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarLeggingsLion), "XXX", "X X", "X X", 'X', GOTItems.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarBootsLion), "X X", "X X", 'X', GOTItems.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarClub), "X", "X", "X", 'X', "plankWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTItems.lhazarSword), "X", "X", "Y", 'X', "ingotBronze", 'Y', "stickWood"));
	}

	public static void createLorathRecipes() {
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableLorath), "XX", "XX", 'X', "plankWood"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathHelmet), "XXX", "X X", 'X', "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTItems.lorathBoots), "X X", "X X", 'X', "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LORATH.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		lorath.addAll(commonEssos);
		lorath.addAll(tinyBasalt);
	}

	public static void createLysRecipes() {
		lys.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableLys), "XX", "XX", 'X', "plankWood"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysHelmet), "XXX", "X X", 'X', "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTItems.lysBoots), "X X", "X X", 'X', "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LYS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		lys.addAll(commonEssos);
		lys.addAll(tinyBasalt);
	}

	public static void createMossovyRecipes() {
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableMossovy), "XX", "XX", 'X', "plankWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovyChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovyLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovyBoots), "X X", "X X", 'X', "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovySword), "X", "X", "Y", 'X', GOTItems.silverIngot, 'Y', "stickWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTItems.mossovyDagger), "X", "Y", 'X', GOTItems.silverIngot, 'Y', "stickWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.MOSSOVY.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
	}

	public static void createMyrRecipes() {
		myr.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableMyr), "XX", "XX", 'X', "plankWood"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrHelmet), "XXX", "X X", 'X', "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTItems.myrBoots), "X X", "X X", 'X', "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.MYR.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		myr.addAll(commonEssos);
		myr.addAll(tinyBasalt);
	}

	public static void createNorthRecipes() {
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableNorth), "XX", "XX", 'X', "plankWood"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northHelmet), "XXX", "X X", 'X', "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.northBoots), "X X", "X X", 'X', "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.ROBB.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		north.addAll(commonWesteros);
		north.addAll(tinyBasalt);
	}

	public static void createNorvosRecipes() {
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableNorvos), "XX", "XX", 'X', "plankWood"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosHelmet), "XXX", "X X", 'X', "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTItems.norvosBoots), "X X", "X X", 'X', "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.NORVOS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		norvos.addAll(commonEssos);
		norvos.addAll(tinyBasalt);
	}

	public static void createPentosRecipes() {
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tablePentos), "XX", "XX", 'X', "plankWood"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosHelmet), "XXX", "X X", 'X', "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTItems.pentosBoots), "X X", "X X", 'X', "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.PENTOS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		pentos.addAll(commonEssos);
	}

	public static void createQarthRecipes() {
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableQarth), "XX", "XX", 'X', "plankWood"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthHelmet), "XXX", "X X", 'X', "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTItems.qarthBoots), "X X", "X X", 'X', "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.QARTH.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		qarth.addAll(commonEssos);
		qarth.addAll(tinyBasalt);
	}

	public static void createQohorRecipes() {
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.unsulliedBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableQohor), "XX", "XX", 'X', "plankWood"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorHelmet), "XXX", "X X", 'X', "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.qohorBoots), "X X", "X X", 'X', "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.QOHOR.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		qohor.addAll(commonEssos);
		qohor.addAll(tinyBasalt);
	}

	public static void createReachRecipes() {
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableReach), "XX", "XX", 'X', "plankWood"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachHelmet), "XXX", "X X", 'X', "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.reachBoots), "X X", "X X", 'X', "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.TYRELL.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		reach.addAll(commonWesteros);
		reach.addAll(tinyBasalt);
	}

	public static void createRiverlandsRecipes() {
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableRiverlands), "XX", "XX", 'X', "plankWood"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.riverlandsBoots), "X X", "X X", 'X', "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.TULLY.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		riverlands.addAll(commonWesteros);
		riverlands.addAll(tinyBasalt);
	}

	public static void createSmeltingRecipes() {
		for (Object obj : Block.blockRegistry) {
			Block block = (Block) obj;
			if (!(block instanceof GOTBlockWoodBase)) {
				continue;
			}
			GameRegistry.addSmelting(block, new ItemStack(Items.coal, 1, 1), 0.15f);
		}
		GameRegistry.addSmelting(Blocks.stone, new ItemStack(GOTBlocks.scorchedStone), 0.1f);
		GameRegistry.addSmelting(GOTBlocks.rock, new ItemStack(GOTBlocks.scorchedStone), 0.1f);
		GameRegistry.addSmelting(GOTBlocks.whiteSand, new ItemStack(Blocks.glass), 0.1f);
		GameRegistry.addSmelting(GOTBlocks.oreCopper, new ItemStack(GOTItems.copperIngot), 0.35f);
		GameRegistry.addSmelting(GOTBlocks.oreTin, new ItemStack(GOTItems.tinIngot), 0.35f);
		GameRegistry.addSmelting(GOTBlocks.oreSilver, new ItemStack(GOTItems.silverIngot), 0.8f);
		GameRegistry.addSmelting(GOTBlocks.oreCobalt, new ItemStack(GOTItems.cobaltIngot), 0.8f);
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
		addSmeltingXPForItem(GOTItems.valyrianIngot, 1.0f);
		addSmeltingXPForItem(GOTItems.valyrianPowder, 0.8f);
		addSmeltingXPForItem(GOTItems.yitiSteelIngot, 0.7f);
	}

	public static void createSothoryosRecipes() {
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableSothoryos), "XX", "XX", 'X', "plankWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.SOTHORYOS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 4, 0), "XX", "XX", 'X', Blocks.stone));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 0)));
		sothoryos.add(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick4, 1, 1), new ItemStack(GOTBlocks.brick4, 1, 0), "vine"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 1)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 1)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 1), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 1)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 2)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 2)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 2)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 4, 3), "XX", "XX", 'X', "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 3)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickGold, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 3)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 3)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 4, 4), "XX", "XX", 'X', GOTItems.obsidianShard));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 4)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSothoryosBrickObsidian, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 4)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 4), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 4, 4)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosShovel), "X", "Y", "Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosPickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosAxe), "XX", "XY", " Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosHoe), "XX", " Y", " Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosDagger), "X", "Y", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosSpear), "  X", " Y ", "Y  ", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosSword), "XZX", "XZX", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood", 'Z', "plankWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosHelmet), "XXX", "X X", 'X', "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosBoots), "X X", "X X", 'X', "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosHelmetChieftain), "X", "Y", 'X', new ItemStack(GOTBlocks.doubleFlower, 1, 3), 'Y', new ItemStack(GOTItems.sothoryosHelmet, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sarbacane), "XYY", 'X', "stickWood", 'Y', GOTBlocks.reeds));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.dart, 4), "X", "Y", "Z", 'X', GOTItems.obsidianShard, 'Y', "stickWood", 'Z', "feather"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.dartPoisoned, 4), " X ", "XYX", " X ", 'X', GOTBlocks.sarbacaneTrap, 'Y', "poison"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.sarbacaneTrap), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 0), 'Y', new ItemStack(GOTItems.sarbacane, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 14), "X", "X", "X", 'X', Blocks.stone));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 14)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosDoubleTorch, 2), "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.sarbacaneTrapGold), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 3), 'Y', new ItemStack(GOTItems.sarbacane, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateSothoryos, 4), "ZYZ", "YXY", "ZYZ", 'X', GOTItems.gateGear, 'Y', "plankWood", 'Z', "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosHammer), "XYX", "XYX", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosPike), "  X", " YX", "Y  ", 'X', GOTItems.obsidianShard, 'Y', "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.sarbacaneTrapObsidian), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 4), 'Y', new ItemStack(GOTItems.sarbacane, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 11), "X", "X", "X", 'X', "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 12), "X", "X", "X", 'X', GOTItems.obsidianShard));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle6, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 11)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle6, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 12)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosHelmetGold), "XXX", "X X", 'X', "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosChestplateGold), "X X", "XXX", "XXX", 'X', "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosLeggingsGold), "XXX", "X X", "X X", 'X', "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTItems.sothoryosBootsGold), "X X", "X X", 'X', "ingotGold"));
	}

	public static void createStandardRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 3), "XXX", "XYX", "XXX", 'X', GOTItems.westerosSword, 'Y', Items.lava_bucket));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 3), "XXX", "XYX", "XXX", 'X', Items.iron_sword, 'Y', Items.lava_bucket));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.flintSpear), "  X", " Y ", "Y  ", 'X', Items.flint, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.flintDagger), "X", "Y", 'X', Items.flint, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.club), "X", "X", "X", 'X', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.trident), " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wildFireJar), "XYX", "YZY", "XYX", 'X', "ingotIron", 'Y', Items.gunpowder, 'Z', GOTItems.fuse));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.firePot, 4), "Z", "Y", "X", 'X', "ingotIron", 'Y', Items.gunpowder, 'Z', GOTItems.fuse));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.skullStaff), "X", "Y", "Y", 'X', Items.skull, 'Y', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.beacon), "XXX", "XXX", "YYY", 'X', "logWood", 'Y', Blocks.cobblestone));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.kebabStandSand), " X ", " Y ", "ZZZ", 'X', "plankWood", 'Y', "stickWood", 'Z', Blocks.sandstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.kebabStand), " X ", " Y ", "ZZZ", 'X', "plankWood", 'Y', "stickWood", 'Z', Blocks.cobblestone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.shishKebab, 2), "  X", " X ", "Y  ", 'X', GOTItems.kebab, 'Y', "stickWood"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick1, 4, 15), "XX", "XX", 'X', new ItemStack(Blocks.sandstone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle4, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 15), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 5), "X", "X", "X", 'X', new ItemStack(Blocks.sandstone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle4, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick3, 1, 8), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick3, 4, 13), "XX", "XX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrickRed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 4), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick3, 1, 15), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick3, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsSandstoneBrickRedCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick3, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 15), "X", "X", "X", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 1, 7), " X ", "XYX", " X ", 'X', "gemLapis", 'Y', new ItemStack(GOTBlocks.brick1, 1, 15)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick1, 1, 2), new ItemStack(GOTBlocks.brick1, 1, 1), "vine"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick1, 1, 5), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick1, 4, 1), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 6), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle1, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 1), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsAndesite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsAndesiteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsAndesiteBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsAndesiteBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 4), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 3)));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_helmet), "XXX", "Y Y", 'X', "ingotIron", 'Y', GOTItems.ironNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_chestplate), "X X", "YYY", "XXX", 'X', "ingotIron", 'Y', GOTItems.ironNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_leggings), "XXX", "Y Y", "X X", 'X', "ingotIron", 'Y', GOTItems.ironNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_boots), "Y Y", "X X", 'X', "ingotIron", 'Y', GOTItems.ironNugget));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeChainmailHelmet), "XXX", "Y Y", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeChainmailChestplate), "X X", "YYY", "XXX", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeChainmailLeggings), "XXX", "Y Y", "X X", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeChainmailBoots), "Y Y", "X X", 'X', GOTItems.bronzeIngot, 'Y', GOTItems.bronzeNugget));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianChainmailHelmet), "XXX", "Y Y", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianChainmailChestplate), "X X", "YYY", "XXX", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianChainmailLeggings), "XXX", "Y Y", "X X", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianChainmailBoots), "Y Y", "X X", 'X', GOTItems.valyrianIngot, 'Y', GOTItems.valyrianNugget));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.alloySteelAxe), "XX", "XY", " Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.alloySteelDagger), "X", "Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.alloySteelHalberd), " XX", " YX", "Y  ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.alloySteelHoe), "XX", " Y", " Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.alloySteelPickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.alloySteelShovel), "X", "Y", "Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.alloySteelSword), "X", "X", "Y", 'X', GOTItems.alloySteelIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 4), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 5), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 7), new ItemStack(GOTBlocks.brick6, 1, 4), "vine"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.iceHeavySword), " XX", " X ", "Y  ", 'X', GOTItems.iceShard, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.iceSword), "X", "X", "Y", 'X', GOTItems.iceShard, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 6), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsLabradorite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsLabradoriteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsLabradoriteBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsLabradoriteBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 0), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 6), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 7), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 8), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle2, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 7)));
		GameRegistry.addRecipe(new ItemStack(Blocks.dirt, 4, 1), "XY", "YX", 'X', new ItemStack(Blocks.dirt, 1, 0), 'Y', Blocks.gravel);
		GameRegistry.addRecipe(new ItemStack(Blocks.obsidian, 1), "XXX", "XXX", "XXX", 'X', GOTItems.obsidianShard);
		GameRegistry.addRecipe(new ItemStack(Blocks.packed_ice), "XX", "XX", 'X', Blocks.ice);
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "XX", "XX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.alloyForge), "XXX", "X X", "XXX", 'X', GOTBlocks.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTItems.alloySteelIngot), "XXX", "XXX", "XXX", 'X', GOTItems.alloySteelNugget);
		GameRegistry.addRecipe(new ItemStack(GOTItems.bananaBread), "XYX", 'X', Items.wheat, 'Y', GOTItems.banana);
		GameRegistry.addRecipe(new ItemStack(GOTItems.bananaCake), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', GOTItems.banana, 'C', Items.egg, 'D', Items.wheat);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.bank), "XXX", "XYX", "XXX", 'X', Blocks.cobblestone, 'Y', GOTItems.coin);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 0), "XXX", "XXX", "XXX", 'X', GOTItems.topaz);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 1), "XXX", "XXX", "XXX", 'X', GOTItems.amethyst);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 2), "XXX", "XXX", "XXX", 'X', GOTItems.sapphire);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 3), "XXX", "XXX", "XXX", 'X', GOTItems.ruby);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 4), "XXX", "XXX", "XXX", 'X', GOTItems.amber);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 5), "XXX", "XXX", "XXX", 'X', GOTItems.diamond);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 6), "XXX", "XXX", "XXX", 'X', GOTItems.pearl);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 7), "XXX", "XXX", "XXX", 'X', GOTItems.opal);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 8), "XX", "XX", 'X', GOTItems.coral);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockGem, 1, 9), "XXX", "XXX", "XXX", 'X', GOTItems.emerald);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 0), "XXX", "XXX", "XXX", 'X', GOTItems.copperIngot);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 1), "XXX", "XXX", "XXX", 'X', GOTItems.tinIngot);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 13), "XXX", "XXX", "XXX", 'X', GOTItems.sulfur);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 14), "XXX", "XXX", "XXX", 'X', GOTItems.saltpeter);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 2), "XXX", "XXX", "XXX", 'X', GOTItems.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 3), "XXX", "XXX", "XXX", 'X', GOTItems.silverIngot);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal1, 1, 4), "XXX", "XXX", "XXX", 'X', GOTItems.valyrianIngot);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal2, 1, 2), "XXX", "XXX", "XXX", 'X', GOTItems.yitiSteelIngot);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal2, 1, 3), "XXX", "XXX", "XXX", 'X', GOTItems.salt);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.blockMetal2, 1, 4), "XXX", "XXX", "XXX", 'X', GOTItems.alloySteelIngot);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.brick1, 4, 14), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.brick2, 4, 2), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.brick3, 1, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.brick3, 1, 1), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.brick4, 4, 15), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.brick5, 4, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.mud, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTItems.bronzeBoots), "X X", "X X", 'X', GOTItems.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.bronzeChestplate), "X X", "XXX", "XXX", 'X', GOTItems.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.bronzeHelmet), "XXX", "X X", 'X', GOTItems.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.bronzeLeggings), "XXX", "X X", "X X", 'X', GOTItems.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.cherryPie), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', GOTItems.cherry, 'C', Items.sugar, 'D', Items.wheat);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.chestBasket), "XXX", "X X", "XXX", 'X', GOTBlocks.driedReeds);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.clayTile, 4, 0), "XX", "XX", 'X', new ItemStack(Blocks.hardened_clay, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTItems.copperIngot), "XXX", "XXX", "XXX", 'X', GOTItems.copperNugget);
		GameRegistry.addRecipe(new ItemStack(GOTItems.bronzeIngot), "XXX", "XXX", "XXX", 'X', GOTItems.bronzeNugget);
		GameRegistry.addRecipe(new ItemStack(Items.iron_ingot), "XXX", "XXX", "XXX", 'X', GOTItems.ironNugget);
		GameRegistry.addRecipe(new ItemStack(GOTItems.cornBread), "XXX", 'X', GOTItems.corn);
		GameRegistry.addRecipe(new ItemStack(GOTItems.diamondHorseArmor), "X  ", "XYX", "XXX", 'X', Items.diamond, 'Y', Items.leather);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.dirtPath, 2, 0), "XX", 'X', Blocks.dirt);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.dirtPath, 2, 1), "XX", 'X', GOTBlocks.mud);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.dirtPath, 3, 2), "XYZ", 'X', Blocks.dirt, 'Y', Blocks.gravel, 'Z', Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(GOTItems.furBoots), "X X", "X X", 'X', GOTItems.fur);
		GameRegistry.addRecipe(new ItemStack(GOTItems.furChestplate), "X X", "XXX", "XXX", 'X', GOTItems.fur);
		GameRegistry.addRecipe(new ItemStack(GOTItems.furHelmet), "XXX", "X X", 'X', GOTItems.fur);
		GameRegistry.addRecipe(new ItemStack(GOTItems.furLeggings), "XXX", "X X", "X X", 'X', GOTItems.fur);
		GameRegistry.addRecipe(new ItemStack(GOTItems.gemsbokBoots), "X X", "X X", 'X', GOTItems.gemsbokHide);
		GameRegistry.addRecipe(new ItemStack(GOTItems.gemsbokChestplate), "X X", "XXX", "XXX", 'X', GOTItems.gemsbokHide);
		GameRegistry.addRecipe(new ItemStack(GOTItems.gemsbokHelmet), "Y Y", "XXX", "X X", 'X', GOTItems.gemsbokHide, 'Y', GOTItems.gemsbokHorn);
		GameRegistry.addRecipe(new ItemStack(GOTItems.gemsbokLeggings), "XXX", "X X", "X X", 'X', GOTItems.gemsbokHide);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.glass, 4), "XX", "XX", 'X', Blocks.glass);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.glassPane, 16), "XXX", "XXX", 'X', GOTBlocks.glass);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.hearth, 3), "XXX", "YYY", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', Items.brick);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.kebabBlock, 1, 0), "XXX", "XXX", "XXX", 'X', GOTItems.kebab);
		GameRegistry.addRecipe(new ItemStack(GOTItems.leatherHat), " X ", "XXX", 'X', Items.leather);
		GameRegistry.addRecipe(new ItemStack(GOTItems.lemonCake), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', GOTItems.lemon, 'C', Items.sugar, 'D', Items.wheat);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.marzipanBlock), "XXX", 'X', GOTItems.marzipan);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.mud, 4, 1), "XY", "YX", 'X', new ItemStack(GOTBlocks.mud, 1, 0), 'Y', Blocks.gravel);
		GameRegistry.addRecipe(new ItemStack(GOTItems.oliveBread), "XYX", 'X', Items.wheat, 'Y', GOTItems.olive);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.oven), "XXX", "X X", "XXX", 'X', Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pillar1, 3, 3), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pillar1, 3, 4), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pillar2, 3, 1), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pillar2, 3, 2), "X", "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pillar2, 3, 3), "X", "X", "X", 'X', Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pressurePlateAndesite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pressurePlateBasalt), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pressurePlateChalk), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pressurePlateDiorite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pressurePlateGranite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pressurePlateRhyolite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.pressurePlateLabradorite), "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.redSandstone, 1, 0), "XX", "XX", 'X', new ItemStack(Blocks.sand, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.rope, 3), "X", "X", "X", 'X', Items.string);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.scorchedSlabSingle, 6, 0), "XXX", 'X', GOTBlocks.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.scorchedWall, 6), "XXX", "XXX", 'X', GOTBlocks.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTItems.silverIngot), "XXX", "XXX", "XXX", 'X', GOTItems.silverNugget);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabClayTileSingle, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle10, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.whiteSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle11, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle3, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle7, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle8, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.smoothStone, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingle9, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 0), "XXX", 'X', new ItemStack(Blocks.dirt, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.dirtPath, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.dirtPath, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.dirtPath, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.mud, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleDirt, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.asshaiDirt, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleGravel, 6, 0), "XXX", 'X', new ItemStack(Blocks.gravel, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleGravel, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.basaltGravel, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleGravel, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.obsidianGravel, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleSand, 6, 0), "XXX", 'X', new ItemStack(Blocks.sand, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleSand, 6, 1), "XXX", 'X', new ItemStack(Blocks.sand, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleSand, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.whiteSand, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleThatch, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleThatch, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 0), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 1), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 4), "XXX", 'X', Blocks.mossy_cobblestone);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 3), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 4), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.smoothStone, 2, 5), "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsAlmond, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsApple, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsAramant, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsAspen, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsBanana, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsBaobab, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsBeech, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsCedar, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsChalk, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsChalkBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsCharred, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsCherry, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsChestnut, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTile, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedBlack, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedBlue, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 11));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedBrown, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 12));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedCyan, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 9));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedGray, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 7));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedGreen, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 13));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedLightBlue, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedLightGray, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 8));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedLime, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedMagenta, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedOrange, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedPink, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedPurple, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 10));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedRed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedWhite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsClayTileDyedYellow, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsCobblestoneMossy, 4), "X  ", "XX ", "XXX", 'X', Blocks.mossy_cobblestone);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsCypress, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsDatePalm, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsDiorite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsDioriteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsDragon, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsFir, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsFotinia, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsGranite, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsGraniteBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsGreenOak, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsHolly, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsKanuka, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsLarch, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsLemon, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsLime, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsMahogany, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsMango, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsMangrove, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsMaple, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsMudBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsOlive, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsOrange, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsPalm, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsPear, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsPine, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsPlum, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsPomegranate, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsRedSandstone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsRedwood, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsReed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsIbbinia, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsRotten, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsScorchedStone, 4), "X  ", "XX ", "XXX", 'X', GOTBlocks.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsStone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsStoneBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsStoneBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsThatch, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 0));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam9, 3, 2), "X", "X", "X", 'X', new ItemStack(GOTBlocks.planks3, 1, 6)));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsUlthos, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsWeirwood, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsWhiteSandstone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.whiteSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.stairsWillow, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.thatch, 4, 1), "XX", "XX", 'X', GOTBlocks.driedReeds);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.thatch, 6, 0), "XYX", "YXY", "XYX", 'X', Items.wheat, 'Y', Blocks.dirt);
		GameRegistry.addRecipe(new ItemStack(GOTItems.valyrianBoots), "X X", "X X", 'X', GOTItems.valyrianIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.valyrianChestplate), "X X", "XXX", "XXX", 'X', GOTItems.valyrianIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.valyrianHelmet), "XXX", "X X", 'X', GOTItems.valyrianIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.valyrianHorseArmor), "X  ", "XYX", "XXX", 'X', GOTItems.valyrianIngot, 'Y', Items.leather);
		GameRegistry.addRecipe(new ItemStack(GOTItems.valyrianIngot), "XXX", "XXX", "XXX", 'X', GOTItems.valyrianNugget);
		GameRegistry.addRecipe(new ItemStack(GOTItems.valyrianLeggings), "XXX", "X X", "X X", 'X', GOTItems.valyrianIngot);
		GameRegistry.addRecipe(new ItemStack(GOTItems.valyrianRing), "XXX", "X X", "XXX", 'X', GOTItems.valyrianNugget);
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 13), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStone1, 6, 14), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStone2, 6, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStone2, 6, 3), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 14), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.whiteSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 6), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 7), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick4, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallClayTile, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 0), "XXX", "XXX", 'X', new ItemStack(Blocks.stone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 1), "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 3), "XXX", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 4), "XXX", "XXX", 'X', new ItemStack(Blocks.sandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 5), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.redSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 6), "XXX", "XXX", 'X', new ItemStack(Blocks.brick_block, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 7), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallStoneV, 6, 8), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.redBrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTBlocks.whiteSandstone, 1, 0), "XX", "XX", 'X', new ItemStack(GOTBlocks.whiteSand, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTItems.whiteWalkersBoots), "X X", "X X", 'X', GOTItems.iceShard);
		GameRegistry.addRecipe(new ItemStack(GOTItems.whiteWalkersChestplate), "X X", "XXX", "XXX", 'X', GOTItems.iceShard);
		GameRegistry.addRecipe(new ItemStack(GOTItems.whiteWalkersLeggings), "XXX", "X X", "X X", 'X', GOTItems.iceShard);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence_gate, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStoneV, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.aleHorn), "X", "Y", 'X', "horn", 'Y', "ingotTin"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.aleHornGold), "X", "Y", 'X', "horn", 'Y', "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.appleCrumble), "AAA", "BCB", "DDD", 'A', Items.milk_bucket, 'B', "apple", 'C', Items.sugar, 'D', Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.armorStand), " X ", " X ", "YYY", 'X', "stickWood", 'Y', Blocks.stone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.barrel), "XXX", "YZY", "XXX", 'X', "plankWood", 'Y', "ingotIron", 'Z', Items.bucket));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.berryPie), "AAA", "BBB", "CCC", 'A', Items.milk_bucket, 'B', "berry", 'C', Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.birdCage, 1, 0), "YYY", "Y Y", "XXX", 'X', "ingotBronze", 'Y', GOTBlocks.bronzeBars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.birdCage, 1, 1), "YYY", "Y Y", "XXX", 'X', "ingotIron", 'Y', Blocks.iron_bars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.birdCage, 1, 2), "YYY", "Y Y", "XXX", 'X', "ingotSilver", 'Y', GOTBlocks.silverBars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.birdCage, 1, 3), "YYY", "Y Y", "XXX", 'X', "ingotGold", 'Y', GOTBlocks.goldBars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.birdCageWood, 1, 0), "YYY", "Y Y", "XXX", 'X', "plankWood", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.bomb, 4), "XYX", "YXY", "XYX", 'X', Items.gunpowder, 'Y', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.boneBlock, 1, 0), "XX", "XX", 'X', "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.boneBoots), "X X", "X X", 'X', "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.boneChestplate), "X X", "XXX", "XXX", 'X', "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.boneHelmet), "XXX", "X X", 'X', "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.boneLeggings), "XXX", "X X", "X X", 'X', "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.brandingIron), "  X", " Y ", "X  ", 'X', "ingotIron", 'Y', GOTItems.gemsbokHide));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.brandingIron), "  X", " Y ", "X  ", 'X', "ingotIron", 'Y', Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeAxe), "XX", "XY", " Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.bronzeBars, 16), "XXX", "XXX", 'X', "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeCrossbow), "XXY", "ZYX", "YZX", 'X', GOTItems.bronzeIngot, 'Y', "stickWood", 'Z', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeDagger), "X", "Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeHoe), "XX", " Y", " Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzePickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeShovel), "X", "Y", "Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeSpear), "  X", " Y ", "Y  ", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeSword), "X", "X", "Y", 'X', GOTItems.bronzeIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeThrowingAxe), " X ", " YX", "Y  ", 'X', "ingotBronze", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.butterflyJar), "X", "Y", 'X', "plankWood", 'Y', Blocks.glass));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.cargocart), "fcf", "fcf", "wpw", 'f', Blocks.fence, 'c', Blocks.chest, 'p', Blocks.planks, 'w', GOTItems.wheel));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.chain, 8), "X", "X", "X", 'X', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.anonymousMask), "XXX", "X X", 'X', Items.paper));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.chandelier, 2, 0), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.chandelier, 2, 1), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.chandelier, 2, 2), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.chandelier, 2, 3), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.chandelier, 2, 4), " X ", "YZY", 'X', "stickWood", 'Y', Blocks.torch, 'Z', GOTItems.valyrianIngot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.chisel), "XY", 'X', "ingotIron", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.clayMug, 2), "X", "Y", "X", 'X', "ingotTin", 'Y', "clayBall"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.clayPlate, 2), "XX", 'X', "clayBall"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.cobblebrick, 4, 0), "XX", "XX", 'X', Blocks.cobblestone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.commandHorn), "XYX", 'X', "ingotBronze", 'Y', "horn"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.commandSword), "X", "Y", "Z", 'X', "ingotIron", 'Y', "ingotBronze", 'Z', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.commandTable), "XXX", "YYY", "ZZZ", 'X', Items.paper, 'Y', "plankWood", 'Z', "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.crossbowBolt, 4), "X", "Y", "Z", 'X', "ingotBronze", 'Y', "stickWood", 'Z', "feather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.crossbowBolt, 4), "X", "Y", "Z", 'X', "ingotIron", 'Y', "stickWood", 'Z', "feather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.daub, 4), "XYX", "YXY", "XYX", 'X', "stickWood", 'Y', Blocks.dirt));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.daub, 4), "XYX", "YXY", "XYX", 'X', GOTBlocks.driedReeds, 'Y', Blocks.dirt));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorAcacia), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorAlmond), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorApple), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorAramant), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorAspen), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorBanana), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorBaobab), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorBeech), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorBirch), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorCedar), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorCharred), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorCherry), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorChestnut), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorCypress), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorDarkOak), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorDatePalm), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorDragon), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorFir), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorFotinia), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorGreenOak), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorHolly), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorJungle), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorKanuka), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorLarch), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorLemon), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorLime), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorMahogany), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorMango), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorMangrove), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorMaple), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorUlthos), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorOlive), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorOrange), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorPalm), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorPear), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorPine), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorPlum), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorPomegranate), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorRedwood), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorIbbinia), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorRotten), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorSpruce), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorWeirwood), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorWillow), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateAcacia, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateAlmond, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateApple, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateAramant, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateAspen, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateBanana, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateBaobab, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateBeech, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateBirch, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateCedar, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateCharred, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateCherry, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateChestnut, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateCypress, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateDarkOak, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateDatePalm, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateDragon, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateFir, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateFotinia, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateGreenOak, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateHolly, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateJungle, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateKanuka, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateLarch, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateLemon, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateLime, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateMahogany, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateMango, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateMangrove, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateMaple, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateUlthos, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateOlive, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateOrange, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGatePalm, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGatePear, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGatePine, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGatePlum, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGatePomegranate, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateRedwood, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateIbbinia, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateRotten, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planksRotten, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateSpruce, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateWeirwood, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks3, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateWillow, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks2, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.flintDagger), "X", "Y", 'X', Items.flint, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.flintSpear), "  X", " Y ", "Y  ", 'X', Items.flint, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.furBed), "XXX", "YYY", 'X', GOTItems.fur, 'Y', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.fuse, 2), "X", "Y", "Y", 'X', new ItemStack(Items.coal, 1, 32767), 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateBronzeBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.gateGear, 4), " X ", "XYX", " X ", 'X', "ingotIron", 'Y', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateGold, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateIronBars, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateSilver, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateValyrian, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', GOTItems.valyrianIngot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateWooden, 4), "ZYZ", "YXY", "ZYZ", 'X', GOTItems.gateGear, 'Y', "plankWood", 'Z', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateWoodenCross, 4), "YYY", "YXY", "YYY", 'X', GOTItems.gateGear, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.gobletCopper, 2), "X X", " X ", " X ", 'X', "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.gobletGold, 2), "X X", " X ", " X ", 'X', "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.gobletSilver, 2), "X X", " X ", " X ", 'X', "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.gobletWood, 2), "X X", " X ", " X ", 'X', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.goldBars, 16), "XXX", "XXX", 'X', "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.goldHorseArmor), "X  ", "XYX", "XXX", 'X', "ingotGold", 'Y', Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.goldRing), "XXX", "X X", "XXX", 'X', "nuggetGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.grapevine), "X", "X", "X", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.ironBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.ironCrossbow), "XXY", "ZYX", "YZX", 'X', "ingotIron", 'Y', "stickWood", 'Z', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.ironDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.ironHorseArmor), "X  ", "XYX", "XXX", 'X', "ingotIron", 'Y', Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.ironPike), "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.ironSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.ironThrowingAxe), " X ", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.lionBed), "XXX", "YYY", 'X', GOTItems.lionFur, 'Y', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.millstone), "XYX", "XZX", "XXX", 'X', Blocks.cobblestone, 'Y', "ingotBronze", 'Z', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.millstone), "XYX", "XZX", "XXX", 'X', Blocks.cobblestone, 'Y', "ingotIron", 'Z', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.mug, 2), "X", "Y", "X", 'X', "ingotTin", 'Y', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.oreGlowstone), "XXX", "XYX", "XXX", 'X', Items.glowstone_dust, 'Y', new ItemStack(Blocks.stone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.pipe), "X  ", " XX", 'X', Items.clay_ball));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.plowcart), "fff", "php", "wpw", 'f', Blocks.fence, 'h', Items.iron_hoe, 'p', Blocks.planks, 'w', GOTItems.wheel));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.pouch, 1, 0), "X X", "X X", "XXX", 'X', Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.redClay), "XX", "XX", 'X', GOTItems.redClayBall));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.reedBars, 16), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.thatch, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.rhinoArmor), "X  ", "XYX", "XXX", 'X', Items.leather, 'Y', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.rollingPin), "XYX", 'X', "stickWood", 'Y', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.silverBars, 16), "XXX", "XXX", 'X', "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.silverRing), "XXX", "X X", "XXX", 'X', GOTItems.silverNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.bronzeRing), "XXX", "X X", "XXX", 'X', GOTItems.bronzeNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.copperRing), "XXX", "X X", "XXX", 'X', GOTItems.copperNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.skullCup), "X", "Y", 'X', new ItemStack(Items.skull, 1, 0), 'Y', "ingotTin"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.skullStaff), "X", "Y", 'X', Items.skull, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabBoneSingle, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.boneBlock, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingleV, 6, 5), "XXX", 'X', new ItemStack(Blocks.stone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.sling), "XYX", "XZX", " X ", 'X', "stickWood", 'Y', Items.leather, 'Z', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.smoothStoneV, 2, 0), "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsBone, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.boneBlock, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.stoneSpear), "  X", " Y ", "Y  ", 'X', "cobblestone", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.strawBed), "XXX", "YYY", 'X', Items.wheat, 'Y', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.sulfurMatch, 4), "X", "Y", 'X', "sulfur", 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.termiteMound, 1, 1), " X ", "XYX", " X ", 'X', GOTItems.termite, 'Y', Blocks.stone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.thatchFloor, 3), "XX", 'X', new ItemStack(GOTBlocks.thatch, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorAlmond, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorApple, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorAramant, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorAspen, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorBanana, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorBaobab, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorBeech, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorBirch, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorCedar, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorCharred, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorCherry, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorChestnut, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorCypress, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorDarkOak, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorDatePalm, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorDragon, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorFir, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorFotinia, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorGreenOak, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorHolly, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorJungle, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorKanuka, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorLarch, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorLemon, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorLime, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorMahogany, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorMango, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorMangrove, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorMaple, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorUlthos, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorOlive, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorOrange, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorPalm, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorPear, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorPine, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorPlum, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorPomegranate, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorRedwood, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorIbbinia, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorRotten, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorSpruce, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorWeirwood, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorWillow, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.unsmeltery), "X X", "YXY", "ZZZ", 'X', "ingotIron", 'Y', "stickWood", 'Z', Blocks.cobblestone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianAxe), "XX", "XY", " Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.valyrianBars, 16), "XXX", "XXX", 'X', GOTItems.valyrianIngot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianBattleaxe), "XXX", "XYX", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianCrossbow), "XXY", "ZYX", "YZX", 'X', GOTItems.valyrianIngot, 'Y', "stickWood", 'Z', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianDagger), "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianHalberd), " XX", " YX", "Y  ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianHammer), "XYX", "XYX", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianHoe), "XX", " Y", " Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianMattock), "XXX", "XY ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianPickaxe), "XXX", " Y ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianShovel), "X", "Y", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianSpear), "  X", " Y ", "Y  ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.valyrianSword), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallBone, 6, 0), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.boneBlock, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.wasteBlock, 4), "XY", "YZ", 'X', Items.rotten_flesh, 'Y', Blocks.dirt, 'Z', "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', GOTItems.fur, 'Y', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', GOTItems.gemsbokHide, 'Y', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', GOTItems.lionFur, 'Y', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.waterskin, 2), " Y ", "X X", " X ", 'X', Items.leather, 'Y', Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.weaponRack), "X X", "YYY", 'X', "stickWood", 'Y', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.wheel), "sss", "sps", "sss", 's', "stickWood", 'p', Blocks.planks));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.wineGlass, 2), "X X", " X ", " X ", 'X', Blocks.glass));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.woodPlate, 2), "XX", 'X', "logWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.arrow, 4), "X", "Y", "Z", 'X', "arrowTip", 'Y', "stickWood", 'Z', "feather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.arrowFire, 4), " X ", "XYX", " X ", 'X', Items.arrow, 'Y', GOTItems.sulfur));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.arrowPoisoned, 4), " X ", "XYX", " X ", 'X', Items.arrow, 'Y', "poison"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTItems.crossbowBoltPoisoned, 4), " X ", "XYX", " X ", 'X', GOTItems.crossbowBolt, 'Y', "poison"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.brewing_stand, 1), " X ", "YYY", 'X', "stickWood", 'Y', Blocks.cobblestone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.clock), " X ", "XYX", " X ", 'X', "ingotGold", 'Y', "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.compass), " X ", "XYX", " X ", 'X', "ingotIron", 'Y', "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', GOTItems.fur));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", 'X', "stickWood", 'Y', GOTItems.gemsbokHide));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3), "XXX", 'X', GOTBlocks.cornStalk));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3), "XXX", 'X', GOTBlocks.reeds));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", 'X', GOTItems.fur, 'Y', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", 'X', GOTItems.gemsbokHide, 'Y', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", 'X', Items.leather, 'Y', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.wooden_door), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsCatalpa, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam1, 3, 1), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fence, 3, 1), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1), 'Y', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceGateCatalpa, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(GOTBlocks.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.doorCatalpa), "XX", "XX", "XX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorCatalpa, 2), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 1)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.dirt, 1, 0), new ItemStack(Blocks.dirt, 1, 1), Items.wheat_seeds));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.mossy_cobblestone, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0), "vine"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick, 1, 0), "vine"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.planks1, 4, 1), new ItemStack(GOTBlocks.wood1, 1, 1)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTItems.brandingIron), new ItemStack(GOTItems.brandingIron, 1, 32767), "ingotIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTItems.dye, 2, 5), "dyeOrange", "dyeBlack"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTItems.dye, 3, 5), "dyeRed", "dyeYellow", "dyeBlack"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTItems.gammon), Items.cooked_porkchop, "salt"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.mud, 1, 0), new ItemStack(GOTBlocks.mud, 1, 1), Items.wheat_seeds));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTItems.questBook), Items.book, new ItemStack(Items.dye, 1, 1), "nuggetGold"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.redBrick, 1, 0), new ItemStack(Blocks.brick_block, 1, 0), "vine"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTItems.saltedFlesh), Items.rotten_flesh, "salt"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 2), GOTItems.termite));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.bomb, 1, 1), new ItemStack(GOTBlocks.bomb, 1, 0), Items.gunpowder, "ingotIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.bomb, 1, 2), new ItemStack(GOTBlocks.bomb, 1, 1), Items.gunpowder, "ingotIron"));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.alloySteelIngot, 9), new ItemStack(GOTBlocks.blockMetal2, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.alloySteelNugget, 9), GOTItems.alloySteelIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.amber, 9), new ItemStack(GOTBlocks.blockGem, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.amethyst, 9), new ItemStack(GOTBlocks.blockGem, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.blackrootStick, 2), GOTBlocks.blackroot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.bottlePoison), Items.glass_bottle, GOTItems.wildberry);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.bronzeIngot, 1), GOTItems.copperIngot, GOTItems.tinIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.bronzeIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.buttonAndesite), new ItemStack(GOTBlocks.rock, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.buttonBasalt), new ItemStack(GOTBlocks.rock, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.buttonChalk), new ItemStack(GOTBlocks.rock, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.buttonDiorite), new ItemStack(GOTBlocks.rock, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.buttonGranite), new ItemStack(GOTBlocks.rock, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.buttonRhyolite), new ItemStack(GOTBlocks.rock, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.buttonLabradorite), new ItemStack(GOTBlocks.rock, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.copperIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.copperNugget, 9), GOTItems.copperIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.coral, 4), new ItemStack(GOTBlocks.blockGem, 1, 8));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.diamond, 9), new ItemStack(GOTBlocks.blockGem, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 0), new ItemStack(GOTBlocks.essosFlower, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 0), new ItemStack(GOTBlocks.yitiFlower, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 1), new ItemStack(GOTBlocks.yitiFlower, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 2), GOTBlocks.bluebell);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 2), new ItemStack(GOTBlocks.yitiFlower, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 3), new ItemStack(GOTBlocks.clover, 1, 32767));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 1, 4), new ItemStack(Items.coal, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.dye, 2, 0), new ItemStack(GOTBlocks.doubleFlower, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.emerald, 9), new ItemStack(GOTBlocks.blockGem, 1, 9));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.flaxSeeds, 2), GOTBlocks.flaxPlant);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.cucumberSeeds, 2), GOTBlocks.cucumberPlant);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.kebab, 9), new ItemStack(GOTBlocks.kebabBlock, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.leekSoup), Items.bowl, GOTItems.leek, GOTItems.leek, Items.potato);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.mapleSyrup), new ItemStack(GOTBlocks.wood3, 1, 0), Items.bowl);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.marzipan), GOTItems.almond, GOTItems.almond, Items.sugar);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.marzipanChocolate), GOTItems.marzipan, new ItemStack(Items.dye, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.melonSoup), Items.bowl, Items.melon, Items.melon, Items.sugar);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.mushroomPie), Items.egg, Blocks.red_mushroom, Blocks.brown_mushroom);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.obsidianShard, 9), Blocks.obsidian);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.opal, 9), new ItemStack(GOTBlocks.blockGem, 1, 7));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.pearl, 9), new ItemStack(GOTBlocks.blockGem, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.pebble, 4), Blocks.gravel);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.pipeweedSeeds), GOTItems.pipeweedLeaf);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.pipeweedSeeds, 2), GOTBlocks.pipeweedPlant);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 0), new ItemStack(GOTBlocks.wood1, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 10), new ItemStack(GOTBlocks.wood2, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 11), new ItemStack(GOTBlocks.wood2, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 12), new ItemStack(GOTBlocks.wood3, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 13), new ItemStack(GOTBlocks.wood3, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 14), new ItemStack(GOTBlocks.wood3, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 15), new ItemStack(GOTBlocks.wood3, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 2), new ItemStack(GOTBlocks.wood1, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 3), new ItemStack(GOTBlocks.wood1, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 4), new ItemStack(GOTBlocks.fruitWood, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 5), new ItemStack(GOTBlocks.fruitWood, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 6), new ItemStack(GOTBlocks.fruitWood, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 7), new ItemStack(GOTBlocks.fruitWood, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 8), new ItemStack(GOTBlocks.wood2, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks1, 4, 9), new ItemStack(GOTBlocks.wood2, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 0), new ItemStack(GOTBlocks.wood4, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 1), new ItemStack(GOTBlocks.wood4, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 10), new ItemStack(GOTBlocks.wood6, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 11), new ItemStack(GOTBlocks.wood6, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 12), new ItemStack(GOTBlocks.wood7, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 13), new ItemStack(GOTBlocks.wood7, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 14), new ItemStack(GOTBlocks.wood7, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 15), new ItemStack(GOTBlocks.wood7, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 2), new ItemStack(GOTBlocks.wood4, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 3), new ItemStack(GOTBlocks.wood4, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 4), new ItemStack(GOTBlocks.wood5, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 5), new ItemStack(GOTBlocks.wood5, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 6), new ItemStack(GOTBlocks.wood5, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 7), new ItemStack(GOTBlocks.wood5, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 8), new ItemStack(GOTBlocks.wood6, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks2, 4, 9), new ItemStack(GOTBlocks.wood6, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 0), new ItemStack(GOTBlocks.wood8, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 1), new ItemStack(GOTBlocks.wood8, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 2), new ItemStack(GOTBlocks.wood8, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 3), new ItemStack(GOTBlocks.wood8, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 4), new ItemStack(GOTBlocks.wood9, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 5), new ItemStack(GOTBlocks.wood9, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planks3, 4, 6), new ItemStack(GOTBlocks.wood9, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTBlocks.planksRotten, 4, 0), new ItemStack(GOTBlocks.rottenLog, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.rabbitStew), Items.bowl, GOTItems.rabbitCooked, Items.potato, Items.potato);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.ruby, 9), new ItemStack(GOTBlocks.blockGem, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.salt, 9), new ItemStack(GOTBlocks.blockMetal2, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.saltpeter, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 14));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.sapphire, 9), new ItemStack(GOTBlocks.blockGem, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.seedsGrapeRed), GOTItems.grapeRed);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.seedsGrapeWhite), GOTItems.grapeWhite);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.silverIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.silverNugget, 9), GOTItems.silverIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.sulfur, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 13));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.tinIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.topaz, 9), new ItemStack(GOTBlocks.blockGem, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.valyrianIngot, 9), new ItemStack(GOTBlocks.blockMetal1, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.valyrianNugget, 9), GOTItems.valyrianIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.bronzeNugget, 9), GOTItems.bronzeIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.ironNugget, 9), Items.iron_ingot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTItems.yitiSteelIngot, 9), new ItemStack(GOTBlocks.blockMetal2, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(GOTBlocks.essosFlower, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 13), new ItemStack(GOTBlocks.essosFlower, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), GOTBlocks.marigold);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(GOTBlocks.yitiFlower, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 5), new ItemStack(GOTBlocks.essosFlower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(GOTBlocks.yitiFlower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 1), new ItemStack(GOTBlocks.doubleFlower, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 13), new ItemStack(GOTBlocks.doubleFlower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), GOTItems.saltpeter, Blocks.dirt);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 5), new ItemStack(GOTBlocks.doubleFlower, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 4, 1), new ItemStack(GOTBlocks.wood9, 1, 0), new ItemStack(GOTBlocks.wood9, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 8, 15), GOTBlocks.boneBlock);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 2), GOTItems.sulfur, GOTItems.saltpeter, new ItemStack(Items.coal, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.string), GOTItems.flax);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.trapdoorAcacia, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 4)));
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
		addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTItems.robesBoots), "X X", "X X", 'X', Blocks.wool);
		addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTItems.robesChestplate), "X X", "XXX", "XXX", 'X', Blocks.wool);
		addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTItems.robesHelmet), "XXX", "X X", 'X', Blocks.wool);
		addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTItems.robesLeggings), "XXX", "X X", "X X", 'X', Blocks.wool);
		addDyeableWoolRobeRecipes(yiti, new ItemStack(GOTItems.kaftanChestplate), "X X", "XXX", "XXX", 'X', Blocks.wool);
		addDyeableWoolRobeRecipes(yiti, new ItemStack(GOTItems.kaftanLeggings), "XXX", "X X", "X X", 'X', Blocks.wool);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTBlocks.treasureCopper, GOTItems.copperIngot);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTBlocks.treasureGold, Items.gold_ingot);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTBlocks.treasureSilver, GOTItems.silverIngot);
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.bronzeDaggerPoisoned, GOTItems.bronzeDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.bronzeDaggerPoisoned, GOTItems.bronzeDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.ironDaggerPoisoned, GOTItems.ironDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.valyrianDaggerPoisoned, GOTItems.valyrianDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.westerosDaggerPoisoned, GOTItems.westerosDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.wildlingDaggerPoisoned, GOTItems.wildlingDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.essosDaggerPoisoned, GOTItems.essosDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.asshaiDaggerPoisoned, GOTItems.asshaiDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.alloySteelDaggerPoisoned, GOTItems.alloySteelDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.lhazarDaggerPoisoned, GOTItems.lhazarDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.sothoryosDaggerPoisoned, GOTItems.sothoryosDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.yitiDaggerPoisoned, GOTItems.yitiDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.summerDaggerPoisoned, GOTItems.summerDagger, GOTItems.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTItems.valyrianChisel, GOTItems.chisel, GOTItems.valyrianPowder));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeamRotten, 3, 0), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rottenLog, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fenceRotten, 3, 0), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0), 'Y', "stickWood"));
		GameRegistry.addRecipe(new GOTRecipeBanners());
		GameRegistry.addRecipe(new GOTRecipeFeatherDye());
		GameRegistry.addRecipe(new GOTRecipeLeatherHatDye());
		GameRegistry.addRecipe(new GOTRecipeLeatherHatFeather());
		GameRegistry.addRecipe(new GOTRecipePartyHatDye());
		GameRegistry.addRecipe(new GOTRecipePipe());
		GameRegistry.addRecipe(new GOTRecipePoisonDrinks());
		GameRegistry.addRecipe(new GOTRecipePouch());
		for (GOTEnumDyeColor dye : GOTEnumDyeColor.values()) {
			GameRegistry.addShapelessRecipe(new ItemStack(getPowderFromDye(dye), 8), Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, new ItemStack(Items.dye, 1, dye.getDyeDamage()));
		}
		int i;
		for (i = 0; i <= 1; ++i) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam9, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood9, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeamV2, 3, i), "X", "X", "X", 'X', new ItemStack(Blocks.log2, 1, i)));
		}
		for (i = 0; i <= 2; ++i) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTBlocks.bomb, 1, i + 8), new ItemStack(GOTBlocks.bomb, 1, i), Items.lava_bucket));
		}
		for (i = 0; i <= 3; ++i) {
			if (i != 1) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam1, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood1, 1, i)));
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam8, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood8, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam7, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood7, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam2, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood2, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeamFruit, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.fruitWood, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam3, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood3, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam4, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood4, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam5, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood5, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeam6, 3, i), "X", "X", "X", 'X', new ItemStack(GOTBlocks.wood6, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodBeamV1, 3, i), "X", "X", "X", 'X', new ItemStack(Blocks.log, 1, i)));
		}
		for (i = 0; i <= 5; ++i) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence, 3, i), "XYX", "XYX", 'X', new ItemStack(Blocks.planks, 1, i), 'Y', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fence3, 3, i), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks3, 1, i), 'Y', "stickWood"));
		}
		for (i = 0; i <= 7; ++i) {
			GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabClayTileDyedSingle1, 6, i), "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, i));
			GameRegistry.addRecipe(new ItemStack(GOTBlocks.slabClayTileDyedSingle2, 6, i), "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, i + 8));
		}
		for (i = 0; i <= 15; ++i) {
			if (i != 1) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fence, 3, i), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks1, 1, i), 'Y', "stickWood"));
			}
			GameRegistry.addRecipe(new ItemStack(GOTBlocks.wallClayTileDyed, 6, i), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.clayTileDyed, 1, i));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.clayTileDyed, 8, i), "XXX", "XYX", "XXX", 'X', new ItemStack(GOTBlocks.clayTile, 1, 0), 'Y', dyeOreNames[BlockColored.func_150032_b(i)]));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.clayTileDyed, 4, i), "XX", "XX", 'X', new ItemStack(Blocks.stained_hardened_clay, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.fence2, 3, i), "XYX", "XYX", 'X', new ItemStack(GOTBlocks.planks2, 1, i), 'Y', "stickWood"));
			GameRegistry.addRecipe(new ItemStack(GOTBlocks.stainedGlass, 4, i), "XX", "XX", 'X', new ItemStack(Blocks.stained_glass, 1, i));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stainedGlass, 8, i), "XXX", "XYX", "XXX", 'X', GOTBlocks.glass, 'Y', dyeOreNames[BlockColored.func_150031_c(i)]));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTBlocks.stainedGlassPane, 16, i), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.stainedGlass, 1, i)));
		}
		for (GOTBlockFallenLeaves fallenLeafBlock : GOTBlockFallenLeaves.allFallenLeaves) {
			for (Block leafBlock : fallenLeafBlock.getLeafBlocks()) {
				if (!(leafBlock instanceof GOTBlockLeavesBase)) {
					continue;
				}
				String[] leafNames = ((GOTBlockLeavesBase) leafBlock).getAllLeafNames();
				for (int leafMeta = 0; leafMeta < leafNames.length; ++leafMeta) {
					Object[] fallenBlockMeta = GOTBlockFallenLeaves.fallenBlockMetaFromLeafBlockMeta(leafBlock, leafMeta);
					if (fallenBlockMeta == null) {
						continue;
					}
					Block fallenBlock = (Block) fallenBlockMeta[0];
					int fallenMeta = (Integer) fallenBlockMeta[1];
					if (fallenBlock == null) {
						continue;
					}
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fallenBlock, 6, fallenMeta), "XXX", 'X', new ItemStack(leafBlock, 1, leafMeta)));
				}
			}
		}
	}

	public static void createStormlandsRecipes() {
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableStormlands), "XX", "XX", 'X', "plankWood"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.stormlandsBoots), "X X", "X X", 'X', "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.RENLY.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		stormlands.addAll(commonWesteros);
		stormlands.addAll(tinyBasalt);
	}

	public static void createSummerRecipes() {
		summer.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableSummer), "XX", "XX", 'X', "plankWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerHelmet), "XXX", "X X", 'X', "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerBoots), "X X", "X X", 'X', "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.SUMMER.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.summerPike), "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTItems.essosBow), " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string));
	}

	public static void createTinyBasaltRecipes() {
		tinyBasalt.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick2, 4, 11), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		tinyBasalt.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick4, 1, 6), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11)));
		tinyBasalt.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar1, 3, 9), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 0)));
		tinyBasalt.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11)));
		tinyBasalt.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle5, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.pillar1, 1, 9)));
		tinyBasalt.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone2, 6, 10), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11)));
		tinyBasalt.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsBasaltWesterosBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick2, 1, 11)));
	}

	public static void createTyroshRecipes() {
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableTyrosh), "XX", "XX", 'X', "plankWood"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshHelmet), "XXX", "X X", 'X', "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyroshBoots), "X X", "X X", 'X', "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.TYROSH.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		tyrosh.addAll(commonEssos);
		tyrosh.addAll(tinyBasalt);
	}

	public static void createUnsmeltingRecipes() {
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.renlyHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.renlyChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.renlyLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.renlyBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.blacksmithHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldHelmet), "XXX", "X X", 'X', "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.goldBoots), "X X", "X X", 'X', "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.redkingHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.redkingChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.redkingLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.redkingBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.westkingHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.westkingChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.westkingLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.westkingBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackfyreHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackfyreChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackfyreLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackfyreBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.royceHelmet), "XXX", "X X", 'X', GOTItems.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.royceChestplate), "X X", "XXX", "XXX", 'X', GOTItems.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.royceLeggings), "XXX", "X X", "X X", 'X', GOTItems.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.royceBoots), "X X", "X X", 'X', GOTItems.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.baelishDagger), "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.boltonDagger), "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.euronDagger), "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyeneDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.gregorCleganeSword), "XX ", "XX ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.ice), "XX ", "XX ", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.areoHotahAxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.victarionAxe), "XXX", "XYX", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.celtigarAxe), "XXX", "XYX", " Y ", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.tyrionAxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.gendryHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.robertHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.daarioArakh), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.darkstar), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.dawn), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.bericSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.eddardSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.jaimeSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.needle), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.nightKingSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.obaraSpear), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.rhaegarSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.robbSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.sandorCleganeSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.sunspear), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.tormundSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.trystaneSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.katana), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackArakh), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.blackfyre), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.brightroar), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.darkSister), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.heartsbane), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.justMaid), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.ladyForlorn), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.lamentation), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.lightbringer), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.longclaw), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.nightfall), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.oathkeeper), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.orphanMaker), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.redRain), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.truth), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.vigilance), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTItems.widowWail), "X", "X", "Y", 'X', GOTItems.valyrianIngot, 'Y', "stickWood"));
	}

	public static void createVolantisRecipes() {
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableVolantis), "XX", "XX", 'X', "plankWood"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisHelmet), "XXX", "X X", 'X', "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTItems.volantisBoots), "X X", "X X", 'X', "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.VOLANTIS.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		volantis.addAll(commonEssos);
		volantis.addAll(tinyBasalt);
	}

	public static void createWesterlandsRecipes() {
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardHelmet), "XXX", "X X", 'X', GOTItems.alloySteelIngot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardChestplate), "X X", "XXX", "XXX", 'X', GOTItems.alloySteelIngot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardLeggings), "XXX", "X X", "X X", 'X', GOTItems.alloySteelIngot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsguardBoots), "X X", "X X", 'X', GOTItems.alloySteelIngot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableWesterlands), "XX", "XX", 'X', "plankWood"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsHelmet), "XXX", "X X", 'X', "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.westerlandsBoots), "X X", "X X", 'X', "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.LANNISTER.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		westerlands.addAll(commonWesteros);
		westerlands.addAll(tinyBasalt);
	}

	public static void createWildlingRecipes() {
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableWildling), "XX", "XX", 'X', "plankWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.wildlingSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.wildlingBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.wildlingDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.wildlingSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.wildlingAxe), "XX", "XY", " Y", 'X', "ingotIron", 'Y', "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.wildlingHammer), "XYX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.WILDLING.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.wildlingPolearm), " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.THENN.bannerID), "XA", "Y ", "Z ", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood", 'A', "stickWood"));
	}

	public static void createWoodenSlabRecipes() {
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 0)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 2)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 3)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 4)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 5)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 6)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle1, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 7)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 8)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 9)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 10)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 11)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 12)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 13)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 14)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle2, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks1, 1, 15)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 0)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 1)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 2)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 3)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 4)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 5)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 6)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle3, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 7)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 8)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 9)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 10)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 11)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 12)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 13)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 14)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle4, 6, 7), "XXX", 'X', new ItemStack(GOTBlocks.planks2, 1, 15)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 0)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 1)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 2)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 3)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 4)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 5)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.woodSlabSingle5, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.planks3, 1, 6)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.rottenSlabSingle, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.planksRotten, 1, 0)));
	}

	public static void createYiTiRecipes() {
		yiti.add(new GOTRecipeRobesDye(GOTMaterial.KAFTAN));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.YITI.bannerID), "X", "Y", "Z", 'X', Blocks.wool, 'Y', "stickWood", 'Z', "plankWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick5, 1, 12), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick5, 4, 11), "XX", "XX", 'X', new ItemStack(Blocks.stone, 1, 0)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 0), " X ", "XYX", " X ", 'X', "nuggetGold", 'Y', new ItemStack(GOTBlocks.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 1, 2), "XX", "XX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.brick6, 4, 1), "XX", "XX", 'X', new ItemStack(GOTBlocks.rock, 1, 4)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.gateYiTi, 4), "ZYZ", "YXY", "ZYZ", 'X', GOTItems.gateGear, 'Y', "plankWood", 'Z', GOTItems.yitiSteelIngot));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 8), "X", "X", "X", 'X', new ItemStack(Blocks.stone, 1, 0)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.pillar2, 3, 9), "X", "X", "X", 'X', new ItemStack(GOTBlocks.rock, 1, 4)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 1), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 13)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 2), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 14)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 3), "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 15)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 4), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 8)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 5), "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.slabSingle12, 6, 6), "XXX", 'X', new ItemStack(GOTBlocks.pillar2, 1, 9)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrick, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickCracked, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 14)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickFlowers, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 15)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickMossy, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 13)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.stairsYiTiBrickRed, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone3, 6, 15), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 10), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 13)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 11), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 14)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 12), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick5, 1, 15)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.wallStone4, 6, 13), "XXX", "XXX", 'X', new ItemStack(GOTBlocks.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiBattleaxe), "XXX", "XYX", " Y ", 'X', "ingotIron", 'Y', "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiBoots), "X X", "X X", 'X', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiBootsFrontier), "X X", "Y Y", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiBootsSamurai), "Y Y", "X X", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiBow), " XY", "X Y", " XY", 'X', "stickWood", 'Y', Items.string));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiChestplate), "X X", "XXX", "XXX", 'X', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiChestplateFrontier), "X X", "YXY", "XYX", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiChestplateSamurai), "X X", "XYX", "YXY", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiDagger), "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiHelmet), "XXX", "X X", 'X', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiHelmetFrontier), "XYX", "Y Y", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiHelmetSamurai), "YYY", "X X", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiHelmetShogune), "XYX", 'X', GOTItems.whiteBisonHorn, 'Y', new ItemStack(GOTItems.yitiHelmetSamurai, 1, 0)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiHorseArmor), "X  ", "XXX", "XXX", 'X', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiLeggings), "XXX", "X X", "X X", 'X', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiLeggingsFrontier), "XXX", "Y Y", "X X", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiLeggingsSamurai), "XXX", "X X", "Y Y", 'X', GOTItems.yitiSteelIngot, 'Y', "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiPike), "  X", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiPolearm), " XX", " YX", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiSpear), "  X", " Y ", "Y  ", 'X', "ingotIron", 'Y', "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTItems.yitiSword), "X", "X", "Y", 'X', "ingotIron", 'Y', "stickWood"));
		yiti.add(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick5, 1, 13), new ItemStack(GOTBlocks.brick5, 1, 11), "vine"));
		yiti.add(new ShapelessOreRecipe(new ItemStack(GOTBlocks.brick5, 1, 15), new ItemStack(GOTBlocks.brick5, 1, 11), new ItemStack(GOTBlocks.yitiFlower, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTBlocks.tableYiTi), "XX", "XX", 'X', "plankWood"));
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

	public static GOTBlockConcretePowder getPowderFromDye(GOTEnumDyeColor dye) {
		return GOTBlocks.concretePowder.get(dye);
	}

	public static void modifyStandardRecipes() {
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence));
		removeRecipesItemStack(recipeList, new ItemStack(Blocks.sandstone, 1, 2));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.sandstone, 2, 2), "X", "X", 'X', new ItemStack(Blocks.sandstone, 1, 0)));

		for (int i = 0; i <= 5; ++i) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence, 3, i), "XYX", "XYX", 'X', new ItemStack(Blocks.planks, 1, i), 'Y', "stickWood"));
		}
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence_gate));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence_gate, 1), "XYX", "XYX", 'X', "stickWood", 'Y', new ItemStack(Blocks.planks, 1, 0)));
		removeRecipesItem(recipeList, Items.wooden_door);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.wooden_door), "XX", "XX", "XX", 'X', new ItemStack(Blocks.planks, 1, 0)));
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.trapdoor));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.trapdoor, 2), "XXX", "XXX", 'X', new ItemStack(Blocks.planks, 1, 0)));
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", 'X', new ItemStack(GOTBlocks.smoothStoneV, 1, 0)));
		removeRecipesItemStack(recipeList, new ItemStack(Blocks.stone_slab, 1, 5));
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.stone_brick_stairs));
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "X  ", "XX ", "XXX", 'X', new ItemStack(Blocks.stonebrick, 1, 0));
		removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.anvil));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.anvil), "XXX", " Y ", "XXX", 'X', "ingotIron", 'Y', "blockIron"));
		removeRecipesClass(recipeList, RecipesArmorDyes.class);
		GameRegistry.addRecipe(new GOTRecipeArmorDyes());
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
		RecipeSorter.register("got:partyHatDye", GOTRecipePartyHatDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		modifyStandardRecipes();
		createStandardRecipes();
		createCommonWesterosRecipes();
		createCommonEssosRecipes();
		createTinyBasaltRecipes();
		createWoodenSlabRecipes();
		CraftingManager.getInstance().getRecipeList().addAll(0, slab);
		createSmeltingRecipes();
		createNorthRecipes();
		createHillmanRecipes();
		createWildlingRecipes();
		createSummerRecipes();
		createGiftRecipes();
		createLhazarRecipes();
		createSothoryosRecipes();
		createYiTiRecipes();
		createIbbenRecipes();
		createUnsmeltingRecipes();
		createArrynRecipes();
		createDorneRecipes();
		createReachRecipes();
		createWesterlandsRecipes();
		createCrownlandsRecipes();
		createStormlandsRecipes();
		createDragonstoneRecipes();
		createRiverlandsRecipes();
		createIronbornRecipes();
		createVolantisRecipes();
		createBraavosRecipes();
		createLysRecipes();
		createMyrRecipes();
		createTyroshRecipes();
		createPentosRecipes();
		createNorvosRecipes();
		createLorathRecipes();
		createQohorRecipes();
		createQarthRecipes();
		createGhiscarRecipes();
		createAsshaiRecipes();
		createJogosRecipes();
		createDothrakiRecipes();
		createMossovyRecipes();
	}

	public static void registerOres() {
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
		OreDictionary.registerOre("stickWood", GOTItems.blackrootStick);
		for (Object obj : Item.itemRegistry) {
			Item item = (Item) obj;
			if (item == Items.bone || item instanceof GOTItemBone) {
				OreDictionary.registerOre("bone", item);
			}
			if (!(item instanceof GOTItemBerry)) {
				continue;
			}
			OreDictionary.registerOre("berry", item);
		}
		OreDictionary.registerOre("oreCopper", GOTBlocks.oreCopper);
		OreDictionary.registerOre("oreTin", GOTBlocks.oreTin);
		OreDictionary.registerOre("oreSilver", GOTBlocks.oreSilver);
		OreDictionary.registerOre("oreCobalt", GOTItems.cobaltIngot);
		OreDictionary.registerOre("oreSulfur", GOTBlocks.oreSulfur);
		OreDictionary.registerOre("oreSaltpeter", GOTBlocks.oreSaltpeter);
		OreDictionary.registerOre("oreSalt", GOTBlocks.oreSalt);
		OreDictionary.registerOre("ingotCopper", GOTItems.copperIngot);
		OreDictionary.registerOre("ingotTin", GOTItems.tinIngot);
		OreDictionary.registerOre("ingotBronze", GOTItems.bronzeIngot);
		OreDictionary.registerOre("ingotSilver", GOTItems.silverIngot);
		OreDictionary.registerOre("nuggetSilver", GOTItems.silverNugget);
		OreDictionary.registerOre("nuggetCopper", GOTItems.copperNugget);
		OreDictionary.registerOre("nuggetAlloySteel", GOTItems.alloySteelNugget);
		OreDictionary.registerOre("sulfur", GOTItems.sulfur);
		OreDictionary.registerOre("saltpeter", GOTItems.saltpeter);
		OreDictionary.registerOre("salt", GOTItems.salt);
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
		OreDictionary.registerOre("feather", Items.feather);
		OreDictionary.registerOre("feather", GOTItems.swanFeather);
		OreDictionary.registerOre("horn", GOTItems.rhinoHorn);
		OreDictionary.registerOre("horn", GOTItems.whiteBisonHorn);
		OreDictionary.registerOre("horn", GOTItems.horn);
		OreDictionary.registerOre("arrowTip", Items.flint);
		OreDictionary.registerOre("arrowTip", GOTItems.rhinoHorn);
		OreDictionary.registerOre("arrowTip", GOTItems.whiteBisonHorn);
		OreDictionary.registerOre("arrowTip", GOTItems.horn);
		OreDictionary.registerOre("poison", GOTItems.bottlePoison);
		OreDictionary.registerOre("vine", Blocks.vine);
		OreDictionary.registerOre("vine", GOTBlocks.willowVines);
		OreDictionary.registerOre("vine", GOTBlocks.mirkVines);
	}

	public static void removeRecipesClass(Collection<IRecipe> recipeList, Class<? extends IRecipe> recipeClass) {
		Collection<IRecipe> recipesToRemove = new ArrayList<>();
		for (IRecipe recipe : recipeList) {
			if (!recipeClass.isAssignableFrom(recipe.getClass())) {
				continue;
			}
			recipesToRemove.add(recipe);
		}
		recipeList.removeAll(recipesToRemove);
	}

	public static void removeRecipesItem(Collection<IRecipe> recipeList, Item outputItem) {
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

	public static void removeRecipesItemStack(Collection<IRecipe> recipeList, ItemStack outputItemStack) {
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

}