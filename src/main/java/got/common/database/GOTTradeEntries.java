package got.common.database;

import got.common.GOTConfig;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.utils.GOTTradeEntry;
import got.common.entity.other.utils.GOTTradeEntryBarrel;
import got.common.entity.other.utils.GOTTradeSellResult;
import got.common.item.other.GOTItemMug;
import got.common.item.weapon.GOTItemLingeringPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import java.util.*;

@SuppressWarnings({"ConstantMathCall", "unused"})
public class GOTTradeEntries {
	public static final GOTTradeEntries BAELISH_SELLS;
	public static final GOTTradeEntries DAVOS_SELLS;
	public static final GOTTradeEntries TOBHO_SELLS;

	public static final GOTTradeEntries CROWNLANDS_ALCHEMIST_SELLS;
	public static final GOTTradeEntries CROWNLANDS_ALCHEMIST_BUYS;

	public static final GOTTradeEntries BAKER_SELLS;
	public static final GOTTradeEntries BAKER_BUYS;
	public static final GOTTradeEntries BREWER_SELLS;
	public static final GOTTradeEntries BREWER_BUYS;
	public static final GOTTradeEntries BUTCHER_SELLS;
	public static final GOTTradeEntries BUTCHER_BUYS;
	public static final GOTTradeEntries GOLDSMITH_SELLS;
	public static final GOTTradeEntries GOLDSMITH_BUYS;
	public static final GOTTradeEntries MINER_SELLS;
	public static final GOTTradeEntries MINER_BUYS;
	public static final GOTTradeEntries FISHMONGER_SELLS;
	public static final GOTTradeEntries FISHMONGER_BUYS;
	public static final GOTTradeEntries MAESTER_SELLS;
	public static final GOTTradeEntries MAESTER_BUYS;
	public static final GOTTradeEntries TRAMP_SELLS;
	public static final GOTTradeEntries TRAMP_BUYS;
	public static final GOTTradeEntries LUMBERMAN_SELLS;
	public static final GOTTradeEntries LUMBERMAN_BUYS;
	public static final GOTTradeEntries LUMBERMAN_EXOTIC_SELLS;
	public static final GOTTradeEntries LUMBERMAN_EXOTIC_BUYS;
	public static final GOTTradeEntries FLORIST_SELLS;
	public static final GOTTradeEntries FLORIST_BUYS;
	public static final GOTTradeEntries FARMER_SELLS;
	public static final GOTTradeEntries FARMER_BUYS;
	public static final GOTTradeEntries FARMER_EXOTIC_SELLS;
	public static final GOTTradeEntries FARMER_EXOTIC_BUYS;
	public static final GOTTradeEntries MASON_SELLS;
	public static final GOTTradeEntries MASON_BUYS;
	public static final GOTTradeEntries ALCHEMIST_SELLS;
	public static final GOTTradeEntries ALCHEMIST_BUYS;
	public static final GOTTradeEntries BARTENDER_SELLS;
	public static final GOTTradeEntries BARTENDER_BUYS;
	public static final GOTTradeEntries BLACKSMITH_SELLS;
	public static final GOTTradeEntries BLACKSMITH_BUYS;

	public static final GOTTradeEntries EMPTY_SELLS = new GOTTradeEntries(TradeType.SELLS, Collections.emptyList());
	public static final GOTTradeEntries EMPTY_BUYS = new GOTTradeEntries(TradeType.BUYS, Collections.emptyList());

	/* Зарегулированные пропорциональные цены */
	private static final int VALYRIAN_INGOT = 512;
	private static final int GOLD_INGOT = 128;
	private static final int SILVER_INGOT = 32;
	private static final int DIAMOND = 128;
	private static final int EMERALD = 96;
	private static final int AMBER = 64;
	private static final int OPAL = 64;
	private static final int RUBY = 64;
	private static final int SAPPHIRE = 64;
	private static final int TOPAZ = 64;
	private static final int AMETHYST = 64;

