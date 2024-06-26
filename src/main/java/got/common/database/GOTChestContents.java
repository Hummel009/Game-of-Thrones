package got.common.database;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTConfig;
import got.common.GOTLore;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.item.other.GOTItemModifierTemplate;
import got.common.item.other.GOTItemMug;
import got.common.item.other.GOTItemPouch;
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
	public static final GOTChestContents GOLDEN;
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

	private static final Collection<WeightedRandomChestContent> NORD_L = new ArrayList<>();
	private static final Collection<WeightedRandomChestContent> SUD_L = new ArrayList<>();
	private static final Collection<WeightedRandomChestContent> NOMAD_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> SOTHORYOS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> YI_TI_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> MOSSOVY_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> GIFT_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> TREASURE_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> ASSHAI_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> BEYOND_WALL_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> IBBEN_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> HILLMEN_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> SUMMER_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> LHAZAR_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> ARRYN_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> BRAAVOS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> CROWNLANDS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> DORNE_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> DRAGONSTONE_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> GHISCAR_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> GOLDEN_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> IRONBORN_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> LORATH_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> LYS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> MYR_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> NORTH_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> NORVOS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> PENTOS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> QARTH_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> QOHOR_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> REACH_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> RIVERLANDS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> STORMLANDS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> TYROSH_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> VOLANTIS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> WESTERLANDS_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> JOGOS_NHAI_L = new ArrayList<>();
	private static final List<WeightedRandomChestContent> DOTHRAKI_L = new ArrayList<>();

	static {
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironCrossbow), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 6, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 6, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperNugget), 1, 6, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 1, 4, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lettuce), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leek), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnip), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnipCooked), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pear), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.plum), 1, 3, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.muttonCooked), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.rabbitCooked), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeAxe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeShovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDagger), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leatherHat), 1, 1, 50));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.aleHorn), 1, 3, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAle), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCider), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPerry), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugMead), 1, 1, 10));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		NORD_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 6, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 6, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperNugget), 1, 6, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironCrossbow), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 1, 6, 75));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.oliveBread), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pear), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.date), 1, 3, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lemon), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lemonCake), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.orange), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lime), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.plum), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.olive), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.almond), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.marzipan), 1, 3, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.marzipanChocolate), 1, 3, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.baked_potato), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.muttonCooked), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.camelCooked), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.kebab), 1, 4, 100));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.shishKebab), 1, 2, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lettuce), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 1, 4, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicPlate), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAraq), 1, 1, 75));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCactusLiqueur), 1, 1, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugOrangeJuice), 1, 1, 50));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugLemonade), 1, 1, 10));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugLemonLiqueur), 1, 1, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugLimeLiqueur), 1, 1, 25));
		SUD_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25));

		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 2, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 2, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.obsidianShard), 1, 4, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 6, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.reeds), 1, 5, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTBlocks.reeds), 1, 5, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTBlocks.driedReeds), 2, 6, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mango), 1, 3, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.banana), 1, 4, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 1, 4, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.melonSoup), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bananaBread), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.cornBread), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.corn), 1, 4, 100));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.cornCooked), 1, 4, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTBlocks.cornStalk), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 5, 100));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.baked_potato), 1, 2, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugMelonLiqueur), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugBananaBeer), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCornLiquor), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugChocolate), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCocoa), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugMangoJuice), 1, 1, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 3), 1, 8, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sothoryosDagger), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sothoryosDaggerPoisoned), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sothoryosAxe), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.sarbacane), 1, 1, 25));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTBlocks.sarbacaneTrap), 2, 8, 50));
		SOTHORYOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dartPoisoned), 1, 4, 25));

		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.melon_block), 2, 8, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.melon), 2, 8, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.paper), 2, 8, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.book), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiSteelIngot), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 75));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.oliveBread), 1, 3, 100));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leek), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnip), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnipCooked), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.olive), 1, 5, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.date), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.raisins), 1, 4, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pomegranate), 1, 3, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.reeds), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.rabbitCooked), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeAxe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeShovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDagger), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiDagger), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiSword), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBattleaxe), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiSpear), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiHelmet), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiChestplate), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiLeggings), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBoots), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leatherHat), 1, 1, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugSourMilk), 1, 1, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.milk_bucket), 1, 1, 50));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAle), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAraq), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPomegranateWine), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPomegranateJuice), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugRedWine), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugWhiteWine), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugRedGrapeJuice), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugWhiteGrapeJuice), 1, 1, 10));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.yiTiBow), 1, 1, 25));
		YI_TI_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 50));

		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 6, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 6, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperNugget), 1, 6, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mossovyChestplate), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mossovyLeggings), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mossovyBoots), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lettuce), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leek), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leekSoup), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mushroomPie), 1, 2, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.mushroom_stew), 1, 1, 100));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnip), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnipCooked), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pear), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.plum), 1, 3, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.muttonCooked), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.rabbitCooked), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeAxe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeShovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDagger), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHelmet), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChestplate), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeLeggings), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeBoots), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_helmet), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_chestplate), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_leggings), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_boots), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leatherHat), 1, 1, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAle), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCider), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPerry), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugMead), 1, 1, 10));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAppleJuice), 1, 1, 50));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		MOSSOVY_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 6, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 6, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperNugget), 1, 6, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lettuce), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leek), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnip), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnipCooked), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pear), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.plum), 1, 3, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.muttonCooked), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.rabbitCooked), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeAxe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeShovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDagger), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftHelmet), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftChestplate), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftLeggings), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.giftBoots), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leatherHat), 1, 1, 50));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAle), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCider), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPerry), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugMead), 1, 1, 10));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		GIFT_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 0), 1, 20, 200));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 1), 1, 8, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.coin, 1, 2), 1, 2, 5));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.gold_nugget), 1, 5, 100));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(Items.gold_ingot), 1, 4, 75));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverNugget), 1, 5, 100));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverIngot), 1, 4, 75));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletGold), 1, 3, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletSilver), 1, 3, 20));
		TREASURE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 3, 20));

		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 3, 100));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 8, 16, 100));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.fur), 1, 4, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.dragon_egg), 1, 1, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.skullCup), 1, 3, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiDagger), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiDaggerPoisoned), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiSword), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiBattleaxe), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiSpear), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiHammer), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.asshaiShadowbinderStaff), 1, 1, 25));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bottlePoison), 1, 1, 50));
		ASSHAI_L.add(new WeightedRandomChestContent(new ItemStack(Items.saddle), 1, 1, 50));

		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 75));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 50));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 3, 50));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerRaw), 1, 3, 100));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 25));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 25));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironBattleaxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDaggerPoisoned), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironPike), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironCrossbow), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironSpear), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironThrowingAxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDagger), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeBattleaxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeAxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeSword), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeSpear), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeThrowingAxe), 1, 1, 10));
		BEYOND_WALL_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 50));

		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailHelmet), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailChestplate), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailLeggings), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailBoots), 1, 1, 5));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 6, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 6, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperNugget), 1, 6, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal, 1, 1), 1, 3, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.flint_and_steel), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.fishing_rod), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 4, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 4, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.horn), 1, 2, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 4, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat_seeds), 1, 6, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 100));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Blocks.hay_block), 1, 6, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lettuce), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leek), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnip), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnipCooked), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pear), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.plum), 1, 3, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_porkchop), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_fished), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_chicken), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.muttonCooked), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.rabbitCooked), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_hoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeHoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_hoe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_axe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeAxe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_axe), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_shovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeShovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_shovel), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeDagger), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.harpoon), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ibbenChestplate), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ibbenLeggings), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ibbenBoots), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leatherHat), 1, 1, 50));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.aleHorn), 1, 3, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAle), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCider), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugPerry), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugMead), 1, 1, 10));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironCrossbow), 1, 1, 25));
		IBBEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));

		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.nomadSword), 1, 1, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.nomadSpear), 1, 1, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.nomadBattleaxe), 1, 1, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.nomadBow), 1, 1, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.oliveBread), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.date), 1, 3, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.camelCooked), 1, 2, 200));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.kebab), 1, 4, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.shishKebab), 1, 2, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicPlate), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAraq), 1, 1, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugCactusLiqueur), 1, 1, 100));
		NOMAD_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25));

		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_helmet), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_chestplate), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_leggings), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.chainmail_boots), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailHelmet), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailChestplate), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailLeggings), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailBoots), 1, 1, 5));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 6, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 6, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperNugget), 1, 6, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.fur), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 3, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 2, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.potato), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.carrot), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.leek), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnip), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.turnipCooked), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 2, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.appleGreen), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerRaw), 1, 3, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.deerCooked), 1, 3, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_sword), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_sword), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.wooden_sword), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_hoe), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.stone_shovel), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stoneSpear), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironDagger), 1, 1, 50));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironSpear), 1, 1, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironBattleaxe), 1, 1, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenHelmet), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenChestplate), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenLeggings), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.hillmenBoots), 1, 1, 20));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.bow), 1, 1, 75));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 5, 100));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.aleHorn), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.skullCup), 1, 3, 25));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 3, 75));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		HILLMEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));

		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailHelmet), 1, 1, 5));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailChestplate), 1, 1, 5));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailLeggings), 1, 1, 5));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeChainmailBoots), 1, 1, 5));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironNugget), 1, 6, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeNugget), 1, 6, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperNugget), 1, 6, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldRing), 1, 1, 10));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.silverRing), 1, 1, 10));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.copperRing), 1, 1, 10));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeRing), 1, 1, 10));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerSword), 1, 1, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerDagger), 1, 1, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerDaggerPoisoned), 1, 1, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerPike), 1, 1, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerHelmet), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerChestplate), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerLeggings), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.summerBoots), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.arrow), 2, 8, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.bucket), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.water_bucket), 1, 1, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 4, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.feather), 1, 2, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.leather), 1, 4, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.bronzeIngot), 1, 3, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.string), 1, 3, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.stick), 1, 8, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.bread), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.oliveBread), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.date), 1, 3, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.cooked_beef), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.rabbitCooked), 1, 2, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.muttonCooked), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.camelCooked), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.kebab), 1, 4, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.shishKebab), 1, 2, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 75));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.wheat), 1, 5, 100));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicPlate), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mug), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ceramicMug), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.waterskin), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletCopper), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.gobletWood), 1, 3, 25));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.glass_bottle), 1, 2, 50));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.mugAraq), 1, 1, 200));
		SUMMER_L.add(new WeightedRandomChestContent(new ItemStack(Items.dye, 1, 4), 1, 8, 25));

		ARRYN_L.addAll(NORD_L);
		BRAAVOS_L.addAll(NORD_L);
		CROWNLANDS_L.addAll(NORD_L);
		DRAGONSTONE_L.addAll(NORD_L);
		IRONBORN_L.addAll(NORD_L);
		LORATH_L.addAll(NORD_L);
		NORTH_L.addAll(NORD_L);
		NORVOS_L.addAll(NORD_L);
		QOHOR_L.addAll(NORD_L);
		REACH_L.addAll(NORD_L);
		RIVERLANDS_L.addAll(NORD_L);
		STORMLANDS_L.addAll(NORD_L);
		WESTERLANDS_L.addAll(NORD_L);

		DORNE_L.addAll(SUD_L);
		GHISCAR_L.addAll(SUD_L);
		LHAZAR_L.addAll(SUD_L);
		GOLDEN_L.addAll(SUD_L);
		LYS_L.addAll(SUD_L);
		MYR_L.addAll(SUD_L);
		PENTOS_L.addAll(SUD_L);
		QARTH_L.addAll(SUD_L);
		TYROSH_L.addAll(SUD_L);
		VOLANTIS_L.addAll(SUD_L);

		JOGOS_NHAI_L.addAll(NOMAD_L);
		DOTHRAKI_L.addAll(NOMAD_L);

		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiHelmet), 1, 1, 10));
		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiChestplate), 1, 1, 10));
		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiLeggings), 1, 1, 10));
		DOTHRAKI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dothrakiBoots), 1, 1, 10));

		JOGOS_NHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiHelmet), 1, 1, 25));
		JOGOS_NHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiChestplate), 1, 1, 25));
		JOGOS_NHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiLeggings), 1, 1, 25));
		JOGOS_NHAI_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.jogosNhaiBoots), 1, 1, 25));

		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynHelmet), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynChestplate), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynLeggings), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.arrynBoots), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		ARRYN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsHelmet), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsChestplate), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsLeggings), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.crownlandsBoots), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		CROWNLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneHelmet), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneChestplate), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneLeggings), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dorneBoots), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		DORNE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneHelmet), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneChestplate), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneLeggings), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.dragonstoneBoots), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		DRAGONSTONE_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornHelmet), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornChestplate), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornLeggings), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ironbornBoots), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		IRONBORN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northHelmet), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northChestplate), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northLeggings), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.northBoots), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		NORTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachHelmet), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachChestplate), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachLeggings), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.reachBoots), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		REACH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsHelmet), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsChestplate), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsLeggings), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.riverlandsBoots), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		RIVERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsHelmet), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsChestplate), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsLeggings), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.stormlandsBoots), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		STORMLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsHelmet), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsChestplate), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsLeggings), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerlandsBoots), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosBow), 1, 1, 25));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosDagger), 1, 1, 10));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSword), 1, 1, 10));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosSpear), 1, 1, 10));
		WESTERLANDS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.westerosHammer), 1, 1, 10));

		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosHelmet), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosChestplate), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosLeggings), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.braavosBoots), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		BRAAVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarHelmet), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarChestplate), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarLeggings), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.ghiscarBoots), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		GHISCAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarHelmet), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarChestplate), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarLeggings), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lhazarBoots), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		LHAZAR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathHelmet), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathChestplate), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathLeggings), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lorathBoots), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		LORATH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysHelmet), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysChestplate), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysLeggings), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.lysBoots), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		LYS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrHelmet), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrChestplate), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrLeggings), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.myrBoots), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		MYR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosHelmet), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosChestplate), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosLeggings), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.norvosBoots), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		NORVOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosHelmet), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosChestplate), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosLeggings), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.pentosBoots), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		PENTOS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthHelmet), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthChestplate), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthLeggings), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qarthBoots), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		QARTH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorHelmet), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorChestplate), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorLeggings), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.qohorBoots), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		QOHOR_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshHelmet), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshChestplate), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshLeggings), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.tyroshBoots), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		TYROSH_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisHelmet), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisChestplate), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisLeggings), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.volantisBoots), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		VOLANTIS_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyHelmet), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyChestplate), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyLeggings), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.goldenCompanyBoots), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSword), 1, 1, 50));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosPolearm), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosDagger), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosSpear), 1, 1, 25));
		GOLDEN_L.add(new WeightedRandomChestContent(new ItemStack(GOTItems.essosBow), 1, 1, 50));

		ARRYN = new GOTChestContents(4, 6, ARRYN_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		ASSHAI = new GOTChestContents(4, 6, ASSHAI_L).enablePouches().setLore(20, GOTLore.LoreCategory.ASSHAI);
		BEYOND_WALL = new GOTChestContents(4, 6, BEYOND_WALL_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);
		BRAAVOS = new GOTChestContents(4, 6, BRAAVOS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		CROWNLANDS = new GOTChestContents(4, 6, CROWNLANDS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		DORNE = new GOTChestContents(4, 6, DORNE_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		DOTHRAKI = new GOTChestContents(4, 6, DOTHRAKI_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);
		DRAGONSTONE = new GOTChestContents(4, 6, DRAGONSTONE_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		GHISCAR = new GOTChestContents(4, 6, GHISCAR_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		GIFT = new GOTChestContents(4, 6, GIFT_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		GOLDEN = new GOTChestContents(4, 6, GOLDEN_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		HILLMEN = new GOTChestContents(4, 6, HILLMEN_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);
		IBBEN = new GOTChestContents(4, 6, IBBEN_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);
		IRONBORN = new GOTChestContents(4, 6, IRONBORN_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		JOGOS_NHAI = new GOTChestContents(4, 6, JOGOS_NHAI_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK);
		LHAZAR = new GOTChestContents(4, 6, LHAZAR_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		LORATH = new GOTChestContents(4, 6, LORATH_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		LYS = new GOTChestContents(4, 6, LYS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		MOSSOVY = new GOTChestContents(4, 6, MOSSOVY_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.MOSSOVY);
		MYR = new GOTChestContents(4, 6, MYR_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		NORTH = new GOTChestContents(4, 6, NORTH_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		NORVOS = new GOTChestContents(4, 6, NORVOS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		PENTOS = new GOTChestContents(4, 6, PENTOS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		QARTH = new GOTChestContents(4, 6, QARTH_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		QOHOR = new GOTChestContents(4, 6, QOHOR_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		REACH = new GOTChestContents(4, 6, REACH_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		RIVERLANDS = new GOTChestContents(4, 6, RIVERLANDS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		SOTHORYOS = new GOTChestContents(4, 6, SOTHORYOS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);
		STORMLANDS = new GOTChestContents(4, 6, STORMLANDS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		SUMMER = new GOTChestContents(4, 6, SUMMER_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.SOTHORYOS);
		TREASURE = new GOTChestContents(4, 6, TREASURE_L).enablePouches();
		TYROSH = new GOTChestContents(4, 6, TYROSH_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		VOLANTIS = new GOTChestContents(4, 6, VOLANTIS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.ESSOS);
		WESTERLANDS = new GOTChestContents(4, 6, WESTERLANDS_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.WESTEROS);
		YI_TI = new GOTChestContents(4, 6, YI_TI_L).enablePouches().setDrinkVessels(GOTFoods.DEFAULT_DRINK).setLore(20, GOTLore.LoreCategory.YI_TI);
	}

	private final WeightedRandomChestContent[] items;
	private final int minItems;
	private final int maxItems;

	private boolean pouches;
	private GOTItemMug.Vessel[] vesselTypes;
	private List<GOTLore.LoreCategory> loreCategories = new ArrayList<>();
	private int loreChance = 10;

	@SuppressWarnings("WeakerAccess")
	public GOTChestContents(int i, int j, List<WeightedRandomChestContent> list) {
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