package got.common.recipe;

import java.lang.reflect.Field;
import java.util.*;

import cpw.mods.fml.common.registry.GameRegistry;
import got.common.GOTConfig;
import got.common.block.leaves.GOTBlockLeavesBase;
import got.common.block.other.*;
import got.common.block.planks.GOTBlockPlanksBase;
import got.common.block.sapling.GOTBlockSaplingBase;
import got.common.block.slab.GOTBlockSlabBase;
import got.common.block.wood.GOTBlockWoodBase;
import got.common.database.*;
import got.common.item.other.*;
import got.common.util.GOTEnumDyeColor;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.world.World;
import net.minecraftforge.oredict.*;

public class GOTRecipe {
	public static List<IRecipe> slab = new ArrayList<>();
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
	public static List<IRecipe> commonWesteros = new ArrayList<>();
	public static List<IRecipe> commonEssos = new ArrayList<>();
	public static String[] dyeOreNames = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
	public static List<IRecipe> mossovy = new ArrayList<>();

	public static void addDyeableWoolRobeRecipes(List recipeList, ItemStack result, Object... params) {
		for (int i = 0; i <= 15; ++i) {
			Object[] paramsDyed = Arrays.copyOf(params, params.length);
			ItemStack wool = new ItemStack(Blocks.wool, 1, i);
			for (int l = 0; l < paramsDyed.length; ++l) {
				Object param = paramsDyed[l];
				if (param instanceof Block && (Block) param == Block.getBlockFromItem(wool.getItem())) {
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

	public static void addDyeableWoolRobeRecipes(List[] recipeLists, ItemStack result, Object... params) {
		for (List list : recipeLists) {
			GOTRecipe.addDyeableWoolRobeRecipes(list, result, params);
		}
	}

	public static void addRecipeTo(List[] recipeLists, IRecipe recipe) {
		for (List list : recipeLists) {
			list.add(recipe);
		}
	}

	public static void addSmeltingXPForItem(Item item, float xp) {
		try {
			Field field = FurnaceRecipes.class.getDeclaredFields()[2];
			field.setAccessible(true);
			HashMap map = (HashMap) field.get(FurnaceRecipes.smelting());
			map.put(new ItemStack(item, 1, 32767), Float.valueOf(xp));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean checkItemEquals(ItemStack target, ItemStack input) {
		return target.getItem().equals(input.getItem()) && (target.getItemDamage() == 32767 || target.getItemDamage() == input.getItemDamage());
	}

	public static void createArrynRecipes() {
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynguardHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynguardChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynguardLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynguardBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableArryn), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrynBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		arryn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.ARRYN.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		arryn.addAll(commonWesteros);
	}

	public static void createAsshaiRecipes() {
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick1, 4, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableAsshai), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle10, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 0), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBasaltBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBasaltBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 9), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.chandelier, 2, 12), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), GOTRegistry.fuseItem, Character.valueOf('Z'), "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 7), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle5, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 7)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick2, 1, 10), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBasalt, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiBow), " XY", "X Y", " XY", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.string));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiDagger), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiTorch), "X", "Y", Character.valueOf('X'), Items.coal, Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiStaff), "  Y", " Y ", "Y  ", Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiMask), "XXX", "X X", Character.valueOf('X'), "plankWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildFireJar), "XYX", "YZY", "XYX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.firePot, 4), "Z", "Y", "X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.skullStaff), "X", "Y", "Y", Character.valueOf('X'), Items.skull, Character.valueOf('Y'), "stickWood"));
		asshai.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.ASSHAI.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
	}

	public static void createBraavosRecipes() {
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableBraavos), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.braavosHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.braavosChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.braavosLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.braavosBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		braavos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.BRAAVOS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		braavos.addAll(commonEssos);
	}

	public static void createCommonEssosRecipes() {
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosHorseArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick1, 4, 15), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 0)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle4, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 15)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSandstoneBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 15)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 15), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 15)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 5), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 0)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle4, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 5)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick3, 1, 8), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 15)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle7, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 11)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSandstoneBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 11)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone3, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 11)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick3, 4, 13), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.redSandstone, 1, 0)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle7, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 13)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSandstoneBrickRed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 13)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone3, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 13)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick3, 1, 15), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 13)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle7, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 14)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSandstoneBrickRedCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 14)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone3, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 1, 14)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 15), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.redSandstone, 1, 0)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle7, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 15)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick4, 1, 7), " X ", "XYX", " X ", Character.valueOf('X'), "gemLapis", Character.valueOf('Y'), new ItemStack(GOTRegistry.brick1, 1, 15)));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateEssos, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.kebabStandSand), " X ", " Y ", "ZZZ", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Blocks.sandstone));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.kebabStand), " X ", " Y ", "ZZZ", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Blocks.cobblestone));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.shishKebab, 2), "  X", " X ", "Y  ", Character.valueOf('X'), GOTRegistry.kebab, Character.valueOf('Y'), "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosDagger), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosHammer), " XX", " XX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosPike), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonEssos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.essosPolearm), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonEssos.add(new GOTRecipeRobesDye(GOTMaterial.ROBES));
	}

	public static void createCommonWesterosRecipes() {
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.beacon), "XXX", "XXX", "YYY", Character.valueOf('X'), "logWood", Character.valueOf('Y'), Blocks.cobblestone));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick1, 1, 5), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick1, 4, 1), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick2, 4, 11), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick4, 1, 6), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 11)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateWesteros, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 6), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 9), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 2)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 3)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle11, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle5, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 6)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle5, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 11)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle5, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 9)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 1), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsAndesite, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsAndesiteBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsAndesiteBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 3)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsAndesiteBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 2)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBasaltWesterosBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 11)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 1)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 2)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 3)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone2, 6, 10), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 11)));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosDagger), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosHorseArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosLance), "  X", " X ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosPike), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonWesteros.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerosSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		commonWesteros.add(new ShapelessOreRecipe(new ItemStack(GOTRegistry.brick1, 1, 2), new ItemStack(GOTRegistry.brick1, 1, 1), "vine"));
	}

	public static void createCrownlandsRecipes() {
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.kingsguardHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.kingsguardChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.kingsguardLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.kingsguardBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableCrownlands), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.crownlandsHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.crownlandsChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.crownlandsLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.crownlandsBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.ROBERT.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick6, 1, 3), "XXX", "XYX", "XXX", Character.valueOf('X'), GOTRegistry.westerosSword, Character.valueOf('Y'), Items.lava_bucket));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick6, 1, 3), "XXX", "XYX", "XXX", Character.valueOf('X'), Items.iron_sword, Character.valueOf('Y'), Items.lava_bucket));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildFireJar), "XYX", "YZY", "XYX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		crownlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.firePot, 4), "Z", "Y", "X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		crownlands.addAll(commonWesteros);
	}

	public static void createDorneRecipes() {
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableDorne), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dorneHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dorneChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dorneLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dorneBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		dorne.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.MARTELL.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		dorne.addAll(commonWesteros);
	}

	public static void createDothrakiRecipes() {
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dothrakiHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dothrakiChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dothrakiLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dothrakiBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.driedReeds));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		dothraki.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
	}

	public static void createDragonstoneRecipes() {
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableDragonstone), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dragonstoneHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dragonstoneChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dragonstoneLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dragonstoneBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		dragonstone.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.STANNIS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		dragonstone.addAll(commonWesteros);
	}

	public static void createGhiscarRecipes() {
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableGhiscar), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ghiscarHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ghiscarChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ghiscarLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ghiscarBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.GHISCAR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		ghiscar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.harpy), "XXX", "X X", Character.valueOf('X'), Items.gold_ingot));
		ghiscar.addAll(commonEssos);
	}

	public static void createGiftRecipes() {
		gift.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableGift), "XX", "XX", Character.valueOf('X'), "plankWood"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.giftHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.giftChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.giftLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.giftBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		gift.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.NIGHT.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		gift.addAll(commonWesteros);
	}

	public static void createHillmanRecipes() {
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableHillTribes), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), Blocks.cobblestone));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.hillmenHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.hillmenChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.hillmenLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.hillmenBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.HILLMEN.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.club), "X", "X", "X", Character.valueOf('X'), "plankWood"));
		hillmen.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.trident), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		hillmen.addAll(commonWesteros);
	}

	public static void createIbbenRecipes() {
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ibbenChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ibbenLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ibbenBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ibbenSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ibbenHarpoon), "  X", " Y ", "YZ ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.club), "X", "X", "X", Character.valueOf('X'), "plankWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.trident), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableIbben), "XX", "XX", Character.valueOf('X'), "plankWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle11, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 2), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick1, 4, 4), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsRhyoliteBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.flintSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.flintDagger), "X", "Y", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.IBBEN.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 8), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle5, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 8)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsRhyolite, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 2)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick5, 1, 3), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 4)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeamS, 3, 0), "X", "X", "X", Character.valueOf('X'), "logWood"));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeamS, 1, 1), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(GOTRegistry.woodBeamS, 1, 0)));
		ibben.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateIbben, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
	}

	public static void createIronbornRecipes() {
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableIronborn), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironbornHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironbornChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironbornLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironbornBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		ironborn.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.GREYJOY.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		ironborn.addAll(commonWesteros);
	}

	public static void createJogosRecipes() {
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.JOGOS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.jogosHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.jogosChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.jogosLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.jogosBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		jogos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nomadBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
	}

	public static void createLhazarRecipes() {
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableLhazar), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), GOTRegistry.redClay));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.LHAZAR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.gemsbokHide));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick3, 4, 10), "XX", "XX", Character.valueOf('X'), GOTRegistry.redClay));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle7, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 4, 10)));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsLhazarBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 4, 10)));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone2, 6, 15), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick3, 4, 10)));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.rhinoHorn, Character.valueOf('Y'), "stickWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.rhinoHorn, Character.valueOf('Y'), "stickWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarSpear), "  X", " X ", "X  ", Character.valueOf('X'), GOTRegistry.rhinoHorn));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarHelmetLion), "XXX", "X X", Character.valueOf('X'), GOTRegistry.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarChestplateLion), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarLeggingsLion), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarBootsLion), "X X", "X X", Character.valueOf('X'), GOTRegistry.lionFur));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarClub), "X", "X", "X", Character.valueOf('X'), "plankWood"));
		lhazar.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lhazarSword), "X", "X", "Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
	}

	public static void createLorathRecipes() {
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableLorath), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lorathHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lorathChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lorathLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lorathBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		lorath.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.LORATH.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		lorath.addAll(commonEssos);
	}

	public static void createLysRecipes() {
		lys.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableLys), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lysHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lysChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lysLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lysBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		lys.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.LYS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		lys.addAll(commonEssos);
	}

	public static void createMossovyRecipes() {
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableMossovy), "XX", "XX", Character.valueOf('X'), "plankWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.mossovyChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.mossovyLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.mossovyBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.mossovySword), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.silverIngot, Character.valueOf('Y'), "stickWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.MOSSOVY.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick1, 4, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle10, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 0), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle1, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBasaltBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 7)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBasaltBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 7)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 9), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 7)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.chandelier, 2, 12), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), GOTRegistry.fuseItem, Character.valueOf('Z'), "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 7), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle5, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 7)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick2, 1, 10), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBasalt, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0)));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiTorch), "X", "Y", Character.valueOf('X'), Items.coal, Character.valueOf('Y'), "stickWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.asshaiBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.skullStaff), "X", "Y", "Y", Character.valueOf('X'), Items.skull, Character.valueOf('Y'), "stickWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.mossovySword), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.silverIngot, Character.valueOf('Y'), "stickWood"));
		mossovy.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.mossovyDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.silverIngot, Character.valueOf('Y'), "stickWood"));
	}

	public static void createMyrRecipes() {
		myr.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableMyr), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.myrHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.myrChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.myrLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.myrBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		myr.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.MYR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		myr.addAll(commonEssos);
	}

	public static void createNorthRecipes() {
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northguardHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northguardChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northguardLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northguardBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableNorth), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.northBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		north.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.ROBB.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		north.addAll(commonWesteros);
	}

	public static void createNorvosRecipes() {
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableNorvos), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.norvosHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.norvosChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.norvosLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.norvosBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		norvos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.NORVOS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		norvos.addAll(commonEssos);
	}

	public static void createPentosRecipes() {
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tablePentos), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pentosHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pentosChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pentosLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pentosBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		pentos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.PENTOS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		pentos.addAll(commonEssos);
	}
	
	public static void createQarthRecipes() {
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableQarth), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qarthHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qarthChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qarthLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qarthBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.QARTH.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildFireJar), "XYX", "YZY", "XYX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.firePot, 4), "Z", "Y", "X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		qarth.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.skullStaff), "X", "Y", "Y", Character.valueOf('X'), Items.skull, Character.valueOf('Y'), "stickWood"));
		qarth.addAll(commonEssos);
	}

	public static void createQohorRecipes() {
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsulliedBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableQohor), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qohorHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qohorChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qohorLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.qohorBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		qohor.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.QOHOR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		qohor.addAll(commonEssos);
	}

	public static void createReachRecipes() {
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachguardHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachguardChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachguardLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachguardBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableReach), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.reachBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		reach.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.TYRELL.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		reach.addAll(commonWesteros);
	}

	public static void createRiverlandsRecipes() {
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableRiverlands), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.riverlandsHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.riverlandsChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.riverlandsLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.riverlandsBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		riverlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.TULLY.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		riverlands.addAll(commonWesteros);
	}

	public static void createSmeltingRecipes() {
		for (Object obj : Block.blockRegistry) {
			Block block = (Block) obj;
			if (!(block instanceof GOTBlockWoodBase)) {
				continue;
			}
			GameRegistry.addSmelting(block, new ItemStack(Items.coal, 1, 1), 0.15f);
		}
		GameRegistry.addSmelting(Blocks.stone, new ItemStack(GOTRegistry.scorchedStone), 0.1f);
		GameRegistry.addSmelting(GOTRegistry.rock, new ItemStack(GOTRegistry.scorchedStone), 0.1f);
		GameRegistry.addSmelting(GOTRegistry.whiteSand, new ItemStack(Blocks.glass), 0.1f);
		GameRegistry.addSmelting(GOTRegistry.oreCopper, new ItemStack(GOTRegistry.copperIngot), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.oreTin, new ItemStack(GOTRegistry.tinIngot), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.oreSilver, new ItemStack(GOTRegistry.silverIngot), 0.8f);
		GameRegistry.addSmelting(GOTRegistry.oreCobalt, new ItemStack(GOTRegistry.cobaltIngot), 0.8f);
		GameRegistry.addSmelting(GOTRegistry.oreGlowstone, new ItemStack(Items.glowstone_dust), 1.0f);
		GameRegistry.addSmelting(GOTRegistry.oreSulfur, new ItemStack(GOTRegistry.sulfur), 1.0f);
		GameRegistry.addSmelting(GOTRegistry.oreSaltpeter, new ItemStack(GOTRegistry.saltpeter), 1.0f);
		GameRegistry.addSmelting(GOTRegistry.oreSalt, new ItemStack(GOTRegistry.salt), 1.0f);
		GameRegistry.addSmelting(GOTRegistry.clayMug, new ItemStack(GOTRegistry.ceramicMug), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.clayPlate, new ItemStack(GOTRegistry.ceramicPlate), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.ceramicPlate, new ItemStack(GOTRegistry.plate), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.redClayBall, new ItemStack(Items.brick), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.redClay, new ItemStack(Blocks.hardened_clay), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.rabbitRaw, new ItemStack(GOTRegistry.rabbitCooked), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.lionRaw, new ItemStack(GOTRegistry.lionCooked), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.zebraRaw, new ItemStack(GOTRegistry.zebraCooked), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.rhinoRaw, new ItemStack(GOTRegistry.rhinoCooked), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.muttonRaw, new ItemStack(GOTRegistry.muttonCooked), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.deerRaw, new ItemStack(GOTRegistry.deerCooked), 0.35f);
		GameRegistry.addSmelting(GOTRegistry.camelRaw, new ItemStack(GOTRegistry.camelCooked), 0.35f);
		GameRegistry.addSmelting(new ItemStack(GOTRegistry.reeds, 1, 0), new ItemStack(GOTRegistry.driedReeds), 0.25f);
		GameRegistry.addSmelting(GOTRegistry.pipeweedLeaf, new ItemStack(GOTRegistry.pipeweed), 0.25f);
		GameRegistry.addSmelting(GOTRegistry.chestnut, new ItemStack(GOTRegistry.chestnutRoast), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.corn, new ItemStack(GOTRegistry.cornCooked), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.turnip, new ItemStack(GOTRegistry.turnipCooked), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.yam, new ItemStack(GOTRegistry.yamRoast), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.grapeRed, new ItemStack(GOTRegistry.raisins), 0.3f);
		GameRegistry.addSmelting(GOTRegistry.grapeWhite, new ItemStack(GOTRegistry.raisins), 0.3f);
		GOTRecipe.addSmeltingXPForItem(GOTRegistry.bronzeIngot, 0.7f);
		GOTRecipe.addSmeltingXPForItem(GOTRegistry.valyrianIngot, 1.0f);
		GOTRecipe.addSmeltingXPForItem(GOTRegistry.valyrianPowder, 0.8f);
		GOTRecipe.addSmeltingXPForItem(GOTRegistry.yitiSteelIngot, 0.7f);
	}

	public static void createSothoryosRecipes() {
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableSothoryos), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.brick4, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.SOTHORYOS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick4, 4, 0), "XX", "XX", Character.valueOf('X'), Blocks.stone));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle8, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSothoryosBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 0)));
		sothoryos.add(new ShapelessOreRecipe(new ItemStack(GOTRegistry.brick4, 1, 1), new ItemStack(GOTRegistry.brick4, 1, 0), "vine"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle8, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 1)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSothoryosBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 1)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 1)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle8, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 2)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSothoryosBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 2)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 2)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick4, 4, 3), "XX", "XX", Character.valueOf('X'), "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle8, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 3)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSothoryosBrickGold, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 3)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 3)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick4, 4, 4), "XX", "XX", Character.valueOf('X'), GOTRegistry.obsidianShard));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle8, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 4)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsSothoryosBrickObsidian, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 4)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 4, 4)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosShovel), "X", "Y", "Y", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosPickaxe), "XXX", " Y ", " Y ", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosAxe), "XX", "XY", " Y", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosHoe), "XX", " Y", " Y", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosSword), "XZX", "XZX", " Y ", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosHelmetChieftain), "X", "Y", Character.valueOf('X'), new ItemStack(GOTRegistry.doubleFlower, 1, 3), Character.valueOf('Y'), new ItemStack(GOTRegistry.sothoryosHelmet, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosSarbacane), "XYY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), GOTRegistry.reeds));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dart, 4), "X", "Y", "Z", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dartPoisoned, 4), " X ", "XYX", " X ", Character.valueOf('X'), GOTRegistry.sarbacaneTrap, Character.valueOf('Y'), "poison"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sarbacaneTrap), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 1, 0), Character.valueOf('Y'), new ItemStack(GOTRegistry.sothoryosSarbacane, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar1, 3, 14), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle8, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 14)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosDoubleTorchItem, 2), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sarbacaneTrapGold), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 1, 3), Character.valueOf('Y'), new ItemStack(GOTRegistry.sothoryosSarbacane, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateSothoryos, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosPike), "  X", " YX", "Y  ", Character.valueOf('X'), GOTRegistry.obsidianShard, Character.valueOf('Y'), "stickWood"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sarbacaneTrapObsidian), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 1, 4), Character.valueOf('Y'), new ItemStack(GOTRegistry.sothoryosSarbacane, 1, 0)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar2, 3, 11), "X", "X", "X", Character.valueOf('X'), "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar2, 3, 12), "X", "X", "X", Character.valueOf('X'), GOTRegistry.obsidianShard));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle6, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 11)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle6, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 12)));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosHelmetGold), "XXX", "X X", Character.valueOf('X'), "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosChestplateGold), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosLeggingsGold), "XXX", "X X", "X X", Character.valueOf('X'), "ingotGold"));
		sothoryos.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sothoryosBootsGold), "X X", "X X", Character.valueOf('X'), "ingotGold"));
	}

	public static void createStandardRecipes() {
		int i;
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.alloySteelAxe), "XX", "XY", " Y", Character.valueOf('X'), GOTRegistry.alloySteelIgnot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.alloySteelDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.alloySteelIgnot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.alloySteelHalberd), " XX", " YX", "Y  ", Character.valueOf('X'), GOTRegistry.alloySteelIgnot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.alloySteelHoe), "XX", " Y", " Y", Character.valueOf('X'), GOTRegistry.alloySteelIgnot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.alloySteelPickaxe), "XXX", " Y ", " Y ", Character.valueOf('X'), GOTRegistry.alloySteelIgnot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.alloySteelShovel), "X", "Y", "Y", Character.valueOf('X'), GOTRegistry.alloySteelIgnot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.alloySteelSword), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.alloySteelIgnot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick6, 1, 4), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick6, 1, 5), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.brick6, 1, 7), new ItemStack(GOTRegistry.brick6, 1, 4), "vine"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.iceHeavySword), " XX", " X ", "Y  ", Character.valueOf('X'), GOTRegistry.iceShard, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.iceSword), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.iceShard, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 6), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsCarnotite, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsCarnotiteBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsCarnotiteBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsCarnotiteBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar2, 3, 0), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle2, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 7)));
		GameRegistry.addRecipe(new ItemStack(Blocks.dirt, 4, 1), "XY", "YX", Character.valueOf('X'), new ItemStack(Blocks.dirt, 1, 0), Character.valueOf('Y'), Blocks.gravel);
		GameRegistry.addRecipe(new ItemStack(Blocks.obsidian, 1), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.obsidianShard);
		GameRegistry.addRecipe(new ItemStack(Blocks.packed_ice), "XX", "XX", Character.valueOf('X'), Blocks.ice);
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.alloyForge), "XXX", "X X", "XXX", Character.valueOf('X'), GOTRegistry.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.alloySteelIgnot), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelNugget);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.bananaBread), "XYX", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), GOTRegistry.banana);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.bananaCakeItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), GOTRegistry.banana, Character.valueOf('C'), Items.egg, Character.valueOf('D'), Items.wheat);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.bank), "XXX", "XYX", "XXX", Character.valueOf('X'), Blocks.cobblestone, Character.valueOf('Y'), GOTRegistry.coin);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 0), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.topaz);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 1), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.amethyst);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 2), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.sapphire);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 3), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.ruby);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 4), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.amber);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 5), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.diamond);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 6), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.pearl);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 7), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.opal);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 8), "XX", "XX", Character.valueOf('X'), GOTRegistry.coral);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockGem, 1, 9), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.emerald);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal1, 1, 0), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.copperIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal1, 1, 1), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.tinIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal1, 1, 13), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.sulfur);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal1, 1, 14), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.saltpeter);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal1, 1, 2), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal1, 1, 3), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.silverIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal1, 1, 4), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.valyrianIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal2, 1, 2), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.yitiSteelIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal2, 1, 3), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.salt);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.blockMetal2, 1, 4), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.brick1, 4, 14), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.brick2, 4, 2), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.brick3, 1, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.brick3, 1, 1), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.brick4, 4, 15), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.brick5, 4, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.mud, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.bronzeBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.bronzeChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.bronzeHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.bronzeLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.bronzeIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.cherryPieItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), GOTRegistry.cherry, Character.valueOf('C'), Items.sugar, Character.valueOf('D'), Items.wheat);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.chestBasket), "XXX", "X X", "XXX", Character.valueOf('X'), GOTRegistry.driedReeds);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.clayTile, 4, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.hardened_clay, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.copperIngot), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.copperNugget);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.cornBread), "XXX", Character.valueOf('X'), GOTRegistry.corn);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.diamondHorseArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), Items.diamond, Character.valueOf('Y'), Items.leather);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.dirtPath, 2, 0), "XX", Character.valueOf('X'), Blocks.dirt);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.dirtPath, 2, 1), "XX", Character.valueOf('X'), GOTRegistry.mud);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.dirtPath, 3, 2), "XYZ", Character.valueOf('X'), Blocks.dirt, Character.valueOf('Y'), Blocks.gravel, Character.valueOf('Z'), Blocks.cobblestone);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.furBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.fur);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.furChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.fur);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.furHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.fur);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.furLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.fur);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.gemsbokBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.gemsbokHide);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.gemsbokChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.gemsbokHide);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.gemsbokHelmet), "Y Y", "XXX", "X X", Character.valueOf('X'), GOTRegistry.gemsbokHide, Character.valueOf('Y'), GOTRegistry.gemsbokHorn);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.gemsbokLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.gemsbokHide);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.glass, 4), "XX", "XX", Character.valueOf('X'), Blocks.glass);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.glassPane, 16), "XXX", "XXX", Character.valueOf('X'), GOTRegistry.glass);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.hearth, 3), "XXX", "YYY", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), Items.brick);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.kebabBlock, 1, 0), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.kebab);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.leatherHat), " X ", "XXX", Character.valueOf('X'), Items.leather);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.lemonCakeItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), GOTRegistry.lemon, Character.valueOf('C'), Items.sugar, Character.valueOf('D'), Items.wheat);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.marzipanBlock), "XXX", Character.valueOf('X'), GOTRegistry.marzipan);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.mud, 4, 1), "XY", "YX", Character.valueOf('X'), new ItemStack(GOTRegistry.mud, 1, 0), Character.valueOf('Y'), Blocks.gravel);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.oliveBread), "XYX", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), GOTRegistry.olive);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.oven), "XXX", "X X", "XXX", Character.valueOf('X'), Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pillar1, 3, 3), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pillar1, 3, 4), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pillar2, 3, 1), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pillar2, 3, 2), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pillar2, 3, 3), "X", "X", "X", Character.valueOf('X'), Blocks.brick_block);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pressurePlateAndesite), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pressurePlateBasalt), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pressurePlateChalk), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pressurePlateDiorite), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pressurePlateGranite), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pressurePlateRhyolite), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.pressurePlateCarnotite), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.redSandstone, 1, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.sand, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.rope, 3), "X", "X", "X", Character.valueOf('X'), Items.string);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.scorchedSlabSingle, 6, 0), "XXX", Character.valueOf('X'), GOTRegistry.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.scorchedWall, 6), "XXX", "XXX", Character.valueOf('X'), GOTRegistry.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.silverIngot), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.silverNugget);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabClayTileSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTile, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle10, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.whiteSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle11, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle11, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle11, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle3, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle3, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle3, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle3, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle3, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle3, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar1, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle7, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle8, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStone, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle9, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle9, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle9, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle9, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingle9, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleDirt, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.dirt, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleDirt, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.dirtPath, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleDirt, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.mud, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleDirt, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.asshaiDirt, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleDirt, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.dirtPath, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleGravel, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.gravel, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleGravel, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.basaltGravel, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleGravel, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.obsidianGravel, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleSand, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.sand, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleSand, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(Blocks.sand, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleSand, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.whiteSand, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleThatch, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.thatch, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleThatch, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.thatch, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleV, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleV, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleV, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redBrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleV, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redBrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabSingleV, 6, 4), "XXX", Character.valueOf('X'), Blocks.mossy_cobblestone);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 3), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 4), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.smoothStone, 2, 5), "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsAlmond, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsApple, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsAramant, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 8));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsAspen, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 12));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsBanana, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 11));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsBaobab, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsBeech, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 9));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redBrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redBrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsCedar, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsChalk, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsChalkBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsCharred, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsCherry, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsChestnut, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTile, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTile, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedBlack, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedBlue, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 11));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedBrown, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 12));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedCyan, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 9));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedGray, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 7));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedGreen, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 13));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedLightBlue, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedLightGray, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 8));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedLime, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedMagenta, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedOrange, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedPink, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedPurple, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 10));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedRed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedWhite, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsClayTileDyedYellow, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsCobblestoneMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), Blocks.mossy_cobblestone);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsCypress, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 10));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsDatePalm, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsDiorite, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsDioriteBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsDragon, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsFir, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsFotinia, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsGranite, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsGraniteBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsGreenOak, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 13));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsHolly, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 10));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsKanuka, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsLarch, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 13));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsLemon, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsLime, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 7));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsMahogany, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 8));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsMango, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 7));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsMangrove, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsMaple, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 12));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsMudBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsOlive, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 11));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsOrange, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsPalm, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsPear, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsPine, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsPlum, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsPomegranate, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsRedSandstone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsRedwood, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsReed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.thatch, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsIbbinia, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsRotten, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planksRotten, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsScorchedStone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), GOTRegistry.scorchedStone);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsStone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsStoneBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsStoneBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsThatch, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.thatch, 1, 0));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam9, 3, 2), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 6)));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsUlthos, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsWeirwood, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 6));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsWhiteSandstone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.whiteSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.stairsWillow, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 9));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.thatch, 4, 1), "XX", "XX", Character.valueOf('X'), GOTRegistry.driedReeds);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.thatch, 6, 0), "XYX", "YXY", "XYX", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), Blocks.dirt);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.valyrianMail);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.valyrianMail);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.valyrianMail);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianHorseArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), GOTRegistry.valyrianMail, Character.valueOf('Y'), Items.leather);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianIngot), "XXX", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.valyrianNugget);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianIngot, 2), "XX", "XX", Character.valueOf('X'), GOTRegistry.valyrianMail);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.valyrianMail);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianMail, 8), "XX", "XX", Character.valueOf('X'), GOTRegistry.valyrianIngot);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.valyrianRing), "XXX", "X X", "XXX", Character.valueOf('X'), GOTRegistry.valyrianNugget);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 13), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStone1, 6, 14), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick1, 1, 14));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStone2, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStone2, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick2, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStone3, 6, 14), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.whiteSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStone3, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStone3, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick4, 1, 15));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallClayTile, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTile, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 2));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redSandstone, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.brick_block, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redBrick, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallStoneV, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.redBrick, 1, 1));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.whiteSandstone, 1, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.whiteSand, 1, 0));
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.whiteWalkersBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.iceShard);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.whiteWalkersChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.iceShard);
		GameRegistry.addRecipe(new ItemStack(GOTRegistry.whiteWalkersLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.iceShard);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence_gate, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStoneV, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.aleHorn), "X", "Y", Character.valueOf('X'), "horn", Character.valueOf('Y'), "ingotTin"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.aleHornGold), "X", "Y", Character.valueOf('X'), "horn", Character.valueOf('Y'), "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.appleCrumbleItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), "apple", Character.valueOf('C'), Items.sugar, Character.valueOf('D'), Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.armorStandItem), " X ", " X ", "YYY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.stone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.barrel), "XXX", "YZY", "XXX", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), "ingotIron", Character.valueOf('Z'), Items.bucket));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.berryPieItem), "AAA", "BBB", "CCC", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), "berry", Character.valueOf('C'), Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.birdCage, 1, 0), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), GOTRegistry.bronzeBars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.birdCage, 1, 1), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Blocks.iron_bars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.birdCage, 1, 2), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotSilver", Character.valueOf('Y'), GOTRegistry.silverBars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.birdCage, 1, 3), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotGold", Character.valueOf('Y'), GOTRegistry.goldBars));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.birdCageWood, 1, 0), "YYY", "Y Y", "XXX", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bomb, 4), "XYX", "YXY", "XYX", Character.valueOf('X'), Items.gunpowder, Character.valueOf('Y'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.boneBlock, 1, 0), "XX", "XX", Character.valueOf('X'), "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.boneBoots), "X X", "X X", Character.valueOf('X'), "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.boneChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.boneHelmet), "XXX", "X X", Character.valueOf('X'), "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.boneLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.brandingIron), "  X", " Y ", "X  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), GOTRegistry.gemsbokHide));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.brandingIron), "  X", " Y ", "X  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeAxe), "XX", "XY", " Y", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeCrossbow), "XXY", "ZYX", "YZX", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeHoe), "XX", " Y", " Y", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzePickaxe), "XXX", " Y ", " Y ", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeShovel), "X", "Y", "Y", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeSword), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.bronzeIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.bronzeThrowingAxe), " X ", " YX", "Y  ", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.butterflyJar), "X", "Y", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), Blocks.glass));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.cargocart), "fcf", "fcf", "wpw", Character.valueOf('f'), Blocks.fence, Character.valueOf('c'), Blocks.chest, Character.valueOf('p'), Blocks.planks, Character.valueOf('w'), GOTRegistry.wheel));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.chain, 8), "X", "X", "X", Character.valueOf('X'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.anonymousMask), "XXX", "X X", Character.valueOf('X'), Items.paper));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.chandelier, 2, 0), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.chandelier, 2, 1), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.chandelier, 2, 2), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.chandelier, 2, 3), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.chandelier, 2, 4), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), GOTRegistry.valyrianIngot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.chisel), "XY", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.clayMug, 2), "X", "Y", "X", Character.valueOf('X'), "ingotTin", Character.valueOf('Y'), "clayBall"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.clayPlate, 2), "XX", Character.valueOf('X'), "clayBall"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.cobblebrick, 4, 0), "XX", "XX", Character.valueOf('X'), Blocks.cobblestone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.commandHorn), "XYX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "horn"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.commandSword), "X", "Y", "Z", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "ingotBronze", Character.valueOf('Z'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.commandTable), "XXX", "YYY", "ZZZ", Character.valueOf('X'), Items.paper, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.crossbowBolt, 4), "X", "Y", "Z", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.crossbowBolt, 4), "X", "Y", "Z", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.daub, 4), "XYX", "YXY", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.dirt));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.daub, 4), "XYX", "YXY", "XYX", Character.valueOf('X'), GOTRegistry.driedReeds, Character.valueOf('Y'), Blocks.dirt));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorAcacia), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorAlmond), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorApple), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorAramant), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorAspen), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorBanana), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorBaobab), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorBeech), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorBirch), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorCedar), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorCharred), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorCherry), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorChestnut), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorCypress), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorDarkOak), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorDatePalm), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorDragon), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorFir), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorFotinia), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorGreenOak), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorHolly), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorJungle), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorKanuka), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorLarch), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorLemon), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorLime), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorMahogany), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorMango), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorMangrove), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorMaple), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorUlthos), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorOlive), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorOrange), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorPalm), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorPear), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorPine), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorPlum), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorPomegranate), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorRedwood), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorIbbinia), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorRotten), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planksRotten, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorSpruce), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorWeirwood), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorWillow), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateAcacia, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateAlmond, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateApple, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateAramant, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateAspen, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateBanana, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateBaobab, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateBeech, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateBirch, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateCedar, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateCharred, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateCherry, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateChestnut, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateCypress, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateDarkOak, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateDatePalm, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateDragon, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks3, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateFir, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateFotinia, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateGreenOak, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateHolly, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateJungle, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateKanuka, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks3, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateLarch, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateLemon, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateLime, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateMahogany, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateMango, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateMangrove, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateMaple, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateUlthos, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateOlive, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateOrange, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGatePalm, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks3, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGatePear, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGatePine, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGatePlum, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks3, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGatePomegranate, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks3, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateRedwood, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks3, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateIbbinia, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateRotten, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planksRotten, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateSpruce, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateWeirwood, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks3, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateWillow, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks2, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.flintDagger), "X", "Y", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.flintSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.furBedItem), "XXX", "YYY", Character.valueOf('X'), GOTRegistry.fur, Character.valueOf('Y'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fuseItem, 2), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateBronzeBars, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "ingotBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateGear, 4), " X ", "XYX", " X ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateGold, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateIronBars, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateSilver, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateValyrian, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), GOTRegistry.valyrianIngot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateWooden, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateWoodenCross, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gobletCopper, 2), "X X", " X ", " X ", Character.valueOf('X'), "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gobletGold, 2), "X X", " X ", " X ", Character.valueOf('X'), "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gobletSilver, 2), "X X", " X ", " X ", Character.valueOf('X'), "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.gobletWood, 2), "X X", " X ", " X ", Character.valueOf('X'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.goldBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.goldHorseArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotGold", Character.valueOf('Y'), Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.goldRing), "XXX", "X X", "XXX", Character.valueOf('X'), "nuggetGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.grapevine), "X", "X", "X", Character.valueOf('X'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironCrossbow), "XXY", "ZYX", "YZX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironDagger), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironHorseArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironPike), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.ironThrowingAxe), " X ", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.lionBedItem), "XXX", "YYY", Character.valueOf('X'), GOTRegistry.lionFur, Character.valueOf('Y'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.millstone), "XYX", "XZX", "XXX", Character.valueOf('X'), Blocks.cobblestone, Character.valueOf('Y'), "ingotBronze", Character.valueOf('Z'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.millstone), "XYX", "XZX", "XXX", Character.valueOf('X'), Blocks.cobblestone, Character.valueOf('Y'), "ingotIron", Character.valueOf('Z'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.mug, 2), "X", "Y", "X", Character.valueOf('X'), "ingotTin", Character.valueOf('Y'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.oreGlowstone), "XXX", "XYX", "XXX", Character.valueOf('X'), Items.glowstone_dust, Character.valueOf('Y'), new ItemStack(Blocks.stone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.pipe), "X  ", " XX", Character.valueOf('X'), Items.clay_ball));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.plowcart), "fff", "php", "wpw", Character.valueOf('f'), Blocks.fence, Character.valueOf('h'), Items.iron_hoe, Character.valueOf('p'), Blocks.planks, Character.valueOf('w'), GOTRegistry.wheel));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.pouch, 1, 0), "X X", "X X", "XXX", Character.valueOf('X'), Items.leather));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.redClay), "XX", "XX", Character.valueOf('X'), GOTRegistry.redClayBall));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.reedBars, 16), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.thatch, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.rhinoArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.rollingPin), "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.silverBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.silverRing), "XXX", "X X", "XXX", Character.valueOf('X'), "nuggetSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.skullCup), "X", "Y", Character.valueOf('X'), new ItemStack(Items.skull, 1, 0), Character.valueOf('Y'), "ingotTin"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.skullStaff), "X", "Y", Character.valueOf('X'), Items.skull, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabBoneSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.boneBlock, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingleV, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.sling), "XYX", "XZX", " X ", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.smoothStoneV, 2, 0), "X", "X", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsBone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.boneBlock, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stoneSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "cobblestone", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.strawBedItem), "XXX", "YYY", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.sulfurMatch, 4), "X", "Y", Character.valueOf('X'), "sulfur", Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.termiteMound, 1, 1), " X ", "XYX", " X ", Character.valueOf('X'), GOTRegistry.termite, Character.valueOf('Y'), Blocks.stone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.thatchFloor, 3), "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.thatch, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorAlmond, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorApple, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorAramant, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorAspen, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorBanana, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorBaobab, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorBeech, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorBirch, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorCedar, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorCharred, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorCherry, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorChestnut, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorCypress, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorDarkOak, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorDatePalm, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorDragon, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorFir, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorFotinia, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 14)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorGreenOak, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorHolly, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 10)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorJungle, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorKanuka, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorLarch, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 13)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorLemon, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorLime, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorMahogany, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 8)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorMango, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 7)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorMangrove, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 15)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorMaple, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 12)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorUlthos, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorOlive, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 11)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorOrange, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorPalm, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 3)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorPear, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 5)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorPine, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorPlum, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorPomegranate, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 2)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorRedwood, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorIbbinia, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorRotten, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planksRotten, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorSpruce, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorWeirwood, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 6)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorWillow, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 9)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.unsmeltery), "X X", "YXY", "ZZZ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Blocks.cobblestone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianAxe), "XX", "XY", " Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianBars, 16), "XXX", "XXX", Character.valueOf('X'), GOTRegistry.valyrianIngot));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianCrossbow), "XXY", "ZYX", "YZX", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianHalberd), " XX", " YX", "Y  ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianHoe), "XX", " Y", " Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianMattock), "XXX", "XY ", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianPickaxe), "XXX", " Y ", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianShovel), "X", "Y", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.valyrianSword), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallBone, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.boneBlock, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wasteBlock, 4), "XY", "YZ", Character.valueOf('X'), Items.rotten_flesh, Character.valueOf('Y'), Blocks.dirt, Character.valueOf('Z'), "bone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), GOTRegistry.fur, Character.valueOf('Y'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), GOTRegistry.gemsbokHide, Character.valueOf('Y'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), GOTRegistry.lionFur, Character.valueOf('Y'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), Items.string));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.weaponRack), "X X", "YYY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wheel), "sss", "sps", "sss", Character.valueOf('s'), "stickWood", Character.valueOf('p'), Blocks.planks));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.wineGlass, 2), "X X", " X ", " X ", Character.valueOf('X'), Blocks.glass));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodPlate, 2), "XX", Character.valueOf('X'), "logWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.arrow, 4), "X", "Y", "Z", Character.valueOf('X'), "arrowTip", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrowFire, 4), " X ", "XYX", " X ", Character.valueOf('X'), Items.arrow, Character.valueOf('Y'), GOTRegistry.sulfur));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.arrowPoisoned, 4), " X ", "XYX", " X ", Character.valueOf('X'), Items.arrow, Character.valueOf('Y'), "poison"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.crossbowBoltPoisoned, 4), " X ", "XYX", " X ", Character.valueOf('X'), GOTRegistry.crossbowBolt, Character.valueOf('Y'), "poison"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.brewing_stand, 1), " X ", "YYY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.cobblestone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.clock), " X ", "XYX", " X ", Character.valueOf('X'), "ingotGold", Character.valueOf('Y'), "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.compass), " X ", "XYX", " X ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), GOTRegistry.fur));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), GOTRegistry.gemsbokHide));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3), "XXX", Character.valueOf('X'), GOTRegistry.cornStalk));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3), "XXX", Character.valueOf('X'), GOTRegistry.reeds));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", Character.valueOf('X'), GOTRegistry.fur, Character.valueOf('Y'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", Character.valueOf('X'), GOTRegistry.gemsbokHide, Character.valueOf('Y'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.wooden_door), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsCatalpa, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam1, 3, 1), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fence, 3, 1), "XYX", "XYX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 1), Character.valueOf('Y'), "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceGateCatalpa, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.doorCatalpa), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 1)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorCatalpa, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 1)));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.dirt, 1, 0), new ItemStack(Blocks.dirt, 1, 1), Items.wheat_seeds));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.mossy_cobblestone, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0), "vine"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick, 1, 0), "vine"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.planks1, 4, 1), new ItemStack(GOTRegistry.wood1, 1, 1)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.brandingIron), new ItemStack(GOTRegistry.brandingIron, 1, 32767), "ingotIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.dye, 2, 5), "dyeOrange", "dyeBlack"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.dye, 3, 5), "dyeRed", "dyeYellow", "dyeBlack"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.gammon), Items.cooked_porkchop, "salt"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.mud, 1, 0), new ItemStack(GOTRegistry.mud, 1, 1), Items.wheat_seeds));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.questBook), Items.book, new ItemStack(Items.dye, 1, 1), "nuggetGold"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.redBrick, 1, 0), new ItemStack(Blocks.brick_block, 1, 0), "vine"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.saltedFlesh), Items.rotten_flesh, "salt"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 2), GOTRegistry.termite));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.bomb, 1, 1), new ItemStack(GOTRegistry.bomb, 1, 0), Items.gunpowder, "ingotIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.bomb, 1, 2), new ItemStack(GOTRegistry.bomb, 1, 1), Items.gunpowder, "ingotIron"));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.alloySteelIgnot, 9), new ItemStack(GOTRegistry.blockMetal2, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.alloySteelNugget, 9), GOTRegistry.alloySteelIgnot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.amber, 9), new ItemStack(GOTRegistry.blockGem, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.amethyst, 9), new ItemStack(GOTRegistry.blockGem, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.blackrootStick, 2), GOTRegistry.blackroot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.bottlePoison), Items.glass_bottle, GOTRegistry.wildberry);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.bronzeIngot, 1), GOTRegistry.copperIngot, GOTRegistry.tinIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.bronzeIngot, 9), new ItemStack(GOTRegistry.blockMetal1, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.buttonAndesite), new ItemStack(GOTRegistry.rock, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.buttonBasalt), new ItemStack(GOTRegistry.rock, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.buttonChalk), new ItemStack(GOTRegistry.rock, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.buttonDiorite), new ItemStack(GOTRegistry.rock, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.buttonGranite), new ItemStack(GOTRegistry.rock, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.buttonRhyolite), new ItemStack(GOTRegistry.rock, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.buttonCarnotite), new ItemStack(GOTRegistry.rock, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.copperIngot, 9), new ItemStack(GOTRegistry.blockMetal1, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.copperNugget, 9), GOTRegistry.copperIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.coral, 4), new ItemStack(GOTRegistry.blockGem, 1, 8));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.diamond, 9), new ItemStack(GOTRegistry.blockGem, 1, 5));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 1, 0), new ItemStack(GOTRegistry.essosFlower, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 1, 0), new ItemStack(GOTRegistry.yitiFlower, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 1, 1), new ItemStack(GOTRegistry.yitiFlower, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 1, 2), GOTRegistry.bluebell);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 1, 2), new ItemStack(GOTRegistry.yitiFlower, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 1, 3), new ItemStack(GOTRegistry.clover, 1, 32767));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 1, 4), new ItemStack(Items.coal, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.dye, 2, 0), new ItemStack(GOTRegistry.doubleFlower, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.emerald, 9), new ItemStack(GOTRegistry.blockGem, 1, 9));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.flaxSeeds, 2), GOTRegistry.flaxPlant);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.kebab, 9), new ItemStack(GOTRegistry.kebabBlock, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.leekSoup), Items.bowl, GOTRegistry.leek, GOTRegistry.leek, Items.potato);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.mapleSyrup), new ItemStack(GOTRegistry.wood3, 1, 0), Items.bowl);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.marzipan), GOTRegistry.almond, GOTRegistry.almond, Items.sugar);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.marzipanChocolate), GOTRegistry.marzipan, new ItemStack(Items.dye, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.melonSoup), Items.bowl, Items.melon, Items.melon, Items.sugar);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.mushroomPie), Items.egg, Blocks.red_mushroom, Blocks.brown_mushroom);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.obsidianShard, 9), Blocks.obsidian);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.opal, 9), new ItemStack(GOTRegistry.blockGem, 1, 7));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.pearl, 9), new ItemStack(GOTRegistry.blockGem, 1, 6));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.pebble, 4), Blocks.gravel);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.pipeweedSeeds), GOTRegistry.pipeweedLeaf);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.pipeweedSeeds, 2), GOTRegistry.pipeweedPlant);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 0), new ItemStack(GOTRegistry.wood1, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 10), new ItemStack(GOTRegistry.wood2, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 11), new ItemStack(GOTRegistry.wood2, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 12), new ItemStack(GOTRegistry.wood3, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 13), new ItemStack(GOTRegistry.wood3, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 14), new ItemStack(GOTRegistry.wood3, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 15), new ItemStack(GOTRegistry.wood3, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 2), new ItemStack(GOTRegistry.wood1, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 3), new ItemStack(GOTRegistry.wood1, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 4), new ItemStack(GOTRegistry.fruitWood, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 5), new ItemStack(GOTRegistry.fruitWood, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 6), new ItemStack(GOTRegistry.fruitWood, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 7), new ItemStack(GOTRegistry.fruitWood, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 8), new ItemStack(GOTRegistry.wood2, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks1, 4, 9), new ItemStack(GOTRegistry.wood2, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 0), new ItemStack(GOTRegistry.wood4, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 1), new ItemStack(GOTRegistry.wood4, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 10), new ItemStack(GOTRegistry.wood6, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 11), new ItemStack(GOTRegistry.wood6, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 12), new ItemStack(GOTRegistry.wood7, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 13), new ItemStack(GOTRegistry.wood7, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 14), new ItemStack(GOTRegistry.wood7, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 15), new ItemStack(GOTRegistry.wood7, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 2), new ItemStack(GOTRegistry.wood4, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 3), new ItemStack(GOTRegistry.wood4, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 4), new ItemStack(GOTRegistry.wood5, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 5), new ItemStack(GOTRegistry.wood5, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 6), new ItemStack(GOTRegistry.wood5, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 7), new ItemStack(GOTRegistry.wood5, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 8), new ItemStack(GOTRegistry.wood6, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks2, 4, 9), new ItemStack(GOTRegistry.wood6, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks3, 4, 0), new ItemStack(GOTRegistry.wood8, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks3, 4, 1), new ItemStack(GOTRegistry.wood8, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks3, 4, 2), new ItemStack(GOTRegistry.wood8, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks3, 4, 3), new ItemStack(GOTRegistry.wood8, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks3, 4, 4), new ItemStack(GOTRegistry.wood9, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks3, 4, 5), new ItemStack(GOTRegistry.wood9, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planks3, 4, 6), new ItemStack(GOTRegistry.wood9, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.planksRotten, 4, 0), new ItemStack(GOTRegistry.rottenLog, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.rabbitStew), Items.bowl, GOTRegistry.rabbitCooked, Items.potato, Items.potato);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.ruby, 9), new ItemStack(GOTRegistry.blockGem, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.salt, 9), new ItemStack(GOTRegistry.blockMetal2, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.saltpeter, 9), new ItemStack(GOTRegistry.blockMetal1, 1, 14));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.sapphire, 9), new ItemStack(GOTRegistry.blockGem, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.seedsGrapeRed), GOTRegistry.grapeRed);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.seedsGrapeWhite), GOTRegistry.grapeWhite);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.silverIngot, 9), new ItemStack(GOTRegistry.blockMetal1, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.silverNugget, 9), GOTRegistry.silverIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.sulfur, 9), new ItemStack(GOTRegistry.blockMetal1, 1, 13));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.tinIngot, 9), new ItemStack(GOTRegistry.blockMetal1, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.topaz, 9), new ItemStack(GOTRegistry.blockGem, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.valyrianIngot, 9), new ItemStack(GOTRegistry.blockMetal1, 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.valyrianNugget, 9), GOTRegistry.valyrianIngot);
		GameRegistry.addShapelessRecipe(new ItemStack(GOTRegistry.yitiSteelIngot, 9), new ItemStack(GOTRegistry.blockMetal2, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(GOTRegistry.essosFlower, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 13), new ItemStack(GOTRegistry.essosFlower, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), GOTRegistry.marigold);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(GOTRegistry.yitiFlower, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 5), new ItemStack(GOTRegistry.essosFlower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(GOTRegistry.yitiFlower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 1), new ItemStack(GOTRegistry.doubleFlower, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 13), new ItemStack(GOTRegistry.doubleFlower, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), GOTRegistry.saltpeter, Blocks.dirt);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 5), new ItemStack(GOTRegistry.doubleFlower, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 4, 1), new ItemStack(GOTRegistry.wood9, 1, 0), new ItemStack(GOTRegistry.wood9, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 8, 15), GOTRegistry.boneBlock);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 2), GOTRegistry.sulfur, GOTRegistry.saltpeter, new ItemStack(Items.coal, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.string), GOTRegistry.flax);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.trapdoorAcacia, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 4)));
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugAppleJuice), new Object[] { "apple", "apple" });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugBlackberryJuice), new Object[] { GOTRegistry.blackberry, GOTRegistry.blackberry, GOTRegistry.blackberry });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugBlueberryJuice), new Object[] { GOTRegistry.blueberry, GOTRegistry.blueberry, GOTRegistry.blueberry });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugChocolate), GOTRegistry.mugMilk, new Object[] { Items.sugar, new ItemStack(Items.dye, 1, 3) });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugCranberryJuice), new Object[] { GOTRegistry.cranberry, GOTRegistry.cranberry, GOTRegistry.cranberry });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugElderberryJuice), new Object[] { GOTRegistry.elderberry, GOTRegistry.elderberry, GOTRegistry.elderberry });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugLemonade), GOTRegistry.mugWater, new Object[] { GOTRegistry.lemon, Items.sugar });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugMangoJuice), new Object[] { GOTRegistry.mango, GOTRegistry.mango });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugOrangeJuice), new Object[] { GOTRegistry.orange, GOTRegistry.orange });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugPomegranateJuice), new Object[] { GOTRegistry.pomegranate, GOTRegistry.pomegranate });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugRaspberryJuice), new Object[] { GOTRegistry.raspberry, GOTRegistry.raspberry, GOTRegistry.raspberry });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugRedGrapeJuice), new Object[] { GOTRegistry.grapeRed, GOTRegistry.grapeRed, GOTRegistry.grapeRed });
		GOTRecipeVessels.addRecipes(new ItemStack(GOTRegistry.mugWhiteGrapeJuice), new Object[] { GOTRegistry.grapeWhite, GOTRegistry.grapeWhite, GOTRegistry.grapeWhite });
		GOTRecipe.addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTRegistry.robesBoots), "X X", "X X", Character.valueOf('X'), Blocks.wool);
		GOTRecipe.addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTRegistry.robesChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), Blocks.wool);
		GOTRecipe.addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTRegistry.robesHelmet), "XXX", "X X", Character.valueOf('X'), Blocks.wool);
		GOTRecipe.addDyeableWoolRobeRecipes(commonEssos, new ItemStack(GOTRegistry.robesLeggings), "XXX", "X X", "X X", Character.valueOf('X'), Blocks.wool);
		GOTRecipe.addDyeableWoolRobeRecipes(yiti, new ItemStack(GOTRegistry.kaftanChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), Blocks.wool);
		GOTRecipe.addDyeableWoolRobeRecipes(yiti, new ItemStack(GOTRegistry.kaftanLeggings), "XXX", "X X", "X X", Character.valueOf('X'), Blocks.wool);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTRegistry.treasureCopper, GOTRegistry.copperIngot);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTRegistry.treasureGold, Items.gold_ingot);
		GOTBlockTreasurePile.generateTreasureRecipes(GOTRegistry.treasureSilver, GOTRegistry.silverIngot);
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.bronzeDaggerPoisoned, GOTRegistry.bronzeDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.bronzeDaggerPoisoned, GOTRegistry.bronzeDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.ironDaggerPoisoned, GOTRegistry.ironDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.valyrianDaggerPoisoned, GOTRegistry.valyrianDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.westerosDaggerPoisoned, GOTRegistry.westerosDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.wildlingDaggerPoisoned, GOTRegistry.wildlingDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.essosDaggerPoisoned, GOTRegistry.essosDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.asshaiDaggerPoisoned, GOTRegistry.asshaiDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.alloySteelDaggerPoisoned, GOTRegistry.alloySteelDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.lhazarDaggerPoisoned, GOTRegistry.lhazarDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.sothoryosDaggerPoisoned, GOTRegistry.sothoryosDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.yitiDaggerPoisoned, GOTRegistry.yitiDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.summerDaggerPoisoned, GOTRegistry.summerDagger, GOTRegistry.bottlePoison));
		GameRegistry.addRecipe(new ShapelessOreRecipe(GOTRegistry.valyrianChisel, GOTRegistry.chisel, GOTRegistry.valyrianPowder));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeamRotten, 3, 0), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rottenLog, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fenceRotten, 3, 0), "XYX", "XYX", Character.valueOf('X'), new ItemStack(GOTRegistry.planksRotten, 1, 0), Character.valueOf('Y'), "stickWood"));
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
		for (i = 0; i <= 1; ++i) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam9, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood9, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeamV2, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.log2, 1, i)));
		}
		for (i = 0; i <= 2; ++i) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(GOTRegistry.bomb, 1, i + 8), new ItemStack(GOTRegistry.bomb, 1, i), Items.lava_bucket));
		}
		for (i = 0; i <= 3; ++i) {
			if (i != 1) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam1, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood1, 1, i)));
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam8, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood8, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam7, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood7, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam2, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood2, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeamFruit, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.fruitWood, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam3, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood3, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam4, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood4, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam5, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood5, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeam6, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.wood6, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodBeamV1, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.log, 1, i)));
		}
		for (i = 0; i <= 5; ++i) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, i), Character.valueOf('Y'), "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fence3, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, i), Character.valueOf('Y'), "stickWood"));
		}
		for (i = 0; i <= 7; ++i) {
			GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabClayTileDyedSingle1, 6, i), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, i));
			GameRegistry.addRecipe(new ItemStack(GOTRegistry.slabClayTileDyedSingle2, 6, i), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, i + 8));
		}
		for (i = 0; i <= 15; ++i) {
			if (i != 1) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fence, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, i), Character.valueOf('Y'), "stickWood"));
			}
			GameRegistry.addRecipe(new ItemStack(GOTRegistry.wallClayTileDyed, 6, i), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTileDyed, 1, i));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.clayTileDyed, 8, i), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.clayTile, 1, 0), Character.valueOf('Y'), dyeOreNames[BlockColored.func_150032_b(i)]));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.clayTileDyed, 4, i), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.stained_hardened_clay, 1, i)));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.fence2, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, i), Character.valueOf('Y'), "stickWood"));
			GameRegistry.addRecipe(new ItemStack(GOTRegistry.stainedGlass, 4, i), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.stained_glass, 1, i));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stainedGlass, 8, i), "XXX", "XYX", "XXX", Character.valueOf('X'), GOTRegistry.glass, Character.valueOf('Y'), dyeOreNames[BlockColored.func_150031_c(i)]));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOTRegistry.stainedGlassPane, 16, i), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.stainedGlass, 1, i)));
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
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fallenBlock, 6, fallenMeta), "XXX", Character.valueOf('X'), new ItemStack(leafBlock, 1, leafMeta)));
				}
			}
		}
	}

	public static void createStormlandsRecipes() {
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableStormlands), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stormlandsHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stormlandsChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stormlandsLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stormlandsBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		stormlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.RENLY.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		stormlands.addAll(commonWesteros);
	}

	public static void createSummerRecipes() {
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableSummer), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.SUMMER.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerSword), "X", "X", "Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerDagger), "X", "Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
		summer.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.summerPike), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
		summer.addAll(commonEssos);
	}

	public static void createTyroshRecipes() {
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableTyrosh), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tyroshHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tyroshChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tyroshLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tyroshBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		tyrosh.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.TYROSH.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		tyrosh.addAll(commonEssos);
	}

	public static void createUnsmeltingRecipes() {
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.renlyHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.renlyChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.renlyLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.renlyBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.blacksmithHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.goldHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.goldChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.goldLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.goldBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.redkingHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.redkingChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.redkingLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.redkingBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westkingHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westkingChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westkingLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westkingBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.blackfyreHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.blackfyreChestplate), "X X", "XXX", "XXX", Character.valueOf('X'),GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.blackfyreLeggings), "XXX", "X X", "X X", Character.valueOf('X'),GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.blackfyreBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.royceHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.royceChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.royceLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.royceBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.bronzeIngot));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.baelishDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.boltonDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.euronDagger), "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tyeneDagger), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.gregorCleganeSword), "XX ", "XX ", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ice), "XX ", "XX ", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.areoHotahAxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.victarionAxe), "XXX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.celtigarAxe), "XXX", "XYX", " Y ", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tyrionAxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.gendryHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.robertHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.daarioArakh), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.darkstar), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.dawn), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.bericSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.eddardSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.jaimeSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.needle), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nightKingSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.obaraSpear), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.rhaegarSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.robbSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sandorCleganeSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.sunspear), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tormundSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.trystaneSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.katana), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.blackArakh), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.blackfyreSword), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brightroar), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.darkSister), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.heartsbane), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.justMaid), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.ladyForlorn), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lamentation), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.lightbringer), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.longclaw), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.nightfall), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.oathkeeper), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.orphanMaker), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.redRain), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.truth), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.vigilance), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
		unsmelt.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.widowWail), "X", "X", "Y", Character.valueOf('X'), GOTRegistry.valyrianIngot, Character.valueOf('Y'), "stickWood"));
	}

	public static void createVolantisRecipes() {
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableVolantis), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.volantisHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.volantisChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.volantisLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.volantisBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		volantis.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.VOLANTIS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		volantis.addAll(commonEssos);
	}

	public static void createWesterlandsRecipes() {
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsguardHelmet), "XXX", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsguardChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsguardLeggings), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsguardBoots), "X X", "X X", Character.valueOf('X'), GOTRegistry.alloySteelIgnot));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableWesterlands), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.rock, 1, 1)));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsHelmet), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsChestplate), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsLeggings), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.westerlandsBoots), "X X", "X X", Character.valueOf('X'), "ingotIron"));
		westerlands.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.LANNISTER.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		westerlands.addAll(commonWesteros);
	}

	public static void createWildlingRecipes() {
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableWildling), "XX", "XX", Character.valueOf('X'), "plankWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildlingSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildlingBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildlingDagger), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildlingSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildlingAxe), "XX", "XY", " Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildlingHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.WILDLING.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildlingPolearm), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		wildling.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.THENN.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "stickWood"));
	}

	public static void createWoodenSlabRecipes() {
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 0)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 2)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 3)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 4)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 5)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 6)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle1, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 7)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 8)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 9)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 10)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 11)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 12)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 13)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 14)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle2, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks1, 1, 15)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 0)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 1)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 2)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 3)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 4)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 5)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 6)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle3, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 7)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 8)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 9)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 10)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 11)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 12)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 13)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 14)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle4, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks2, 1, 15)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle5, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 0)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle5, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 1)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle5, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 2)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle5, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 3)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle5, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 4)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle5, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 5)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.woodSlabSingle5, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planks3, 1, 6)));
		slab.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.rottenSlabSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.planksRotten, 1, 0)));
	}

	public static void createYiTiRecipes() {
		OreDictionary.registerOre("yitiStone", new ItemStack(Blocks.stone, 1, 0));
		OreDictionary.registerOre("yitiStone", new ItemStack(Blocks.sandstone, 1, 0));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.tableYiTi), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(GOTRegistry.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.banner, 1, GOTItemBanner.BannerType.YITI.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick5, 4, 11), "XX", "XX", Character.valueOf('X'), "yitiStone"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle12, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsYiTiBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone3, 6, 15), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick5, 1, 12), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar2, 3, 8), "X", "X", "X", Character.valueOf('X'), "yitiStone"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle12, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 8)));
		yiti.add(new ShapelessOreRecipe(new ItemStack(GOTRegistry.brick5, 1, 13), new ItemStack(GOTRegistry.brick5, 1, 11), "vine"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle12, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 13)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsYiTiBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 13)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 10), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 13)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle12, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 14)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsYiTiBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 14)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 11), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 14)));
		yiti.add(new ShapelessOreRecipe(new ItemStack(GOTRegistry.brick5, 1, 15), new ItemStack(GOTRegistry.brick5, 1, 11), new ItemStack(GOTRegistry.yitiFlower, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle12, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 15)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsYiTiBrickFlowers, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 15)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 12), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick5, 1, 15)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick6, 1, 0), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(GOTRegistry.brick5, 1, 11)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick6, 4, 1), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle12, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.stairsYiTiBrickRed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wallStone4, 6, 13), "XXX", "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.brick6, 1, 2), "XX", "XX", Character.valueOf('X'), new ItemStack(GOTRegistry.brick6, 1, 1)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.pillar2, 3, 9), "X", "X", "X", Character.valueOf('X'), new ItemStack(GOTRegistry.rock, 1, 4)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.slabSingle12, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.pillar2, 1, 9)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiSword), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiDagger), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiSpear), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiPolearm), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiPike), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiHelmet), "XXX", "Y Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiChestplate), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiLeggings), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiBoots), "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiHorseArmor), "X  ", "XYX", "XXX", Character.valueOf('X'), GOTRegistry.yitiSteelIngot, Character.valueOf('Y'), Items.leather));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.gateYiTi, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), GOTRegistry.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), GOTRegistry.yitiSteelIngot));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.wildFireJar), "XYX", "YZY", "XYX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.firePot, 4), "Z", "Y", "X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), GOTRegistry.fuseItem));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiHelmetSamurai), "XXX", "X X", Character.valueOf('X'), GOTRegistry.yitiSteelIngot));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiChestplateSamurai), "X X", "XXX", "XXX", Character.valueOf('X'), GOTRegistry.yitiSteelIngot));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiLeggingsSamurai), "XXX", "X X", "X X", Character.valueOf('X'), GOTRegistry.yitiSteelIngot));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiBootsSamurai), "X X", "X X", Character.valueOf('X'), GOTRegistry.yitiSteelIngot));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiHelmetShogune), "XYX", Character.valueOf('X'), GOTRegistry.whiteBisonHorn, Character.valueOf('Y'), new ItemStack(GOTRegistry.yitiHelmetSamurai, 1, 0)));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiBattleaxe), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiHelmetFrontier), "YYY", "X X", Character.valueOf('X'), GOTRegistry.yitiSteelIngot, Character.valueOf('Y'), "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiChestplateFrontier), "X X", "YXY", "YXY", Character.valueOf('X'), GOTRegistry.yitiSteelIngot, Character.valueOf('Y'), "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiLeggingsFrontier), "XXX", "Y Y", "X X", Character.valueOf('X'), GOTRegistry.yitiSteelIngot, Character.valueOf('Y'), "ingotIron"));
		yiti.add(new ShapedOreRecipe(new ItemStack(GOTRegistry.yitiBootsFrontier), "X X", "Y Y", Character.valueOf('X'), GOTRegistry.yitiSteelIngot, Character.valueOf('Y'), "ingotIron"));
		yiti.add(new GOTRecipeRobesDye(GOTMaterial.KAFTAN));
	}

	public static ItemStack findMatchingRecipe(List recipeList, InventoryCrafting inv, World world) {
		for (Object element : recipeList) {
			IRecipe recipe = (IRecipe) element;
			if (!recipe.matches(inv, world)) {
				continue;
			}
			return recipe.getCraftingResult(inv);
		}
		return null;
	}

	public static GOTBlockConcretePowder getPowderFromDye(GOTEnumDyeColor dye) {
		return GOTRegistry.concretePowder.get(dye);
	}

	public static void modifyStandardRecipes() {
		List recipeList = CraftingManager.getInstance().getRecipeList();
		GOTRecipe.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence));
		for (int i = 0; i <= 5; ++i) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, i), Character.valueOf('Y'), "stickWood"));
		}
		GOTRecipe.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence_gate));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence_gate, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 0)));
		GOTRecipe.removeRecipesItem(recipeList, Items.wooden_door);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.wooden_door), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 0)));
		GOTRecipe.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.trapdoor));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.trapdoor, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 0)));
		if (GOTConfig.removeGoldenAppleRecipes) {
			GOTRecipe.removeRecipesItem(recipeList, Items.golden_apple);
		}
		if (GOTConfig.removeDiamondArmorRecipes) {
			GOTRecipe.removeRecipesItem(recipeList, Items.diamond_helmet);
			GOTRecipe.removeRecipesItem(recipeList, Items.diamond_chestplate);
			GOTRecipe.removeRecipesItem(recipeList, Items.diamond_leggings);
			GOTRecipe.removeRecipesItem(recipeList, Items.diamond_boots);
		}
		GOTRecipe.removeRecipesItemStack(recipeList, new ItemStack(Blocks.stone_slab, 1, 0));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(GOTRegistry.smoothStoneV, 1, 0)));
		GOTRecipe.removeRecipesItemStack(recipeList, new ItemStack(Blocks.stone_slab, 1, 5));
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
		GOTRecipe.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.stone_brick_stairs));
		GameRegistry.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
		GOTRecipe.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.anvil));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.anvil), "XXX", " Y ", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "blockIron"));
		GOTRecipe.removeRecipesClass(recipeList, RecipesArmorDyes.class);
		GameRegistry.addRecipe(new GOTRecipeArmorDyes());
	}

	public static void onInit() {
		GOTRecipe.registerOres();
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
		GOTRecipe.modifyStandardRecipes();
		GOTRecipe.createStandardRecipes();
		GOTRecipe.createCommonWesterosRecipes();
		GOTRecipe.createCommonEssosRecipes();
		GOTRecipe.createWoodenSlabRecipes();
		CraftingManager.getInstance().getRecipeList().addAll(0, slab);
		GOTRecipe.createSmeltingRecipes();
		GOTRecipe.createNorthRecipes();
		GOTRecipe.createHillmanRecipes();
		GOTRecipe.createWildlingRecipes();
		GOTRecipe.createSummerRecipes();
		GOTRecipe.createGiftRecipes();
		GOTRecipe.createLhazarRecipes();
		GOTRecipe.createSothoryosRecipes();
		GOTRecipe.createYiTiRecipes();
		GOTRecipe.createIbbenRecipes();
		GOTRecipe.createUnsmeltingRecipes();
		GOTRecipe.createArrynRecipes();
		GOTRecipe.createDorneRecipes();
		GOTRecipe.createReachRecipes();
		GOTRecipe.createWesterlandsRecipes();
		GOTRecipe.createCrownlandsRecipes();
		GOTRecipe.createStormlandsRecipes();
		GOTRecipe.createDragonstoneRecipes();
		GOTRecipe.createRiverlandsRecipes();
		GOTRecipe.createIronbornRecipes();
		GOTRecipe.createVolantisRecipes();
		GOTRecipe.createBraavosRecipes();
		GOTRecipe.createLysRecipes();
		GOTRecipe.createMyrRecipes();
		GOTRecipe.createTyroshRecipes();
		GOTRecipe.createPentosRecipes();
		GOTRecipe.createNorvosRecipes();
		GOTRecipe.createLorathRecipes();
		GOTRecipe.createQohorRecipes();
		GOTRecipe.createQarthRecipes();
		GOTRecipe.createGhiscarRecipes();
		GOTRecipe.createAsshaiRecipes();
		GOTRecipe.createJogosRecipes();
		GOTRecipe.createDothrakiRecipes();
		GOTRecipe.createMossovyRecipes();
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
		OreDictionary.registerOre("stickWood", GOTRegistry.blackrootStick);
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
		OreDictionary.registerOre("oreCopper", GOTRegistry.oreCopper);
		OreDictionary.registerOre("oreTin", GOTRegistry.oreTin);
		OreDictionary.registerOre("oreSilver", GOTRegistry.oreSilver);
		OreDictionary.registerOre("oreCobalt", GOTRegistry.cobaltIngot);
		OreDictionary.registerOre("oreSulfur", GOTRegistry.oreSulfur);
		OreDictionary.registerOre("oreSaltpeter", GOTRegistry.oreSaltpeter);
		OreDictionary.registerOre("oreSalt", GOTRegistry.oreSalt);
		OreDictionary.registerOre("ingotCopper", GOTRegistry.copperIngot);
		OreDictionary.registerOre("ingotTin", GOTRegistry.tinIngot);
		OreDictionary.registerOre("ingotBronze", GOTRegistry.bronzeIngot);
		OreDictionary.registerOre("ingotSilver", GOTRegistry.silverIngot);
		OreDictionary.registerOre("nuggetSilver", GOTRegistry.silverNugget);
		OreDictionary.registerOre("nuggetCopper", GOTRegistry.copperNugget);
		OreDictionary.registerOre("nuggetAlloySteel", GOTRegistry.alloySteelNugget);
		OreDictionary.registerOre("sulfur", GOTRegistry.sulfur);
		OreDictionary.registerOre("saltpeter", GOTRegistry.saltpeter);
		OreDictionary.registerOre("salt", GOTRegistry.salt);
		OreDictionary.registerOre("clayBall", Items.clay_ball);
		OreDictionary.registerOre("clayBall", GOTRegistry.redClayBall);
		OreDictionary.registerOre("dyeYellow", new ItemStack(GOTRegistry.dye, 1, 0));
		OreDictionary.registerOre("dyeWhite", new ItemStack(GOTRegistry.dye, 1, 1));
		OreDictionary.registerOre("dyeBlue", new ItemStack(GOTRegistry.dye, 1, 2));
		OreDictionary.registerOre("dyeGreen", new ItemStack(GOTRegistry.dye, 1, 3));
		OreDictionary.registerOre("dyeBlack", new ItemStack(GOTRegistry.dye, 1, 4));
		OreDictionary.registerOre("dyeBrown", new ItemStack(GOTRegistry.dye, 1, 5));
		OreDictionary.registerOre("sand", new ItemStack(GOTRegistry.whiteSand, 1, 32767));
		OreDictionary.registerOre("sandstone", new ItemStack(GOTRegistry.redSandstone, 1, 32767));
		OreDictionary.registerOre("sandstone", new ItemStack(GOTRegistry.whiteSandstone, 1, 32767));
		OreDictionary.registerOre("apple", Items.apple);
		OreDictionary.registerOre("apple", GOTRegistry.appleGreen);
		OreDictionary.registerOre("feather", Items.feather);
		OreDictionary.registerOre("feather", GOTRegistry.swanFeather);
		OreDictionary.registerOre("horn", GOTRegistry.rhinoHorn);
		OreDictionary.registerOre("horn", GOTRegistry.whiteBisonHorn);
		OreDictionary.registerOre("horn", GOTRegistry.horn);
		OreDictionary.registerOre("arrowTip", Items.flint);
		OreDictionary.registerOre("arrowTip", GOTRegistry.rhinoHorn);
		OreDictionary.registerOre("arrowTip", GOTRegistry.whiteBisonHorn);
		OreDictionary.registerOre("arrowTip", GOTRegistry.horn);
		OreDictionary.registerOre("poison", GOTRegistry.bottlePoison);
		OreDictionary.registerOre("vine", Blocks.vine);
		OreDictionary.registerOre("vine", GOTRegistry.willowVines);
		OreDictionary.registerOre("vine", GOTRegistry.mirkVines);
	}

	public static void removeRecipesClass(List recipeList, Class<? extends IRecipe> recipeClass) {
		ArrayList<IRecipe> recipesToRemove = new ArrayList<>();
		for (Object obj : recipeList) {
			IRecipe recipe = (IRecipe) obj;
			if (!recipeClass.isAssignableFrom(recipe.getClass())) {
				continue;
			}
			recipesToRemove.add(recipe);
		}
		recipeList.removeAll(recipesToRemove);
	}

	public static void removeRecipesItem(List recipeList, Item outputItem) {
		ArrayList<IRecipe> recipesToRemove = new ArrayList<>();
		for (Object obj : recipeList) {
			IRecipe recipe = (IRecipe) obj;
			ItemStack output = recipe.getRecipeOutput();
			if (output == null || output.getItem() != outputItem) {
				continue;
			}
			recipesToRemove.add(recipe);
		}
		recipeList.removeAll(recipesToRemove);
	}

	public static void removeRecipesItemStack(List recipeList, ItemStack outputItemStack) {
		ArrayList<IRecipe> recipesToRemove = new ArrayList<>();
		for (Object obj : recipeList) {
			IRecipe recipe = (IRecipe) obj;
			ItemStack output = recipe.getRecipeOutput();
			if (output == null || !output.isItemEqual(outputItemStack)) {
				continue;
			}
			recipesToRemove.add(recipe);
		}
		recipeList.removeAll(recipesToRemove);
	}

}