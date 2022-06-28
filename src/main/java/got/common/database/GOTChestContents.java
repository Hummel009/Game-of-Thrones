package got.common.database;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.common.*;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.item.other.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class GOTChestContents {
	public static GOTChestContents ARRYN;
	public static GOTChestContents ASSHAI;
	public static GOTChestContents BEYOND_WALL;
	public static GOTChestContents BRAAVOS;
	public static GOTChestContents CROWNLANDS;
	public static GOTChestContents DORNE;
	public static GOTChestContents DRAGONSTONE;
	public static GOTChestContents GHISCAR;
	public static GOTChestContents GIFT;
	public static GOTChestContents GOLDEN;
	public static GOTChestContents HILLMEN;
	public static GOTChestContents IBBEN;
	public static GOTChestContents IRONBORN;
	public static GOTChestContents LORATH;
	public static GOTChestContents LYS;
	public static GOTChestContents MOSSOVY;
	public static GOTChestContents MYR;
	public static GOTChestContents DOTHRAKI;
	public static GOTChestContents JOGOS;
	public static GOTChestContents NORTH;
	public static GOTChestContents NORVOS;
	public static GOTChestContents PENTOS;
	public static GOTChestContents QARTH;
	public static GOTChestContents QOHOR;
	public static GOTChestContents REACH;
	public static GOTChestContents RIVERLANDS;
	public static GOTChestContents SOTHORYOS;
	public static GOTChestContents STORMLANDS;
	public static GOTChestContents TREASURE;
	public static GOTChestContents TYROSH;
	public static GOTChestContents VOLANTIS;
	public static GOTChestContents WESTERLANDS;
	public static GOTChestContents YI_TI;
	public static GOTChestContents SUMMER;
	public static GOTChestContents LHAZAR;

	static {
		WeightedRandomChestContent[] ASSHAI_W = new WeightedRandomChestContent[16];
		WeightedRandomChestContent[] BEYOND_WALL_W = new WeightedRandomChestContent[21];
		WeightedRandomChestContent[] ESSOS_W = new WeightedRandomChestContent[70];
		WeightedRandomChestContent[] GIFT_W = new WeightedRandomChestContent[63];
		WeightedRandomChestContent[] HILLMEN_W = new WeightedRandomChestContent[36];
		WeightedRandomChestContent[] IBBEN_W = new WeightedRandomChestContent[68];
		WeightedRandomChestContent[] MOSSOVY_W = new WeightedRandomChestContent[79];
		WeightedRandomChestContent[] DOTHRAKI_W = new WeightedRandomChestContent[38];
		WeightedRandomChestContent[] SOTHORYOS_W = new WeightedRandomChestContent[46];
		WeightedRandomChestContent[] TREASURE_W = new WeightedRandomChestContent[24];
		WeightedRandomChestContent[] WESTEROS_W = new WeightedRandomChestContent[78];
		WeightedRandomChestContent[] YI_TI_W = new WeightedRandomChestContent[70];
		WeightedRandomChestContent[] SUMMER_W = new WeightedRandomChestContent[41];
		WeightedRandomChestContent[] LHAZAR_W = new WeightedRandomChestContent[45];

		int i = 0;

		WESTEROS_W[i] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynHelmet), 1, 1, 10);
		i++;
		WESTEROS_W[i] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynChestplate), 1, 1, 10);
		i++;
		WESTEROS_W[i] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynLeggings), 1, 1, 10);
		i++;
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynBoots), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.paper), 1, 4, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.olive), 1, 4, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.almond), 1, 4, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.marzipan), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.marzipanChocolate), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosDagger), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSword), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosSpear), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosHammer), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.aleHorn), 1, 3, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerosBow), 1, 1, 25);
		WESTEROS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100);
		i = 0;

		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosHelmet), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosChestplate), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosLeggings), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosBoots), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSword), 1, 1, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosPolearm), 1, 1, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosDagger), 1, 1, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosSpear), 1, 1, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.essosBow), 1, 1, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 1, 6, 75);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lemon), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lemonCakeItem), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.orange), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lime), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.olive), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.almond), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.marzipan), 1, 3, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.marzipanChocolate), 1, 3, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 4, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.baked_potato), 1, 2, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 2, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 2, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 2, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 4, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 4, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 1, 4, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 75);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCactusLiqueur), 1, 1, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugOrangeJuice), 1, 1, 50);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugLemonade), 1, 1, 10);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugLemonLiqueur), 1, 1, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugLimeLiqueur), 1, 1, 25);
		ESSOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25);

		i = 0;

		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 2, 8, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.melon), 2, 8, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.obsidianShard), 1, 4, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 6, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.reeds), 1, 5, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.reeds), 1, 5, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.driedReeds), 2, 6, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mango), 1, 3, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.banana), 1, 4, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 4, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.melonSoup), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bananaBread), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.cornBread), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.corn), 1, 4, 100);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.cornCooked), 1, 4, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.cornStalk), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 5, 100);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.baked_potato), 1, 2, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMelonLiqueur), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugBananaBeer), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCornLiquor), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugChocolate), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCocoa), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMangoJuice), 1, 1, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 3), 1, 8, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.sothoryosDagger), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.sothoryosDaggerPoisoned), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.sothoryosAxe), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.sarbacane), 1, 1, 25);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.sarbacaneTrap), 2, 8, 50);
		SOTHORYOS_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dartPoisoned), 1, 4, 25);

		i = 0;

		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 2, 8, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.melon), 2, 8, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 100);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiSteelIngot), 1, 3, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 75);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 100);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 3, 100);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 100);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.olive), 1, 5, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.raisins), 1, 4, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pomegranate), 1, 3, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.reeds), 1, 3, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiDagger), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiSword), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiBattleaxe), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiSpear), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiHelmet), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiChestplate), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiLeggings), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiBoots), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugSourMilk), 1, 1, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.milk_bucket), 1, 1, 50);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPomegranateWine), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPomegranateJuice), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugRedWine), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugWhiteWine), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugRedGrapeJuice), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugWhiteGrapeJuice), 1, 1, 10);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.yitiBow), 1, 1, 25);
		YI_TI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 50);

		i = 0;

		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovyChestplate), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovyLeggings), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovyBoots), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mossovySword), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leekSoup), 1, 1, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mushroomPie), 1, 2, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.mushroom_stew), 1, 1, 100);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHelmet), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeChestplate), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeLeggings), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeBoots), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_helmet), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_chestplate), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_leggings), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_boots), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAppleJuice), 1, 1, 50);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25);
		MOSSOVY_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100);

		i = 0;

		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftHelmet), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftChestplate), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftLeggings), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.giftBoots), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25);
		GIFT_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100);

		i = 0;

		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.coin, 1, 0), 1, 20, 200);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.coin, 1, 1), 1, 8, 20);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.coin, 1, 2), 1, 2, 5);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.gold_nugget), 1, 5, 100);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.gold_ingot), 1, 4, 75);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverNugget), 1, 5, 100);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverIngot), 1, 4, 75);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 25);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeSword), 1, 1, 25);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHelmet), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeChestplate), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeLeggings), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeBoots), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 25);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 25);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_helmet), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_chestplate), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_leggings), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_boots), 1, 1, 10);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 20);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 20);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletGold), 1, 3, 20);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletSilver), 1, 3, 20);
		TREASURE_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 20);

		i = 0;

		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 3, 100);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 8, 16, 100);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.fur), 1, 4, 50);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.dragon_egg), 1, 1, 50);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.skullCup), 1, 3, 50);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 50);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiDagger), 1, 1, 25);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiDaggerPoisoned), 1, 1, 25);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiSword), 1, 1, 25);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiBattleaxe), 1, 1, 25);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiSpear), 1, 1, 25);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiHammer), 1, 1, 25);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.asshaiStaff), 1, 1, 25);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bottlePoison), 1, 1, 50);
		ASSHAI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.saddle), 1, 1, 50);

		i = 0;

		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 75);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 50);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerRaw), 1, 3, 100);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 25);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 25);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironBattleaxe), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDaggerPoisoned), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironPike), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironSpear), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironThrowingAxe), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeBattleaxe), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeSword), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeSpear), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeThrowingAxe), 1, 1, 10);
		BEYOND_WALL_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 50);

		i = 0;

		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.horn), 1, 2, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lettuce), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 3, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pear), 1, 3, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.plum), 1, 3, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeHoe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeAxe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeShovel), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeDagger), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.flintDagger), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenSword), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenHarpoon), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenChestplate), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenLeggings), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ibbenBoots), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leatherHat), 1, 1, 50);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.aleHorn), 1, 3, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAle), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCider), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugPerry), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugMead), 1, 1, 10);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironCrossbow), 1, 1, 25);
		IBBEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100);

		i = 0;

		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiHelmet), 1, 1, 10);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiChestplate), 1, 1, 10);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiLeggings), 1, 1, 10);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dothrakiBoots), 1, 1, 10);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadSword), 1, 1, 100);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadSpear), 1, 1, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadBattleaxe), 1, 1, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.nomadBow), 1, 1, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 100);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 200);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 100);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugCactusLiqueur), 1, 1, 100);
		DOTHRAKI_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25);

		i = 0;

		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 2, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 2, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.fur), 1, 2, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 3, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 2, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.leek), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnip), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.turnipCooked), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 2, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.appleGreen), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerRaw), 1, 3, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.deerCooked), 1, 3, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_sword), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wooden_sword), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.stoneSpear), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironDagger), 1, 1, 50);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironSpear), 1, 1, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironBattleaxe), 1, 1, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenHelmet), 1, 1, 20);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenChestplate), 1, 1, 20);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenLeggings), 1, 1, 20);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.hillmenBoots), 1, 1, 20);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 75);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 5, 100);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.aleHorn), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.skullCup), 1, 3, 25);
		HILLMEN_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 75);

		i = 0;

		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerSword), 1, 1, 100);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerDagger), 1, 1, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerDaggerPoisoned), 1, 1, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerPike), 1, 1, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerHelmet), 1, 1, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerChestplate), 1, 1, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerLeggings), 1, 1, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.summerBoots), 1, 1, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.water_bucket), 1, 1, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 100);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 2, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 2, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 200);
		SUMMER_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25);

		i = 0;

		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldRing), 1, 1, 10);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.silverRing), 1, 1, 10);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarSword), 1, 1, 100);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarDagger), 1, 1, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarDaggerPoisoned), 1, 1, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarClub), 1, 1, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarHelmet), 1, 1, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarChestplate), 1, 1, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarLeggings), 1, 1, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarBoots), 1, 1, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lhazarSpear), 1, 1, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.water_bucket), 1, 1, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.bronzeIngot), 1, 3, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.oliveBread), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.date), 1, 3, 100);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.rabbitCooked), 1, 2, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.muttonCooked), 1, 2, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.camelCooked), 1, 2, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lionCooked), 1, 2, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.zebraCooked), 1, 2, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.kebab), 1, 4, 100);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.shishKebab), 1, 2, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.saltedFlesh), 1, 3, 100);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicPlate), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.woodPlate), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mug), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ceramicMug), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.waterskin), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletCopper), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.gobletWood), 1, 3, 25);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.mugAraq), 1, 1, 200);
		LHAZAR_W[i++] = new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25);

		WeightedRandomChestContent[] ARRYN_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] BRAAVOS_W = ESSOS_W.clone();
		WeightedRandomChestContent[] CROWNLANDS_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] DORNE_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] DRAGONSTONE_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] GHISCAR_W = ESSOS_W.clone();
		WeightedRandomChestContent[] GOLDEN_W = ESSOS_W.clone();
		WeightedRandomChestContent[] IRONBORN_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] LORATH_W = ESSOS_W.clone();
		WeightedRandomChestContent[] LYS_W = ESSOS_W.clone();
		WeightedRandomChestContent[] MYR_W = ESSOS_W.clone();
		WeightedRandomChestContent[] NORTH_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] NORVOS_W = ESSOS_W.clone();
		WeightedRandomChestContent[] PENTOS_W = ESSOS_W.clone();
		WeightedRandomChestContent[] QARTH_W = ESSOS_W.clone();
		WeightedRandomChestContent[] QOHOR_W = ESSOS_W.clone();
		WeightedRandomChestContent[] REACH_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] RIVERLANDS_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] STORMLANDS_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] TYROSH_W = ESSOS_W.clone();
		WeightedRandomChestContent[] VOLANTIS_W = ESSOS_W.clone();
		WeightedRandomChestContent[] WESTERLANDS_W = WESTEROS_W.clone();
		WeightedRandomChestContent[] JOGOS_W = DOTHRAKI_W.clone();

		ASSHAI = new GOTChestContents(4, 6, ASSHAI_W).enablePouches().setLore(20, GOTLore.LoreCategory.ASSHAI);
		BEYOND_WALL = new GOTChestContents(4, 6, BEYOND_WALL_W).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK);
		HILLMEN = new GOTChestContents(4, 6, HILLMEN_W).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK);
		IBBEN = new GOTChestContents(4, 6, IBBEN_W).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK);
		MOSSOVY = new GOTChestContents(4, 6, MOSSOVY_W).enablePouches().setDrinkVessels(GOTFoods.WILD_DRINK).setLore(20, GOTLore.LoreCategory.ASSHAI);
		DOTHRAKI = new GOTChestContents(4, 6, DOTHRAKI_W).enablePouches().setDrinkVessels(GOTFoods.NOMAD_DRINK);
		LHAZAR = new GOTChestContents(4, 6, LHAZAR_W).enablePouches().setDrinkVessels(GOTFoods.NOMAD_DRINK);
		SOTHORYOS = new GOTChestContents(4, 6, SOTHORYOS_W).enablePouches().setDrinkVessels(GOTFoods.SOTHORYOS_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);
		TREASURE = new GOTChestContents(4, 6, TREASURE_W).enablePouches();
		YI_TI = new GOTChestContents(4, 6, YI_TI_W).enablePouches().setDrinkVessels(GOTFoods.RICH_DRINK).setLore(20, GOTLore.LoreCategory.YITI);
		SUMMER = new GOTChestContents(4, 6, SUMMER_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);

		JOGOS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosHelmet), 1, 1, 25);
		JOGOS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosChestplate), 1, 1, 25);
		JOGOS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosLeggings), 1, 1, 25);
		JOGOS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.jogosBoots), 1, 1, 25);
		JOGOS = new GOTChestContents(4, 6, JOGOS_W).enablePouches().setDrinkVessels(GOTFoods.NOMAD_DRINK);

		ARRYN_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynHelmet), 1, 1, 25);
		ARRYN_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynChestplate), 1, 1, 25);
		ARRYN_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynLeggings), 1, 1, 25);
		ARRYN_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.arrynBoots), 1, 1, 25);
		ARRYN = new GOTChestContents(4, 6, ARRYN_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		CROWNLANDS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsHelmet), 1, 1, 25);
		CROWNLANDS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsChestplate), 1, 1, 25);
		CROWNLANDS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsLeggings), 1, 1, 25);
		CROWNLANDS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.crownlandsBoots), 1, 1, 25);
		CROWNLANDS = new GOTChestContents(4, 6, CROWNLANDS_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		DORNE_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneHelmet), 1, 1, 25);
		DORNE_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneChestplate), 1, 1, 25);
		DORNE_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneLeggings), 1, 1, 25);
		DORNE_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dorneBoots), 1, 1, 25);
		DORNE = new GOTChestContents(4, 6, DORNE_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		DRAGONSTONE_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneHelmet), 1, 1, 25);
		DRAGONSTONE_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneChestplate), 1, 1, 25);
		DRAGONSTONE_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneLeggings), 1, 1, 25);
		DRAGONSTONE_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.dragonstoneBoots), 1, 1, 25);
		DRAGONSTONE = new GOTChestContents(4, 6, DRAGONSTONE_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		IRONBORN_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornHelmet), 1, 1, 25);
		IRONBORN_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornChestplate), 1, 1, 25);
		IRONBORN_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornLeggings), 1, 1, 25);
		IRONBORN_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ironbornBoots), 1, 1, 25);
		IRONBORN = new GOTChestContents(4, 6, IRONBORN_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		NORTH_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.northHelmet), 1, 1, 25);
		NORTH_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.northChestplate), 1, 1, 25);
		NORTH_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.northLeggings), 1, 1, 25);
		NORTH_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.northBoots), 1, 1, 25);
		NORTH = new GOTChestContents(4, 6, NORTH_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		REACH_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachHelmet), 1, 1, 25);
		REACH_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachChestplate), 1, 1, 25);
		REACH_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachLeggings), 1, 1, 25);
		REACH_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.reachBoots), 1, 1, 25);
		REACH = new GOTChestContents(4, 6, REACH_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		RIVERLANDS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsHelmet), 1, 1, 25);
		RIVERLANDS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsChestplate), 1, 1, 25);
		RIVERLANDS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsLeggings), 1, 1, 25);
		RIVERLANDS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.riverlandsBoots), 1, 1, 25);
		RIVERLANDS = new GOTChestContents(4, 6, RIVERLANDS_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		STORMLANDS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsHelmet), 1, 1, 25);
		STORMLANDS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsChestplate), 1, 1, 25);
		STORMLANDS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsLeggings), 1, 1, 25);
		STORMLANDS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.stormlandsBoots), 1, 1, 25);
		STORMLANDS = new GOTChestContents(4, 6, STORMLANDS_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		WESTERLANDS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsHelmet), 1, 1, 25);
		WESTERLANDS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsChestplate), 1, 1, 25);
		WESTERLANDS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsLeggings), 1, 1, 25);
		WESTERLANDS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.westerlandsBoots), 1, 1, 25);
		WESTERLANDS = new GOTChestContents(4, 6, WESTERLANDS_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		GIFT = new GOTChestContents(4, 6, GIFT_W).enablePouches().setDrinkVessels(GOTFoods.WESTEROS_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		BRAAVOS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosHelmet), 1, 1, 25);
		BRAAVOS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosChestplate), 1, 1, 25);
		BRAAVOS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosLeggings), 1, 1, 25);
		BRAAVOS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.braavosBoots), 1, 1, 25);
		BRAAVOS = new GOTChestContents(4, 6, BRAAVOS_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		GHISCAR_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarHelmet), 1, 1, 25);
		GHISCAR_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarChestplate), 1, 1, 25);
		GHISCAR_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarLeggings), 1, 1, 25);
		GHISCAR_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.ghiscarBoots), 1, 1, 25);
		GHISCAR = new GOTChestContents(4, 6, GHISCAR_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		LORATH_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathHelmet), 1, 1, 25);
		LORATH_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathChestplate), 1, 1, 25);
		LORATH_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathLeggings), 1, 1, 25);
		LORATH_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lorathBoots), 1, 1, 25);
		LORATH = new GOTChestContents(4, 6, LORATH_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		LYS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysHelmet), 1, 1, 25);
		LYS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysChestplate), 1, 1, 25);
		LYS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysLeggings), 1, 1, 25);
		LYS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.lysBoots), 1, 1, 25);
		LYS = new GOTChestContents(4, 6, LYS_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		MYR_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrHelmet), 1, 1, 25);
		MYR_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrChestplate), 1, 1, 25);
		MYR_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrLeggings), 1, 1, 25);
		MYR_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.myrBoots), 1, 1, 25);
		MYR = new GOTChestContents(4, 6, MYR_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		NORVOS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosHelmet), 1, 1, 25);
		NORVOS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosChestplate), 1, 1, 25);
		NORVOS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosLeggings), 1, 1, 25);
		NORVOS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.norvosBoots), 1, 1, 25);
		NORVOS = new GOTChestContents(4, 6, NORVOS_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		PENTOS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosHelmet), 1, 1, 25);
		PENTOS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosChestplate), 1, 1, 25);
		PENTOS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosLeggings), 1, 1, 25);
		PENTOS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.pentosBoots), 1, 1, 25);
		PENTOS = new GOTChestContents(4, 6, PENTOS_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		QARTH_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthHelmet), 1, 1, 25);
		QARTH_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthChestplate), 1, 1, 25);
		QARTH_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthLeggings), 1, 1, 25);
		QARTH_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qarthBoots), 1, 1, 25);
		QARTH = new GOTChestContents(4, 6, QARTH_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		QOHOR_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorHelmet), 1, 1, 25);
		QOHOR_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorChestplate), 1, 1, 25);
		QOHOR_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorLeggings), 1, 1, 25);
		QOHOR_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.qohorBoots), 1, 1, 25);
		QOHOR = new GOTChestContents(4, 6, QOHOR_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		TYROSH_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshHelmet), 1, 1, 25);
		TYROSH_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshChestplate), 1, 1, 25);
		TYROSH_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshLeggings), 1, 1, 25);
		TYROSH_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.tyroshBoots), 1, 1, 25);
		TYROSH = new GOTChestContents(4, 6, TYROSH_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		VOLANTIS_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisHelmet), 1, 1, 25);
		VOLANTIS_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisChestplate), 1, 1, 25);
		VOLANTIS_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisLeggings), 1, 1, 25);
		VOLANTIS_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.volantisBoots), 1, 1, 25);
		VOLANTIS = new GOTChestContents(4, 6, VOLANTIS_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		GOLDEN_W[0] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldHelmet), 1, 1, 25);
		GOLDEN_W[1] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldChestplate), 1, 1, 25);
		GOLDEN_W[2] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldLeggings), 1, 1, 25);
		GOLDEN_W[3] = new WeightedRandomChestContent(new ItemStack(GOTRegistry.goldBoots), 1, 1, 25);
		GOLDEN = new GOTChestContents(4, 6, GOLDEN_W).enablePouches().setDrinkVessels(GOTFoods.ESSOS_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

	}

	public WeightedRandomChestContent[] items;
	public int minItems;
	public int maxItems;
	public boolean pouches = false;
	public GOTItemMug.Vessel[] vesselTypes;
	public List<GOTLore.LoreCategory> loreCategories = new ArrayList<>();
	public int loreChance = 10;

	public GOTChestContents(int i, int j, WeightedRandomChestContent[] w) {
		minItems = i;
		maxItems = j;
		items = w;
	}

	public GOTChestContents enablePouches() {
		pouches = true;
		return this;
	}

	public ItemStack getOneItem(Random random, boolean isNPCDrop) {
		InventoryBasic drops = new InventoryBasic("oneItem", false, 1);
		GOTChestContents.fillInventory(drops, random, this, 1, isNPCDrop);
		ItemStack item = drops.getStackInSlot(0);
		item.stackSize = 1;
		return item;
	}

	public GOTChestContents setDrinkVessels(GOTFoods foods) {
		return this.setDrinkVessels(foods.getDrinkVessels());
	}

	public GOTChestContents setDrinkVessels(GOTItemMug.Vessel... v) {
		vesselTypes = v;
		return this;
	}

	public GOTChestContents setLore(int chance, GOTLore.LoreCategory... categories) {
		loreCategories = Arrays.asList(categories);
		loreChance = chance;
		return this;
	}

	public static void fillChest(World world, Random random, int i, int j, int k, GOTChestContents itemPool) {
		GOTChestContents.fillChest(world, random, i, j, k, itemPool, -1);
	}

	public static void fillChest(World world, Random random, int i, int j, int k, GOTChestContents itemPool, int amount) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (tileentity == null || !(tileentity instanceof IInventory)) {
			if (j >= 0 && j < 256) {
				FMLLog.warning("Warning: GOTChestContents attempted to fill a chest at " + i + ", " + j + ", " + k + " which does not exist");
			}
			return;
		}
		GOTChestContents.fillInventory((IInventory) tileentity, random, itemPool, amount);
	}

	public static void fillInventory(IInventory inventory, Random random, GOTChestContents itemPool, int amount) {
		GOTChestContents.fillInventory(inventory, random, itemPool, amount, false);
	}

	public static void fillInventory(IInventory inventory, Random random, GOTChestContents itemPool, int amount, boolean isNPCDrop) {
		if (amount == -1) {
			amount = GOTChestContents.getRandomItemAmount(itemPool, random);
		} else if (amount <= 0) {
			throw new IllegalArgumentException("GOTChestContents tried to fill a chest with " + amount + " items");
		}
		for (int i = 0; i < amount; ++i) {
			WeightedRandomChestContent wrcc = (WeightedRandomChestContent) WeightedRandom.getRandomItem(random, itemPool.items);
			for (ItemStack itemstack : ChestGenHooks.generateStacks(random, wrcc.theItemId, wrcc.theMinimumChanceToGenerateItem, wrcc.theMaximumChanceToGenerateItem)) {
				Item item;
				if (!isNPCDrop && itemPool.pouches) {
					if (random.nextInt(50) == 0) {
						itemstack = new ItemStack(GOTRegistry.pouch, 1, GOTItemPouch.getRandomPouchSize(random));
					} else if (random.nextInt(50) == 0) {
						itemstack = GOTItemModifierTemplate.getRandomCommonTemplate(random);
					}
				}
				if (!itemPool.loreCategories.isEmpty()) {
					GOTLore lore;
					int loreChance = itemPool.loreChance;
					int minDropLoreChance = 8;
					if (isNPCDrop && loreChance > minDropLoreChance) {
						loreChance = (int) (loreChance * 0.75f);
						loreChance = Math.max(loreChance, minDropLoreChance);
					}
					if (random.nextInt(loreChance = Math.max(loreChance, 1)) == 0 && (lore = GOTLore.getMultiRandomLore(itemPool.loreCategories, random, false)) != null) {
						itemstack = lore.createLoreBook(random);
					}
				}
				if (itemstack.isItemStackDamageable() && !itemstack.getHasSubtypes()) {
					itemstack.setItemDamage(MathHelper.floor_double(itemstack.getMaxDamage() * MathHelper.randomFloatClamp(random, 0.0f, 0.75f)));
				}
				if (itemstack.stackSize > itemstack.getMaxStackSize()) {
					itemstack.stackSize = itemstack.getMaxStackSize();
				}
				if (GOTConfig.enchantingGOT) {
					boolean skilful = !isNPCDrop && random.nextInt(5) == 0;
					GOTEnchantmentHelper.applyRandomEnchantments(itemstack, random, skilful, false);
				}
				if ((item = itemstack.getItem()) instanceof GOTItemMug) {
					GOTItemMug.Vessel[] vessels;
					if (((GOTItemMug) item).isBrewable) {
						GOTItemMug.setStrengthMeta(itemstack, 1 + random.nextInt(3));
					}
					if (GOTItemMug.isItemFullDrink(itemstack) && (vessels = itemPool.vesselTypes) != null) {
						GOTItemMug.Vessel v = vessels[random.nextInt(vessels.length)];
						GOTItemMug.setVessel(itemstack, v, true);
					}
				}
				inventory.setInventorySlotContents(random.nextInt(inventory.getSizeInventory()), itemstack);
			}
		}
	}

	public static int getRandomItemAmount(GOTChestContents itemPool, Random random) {
		return MathHelper.getRandomIntegerInRange(random, itemPool.minItems, itemPool.maxItems);
	}
}