	static {
		List<GOTTradeEntry> baelishSells = new ArrayList<>();
		baelishSells.add(new GOTTradeEntry(new ItemStack(GOTItems.petyrBaelishDagger), VALYRIAN_INGOT * 8, true));
		BAELISH_SELLS = new GOTTradeEntries(TradeType.SELLS, baelishSells);

		List<GOTTradeEntry> davosSells = new ArrayList<>();
		davosSells.add(new GOTTradeEntry(new ItemStack(GOTItems.leek), 5));
		davosSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.leekCrop), 5));
		davosSells.add(new GOTTradeEntry(new ItemStack(GOTItems.leekSoup), 5));
		DAVOS_SELLS = new GOTTradeEntries(TradeType.SELLS, davosSells);

		List<GOTTradeEntry> tobhoSells = new ArrayList<>();
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianDagger), VALYRIAN_INGOT, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianDaggerPoisoned), VALYRIAN_INGOT, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianHammer), VALYRIAN_INGOT * 4, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianSpear), VALYRIAN_INGOT, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianSword), VALYRIAN_INGOT * 2, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianHelmet), VALYRIAN_INGOT * 5, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianChestplate), VALYRIAN_INGOT * 8, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianLeggings), VALYRIAN_INGOT * 7, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianBoots), VALYRIAN_INGOT * 4, true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianChainmailHelmet), VALYRIAN_INGOT * 3 + (int) Math.ceil(VALYRIAN_INGOT / 9.0 * 2), true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianChainmailChestplate), VALYRIAN_INGOT * 5 + (int) Math.ceil(VALYRIAN_INGOT / 9.0 * 3), true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianChainmailLeggings), VALYRIAN_INGOT * 5 + (int) Math.ceil(VALYRIAN_INGOT / 9.0 * 2), true));
		tobhoSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianChainmailBoots), VALYRIAN_INGOT * 2 + (int) Math.ceil(VALYRIAN_INGOT / 9.0 * 2), true));
		TOBHO_SELLS = new GOTTradeEntries(TradeType.SELLS, tobhoSells);

		List<GOTTradeEntry> bakerSells = new ArrayList<>();
		bakerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.appleCrumble), 12));
		bakerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.cherryPie), 12));
		bakerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.berryPie), 12));
		bakerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.cornBread), 5));
		bakerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.ceramicPlate), 2));
		bakerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.plate), 4));
		bakerSells.add(new GOTTradeEntry(new ItemStack(Items.bread), 5));
		bakerSells.add(new GOTTradeEntry(new ItemStack(Items.cake), 12));
		bakerSells.add(new GOTTradeEntry(new ItemStack(Items.cookie), 4));
		BAKER_SELLS = new GOTTradeEntries(TradeType.SELLS, bakerSells);

		List<GOTTradeEntry> bakerBuys = new ArrayList<>();
		bakerBuys.add(new GOTTradeEntry(new ItemStack(Items.apple), 1));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.appleGreen), 1));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.cherry), 2));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.blackberry), 2));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.blueberry), 2));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.cranberry), 2));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.elderberry), 2));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.raspberry), 2));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.clayPlate), 1));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.corn), 3));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.salt), 10));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(Items.bucket), 3));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(Items.milk_bucket), 4));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(Items.water_bucket), 4));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(Items.egg, 2), 1));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(Items.sugar, 2), 1));
		bakerBuys.add(new GOTTradeEntry(new ItemStack(Items.wheat, 2), 1));
		BAKER_BUYS = new GOTTradeEntries(TradeType.BUYS, bakerBuys);

		List<GOTTradeEntry> butcherSells = new ArrayList<>();
		butcherSells.add(new GOTTradeEntry(new ItemStack(GOTItems.gammon), 6));
		butcherSells.add(new GOTTradeEntry(new ItemStack(GOTItems.muttonRaw), 5));
		butcherSells.add(new GOTTradeEntry(new ItemStack(GOTItems.deerRaw), 4));
		butcherSells.add(new GOTTradeEntry(new ItemStack(GOTItems.rabbitRaw), 4));
		butcherSells.add(new GOTTradeEntry(new ItemStack(GOTItems.beaverRaw), 3));
		butcherSells.add(new GOTTradeEntry(new ItemStack(GOTItems.beaverTail), 3));
		butcherSells.add(new GOTTradeEntry(new ItemStack(Items.beef), 5));
		butcherSells.add(new GOTTradeEntry(new ItemStack(Items.chicken), 4));
		butcherSells.add(new GOTTradeEntry(new ItemStack(Items.leather), 3));
		butcherSells.add(new GOTTradeEntry(new ItemStack(Items.porkchop), 5));
		BUTCHER_SELLS = new GOTTradeEntries(TradeType.SELLS, butcherSells);

		List<GOTTradeEntry> butcherBuys = new ArrayList<>();
		butcherBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeDagger), 3));
		butcherBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.ironDagger), 3));
		butcherBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.salt), 10));
		butcherBuys.add(new GOTTradeEntry(new ItemStack(Items.iron_ingot), 3));
		butcherBuys.add(new GOTTradeEntry(new ItemStack(Items.lead), 4));
		BUTCHER_BUYS = new GOTTradeEntries(TradeType.BUYS, butcherBuys);

		List<GOTTradeEntry> brewerSells = new ArrayList<>();
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugAle), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugAraq), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugBananaBeer), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugBrandy), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugCactusLiqueur), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugCarrotWine), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugCherryLiqueur), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugCider), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugCornLiquor), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugGin), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugLemonLiqueur), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugLimeLiqueur), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugMapleBeer), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugMead), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugMelonLiqueur), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugPerry), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugPlumKvass), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugPomegranateWine), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugRedWine), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugRum), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugSambuca), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugSourMilk), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugVodka), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugWhisky), 138));
		brewerSells.add(new GOTTradeEntryBarrel(new ItemStack(GOTItems.mugWhiteWine), 138));
		BREWER_SELLS = new GOTTradeEntries(TradeType.SELLS, brewerSells);

		List<GOTTradeEntry> brewerBuys = new ArrayList<>();
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.almond), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.banana), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.cherry), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.corn), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.date), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.elderberry), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.grapeRed), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.grapeWhite), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.lemon), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.lime), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.mapleSyrup), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.pear), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.plum), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.pomegranate), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(Items.carrot), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(Items.melon), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(Items.milk_bucket), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(Items.potato), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(Items.reeds), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(Items.sugar), 1));
		brewerBuys.add(new GOTTradeEntry(new ItemStack(Items.wheat), 1));
		BREWER_BUYS = new GOTTradeEntries(TradeType.BUYS, brewerBuys);

		List<GOTTradeEntry> goldsmithSells = new ArrayList<>();
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.birdCage, 1, 3), SILVER_INGOT * 3 + (int) Math.ceil(SILVER_INGOT * 6.0 / 16.0 * 5), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.birdCage, 1, 4), GOLD_INGOT * 3 + (int) Math.ceil(GOLD_INGOT * 6.0 / 16.0 * 5), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.birdCage, 1, 5), VALYRIAN_INGOT * 3 + (int) Math.ceil(VALYRIAN_INGOT * 6.0 / 16.0 * 5), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.gobletSilver), SILVER_INGOT * 2, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.gobletGold), GOLD_INGOT * 2, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.gobletValyrian), VALYRIAN_INGOT * 2, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.silverBars), (int) Math.ceil(SILVER_INGOT * 6.0 / 16.0), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.goldBars), (int) Math.ceil(GOLD_INGOT * 6.0 / 16.0), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.valyrianBars), (int) Math.ceil(VALYRIAN_INGOT * 6.0 / 16.0), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.chandelier, 1, 3), SILVER_INGOT / 2, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.chandelier, 1, 4), GOLD_INGOT / 2, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.chandelier, 1, 5), VALYRIAN_INGOT / 2, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.silverRing), (int) Math.ceil(SILVER_INGOT / 9.0 * 8), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.goldRing), (int) Math.ceil(GOLD_INGOT / 9.0 * 8), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianRing), (int) Math.ceil(VALYRIAN_INGOT / 9.0 * 8), true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.gateSilverBars), SILVER_INGOT * 8, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.gateGoldBars), GOLD_INGOT * 8, true));
		goldsmithSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.gateValyrianBars), VALYRIAN_INGOT * 8, true));
		GOLDSMITH_SELLS = new GOTTradeEntries(TradeType.SELLS, goldsmithSells);

		List<GOTTradeEntry> goldsmithBuys = new ArrayList<>();
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.amber), AMBER, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.amethyst), AMETHYST, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.diamond), DIAMOND, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.emerald), EMERALD, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.opal), OPAL, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.ruby), RUBY, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.sapphire), SAPPHIRE, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.silverIngot), SILVER_INGOT, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.topaz), TOPAZ, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianIngot), VALYRIAN_INGOT, true));
		goldsmithBuys.add(new GOTTradeEntry(new ItemStack(Items.gold_ingot), GOLD_INGOT, true));
		GOLDSMITH_BUYS = new GOTTradeEntries(TradeType.BUYS, goldsmithBuys);

		List<GOTTradeEntry> minerSells = new ArrayList<>();
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.amber), AMBER, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.amethyst), AMETHYST, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.diamond), DIAMOND, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.emerald), EMERALD, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.opal), OPAL, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.ruby), RUBY, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.sapphire), SAPPHIRE, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.silverIngot), SILVER_INGOT, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.topaz), TOPAZ, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.valyrianIngot), VALYRIAN_INGOT, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(Items.gold_ingot), GOLD_INGOT, true));
		minerSells.add(new GOTTradeEntry(new ItemStack(Items.iron_ingot), 9));
		minerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeIngot), 6));
		MINER_SELLS = new GOTTradeEntries(TradeType.SELLS, minerSells);

		List<GOTTradeEntry> minerBuys = new ArrayList<>();
		minerBuys.add(new GOTTradeEntry(new ItemStack(Blocks.torch, 16), 2));
		minerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzePickaxe), 6));
		minerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.deerCooked), 3));
		minerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.muttonCooked), 3));
		minerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.rabbitCooked), 3));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.bread), 2));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.bucket), 3));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.coal, 2, 32767), 1));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.cooked_beef), 3));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.cooked_chicken), 3));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.cooked_porkchop), 3));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.iron_pickaxe), 8));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.stone_pickaxe), 1));
		minerBuys.add(new GOTTradeEntry(new ItemStack(Items.water_bucket), 4));
		MINER_BUYS = new GOTTradeEntries(TradeType.BUYS, minerBuys);

		List<GOTTradeEntry> fishmongerSells = new ArrayList<>();
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Blocks.sponge), 15));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.pearl), 12));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.boat), 5));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.dye, 1, 0), 4));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.fish, 1, 0), 4));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.fish, 1, 1), 6));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.fish, 1, 2), 8));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.fish, 1, 3), 12));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.fishing_rod), 8));
		fishmongerSells.add(new GOTTradeEntry(new ItemStack(Items.leather_boots), 5));
		FISHMONGER_SELLS = new GOTTradeEntries(TradeType.SELLS, fishmongerSells);

		List<GOTTradeEntry> fishmongerBuys = new ArrayList<>();
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(Blocks.planks, 4, 0), 1));
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeDagger), 3));
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.ironDagger), 3));
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.salt), 10));
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(Items.bucket), 3));
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(Items.iron_ingot), 3));
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(Items.stick, 8), 1));
		fishmongerBuys.add(new GOTTradeEntry(new ItemStack(Items.string, 3), 1));
		FISHMONGER_BUYS = new GOTTradeEntries(TradeType.BUYS, fishmongerBuys);

		List<GOTTradeEntry> maesterSells = new ArrayList<>();
		maesterSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bottlePoison), 10));
		maesterSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugPlantainBrew), 12));
		maesterSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugPoppyMilk), 12));
		maesterSells.add(new GOTTradeEntry(new ItemStack(Items.book, 1), 3));
		maesterSells.add(new GOTTradeEntry(new ItemStack(Items.writable_book, 1), 8));
		MAESTER_SELLS = new GOTTradeEntries(TradeType.SELLS, maesterSells);

		List<GOTTradeEntry> maesterBuys = new ArrayList<>();
		maesterBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.sulfurMatch), 1));
		maesterBuys.add(new GOTTradeEntry(new ItemStack(Items.flint_and_steel), 2));
		maesterBuys.add(new GOTTradeEntry(new ItemStack(Items.paper, 2), 1));
		MAESTER_BUYS = new GOTTradeEntries(TradeType.BUYS, maesterBuys);

		List<GOTTradeEntry> trampSells = new ArrayList<>();
		trampSells.add(new GOTTradeEntry(new ItemStack(GOTItems.saltedFlesh), 7));
		TRAMP_SELLS = new GOTTradeEntries(TradeType.SELLS, trampSells);

		List<GOTTradeEntry> trampBuys = new ArrayList<>();
		trampBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.chestnut, 2), 1));
		trampBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.fur), 2));
		trampBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.pipeweed, 3), 10));
		trampBuys.add(new GOTTradeEntry(new ItemStack(Items.feather), 2));
		trampBuys.add(new GOTTradeEntry(new ItemStack(Items.leather), 2));
		trampBuys.add(new GOTTradeEntry(new ItemStack(Items.rotten_flesh), 2));
		trampBuys.add(new GOTTradeEntry(new ItemStack(Items.string, 8), 1));
		trampBuys.add(new GOTTradeEntry(new ItemStack(Items.wheat_seeds, 2), 1));
		trampBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.beaverTail), 2));
		TRAMP_BUYS = new GOTTradeEntries(TradeType.BUYS, trampBuys);

		List<GOTTradeEntry> lumbermanSells = new ArrayList<>();
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.log, 1, 0), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.log, 1, 1), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.log, 1, 2), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.log2, 1, 1), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood2, 1, 1), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood2, 1, 2), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood3, 1, 0), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood3, 1, 1), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood4, 1, 0), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood4, 1, 2), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood4, 1, 3), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood5, 1, 0), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood6, 1, 1), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood6, 1, 2), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood6, 1, 3), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood7, 1, 0), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood7, 1, 2), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood7, 1, 3), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood8, 1, 0), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood8, 1, 2), 4));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.sapling, 1, 0), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.sapling, 1, 1), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.sapling, 1, 2), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(Blocks.sapling, 1, 5), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling2, 1, 1), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling2, 1, 2), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling3, 1, 0), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling3, 1, 1), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling4, 1, 0), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling4, 1, 2), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling4, 1, 3), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling5, 1, 0), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling6, 1, 1), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling6, 1, 2), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling6, 1, 3), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling7, 1, 0), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling7, 1, 3), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling8, 1, 0), 14));
		lumbermanSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling8, 1, 2), 14));
		LUMBERMAN_SELLS = new GOTTradeEntries(TradeType.SELLS, lumbermanSells);

		List<GOTTradeEntry> lumbermanBuys = new ArrayList<>();
		lumbermanBuys.add(new GOTTradeEntry(new ItemStack(Items.iron_axe), 8));
		lumbermanBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeAxe), 6));
		lumbermanBuys.add(new GOTTradeEntry(new ItemStack(Items.stone_axe), 1));
		lumbermanBuys.add(new GOTTradeEntry(new ItemStack(Items.bucket), 3));
		lumbermanBuys.add(new GOTTradeEntry(new ItemStack(Items.dye, 6, 15), 1));
		lumbermanBuys.add(new GOTTradeEntry(new ItemStack(Items.shears), 5));
		lumbermanBuys.add(new GOTTradeEntry(new ItemStack(Items.water_bucket), 4));
		LUMBERMAN_BUYS = new GOTTradeEntries(TradeType.BUYS, lumbermanBuys);

		List<GOTTradeEntry> lumbermanExoticSells = new ArrayList<>(lumbermanSells);
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(Blocks.log, 1, 3), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(Blocks.log2, 1, 0), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood1, 1, 0), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood1, 1, 1), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood2, 1, 0), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood2, 1, 3), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood3, 1, 2), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood3, 1, 3), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood4, 1, 1), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood5, 1, 1), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood5, 1, 2), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood5, 1, 3), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood6, 1, 0), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wood8, 1, 1), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(Blocks.sapling, 1, 3), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(Blocks.sapling, 1, 4), 4));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling1, 1, 0), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling1, 1, 1), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling2, 1, 0), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling2, 1, 3), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling3, 1, 2), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling3, 1, 3), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling4, 1, 1), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling5, 1, 1), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling5, 1, 2), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling5, 1, 3), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling6, 1, 0), 14));
		lumbermanExoticSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.sapling8, 1, 1), 14));
		LUMBERMAN_EXOTIC_SELLS = new GOTTradeEntries(TradeType.SELLS, lumbermanExoticSells);

		List<GOTTradeEntry> lumbermanExoticBuys = new ArrayList<>(lumbermanBuys);
		LUMBERMAN_EXOTIC_BUYS = new GOTTradeEntries(TradeType.BUYS, lumbermanExoticBuys);

		List<GOTTradeEntry> farmerSells = new ArrayList<>();
		farmerSells.add(new GOTTradeEntry(new ItemStack(Blocks.melon_block, 1), 3, true));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Blocks.melon_block, 3), 10, true));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.melon, 15), 10, true));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.melon, 5), 3, true));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Blocks.pumpkin, 1), 3));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Blocks.hay_block), 12));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Blocks.wool), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.carrot), 3));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.milk_bucket), 8));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.potato), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.apple), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.wheat), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(Items.wheat_seeds), 1));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.cornStalk), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.driedReeds), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.reeds), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.pipeweedPlant), 4));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.almond), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.appleGreen), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.cherry), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.corn), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.date), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.grapeRed), 3));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.grapeWhite), 3));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.leek), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.lettuce), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.olive), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.pear), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.plum), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.pomegranate), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.raisins), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.turnip), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.yam), 2));
		FARMER_SELLS = new GOTTradeEntries(TradeType.SELLS, farmerSells);

		List<GOTTradeEntry> farmerBuys = new ArrayList<>();
		farmerBuys.add(new GOTTradeEntry(new ItemStack(Items.iron_hoe), 8));
		farmerBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeHoe), 6));
		farmerBuys.add(new GOTTradeEntry(new ItemStack(Items.stone_hoe), 1));
		farmerBuys.add(new GOTTradeEntry(new ItemStack(Items.bucket), 3));
		farmerBuys.add(new GOTTradeEntry(new ItemStack(Items.dye, 6, 15), 1));
		farmerBuys.add(new GOTTradeEntry(new ItemStack(Items.water_bucket), 4));
		FARMER_BUYS = new GOTTradeEntries(TradeType.BUYS, farmerBuys);

		List<GOTTradeEntry> farmerExoticSells = new ArrayList<>(farmerSells);
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.banana), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.date), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.lemon), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.lime), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mango), 2));
		farmerSells.add(new GOTTradeEntry(new ItemStack(GOTItems.orange), 2));
		FARMER_EXOTIC_SELLS = new GOTTradeEntries(TradeType.SELLS, farmerExoticSells);

		List<GOTTradeEntry> farmerExoticBuys = new ArrayList<>(farmerBuys);
		FARMER_EXOTIC_BUYS = new GOTTradeEntries(TradeType.BUYS, farmerExoticBuys);

		List<GOTTradeEntry> floristSells = new ArrayList<>();
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.double_plant, 1, 0), 15));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.double_plant, 1, 1), 8));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.double_plant, 1, 4), 8));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.double_plant, 1, 5), 8));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 0), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 1), 8));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 2), 6));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 3), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 4), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 5), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 6), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 7), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.red_flower, 1, 8), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(Blocks.yellow_flower, 1, 8), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.bluebell, 1, 0), 3));
		floristSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.doubleFlower, 1, 0), 25));
		floristSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.doubleFlower, 1, 1), 10));
		floristSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.doubleFlower, 1, 2), 45));
		floristSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.doubleFlower, 1, 3), 45));
		floristSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.marigold, 1, 0), 3));
		FLORIST_SELLS = new GOTTradeEntries(TradeType.SELLS, floristSells);

		List<GOTTradeEntry> floristBuys = new ArrayList<>(farmerBuys);
		FLORIST_BUYS = new GOTTradeEntries(TradeType.BUYS, floristBuys);

		List<GOTTradeEntry> blacksmithSells = new ArrayList<>();
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(Items.chainmail_boots), (int) Math.ceil(2.5 * 4)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(Items.chainmail_chestplate), (int) Math.ceil(2.5 * 8)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(Items.chainmail_helmet), (int) Math.ceil(2.5 * 5)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(Items.chainmail_leggings), (int) Math.ceil(2.5 * 7)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeChainmailBoots), (int) Math.ceil(1.5 * 4)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeChainmailChestplate), (int) Math.ceil(1.5 * 8)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeChainmailHelmet), (int) Math.ceil(1.5 * 5)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeChainmailLeggings), (int) Math.ceil(1.5 * 7)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.blacksmithHammer), (int) Math.ceil(2.5 * 8)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.harpoon), (int) Math.ceil(2.5 * 8)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.trident), (int) Math.ceil(2.5 * 8)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.ironCrossbow), (int) Math.ceil(3.0 * 8)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeCrossbow), (int) Math.ceil(3.0 * 8)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.crossbowBolt, 4), 3));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.ironDagger), (int) Math.ceil(2.5 * 2)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.ironSpear), (int) Math.ceil(2.5 * 2)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.ironBattleaxe), (int) Math.ceil(2.5 * 4)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.ironThrowingAxe), (int) Math.ceil(2.5 * 3)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeDagger), (int) Math.ceil(1.5 * 2)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeSpear), (int) Math.ceil(1.5 * 2)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeBattleaxe), (int) Math.ceil(1.5 * 2)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeThrowingAxe), (int) Math.ceil(1.5 * 2)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(Items.iron_sword), (int) Math.ceil(2.5 * 3)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(Items.saddle), (int) Math.ceil(2.0 * 2)));
		blacksmithSells.add(new GOTTradeEntry(new ItemStack(Items.bow), (int) Math.ceil(2.0 * 2)));
		BLACKSMITH_SELLS = new GOTTradeEntries(TradeType.SELLS, blacksmithSells);

		List<GOTTradeEntry> blacksmithBuys = new ArrayList<>();
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.alloySteelIngot), 12));
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(Items.iron_ingot), 9));
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.bronzeIngot), 6));
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.copperIngot), 3));
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.tinIngot), 3));
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(Items.coal, 2, 32767), 1));
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(Items.leather), 2));
		blacksmithBuys.add(new GOTTradeEntry(new ItemStack(Items.string, 3), 1));
		BLACKSMITH_BUYS = new GOTTradeEntries(TradeType.BUYS, blacksmithBuys);

		List<GOTTradeEntry> masonSells = new ArrayList<>();
		masonSells.add(new GOTTradeEntry(new ItemStack(Blocks.sandstone, 8), 4));
		masonSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.redSandstone, 8), 4));
		masonSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.whiteSandstone, 8), 4));
		masonSells.add(new GOTTradeEntry(new ItemStack(Blocks.stone, 8), 2));
		masonSells.add(new GOTTradeEntry(new ItemStack(Blocks.cobblestone, 8), 1));
		masonSells.add(new GOTTradeEntry(new ItemStack(Blocks.hardened_clay, 8), 4));
		for (int i = 0; i <= 6; i++) {
			masonSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.rock, 8, i), 3));
		}
		for (int i = 0; i <= 15; i++) {
			masonSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.concretePowder, 8, i), 3));
		}
		MASON_SELLS = new GOTTradeEntries(TradeType.SELLS, masonSells);

		List<GOTTradeEntry> masonBuys = new ArrayList<>(minerBuys);
		MASON_BUYS = new GOTTradeEntries(TradeType.BUYS, masonBuys);

		List<GOTTradeEntry> alchemistSells = new ArrayList<>();
		alchemistSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bottlePoison), 8));

		List<Integer> ids = GOTItemLingeringPotion.getPotionsSubIds();
		for (int i : ids) {
			alchemistSells.add(new GOTTradeEntry(new ItemStack(GOTItems.lingeringPotion, 1, i), 64));
			alchemistSells.add(new GOTTradeEntry(new ItemStack(Items.potionitem, 1, i), 64));
		}

		ALCHEMIST_SELLS = new GOTTradeEntries(TradeType.SELLS, alchemistSells);

		List<GOTTradeEntry> alchemistBuys = new ArrayList<>();
		alchemistBuys.add(new GOTTradeEntry(new ItemStack(Blocks.brown_mushroom, 1, 0), 2));
		alchemistBuys.add(new GOTTradeEntry(new ItemStack(Blocks.red_mushroom, 1, 0), 2));
		alchemistBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.mug), 1));
		alchemistBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.obsidianShard), 3));
		alchemistBuys.add(new GOTTradeEntry(new ItemStack(Items.bone), 1));
		alchemistBuys.add(new GOTTradeEntry(new ItemStack(Items.glass_bottle), 2));
		ALCHEMIST_BUYS = new GOTTradeEntries(TradeType.BUYS, alchemistBuys);

		List<GOTTradeEntry> crownlandsAlchemistSells = new ArrayList<>();
		crownlandsAlchemistSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.bomb), 64));
		crownlandsAlchemistSells.add(new GOTTradeEntry(new ItemStack(GOTItems.bottlePoison), 10));
		crownlandsAlchemistSells.add(new GOTTradeEntry(new ItemStack(GOTItems.firePot), 32));
		crownlandsAlchemistSells.add(new GOTTradeEntry(new ItemStack(GOTItems.sulfurMatch), 1));
		crownlandsAlchemistSells.add(new GOTTradeEntry(new ItemStack(GOTBlocks.wildFireJar), 128));
		CROWNLANDS_ALCHEMIST_SELLS = new GOTTradeEntries(TradeType.SELLS, crownlandsAlchemistSells);

		List<GOTTradeEntry> crownlandsAlchemistBuys = new ArrayList<>(alchemistBuys);
		CROWNLANDS_ALCHEMIST_BUYS = new GOTTradeEntries(TradeType.BUYS, crownlandsAlchemistBuys);

		List<GOTTradeEntry> bartenderSells = new ArrayList<>();
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugAle), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugAraq), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugBananaBeer), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugBrandy), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugCactusLiqueur), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugCarrotWine), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugCherryLiqueur), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugCider), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugCornLiquor), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugGin), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugLemonLiqueur), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugLimeLiqueur), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugMapleBeer), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugMead), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugMelonLiqueur), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugPerry), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugPlumKvass), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugPomegranateWine), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugRedWine), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugRum), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugSambuca), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugSourMilk), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugVodka), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugWhisky), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.mugWhiteWine), 12));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.beaverCooked), 6));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.deerCooked), 6));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.muttonCooked), 7));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(GOTItems.rabbitCooked), 7));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(Items.cooked_beef), 7));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(Items.cooked_chicken), 6));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(Items.cooked_fished), 6));
		bartenderSells.add(new GOTTradeEntry(new ItemStack(Items.cooked_porkchop), 7));
		BARTENDER_SELLS = new GOTTradeEntries(TradeType.SELLS, bartenderSells);

		List<GOTTradeEntry> bartenderBuys = new ArrayList<>();
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.plate), 2));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.clayPlate), 1));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.ceramicPlate), 2));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.mug), 2));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.clayMug), 1));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.ceramicMug), 2));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.gobletWood), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.gobletBronze), 5));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.gobletCopper), 5));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.aleHorn), 4));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.waterskin), 4));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.wineGlass), 4));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.beaverRaw), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.deerRaw), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.muttonRaw), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(GOTItems.rabbitRaw), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(Items.beef), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(Items.chicken), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(Items.fish), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(Items.porkchop), 3));
		bartenderBuys.add(new GOTTradeEntry(new ItemStack(Items.coal, 2, 32767), 1));
		BARTENDER_BUYS = new GOTTradeEntries(TradeType.BUYS, bartenderBuys);
	}

	private final GOTTradeEntries.TradeType tradeType;
	private final GOTTradeEntry[] tradeEntries;

	private GOTItemMug.Vessel[] drinkVessels;

	@SuppressWarnings("WeakerAccess")
	public GOTTradeEntries(GOTTradeEntries.TradeType t, List<GOTTradeEntry> list) {
		GOTTradeEntry[] arr = new GOTTradeEntry[list.size()];
		arr = list.toArray(arr);
		tradeType = t;
		tradeEntries = arr;
	}

	public static GOTTradeSellResult getItemSellResult(ItemStack itemstack, GOTEntityNPC trader) {
		GOTTradeEntry[] sellTrades = trader.getTraderInfo().getSellTrades();
		if (sellTrades != null) {
			for (int index = 0; index < sellTrades.length; ++index) {
				GOTTradeEntry trade = sellTrades[index];
				if (trade != null && trade.matches(itemstack)) {
					return new GOTTradeSellResult(index, trade, itemstack);
				}
			}
		}
		return null;
	}

	public GOTTradeEntry[] getRandomTrades(Random random) {
		int numTrades = 3 + random.nextInt(3) + random.nextInt(3) + random.nextInt(3);
		if (numTrades > tradeEntries.length) {
			numTrades = tradeEntries.length;
		}
		GOTTradeEntry[] tempTrades = new GOTTradeEntry[tradeEntries.length];
		System.arraycopy(tradeEntries, 0, tempTrades, 0, tradeEntries.length);
		List<GOTTradeEntry> tempTradesAsList = Arrays.asList(tempTrades);
		Collections.shuffle(tempTradesAsList);
		tempTrades = tempTradesAsList.toArray(tempTrades);
		GOTTradeEntry[] trades = new GOTTradeEntry[numTrades];

		for (int i = 0; i < trades.length; ++i) {
			ItemStack tradeItem = tempTrades[i].createTradeItem();
			int originalCost = tempTrades[i].getCost();
			float tradeCost = originalCost;
			boolean frozenPrice = tempTrades[i].isFrozenPrice();
			int tradeCostI;
			if (tradeItem.getItem() instanceof GOTItemMug && ((GOTItemMug) tradeItem.getItem()).isBrewable() && tradeItem.getItemDamage() == 9999) {
				tradeCostI = 1 + random.nextInt(3);
				tradeItem.setItemDamage(tradeCostI);
				tradeCost *= GOTItemMug.getFoodStrength(tradeItem);
			}
			if (drinkVessels != null && GOTItemMug.isItemFullDrink(tradeItem)) {
				GOTItemMug.Vessel v = drinkVessels[random.nextInt(drinkVessels.length)];
				GOTItemMug.setVessel(tradeItem, v, true);
				tradeCost += v.getExtraPrice();
			}
			if (GOTConfig.enchantingGOT && tradeType == GOTTradeEntries.TradeType.SELLS) {
				boolean skilful = random.nextInt(3) == 0;
				GOTEnchantmentHelper.applyRandomEnchantments(tradeItem, random, skilful, false);
				tradeCost *= GOTEnchantmentHelper.calcTradeValueFactor(tradeItem);
			}
			tradeCost *= MathHelper.randomFloatClamp(random, 0.75F, 1.25F);
			tradeCost = Math.max(tradeCost, 1.0F);
			tradeCostI = Math.round(tradeCost);
			tradeCostI = Math.max(tradeCostI, 1);
			trades[i] = new GOTTradeEntry(tradeItem, frozenPrice ? originalCost : tradeCostI);
		}
		return trades;
	}

	@SuppressWarnings("WeakerAccess")
	public GOTTradeEntries setVessels(GOTFoods foods) {
		return setVessels(foods.getDrinkVessels());
	}

	@SuppressWarnings("WeakerAccess")
	public GOTTradeEntries setVessels(GOTItemMug.Vessel... v) {
		if (tradeType != GOTTradeEntries.TradeType.SELLS) {
			throw new IllegalArgumentException("Cannot set the vessel types for a sell list");
		}
		drinkVessels = v;
		return this;
	}

	public GOTTradeEntry[] getTradeEntries() {
		return tradeEntries;
	}

	public enum TradeType {
		SELLS, BUYS
	}
}