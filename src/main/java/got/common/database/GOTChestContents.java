package got.common.database;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTConfig;
import got.common.GOTLore;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.item.other.GOTItemModifierTemplate;
import got.common.item.other.GOTItemMug;
import got.common.item.other.GOTItemPouch;
import got.common.item.weapon.GOTItemLingeringPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ChestGenHooks;

import java.util.*;

public class GOTChestContents {
	public static final GOTChestContents ARRYN;
	public static final GOTChestContents ASSHAI;
	public static final GOTChestContents BEYOND_WALL;
	public static final GOTChestContents BRAAVOS;
	public static final GOTChestContents CROWNLANDS;
	public static final GOTChestContents DORNE;
	public static final GOTChestContents DOTHRAKI;
	public static final GOTChestContents DRAGONSTONE;
	public static final GOTChestContents GHISCAR;
	public static final GOTChestContents GIFT;
	public static final GOTChestContents GOLDEN_COMPANY;
	public static final GOTChestContents HILLMEN;
	public static final GOTChestContents IBBEN;
	public static final GOTChestContents IRONBORN;
	public static final GOTChestContents JOGOS_NHAI;
	public static final GOTChestContents LHAZAR;
	public static final GOTChestContents LORATH;
	public static final GOTChestContents LYS;
	public static final GOTChestContents MOSSOVY;
	public static final GOTChestContents MYR;
	public static final GOTChestContents NORTH;
	public static final GOTChestContents NORVOS;
	public static final GOTChestContents PENTOS;
	public static final GOTChestContents QARTH;
	public static final GOTChestContents QOHOR;
	public static final GOTChestContents REACH;
	public static final GOTChestContents RIVERLANDS;
	public static final GOTChestContents SOTHORYOS;
	public static final GOTChestContents STORMLANDS;
	public static final GOTChestContents SUMMER;
	public static final GOTChestContents TREASURE;
	public static final GOTChestContents TYROSH;
	public static final GOTChestContents VOLANTIS;
	public static final GOTChestContents WESTERLANDS;
	public static final GOTChestContents YI_TI;

	static {
		Collection<WeightedRandomChestContent> common = new ArrayList<>();
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 2));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 2));

		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailBoots), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailChestplate), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailHelmet), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailLeggings), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeBoots), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChestplate), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHelmet), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeLeggings), 1, 1, 5));

		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 2, 40));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 2, 30));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 2, 20));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 2, 50));

		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeAxe), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeBattleaxe), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeCrossbow), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDagger), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDaggerPoisoned), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHammer), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeMattock), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeScimitar), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeSpear), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeSword), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeThrowingAxe), 1, 1, 10));

		common.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.iron_boots), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.iron_chestplate), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.iron_helmet), 1, 1, 5));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.iron_leggings), 1, 1, 5));

		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 2, 30));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 2, 30));

		common.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironBattleaxe), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironCrossbow), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDaggerPoisoned), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironHammer), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironMattock), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironScimitar), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironSpear), 1, 1, 10));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironThrowingAxe), 1, 1, 10));

		common.add(new WeightedRandomChestContent(new ItemStack(GOTBlocks.rope), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leatherHat), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.clayMug), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.plate), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicPlate), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.clayPlate), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletBronze), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.horn), 1, 2, 25));

		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAle), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCider), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCornLiquor), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugMapleBeer), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPerry), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPlumKvass), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugRedWine), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugSourMilk), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugVodka), 1, 1, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugWhiteWine), 1, 1, 50));

		common.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 5, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crossbowBolt), 2, 5, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 75));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.longbow), 1, 1, 75));

		common.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 3, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.milk_bucket), 1, 1, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.water_bucket), 1, 1, 25));

		common.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.saddle), 1, 1, 25));

		common.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 25));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 2, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.fur), 1, 3, 100));

		common.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));

		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.beaverCooked), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.muttonCooked), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.rabbitCooked), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Blocks.wool), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTBlocks.cornStalk), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.cherry), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.corn), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.grapeRed), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.grapeWhite), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pear), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.plum), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.fish, 1, 0), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.fish, 1, 1), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.fish, 1, 2), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(GOTItems.cornBread), 1, 3, 100));
		common.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));

		Collection<WeightedRandomChestContent> treasure = new ArrayList<>();
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 0), 1, 5, 256));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 1), 1, 4, 64));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 2), 1, 3, 16));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 3), 1, 2, 4));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 4), 1, 1, 1));
		treasure.add(new WeightedRandomChestContent(new ItemStack(Items.gold_ingot), 1, 1, 1));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverIngot), 1, 1, 4));
		treasure.add(new WeightedRandomChestContent(new ItemStack(Items.gold_nugget), 1, 1, 9));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverNugget), 1, 1, 36));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 3));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 12));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 48));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 48));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletGold), 1, 1, 2));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletSilver), 1, 1, 8));
		treasure.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 1, 32));
		TREASURE = new GOTChestContents(4, 6, treasure).enablePouches();

		Collection<WeightedRandomChestContent> asshai = new ArrayList<>();
		asshai.add(new WeightedRandomChestContent(new ItemStack(Blocks.dragon_egg), 1, 1, 100));
		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sulfurMatch), 1, 1, 75));

		asshai.add(new WeightedRandomChestContent(new ItemStack(Blocks.brown_mushroom, 1, 0), 1, 3, 50));
		asshai.add(new WeightedRandomChestContent(new ItemStack(Blocks.red_mushroom, 1, 0), 1, 3, 50));

		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiShadowbinderStaff), 1, 1, 10));

		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.obsidianShard), 1, 3, 25));
		asshai.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 3, 25));

		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.skullCup), 1, 3, 20));
		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 20));
		asshai.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 3, 20));

		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTBlocks.bomb), 1, 1, 10));
		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.firePot), 1, 1, 10));

		asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bottlePoison), 1, 2, 5));

		List<Integer> ids = GOTItemLingeringPotion.getPotionsSubIds();
		for (int i : ids) {
			asshai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lingeringPotion, 1, i), 1, 2, 5));
			asshai.add(new WeightedRandomChestContent(new ItemStack(Items.potionitem, 1, i), 1, 2, 5));
		}

		ASSHAI = new GOTChestContents(4, 6, asshai).enablePouches().setLore(20, GOTLore.LoreCategory.ASSHAI);

		Collection<WeightedRandomChestContent> dothraki = new ArrayList<>(common);
		dothraki.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiHelmet), 1, 1, 10));
		dothraki.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiChestplate), 1, 1, 10));
		dothraki.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiLeggings), 1, 1, 10));
		dothraki.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiBoots), 1, 1, 10));
		DOTHRAKI = new GOTChestContents(4, 6, dothraki).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);

		Collection<WeightedRandomChestContent> jogosNhai = new ArrayList<>(common);
		jogosNhai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiHelmet), 1, 1, 25));
		jogosNhai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiChestplate), 1, 1, 25));
		jogosNhai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiLeggings), 1, 1, 25));
		jogosNhai.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiBoots), 1, 1, 25));
		JOGOS_NHAI = new GOTChestContents(4, 6, jogosNhai).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);

		Collection<WeightedRandomChestContent> arryn = new ArrayList<>(common);
		arryn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynHelmet), 1, 1, 25));
		arryn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynChestplate), 1, 1, 25));
		arryn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynLeggings), 1, 1, 25));
		arryn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynBoots), 1, 1, 25));
		ARRYN = new GOTChestContents(4, 6, arryn).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> crownlands = new ArrayList<>(common);
		crownlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsHelmet), 1, 1, 25));
		crownlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsChestplate), 1, 1, 25));
		crownlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsLeggings), 1, 1, 25));
		crownlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsBoots), 1, 1, 25));
		CROWNLANDS = new GOTChestContents(4, 6, crownlands).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> dorne = new ArrayList<>(common);
		dorne.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneHelmet), 1, 1, 25));
		dorne.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneChestplate), 1, 1, 25));
		dorne.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneLeggings), 1, 1, 25));
		dorne.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneBoots), 1, 1, 25));
		DORNE = new GOTChestContents(4, 6, dorne).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> dragonstone = new ArrayList<>(common);
		dragonstone.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneHelmet), 1, 1, 25));
		dragonstone.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneChestplate), 1, 1, 25));
		dragonstone.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneLeggings), 1, 1, 25));
		dragonstone.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneBoots), 1, 1, 25));
		DRAGONSTONE = new GOTChestContents(4, 6, dragonstone).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> ironborn = new ArrayList<>(common);
		ironborn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornHelmet), 1, 1, 25));
		ironborn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornChestplate), 1, 1, 25));
		ironborn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornLeggings), 1, 1, 25));
		ironborn.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornBoots), 1, 1, 25));
		IRONBORN = new GOTChestContents(4, 6, ironborn).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> north = new ArrayList<>(common);
		north.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northHelmet), 1, 1, 25));
		north.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northChestplate), 1, 1, 25));
		north.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northLeggings), 1, 1, 25));
		north.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northBoots), 1, 1, 25));
		NORTH = new GOTChestContents(4, 6, north).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> reach = new ArrayList<>(common);
		reach.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachHelmet), 1, 1, 25));
		reach.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachChestplate), 1, 1, 25));
		reach.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachLeggings), 1, 1, 25));
		reach.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachBoots), 1, 1, 25));
		REACH = new GOTChestContents(4, 6, reach).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> riverlands = new ArrayList<>(common);
		riverlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsHelmet), 1, 1, 25));
		riverlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsChestplate), 1, 1, 25));
		riverlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsLeggings), 1, 1, 25));
		riverlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsBoots), 1, 1, 25));
		RIVERLANDS = new GOTChestContents(4, 6, riverlands).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> stormlands = new ArrayList<>(common);
		stormlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsHelmet), 1, 1, 25));
		stormlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsChestplate), 1, 1, 25));
		stormlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsLeggings), 1, 1, 25));
		stormlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsBoots), 1, 1, 25));
		STORMLANDS = new GOTChestContents(4, 6, stormlands).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> westerlands = new ArrayList<>(common);
		westerlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsHelmet), 1, 1, 25));
		westerlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsChestplate), 1, 1, 25));
		westerlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsLeggings), 1, 1, 25));
		westerlands.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsBoots), 1, 1, 25));
		WESTERLANDS = new GOTChestContents(4, 6, westerlands).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> braavos = new ArrayList<>(common);
		braavos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosHelmet), 1, 1, 25));
		braavos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosChestplate), 1, 1, 25));
		braavos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosLeggings), 1, 1, 25));
		braavos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosBoots), 1, 1, 25));
		BRAAVOS = new GOTChestContents(4, 6, braavos).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> ghiscar = new ArrayList<>(common);
		ghiscar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarHelmet), 1, 1, 25));
		ghiscar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarChestplate), 1, 1, 25));
		ghiscar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarLeggings), 1, 1, 25));
		ghiscar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarBoots), 1, 1, 25));
		GHISCAR = new GOTChestContents(4, 6, ghiscar).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> lhazar = new ArrayList<>(common);
		lhazar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarHelmet), 1, 1, 25));
		lhazar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarChestplate), 1, 1, 25));
		lhazar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarLeggings), 1, 1, 25));
		lhazar.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarBoots), 1, 1, 25));
		LHAZAR = new GOTChestContents(4, 6, lhazar).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> lorath = new ArrayList<>(common);
		lorath.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathHelmet), 1, 1, 25));
		lorath.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathChestplate), 1, 1, 25));
		lorath.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathLeggings), 1, 1, 25));
		lorath.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathBoots), 1, 1, 25));
		LORATH = new GOTChestContents(4, 6, lorath).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> lys = new ArrayList<>(common);
		lys.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysHelmet), 1, 1, 25));
		lys.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysChestplate), 1, 1, 25));
		lys.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysLeggings), 1, 1, 25));
		lys.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysBoots), 1, 1, 25));
		LYS = new GOTChestContents(4, 6, lys).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> myr = new ArrayList<>(common);
		myr.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrHelmet), 1, 1, 25));
		myr.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrChestplate), 1, 1, 25));
		myr.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrLeggings), 1, 1, 25));
		myr.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrBoots), 1, 1, 25));
		MYR = new GOTChestContents(4, 6, myr).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> norvos = new ArrayList<>(common);
		norvos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosHelmet), 1, 1, 25));
		norvos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosChestplate), 1, 1, 25));
		norvos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosLeggings), 1, 1, 25));
		norvos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosBoots), 1, 1, 25));
		NORVOS = new GOTChestContents(4, 6, norvos).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> pentos = new ArrayList<>(common);
		pentos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosHelmet), 1, 1, 25));
		pentos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosChestplate), 1, 1, 25));
		pentos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosLeggings), 1, 1, 25));
		pentos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosBoots), 1, 1, 25));
		PENTOS = new GOTChestContents(4, 6, pentos).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> qarth = new ArrayList<>(common);
		qarth.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthHelmet), 1, 1, 25));
		qarth.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthChestplate), 1, 1, 25));
		qarth.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthLeggings), 1, 1, 25));
		qarth.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthBoots), 1, 1, 25));
		QARTH = new GOTChestContents(4, 6, qarth).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> qohor = new ArrayList<>(common);
		qohor.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorHelmet), 1, 1, 25));
		qohor.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorChestplate), 1, 1, 25));
		qohor.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorLeggings), 1, 1, 25));
		qohor.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorBoots), 1, 1, 25));
		QOHOR = new GOTChestContents(4, 6, qohor).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> tyrosh = new ArrayList<>(common);
		tyrosh.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshHelmet), 1, 1, 25));
		tyrosh.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshChestplate), 1, 1, 25));
		tyrosh.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshLeggings), 1, 1, 25));
		tyrosh.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshBoots), 1, 1, 25));
		TYROSH = new GOTChestContents(4, 6, tyrosh).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> volantis = new ArrayList<>(common);
		volantis.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisHelmet), 1, 1, 25));
		volantis.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisChestplate), 1, 1, 25));
		volantis.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisLeggings), 1, 1, 25));
		volantis.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisBoots), 1, 1, 25));
		VOLANTIS = new GOTChestContents(4, 6, volantis).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> goldenCompany = new ArrayList<>(common);
		goldenCompany.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyHelmet), 1, 1, 25));
		goldenCompany.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyChestplate), 1, 1, 25));
		goldenCompany.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyLeggings), 1, 1, 25));
		goldenCompany.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyBoots), 1, 1, 25));
		GOLDEN_COMPANY = new GOTChestContents(4, 6, goldenCompany).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);

		Collection<WeightedRandomChestContent> beyondWall = new ArrayList<>(common);
		beyondWall.add(new WeightedRandomChestContent(new ItemStack(GOTItems.furHelmet), 1, 1, 25));
		beyondWall.add(new WeightedRandomChestContent(new ItemStack(GOTItems.furChestplate), 1, 1, 25));
		beyondWall.add(new WeightedRandomChestContent(new ItemStack(GOTItems.furLeggings), 1, 1, 25));
		beyondWall.add(new WeightedRandomChestContent(new ItemStack(GOTItems.furBoots), 1, 1, 25));
		BEYOND_WALL = new GOTChestContents(4, 6, beyondWall).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);

		Collection<WeightedRandomChestContent> gift = new ArrayList<>(common);
		gift.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftHelmet), 1, 1, 25));
		gift.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftChestplate), 1, 1, 25));
		gift.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftLeggings), 1, 1, 25));
		gift.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftBoots), 1, 1, 25));
		GIFT = new GOTChestContents(4, 6, gift).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);

		Collection<WeightedRandomChestContent> hillmen = new ArrayList<>(common);
		hillmen.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenHelmet), 1, 1, 25));
		hillmen.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenChestplate), 1, 1, 25));
		hillmen.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenLeggings), 1, 1, 25));
		hillmen.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenBoots), 1, 1, 25));
		HILLMEN = new GOTChestContents(4, 6, hillmen).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);

		Collection<WeightedRandomChestContent> ibben = new ArrayList<>(common);
		ibben.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ibbenChestplate), 1, 1, 25));
		ibben.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ibbenLeggings), 1, 1, 25));
		ibben.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ibbenBoots), 1, 1, 25));
		IBBEN = new GOTChestContents(4, 6, ibben).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);

		Collection<WeightedRandomChestContent> mossovy = new ArrayList<>(common);
		mossovy.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mossovyChestplate), 1, 1, 25));
		mossovy.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mossovyLeggings), 1, 1, 25));
		mossovy.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mossovyBoots), 1, 1, 25));
		MOSSOVY = new GOTChestContents(4, 6, mossovy).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);

		Collection<WeightedRandomChestContent> sothoryos = new ArrayList<>(common);
		sothoryos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sothoryosHelmet), 1, 1, 25));
		sothoryos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sothoryosChestplate), 1, 1, 25));
		sothoryos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sothoryosLeggings), 1, 1, 25));
		sothoryos.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sothoryosBoots), 1, 1, 25));
		SOTHORYOS = new GOTChestContents(4, 6, sothoryos).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);

		Collection<WeightedRandomChestContent> summer = new ArrayList<>(common);
		summer.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerHelmet), 1, 1, 25));
		summer.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerChestplate), 1, 1, 25));
		summer.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerLeggings), 1, 1, 25));
		summer.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerBoots), 1, 1, 25));
		SUMMER = new GOTChestContents(4, 6, summer).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);

		Collection<WeightedRandomChestContent> yiTi = new ArrayList<>(common);
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiHelmet), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiChestplate), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiLeggings), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBoots), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBombardierHelmet), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBombardierChestplate), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBombardierLeggings), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBombardierBoots), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiSamuraiHelmet), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiSamuraiChestplate), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiSamuraiLeggings), 1, 1, 25));
		yiTi.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiSamuraiBoots), 1, 1, 25));
		YI_TI = new GOTChestContents(4, 6, yiTi).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.YI_TI);
	}

	private final WeightedRandomChestContent[] items;
	private final int minItems;
	private final int maxItems;

	private boolean pouches;
	private GOTItemMug.Vessel[] vesselTypes;
	private List<GOTLore.LoreCategory> loreCategories = new ArrayList<>();
	private int loreChance = 10;

	@SuppressWarnings("WeakerAccess")
	public GOTChestContents(int i, int j, Collection<WeightedRandomChestContent> list) {
		WeightedRandomChestContent[] w = new WeightedRandomChestContent[list.size()];
		w = list.toArray(w);
		minItems = i;
		maxItems = j;
		items = w;
	}

	public static void fillChest(IBlockAccess world, Random random, int i, int j, int k, GOTChestContents itemPool) {
		fillChest(world, random, i, j, k, itemPool, -1);
	}

	public static void fillChest(IBlockAccess world, Random random, int i, int j, int k, GOTChestContents itemPool, int amount) {
		TileEntity tileentity = world.getTileEntity(i, j, k);
		if (!(tileentity instanceof IInventory)) {
			if (j >= 0 && j < 256) {
				FMLLog.warning("Warning: GOTChestContents attempted to fill a chest at " + i + ", " + j + ", " + k + " which does not exist");
			}
			return;
		}
		fillInventory((IInventory) tileentity, random, itemPool, amount);
	}

	public static void fillInventory(IInventory inventory, Random random, GOTChestContents itemPool, int amount) {
		fillInventory(inventory, random, itemPool, amount, false);
	}

	public static void fillInventory(IInventory inventory, Random random, GOTChestContents itemPool, int amount, boolean isNPCDrop) {
		int amount1 = amount;
		if (amount1 == -1) {
			amount1 = getRandomItemAmount(itemPool, random);
		} else if (amount1 <= 0) {
			throw new IllegalArgumentException("GOTChestContents tried to fill a chest with " + amount1 + " items");
		}
		for (int i = 0; i < amount1; ++i) {
			WeightedRandomChestContent wrcc = (WeightedRandomChestContent) WeightedRandom.getRandomItem(random, itemPool.items);
			for (ItemStack itemstack : ChestGenHooks.generateStacks(random, wrcc.theItemId, wrcc.theMinimumChanceToGenerateItem, wrcc.theMaximumChanceToGenerateItem)) {
				Item item;
				if (!isNPCDrop && itemPool.pouches) {
					if (random.nextInt(50) == 0) {
						itemstack = new ItemStack(GOTItems.pouch, 1, GOTItemPouch.getRandomPouchSize(random));
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
					if (random.nextInt(Math.max(loreChance, 1)) == 0 && (lore = GOTLore.getMultiRandomLore(itemPool.loreCategories, random, false)) != null) {
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
					if (((GOTItemMug) item).isBrewable()) {
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

	private static int getRandomItemAmount(GOTChestContents itemPool, Random random) {
		return MathHelper.getRandomIntegerInRange(random, itemPool.minItems, itemPool.maxItems);
	}

	public ItemStack getOneItem(Random random, boolean isNPCDrop) {
		IInventory drops = new InventoryBasic("oneItem", false, 1);
		fillInventory(drops, random, this, 1, isNPCDrop);
		ItemStack item = drops.getStackInSlot(0);
		item.stackSize = 1;
		return item;
	}

	@SuppressWarnings("WeakerAccess")
	public GOTChestContents enablePouches() {
		pouches = true;
		return this;
	}

	@SuppressWarnings("WeakerAccess")
	public GOTChestContents setDrinkVessels(GOTFoods foods) {
		return setDrinkVessels(foods.getDrinkVessels());
	}

	@SuppressWarnings("WeakerAccess")
	public GOTChestContents setDrinkVessels(GOTItemMug.Vessel... v) {
		vesselTypes = v;
		return this;
	}

	@SuppressWarnings("WeakerAccess")
	public GOTChestContents setLore(int chance, GOTLore.LoreCategory... categories) {
		loreCategories = Arrays.asList(categories);
		loreChance = chance;
		return this;
	}
}